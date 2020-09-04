package tech.pegasys.ltacfc.lockablestorage.soliditywrappers;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
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
public class LockableStorage extends Contract {
    public static final String BINARY = "60a060405234801561001057600080fd5b506040516110163803806110168339818101604052602081101561003357600080fd5b505133606081901b608052600180546001600160a01b0319166001600160a01b0390931692909217909155610fa1610075600039806108b85250610fa16000f3fe608060405234801561001057600080fd5b50600436106100935760003560e01c806357bc2ef31161006657806357bc2ef31461016c5780635bf9755e146101fe5780639c235a6814610221578063cf30901214610247578063eda1824d1461026357610093565b8063011827fd1461009857806301b6a88014610111578063441abbac146101355780635384d8bd14610164575b600080fd5b61010f600480360360408110156100ae57600080fd5b813591908101906040810160208201356401000000008111156100d057600080fd5b8201836020820111156100e257600080fd5b8035906020019184600183028401116401000000008311171561010457600080fd5b50909250905061026b565b005b610119610401565b604080516001600160a01b039092168252519081900360200190f35b6101526004803603602081101561014b57600080fd5b5035610410565b60408051918252519081900360200190f35b610152610531565b6101896004803603602081101561018257600080fd5b5035610537565b6040805160208082528351818301528351919283929083019185019080838360005b838110156101c35781810151838201526020016101ab565b50505050905090810190601f1680156101f05780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b61010f6004803603604081101561021457600080fd5b5080359060200135610736565b61010f6004803603602081101561023757600080fd5b50356001600160a01b03166108b6565b61024f610923565b604080519115158252519081900360200190f35b610152610933565b6000546001600160a01b031633146102b45760405162461bcd60e51b8152600401808060200182810382526026815260200180610f1e6026913960400191505060405180910390fd5b600160009054906101000a90046001600160a01b03166001600160a01b031663b4c3b7566040518163ffffffff1660e01b815260040160206040518083038186803b15801561030257600080fd5b505afa158015610316573d6000803e3d6000fd5b505050506040513d602081101561032c57600080fd5b50511561039e57600154600160a01b900460ff161561037c5760405162461bcd60e51b8152600401808060200182810382526035815260200180610ee96035913960400191505060405180910390fd5b6000838152600460205260409020610398906002018383610e18565b506103fc565b600154600160a01b900460ff16156103d5576103b983610939565b6000838152600560205260409020610398906002018383610e18565b6103de83610af8565b60008381526005602052604090206103fa906002018383610e18565b505b505050565b6000546001600160a01b031681565b60015460408051635a61dbab60e11b815290516000926001600160a01b03169163b4c3b756916004808301926020929190829003018186803b15801561045557600080fd5b505afa158015610469573d6000803e3d6000fd5b505050506040513d602081101561047f57600080fd5b5051156104e757600154600160a01b900460ff16156104cf5760405162461bcd60e51b8152600401808060200182810382526035815260200180610ee96035913960400191505060405180910390fd5b5060008181526004602052604090206001015461052c565b600154600160a01b900460ff16156104cf57610501610ca4565b60008281526007602052604090205460ff16156104cf57506000818152600560205260409020600101545b919050565b60025481565b60015460408051635a61dbab60e11b815290516060926001600160a01b03169163b4c3b756916004808301926020929190829003018186803b15801561057c57600080fd5b505afa158015610590573d6000803e3d6000fd5b505050506040513d60208110156105a657600080fd5b50511561069957600154600160a01b900460ff16156105f65760405162461bcd60e51b8152600401808060200182810382526035815260200180610ee96035913960400191505060405180910390fd5b6000828152600460209081526040918290206002908101805484516001821615610100026000190190911692909204601f81018490048402830184019094528382529092909183018282801561068d5780601f106106625761010080835404028352916020019161068d565b820191906000526020600020905b81548152906001019060200180831161067057829003601f168201915b5050505050905061052c565b600154600160a01b900460ff16156105f6576106b3610ca4565b60008281526007602052604090205460ff16156105f6576000828152600560209081526040918290206002908101805484516001821615610100026000190190911692909204601f81018490048402830184019094528382529092909183018282801561068d5780601f106106625761010080835404028352916020019161068d565b6000546001600160a01b0316331461077f5760405162461bcd60e51b8152600401808060200182810382526026815260200180610f1e6026913960400191505060405180910390fd5b600160009054906101000a90046001600160a01b03166001600160a01b031663b4c3b7566040518163ffffffff1660e01b815260040160206040518083038186803b1580156107cd57600080fd5b505afa1580156107e1573d6000803e3d6000fd5b505050506040513d60208110156107f757600080fd5b50511561086057600154600160a01b900460ff16156108475760405162461bcd60e51b8152600401808060200182810382526035815260200180610ee96035913960400191505060405180910390fd5b60008281526004602052604090206001018190556108b2565b600154600160a01b900460ff16156108945761087b82610939565b60008281526005602052604090206001018190556108b2565b61089d82610af8565b60008281526005602052604090206001018190555b5050565b7f00000000000000000000000000000000000000000000000000000000000000006001600160a01b031633146108eb57600080fd5b6000546001600160a01b03161561090157600080fd5b600080546001600160a01b0319166001600160a01b0392909216919091179055565b600154600160a01b900460ff1681565b60035481565b600160009054906101000a90046001600160a01b03166001600160a01b031663336d5b096040518163ffffffff1660e01b815260040160206040518083038186803b15801561098757600080fd5b505afa15801561099b573d6000803e3d6000fd5b505050506040513d60208110156109b157600080fd5b5051600254146109f25760405162461bcd60e51b8152600401808060200182810382526028815260200180610f446028913960400191505060405180910390fd5b600160009054906101000a90046001600160a01b03166001600160a01b03166366b79f5a6040518163ffffffff1660e01b815260040160206040518083038186803b158015610a4057600080fd5b505afa158015610a54573d6000803e3d6000fd5b505050506040513d6020811015610a6a57600080fd5b505160035414610aab5760405162461bcd60e51b8152600401808060200182810382526035815260200180610eb46035913960400191505060405180910390fd5b6006805460018181019092557ff652222313e28459528d920b65115c16c04f3efc82aaedc97be59f3f377c0d3f01829055600091825260076020526040909120805460ff19169091179055565b6001805460ff60a01b1916600160a01b17908190556040805163336d5b0960e01b815290516001600160a01b039092169163336d5b0991600480820192602092909190829003018186803b158015610b4f57600080fd5b505afa158015610b63573d6000803e3d6000fd5b505050506040513d6020811015610b7957600080fd5b50516002556001546040805163335bcfad60e11b815290516001600160a01b03909216916366b79f5a91600480820192602092909190829003018186803b158015610bc357600080fd5b505afa158015610bd7573d6000803e3d6000fd5b505050506040513d6020811015610bed57600080fd5b505160035560015460408051630df9736160e01b815230600482015290516001600160a01b0390921691630df973619160248082019260009290919082900301818387803b158015610c3e57600080fd5b505af1158015610c52573d6000803e3d6000fd5b50506006805460018181019092557ff652222313e28459528d920b65115c16c04f3efc82aaedc97be59f3f377c0d3f01849055600093845260076020526040909320805460ff19169093179092555050565b600160009054906101000a90046001600160a01b03166001600160a01b031663336d5b096040518163ffffffff1660e01b815260040160206040518083038186803b158015610cf257600080fd5b505afa158015610d06573d6000803e3d6000fd5b505050506040513d6020811015610d1c57600080fd5b505160025414610d5d5760405162461bcd60e51b8152600401808060200182810382526028815260200180610f446028913960400191505060405180910390fd5b600160009054906101000a90046001600160a01b03166001600160a01b03166366b79f5a6040518163ffffffff1660e01b815260040160206040518083038186803b158015610dab57600080fd5b505afa158015610dbf573d6000803e3d6000fd5b505050506040513d6020811015610dd557600080fd5b505160035414610e165760405162461bcd60e51b8152600401808060200182810382526035815260200180610eb46035913960400191505060405180910390fd5b565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10610e595782800160ff19823516178555610e86565b82800160010185558215610e86579182015b82811115610e86578235825591602001919060010190610e6b565b50610e92929150610e96565b5090565b610eb091905b80821115610e925760008155600101610e9c565b9056fe436f6e7472616374206c6f636b6564206279206f746865722063726f73732d626c6f636b636861696e207472616e73616374696f6e417474656d707465642073696e676c6520626c6f636b636861696e2063616c6c207768656e20636f6e7472616374206c6f636b65644f6e6c792063616c6c2066726f6d20627573696e657373206c6f67696320636f6e7472616374436f6e7472616374206c6f636b6564206279206f7468657220726f6f7420626c6f636b636861696ea2646970667358221220654c2f6e63c3c40c06bf212f1034fe8d40067cc3305fd1604c376ad583a0560164736f6c634300060b0033";

    public static final String FUNC_BUSINESSLOGICCONTRACT = "businessLogicContract";

    public static final String FUNC_GETBYTES = "getBytes";

    public static final String FUNC_GETUINT256 = "getUint256";

    public static final String FUNC_LOCKED = "locked";

    public static final String FUNC_LOCKEDBYROOTBLOCKCHAINID = "lockedByRootBlockchainId";

    public static final String FUNC_LOCKEDBYTRANSACTIONID = "lockedByTransactionId";

    public static final String FUNC_SETBUSINESSLOGICCONTRACT = "setBusinessLogicContract";

    public static final String FUNC_SETBYTES = "setBytes";

    public static final String FUNC_SETUINT256 = "setUint256";

    @Deprecated
    protected LockableStorage(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected LockableStorage(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected LockableStorage(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected LockableStorage(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<String> businessLogicContract() {
        final Function function = new Function(FUNC_BUSINESSLOGICCONTRACT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public String getRLP_businessLogicContract() {
        final Function function = new Function(
                FUNC_BUSINESSLOGICCONTRACT, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<byte[]> getBytes(BigInteger _key) {
        final Function function = new Function(FUNC_GETBYTES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_key)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicBytes>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public String getRLP_getBytes(BigInteger _key) {
        final Function function = new Function(
                FUNC_GETBYTES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_key)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<BigInteger> getUint256(BigInteger _key) {
        final Function function = new Function(FUNC_GETUINT256, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_key)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public String getRLP_getUint256(BigInteger _key) {
        final Function function = new Function(
                FUNC_GETUINT256, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_key)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<Boolean> locked() {
        final Function function = new Function(FUNC_LOCKED, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public String getRLP_locked() {
        final Function function = new Function(
                FUNC_LOCKED, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<BigInteger> lockedByRootBlockchainId() {
        final Function function = new Function(FUNC_LOCKEDBYROOTBLOCKCHAINID, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public String getRLP_lockedByRootBlockchainId() {
        final Function function = new Function(
                FUNC_LOCKEDBYROOTBLOCKCHAINID, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<BigInteger> lockedByTransactionId() {
        final Function function = new Function(FUNC_LOCKEDBYTRANSACTIONID, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public String getRLP_lockedByTransactionId() {
        final Function function = new Function(
                FUNC_LOCKEDBYTRANSACTIONID, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setBusinessLogicContract(String _businessLogicContract) {
        final Function function = new Function(
                FUNC_SETBUSINESSLOGICCONTRACT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _businessLogicContract)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getRLP_setBusinessLogicContract(String _businessLogicContract) {
        final Function function = new Function(
                FUNC_SETBUSINESSLOGICCONTRACT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _businessLogicContract)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setBytes(BigInteger _key, byte[] _val) {
        final Function function = new Function(
                FUNC_SETBYTES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_key), 
                new org.web3j.abi.datatypes.DynamicBytes(_val)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getRLP_setBytes(BigInteger _key, byte[] _val) {
        final Function function = new Function(
                FUNC_SETBYTES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_key), 
                new org.web3j.abi.datatypes.DynamicBytes(_val)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setUint256(BigInteger _key, BigInteger _val) {
        final Function function = new Function(
                FUNC_SETUINT256, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_key), 
                new org.web3j.abi.datatypes.generated.Uint256(_val)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getRLP_setUint256(BigInteger _key, BigInteger _val) {
        final Function function = new Function(
                FUNC_SETUINT256, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_key), 
                new org.web3j.abi.datatypes.generated.Uint256(_val)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    @Deprecated
    public static LockableStorage load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new LockableStorage(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static LockableStorage load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new LockableStorage(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static LockableStorage load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new LockableStorage(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static LockableStorage load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new LockableStorage(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<LockableStorage> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _crossBlockchainControl) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _crossBlockchainControl)));
        return deployRemoteCall(LockableStorage.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<LockableStorage> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _crossBlockchainControl) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _crossBlockchainControl)));
        return deployRemoteCall(LockableStorage.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<LockableStorage> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _crossBlockchainControl) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _crossBlockchainControl)));
        return deployRemoteCall(LockableStorage.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<LockableStorage> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _crossBlockchainControl) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _crossBlockchainControl)));
        return deployRemoteCall(LockableStorage.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }
}
