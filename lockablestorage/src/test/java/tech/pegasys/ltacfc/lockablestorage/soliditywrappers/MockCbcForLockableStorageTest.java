package tech.pegasys.ltacfc.lockablestorage.soliditywrappers;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.DynamicStruct;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.7.0-SNAPSHOT.
 */
@SuppressWarnings("rawtypes")
public class MockCbcForLockableStorageTest extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b50610681806100206000396000f3fe608060405234801561001057600080fd5b50600436106101165760003560e01c806366b79f5a116100a25780639ad8a5b6116100715780639ad8a5b6146101f0578063b4c3b7561461020b578063bae3cdc814610213578063df1bba0114610226578063f3a90df91461023457610116565b806366b79f5a146101af5780637c5fc7f5146101b75780638e22d534146101ca57806392b2c335146101dd57610116565b80633ab56127116100e95780633ab56127146101445780633cdc710414610174578063439160df146101875780634c97042e1461011b578063653cf5a61461018f57610116565b806308148f7a1461011b5780631a26720c14610144578063336d5b091461015957806339ce107e14610161575b600080fd5b61012e61012936600461044f565b610242565b60405161013b9190610642565b60405180910390f35b610157610152366004610381565b610248565b005b61012e61024d565b61015761016f36600461035f565b610253565b610157610182366004610467565b610256565b61012e610263565b6101a261019d36600461044f565b610268565b60405161013b91906105ef565b61012e61027b565b6101576101c536600461044f565b610281565b61012e6101d8366004610552565b610286565b6101576101eb366004610552565b610290565b6101fe61012936600461044f565b60405161013b91906105e4565b6101fe610296565b61015761022136600461044f565b61029d565b6101576101eb3660046105ab565b6101576101523660046103cb565b50600090565b505050565b60005490565b50565b5050505050505050505050565b600090565b5060408051602081019091526000815290565b60015490565b600155565b6000949350505050565b50505050565b6000541590565b600055565b80356001600160a01b03811681146102b957600080fd5b92915050565b60008083601f8401126102d0578182fd5b50813567ffffffffffffffff8111156102e7578182fd5b602083019150836020808302850101111561030157600080fd5b9250929050565b60008083601f840112610319578182fd5b50813567ffffffffffffffff811115610330578182fd5b60208301915083602082850101111561030157600080fd5b600060c08284031215610359578081fd5b50919050565b600060208284031215610370578081fd5b61037a83836102a2565b9392505050565b600080600060408486031215610395578182fd5b83359250602084013567ffffffffffffffff8111156103b2578283fd5b6103be86828701610308565b9497909650939450505050565b6000806000606084860312156103df578283fd5b833567ffffffffffffffff808211156103f6578485fd5b61040287838801610348565b94506020860135915080821115610417578384fd5b61042387838801610348565b93506040860135915080821115610438578283fd5b5061044586828701610348565b9150509250925092565b600060208284031215610460578081fd5b5035919050565b600080600080600080600080600080600060e08c8e031215610487578687fd5b8b359a506104988d60208e016102a2565b995060408c0135985067ffffffffffffffff8060608e013511156104ba578788fd5b6104ca8e60608f01358f01610308565b909950975060808d01358110156104df578687fd5b6104ef8e60808f01358f016102bf565b909750955060a08d0135811015610504578485fd5b6105148e60a08f01358f016102bf565b909550935060c08d0135811015610529578283fd5b5061053a8d60c08e01358e016102bf565b81935080925050509295989b509295989b9093969950565b60008060008060608587031215610567578384fd5b8435935061057886602087016102a2565b9250604085013567ffffffffffffffff811115610593578283fd5b61059f87828801610308565b95989497509550505050565b600080600080606085870312156105c0578384fd5b8435935060208501359250604085013567ffffffffffffffff811115610593578283fd5b901515815260200190565b6000602080835283518082850152825b8181101561061b578581018301518582016040015282016105ff565b8181111561062c5783604083870101525b50601f01601f1916929092016040019392505050565b9081526020019056fea2646970667358221220386d10ca8a88a4e805c05cd8826103361364b81229de112f70583af5408464d364736f6c63430007010033";

    public static final String FUNC_ADDTOLISTOFLOCKEDCONTRACTS = "addToListOfLockedContracts";

    public static final String FUNC_CALLGRAPHS = "callGraphs";

    public static final String FUNC_CLOSE = "close";

    public static final String FUNC_CROSSBLOCKCHAINCALL = "crossBlockchainCall";

    public static final String FUNC_CROSSBLOCKCHAINCALLRETURNSUINT256 = "crossBlockchainCallReturnsUint256";

    public static final String FUNC_CROSSBLOCKCHAINTRANSACTIONEXISTS = "crossBlockchainTransactionExists";

    public static final String FUNC_CROSSBLOCKCHAINTRANSACTIONTIMEOUT = "crossBlockchainTransactionTimeout";

    public static final String FUNC_GETACTIVECALLCROSSBLOCKCHAINTRANSACTIONID = "getActiveCallCrossBlockchainTransactionId";

    public static final String FUNC_GETACTIVECALLROOTBLOCKCHAINID = "getActiveCallRootBlockchainId";

    public static final String FUNC_ISSINGLEBLOCKCHAINCALL = "isSingleBlockchainCall";

    public static final String FUNC_MYBLOCKCHAINID = "myBlockchainId";

    public static final String FUNC_ROOT = "root";

    public static final String FUNC_SEGMENT = "segment";

    public static final String FUNC_SETCROSSBLOCKCHAINTRANSACTIONID = "setCrossBlockchainTransactionId";

    public static final String FUNC_SETROOTBLOCKCHAINID = "setRootBlockchainId";

    public static final String FUNC_SIGNALLING = "signalling";

    public static final String FUNC_START = "start";

    public static final String FUNC_TIMEOUT = "timeout";

    @Deprecated
    protected MockCbcForLockableStorageTest(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected MockCbcForLockableStorageTest(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected MockCbcForLockableStorageTest(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected MockCbcForLockableStorageTest(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<TransactionReceipt> addToListOfLockedContracts(String param0) {
        final Function function = new Function(
                FUNC_ADDTOLISTOFLOCKEDCONTRACTS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getRLP_addToListOfLockedContracts(String param0) {
        final Function function = new Function(
                FUNC_ADDTOLISTOFLOCKEDCONTRACTS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<byte[]> callGraphs(BigInteger param0) {
        final Function function = new Function(FUNC_CALLGRAPHS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicBytes>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public String getRLP_callGraphs(BigInteger param0) {
        final Function function = new Function(
                FUNC_CALLGRAPHS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public String getRLP_close(byte[] param0, byte[] param1) {
        final Function function = new Function(
                FUNC_CLOSE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(param0), 
                new org.web3j.abi.datatypes.DynamicBytes(param1)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<TransactionReceipt> crossBlockchainCall(BigInteger param0, String param1, byte[] param2) {
        final Function function = new Function(
                FUNC_CROSSBLOCKCHAINCALL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0), 
                new org.web3j.abi.datatypes.Address(160, param1), 
                new org.web3j.abi.datatypes.DynamicBytes(param2)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getRLP_crossBlockchainCall(BigInteger param0, String param1, byte[] param2) {
        final Function function = new Function(
                FUNC_CROSSBLOCKCHAINCALL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0), 
                new org.web3j.abi.datatypes.Address(160, param1), 
                new org.web3j.abi.datatypes.DynamicBytes(param2)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<BigInteger> crossBlockchainCallReturnsUint256(BigInteger param0, String param1, byte[] param2) {
        final Function function = new Function(FUNC_CROSSBLOCKCHAINCALLRETURNSUINT256, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0), 
                new org.web3j.abi.datatypes.Address(160, param1), 
                new org.web3j.abi.datatypes.DynamicBytes(param2)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public String getRLP_crossBlockchainCallReturnsUint256(BigInteger param0, String param1, byte[] param2) {
        final Function function = new Function(
                FUNC_CROSSBLOCKCHAINCALLRETURNSUINT256, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0), 
                new org.web3j.abi.datatypes.Address(160, param1), 
                new org.web3j.abi.datatypes.DynamicBytes(param2)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<Boolean> crossBlockchainTransactionExists(BigInteger param0) {
        final Function function = new Function(FUNC_CROSSBLOCKCHAINTRANSACTIONEXISTS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public String getRLP_crossBlockchainTransactionExists(BigInteger param0) {
        final Function function = new Function(
                FUNC_CROSSBLOCKCHAINTRANSACTIONEXISTS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<BigInteger> crossBlockchainTransactionTimeout(BigInteger param0) {
        final Function function = new Function(FUNC_CROSSBLOCKCHAINTRANSACTIONTIMEOUT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public String getRLP_crossBlockchainTransactionTimeout(BigInteger param0) {
        final Function function = new Function(
                FUNC_CROSSBLOCKCHAINTRANSACTIONTIMEOUT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<BigInteger> getActiveCallCrossBlockchainTransactionId() {
        final Function function = new Function(FUNC_GETACTIVECALLCROSSBLOCKCHAINTRANSACTIONID, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public String getRLP_getActiveCallCrossBlockchainTransactionId() {
        final Function function = new Function(
                FUNC_GETACTIVECALLCROSSBLOCKCHAINTRANSACTIONID, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<BigInteger> getActiveCallRootBlockchainId() {
        final Function function = new Function(FUNC_GETACTIVECALLROOTBLOCKCHAINID, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public String getRLP_getActiveCallRootBlockchainId() {
        final Function function = new Function(
                FUNC_GETACTIVECALLROOTBLOCKCHAINID, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<Boolean> isSingleBlockchainCall() {
        final Function function = new Function(FUNC_ISSINGLEBLOCKCHAINCALL, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public String getRLP_isSingleBlockchainCall() {
        final Function function = new Function(
                FUNC_ISSINGLEBLOCKCHAINCALL, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<BigInteger> myBlockchainId() {
        final Function function = new Function(FUNC_MYBLOCKCHAINID, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public String getRLP_myBlockchainId() {
        final Function function = new Function(
                FUNC_MYBLOCKCHAINID, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<TransactionReceipt> root(Info param0, Info param1, Info param2) {
        final Function function = new Function(
                FUNC_ROOT, 
                Arrays.<Type>asList(param0, 
                param1, 
                param2), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getRLP_root(Info param0, Info param1, Info param2) {
        final Function function = new Function(
                FUNC_ROOT, 
                Arrays.<Type>asList(param0, 
                param1, 
                param2), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<TransactionReceipt> segment(BigInteger param0, String param1, byte[] param2, byte[] param3, List<BigInteger> param4, List<byte[]> param5, List<BigInteger> param6) {
        final Function function = new Function(
                FUNC_SEGMENT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0), 
                new org.web3j.abi.datatypes.Address(160, param1), 
                new org.web3j.abi.datatypes.generated.Bytes32(param2), 
                new org.web3j.abi.datatypes.DynamicBytes(param3), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.datatypes.generated.Uint256.class,
                        org.web3j.abi.Utils.typeMap(param4, org.web3j.abi.datatypes.generated.Uint256.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.DynamicBytes>(
                        org.web3j.abi.datatypes.DynamicBytes.class,
                        org.web3j.abi.Utils.typeMap(param5, org.web3j.abi.datatypes.DynamicBytes.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.datatypes.generated.Uint256.class,
                        org.web3j.abi.Utils.typeMap(param6, org.web3j.abi.datatypes.generated.Uint256.class))), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getRLP_segment(BigInteger param0, String param1, byte[] param2, byte[] param3, List<BigInteger> param4, List<byte[]> param5, List<BigInteger> param6) {
        final Function function = new Function(
                FUNC_SEGMENT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0), 
                new org.web3j.abi.datatypes.Address(160, param1), 
                new org.web3j.abi.datatypes.generated.Bytes32(param2), 
                new org.web3j.abi.datatypes.DynamicBytes(param3), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.datatypes.generated.Uint256.class,
                        org.web3j.abi.Utils.typeMap(param4, org.web3j.abi.datatypes.generated.Uint256.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.DynamicBytes>(
                        org.web3j.abi.datatypes.DynamicBytes.class,
                        org.web3j.abi.Utils.typeMap(param5, org.web3j.abi.datatypes.DynamicBytes.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.datatypes.generated.Uint256.class,
                        org.web3j.abi.Utils.typeMap(param6, org.web3j.abi.datatypes.generated.Uint256.class))), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setCrossBlockchainTransactionId(BigInteger _txId) {
        final Function function = new Function(
                FUNC_SETCROSSBLOCKCHAINTRANSACTIONID, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_txId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getRLP_setCrossBlockchainTransactionId(BigInteger _txId) {
        final Function function = new Function(
                FUNC_SETCROSSBLOCKCHAINTRANSACTIONID, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_txId)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setRootBlockchainId(BigInteger _rootBcId) {
        final Function function = new Function(
                FUNC_SETROOTBLOCKCHAINID, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_rootBcId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getRLP_setRootBlockchainId(BigInteger _rootBcId) {
        final Function function = new Function(
                FUNC_SETROOTBLOCKCHAINID, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_rootBcId)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public String getRLP_signalling(byte[] param0, byte[] param1) {
        final Function function = new Function(
                FUNC_SIGNALLING, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(param0), 
                new org.web3j.abi.datatypes.DynamicBytes(param1)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<TransactionReceipt> start(BigInteger param0, BigInteger param1, byte[] param2) {
        final Function function = new Function(
                FUNC_START, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0), 
                new org.web3j.abi.datatypes.generated.Uint256(param1), 
                new org.web3j.abi.datatypes.DynamicBytes(param2)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getRLP_start(BigInteger param0, BigInteger param1, byte[] param2) {
        final Function function = new Function(
                FUNC_START, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0), 
                new org.web3j.abi.datatypes.generated.Uint256(param1), 
                new org.web3j.abi.datatypes.DynamicBytes(param2)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<BigInteger> timeout(BigInteger param0) {
        final Function function = new Function(FUNC_TIMEOUT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public String getRLP_timeout(BigInteger param0) {
        final Function function = new Function(
                FUNC_TIMEOUT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    @Deprecated
    public static MockCbcForLockableStorageTest load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new MockCbcForLockableStorageTest(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static MockCbcForLockableStorageTest load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new MockCbcForLockableStorageTest(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static MockCbcForLockableStorageTest load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new MockCbcForLockableStorageTest(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static MockCbcForLockableStorageTest load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new MockCbcForLockableStorageTest(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<MockCbcForLockableStorageTest> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(MockCbcForLockableStorageTest.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<MockCbcForLockableStorageTest> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(MockCbcForLockableStorageTest.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<MockCbcForLockableStorageTest> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(MockCbcForLockableStorageTest.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<MockCbcForLockableStorageTest> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(MockCbcForLockableStorageTest.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class Info extends DynamicStruct {
        public BigInteger blockchainId;

        public String cBCContract;

        public byte[] txReceiptRoot;

        public byte[] txReceipt;

        public List<BigInteger> proofOffset;

        public List<byte[]> proof;

        public Info(BigInteger blockchainId, String cBCContract, byte[] txReceiptRoot, byte[] txReceipt, List<BigInteger> proofOffset, List<byte[]> proof) {
            super(new org.web3j.abi.datatypes.generated.Uint256(blockchainId),new org.web3j.abi.datatypes.Address(cBCContract),new org.web3j.abi.datatypes.generated.Bytes32(txReceiptRoot),new org.web3j.abi.datatypes.DynamicBytes(txReceipt),new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(org.web3j.abi.datatypes.generated.Uint256.class, convertTo_uint256(proofOffset)),new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.DynamicBytes>(org.web3j.abi.datatypes.DynamicBytes.class, convertTo_bytes(proof)));
            this.blockchainId = blockchainId;
            this.cBCContract = cBCContract;
            this.txReceiptRoot = txReceiptRoot;
            this.txReceipt = txReceipt;
            this.proofOffset = proofOffset;
            this.proof = proof;
        }

        public Info(Uint256 blockchainId, Address cBCContract, Bytes32 txReceiptRoot, DynamicBytes txReceipt, DynamicArray<Uint256> proofOffset, DynamicArray<DynamicBytes> proof) {
            super(blockchainId,cBCContract,txReceiptRoot,txReceipt,proofOffset,proof);
            this.blockchainId = blockchainId.getValue();
            this.cBCContract = cBCContract.getValue();
            this.txReceiptRoot = txReceiptRoot.getValue();
            this.txReceipt = txReceipt.getValue();
            this.proofOffset = new java.util.ArrayList<>();;
            for (org.web3j.abi.datatypes.generated.Uint256 z: proofOffset.getValue()) {;
            this.proofOffset.add(z.getValue());;
            };
            this.proof = new java.util.ArrayList<>();;
            for (org.web3j.abi.datatypes.DynamicBytes z: proof.getValue()) {;
            this.proof.add(z.getValue());;
            };
        }

        static List convertTo_uint256(List<BigInteger> param) {
            List<Uint256> z = new java.util.ArrayList<>();
            for (BigInteger a: param) {;
               z.add(new Uint256(a));
            };
            return z;
        }

        static List convertTo_bytes(List<byte[]> param) {
            List<org.web3j.abi.datatypes.DynamicBytes> z = new java.util.ArrayList<>();
            for (byte[] a: param) {;
               z.add(new org.web3j.abi.datatypes.DynamicBytes(a));
            };
            return z;
        }
    }
}
