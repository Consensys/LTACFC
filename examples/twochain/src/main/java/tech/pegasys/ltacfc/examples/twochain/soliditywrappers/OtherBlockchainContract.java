package tech.pegasys.ltacfc.examples.twochain.soliditywrappers;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
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
    public static final String BINARY = "608060405234801561001057600080fd5b50604051610c06380380610c068339818101604052602081101561003357600080fd5b5051600080546001600160a01b039092166001600160a01b0319909216919091179055610ba1806100656000396000f3fe608060405234801561001057600080fd5b50600436106100f55760003560e01c80635d1bd96211610097578063da359dc811610066578063da359dc8146102ec578063e1cb0e521461035c578063f596668614610364578063f963393014610387576100f5565b80635d1bd9621461026c5780637973830a1461028957806384871ff4146102a6578063a366474f146102c9576100f5565b8063118307d7116100d3578063118307d71461019b5780631ae5af15146101c05780633927f6af146102305780633d4197f01461024f576100f5565b80630849cc99146100fa5780630bcd3b33146101145780630d7f9bde14610191575b600080fd5b6101026103a3565b60408051918252519081900360200190f35b61011c610422565b6040805160208082528351818301528351919283929083019185019080838360005b8381101561015657818101518382015260200161013e565b50505050905090810190601f1680156101835780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b61019961055f565b005b610199600480360360408110156101b157600080fd5b5080351515906020013561064c565b610199600480360360208110156101d657600080fd5b8101906020810181356401000000008111156101f157600080fd5b82018360208201111561020357600080fd5b8035906020019184602083028401116401000000008311171561022557600080fd5b509092509050610662565b6101996004803603602081101561024657600080fd5b50351515610765565b6101996004803603602081101561026557600080fd5b50356107b5565b6101026004803603602081101561028257600080fd5b5035610804565b6101026004803603602081101561029f57600080fd5b5035610886565b6102ae61092e565b60408051921515835260208301919091528051918290030190f35b610199600480360360408110156102df57600080fd5b5080359060200135610949565b6101996004803603602081101561030257600080fd5b81019060208101813564010000000081111561031d57600080fd5b82018360208201111561032f57600080fd5b8035906020019184600183028401116401000000008311171561035157600080fd5b5090925090506109b7565b610102610a38565b6101996004803603604081101561037a57600080fd5b5080359060200135610a85565b61038f610b1e565b604080519115158252519081900360200190f35b6000805460408051631106aeeb60e21b81526103e8600482015290516001600160a01b039092169163441abbac91602480820192602092909190829003018186803b1580156103f157600080fd5b505afa158015610405573d6000803e3d6000fd5b505050506040513d602081101561041b57600080fd5b5051905090565b60008054604080516357bc2ef360e01b81526002600482015290516060936001600160a01b03909316926357bc2ef39260248082019391829003018186803b15801561046d57600080fd5b505afa158015610481573d6000803e3d6000fd5b505050506040513d6000823e601f3d908101601f1916820160405260208110156104aa57600080fd5b81019080805160405193929190846401000000008211156104ca57600080fd5b9083019060208201858111156104df57600080fd5b82516401000000008111828201881017156104f957600080fd5b82525081516020918201929091019080838360005b8381101561052657818101518382015260200161050e565b50505050905090810190601f1680156105535780820380516001836020036101000a031916815260200191505b50604052505050905090565b6000805460408051631106aeeb60e21b81526001600482015290516001600160a01b039092169163441abbac91602480820192602092909190829003018186803b1580156105ac57600080fd5b505afa1580156105c0573d6000803e3d6000fd5b505050506040513d60208110156105d657600080fd5b50516000805460408051632dfcbaaf60e11b81526001600482018190529094016024850181905290519094506001600160a01b0390911692635bf9755e92604480830193919282900301818387803b15801561063157600080fd5b505af1158015610645573d6000803e3d6000fd5b5050505050565b61065582610765565b61065e816107b5565b5050565b6000805460408051632dfcbaaf60e11b81526103e860048201526024810185905290516001600160a01b0390921692635bf9755e9260448084019382900301818387803b1580156106b257600080fd5b505af11580156106c6573d6000803e3d6000fd5b506000925050505b81811015610760576000546001600160a01b0316635bf9755e6103e983018585858181106106f857fe5b905060200201356040518363ffffffff1660e01b81526004018083815260200182815260200192505050600060405180830381600087803b15801561073c57600080fd5b505af1158015610750573d6000803e3d6000fd5b5050600190920191506106ce9050565b505050565b6000805460408051634be76e3b60e11b815260048101849052841515602482015290516001600160a01b03909216926397cedc769260448084019382900301818387803b15801561063157600080fd5b6000805460408051632dfcbaaf60e11b8152600160048201526024810185905290516001600160a01b0390921692635bf9755e9260448084019382900301818387803b15801561063157600080fd5b6000805460408051631106aeeb60e21b81526103e98501600482015290516001600160a01b039092169163441abbac91602480820192602092909190829003018186803b15801561085457600080fd5b505afa158015610868573d6000803e3d6000fd5b505050506040513d602081101561087e57600080fd5b505192915050565b6040805160036020808301919091528183018490528251808303840181526060830180855281519183019190912060008054631106aeeb60e21b90935260648501829052945190936001600160a01b039092169263441abbac9260848082019391829003018186803b1580156108fb57600080fd5b505afa15801561090f573d6000803e3d6000fd5b505050506040513d602081101561092557600080fd5b50519392505050565b600080610939610b1e565b610941610a38565b915091509091565b6000805460408051632dfcbaaf60e11b81526103e9860160048201526024810185905290516001600160a01b0390921692635bf9755e9260448084019382900301818387803b15801561099b57600080fd5b505af11580156109af573d6000803e3d6000fd5b505050505050565b6000546040805163011827fd60e01b815260026004820181815260248301938452604483018690526001600160a01b039094169363011827fd9391928792879291606401848480828437600081840152601f19601f820116905080830192505050945050505050600060405180830381600087803b15801561099b57600080fd5b6000805460408051631106aeeb60e21b81526001600482015290516001600160a01b039092169163441abbac91602480820192602092909190829003018186803b1580156103f157600080fd5b604080516003602080830191909152818301859052825180830384018152606083018085528151919092012060008054632dfcbaaf60e11b9093526064840182905260848401869052935190936001600160a01b0390921692635bf9755e9260a480830193919282900301818387803b158015610b0157600080fd5b505af1158015610b15573d6000803e3d6000fd5b50505050505050565b60008054604080516331a1aa3360e11b81526004810184905290516001600160a01b0390921691636343546691602480820192602092909190829003018186803b1580156103f157600080fdfea264697066735822122056e7a5ca366e8388847d0792ce3538bcb7487ec86d65f40476d48648cf9f288564736f6c634300060b0033";

    public static final String FUNC_GETARRAYLENGTH = "getArrayLength";

    public static final String FUNC_GETARRAYVALUE = "getArrayValue";

    public static final String FUNC_GETBYTES = "getBytes";

    public static final String FUNC_GETFLAG = "getFlag";

    public static final String FUNC_GETMAPVALUE = "getMapValue";

    public static final String FUNC_GETVAL = "getVal";

    public static final String FUNC_GETVALANDFLAG = "getValAndFlag";

    public static final String FUNC_INCREMENTVAL = "incrementVal";

    public static final String FUNC_SETARRAYVALUE = "setArrayValue";

    public static final String FUNC_SETARRAYVALUES = "setArrayValues";

    public static final String FUNC_SETBYTES = "setBytes";

    public static final String FUNC_SETFLAG = "setFlag";

    public static final String FUNC_SETMAPVALUE = "setMapValue";

    public static final String FUNC_SETVAL = "setVal";

    public static final String FUNC_SETVALANDFLAG = "setValAndFlag";

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

    public RemoteFunctionCall<BigInteger> getArrayLength() {
        final Function function = new Function(FUNC_GETARRAYLENGTH, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public String getRLP_getArrayLength() {
        final Function function = new Function(
                FUNC_GETARRAYLENGTH, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<BigInteger> getArrayValue(BigInteger _index) {
        final Function function = new Function(FUNC_GETARRAYVALUE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_index)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public String getRLP_getArrayValue(BigInteger _index) {
        final Function function = new Function(
                FUNC_GETARRAYVALUE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_index)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<byte[]> getBytes() {
        final Function function = new Function(FUNC_GETBYTES, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicBytes>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public String getRLP_getBytes() {
        final Function function = new Function(
                FUNC_GETBYTES, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
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

    public RemoteFunctionCall<BigInteger> getMapValue(BigInteger _key) {
        final Function function = new Function(FUNC_GETMAPVALUE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_key)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public String getRLP_getMapValue(BigInteger _key) {
        final Function function = new Function(
                FUNC_GETMAPVALUE, 
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

    public RemoteFunctionCall<TransactionReceipt> setArrayValue(BigInteger _index, BigInteger _val) {
        final Function function = new Function(
                FUNC_SETARRAYVALUE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_index), 
                new org.web3j.abi.datatypes.generated.Uint256(_val)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getRLP_setArrayValue(BigInteger _index, BigInteger _val) {
        final Function function = new Function(
                FUNC_SETARRAYVALUE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_index), 
                new org.web3j.abi.datatypes.generated.Uint256(_val)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setArrayValues(List<BigInteger> _vals) {
        final Function function = new Function(
                FUNC_SETARRAYVALUES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.datatypes.generated.Uint256.class,
                        org.web3j.abi.Utils.typeMap(_vals, org.web3j.abi.datatypes.generated.Uint256.class))), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getRLP_setArrayValues(List<BigInteger> _vals) {
        final Function function = new Function(
                FUNC_SETARRAYVALUES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.datatypes.generated.Uint256.class,
                        org.web3j.abi.Utils.typeMap(_vals, org.web3j.abi.datatypes.generated.Uint256.class))), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setBytes(byte[] _val) {
        final Function function = new Function(
                FUNC_SETBYTES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicBytes(_val)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getRLP_setBytes(byte[] _val) {
        final Function function = new Function(
                FUNC_SETBYTES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicBytes(_val)), 
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

    public RemoteFunctionCall<TransactionReceipt> setMapValue(BigInteger _key, BigInteger _val) {
        final Function function = new Function(
                FUNC_SETMAPVALUE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_key), 
                new org.web3j.abi.datatypes.generated.Uint256(_val)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getRLP_setMapValue(BigInteger _key, BigInteger _val) {
        final Function function = new Function(
                FUNC_SETMAPVALUE, 
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
