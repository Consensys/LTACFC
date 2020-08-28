package tech.pegasys.ltacfc.examples.twochain.soliditywrappers;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import org.web3j.abi.FunctionEncoder;
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
public class LockableStorage extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b506040516113133803806113138339818101604052604081101561003357600080fd5b508051602090910151600180546001600160a01b039384166001600160a01b031991821617909155600080549390921692169190911790556112998061007a6000396000f3fe608060405234801561001057600080fd5b506004361061007d5760003560e01c80635bf9755e1161005b5780635bf9755e146101bc57806363435466146101df57806397cedc7614610210578063cf309012146102355761007d565b8063011827fd14610082578063441abbac146100fb57806357bc2ef31461012a575b600080fd5b6100f96004803603604081101561009857600080fd5b813591908101906040810160208201356401000000008111156100ba57600080fd5b8201836020820111156100cc57600080fd5b803590602001918460018302840111640100000000831117156100ee57600080fd5b50909250905061023d565b005b6101186004803603602081101561011157600080fd5b50356103d3565b60408051918252519081900360200190f35b6101476004803603602081101561014057600080fd5b5035610547565b6040805160208082528351818301528351919283929083019185019080838360005b83811015610181578181015183820152602001610169565b50505050905090810190601f1680156101ae5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b6100f9600480360360408110156101d257600080fd5b508035906020013561079b565b6101fc600480360360208110156101f557600080fd5b503561091b565b604080519115158252519081900360200190f35b6100f96004803603604081101561022657600080fd5b50803590602001351515610a8e565b6101fc610c21565b6000546001600160a01b031633146102865760405162461bcd60e51b81526004018080602001828103825260268152602001806112166026913960400191505060405180910390fd5b600160009054906101000a90046001600160a01b03166001600160a01b031663b4c3b7566040518163ffffffff1660e01b815260040160206040518083038186803b1580156102d457600080fd5b505afa1580156102e8573d6000803e3d6000fd5b505050506040513d60208110156102fe57600080fd5b50511561037057600154600160a01b900460ff161561034e5760405162461bcd60e51b81526004018080602001828103825260358152602001806111e16035913960400191505060405180910390fd5b600083815260046020526040902061036a906002018383611110565b506103ce565b600154600160a01b900460ff16156103a75761038b83610c31565b600083815260056020526040902061036a906002018383611110565b6103b083610df0565b60008381526005602052604090206103cc906002018383611110565b505b505050565b600080546001600160a01b0316331461041d5760405162461bcd60e51b81526004018080602001828103825260268152602001806112166026913960400191505060405180910390fd5b600160009054906101000a90046001600160a01b03166001600160a01b031663b4c3b7566040518163ffffffff1660e01b815260040160206040518083038186803b15801561046b57600080fd5b505afa15801561047f573d6000803e3d6000fd5b505050506040513d602081101561049557600080fd5b5051156104fd57600154600160a01b900460ff16156104e55760405162461bcd60e51b81526004018080602001828103825260358152602001806111e16035913960400191505060405180910390fd5b50600081815260046020526040902060010154610542565b600154600160a01b900460ff16156104e557610517610f9c565b60008281526007602052604090205460ff16156104e557506000818152600560205260409020600101545b919050565b6000546060906001600160a01b031633146105935760405162461bcd60e51b81526004018080602001828103825260268152602001806112166026913960400191505060405180910390fd5b600160009054906101000a90046001600160a01b03166001600160a01b031663b4c3b7566040518163ffffffff1660e01b815260040160206040518083038186803b1580156105e157600080fd5b505afa1580156105f5573d6000803e3d6000fd5b505050506040513d602081101561060b57600080fd5b5051156106fe57600154600160a01b900460ff161561065b5760405162461bcd60e51b81526004018080602001828103825260358152602001806111e16035913960400191505060405180910390fd5b6000828152600460209081526040918290206002908101805484516001821615610100026000190190911692909204601f8101849004840283018401909452838252909290918301828280156106f25780601f106106c7576101008083540402835291602001916106f2565b820191906000526020600020905b8154815290600101906020018083116106d557829003601f168201915b50505050509050610542565b600154600160a01b900460ff161561065b57610718610f9c565b60008281526007602052604090205460ff161561065b576000828152600560209081526040918290206002908101805484516001821615610100026000190190911692909204601f8101849004840283018401909452838252909290918301828280156106f25780601f106106c7576101008083540402835291602001916106f2565b6000546001600160a01b031633146107e45760405162461bcd60e51b81526004018080602001828103825260268152602001806112166026913960400191505060405180910390fd5b600160009054906101000a90046001600160a01b03166001600160a01b031663b4c3b7566040518163ffffffff1660e01b815260040160206040518083038186803b15801561083257600080fd5b505afa158015610846573d6000803e3d6000fd5b505050506040513d602081101561085c57600080fd5b5051156108c557600154600160a01b900460ff16156108ac5760405162461bcd60e51b81526004018080602001828103825260358152602001806111e16035913960400191505060405180910390fd5b6000828152600460205260409020600101819055610917565b600154600160a01b900460ff16156108f9576108e082610c31565b6000828152600560205260409020600101819055610917565b61090282610df0565b60008281526005602052604090206001018190555b5050565b600080546001600160a01b031633146109655760405162461bcd60e51b81526004018080602001828103825260268152602001806112166026913960400191505060405180910390fd5b600160009054906101000a90046001600160a01b03166001600160a01b031663b4c3b7566040518163ffffffff1660e01b815260040160206040518083038186803b1580156109b357600080fd5b505afa1580156109c7573d6000803e3d6000fd5b505050506040513d60208110156109dd57600080fd5b505115610a4557600154600160a01b900460ff1615610a2d5760405162461bcd60e51b81526004018080602001828103825260358152602001806111e16035913960400191505060405180910390fd5b5060008181526004602052604090205460ff16610542565b600154600160a01b900460ff1615610a2d57610a5f610f9c565b60008281526007602052604090205460ff1615610a2d575060008181526005602052604090205460ff16610542565b6000546001600160a01b03163314610ad75760405162461bcd60e51b81526004018080602001828103825260268152602001806112166026913960400191505060405180910390fd5b600160009054906101000a90046001600160a01b03166001600160a01b031663b4c3b7566040518163ffffffff1660e01b815260040160206040518083038186803b158015610b2557600080fd5b505afa158015610b39573d6000803e3d6000fd5b505050506040513d6020811015610b4f57600080fd5b505115610bbe57600154600160a01b900460ff1615610b9f5760405162461bcd60e51b81526004018080602001828103825260358152602001806111e16035913960400191505060405180910390fd5b6000828152600460205260409020805460ff1916821515179055610917565b600154600160a01b900460ff1615610bf857610bd982610c31565b6000828152600560205260409020805460ff1916821515179055610917565b610c0182610df0565b6000828152600560205260409020805482151560ff199091161790555050565b600154600160a01b900460ff1681565b600160009054906101000a90046001600160a01b03166001600160a01b031663336d5b096040518163ffffffff1660e01b815260040160206040518083038186803b158015610c7f57600080fd5b505afa158015610c93573d6000803e3d6000fd5b505050506040513d6020811015610ca957600080fd5b505160025414610cea5760405162461bcd60e51b815260040180806020018281038252602881526020018061123c6028913960400191505060405180910390fd5b600160009054906101000a90046001600160a01b03166001600160a01b03166366b79f5a6040518163ffffffff1660e01b815260040160206040518083038186803b158015610d3857600080fd5b505afa158015610d4c573d6000803e3d6000fd5b505050506040513d6020811015610d6257600080fd5b505160035414610da35760405162461bcd60e51b81526004018080602001828103825260358152602001806111ac6035913960400191505060405180910390fd5b6006805460018181019092557ff652222313e28459528d920b65115c16c04f3efc82aaedc97be59f3f377c0d3f01829055600091825260076020526040909120805460ff19169091179055565b6001805460ff60a01b1916600160a01b17908190556040805163336d5b0960e01b815290516001600160a01b039092169163336d5b0991600480820192602092909190829003018186803b158015610e4757600080fd5b505afa158015610e5b573d6000803e3d6000fd5b505050506040513d6020811015610e7157600080fd5b50516002556001546040805163335bcfad60e11b815290516001600160a01b03909216916366b79f5a91600480820192602092909190829003018186803b158015610ebb57600080fd5b505afa158015610ecf573d6000803e3d6000fd5b505050506040513d6020811015610ee557600080fd5b505160035560015460408051630df9736160e01b815230600482015290516001600160a01b0390921691630df973619160248082019260009290919082900301818387803b158015610f3657600080fd5b505af1158015610f4a573d6000803e3d6000fd5b50506006805460018181019092557ff652222313e28459528d920b65115c16c04f3efc82aaedc97be59f3f377c0d3f01849055600093845260076020526040909320805460ff19169093179092555050565b600160009054906101000a90046001600160a01b03166001600160a01b031663336d5b096040518163ffffffff1660e01b815260040160206040518083038186803b158015610fea57600080fd5b505afa158015610ffe573d6000803e3d6000fd5b505050506040513d602081101561101457600080fd5b5051600254146110555760405162461bcd60e51b815260040180806020018281038252602881526020018061123c6028913960400191505060405180910390fd5b600160009054906101000a90046001600160a01b03166001600160a01b03166366b79f5a6040518163ffffffff1660e01b815260040160206040518083038186803b1580156110a357600080fd5b505afa1580156110b7573d6000803e3d6000fd5b505050506040513d60208110156110cd57600080fd5b50516003541461110e5760405162461bcd60e51b81526004018080602001828103825260358152602001806111ac6035913960400191505060405180910390fd5b565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106111515782800160ff1982351617855561117e565b8280016001018555821561117e579182015b8281111561117e578235825591602001919060010190611163565b5061118a92915061118e565b5090565b6111a891905b8082111561118a5760008155600101611194565b9056fe436f6e7472616374206c6f636b6564206279206f746865722063726f73732d626c6f636b636861696e207472616e73616374696f6e417474656d707465642073696e676c6520626c6f636b636861696e2063616c6c207768656e20636f6e7472616374206c6f636b65644f6e6c792063616c6c2066726f6d20627573696e657373206c6f67696320636f6e7472616374436f6e7472616374206c6f636b6564206279206f7468657220726f6f7420626c6f636b636861696ea2646970667358221220c147d8a41c332906d5101cf9e29475c55b8a6602c4d83504d6faf18cc1f5a91264736f6c634300060b0033";

    public static final String FUNC_GETBOOL = "getBool";

    public static final String FUNC_GETBYTES = "getBytes";

    public static final String FUNC_GETUINT256 = "getUint256";

    public static final String FUNC_LOCKED = "locked";

    public static final String FUNC_SETBOOL = "setBool";

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

    public RemoteFunctionCall<Boolean> getBool(BigInteger _key) {
        final Function function = new Function(FUNC_GETBOOL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_key)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public String getRLP_getBool(BigInteger _key) {
        final Function function = new Function(
                FUNC_GETBOOL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_key)), 
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

    public RemoteFunctionCall<TransactionReceipt> setBool(BigInteger _key, Boolean _val) {
        final Function function = new Function(
                FUNC_SETBOOL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_key), 
                new org.web3j.abi.datatypes.Bool(_val)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getRLP_setBool(BigInteger _key, Boolean _val) {
        final Function function = new Function(
                FUNC_SETBOOL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_key), 
                new org.web3j.abi.datatypes.Bool(_val)), 
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

    public static RemoteCall<LockableStorage> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _crossBlockchainControl, String _businessLogicContract) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _crossBlockchainControl), 
                new org.web3j.abi.datatypes.Address(160, _businessLogicContract)));
        return deployRemoteCall(LockableStorage.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<LockableStorage> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _crossBlockchainControl, String _businessLogicContract) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _crossBlockchainControl), 
                new org.web3j.abi.datatypes.Address(160, _businessLogicContract)));
        return deployRemoteCall(LockableStorage.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<LockableStorage> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _crossBlockchainControl, String _businessLogicContract) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _crossBlockchainControl), 
                new org.web3j.abi.datatypes.Address(160, _businessLogicContract)));
        return deployRemoteCall(LockableStorage.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<LockableStorage> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _crossBlockchainControl, String _businessLogicContract) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _crossBlockchainControl), 
                new org.web3j.abi.datatypes.Address(160, _businessLogicContract)));
        return deployRemoteCall(LockableStorage.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }
}
