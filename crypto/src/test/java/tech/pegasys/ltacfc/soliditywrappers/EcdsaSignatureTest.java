package tech.pegasys.ltacfc.soliditywrappers;

import java.math.BigInteger;
import java.util.Arrays;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
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
public class EcdsaSignatureTest extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b5061035a806100206000396000f3fe608060405234801561001057600080fd5b50600436106100365760003560e01c8063018ea9ca1461003b578063c976588c146100d4575b600080fd5b6100c06004803603606081101561005157600080fd5b6001600160a01b038235169160208101359181019060608101604082013564010000000081111561008157600080fd5b82018360208201111561009357600080fd5b803590602001918460018302840111640100000000831117156100b557600080fd5b5090925090506101a6565b604080519115158252519081900360200190f35b6100c0600480360360608110156100ea57600080fd5b6001600160a01b03823516919081019060408101602082013564010000000081111561011557600080fd5b82018360208201111561012757600080fd5b8035906020019184600183028401116401000000008311171561014957600080fd5b91939092909160208101903564010000000081111561016757600080fd5b82018360208201111561017957600080fd5b8035906020019184600183028401116401000000008311171561019b57600080fd5b5090925090506101bd565b60006101b4858585856101f8565b95945050505050565b6000808585604051808383808284378083019250505092505050604051809103902090506101ed878286866101f8565b979650505050505050565b6000610205848484610223565b6001600160a01b0316856001600160a01b0316149050949350505050565b60008080806041851461023c576000935050505061031d565b606086868080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920182905250602085015160408601516060870151919a509850901a95509293505050601b8314801591506102a357508160ff16601c14155b156102b557600094505050505061031d565b604080516000815260208082018084528b905260ff8516828401526060820187905260808201869052915160019260a0808401939192601f1981019281900390910190855afa15801561030c573d6000803e3d6000fd5b505050602060405103519450505050505b939250505056fea26469706673582212206d7d7ae4654bb03ce5b0e9fd95386d3fa990c4c3eef9084da5f54b908b5cecb264736f6c63430006090033";

    public static final String FUNC_VERIFY1 = "verify1";

    public static final String FUNC_VERIFY2 = "verify2";

    @Deprecated
    protected EcdsaSignatureTest(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected EcdsaSignatureTest(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected EcdsaSignatureTest(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected EcdsaSignatureTest(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<Boolean> verify1(String signer, byte[] digest, byte[] signature) {
        final Function function = new Function(FUNC_VERIFY1, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, signer), 
                new org.web3j.abi.datatypes.generated.Bytes32(digest), 
                new org.web3j.abi.datatypes.DynamicBytes(signature)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<Boolean> verify2(String signer, byte[] message, byte[] signature) {
        final Function function = new Function(FUNC_VERIFY2, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, signer), 
                new org.web3j.abi.datatypes.DynamicBytes(message), 
                new org.web3j.abi.datatypes.DynamicBytes(signature)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    @Deprecated
    public static EcdsaSignatureTest load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new EcdsaSignatureTest(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static EcdsaSignatureTest load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new EcdsaSignatureTest(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static EcdsaSignatureTest load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new EcdsaSignatureTest(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static EcdsaSignatureTest load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new EcdsaSignatureTest(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<EcdsaSignatureTest> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(EcdsaSignatureTest.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<EcdsaSignatureTest> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(EcdsaSignatureTest.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<EcdsaSignatureTest> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(EcdsaSignatureTest.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<EcdsaSignatureTest> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(EcdsaSignatureTest.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }
}
