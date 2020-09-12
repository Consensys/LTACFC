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
public class LockableStorageWrapper extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b50604051610fa4380380610fa48339818101604052602081101561003357600080fd5b5051600080546001600160a01b039092166001600160a01b0319909216919091179055610f3f806100656000396000f3fe608060405234801561001057600080fd5b50600436106101005760003560e01c806363435466116100975780639cc51b23116100665780639cc51b2314610368578063ac4ce2c61461038b578063b93f9b0a146103b7578063e2b6f3f3146103cd57610100565b806363435466146102d25780636716b0ec14610303578063891276e11461032657806397cedc761461034357610100565b8063441abbac116100d3578063441abbac146101cb57806357bc2ef3146101fa5780635bf9755e1461028c57806361d0f54f146102af57610100565b8063011827fd1461010557806311ce02671461017e5780632f8d2984146101a25780633e06e621146101cb575b600080fd5b61017c6004803603604081101561011b57600080fd5b8135919081019060408101602082013564010000000081111561013d57600080fd5b82018360208201111561014f57600080fd5b8035906020019184600183028401116401000000008311171561017157600080fd5b5090925090506103f6565b005b610186610493565b604080516001600160a01b039092168252519081900360200190f35b61017c600480360360608110156101b857600080fd5b50803590602081013590604001356104a2565b6101e8600480360360208110156101e157600080fd5b50356105f9565b60408051918252519081900360200190f35b6102176004803603602081101561021057600080fd5b5035610678565b6040805160208082528351818301528351919283929083019185019080838360005b83811015610251578181015183820152602001610239565b50505050905090810190601f16801561027e5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b61017c600480360360408110156102a257600080fd5b50803590602001356107b7565b6101e8600480360360408110156102c557600080fd5b5080359060200135610822565b6102ef600480360360208110156102e857600080fd5b50356108c8565b604080519115158252519081900360200190f35b61017c6004803603604081101561031957600080fd5b5080359060200135610958565b61017c6004803603602081101561033c57600080fd5b5035610ace565b61017c6004803603604081101561035957600080fd5b50803590602001351515610c7a565b6101e86004803603604081101561037e57600080fd5b5080359060200135610cdb565b61017c600480360360408110156103a157600080fd5b50803590602001356001600160a01b0316610e3e565b610186600480360360208110156101e157600080fd5b61017c600480360360608110156103e357600080fd5b5080359060208101359060400135610e90565b6000546040805163011827fd60e01b81526004810186815260248201928352604482018590526001600160a01b039093169263011827fd928792879287929091606401848480828437600081840152601f19601f820116905080830192505050945050505050600060405180830381600087803b15801561047657600080fd5b505af115801561048a573d6000803e3d6000fd5b50505050505050565b6000546001600160a01b031681565b6000805460408051631106aeeb60e21b81526004810187905290516001600160a01b039092169163441abbac91602480820192602092909190829003018186803b1580156104ef57600080fd5b505afa158015610503573d6000803e3d6000fd5b505050506040513d602081101561051957600080fd5b50519050808310610567576040805162461bcd60e51b8152602060048201526013602482015272496e646578206f7574206f6620626f756e647360681b604482015290519081900360640190fd5b6040805160208082018790528251808303820181528284018085528151919092012060008054632dfcbaaf60e11b909352878201604485015260648401879052935190936001600160a01b0390921692635bf9755e92608480830193919282900301818387803b1580156105da57600080fd5b505af11580156105ee573d6000803e3d6000fd5b505050505050505050565b6000805460408051631106aeeb60e21b81526004810185905290516001600160a01b039092169163441abbac91602480820192602092909190829003018186803b15801561064657600080fd5b505afa15801561065a573d6000803e3d6000fd5b505050506040513d602081101561067057600080fd5b505192915050565b60008054604080516357bc2ef360e01b81526004810185905290516060936001600160a01b03909316926357bc2ef39260248082019391829003018186803b1580156106c357600080fd5b505afa1580156106d7573d6000803e3d6000fd5b505050506040513d6000823e601f3d908101601f19168201604052602081101561070057600080fd5b810190808051604051939291908464010000000082111561072057600080fd5b90830190602082018581111561073557600080fd5b825164010000000081118282018810171561074f57600080fd5b82525081516020918201929091019080838360005b8381101561077c578181015183820152602001610764565b50505050905090810190601f1680156107a95780820380516001836020036101000a031916815260200191505b506040525050509050919050565b6000805460408051632dfcbaaf60e11b8152600481018690526024810185905290516001600160a01b0390921692635bf9755e9260448084019382900301818387803b15801561080657600080fd5b505af115801561081a573d6000803e3d6000fd5b505050505050565b6040805160208082018590528183018490528251808303840181526060830180855281519183019190912060008054631106aeeb60e21b90935260648501829052945190936001600160a01b039092169263441abbac9260848082019391829003018186803b15801561089457600080fd5b505afa1580156108a8573d6000803e3d6000fd5b505050506040513d60208110156108be57600080fd5b5051949350505050565b6000805460408051631106aeeb60e21b81526004810185905290516001600160a01b039092169163441abbac91602480820192602092909190829003018186803b15801561091557600080fd5b505afa158015610929573d6000803e3d6000fd5b505050506040513d602081101561093f57600080fd5b505160011461094f576000610952565b60015b92915050565b6000805460408051631106aeeb60e21b81526004810186905290516001600160a01b039092169163441abbac91602480820192602092909190829003018186803b1580156109a557600080fd5b505afa1580156109b9573d6000803e3d6000fd5b505050506040513d60208110156109cf57600080fd5b50516040805160208181018790528251808303820181528284018085528151919092012060008054632dfcbaaf60e11b9093528186016044850152606484018890529351949550936001600160a01b0390911692635bf9755e926084808201939182900301818387803b158015610a4557600080fd5b505af1158015610a59573d6000803e3d6000fd5b50506000805460408051632dfcbaaf60e11b8152600481018a905260018801602482015290516001600160a01b039092169450635bf9755e9350604480820193929182900301818387803b158015610ab057600080fd5b505af1158015610ac4573d6000803e3d6000fd5b5050505050505050565b6000805460408051631106aeeb60e21b81526004810185905290516001600160a01b039092169163441abbac91602480820192602092909190829003018186803b158015610b1b57600080fd5b505afa158015610b2f573d6000803e3d6000fd5b505050506040513d6020811015610b4557600080fd5b5051905080610b9b576040805162461bcd60e51b815260206004820152601e60248201527f506f702063616c6c6564206f6e7a65726f206c656e6774682061727261790000604482015290519081900360640190fd5b6040805160208082018590528251808303820181528284018085528151919092012060008054632dfcbaaf60e11b909352858201604485015260648401819052935190936001600160a01b0390921692635bf9755e92608480830193919282900301818387803b158015610c0e57600080fd5b505af1158015610c22573d6000803e3d6000fd5b50506000805460408051632dfcbaaf60e11b8152600481018990526000198801602482015290516001600160a01b039092169450635bf9755e9350604480820193929182900301818387803b15801561047657600080fd5b6000546001600160a01b0316635bf9755e8383610c98576000610c9b565b60015b6040518363ffffffff1660e01b8152600401808381526020018260ff16815260200192505050600060405180830381600087803b15801561080657600080fd5b6000805460408051631106aeeb60e21b815260048101869052905183926001600160a01b03169163441abbac916024808301926020929190829003018186803b158015610d2757600080fd5b505afa158015610d3b573d6000803e3d6000fd5b505050506040513d6020811015610d5157600080fd5b50519050828111610d9f576040805162461bcd60e51b8152602060048201526013602482015272496e646578206f7574206f6620626f756e647360681b604482015290519081900360640190fd5b604080516020808201879052825180830382018152828401808552815191830191909120600054631106aeeb60e21b909252878101604485015293516001600160a01b039091169263441abbac926064808301939192829003018186803b158015610e0957600080fd5b505afa158015610e1d573d6000803e3d6000fd5b505050506040513d6020811015610e3357600080fd5b505195945050505050565b6000805460408051632dfcbaaf60e11b8152600481018690526001600160a01b03858116602483015291519190921692635bf9755e926044808201939182900301818387803b15801561080657600080fd5b604080516020808201869052818301859052825180830384018152606083018085528151919092012060008054632dfcbaaf60e11b9093526064840182905260848401869052935190936001600160a01b0390921692635bf9755e9260a480830193919282900301818387803b158015610ab057600080fdfea264697066735822122021dd4ecc71a0110992fb8ef4196405f53c527e371a493bca8aa6032c0aabb58f64736f6c634300060b0033";

    public static final String FUNC_GETADDRESS = "getAddress";

    public static final String FUNC_GETARRAYLENGTH = "getArrayLength";

    public static final String FUNC_GETARRAYVALUE = "getArrayValue";

    public static final String FUNC_GETBOOL = "getBool";

    public static final String FUNC_GETBYTES = "getBytes";

    public static final String FUNC_GETMAPVALUE = "getMapValue";

    public static final String FUNC_GETUINT256 = "getUint256";

    public static final String FUNC_POPARRAYVALUE = "popArrayValue";

    public static final String FUNC_PUSHARRAYVALUE = "pushArrayValue";

    public static final String FUNC_SETADDRESS = "setAddress";

    public static final String FUNC_SETARRAYVALUE = "setArrayValue";

    public static final String FUNC_SETBOOL = "setBool";

    public static final String FUNC_SETBYTES = "setBytes";

    public static final String FUNC_SETMAPVALUE = "setMapValue";

    public static final String FUNC_SETUINT256 = "setUint256";

    public static final String FUNC_STORAGECONTRACT = "storageContract";

    @Deprecated
    protected LockableStorageWrapper(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected LockableStorageWrapper(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected LockableStorageWrapper(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected LockableStorageWrapper(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<String> getAddress(BigInteger _key) {
        final Function function = new Function(FUNC_GETADDRESS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_key)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public String getRLP_getAddress(BigInteger _key) {
        final Function function = new Function(
                FUNC_GETADDRESS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_key)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<BigInteger> getArrayLength(BigInteger _key) {
        final Function function = new Function(FUNC_GETARRAYLENGTH, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_key)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public String getRLP_getArrayLength(BigInteger _key) {
        final Function function = new Function(
                FUNC_GETARRAYLENGTH, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_key)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<BigInteger> getArrayValue(BigInteger _key, BigInteger _index) {
        final Function function = new Function(FUNC_GETARRAYVALUE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_key), 
                new org.web3j.abi.datatypes.generated.Uint256(_index)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public String getRLP_getArrayValue(BigInteger _key, BigInteger _index) {
        final Function function = new Function(
                FUNC_GETARRAYVALUE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_key), 
                new org.web3j.abi.datatypes.generated.Uint256(_index)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
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

    public RemoteFunctionCall<BigInteger> getMapValue(BigInteger _key, BigInteger _mapKey) {
        final Function function = new Function(FUNC_GETMAPVALUE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_key), 
                new org.web3j.abi.datatypes.generated.Uint256(_mapKey)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public String getRLP_getMapValue(BigInteger _key, BigInteger _mapKey) {
        final Function function = new Function(
                FUNC_GETMAPVALUE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_key), 
                new org.web3j.abi.datatypes.generated.Uint256(_mapKey)), 
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

    public RemoteFunctionCall<TransactionReceipt> popArrayValue(BigInteger _key) {
        final Function function = new Function(
                FUNC_POPARRAYVALUE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_key)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getRLP_popArrayValue(BigInteger _key) {
        final Function function = new Function(
                FUNC_POPARRAYVALUE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_key)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<TransactionReceipt> pushArrayValue(BigInteger _key, BigInteger _val) {
        final Function function = new Function(
                FUNC_PUSHARRAYVALUE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_key), 
                new org.web3j.abi.datatypes.generated.Uint256(_val)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getRLP_pushArrayValue(BigInteger _key, BigInteger _val) {
        final Function function = new Function(
                FUNC_PUSHARRAYVALUE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_key), 
                new org.web3j.abi.datatypes.generated.Uint256(_val)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setAddress(BigInteger _key, String _address) {
        final Function function = new Function(
                FUNC_SETADDRESS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_key), 
                new org.web3j.abi.datatypes.Address(160, _address)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getRLP_setAddress(BigInteger _key, String _address) {
        final Function function = new Function(
                FUNC_SETADDRESS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_key), 
                new org.web3j.abi.datatypes.Address(160, _address)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setArrayValue(BigInteger _key, BigInteger _index, BigInteger _val) {
        final Function function = new Function(
                FUNC_SETARRAYVALUE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_key), 
                new org.web3j.abi.datatypes.generated.Uint256(_index), 
                new org.web3j.abi.datatypes.generated.Uint256(_val)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getRLP_setArrayValue(BigInteger _key, BigInteger _index, BigInteger _val) {
        final Function function = new Function(
                FUNC_SETARRAYVALUE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_key), 
                new org.web3j.abi.datatypes.generated.Uint256(_index), 
                new org.web3j.abi.datatypes.generated.Uint256(_val)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setBool(BigInteger _key, Boolean _flag) {
        final Function function = new Function(
                FUNC_SETBOOL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_key), 
                new org.web3j.abi.datatypes.Bool(_flag)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getRLP_setBool(BigInteger _key, Boolean _flag) {
        final Function function = new Function(
                FUNC_SETBOOL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_key), 
                new org.web3j.abi.datatypes.Bool(_flag)), 
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

    public RemoteFunctionCall<TransactionReceipt> setMapValue(BigInteger _key, BigInteger _mapKey, BigInteger _val) {
        final Function function = new Function(
                FUNC_SETMAPVALUE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_key), 
                new org.web3j.abi.datatypes.generated.Uint256(_mapKey), 
                new org.web3j.abi.datatypes.generated.Uint256(_val)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getRLP_setMapValue(BigInteger _key, BigInteger _mapKey, BigInteger _val) {
        final Function function = new Function(
                FUNC_SETMAPVALUE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_key), 
                new org.web3j.abi.datatypes.generated.Uint256(_mapKey), 
                new org.web3j.abi.datatypes.generated.Uint256(_val)), 
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

    public RemoteFunctionCall<String> storageContract() {
        final Function function = new Function(FUNC_STORAGECONTRACT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public String getRLP_storageContract() {
        final Function function = new Function(
                FUNC_STORAGECONTRACT, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    @Deprecated
    public static LockableStorageWrapper load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new LockableStorageWrapper(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static LockableStorageWrapper load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new LockableStorageWrapper(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static LockableStorageWrapper load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new LockableStorageWrapper(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static LockableStorageWrapper load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new LockableStorageWrapper(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<LockableStorageWrapper> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _storageContract) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _storageContract)));
        return deployRemoteCall(LockableStorageWrapper.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<LockableStorageWrapper> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _storageContract) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _storageContract)));
        return deployRemoteCall(LockableStorageWrapper.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<LockableStorageWrapper> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _storageContract) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _storageContract)));
        return deployRemoteCall(LockableStorageWrapper.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<LockableStorageWrapper> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _storageContract) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _storageContract)));
        return deployRemoteCall(LockableStorageWrapper.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }
}
