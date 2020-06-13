/*
 * Copyright 2020 ConsenSys AG.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
pragma solidity >=0.4.23;

import "./RegistrarInterface.sol";
import "./VotingAlgInterface.sol";

contract Registrar is RegistrarInterface {
    // Implementation version.
    uint16 constant private VERSION_ONE = 1;

    // Indications that a vote is underway.
    // VOTE_NONE indicates no vote is underway. Also matches the deleted value for integers.
    enum VoteType {
        VOTE_NONE,                            // 0: MUST be the first value so it is the zero / deleted value.
        VOTE_ADD_ADMIN,                       // 1
        VOTE_REMOVE_ADMIN,                    // 2
        VOTE_CHANGE_VOTING,                   // 3
        VOTE_ADD_BLOCKCHAIN,                  // 4
        VOTE_SET_SIGNING_THRESHOLD            // 5
    }


    struct Votes {
        // The type of vote being voted on.
        VoteType voteType;
        // Additional information about what is being voted on.
        uint256 additionalInfo;
        // The block number when voting will cease.
        uint endOfVotingBlockNumber;

        // Map of who has voted on the proposal.
        mapping(address=>bool) hasVoted;
        // The number of participants who voted for the proposal.
        uint64 numVotedFor;
        // The number of participants who voted against the proposal.
        uint64 numVotedAgainst;
    }

    struct BlockchainRecord {
        uint64 signingThreshold;
    }

    mapping(uint256=>BlockchainRecord) private blockchains;

    // The algorithm for assessing the votes.
    address private votingAlgorithmContract;
    // Voting period in blocks. This is the period in which participants can vote. Must be greater than 0.
    uint64 private votingPeriod;

    // Number of active administrators.
    uint64 private numAdmins;
    // Address of accounts who administer this contract and the offset in the array+1.
    mapping(address => uint256) private adminsMap;
    address[] private adminsArray;

    // Votes for adding and removing participants, for changing voting algorithm and voting period.
    mapping(uint256=>Votes) private votes;


    /**
     * Function modifier to ensure only admins can call the function.
     *
     * @dev Throws if the message sender isn't an admin.
     */
    modifier onlyAdmin() {
        require(adminsMap[msg.sender] != 0);
        _;
    }



    constructor() public {
        // Have msg.sender deploying this contract as an admin
        adminsArray.push(msg.sender);
        // The value is the offset into the array + 1.
        adminsMap[msg.sender] = 1;
        numAdmins = 1;
    }




    function proposeVote(uint16 _action, uint256 _voteTarget, uint256 _additionalInfo) external override(RegistrarInterface) onlyAdmin() {
        // This will throw an error if the action is not a valid VoteType.
        VoteType action = VoteType(_action);

        // Can't start a vote if a vote is already underway.
        require(votes[_voteTarget].voteType == VoteType.VOTE_NONE);

        address targetAddr = address(_voteTarget);

        if (action == VoteType.VOTE_ADD_ADMIN) {
            // If the action is to add an admin, then they shouldn't be an admin already.
            require(adminsMap[targetAddr] == 0);
        }
        else if (action == VoteType.VOTE_REMOVE_ADMIN) {
            // If the action is to remove an admin, then they should be an admin already.
            require(adminsMap[targetAddr] != 0);
            // Don't allow admins to propose removing themselves. This means the case of removing
            // the only admin is avoided.
            require(targetAddr != msg.sender);
        }
        //else if (action == VoteType.VOTE_CHANGE_VOTING) {
            // Nothing to check
        //}
        else if (action == VoteType.VOTE_ADD_BLOCKCHAIN) {
            // Ensure the blockchain does not yet exist
            require(blockchains[_voteTarget].signingThreshold == 0);
        }
        else if (action == VoteType.VOTE_SET_SIGNING_THRESHOLD) {
            // Ensure the blockchain exists
            require(blockchains[_voteTarget].signingThreshold != 0);
        }

        // Set-up the vote.
        votes[_voteTarget].voteType = action;
        votes[_voteTarget].endOfVotingBlockNumber = block.number + votingPeriod;
        votes[_voteTarget].additionalInfo = _additionalInfo;

        if (votingAlgorithmContract == address(0)) {
            // If there is no voting algorithm then all proposals are acted upon immediately.
            actionVotesNoChecks(_voteTarget, true);
        }
        else {
            // The proposer is deemed to be voting for the proposal.
            voteNoChecks(_action, _voteTarget, true);
        }
    }

    function vote(uint16 _action, uint256 _voteTarget, bool _voteFor) external override(RegistrarInterface) onlyAdmin() {
        // This will throw an error if the action is not a valid VoteType.
        VoteType action = VoteType(_action);

        // The type of vote must match what is currently being voted on.
        // Note that this will catch the case when someone is voting when there is no active vote.
        require(votes[_voteTarget].voteType == action);
        // Ensure the account has not voted yet.
        require(votes[_voteTarget].hasVoted[msg.sender] == false);

        // Check voting period has not expired.
        require(votes[_voteTarget].endOfVotingBlockNumber >= block.number);

        voteNoChecks(_action, _voteTarget, _voteFor);
    }

    function actionVotes(uint256 _voteTarget) external override(RegistrarInterface) onlyAdmin() {
        // If no vote is underway, then there is nothing to action.
        VoteType action = votes[_voteTarget].voteType;
        require(action != VoteType.VOTE_NONE);
        // Can only action vote after voting period has ended.
        require(votes[_voteTarget].endOfVotingBlockNumber < block.number);

        VotingAlgInterface voteAlg = VotingAlgInterface(votingAlgorithmContract);
        bool result = voteAlg.assess(
            numAdmins,
            votes[_voteTarget].numVotedFor,
            votes[_voteTarget].numVotedAgainst);
        emit VoteResult(uint16(action), _voteTarget, result);

        actionVotesNoChecks(_voteTarget, result);
    }


    /*
     * Return the implementation version.
     */
    function getVersion() external pure override(RegistrarInterface) returns (uint16) {
        return VERSION_ONE;
    }

    /************************************* PRIVATE FUNCTIONS BELOW HERE *************************************
    /************************************* PRIVATE FUNCTIONS BELOW HERE *************************************
    /************************************* PRIVATE FUNCTIONS BELOW HERE *************************************

    /**
     * This function is used to indicate that an admin has voted. It has been created so that
     * calls to proposeVote do not have to incur all of the value checking in the vote call.
     *
     */
    function voteNoChecks(uint16 _action, uint256 _voteTarget, bool _voteFor) private {
        // Indicate msg.sender has voted.
        emit Voted(msg.sender, _action, _voteTarget, _voteFor);
        votes[_voteTarget].hasVoted[msg.sender] = true;

        if (_voteFor) {
            votes[_voteTarget].numVotedFor++;
        } else {
            votes[_voteTarget].numVotedAgainst++;
        }
    }


    function actionVotesNoChecks(uint256 _voteTarget, bool _result) private {
        if (_result) {
            // The vote has been decided in the affirmative.
            VoteType action = votes[_voteTarget].voteType;
            uint256 additionalInfo = votes[_voteTarget].additionalInfo;
            address addr = address(_voteTarget);
            if (action == VoteType.VOTE_ADD_ADMIN) {
                adminsArray.push(addr);
                adminsMap[addr] = adminsArray.length;
                numAdmins++;
            }
            else if (action == VoteType.VOTE_REMOVE_ADMIN) {
                uint256 arrayOfsPlusOne = adminsMap[addr];
                delete adminsArray[arrayOfsPlusOne-1];
                delete adminsMap[addr];
                numAdmins--;
            }
            else if (action == VoteType.VOTE_CHANGE_VOTING) {
                votingAlgorithmContract = addr;
                votingPeriod = uint64(additionalInfo);
            }
            else if (action == VoteType.VOTE_ADD_BLOCKCHAIN) {
                blockchains[_voteTarget].signingThreshold = uint64(additionalInfo);
            }
            else if (action == VoteType.VOTE_SET_SIGNING_THRESHOLD) {
                blockchains[_voteTarget].signingThreshold = uint64(additionalInfo);
            }
        }


        // The vote is over. Now delete the voting arrays and indicate there is no vote underway.
        // Remove all values from the map: Maps can't be deleted in Solidity.
        // NOTE: The code below has used values directly, rather than a local variable due to running
        // out of local variables.
        for (uint i = 0; i < adminsArray.length; i++) {
            if (adminsArray[i] != address(0)) {
                delete votes[_voteTarget].hasVoted[adminsArray[i]];
            }
        }
        // This will recursively delete everything in the structure, except for the map, which was
        // deleted in the for loop above.
        delete votes[_voteTarget];
    }



    function addSignerPublicKeyAddress(uint256 _blockchainId, address _signerPublicKeyAddress) external override(RegistrarInterface) {

    }

    function removeSignerPublicKeyAddress(uint256 _blockchainId, address _signerPublicKeyAddress) external override(RegistrarInterface) {

    }



    function verify(
        uint256 _blockchainId,
        address[] calldata signers,
        bytes32[] calldata sigR,
        bytes32[] calldata sigS,
        uint8[] calldata sigV,
        bytes calldata plainText) external override(RegistrarInterface) {

    }



    function adminArraySize() external view override(RegistrarInterface) returns (uint256) {
        return adminsArray.length;
    }

    function getAdmin(uint256 _index) external view override(RegistrarInterface) returns (address)  {
        return adminsArray[_index];
    }

    function isAdmin(address _mightBeAdmin) external view override(RegistrarInterface) returns (bool)  {
        return adminsMap[_mightBeAdmin] != 0;
    }

    function getNumAdmins() external view override(RegistrarInterface) returns (uint64) {
        return numAdmins;
    }

}