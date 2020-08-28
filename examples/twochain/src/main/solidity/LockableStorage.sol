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

import "../../../../../crossblockchaincontrol/src/main/solidity/CrossBlockchainControl.sol";


/**
 * Lockable storage contract.
 *
 * Write Algorithm: Used to write a value to the contract.
 *
 *  If caller is not Business Logic Contract {
 *    throw an error
 *  }
 *  Check Cross-Blockchain Control Contract: is there an active cross-blockchain call involving this contract?
 *  If not (normal single blockchain call) {
 *    If locked {throw an error}
 *    Else (not locked) {Write to normal storage}
 *  }
 *  Else (this is a cross-blockchain call) {
 *    If locked {
 *      Check Cross-Blockchain Control Contract: has this cross-blockchain call previously
 *      locked the contract?
 *      If no {throw an error}
 *      If yes {Write to provisional storage}
 *    }
 *    Else (not locked) {
 *      Lock the contract.
 *      Indicate in the Cross-Blockchain Control Contract that this call is locking the
 *       Lockable Storage contract
 *      Write to provisional storage
 *    }
 * }
 *
 * Read Algorithm: Used to read a value from the contract.
 *
 * If caller is not Business Logic Contract {
 *   throw an error
 * }
 * Check Cross-Blockchain Control Contract: is there an active cross-blockchain call?
 * If not (normal single blockchain call) {
 *   If locked {throw an error}
 *   Else (not locked) {Read from normal storage}
 * Else (this is a cross-blockchain call) { If locked {
 * Check Cross-Blockchain Control Contract: has this cross-blockchain call previously locked the contract?
 * If no {throw an error}
 * If yes {Allow the read. If the value isn’t available in provisional storage, return
 *  the value in normal storage.} }
 * Else (not locked) {Read from normal storage}
 */
contract LockableStorage {

    // TODO be able to upgrade the business logic contract
    // Address of the contract that this contract is supplying storage for.
    address private businessLogicContract;
    // The Cross-Blockchain Control Contract used for cross-blockchain transaction locking.
    CrossBlockchainControl private crossBlockchainControl;

    // True when this contract is locked.
    bool public locked;
    // Which root blockchain / transaction id locked this contract.
    uint256 lockedByRootBlockchainId;
    uint256 lockedByTransactionId;

    struct AllTypes {
        bool boolVal;
        uint256 uint256Val;
        bytes bytesVal;
    }
    mapping(uint256=>AllTypes) private dataStore;
    mapping(uint256=>AllTypes) private provisionalUpdates;
    uint256[] private provisionalUpdatesList;
    mapping(uint256=>bool) private provisionalUpdateExists;


    /**
     * Revert if the caller is not Business Logic Contract.
     */
    modifier onlyBusinessLogicContract() {
        require(msg.sender == businessLogicContract, "Only call from business logic contract");
        _;
    }

    constructor (address _crossBlockchainControl, address _businessLogicContract) public {
        crossBlockchainControl = CrossBlockchainControl(_crossBlockchainControl);
        businessLogicContract = _businessLogicContract;
    }



    /**
     * Set a boolean value using the write algorithm.
     *
     * @param _key Key that the value is being stored with.
     * @param _val The boolean value to store.
     */
    function setBool(uint256 _key, bool _val) external onlyBusinessLogicContract {
        // Check Cross-Blockchain Control Contract: is there an active cross-blockchain call involving this contract?
        // If not (normal single blockchain call) {
        //   If locked {throw an error}
        //   Else (not locked) {Write to normal storage}
        // }
        if (crossBlockchainControl.isSingleBlockchainCall()) {
            require(!locked, "Attempted single blockchain call when contract locked");
            dataStore[_key].boolVal = _val;
        }
        // Else (this is a cross-blockchain call) {
        else {
            //   If locked {
            //     Check Cross-Blockchain Control Contract: has this cross-blockchain call previously
            //     locked the contract?
            //     If no {throw an error}
            //     If yes {Write to provisional storage}
            if (locked) {
                checkLockingAndWrite(_key);
                provisionalUpdates[_key].boolVal = _val;
            }
            //   Else (not locked) {
            //     Lock the contract.
            //     Indicate in the Cross-Blockchain Control Contract that this call is locking the
            //      Lockable Storage contract
            //     Write to provisional storage
            else {
                lockContract(_key);
                provisionalUpdates[_key].boolVal = _val;
            }
        }
    }


    /**
     * Set a uint256 value using the write algorithm.
     *
     * @param _key Key that the value is being stored with.
     * @param _val The value to store.
     */
    function setUint256(uint256 _key, uint256 _val) external onlyBusinessLogicContract {
        // Check Cross-Blockchain Control Contract: is there an active cross-blockchain call involving this contract?
        // If not (normal single blockchain call) {
        //   If locked {throw an error}
        //   Else (not locked) {Write to normal storage}
        // }
        if (crossBlockchainControl.isSingleBlockchainCall()) {
            require(!locked, "Attempted single blockchain call when contract locked");
            dataStore[_key].uint256Val = _val;
        }
        // Else (this is a cross-blockchain call) {
        else {
            //   If locked {
            //     Check Cross-Blockchain Control Contract: has this cross-blockchain call previously
            //     locked the contract?
            //     If no {throw an error}
            //     If yes {Write to provisional storage}
            if (locked) {
                checkLockingAndWrite(_key);
                provisionalUpdates[_key].uint256Val = _val;
            }
            //   Else (not locked) {
            //     Lock the contract.
            //     Indicate in the Cross-Blockchain Control Contract that this call is locking the
            //      Lockable Storage contract
            //     Write to provisional storage
            else {
                lockContract(_key);
                provisionalUpdates[_key].uint256Val = _val;
            }
        }
    }



    /**
     * Set a uint256 value using the write algorithm.
     *
     * @param _key Key that the value is being stored with.
     * @param _val The value to store.
     */
    function setBytes(uint256 _key, bytes calldata _val) external onlyBusinessLogicContract {
        // Check Cross-Blockchain Control Contract: is there an active cross-blockchain call involving this contract?
        // If not (normal single blockchain call) {
        //   If locked {throw an error}
        //   Else (not locked) {Write to normal storage}
        // }
        if (crossBlockchainControl.isSingleBlockchainCall()) {
            require(!locked, "Attempted single blockchain call when contract locked");
            dataStore[_key].bytesVal = _val;
        }
        // Else (this is a cross-blockchain call) {
        else {
            //   If locked {
            //     Check Cross-Blockchain Control Contract: has this cross-blockchain call previously
            //     locked the contract?
            //     If no {throw an error}
            //     If yes {Write to provisional storage}
            if (locked) {
                checkLockingAndWrite(_key);
                provisionalUpdates[_key].bytesVal = _val;
            }
            //   Else (not locked) {
            //     Lock the contract.
            //     Indicate in the Cross-Blockchain Control Contract that this call is locking the
            //      Lockable Storage contract
            //     Write to provisional storage
            else {
                lockContract(_key);
                provisionalUpdates[_key].bytesVal = _val;
            }
        }
    }


    function getBool(uint256 _key) external view onlyBusinessLogicContract returns(bool) {
        // Check Cross-Blockchain Control Contract: is there an active cross-blockchain call involving this contract?
        // If not (normal single blockchain call) {
        //   If locked {throw an error}
        //   Else (not locked) {Read from to normal storage}
        // }
        if (crossBlockchainControl.isSingleBlockchainCall()) {
            require(!locked, "Attempted single blockchain call when contract locked");
            return dataStore[_key].boolVal;
        }
        // Else (this is a cross-blockchain call) {
        else {
            //   If locked {
            //     Check Cross-Blockchain Control Contract: has this cross-blockchain call previously
            //     locked the contract?
            //     If no {throw an error}
            //     If yes {Allow the read. If the value isn’t available in provisional storage, return
            //       the value in normal storage.} }
            if (locked) {
                checkPreviousCrossCall();
                if (provisionalUpdateExists[_key]) {
                    return provisionalUpdates[_key].boolVal;
                }
                else {
                    return dataStore[_key].boolVal;
                }
            }
            // Else (not locked) {Read from normal storage}
            else {
                return dataStore[_key].boolVal;
            }
        }
    }


    function getUint256(uint256 _key) external view onlyBusinessLogicContract returns(uint256) {
        // Check Cross-Blockchain Control Contract: is there an active cross-blockchain call involving this contract?
        // If not (normal single blockchain call) {
        //   If locked {throw an error}
        //   Else (not locked) {Read from to normal storage}
        // }
        if (crossBlockchainControl.isSingleBlockchainCall()) {
            require(!locked, "Attempted single blockchain call when contract locked");
            return dataStore[_key].uint256Val;
        }
        // Else (this is a cross-blockchain call) {
        else {
            //   If locked {
            //     Check Cross-Blockchain Control Contract: has this cross-blockchain call previously
            //     locked the contract?
            //     If no {throw an error}
            //     If yes {Allow the read. If the value isn’t available in provisional storage, return
            //       the value in normal storage.} }
            if (locked) {
                checkPreviousCrossCall();
                if (provisionalUpdateExists[_key]) {
                    return provisionalUpdates[_key].uint256Val;
                }
                else {
                    return dataStore[_key].uint256Val;
                }
            }
            // Else (not locked) {Read from normal storage}
            else {
                return dataStore[_key].uint256Val;
            }
        }
    }


    function getBytes(uint256 _key) external view onlyBusinessLogicContract returns(bytes memory) {
        // Check Cross-Blockchain Control Contract: is there an active cross-blockchain call involving this contract?
        // If not (normal single blockchain call) {
        //   If locked {throw an error}
        //   Else (not locked) {Read from to normal storage}
        // }
        if (crossBlockchainControl.isSingleBlockchainCall()) {
            require(!locked, "Attempted single blockchain call when contract locked");
            return dataStore[_key].bytesVal;
        }
        // Else (this is a cross-blockchain call) {
        else {
            //   If locked {
            //     Check Cross-Blockchain Control Contract: has this cross-blockchain call previously
            //     locked the contract?
            //     If no {throw an error}
            //     If yes {Allow the read. If the value isn’t available in provisional storage, return
            //       the value in normal storage.} }
            if (locked) {
                checkPreviousCrossCall();
                if (provisionalUpdateExists[_key]) {
                    return provisionalUpdates[_key].bytesVal;
                }
                else {
                    return dataStore[_key].bytesVal;
                }
            }
            // Else (not locked) {Read from normal storage}
            else {
                return dataStore[_key].bytesVal;
            }
        }
    }


    /**
     * Do the type-independant things that need to be done to check that a locked contract
     * is being written to by the same cross-blockchain call, and then do the write.
     *
     * Check Cross-Blockchain Control Contract: has this cross-blockchain call previously
     *   locked the contract?
     * If no {throw an error}
     * If yes {Write to provisional storage}
     *
     * @param _key The key for the value that is currently being written.
     */
    function checkLockingAndWrite(uint256 _key) private {
        require(lockedByRootBlockchainId == crossBlockchainControl.getActiveCallRootBlockchainId(), "Contract locked by other root blockchain");
        require(lockedByTransactionId == crossBlockchainControl.getActiveCallCrossBlockchainTransactionId(), "Contract locked by other cross-blockchain transaction");
        provisionalUpdatesList.push(_key);
        provisionalUpdateExists[_key]= true;
    }

    /**
     * Do the type-independant things that need to be done to lock a contract.
     *
     * Lock the contract.
     * Indicate in the Cross-Blockchain Control Contract that this call is locking the
     *   Lockable Storage contract
     *  Generic parts of "write to provisional storage"
     *
     * @param _keyCausingLock The key for the value that is currently being written.
     */
    function lockContract(uint256 _keyCausingLock) private {
        locked = true;
        lockedByRootBlockchainId = crossBlockchainControl.getActiveCallRootBlockchainId();
        lockedByTransactionId = crossBlockchainControl.getActiveCallCrossBlockchainTransactionId();
        crossBlockchainControl.lockContract(address(this));
        provisionalUpdatesList.push(_keyCausingLock);
        provisionalUpdateExists[_keyCausingLock]= true;
    }

    function checkPreviousCrossCall() private view {
        require(lockedByRootBlockchainId == crossBlockchainControl.getActiveCallRootBlockchainId(), "Contract locked by other root blockchain");
        require(lockedByTransactionId == crossBlockchainControl.getActiveCallCrossBlockchainTransactionId(), "Contract locked by other cross-blockchain transaction");
    }
}
