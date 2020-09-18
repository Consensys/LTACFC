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
public class RootBlockchainContract extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b5060405161170a38038061170a8339818101604052608081101561003357600080fd5b50805160208201516040830151606090930151600080546001600160a01b039283166001600160a01b031991821617909155600180549483169482169490941790935560029190915560038054919093169116179055611672806100986000396000f3fe608060405234801561001057600080fd5b50600436106101375760003560e01c806361d0f54f116100b8578063891276e11161007c578063891276e1146103c457806397cedc76146103e15780639cc51b2314610406578063ac4ce2c614610429578063b93f9b0a14610455578063e2b6f3f31461046b57610137565b806361d0f54f1461033d57806363435466146103605780636716b0ec146103915780637921381a146103b457806382bc854b146103bc57610137565b8063441abbac116100ff578063441abbac1461021f57806347c9c2d51461024e57806357bc2ef31461026b57806358033de1146102fd5780635bf9755e1461031a57610137565b8063011827fd1461013c57806311ce0267146101b55780632f8d2984146101d95780633507eaa7146102025780633e06e6211461021f575b600080fd5b6101b36004803603604081101561015257600080fd5b8135919081019060408101602082013564010000000081111561017457600080fd5b82018360208201111561018657600080fd5b803590602001918460018302840111640100000000831117156101a857600080fd5b509092509050610494565b005b6101bd610531565b604080516001600160a01b039092168252519081900360200190f35b6101b3600480360360608110156101ef57600080fd5b5080359060208101359060400135610540565b6101b36004803603602081101561021857600080fd5b5035610697565b61023c6004803603602081101561023557600080fd5b50356107ad565b60408051918252519081900360200190f35b6101b36004803603602081101561026457600080fd5b503561082c565b6102886004803603602081101561028157600080fd5b503561083b565b6040805160208082528351818301528351919283929083019185019080838360005b838110156102c25781810151838201526020016102aa565b50505050905090810190601f1680156102ef5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b6101b36004803603602081101561031357600080fd5b503561097a565b6101b36004803603604081101561033057600080fd5b5080359060200135610bdc565b61023c6004803603604081101561035357600080fd5b5080359060200135610d4f565b61037d6004803603602081101561037657600080fd5b5035610df5565b604080519115158252519081900360200190f35b6101b3600480360360408110156103a757600080fd5b5080359060200135610e85565b61023c610ffb565b6101b361100e565b6101b3600480360360208110156103da57600080fd5b5035611137565b6101b3600480360360408110156103f757600080fd5b508035906020013515156112e3565b61023c6004803603604081101561041c57600080fd5b5080359060200135611360565b6101b36004803603604081101561043f57600080fd5b50803590602001356001600160a01b03166114c3565b6101bd6004803603602081101561023557600080fd5b6101b36004803603606081101561048157600080fd5b5080359060208101359060400135611515565b6000546040805163011827fd60e01b81526004810186815260248201928352604482018590526001600160a01b039093169263011827fd928792879287929091606401848480828437600081840152601f19601f820116905080830192505050945050505050600060405180830381600087803b15801561051457600080fd5b505af1158015610528573d6000803e3d6000fd5b50505050505050565b6000546001600160a01b031681565b6000805460408051631106aeeb60e21b81526004810187905290516001600160a01b039092169163441abbac91602480820192602092909190829003018186803b15801561058d57600080fd5b505afa1580156105a1573d6000803e3d6000fd5b505050506040513d60208110156105b757600080fd5b50519050808310610605576040805162461bcd60e51b8152602060048201526013602482015272496e646578206f7574206f6620626f756e647360681b604482015290519081900360640190fd5b6040805160208082018790528251808303820181528284018085528151919092012060008054632dfcbaaf60e11b909352878201604485015260648401879052935190936001600160a01b0390921692635bf9755e92608480830193919282900301818387803b15801561067857600080fd5b505af115801561068c573d6000803e3d6000fd5b505050505050505050565b600154600254600354604080516024808201879052825180830382018152604492830184526020810180516001600160e01b03166303d4197f60e41b17815293516392b2c33560e01b8152600481018781526001600160a01b03968716938201849052606094820194855282516064830152825196909816976392b2c335979693959294909390926084909201919080838360005b8381101561074457818101518382015260200161072c565b50505050905090810190601f1680156107715780820380516001836020036101000a031916815260200191505b50945050505050600060405180830381600087803b15801561079257600080fd5b505af11580156107a6573d6000803e3d6000fd5b5050505050565b6000805460408051631106aeeb60e21b81526004810185905290516001600160a01b039092169163441abbac91602480820192602092909190829003018186803b1580156107fa57600080fd5b505afa15801561080e573d6000803e3d6000fd5b505050506040513d602081101561082457600080fd5b505192915050565b61083860045482610bdc565b50565b60008054604080516357bc2ef360e01b81526004810185905290516060936001600160a01b03909316926357bc2ef39260248082019391829003018186803b15801561088657600080fd5b505afa15801561089a573d6000803e3d6000fd5b505050506040513d6000823e601f3d908101601f1916820160405260208110156108c357600080fd5b81019080805160405193929190846401000000008211156108e357600080fd5b9083019060208201858111156108f857600080fd5b825164010000000081118282018810171561091257600080fd5b82525081516020918201929091019080838360005b8381101561093f578181015183820152602001610927565b50505050905090810190601f16801561096c5780820380516001836020036101000a031916815260200191505b506040525050509050919050565b600154600254600354604080516004808252602480830184526020830180516001600160e01b03166370e5872960e11b1781529351632388b54d60e21b81529182018681526001600160a01b039586169183018290526060604484019081528451606485015284516000999790971697638e22d5349790969395949293919260840191908083838d5b83811015610a1b578181015183820152602001610a03565b50505050905090810190601f168015610a485780820380516001836020036101000a031916815260200191505b5094505050505060206040518083038186803b158015610a6757600080fd5b505afa158015610a7b573d6000803e3d6000fd5b505050506040513d6020811015610a9157600080fd5b5051905080821115610bc5576001546002546003546040805160248082018890526044808301889052835180840382018152606493840185526020810180516001600160e01b0316634e1efd7b60e11b17815294516392b2c33560e01b8152600481018881526001600160a01b039788169482018590526060938201938452825195820195909552815196909816976392b2c335979693959194936084909101919080838360005b83811015610b51578181015183820152602001610b39565b50505050905090810190601f168015610b7e5780820380516001836020036101000a031916815260200191505b50945050505050600060405180830381600087803b158015610b9f57600080fd5b505af1158015610bb3573d6000803e3d6000fd5b50505050610bc08161082c565b610bd8565b610bcf6001610697565b610bd88261082c565b5050565b6000805460408051632dfcbaaf60e11b8152600481018690526024810185905290516001600160a01b0390921692635bf9755e9260448084019382900301818387803b158015610c2b57600080fd5b505af1925050508015610c3c575060015b610bd857610c48611594565b80610c535750610cd8565b8060405162461bcd60e51b81526004018080602001828103825283818151815260200191508051906020019080838360005b83811015610c9d578181015183820152602001610c85565b50505050905090810190601f168015610cca5780820380516001836020036101000a031916815260200191505b509250505060405180910390fd5b3d808015610d02576040519150601f19603f3d011682016040523d82523d6000602084013e610d07565b606091505b5060405162461bcd60e51b8152602060048201818152835160248401528351849391928392604401919085019080838360008315610c9d578181015183820152602001610c85565b6040805160208082018590528183018490528251808303840181526060830180855281519183019190912060008054631106aeeb60e21b90935260648501829052945190936001600160a01b039092169263441abbac9260848082019391829003018186803b158015610dc157600080fd5b505afa158015610dd5573d6000803e3d6000fd5b505050506040513d6020811015610deb57600080fd5b5051949350505050565b6000805460408051631106aeeb60e21b81526004810185905290516001600160a01b039092169163441abbac91602480820192602092909190829003018186803b158015610e4257600080fd5b505afa158015610e56573d6000803e3d6000fd5b505050506040513d6020811015610e6c57600080fd5b5051600114610e7c576000610e7f565b60015b92915050565b6000805460408051631106aeeb60e21b81526004810186905290516001600160a01b039092169163441abbac91602480820192602092909190829003018186803b158015610ed257600080fd5b505afa158015610ee6573d6000803e3d6000fd5b505050506040513d6020811015610efc57600080fd5b50516040805160208181018790528251808303820181528284018085528151919092012060008054632dfcbaaf60e11b9093528186016044850152606484018890529351949550936001600160a01b0390911692635bf9755e926084808201939182900301818387803b158015610f7257600080fd5b505af1158015610f86573d6000803e3d6000fd5b50506000805460408051632dfcbaaf60e11b8152600481018a905260018801602482015290516001600160a01b039092169450635bf9755e9350604480820193929182900301818387803b158015610fdd57600080fd5b505af1158015610ff1573d6000803e3d6000fd5b5050505050505050565b60006110086004546107ad565b90505b90565b600154600254600354604080516004808252602480830184526020830180516001600160e01b03166370e5872960e11b1781529351632388b54d60e21b81529182018681526001600160a01b039586169183018290526060604484019081528451606485015284516000999790971697638e22d5349790969395949293919260840191908083838d5b838110156110af578181015183820152602001611097565b50505050905090810190601f1680156110dc5780820380516001836020036101000a031916815260200191505b5094505050505060206040518083038186803b1580156110fb57600080fd5b505afa15801561110f573d6000803e3d6000fd5b505050506040513d602081101561112557600080fd5b50516004549091506108389082610bdc565b6000805460408051631106aeeb60e21b81526004810185905290516001600160a01b039092169163441abbac91602480820192602092909190829003018186803b15801561118457600080fd5b505afa158015611198573d6000803e3d6000fd5b505050506040513d60208110156111ae57600080fd5b5051905080611204576040805162461bcd60e51b815260206004820152601e60248201527f506f702063616c6c6564206f6e7a65726f206c656e6774682061727261790000604482015290519081900360640190fd5b6040805160208082018590528251808303820181528284018085528151919092012060008054632dfcbaaf60e11b909352858201604485015260648401819052935190936001600160a01b0390921692635bf9755e92608480830193919282900301818387803b15801561127757600080fd5b505af115801561128b573d6000803e3d6000fd5b50506000805460408051632dfcbaaf60e11b8152600481018990526000198801602482015290516001600160a01b039092169450635bf9755e9350604480820193929182900301818387803b15801561051457600080fd5b6000546001600160a01b0316635bf9755e8383611301576000611304565b60015b6040518363ffffffff1660e01b8152600401808381526020018260ff16815260200192505050600060405180830381600087803b15801561134457600080fd5b505af1158015611358573d6000803e3d6000fd5b505050505050565b6000805460408051631106aeeb60e21b815260048101869052905183926001600160a01b03169163441abbac916024808301926020929190829003018186803b1580156113ac57600080fd5b505afa1580156113c0573d6000803e3d6000fd5b505050506040513d60208110156113d657600080fd5b50519050828111611424576040805162461bcd60e51b8152602060048201526013602482015272496e646578206f7574206f6620626f756e647360681b604482015290519081900360640190fd5b604080516020808201879052825180830382018152828401808552815191830191909120600054631106aeeb60e21b909252878101604485015293516001600160a01b039091169263441abbac926064808301939192829003018186803b15801561148e57600080fd5b505afa1580156114a2573d6000803e3d6000fd5b505050506040513d60208110156114b857600080fd5b505195945050505050565b6000805460408051632dfcbaaf60e11b8152600481018690526001600160a01b03858116602483015291519190921692635bf9755e926044808201939182900301818387803b15801561134457600080fd5b604080516020808201869052818301859052825180830384018152606083018085528151919092012060008054632dfcbaaf60e11b9093526064840182905260848401869052935190936001600160a01b0390921692635bf9755e9260a480830193919282900301818387803b158015610fdd57600080fd5b60e01c90565b600060443d10156115a45761100b565b600481823e6308c379a06115b8825161158e565b146115c25761100b565b6040513d600319016004823e80513d67ffffffffffffffff81602484011181841117156115f2575050505061100b565b8284019150815192508083111561160c575050505061100b565b503d830160208383010111156116245750505061100b565b601f91909101601f191681016020016040529150509056fea2646970667358221220baa368d363edf09d5a5ccbf0c893641008cdd6abb824a4bc0c18386129c3b78e64736f6c634300060b0033";

    public static final String FUNC_GETADDRESS = "getAddress";

    public static final String FUNC_GETARRAYLENGTH = "getArrayLength";

    public static final String FUNC_GETARRAYVALUE = "getArrayValue";

    public static final String FUNC_GETBOOL = "getBool";

    public static final String FUNC_GETBYTES = "getBytes";

    public static final String FUNC_GETLOCALVAL = "getLocalVal";

    public static final String FUNC_GETMAPVALUE = "getMapValue";

    public static final String FUNC_GETREMOTEVAL = "getRemoteVal";

    public static final String FUNC_GETUINT256 = "getUint256";

    public static final String FUNC_POPARRAYVALUE = "popArrayValue";

    public static final String FUNC_PUSHARRAYVALUE = "pushArrayValue";

    public static final String FUNC_SETADDRESS = "setAddress";

    public static final String FUNC_SETARRAYVALUE = "setArrayValue";

    public static final String FUNC_SETBOOL = "setBool";

    public static final String FUNC_SETBYTES = "setBytes";

    public static final String FUNC_SETLOCALVAL = "setLocalVal";

    public static final String FUNC_SETMAPVALUE = "setMapValue";

    public static final String FUNC_SETUINT256 = "setUint256";

    public static final String FUNC_SETVALREMOTE = "setValRemote";

    public static final String FUNC_SOMECOMPLEXBUSINESSLOGIC = "someComplexBusinessLogic";

    public static final String FUNC_STORAGECONTRACT = "storageContract";

    @Deprecated
    protected RootBlockchainContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected RootBlockchainContract(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected RootBlockchainContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected RootBlockchainContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
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

    public RemoteFunctionCall<BigInteger> getLocalVal() {
        final Function function = new Function(FUNC_GETLOCALVAL, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public String getRLP_getLocalVal() {
        final Function function = new Function(
                FUNC_GETLOCALVAL, 
                Arrays.<Type>asList(), 
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

    public RemoteFunctionCall<TransactionReceipt> getRemoteVal() {
        final Function function = new Function(
                FUNC_GETREMOTEVAL, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getRLP_getRemoteVal() {
        final Function function = new Function(
                FUNC_GETREMOTEVAL, 
                Arrays.<Type>asList(), 
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

    public RemoteFunctionCall<TransactionReceipt> setLocalVal(BigInteger _val) {
        final Function function = new Function(
                FUNC_SETLOCALVAL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_val)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getRLP_setLocalVal(BigInteger _val) {
        final Function function = new Function(
                FUNC_SETLOCALVAL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_val)), 
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

    public RemoteFunctionCall<TransactionReceipt> setValRemote(BigInteger _val) {
        final Function function = new Function(
                FUNC_SETVALREMOTE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_val)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getRLP_setValRemote(BigInteger _val) {
        final Function function = new Function(
                FUNC_SETVALREMOTE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_val)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<TransactionReceipt> someComplexBusinessLogic(BigInteger _val) {
        final Function function = new Function(
                FUNC_SOMECOMPLEXBUSINESSLOGIC, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_val)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getRLP_someComplexBusinessLogic(BigInteger _val) {
        final Function function = new Function(
                FUNC_SOMECOMPLEXBUSINESSLOGIC, 
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
    public static RootBlockchainContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new RootBlockchainContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static RootBlockchainContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new RootBlockchainContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static RootBlockchainContract load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new RootBlockchainContract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static RootBlockchainContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new RootBlockchainContract(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<RootBlockchainContract> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _crossBlockchainControl, BigInteger _otherBlockchainId, String _otherContract, String _storageContract) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _crossBlockchainControl), 
                new org.web3j.abi.datatypes.generated.Uint256(_otherBlockchainId), 
                new org.web3j.abi.datatypes.Address(160, _otherContract), 
                new org.web3j.abi.datatypes.Address(160, _storageContract)));
        return deployRemoteCall(RootBlockchainContract.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<RootBlockchainContract> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _crossBlockchainControl, BigInteger _otherBlockchainId, String _otherContract, String _storageContract) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _crossBlockchainControl), 
                new org.web3j.abi.datatypes.generated.Uint256(_otherBlockchainId), 
                new org.web3j.abi.datatypes.Address(160, _otherContract), 
                new org.web3j.abi.datatypes.Address(160, _storageContract)));
        return deployRemoteCall(RootBlockchainContract.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<RootBlockchainContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _crossBlockchainControl, BigInteger _otherBlockchainId, String _otherContract, String _storageContract) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _crossBlockchainControl), 
                new org.web3j.abi.datatypes.generated.Uint256(_otherBlockchainId), 
                new org.web3j.abi.datatypes.Address(160, _otherContract), 
                new org.web3j.abi.datatypes.Address(160, _storageContract)));
        return deployRemoteCall(RootBlockchainContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<RootBlockchainContract> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _crossBlockchainControl, BigInteger _otherBlockchainId, String _otherContract, String _storageContract) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _crossBlockchainControl), 
                new org.web3j.abi.datatypes.generated.Uint256(_otherBlockchainId), 
                new org.web3j.abi.datatypes.Address(160, _otherContract), 
                new org.web3j.abi.datatypes.Address(160, _storageContract)));
        return deployRemoteCall(RootBlockchainContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }
}
