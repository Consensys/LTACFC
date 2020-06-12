package tech.pegasys.ltacfc.soliditywrappers;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
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
 * <p>Generated with web3j version 4.5.16.
 */
@SuppressWarnings("rawtypes")
public class Registrar extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b506001805480820182557fb10e2d527612073b26eecdfd717e6a320cf44b4afac2b0732d9fcbe2b7fa0cf60180546001600160a01b031916339081179091556000908152602081905260409020556105be8061006d6000396000f3fe608060405234801561001057600080fd5b50600436106100b45760003560e01c80637ca26a2b116100715780637ca26a2b146101a6578063819e7246146101cd578063a6847cad14610154578063aa6dc926146101f0578063cb686ddd14610154578063ea13ec8b1461020a576100b4565b8063111fd88b146100b95780631785f53c146100f257806324d7806c1461011a57806324e8cdc71461015457806357db093a146101545780637048027514610180575b600080fd5b6100d6600480360360208110156100cf57600080fd5b50356103bf565b604080516001600160a01b039092168252519081900360200190f35b6101186004803603602081101561010857600080fd5b50356001600160a01b03166103e9565b005b6101406004803603602081101561013057600080fd5b50356001600160a01b0316610484565b604080519115158252519081900360200190f35b6101186004803603604081101561016a57600080fd5b50803590602001356001600160a01b03166104a1565b6101186004803603602081101561019657600080fd5b50356001600160a01b03166104a5565b610118600480360360408110156101bc57600080fd5b508035906020013561ffff166104a1565b610118600480360360408110156101e357600080fd5b50803590602001356104a1565b6101f8610575565b60408051918252519081900360200190f35b610118600480360360c081101561022057600080fd5b81359190810190604081016020820135600160201b81111561024157600080fd5b82018360208201111561025357600080fd5b803590602001918460208302840111600160201b8311171561027457600080fd5b919390929091602081019035600160201b81111561029157600080fd5b8201836020820111156102a357600080fd5b803590602001918460208302840111600160201b831117156102c457600080fd5b919390929091602081019035600160201b8111156102e157600080fd5b8201836020820111156102f357600080fd5b803590602001918460208302840111600160201b8311171561031457600080fd5b919390929091602081019035600160201b81111561033157600080fd5b82018360208201111561034357600080fd5b803590602001918460208302840111600160201b8311171561036457600080fd5b919390929091602081019035600160201b81111561038157600080fd5b82018360208201111561039357600080fd5b803590602001918460018302840111600160201b831117156103b457600080fd5b50909250905061057b565b6000600182815481106103ce57fe5b6000918252602090912001546001600160a01b031692915050565b3360009081526020819052604090205461040257600080fd5b6001600160a01b0381166000908152602081905260409020548061042557600080fd5b6001600160a01b038216600090815260208190526040812081905560018054600019840190811061045257fe5b9060005260206000200160006101000a8154816001600160a01b0302191690836001600160a01b031602179055505050565b6001600160a01b0316600090815260208190526040902054151590565b5050565b336000908152602081905260409020546104be57600080fd5b6001600160a01b0381166000908152602081905260409020541561051a576040805162461bcd60e51b815260206004820152600e60248201526d22bc34b9ba34b7339020b236b4b760911b604482015290519081900360640190fd5b6001805480820182557fb10e2d527612073b26eecdfd717e6a320cf44b4afac2b0732d9fcbe2b7fa0cf60180546001600160a01b039093166001600160a01b0319909316831790555460009182526020829052604090912055565b60015490565b505050505050505050505056fea2646970667358221220ae773843199bf93776ed7c3105f0c7049f821723cec1d91c79a88d05fc124faa64736f6c63430006090033";

    public static final String FUNC_ADDADMIN = "addAdmin";

    public static final String FUNC_ADDBLOCKCHAIN = "addBlockchain";

    public static final String FUNC_ADDBLOCKCHAINADMIN = "addBlockchainAdmin";

    public static final String FUNC_ADDSIGNERPUBLICKEYADDRESS = "addSignerPublicKeyAddress";

    public static final String FUNC_ADMINARRAYSIZE = "adminArraySize";

    public static final String FUNC_GETADMIN = "getAdmin";

    public static final String FUNC_ISADMIN = "isAdmin";

    public static final String FUNC_REMOVEADMIN = "removeAdmin";

    public static final String FUNC_REMOVEBLOCKCHAINADMIN = "removeBlockchainAdmin";

    public static final String FUNC_REMOVESIGNERPUBLICKEYADDRESS = "removeSignerPublicKeyAddress";

    public static final String FUNC_SETSIGNATURETHRESHOLD = "setSignatureThreshold";

    public static final String FUNC_VERIFY = "verify";

    @Deprecated
    protected Registrar(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Registrar(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Registrar(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Registrar(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<TransactionReceipt> addAdmin(String _admin) {
        final Function function = new Function(
                FUNC_ADDADMIN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _admin)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> addBlockchain(BigInteger _blockchainId, BigInteger _signingAlgorithm) {
        final Function function = new Function(
                FUNC_ADDBLOCKCHAIN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_blockchainId), 
                new org.web3j.abi.datatypes.generated.Uint16(_signingAlgorithm)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> addBlockchainAdmin(BigInteger _blockchainId, String _admin) {
        final Function function = new Function(
                FUNC_ADDBLOCKCHAINADMIN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_blockchainId), 
                new org.web3j.abi.datatypes.Address(160, _admin)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> addSignerPublicKeyAddress(BigInteger _blockchainId, String _signerPublicKeyAddress) {
        final Function function = new Function(
                FUNC_ADDSIGNERPUBLICKEYADDRESS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_blockchainId), 
                new org.web3j.abi.datatypes.Address(160, _signerPublicKeyAddress)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> adminArraySize() {
        final Function function = new Function(FUNC_ADMINARRAYSIZE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> getAdmin(BigInteger _index) {
        final Function function = new Function(FUNC_GETADMIN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_index)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<Boolean> isAdmin(String _mightBeAdmin) {
        final Function function = new Function(FUNC_ISADMIN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _mightBeAdmin)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<TransactionReceipt> removeAdmin(String _admin) {
        final Function function = new Function(
                FUNC_REMOVEADMIN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _admin)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> removeBlockchainAdmin(BigInteger _blockchainId, String _admin) {
        final Function function = new Function(
                FUNC_REMOVEBLOCKCHAINADMIN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_blockchainId), 
                new org.web3j.abi.datatypes.Address(160, _admin)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> removeSignerPublicKeyAddress(BigInteger _blockchainId, String _signerPublicKeyAddress) {
        final Function function = new Function(
                FUNC_REMOVESIGNERPUBLICKEYADDRESS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_blockchainId), 
                new org.web3j.abi.datatypes.Address(160, _signerPublicKeyAddress)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setSignatureThreshold(BigInteger _blockchainId, BigInteger _threshold) {
        final Function function = new Function(
                FUNC_SETSIGNATURETHRESHOLD, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_blockchainId), 
                new org.web3j.abi.datatypes.generated.Uint256(_threshold)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> verify(BigInteger _blockchainId, List<String> signers, List<byte[]> sigR, List<byte[]> sigS, List<BigInteger> sigV, byte[] plainText) {
        final Function function = new Function(
                FUNC_VERIFY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_blockchainId), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(signers, org.web3j.abi.datatypes.Address.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Bytes32>(
                        org.web3j.abi.datatypes.generated.Bytes32.class,
                        org.web3j.abi.Utils.typeMap(sigR, org.web3j.abi.datatypes.generated.Bytes32.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Bytes32>(
                        org.web3j.abi.datatypes.generated.Bytes32.class,
                        org.web3j.abi.Utils.typeMap(sigS, org.web3j.abi.datatypes.generated.Bytes32.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint8>(
                        org.web3j.abi.datatypes.generated.Uint8.class,
                        org.web3j.abi.Utils.typeMap(sigV, org.web3j.abi.datatypes.generated.Uint8.class)), 
                new org.web3j.abi.datatypes.DynamicBytes(plainText)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static Registrar load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Registrar(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Registrar load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Registrar(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Registrar load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Registrar(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Registrar load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Registrar(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Registrar> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Registrar.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<Registrar> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Registrar.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Registrar> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Registrar.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Registrar> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Registrar.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }
}
