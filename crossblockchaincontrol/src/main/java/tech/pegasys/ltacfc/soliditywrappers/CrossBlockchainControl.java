package tech.pegasys.ltacfc.soliditywrappers;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Bool;
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
 * <p>Generated with web3j version 4.5.16.
 */
@SuppressWarnings("rawtypes")
public class CrossBlockchainControl extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b506104f2806100206000396000f3fe608060405234801561001057600080fd5b50600436106100935760003560e01c80634e2edb76116100665780634e2edb76146100c75780634ebaa78a146100c7578063653cf5a61461015d5780639ad8a5b6146101ef578063df1bba011461022057610093565b806308148f7a14610098578063371c148f146100c7578063415ffba7146100c75780634c97042e14610140575b600080fd5b6100b5600480360360208110156100ae57600080fd5b503561029c565b60408051918252519081900360200190f35b61013e600480360360408110156100dd57600080fd5b813591908101906040810160208201356401000000008111156100ff57600080fd5b82018360208201111561011157600080fd5b8035906020019184600183028401116401000000008311171561013357600080fd5b5090925090506102ae565b005b6100b56004803603602081101561015657600080fd5b50356102b3565b61017a6004803603602081101561017357600080fd5b50356102c5565b6040805160208082528351818301528351919283929083019185019080838360005b838110156101b457818101518382015260200161019c565b50505050905090810190601f1680156101e15780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b61020c6004803603602081101561020557600080fd5b503561035f565b604080519115158252519081900360200190f35b61013e6004803603606081101561023657600080fd5b81359160208101359181019060608101604082013564010000000081111561025d57600080fd5b82018360208201111561026f57600080fd5b8035906020019184600183028401116401000000008311171561029157600080fd5b509092509050610373565b60006020819052908152604090205481565b505050565b60009081526020819052604090205490565b60016020818152600092835260409283902080548451600294821615610100026000190190911693909304601f81018390048302840183019094528383529192908301828280156103575780601f1061032c57610100808354040283529160200191610357565b820191906000526020600020905b81548152906001019060200180831161033a57829003601f168201915b505050505081565b600090815260208190526040902054151590565b61037c8461035f565b1561038657600080fd5b600084815260208181526040808320869055600190915290206103aa908383610421565b507fe071861e96e2f86d7704105f1697b783ce582ea599692363bb7ec62945575fec8484848460405180858152602001848152602001806020018281038252848482818152602001925080828437600083820152604051601f909101601f191690920182900397509095505050505050a150505050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106104625782800160ff1982351617855561048f565b8280016001018555821561048f579182015b8281111561048f578235825591602001919060010190610474565b5061049b92915061049f565b5090565b6104b991905b8082111561049b57600081556001016104a5565b9056fea26469706673582212206799a1bf66307d08543aecdbad4c3d6809035475540f69604c0d65a0c77e35af64736f6c63430006090033";

    public static final String FUNC_CALLGRAPHS = "callGraphs";

    public static final String FUNC_CLOSE = "close";

    public static final String FUNC_CROSSBLOCKCHAINTRANSACTIONEXISTS = "crossBlockchainTransactionExists";

    public static final String FUNC_CROSSBLOCKCHAINTRANSACTIONTIMEOUT = "crossBlockchainTransactionTimeout";

    public static final String FUNC_ROOT = "root";

    public static final String FUNC_SEGMENT = "segment";

    public static final String FUNC_SIGNALLING = "signalling";

    public static final String FUNC_START = "start";

    public static final String FUNC_TIMEOUT = "timeout";

    public static final Event START_EVENT = new Event("Start", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<DynamicBytes>() {}));
    ;

    @Deprecated
    protected CrossBlockchainControl(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected CrossBlockchainControl(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected CrossBlockchainControl(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected CrossBlockchainControl(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<StartEventResponse> getStartEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(START_EVENT, transactionReceipt);
        ArrayList<StartEventResponse> responses = new ArrayList<StartEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            StartEventResponse typedResponse = new StartEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._crossBlockchainTransactionId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse._timeout = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse._callGraph = (byte[]) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<StartEventResponse> startEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, StartEventResponse>() {
            @Override
            public StartEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(START_EVENT, log);
                StartEventResponse typedResponse = new StartEventResponse();
                typedResponse.log = log;
                typedResponse._crossBlockchainTransactionId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse._timeout = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse._callGraph = (byte[]) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<StartEventResponse> startEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(START_EVENT));
        return startEventFlowable(filter);
    }

    public RemoteFunctionCall<byte[]> callGraphs(BigInteger param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_CALLGRAPHS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicBytes>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<Boolean> crossBlockchainTransactionExists(BigInteger _crossBlockchainTransactionId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_CROSSBLOCKCHAINTRANSACTIONEXISTS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_crossBlockchainTransactionId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<BigInteger> crossBlockchainTransactionTimeout(BigInteger _crossBlockchainTransactionId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_CROSSBLOCKCHAINTRANSACTIONTIMEOUT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_crossBlockchainTransactionId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> start(BigInteger _crossBlockchainTransactionId, BigInteger _timeout, byte[] _callGraph) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_START, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_crossBlockchainTransactionId), 
                new org.web3j.abi.datatypes.generated.Uint256(_timeout), 
                new org.web3j.abi.datatypes.DynamicBytes(_callGraph)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> timeout(BigInteger param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_TIMEOUT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    @Deprecated
    public static CrossBlockchainControl load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new CrossBlockchainControl(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static CrossBlockchainControl load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new CrossBlockchainControl(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static CrossBlockchainControl load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new CrossBlockchainControl(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static CrossBlockchainControl load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new CrossBlockchainControl(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<CrossBlockchainControl> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(CrossBlockchainControl.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<CrossBlockchainControl> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(CrossBlockchainControl.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<CrossBlockchainControl> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(CrossBlockchainControl.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<CrossBlockchainControl> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(CrossBlockchainControl.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class StartEventResponse extends BaseEventResponse {
        public BigInteger _crossBlockchainTransactionId;

        public BigInteger _timeout;

        public byte[] _callGraph;
    }
}
