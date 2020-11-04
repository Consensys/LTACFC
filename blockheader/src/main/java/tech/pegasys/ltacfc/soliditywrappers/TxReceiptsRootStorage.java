package tech.pegasys.ltacfc.soliditywrappers;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
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
public class TxReceiptsRootStorage extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b50604051610a61380380610a6183398101604081905261002f916100bd565b600060208190527f67be87c3ff9960ca1e9cfac5cab2ff4747269cf9ed20c9b7306235ac35a491c58054600160ff19918216811790925581546001600160a01b0319166001600160a01b0394909416939093178155636e5ffbcd60e11b9091527fd67af1d5b224d175be333c2e82e9bb6a30eddd027ae07c15df08c27063924db380549092161790556100eb565b6000602082840312156100ce578081fd5b81516001600160a01b03811681146100e4578182fd5b9392505050565b610967806100fa6000396000f3fe608060405234801561001057600080fd5b506004361061004c5760003560e01c806301ffc9a7146100515780630dc7bade1461007a578063674f78ae1461008d578063b63735ea146100a2575b600080fd5b61006461005f36600461041b565b6100b5565b60405161007191906106eb565b60405180910390f35b610064610088366004610539565b6100d4565b6100a061009b366004610443565b61025f565b005b6100646100b0366004610518565b610344565b6001600160e01b03191660009081526020819052604090205460ff1690565b60008881526002602090815260408083208a845290915281205460ff166101165760405162461bcd60e51b815260040161010d90610742565b60405180910390fd5b8184146101355760405162461bcd60e51b815260040161010d9061079f565b600087876040516101479291906106db565b6040518091039020905060005b8381101561022f5760006101ce86868481811061016d57fe5b905060200281019061017f91906108ec565b8080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152508c92508b91508690508181106101c257fe5b90506020020135610364565b90508281146101ef5760405162461bcd60e51b815260040161010d906106f6565b8585838181106101fb57fe5b905060200281019061020d91906108ec565b60405161021b9291906106db565b604051908190039020925050600101610154565b5080891461024f5760405162461bcd60e51b815260040161010d906107f0565b5060019998505050505050505050565b60608160405160200161027291906106d2565b60408051601f198184030181529082905260015463ea13ec8b60e01b83529092506001600160a01b03169063ea13ec8b906102c3908e908e908e908e908e908e908e908e908e908d90600401610837565b60206040518083038186803b1580156102db57600080fd5b505afa1580156102ef573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525081019061031391906103f4565b50506000998a52600260209081526040808c20928c52919052909820805460ff191660011790555050505050505050565b600091825260026020908152604080842092845291905290205460ff1690565b60008060005b60208110156103a35780600802858286018151811061038557fe5b01602001516001600160f81b031916901c919091179060010161036a565b509392505050565b60008083601f8401126103bc578182fd5b50813567ffffffffffffffff8111156103d3578182fd5b60208301915083602080830285010111156103ed57600080fd5b9250929050565b600060208284031215610405578081fd5b81518015158114610414578182fd5b9392505050565b60006020828403121561042c578081fd5b81356001600160e01b031981168114610414578182fd5b60008060008060008060008060008060c08b8d031215610461578586fd5b8a35995060208b013567ffffffffffffffff8082111561047f578788fd5b61048b8e838f016103ab565b909b50995060408d01359150808211156104a3578788fd5b6104af8e838f016103ab565b909950975060608d01359150808211156104c7578687fd5b6104d38e838f016103ab565b909750955060808d01359150808211156104eb578485fd5b506104f88d828e016103ab565b9150809450508092505060a08b013590509295989b9194979a5092959850565b6000806040838503121561052a578182fd5b50508035926020909101359150565b60008060008060008060008060a0898b031215610554578384fd5b8835975060208901359650604089013567ffffffffffffffff80821115610579578586fd5b818b0191508b601f83011261058c578586fd5b81358181111561059a578687fd5b8c60208285010111156105ab578687fd5b6020830198508097505060608b01359150808211156105c8578586fd5b6105d48c838d016103ab565b909650945060808b01359150808211156105ec578384fd5b506105f98b828c016103ab565b999c989b5096995094979396929594505050565b81835260006001600160fb1b03831115610625578081fd5b6020830280836020870137939093016020019283525090919050565b60008284526020808501945082825b8581101561067c57813560ff8116808214610669578586fd5b8852509582019590820190600101610650565b509495945050505050565b60008151808452815b818110156106ac57602081850181015186830182015201610690565b818111156106bd5782602083870101525b50601f01601f19169290920160200192915050565b90815260200190565b6000828483379101908152919050565b901515815260200190565b6020808252602c908201527f43616e646964617465204861736820646964206e6f74206d617463682063616c60408201526b0c6ead8c2e8cac840d0c2e6d60a31b606082015260800190565b60208082526039908201527f5472616e73616374696f6e207265636569707420726f6f7420646f6573206e6f60408201527f7420657869737420666f7220626c6f636b636861696e20696400000000000000606082015260800190565b60208082526031908201527f4c656e677468206f662070726f6f667320616e642070726f6f66734f666673656040820152700e8e640c8decae640dcdee840dac2e8c6d607b1b606082015260800190565b60208082526027908201527f526f6f74204861736820646964206e6f74206d617463682063616c63756c61746040820152660cac840d0c2e6d60cb1b606082015260800190565b8a815260c060208083018290529082018a90526000908b9060e08401835b8d8110156108875783356001600160a01b038116808214610874578687fd5b8352509282019290820190600101610855565b50848103604086015261089b818c8e61060d565b9250505082810360608401526108b281888a61060d565b905082810360808401526108c7818688610641565b905082810360a08401526108db8185610687565b9d9c50505050505050505050505050565b6000808335601e19843603018112610902578283fd5b83018035915067ffffffffffffffff82111561091c578283fd5b6020019150368190038213156103ed57600080fdfea2646970667358221220982fb05e322dd22cf35574a4337dafc2164192040b1381973eec0324ca34f50564736f6c63430007040033";

    public static final String FUNC_ADDTXRECEIPTROOT = "addTxReceiptRoot";

    public static final String FUNC_CONTAINSTXRECEIPTROOT = "containsTxReceiptRoot";

    public static final String FUNC_SUPPORTSINTERFACE = "supportsInterface";

    public static final String FUNC_VERIFY = "verify";

    @Deprecated
    protected TxReceiptsRootStorage(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected TxReceiptsRootStorage(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected TxReceiptsRootStorage(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected TxReceiptsRootStorage(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<TransactionReceipt> addTxReceiptRoot(BigInteger _blockchainId, List<String> _signers, List<byte[]> _sigR, List<byte[]> _sigS, List<BigInteger> _sigV, byte[] _txReceiptsRoot) {
        final Function function = new Function(
                FUNC_ADDTXRECEIPTROOT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_blockchainId), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(_signers, org.web3j.abi.datatypes.Address.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Bytes32>(
                        org.web3j.abi.datatypes.generated.Bytes32.class,
                        org.web3j.abi.Utils.typeMap(_sigR, org.web3j.abi.datatypes.generated.Bytes32.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Bytes32>(
                        org.web3j.abi.datatypes.generated.Bytes32.class,
                        org.web3j.abi.Utils.typeMap(_sigS, org.web3j.abi.datatypes.generated.Bytes32.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint8>(
                        org.web3j.abi.datatypes.generated.Uint8.class,
                        org.web3j.abi.Utils.typeMap(_sigV, org.web3j.abi.datatypes.generated.Uint8.class)), 
                new org.web3j.abi.datatypes.generated.Bytes32(_txReceiptsRoot)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getRLP_addTxReceiptRoot(BigInteger _blockchainId, List<String> _signers, List<byte[]> _sigR, List<byte[]> _sigS, List<BigInteger> _sigV, byte[] _txReceiptsRoot) {
        final Function function = new Function(
                FUNC_ADDTXRECEIPTROOT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_blockchainId), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(_signers, org.web3j.abi.datatypes.Address.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Bytes32>(
                        org.web3j.abi.datatypes.generated.Bytes32.class,
                        org.web3j.abi.Utils.typeMap(_sigR, org.web3j.abi.datatypes.generated.Bytes32.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Bytes32>(
                        org.web3j.abi.datatypes.generated.Bytes32.class,
                        org.web3j.abi.Utils.typeMap(_sigS, org.web3j.abi.datatypes.generated.Bytes32.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint8>(
                        org.web3j.abi.datatypes.generated.Uint8.class,
                        org.web3j.abi.Utils.typeMap(_sigV, org.web3j.abi.datatypes.generated.Uint8.class)), 
                new org.web3j.abi.datatypes.generated.Bytes32(_txReceiptsRoot)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<Boolean> containsTxReceiptRoot(BigInteger _blockchainId, byte[] _txReceiptsRoot) {
        final Function function = new Function(FUNC_CONTAINSTXRECEIPTROOT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_blockchainId), 
                new org.web3j.abi.datatypes.generated.Bytes32(_txReceiptsRoot)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public String getRLP_containsTxReceiptRoot(BigInteger _blockchainId, byte[] _txReceiptsRoot) {
        final Function function = new Function(
                FUNC_CONTAINSTXRECEIPTROOT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_blockchainId), 
                new org.web3j.abi.datatypes.generated.Bytes32(_txReceiptsRoot)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<Boolean> supportsInterface(byte[] interfaceID) {
        final Function function = new Function(FUNC_SUPPORTSINTERFACE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes4(interfaceID)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public String getRLP_supportsInterface(byte[] interfaceID) {
        final Function function = new Function(
                FUNC_SUPPORTSINTERFACE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes4(interfaceID)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<Boolean> verify(BigInteger _blockchainId, byte[] _txReceiptsRoot, byte[] _txReceipt, List<BigInteger> _proofOffsets, List<byte[]> _proof) {
        final Function function = new Function(FUNC_VERIFY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_blockchainId), 
                new org.web3j.abi.datatypes.generated.Bytes32(_txReceiptsRoot), 
                new org.web3j.abi.datatypes.DynamicBytes(_txReceipt), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.datatypes.generated.Uint256.class,
                        org.web3j.abi.Utils.typeMap(_proofOffsets, org.web3j.abi.datatypes.generated.Uint256.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.DynamicBytes>(
                        org.web3j.abi.datatypes.DynamicBytes.class,
                        org.web3j.abi.Utils.typeMap(_proof, org.web3j.abi.datatypes.DynamicBytes.class))), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public String getRLP_verify(BigInteger _blockchainId, byte[] _txReceiptsRoot, byte[] _txReceipt, List<BigInteger> _proofOffsets, List<byte[]> _proof) {
        final Function function = new Function(
                FUNC_VERIFY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_blockchainId), 
                new org.web3j.abi.datatypes.generated.Bytes32(_txReceiptsRoot), 
                new org.web3j.abi.datatypes.DynamicBytes(_txReceipt), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.datatypes.generated.Uint256.class,
                        org.web3j.abi.Utils.typeMap(_proofOffsets, org.web3j.abi.datatypes.generated.Uint256.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.DynamicBytes>(
                        org.web3j.abi.datatypes.DynamicBytes.class,
                        org.web3j.abi.Utils.typeMap(_proof, org.web3j.abi.datatypes.DynamicBytes.class))), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    @Deprecated
    public static TxReceiptsRootStorage load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new TxReceiptsRootStorage(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static TxReceiptsRootStorage load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new TxReceiptsRootStorage(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static TxReceiptsRootStorage load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new TxReceiptsRootStorage(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static TxReceiptsRootStorage load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new TxReceiptsRootStorage(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<TxReceiptsRootStorage> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _registrar) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _registrar)));
        return deployRemoteCall(TxReceiptsRootStorage.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<TxReceiptsRootStorage> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _registrar) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _registrar)));
        return deployRemoteCall(TxReceiptsRootStorage.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<TxReceiptsRootStorage> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _registrar) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _registrar)));
        return deployRemoteCall(TxReceiptsRootStorage.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<TxReceiptsRootStorage> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _registrar) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _registrar)));
        return deployRemoteCall(TxReceiptsRootStorage.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }
}
