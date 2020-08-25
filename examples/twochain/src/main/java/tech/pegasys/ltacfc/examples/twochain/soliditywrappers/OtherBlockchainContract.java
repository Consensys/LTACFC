package tech.pegasys.ltacfc.examples.twochain.soliditywrappers;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple2;
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
public class OtherBlockchainContract extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b506040516104433803806104438339818101604052602081101561003357600080fd5b5051600280546001600160a01b0319166001600160a01b039092169190911790556103e0806100636000396000f3fe608060405234801561001057600080fd5b50600436106100935760003560e01c80633d4197f0116100665780633d4197f0146101155780636da2c5d01461013257806384871ff4146101a2578063e1cb0e52146101c5578063f9633930146101cd57610093565b80630d7f9bde146100985780630ff4c916146100a2578063118307d7146100d15780633927f6af146100f6575b600080fd5b6100a06101e9565b005b6100bf600480360360208110156100b857600080fd5b50356101f4565b60408051918252519081900360200190f35b6100a0600480360360408110156100e757600080fd5b50803515159060200135610215565b6100a06004803603602081101561010c57600080fd5b50351515610224565b6100a06004803603602081101561012b57600080fd5b5035610293565b6100a06004803603602081101561014857600080fd5b81019060208101813564010000000081111561016357600080fd5b82018360208201111561017557600080fd5b8035906020019184602083028401116401000000008311171561019757600080fd5b509092509050610298565b6101aa6102a9565b60408051921515835260208301919091528051918290030190f35b6100bf6102bf565b6101d56102c6565b604080519115158252519081900360200190f35b600080546001019055565b60006001828154811061020357fe5b90600052602060002001549050919050565b61021e82610224565b60005550565b60025460408051634be76e3b60e11b8152600060048201819052841515602483015291516001600160a01b03909316926397cedc769260448084019391929182900301818387803b15801561027857600080fd5b505af115801561028c573d6000803e3d6000fd5b5050505050565b600055565b6102a460018383610345565b505050565b6000806102b46102c6565b600054915091509091565b6000545b90565b600254604080516331a1aa3360e11b8152600060048201819052915191926001600160a01b031691636343546691602480820192602092909190829003018186803b15801561031457600080fd5b505afa158015610328573d6000803e3d6000fd5b505050506040513d602081101561033e57600080fd5b5051905090565b828054828255906000526020600020908101928215610380579160200282015b82811115610380578235825591602001919060010190610365565b5061038c929150610390565b5090565b6102c391905b8082111561038c576000815560010161039656fea26469706673582212206c58f5b177a7f1ce951c31ed5f64fb2baebd886e77f4f79195340adb11973f5964736f6c634300060b0033";

    public static final String FUNC_GETFLAG = "getFlag";

    public static final String FUNC_GETVAL = "getVal";

    public static final String FUNC_GETVALANDFLAG = "getValAndFlag";

    public static final String FUNC_GETVALUE = "getValue";

    public static final String FUNC_INCREMENTVAL = "incrementVal";

    public static final String FUNC_SETFLAG = "setFlag";

    public static final String FUNC_SETVAL = "setVal";

    public static final String FUNC_SETVALANDFLAG = "setValAndFlag";

    public static final String FUNC_SETVALUES = "setValues";

    @Deprecated
    protected OtherBlockchainContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected OtherBlockchainContract(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected OtherBlockchainContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected OtherBlockchainContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<Boolean> getFlag() {
        final Function function = new Function(FUNC_GETFLAG, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public String getRLP_getFlag() {
        final Function function = new Function(
                FUNC_GETFLAG, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<BigInteger> getVal() {
        final Function function = new Function(FUNC_GETVAL, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public String getRLP_getVal() {
        final Function function = new Function(
                FUNC_GETVAL, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<Tuple2<Boolean, BigInteger>> getValAndFlag() {
        final Function function = new Function(FUNC_GETVALANDFLAG, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple2<Boolean, BigInteger>>(function,
                new Callable<Tuple2<Boolean, BigInteger>>() {
                    @Override
                    public Tuple2<Boolean, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple2<Boolean, BigInteger>(
                                (Boolean) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue());
                    }
                });
    }

    public String getRLP_getValAndFlag() {
        final Function function = new Function(
                FUNC_GETVALANDFLAG, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<BigInteger> getValue(BigInteger _index) {
        final Function function = new Function(FUNC_GETVALUE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_index)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public String getRLP_getValue(BigInteger _index) {
        final Function function = new Function(
                FUNC_GETVALUE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_index)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<TransactionReceipt> incrementVal() {
        final Function function = new Function(
                FUNC_INCREMENTVAL, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getRLP_incrementVal() {
        final Function function = new Function(
                FUNC_INCREMENTVAL, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setFlag(Boolean _flag) {
        final Function function = new Function(
                FUNC_SETFLAG, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Bool(_flag)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getRLP_setFlag(Boolean _flag) {
        final Function function = new Function(
                FUNC_SETFLAG, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Bool(_flag)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setVal(BigInteger _val) {
        final Function function = new Function(
                FUNC_SETVAL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_val)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getRLP_setVal(BigInteger _val) {
        final Function function = new Function(
                FUNC_SETVAL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_val)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setValAndFlag(Boolean _flag, BigInteger _val) {
        final Function function = new Function(
                FUNC_SETVALANDFLAG, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Bool(_flag), 
                new org.web3j.abi.datatypes.generated.Uint256(_val)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getRLP_setValAndFlag(Boolean _flag, BigInteger _val) {
        final Function function = new Function(
                FUNC_SETVALANDFLAG, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Bool(_flag), 
                new org.web3j.abi.datatypes.generated.Uint256(_val)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setValues(List<BigInteger> _vals) {
        final Function function = new Function(
                FUNC_SETVALUES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.datatypes.generated.Uint256.class,
                        org.web3j.abi.Utils.typeMap(_vals, org.web3j.abi.datatypes.generated.Uint256.class))), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getRLP_setValues(List<BigInteger> _vals) {
        final Function function = new Function(
                FUNC_SETVALUES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.datatypes.generated.Uint256.class,
                        org.web3j.abi.Utils.typeMap(_vals, org.web3j.abi.datatypes.generated.Uint256.class))), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    @Deprecated
    public static OtherBlockchainContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new OtherBlockchainContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static OtherBlockchainContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new OtherBlockchainContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static OtherBlockchainContract load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new OtherBlockchainContract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static OtherBlockchainContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new OtherBlockchainContract(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<OtherBlockchainContract> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _storageContract) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _storageContract)));
        return deployRemoteCall(OtherBlockchainContract.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<OtherBlockchainContract> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _storageContract) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _storageContract)));
        return deployRemoteCall(OtherBlockchainContract.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<OtherBlockchainContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _storageContract) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _storageContract)));
        return deployRemoteCall(OtherBlockchainContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<OtherBlockchainContract> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _storageContract) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _storageContract)));
        return deployRemoteCall(OtherBlockchainContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }
}
