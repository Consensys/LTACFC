package tech.pegasys.ltacfc.examples.twochain.soliditywrappers;

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
public class OtherBlockchainContract extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b506040516112053803806112058339818101604052602081101561003357600080fd5b5051600080546001600160a01b039092166001600160a01b03199092169190911790556111a0806100656000396000f3fe608060405234801561001057600080fd5b50600436106101215760003560e01c806361d0f54f116100ad5780639cc51b23116100715780639cc51b23146103ae578063ac4ce2c6146103d1578063b93f9b0a146103fd578063e1cb0e5214610413578063e2b6f3f31461041b57610121565b806361d0f54f146102f557806363435466146103185780636716b0ec14610349578063891276e11461036c57806397cedc761461038957610121565b80633d4197f0116100f45780633d4197f0146101f45780633e06e62114610211578063441abbac1461021157806357bc2ef3146102405780635bf9755e146102d257610121565b8063011827fd146101265780630d7f9bde1461019f57806311ce0267146101a75780632f8d2984146101cb575b600080fd5b61019d6004803603604081101561013c57600080fd5b8135919081019060408101602082013564010000000081111561015e57600080fd5b82018360208201111561017057600080fd5b8035906020019184600183028401116401000000008311171561019257600080fd5b509092509050610444565b005b61019d6104e1565b6101af610501565b604080516001600160a01b039092168252519081900360200190f35b61019d600480360360608110156101e157600080fd5b5080359060208101359060400135610510565b61019d6004803603602081101561020a57600080fd5b5035610667565b61022e6004803603602081101561022757600080fd5b5035610672565b60408051918252519081900360200190f35b61025d6004803603602081101561025657600080fd5b50356106f1565b6040805160208082528351818301528351919283929083019185019080838360005b8381101561029757818101518382015260200161027f565b50505050905090810190601f1680156102c45780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b61019d600480360360408110156102e857600080fd5b5080359060200135610830565b61022e6004803603604081101561030b57600080fd5b50803590602001356109a7565b6103356004803603602081101561032e57600080fd5b5035610a4d565b604080519115158252519081900360200190f35b61019d6004803603604081101561035f57600080fd5b5080359060200135610add565b61019d6004803603602081101561038257600080fd5b5035610c53565b61019d6004803603604081101561039f57600080fd5b50803590602001351515610dff565b61022e600480360360408110156103c457600080fd5b5080359060200135610e7c565b61019d600480360360408110156103e757600080fd5b50803590602001356001600160a01b0316610fdf565b6101af6004803603602081101561022757600080fd5b61022e611031565b61019d6004803603606081101561043157600080fd5b5080359060208101359060400135611043565b6000546040805163011827fd60e01b81526004810186815260248201928352604482018590526001600160a01b039093169263011827fd928792879287929091606401848480828437600081840152601f19601f820116905080830192505050945050505050600060405180830381600087803b1580156104c457600080fd5b505af11580156104d8573d6000803e3d6000fd5b50505050505050565b60006104ed6001610672565b600190810191506104fe9082610830565b50565b6000546001600160a01b031681565b6000805460408051631106aeeb60e21b81526004810187905290516001600160a01b039092169163441abbac91602480820192602092909190829003018186803b15801561055d57600080fd5b505afa158015610571573d6000803e3d6000fd5b505050506040513d602081101561058757600080fd5b505190508083106105d5576040805162461bcd60e51b8152602060048201526013602482015272496e646578206f7574206f6620626f756e647360681b604482015290519081900360640190fd5b6040805160208082018790528251808303820181528284018085528151919092012060008054632dfcbaaf60e11b909352878201604485015260648401879052935190936001600160a01b0390921692635bf9755e92608480830193919282900301818387803b15801561064857600080fd5b505af115801561065c573d6000803e3d6000fd5b505050505050505050565b6104fe600182610830565b6000805460408051631106aeeb60e21b81526004810185905290516001600160a01b039092169163441abbac91602480820192602092909190829003018186803b1580156106bf57600080fd5b505afa1580156106d3573d6000803e3d6000fd5b505050506040513d60208110156106e957600080fd5b505192915050565b60008054604080516357bc2ef360e01b81526004810185905290516060936001600160a01b03909316926357bc2ef39260248082019391829003018186803b15801561073c57600080fd5b505afa158015610750573d6000803e3d6000fd5b505050506040513d6000823e601f3d908101601f19168201604052602081101561077957600080fd5b810190808051604051939291908464010000000082111561079957600080fd5b9083019060208201858111156107ae57600080fd5b82516401000000008111828201881017156107c857600080fd5b82525081516020918201929091019080838360005b838110156107f55781810151838201526020016107dd565b50505050905090810190601f1680156108225780820380516001836020036101000a031916815260200191505b506040525050509050919050565b6000805460408051632dfcbaaf60e11b8152600481018690526024810185905290516001600160a01b0390921692635bf9755e9260448084019382900301818387803b15801561087f57600080fd5b505af1925050508015610890575060015b6109a35761089c6110c2565b806108a7575061092c565b8060405162461bcd60e51b81526004018080602001828103825283818151815260200191508051906020019080838360005b838110156108f15781810151838201526020016108d9565b50505050905090810190601f16801561091e5780820380516001836020036101000a031916815260200191505b509250505060405180910390fd5b3d808015610956576040519150601f19603f3d011682016040523d82523d6000602084013e61095b565b606091505b5060405162461bcd60e51b81526020600482018181528351602484015283518493919283926044019190850190808383600083156108f15781810151838201526020016108d9565b5050565b6040805160208082018590528183018490528251808303840181526060830180855281519183019190912060008054631106aeeb60e21b90935260648501829052945190936001600160a01b039092169263441abbac9260848082019391829003018186803b158015610a1957600080fd5b505afa158015610a2d573d6000803e3d6000fd5b505050506040513d6020811015610a4357600080fd5b5051949350505050565b6000805460408051631106aeeb60e21b81526004810185905290516001600160a01b039092169163441abbac91602480820192602092909190829003018186803b158015610a9a57600080fd5b505afa158015610aae573d6000803e3d6000fd5b505050506040513d6020811015610ac457600080fd5b5051600114610ad4576000610ad7565b60015b92915050565b6000805460408051631106aeeb60e21b81526004810186905290516001600160a01b039092169163441abbac91602480820192602092909190829003018186803b158015610b2a57600080fd5b505afa158015610b3e573d6000803e3d6000fd5b505050506040513d6020811015610b5457600080fd5b50516040805160208181018790528251808303820181528284018085528151919092012060008054632dfcbaaf60e11b9093528186016044850152606484018890529351949550936001600160a01b0390911692635bf9755e926084808201939182900301818387803b158015610bca57600080fd5b505af1158015610bde573d6000803e3d6000fd5b50506000805460408051632dfcbaaf60e11b8152600481018a905260018801602482015290516001600160a01b039092169450635bf9755e9350604480820193929182900301818387803b158015610c3557600080fd5b505af1158015610c49573d6000803e3d6000fd5b5050505050505050565b6000805460408051631106aeeb60e21b81526004810185905290516001600160a01b039092169163441abbac91602480820192602092909190829003018186803b158015610ca057600080fd5b505afa158015610cb4573d6000803e3d6000fd5b505050506040513d6020811015610cca57600080fd5b5051905080610d20576040805162461bcd60e51b815260206004820152601e60248201527f506f702063616c6c6564206f6e7a65726f206c656e6774682061727261790000604482015290519081900360640190fd5b6040805160208082018590528251808303820181528284018085528151919092012060008054632dfcbaaf60e11b909352858201604485015260648401819052935190936001600160a01b0390921692635bf9755e92608480830193919282900301818387803b158015610d9357600080fd5b505af1158015610da7573d6000803e3d6000fd5b50506000805460408051632dfcbaaf60e11b8152600481018990526000198801602482015290516001600160a01b039092169450635bf9755e9350604480820193929182900301818387803b1580156104c457600080fd5b6000546001600160a01b0316635bf9755e8383610e1d576000610e20565b60015b6040518363ffffffff1660e01b8152600401808381526020018260ff16815260200192505050600060405180830381600087803b158015610e6057600080fd5b505af1158015610e74573d6000803e3d6000fd5b505050505050565b6000805460408051631106aeeb60e21b815260048101869052905183926001600160a01b03169163441abbac916024808301926020929190829003018186803b158015610ec857600080fd5b505afa158015610edc573d6000803e3d6000fd5b505050506040513d6020811015610ef257600080fd5b50519050828111610f40576040805162461bcd60e51b8152602060048201526013602482015272496e646578206f7574206f6620626f756e647360681b604482015290519081900360640190fd5b604080516020808201879052825180830382018152828401808552815191830191909120600054631106aeeb60e21b909252878101604485015293516001600160a01b039091169263441abbac926064808301939192829003018186803b158015610faa57600080fd5b505afa158015610fbe573d6000803e3d6000fd5b505050506040513d6020811015610fd457600080fd5b505195945050505050565b6000805460408051632dfcbaaf60e11b8152600481018690526001600160a01b03858116602483015291519190921692635bf9755e926044808201939182900301818387803b158015610e6057600080fd5b600061103d6001610672565b90505b90565b604080516020808201869052818301859052825180830384018152606083018085528151919092012060008054632dfcbaaf60e11b9093526064840182905260848401869052935190936001600160a01b0390921692635bf9755e9260a480830193919282900301818387803b158015610c3557600080fd5b60e01c90565b600060443d10156110d257611040565b600481823e6308c379a06110e682516110bc565b146110f057611040565b6040513d600319016004823e80513d67ffffffffffffffff81602484011181841117156111205750505050611040565b8284019150815192508083111561113a5750505050611040565b503d8301602083830101111561115257505050611040565b601f91909101601f191681016020016040529150509056fea2646970667358221220a12abdf7d73d19567f002c3288503674f76ffb3e3b1e622ca3dfe7206fc1a96e64736f6c634300060b0033";

    public static final String FUNC_GETADDRESS = "getAddress";

    public static final String FUNC_GETARRAYLENGTH = "getArrayLength";

    public static final String FUNC_GETARRAYVALUE = "getArrayValue";

    public static final String FUNC_GETBOOL = "getBool";

    public static final String FUNC_GETBYTES = "getBytes";

    public static final String FUNC_GETMAPVALUE = "getMapValue";

    public static final String FUNC_GETUINT256 = "getUint256";

    public static final String FUNC_GETVAL = "getVal";

    public static final String FUNC_INCREMENTVAL = "incrementVal";

    public static final String FUNC_POPARRAYVALUE = "popArrayValue";

    public static final String FUNC_PUSHARRAYVALUE = "pushArrayValue";

    public static final String FUNC_SETADDRESS = "setAddress";

    public static final String FUNC_SETARRAYVALUE = "setArrayValue";

    public static final String FUNC_SETBOOL = "setBool";

    public static final String FUNC_SETBYTES = "setBytes";

    public static final String FUNC_SETMAPVALUE = "setMapValue";

    public static final String FUNC_SETUINT256 = "setUint256";

    public static final String FUNC_SETVAL = "setVal";

    public static final String FUNC_STORAGECONTRACT = "storageContract";

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
