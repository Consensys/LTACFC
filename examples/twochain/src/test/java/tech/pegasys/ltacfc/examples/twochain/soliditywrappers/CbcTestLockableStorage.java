package tech.pegasys.ltacfc.examples.twochain.soliditywrappers;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
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
public class CbcTestLockableStorage extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b506105db806100206000396000f3fe608060405234801561001057600080fd5b506004361061010b5760003560e01c8063653cf5a6116100a257806392b2c3351161007157806392b2c3351461043b5780639ad8a5b6146104be578063b4c3b756146104e8578063bae3cdc8146104f0578063df1bba011461050d5761010b565b8063653cf5a61461030157806366b79f5a146103935780637c5fc7f51461039b5780638e22d534146103b85761010b565b8063415ffba7116100de578063415ffba714610284578063439160df146102f95780634c97042e146101105780634e2edb76146102845761010b565b806308148f7a146101105780630df973611461013f5780632a615b3814610167578063336d5b091461027c575b600080fd5b61012d6004803603602081101561012657600080fd5b5035610549565b60408051918252519081900360200190f35b6101656004803603602081101561015557600080fd5b50356001600160a01b031661054f565b005b6101656004803603608081101561017d57600080fd5b81359190810190604081016020820135600160201b81111561019e57600080fd5b8201836020820111156101b057600080fd5b803590602001918460018302840111600160201b831117156101d157600080fd5b919390929091602081019035600160201b8111156101ee57600080fd5b82018360208201111561020057600080fd5b803590602001918460018302840111600160201b8311171561022157600080fd5b919390929091602081019035600160201b81111561023e57600080fd5b82018360208201111561025057600080fd5b803590602001918460208302840111600160201b8311171561027157600080fd5b509092509050610552565b61012d61055b565b6101656004803603604081101561029a57600080fd5b81359190810190604081016020820135600160201b8111156102bb57600080fd5b8201836020820111156102cd57600080fd5b803590602001918460018302840111600160201b831117156102ee57600080fd5b509092509050610561565b61012d610566565b61031e6004803603602081101561031757600080fd5b503561056b565b6040805160208082528351818301528351919283929083019185019080838360005b83811015610358578181015183820152602001610340565b50505050905090810190601f1680156103855780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b61012d61057e565b610165600480360360208110156103b157600080fd5b5035610584565b61012d600480360360608110156103ce57600080fd5b8135916001600160a01b0360208201351691810190606081016040820135600160201b8111156103fd57600080fd5b82018360208201111561040f57600080fd5b803590602001918460018302840111600160201b8311171561043057600080fd5b509092509050610589565b6101656004803603606081101561045157600080fd5b8135916001600160a01b0360208201351691810190606081016040820135600160201b81111561048057600080fd5b82018360208201111561049257600080fd5b803590602001918460018302840111600160201b831117156104b357600080fd5b509092509050610593565b6104d46004803603602081101561012657600080fd5b604080519115158252519081900360200190f35b6104d4610599565b6101656004803603602081101561050657600080fd5b50356105a0565b6101656004803603606081101561052357600080fd5b813591602081013591810190606081016040820135600160201b81111561048057600080fd5b50600090565b50565b50505050505050565b60005490565b505050565b600090565b5060408051602081019091526000815290565b60015490565b600155565b6000949350505050565b50505050565b6000541590565b60005556fea2646970667358221220b3771c4541226385aa6d18a27319424afb2fc9d3e649746b0fb2f916ca3c8feb64736f6c634300060b0033";

    public static final String FUNC_CALLGRAPHS = "callGraphs";

    public static final String FUNC_CLOSE = "close";

    public static final String FUNC_CROSSBLOCKCHAINCALL = "crossBlockchainCall";

    public static final String FUNC_CROSSBLOCKCHAINCALLRETURNSUINT256 = "crossBlockchainCallReturnsUint256";

    public static final String FUNC_CROSSBLOCKCHAINTRANSACTIONEXISTS = "crossBlockchainTransactionExists";

    public static final String FUNC_CROSSBLOCKCHAINTRANSACTIONTIMEOUT = "crossBlockchainTransactionTimeout";

    public static final String FUNC_GETACTIVECALLCROSSBLOCKCHAINTRANSACTIONID = "getActiveCallCrossBlockchainTransactionId";

    public static final String FUNC_GETACTIVECALLROOTBLOCKCHAINID = "getActiveCallRootBlockchainId";

    public static final String FUNC_ISSINGLEBLOCKCHAINCALL = "isSingleBlockchainCall";

    public static final String FUNC_LOCKCONTRACT = "lockContract";

    public static final String FUNC_MYBLOCKCHAINID = "myBlockchainId";

    public static final String FUNC_SEGMENT = "segment";

    public static final String FUNC_SETCROSSBLOCKCHAINTRANSACTIONID = "setCrossBlockchainTransactionId";

    public static final String FUNC_SETROOTBLOCKCHAINID = "setRootBlockchainId";

    public static final String FUNC_SIGNALLING = "signalling";

    public static final String FUNC_START = "start";

    public static final String FUNC_TIMEOUT = "timeout";

    @Deprecated
    protected CbcTestLockableStorage(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected CbcTestLockableStorage(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected CbcTestLockableStorage(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected CbcTestLockableStorage(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
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

    public String getRLP_close(BigInteger param0, byte[] param1) {
        final Function function = new Function(
                FUNC_CLOSE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0), 
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

    public RemoteFunctionCall<TransactionReceipt> lockContract(String param0) {
        final Function function = new Function(
                FUNC_LOCKCONTRACT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getRLP_lockContract(String param0) {
        final Function function = new Function(
                FUNC_LOCKCONTRACT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0)), 
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

    public RemoteFunctionCall<TransactionReceipt> segment(BigInteger param0, byte[] param1, byte[] param2, List<BigInteger> param3) {
        final Function function = new Function(
                FUNC_SEGMENT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0), 
                new org.web3j.abi.datatypes.DynamicBytes(param1), 
                new org.web3j.abi.datatypes.DynamicBytes(param2), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.datatypes.generated.Uint256.class,
                        org.web3j.abi.Utils.typeMap(param3, org.web3j.abi.datatypes.generated.Uint256.class))), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getRLP_segment(BigInteger param0, byte[] param1, byte[] param2, List<BigInteger> param3) {
        final Function function = new Function(
                FUNC_SEGMENT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0), 
                new org.web3j.abi.datatypes.DynamicBytes(param1), 
                new org.web3j.abi.datatypes.DynamicBytes(param2), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.datatypes.generated.Uint256.class,
                        org.web3j.abi.Utils.typeMap(param3, org.web3j.abi.datatypes.generated.Uint256.class))), 
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

    public String getRLP_signalling(BigInteger param0, byte[] param1) {
        final Function function = new Function(
                FUNC_SIGNALLING, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0), 
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
    public static CbcTestLockableStorage load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new CbcTestLockableStorage(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static CbcTestLockableStorage load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new CbcTestLockableStorage(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static CbcTestLockableStorage load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new CbcTestLockableStorage(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static CbcTestLockableStorage load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new CbcTestLockableStorage(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<CbcTestLockableStorage> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(CbcTestLockableStorage.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<CbcTestLockableStorage> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(CbcTestLockableStorage.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<CbcTestLockableStorage> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(CbcTestLockableStorage.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<CbcTestLockableStorage> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(CbcTestLockableStorage.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }
}
