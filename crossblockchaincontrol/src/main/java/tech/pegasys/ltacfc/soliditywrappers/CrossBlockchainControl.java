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
    public static final String BINARY = "608060405234801561001057600080fd5b506101ce806100206000396000f3fe608060405234801561001057600080fd5b50600436106100625760003560e01c806308148f7a14610067578063371c148f14610096578063415ffba7146100965780634e2edb76146100965780634ebaa78a146100965780638fb4b5731461010f575b600080fd5b6100846004803603602081101561007d57600080fd5b5035610132565b60408051918252519081900360200190f35b61010d600480360360408110156100ac57600080fd5b813591908101906040810160208201356401000000008111156100ce57600080fd5b8201836020820111156100e057600080fd5b8035906020019184600183028401116401000000008311171561010257600080fd5b509092509050610144565b005b61010d6004803603604081101561012557600080fd5b5080359060200135610149565b60006020819052908152604090205481565b505050565b60008281526020818152604091829020839055815184815290810183905281517f5a7d5f50ab70a39d193bf53f0fb995377776df93bb6fdcf1cd1868b7e0dd44ee929181900390910190a1505056fea264697066735822122089781da94ea568ee9c455c2f8bf8e183b41e67be2831acb850fe4a16b11dae1c64736f6c63430006090033";

    public static final String FUNC_CLOSE = "close";

    public static final String FUNC_ROOT = "root";

    public static final String FUNC_SEGMENT = "segment";

    public static final String FUNC_SIGNALLING = "signalling";

    public static final String FUNC_START = "start";

    public static final String FUNC_TIMEOUT = "timeout";

    public static final Event START_EVENT = new Event("Start", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
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
            typedResponse.id = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.timeout = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
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
                typedResponse.id = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.timeout = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<StartEventResponse> startEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(START_EVENT));
        return startEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> close(BigInteger _startEventBlockHash, byte[] _startEvent) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CLOSE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_startEventBlockHash), 
                new org.web3j.abi.datatypes.DynamicBytes(_startEvent)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> root(BigInteger _startEventBlockHash, byte[] _startEvent) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ROOT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_startEventBlockHash), 
                new org.web3j.abi.datatypes.DynamicBytes(_startEvent)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> segment(BigInteger _startEventBlockHash, byte[] _startEvent) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SEGMENT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_startEventBlockHash), 
                new org.web3j.abi.datatypes.DynamicBytes(_startEvent)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> signalling(BigInteger _startEventBlockHash, byte[] _startEvent) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SIGNALLING, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_startEventBlockHash), 
                new org.web3j.abi.datatypes.DynamicBytes(_startEvent)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> start(BigInteger _crossBlockchainTransactionId, BigInteger _timeout) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_START, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_crossBlockchainTransactionId), 
                new org.web3j.abi.datatypes.generated.Uint256(_timeout)), 
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
        public BigInteger id;

        public BigInteger timeout;
    }
}
