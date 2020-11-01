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
    public static final String BINARY = "608060405234801561001057600080fd5b506040516117243803806117248339818101604052608081101561003357600080fd5b50805160208201516040830151606090930151600080546001600160a01b039283166001600160a01b03199182161790915560018054948316948216949094179093556002919091556003805491909316911617905561168c806100986000396000f3fe608060405234801561001057600080fd5b50600436106101425760003560e01c806382bc854b116100b8578063b51332311161007c578063b51332311461041e578063b52b657d1461043b578063b93f9b0a14610458578063cb8b383b1461046e578063d980192514610476578063e2b6f3f31461047e57610142565b806382bc854b14610385578063891276e11461038d57806397cedc76146103aa5780639cc51b23146103cf578063ac4ce2c6146103f257610142565b806357bc2ef31161010a57806357bc2ef31461023c57806358033de1146102ce5780635bf9755e146102eb57806361d0f54f1461030e57806363435466146103315780636716b0ec1461036257610142565b8063011827fd1461014757806311ce0267146101c05780632f8d2984146101e45780633e06e6211461020d578063441abbac1461020d575b600080fd5b6101be6004803603604081101561015d57600080fd5b8135919081019060408101602082013564010000000081111561017f57600080fd5b82018360208201111561019157600080fd5b803590602001918460018302840111640100000000831117156101b357600080fd5b5090925090506104a7565b005b6101c8610544565b604080516001600160a01b039092168252519081900360200190f35b6101be600480360360608110156101fa57600080fd5b5080359060208101359060400135610553565b61022a6004803603602081101561022357600080fd5b50356106aa565b60408051918252519081900360200190f35b6102596004803603602081101561025257600080fd5b5035610729565b6040805160208082528351818301528351919283929083019185019080838360005b8381101561029357818101518382015260200161027b565b50505050905090810190601f1680156102c05780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b6101be600480360360208110156102e457600080fd5b5035610868565b6101be6004803603604081101561030157600080fd5b5080359060200135610be5565b61022a6004803603604081101561032457600080fd5b5080359060200135610d58565b61034e6004803603602081101561034757600080fd5b5035610dfe565b604080519115158252519081900360200190f35b6101be6004803603604081101561037857600080fd5b5080359060200135610e8e565b6101be611004565b6101be600480360360208110156103a357600080fd5b503561111d565b6101be600480360360408110156103c057600080fd5b508035906020013515156112c9565b61022a600480360360408110156103e557600080fd5b5080359060200135611346565b6101be6004803603604081101561040857600080fd5b50803590602001356001600160a01b03166114a9565b6101be6004803603602081101561043457600080fd5b50356114fb565b6101be6004803603602081101561045157600080fd5b5035611509565b6101c86004803603602081101561022357600080fd5b61022a611514565b61022a611526565b6101be6004803603606081101561049457600080fd5b5080359060208101359060400135611532565b6000546040805163011827fd60e01b81526004810186815260248201928352604482018590526001600160a01b039093169263011827fd928792879287929091606401848480828437600081840152601f19601f820116905080830192505050945050505050600060405180830381600087803b15801561052757600080fd5b505af115801561053b573d6000803e3d6000fd5b50505050505050565b6000546001600160a01b031681565b6000805460408051631106aeeb60e21b81526004810187905290516001600160a01b039092169163441abbac91602480820192602092909190829003018186803b1580156105a057600080fd5b505afa1580156105b4573d6000803e3d6000fd5b505050506040513d60208110156105ca57600080fd5b50519050808310610618576040805162461bcd60e51b8152602060048201526013602482015272496e646578206f7574206f6620626f756e647360681b604482015290519081900360640190fd5b6040805160208082018790528251808303820181528284018085528151919092012060008054632dfcbaaf60e11b909352878201604485015260648401879052935190936001600160a01b0390921692635bf9755e92608480830193919282900301818387803b15801561068b57600080fd5b505af115801561069f573d6000803e3d6000fd5b505050505050505050565b6000805460408051631106aeeb60e21b81526004810185905290516001600160a01b039092169163441abbac91602480820192602092909190829003018186803b1580156106f757600080fd5b505afa15801561070b573d6000803e3d6000fd5b505050506040513d602081101561072157600080fd5b505192915050565b60008054604080516357bc2ef360e01b81526004810185905290516060936001600160a01b03909316926357bc2ef39260248082019391829003018186803b15801561077457600080fd5b505afa158015610788573d6000803e3d6000fd5b505050506040513d6000823e601f3d908101601f1916820160405260208110156107b157600080fd5b81019080805160405193929190846401000000008211156107d157600080fd5b9083019060208201858111156107e657600080fd5b825164010000000081118282018810171561080057600080fd5b82525081516020918201929091019080838360005b8381101561082d578181015183820152602001610815565b50505050905090810190601f16801561085a5780820380516001836020036101000a031916815260200191505b506040525050509050919050565b600154600254600354604080516004808252602480830184526020830180516001600160e01b03166370e5872960e11b1781529351632388b54d60e21b81529182018681526001600160a01b039586169183018290526060604484019081528451606485015284516000999790971697638e22d5349790969395949293919260840191908083838d5b838110156109095781810151838201526020016108f1565b50505050905090810190601f1680156109365780820380516001836020036101000a031916815260200191505b50945050505050602060405180830381600087803b15801561095757600080fd5b505af115801561096b573d6000803e3d6000fd5b505050506040513d602081101561098157600080fd5b5051905061098e81611509565b80821115610abe576001546002546003546040805160248082018890526044808301889052835180840382018152606493840185526020810180516001600160e01b0316634e1efd7b60e11b17815294516392b2c33560e01b8152600481018881526001600160a01b039788169482018590526060938201938452825195820195909552815196909816976392b2c335979693959194936084909101919080838360005b83811015610a4a578181015183820152602001610a32565b50505050905090810190601f168015610a775780820380516001836020036101000a031916815260200191505b50945050505050600060405180830381600087803b158015610a9857600080fd5b505af1158015610aac573d6000803e3d6000fd5b50505050610ab9816114fb565b610be1565b60015460025460035460408051600d87016024808301829052835180840382018152604493840185526020810180516001600160e01b03166303d4197f60e41b17815294516392b2c33560e01b8152600481018881526001600160a01b039788169382018490526060958201958652825160648301528251949997909716976392b2c3359790969395929490936084909201919080838360005b83811015610b70578181015183820152602001610b58565b50505050905090810190601f168015610b9d5780820380516001836020036101000a031916815260200191505b50945050505050600060405180830381600087803b158015610bbe57600080fd5b505af1158015610bd2573d6000803e3d6000fd5b50505050610bdf836114fb565b505b5050565b6000805460408051632dfcbaaf60e11b8152600481018690526024810185905290516001600160a01b0390921692635bf9755e9260448084019382900301818387803b158015610c3457600080fd5b505af1925050508015610c45575060015b610be157610c516115b1565b80610c5c5750610ce1565b8060405162461bcd60e51b81526004018080602001828103825283818151815260200191508051906020019080838360005b83811015610ca6578181015183820152602001610c8e565b50505050905090810190601f168015610cd35780820380516001836020036101000a031916815260200191505b509250505060405180910390fd5b3d808015610d0b576040519150601f19603f3d011682016040523d82523d6000602084013e610d10565b606091505b5060405162461bcd60e51b8152602060048201818152835160248401528351849391928392604401919085019080838360008315610ca6578181015183820152602001610c8e565b6040805160208082018590528183018490528251808303840181526060830180855281519183019190912060008054631106aeeb60e21b90935260648501829052945190936001600160a01b039092169263441abbac9260848082019391829003018186803b158015610dca57600080fd5b505afa158015610dde573d6000803e3d6000fd5b505050506040513d6020811015610df457600080fd5b5051949350505050565b6000805460408051631106aeeb60e21b81526004810185905290516001600160a01b039092169163441abbac91602480820192602092909190829003018186803b158015610e4b57600080fd5b505afa158015610e5f573d6000803e3d6000fd5b505050506040513d6020811015610e7557600080fd5b5051600114610e85576000610e88565b60015b92915050565b6000805460408051631106aeeb60e21b81526004810186905290516001600160a01b039092169163441abbac91602480820192602092909190829003018186803b158015610edb57600080fd5b505afa158015610eef573d6000803e3d6000fd5b505050506040513d6020811015610f0557600080fd5b50516040805160208181018790528251808303820181528284018085528151919092012060008054632dfcbaaf60e11b9093528186016044850152606484018890529351949550936001600160a01b0390911692635bf9755e926084808201939182900301818387803b158015610f7b57600080fd5b505af1158015610f8f573d6000803e3d6000fd5b50506000805460408051632dfcbaaf60e11b8152600481018a905260018801602482015290516001600160a01b039092169450635bf9755e9350604480820193929182900301818387803b158015610fe657600080fd5b505af1158015610ffa573d6000803e3d6000fd5b5050505050505050565b600154600254600354604080516004808252602480830184526020830180516001600160e01b03166370e5872960e11b1781529351632388b54d60e21b81529182018681526001600160a01b039586169183018290526060604484019081528451606485015284516000999790971697638e22d5349790969395949293919260840191908083838d5b838110156110a557818101518382015260200161108d565b50505050905090810190601f1680156110d25780820380516001836020036101000a031916815260200191505b50945050505050602060405180830381600087803b1580156110f357600080fd5b505af1158015611107573d6000803e3d6000fd5b505050506040513d6020811015610bdf57600080fd5b6000805460408051631106aeeb60e21b81526004810185905290516001600160a01b039092169163441abbac91602480820192602092909190829003018186803b15801561116a57600080fd5b505afa15801561117e573d6000803e3d6000fd5b505050506040513d602081101561119457600080fd5b50519050806111ea576040805162461bcd60e51b815260206004820152601e60248201527f506f702063616c6c6564206f6e7a65726f206c656e6774682061727261790000604482015290519081900360640190fd5b6040805160208082018590528251808303820181528284018085528151919092012060008054632dfcbaaf60e11b909352858201604485015260648401819052935190936001600160a01b0390921692635bf9755e92608480830193919282900301818387803b15801561125d57600080fd5b505af1158015611271573d6000803e3d6000fd5b50506000805460408051632dfcbaaf60e11b8152600481018990526000198801602482015290516001600160a01b039092169450635bf9755e9350604480820193929182900301818387803b15801561052757600080fd5b6000546001600160a01b0316635bf9755e83836112e75760006112ea565b60015b6040518363ffffffff1660e01b8152600401808381526020018260ff16815260200192505050600060405180830381600087803b15801561132a57600080fd5b505af115801561133e573d6000803e3d6000fd5b505050505050565b6000805460408051631106aeeb60e21b815260048101869052905183926001600160a01b03169163441abbac916024808301926020929190829003018186803b15801561139257600080fd5b505afa1580156113a6573d6000803e3d6000fd5b505050506040513d60208110156113bc57600080fd5b5051905082811161140a576040805162461bcd60e51b8152602060048201526013602482015272496e646578206f7574206f6620626f756e647360681b604482015290519081900360640190fd5b604080516020808201879052825180830382018152828401808552815191830191909120600054631106aeeb60e21b909252878101604485015293516001600160a01b039091169263441abbac926064808301939192829003018186803b15801561147457600080fd5b505afa158015611488573d6000803e3d6000fd5b505050506040513d602081101561149e57600080fd5b505195945050505050565b6000805460408051632dfcbaaf60e11b8152600481018690526001600160a01b03858116602483015291519190921692635bf9755e926044808201939182900301818387803b15801561132a57600080fd5b611506600082610be5565b50565b611506600182610be5565b600061152060006106aa565b90505b90565b600061152060016106aa565b604080516020808201869052818301859052825180830384018152606083018085528151919092012060008054632dfcbaaf60e11b9093526064840182905260848401869052935190936001600160a01b0390921692635bf9755e9260a480830193919282900301818387803b158015610fe657600080fd5b60e01c90565b600060443d10156115c157611523565b600481823e6308c379a06115d582516115ab565b146115df57611523565b6040513d600319016004823e80513d67ffffffffffffffff816024840111818411171561160f5750505050611523565b828401925082519150808211156116295750505050611523565b503d8301602082840101111561164157505050611523565b601f01601f191681016020016040529150509056fea26469706673582212203b2a3492a4d6f9ac07f1605438b0b5bffea47f8bdb28f9f33eb3ce39ca977b6464736f6c63430007040033";

    public static final String FUNC_GETADDRESS = "getAddress";

    public static final String FUNC_GETARRAYLENGTH = "getArrayLength";

    public static final String FUNC_GETARRAYVALUE = "getArrayValue";

    public static final String FUNC_GETBOOL = "getBool";

    public static final String FUNC_GETBYTES = "getBytes";

    public static final String FUNC_GETMAPVALUE = "getMapValue";

    public static final String FUNC_GETREMOTEVAL = "getRemoteVal";

    public static final String FUNC_GETUINT256 = "getUint256";

    public static final String FUNC_GETVAL1 = "getVal1";

    public static final String FUNC_GETVAL2 = "getVal2";

    public static final String FUNC_POPARRAYVALUE = "popArrayValue";

    public static final String FUNC_PUSHARRAYVALUE = "pushArrayValue";

    public static final String FUNC_SETADDRESS = "setAddress";

    public static final String FUNC_SETARRAYVALUE = "setArrayValue";

    public static final String FUNC_SETBOOL = "setBool";

    public static final String FUNC_SETBYTES = "setBytes";

    public static final String FUNC_SETMAPVALUE = "setMapValue";

    public static final String FUNC_SETUINT256 = "setUint256";

    public static final String FUNC_SETVAL1 = "setVal1";

    public static final String FUNC_SETVAL2 = "setVal2";

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

    public RemoteFunctionCall<BigInteger> getVal1() {
        final Function function = new Function(FUNC_GETVAL1, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public String getRLP_getVal1() {
        final Function function = new Function(
                FUNC_GETVAL1, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<BigInteger> getVal2() {
        final Function function = new Function(FUNC_GETVAL2, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public String getRLP_getVal2() {
        final Function function = new Function(
                FUNC_GETVAL2, 
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

    public RemoteFunctionCall<TransactionReceipt> setVal1(BigInteger _val) {
        final Function function = new Function(
                FUNC_SETVAL1, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_val)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getRLP_setVal1(BigInteger _val) {
        final Function function = new Function(
                FUNC_SETVAL1, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_val)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setVal2(BigInteger _val) {
        final Function function = new Function(
                FUNC_SETVAL2, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_val)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getRLP_setVal2(BigInteger _val) {
        final Function function = new Function(
                FUNC_SETVAL2, 
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
