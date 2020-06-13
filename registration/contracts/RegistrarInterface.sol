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
pragma solidity >=0.6.9;

interface RegistrarInterface {


    /**
     * Propose that a certain action be voted on.
     *
     * Proposals are actioned immediately if there is no voting algorithm at the contract level for contract
     * level actions, and for a blockchain for blockchain specific actions.
     *
     * Proposer must be an admin at the contract or for a blockchain to propose a vote.
     *
     * When an account proposes a vote, it automatically votes for the vote. That is, the proposer does
     * not need to separately call the vote function.
     *
     * Types of votes:
     *
     * Value  Action                                  Blockchain              Target                        Additional Information
     * 1      Add an admin                            Blockchain Id or 0x00   Address of admin to add       ignored
     *         Revert if the address is already an admin.
     * 2      Remove an admin                         Blockchain Id or 0x00   Address of admin to remove    ignored
     *         Revert if the address is not an admin.
     * 3      Change voting algorithm & voting period Blockchain Id or 0x00   Address of voting contract    Voting period
     *         Proposing a voting algorithm with address(0) removes the voting algorithm.
     *         The voting period must be greater than 1.
     * 4      Add a blockchain                        0x00                    Blockchain Id                 Signing algorithm used for blockchain.
     *         The blockchain must not exist yet. The signing algorithm must be valid.
     * 5     Change signing threshold                Blockchain Id            Signing threshold
     *         The signing threshold must be 1 or more.
     *
     * @param _action         The type of vote
     * @param _voteTarget     What is being voted on
     * @param _additionalInfo Additional information as per the table above.
     */
    function proposeVote(uint16 _action, uint256 _voteTarget, uint256 _additionalInfo) external;

    /**
     * Vote for a proposal.
     *
     * If an account has already voted, they can not vote again or change their vote.
     * Voters must be an admin at the contract or for a blockchain.
     *
     * @param _action The type of vote.
     * @param _voteTarget What is being voted on
     * @param _voteFor True if the transaction sender wishes to vote for the action.
     */
    function vote(uint16 _action, uint256 _voteTarget, bool _voteFor) external;

    /**
     * Action votes to affect the change.
     *
     * Only adminss at the contract level or for a blockchain can action votes.
     *
     * @param _voteTarget What is being voted on.
     */
    function actionVotes(uint256 _voteTarget) external;


    /**
     * Add a signer for the blockchain.
     */
    function addSignerPublicKeyAddress(uint256 _blockchainId, address _signerPublicKeyAddress) external;

    /**
     * Add a signer for the blockchain.
     */
    function removeSignerPublicKeyAddress(uint256 _blockchainId, address _signerPublicKeyAddress) external;


    /**
     * Verify signatures.
     */
    function verify(
        uint256 _blockchainId,
        address[] calldata signers,
        bytes32[] calldata sigR,
        bytes32[] calldata sigS,
        uint8[] calldata sigV,
        bytes calldata plainText) external;


    function adminArraySize() external view returns (uint256);

    function getAdmin(uint256 _index) external view returns (address);

    function isAdmin(address _mightBeAdmin) external view returns (bool);

    function getNumAdmins() external view returns (uint64);

    /*
     * Return the implementation version.
     */
    function getVersion() external pure returns (uint16);


    event Voted(address _participant, uint16 _action, uint256 _voteTarget, bool _votedFor);
    event VoteResult(uint16 _action, uint256 _voteTarget, bool _result);

}