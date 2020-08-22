package tech.pegasys.ltacfc.receipts.soliditywrappers;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
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
public class TestReceipts extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b50610bc9806100206000396000f3fe608060405234801561001057600080fd5b50600436106100625760003560e01c80630fe640261461006757806310ba2039146101955780632ae31cc0146102555780638feb676c14610195578063b401d83f14610274578063e4e2622314610328575b600080fd5b6101206004803603606081101561007d57600080fd5b6001600160a01b0382351691602081013591810190606081016040820135600160201b8111156100ac57600080fd5b8201836020820111156100be57600080fd5b803590602001918460018302840111600160201b831117156100df57600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295506103de945050505050565b6040805160208082528351818301528351919283929083019185019080838360005b8381101561015a578181015183820152602001610142565b50505050905090810190601f1680156101875780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b610239600480360360208110156101ab57600080fd5b810190602081018135600160201b8111156101c557600080fd5b8201836020820111156101d757600080fd5b803590602001918460018302840111600160201b831117156101f857600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295506103f6945050505050565b604080516001600160a01b039092168252519081900360200190f35b6102726004803603602081101561026b57600080fd5b5035610483565b005b6101206004803603604081101561028a57600080fd5b6001600160a01b038235169190810190604081016020820135600160201b8111156102b457600080fd5b8201836020820111156102c657600080fd5b803590602001918460018302840111600160201b831117156102e757600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295506104b9945050505050565b6103cc6004803603602081101561033e57600080fd5b810190602081018135600160201b81111561035857600080fd5b82018360208201111561036a57600080fd5b803590602001918460018302840111600160201b8311171561038b57600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295506104fb945050505050565b60408051918252519081900360200190f35b6060806103ec858585610528565b9695505050505050565b6000606061040b61040684610646565b610689565b9050606061042c8260038151811061041f57fe5b6020026020010151610689565b9050805160011461043c57600080fd5b606061044e8260008151811061041f57fe5b905060006104776104728360008151811061046557fe5b6020026020010151610752565b6107ce565b9450505050505b919050565b6040805182815290517f98ab4df6be53ea2dac064846a792a2a8f68f04fe0d840eb824961c516440e2959181900360200190a150565b6060806104f28460405180807253746172744576656e742875696e743235362960681b8152506013019050604051809103902085610528565b95945050505050565b6000606061050b61040684610646565b9050606061051f8260038151811061041f57fe5b51949350505050565b606080606061053961040685610646565b9050606061054d8260038151811061041f57fe5b905060005b815181101561060657606061056c83838151811061041f57fe5b905060006105836104728360008151811061046557fe5b90506105958260018151811061041f57fe5b96506105a78260028151811061046557fe5b955060006105c8886000815181106105bb57fe5b60200260200101516107d5565b905089811480156105ea57508a6001600160a01b0316826001600160a01b0316145b156105fb575061063e945050505050565b505050600101610552565b5060405162461bcd60e51b8152600401808060200182810382526025815260200180610b6f6025913960400191505060405180910390fd5b935093915050565b61064e610b34565b815180610670575050604080518082019091526000808252602082015261047e565b6040805180820190915260209384018152928301525090565b6060610694826107e6565b61069d57600080fd5b60006106a88361080d565b90508067ffffffffffffffff811180156106c157600080fd5b506040519080825280602002602001820160405280156106fb57816020015b6106e8610b34565b8152602001906001900390816106e05790505b509150610706610b4e565b61070f84610867565b905060005b61071d8261089e565b1561074a5761072b826108bd565b84828151811061073757fe5b6020908102919091010152600101610714565b505050919050565b606061075d826108fd565b61076657600080fd5b60008061077284610923565b90925090508067ffffffffffffffff8111801561078e57600080fd5b506040519080825280601f01601f1916602001820160405280156107b9576020820181803683370190505b5092506107c7828483610993565b5050919050565b6014015190565b60006107e0826109d1565b92915050565b60008160200151600014156107fd5750600061047e565b50515160c060009190911a101590565b6000610818826107e6565b6108245750600061047e565b81518051600090811a919061083885610a28565b6020860151908301915082016000190160005b8183116104775761085b83610aa6565b9092019160010161084b565b61086f610b4e565b610878826107e6565b61088157600080fd5b600061088c83610a28565b83519383529092016020820152919050565b60006108a8610b34565b50508051602080820151915192015191011190565b6108c5610b34565b6108ce8261089e565b1561006257602082015160006108e382610aa6565b82845260208085018290529201918401919091525061047e565b60008160200151600014156109145750600061047e565b50515160c060009190911a1090565b60008061092f836108fd565b61093857600080fd5b8251805160001a9060808210156109565792506001915061098e9050565b60b8821015610974576001856020015103925080600101935061098b565b602085015182820160b51901945082900360b60192505b50505b915091565b6020601f820104836020840160005b838110156109be576020810283810151908301526001016109a2565b5050505060008251602001830152505050565b60006109dc826108fd565b6109e557600080fd5b6000806109f184610923565b90925090506020811115610a0457600080fd5b80610a145760009250505061047e565b806020036101000a82510492505050919050565b6000816020015160001415610a3f5750600061047e565b8151805160001a906080821015610a5b5760009250505061047e565b60b8821080610a76575060c08210158015610a76575060f882105b15610a865760019250505061047e565b60c0821015610a9b575060b51901905061047e565b5060f5190192915050565b8051600090811a6080811015610abf5760019150610b2e565b60b8811015610ad457607e1981019150610b2e565b60c0811015610afd57600183015160b76020839003016101000a9004810160b519019150610b2e565b60f8811015610b125760be1981019150610b2e565b600183015160f76020839003016101000a9004810160f5190191505b50919050565b604051806040016040528060008152602001600081525090565b6040518060400160405280610b61610b34565b815260200160008152509056fe4e6f206576656e7420666f756e6420696e207472616e73616374696f6e2072656365697074a26469706673582212202039a51309419fccd31ff150810a44fc8ccf9eb32d43e2087829dfc8c8c4b9be64736f6c634300060b0033";

    public static final String FUNC_EMITTINGCONTRACTFIRSTLOG = "emittingContractFirstLog";

    public static final String FUNC_GETEVENTSIGNATUREFIRSTLOG = "getEventSignatureFirstLog";

    public static final String FUNC_NUMLOGSFOUND = "numLogsFound";

    public static final String FUNC_RETRIEVEALOG = "retrieveALog";

    public static final String FUNC_RETRIEVESTARTLOG = "retrieveStartLog";

    public static final String FUNC_TRIGGERSTARTEVENT = "triggerStartEvent";

    public static final Event STARTEVENT_EVENT = new Event("StartEvent", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected TestReceipts(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected TestReceipts(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected TestReceipts(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected TestReceipts(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<StartEventEventResponse> getStartEventEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(STARTEVENT_EVENT, transactionReceipt);
        ArrayList<StartEventEventResponse> responses = new ArrayList<StartEventEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            StartEventEventResponse typedResponse = new StartEventEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._val = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<StartEventEventResponse> startEventEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, StartEventEventResponse>() {
            @Override
            public StartEventEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(STARTEVENT_EVENT, log);
                StartEventEventResponse typedResponse = new StartEventEventResponse();
                typedResponse.log = log;
                typedResponse._val = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<StartEventEventResponse> startEventEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(STARTEVENT_EVENT));
        return startEventEventFlowable(filter);
    }

    public RemoteFunctionCall<String> emittingContractFirstLog(byte[] _receiptRlp) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_EMITTINGCONTRACTFIRSTLOG, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicBytes(_receiptRlp)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public String getRLP_emittingContractFirstLog(byte[] _receiptRlp) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_EMITTINGCONTRACTFIRSTLOG, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicBytes(_receiptRlp)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<String> getEventSignatureFirstLog(byte[] _receiptRlp) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETEVENTSIGNATUREFIRSTLOG, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicBytes(_receiptRlp)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public String getRLP_getEventSignatureFirstLog(byte[] _receiptRlp) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_GETEVENTSIGNATUREFIRSTLOG, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicBytes(_receiptRlp)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<BigInteger> numLogsFound(byte[] _receiptRlp) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_NUMLOGSFOUND, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicBytes(_receiptRlp)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public String getRLP_numLogsFound(byte[] _receiptRlp) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_NUMLOGSFOUND, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicBytes(_receiptRlp)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<byte[]> retrieveALog(String _contractAddress, byte[] _eventFunctionSignature, byte[] _receiptRlp) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_RETRIEVEALOG, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _contractAddress), 
                new org.web3j.abi.datatypes.generated.Bytes32(_eventFunctionSignature), 
                new org.web3j.abi.datatypes.DynamicBytes(_receiptRlp)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicBytes>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public String getRLP_retrieveALog(String _contractAddress, byte[] _eventFunctionSignature, byte[] _receiptRlp) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_RETRIEVEALOG, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _contractAddress), 
                new org.web3j.abi.datatypes.generated.Bytes32(_eventFunctionSignature), 
                new org.web3j.abi.datatypes.DynamicBytes(_receiptRlp)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<byte[]> retrieveStartLog(String _contractAddress, byte[] _receiptRlp) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_RETRIEVESTARTLOG, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _contractAddress), 
                new org.web3j.abi.datatypes.DynamicBytes(_receiptRlp)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicBytes>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public String getRLP_retrieveStartLog(String _contractAddress, byte[] _receiptRlp) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_RETRIEVESTARTLOG, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _contractAddress), 
                new org.web3j.abi.datatypes.DynamicBytes(_receiptRlp)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<TransactionReceipt> triggerStartEvent(BigInteger _val) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_TRIGGERSTARTEVENT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_val)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getRLP_triggerStartEvent(BigInteger _val) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_TRIGGERSTARTEVENT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_val)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    @Deprecated
    public static TestReceipts load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new TestReceipts(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static TestReceipts load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new TestReceipts(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static TestReceipts load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new TestReceipts(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static TestReceipts load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new TestReceipts(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<TestReceipts> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(TestReceipts.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<TestReceipts> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(TestReceipts.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<TestReceipts> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(TestReceipts.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<TestReceipts> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(TestReceipts.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class StartEventEventResponse extends BaseEventResponse {
        public BigInteger _val;
    }
}
