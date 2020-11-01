package tech.pegasys.ltacfc.soliditywrappers;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes32;
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
public class CbcSignedEvent extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b5060405161120c38038061120c83398101604081905261002f91610060565b600091909155600a80546001600160a01b0390921661010002610100600160a81b031990921691909117905561009b565b60008060408385031215610072578182fd5b825160208401519092506001600160a01b0381168114610090578182fd5b809150509250929050565b611162806100aa6000396000f3fe608060405234801561001057600080fd5b50600436106100b45760003560e01c806366b79f5a1161007157806366b79f5a146101245780638e22d5341461012c57806392b2c3351461013f578063b4c3b75614610152578063d5d6b09c14610167578063df1bba011461016f576100b4565b80632af6cdf0146100b9578063336d5b09146100d757806339ce107e146100df57806342b011c0146100f4578063439160df146101075780635c27d3071461010f575b600080fd5b6100c1610182565b6040516100ce919061104d565b60405180910390f35b6100c1610188565b6100f26100ed366004610e3d565b61018e565b005b6100c1610102366004610e5e565b610215565b6100c1610227565b61011761022d565b6040516100ce9190610f87565b6100c16102bb565b6100c161013a366004610e76565b6102c1565b6100f261014d366004610e76565b6102e8565b61015a610321565b6040516100ce9190610f7c565b6100c1610328565b6100f261017d366004610ece565b61032e565b60025481565b60025490565b6001600160a01b03811660009081526008602052604090205460ff16610212576009805460018082019092557f6e1540171b6c0c960b71a7020d9f60077f6af931a8bbf590da0223dacf75c7af0180546001600160a01b0319166001600160a01b0384169081179091556000908152600860205260409020805460ff191690911790555b50565b60016020526000908152604090205481565b60005481565b6004805460408051602060026001851615610100026000190190941693909304601f810184900484028201840190925281815292918301828280156102b35780601f10610288576101008083540402835291602001916102b3565b820191906000526020600020905b81548152906001019060200180831161029657829003601f168201915b505050505081565b60035490565b600060606102d1868686866103df565b90506102de81600061071c565b9695505050505050565b60606102f6858585856103df565b9050610311816040518060200160405280600081525061077d565b61031a57600080fd5b5050505050565b6002541590565b60035481565b3233146103565760405162461bcd60e51b815260040161034d90610fe1565b60405180910390fd5b600084815260016020526040902054156103825760405162461bcd60e51b815260040161034d90611016565b600084815260016020526040908190204285019081905590517f77dab611ad9a24b763e2742f57749a0227393e0da76212d74fceb326b0661424906103d09087903390859088908890611056565b60405180910390a15050505050565b60607f3f65ec40a934b9a9e582b4c728ca2798fc40cf7de89354f5ab26a47fe60996c7856000801b86868660405161041b95949392919061108f565b60405180910390a1600654600754106104465760405162461bcd60e51b815260040161034d90610f9a565b60055460609060010167ffffffffffffffff8111801561046557600080fd5b5060405190808252806020026020018201604052801561048f578160200160208202803683370190505b50905060005b6005548110156104d657600581815481106104ac57fe5b90600052602060002001548282815181106104c357fe5b6020908102919091010152600101610495565b50600754600101816001835103815181106104ed57fe5b6020908102919091018101919091526004805460408051601f600260001961010060018716150201909416939093049283018590048502810185019091528181526000938493606093610597939283018282801561058c5780601f106105615761010080835404028352916020019161058c565b820191906000526020600020905b81548152906001019060200180831161056f57829003601f168201915b5050505050856107a8565b92509250925082891415806105be5750816001600160a01b0316886001600160a01b031614155b80610607575061060587878080601f01602080910402602001604051908101604052809392919081815260200183838082843760009201919091525085925061077d915050565b155b1561061a57600a805460ff191660011790555b60078054600181019091556006805460609290811061063557fe5b600091825260209182902001805460408051601f60026000196101006001871615020190941693909304928301859004850281018501909152818152928301828280156106c35780601f10610698576101008083540402835291602001916106c3565b820191906000526020600020905b8154815290600101906020018083116106a657829003601f168201915b505050505090507fa055e0edab905faf52d7d9c5d647017542eab38b9bb20a3d1c1cbe0554fa9626848b858c868d8d886040516107079897969594939291906110bd565b60405180910390a19998505050505050505050565b6000816020018351101561076e576040805162461bcd60e51b8152602060048201526014602482015273736c6963696e67206f7574206f662072616e676560601b604482015290519081900360640190fd5b50818101602001515b92915050565b6000815183511461079057506000610777565b81805190602001208380519060200120149050610777565b6000806060806107bf6107ba87610888565b6108d1565b905060005b6001865103811015610809576107ff828783815181106107e057fe5b6020026020010151815181106107f257fe5b60200260200101516108d1565b91506001016107c4565b50606061082082876001895103815181106107e057fe5b905061083f8160008151811061083257fe5b602002602001015161099a565b945061085e8160018151811061085157fe5b60200260200101516109f1565b935061087d8160028151811061087057fe5b6020026020010151610a32565b925050509250925092565b610890610da5565b8151806108b257505060408051808201909152600080825260208201526108cc565b604080518082019091526020848101825281019190915290505b919050565b60606108dc82610aae565b6108e557600080fd5b60006108f083610ad5565b90508067ffffffffffffffff8111801561090957600080fd5b5060405190808252806020026020018201604052801561094357816020015b610930610da5565b8152602001906001900390816109285790505b50915061094e610dbf565b61095784610b2f565b905060005b61096582610b66565b156109925761097382610b85565b84828151811061097f57fe5b602090810291909101015260010161095c565b505050919050565b60006109a582610bc5565b6109ae57600080fd5b6000806109ba84610beb565b909250905060208111156109cd57600080fd5b806109dd576000925050506108cc565b806020036101000a82510492505050919050565b60006109fc82610bc5565b610a0557600080fd5b600080610a1184610beb565b909250905060148114610a2357600080fd5b5051600160601b900492915050565b6060610a3d82610bc5565b610a4657600080fd5b600080610a5284610beb565b90925090508067ffffffffffffffff81118015610a6e57600080fd5b506040519080825280601f01601f191660200182016040528015610a99576020820181803683370190505b509250610aa7828483610c5b565b5050919050565b6000816020015160001415610ac5575060006108cc565b50515160c060009190911a101590565b6000610ae082610aae565b610aec575060006108cc565b81518051600090811a9190610b0085610c99565b6020860151908301915082016000190160005b8183116102de57610b2383610d17565b90920191600101610b13565b610b37610dbf565b610b4082610aae565b610b4957600080fd5b6000610b5483610c99565b83519383529092016020820152919050565b6000610b70610da5565b50508051602080820151915192015191011190565b610b8d610da5565b610b9682610b66565b156100b45760208201516000610bab82610d17565b8284526020808501829052920191840191909152506108cc565b6000816020015160001415610bdc575060006108cc565b50515160c060009190911a1090565b600080610bf783610bc5565b610c0057600080fd5b8251805160001a906080821015610c1e57925060019150610c569050565b60b8821015610c3c5760018560200151039250806001019350610c53565b602085015182820160b51901945082900360b60192505b50505b915091565b6020601f820104836020840160005b83811015610c8657602081028381015190830152600101610c6a565b5050505060008251602001830152505050565b6000816020015160001415610cb0575060006108cc565b8151805160001a906080821015610ccc576000925050506108cc565b60b8821080610ce7575060c08210158015610ce7575060f882105b15610cf7576001925050506108cc565b60c0821015610d0c575060b5190190506108cc565b5060f5190192915050565b8051600090811a6080811015610d305760019150610d9f565b60b8811015610d4557607e1981019150610d9f565b60c0811015610d6e57600183015160b76020839003016101000a9004810160b519019150610d9f565b60f8811015610d835760be1981019150610d9f565b600183015160f76020839003016101000a9004810160f5190191505b50919050565b604051806040016040528060008152602001600081525090565b6040518060400160405280610dd2610da5565b8152602001600081525090565b80356001600160a01b03811681146108cc57600080fd5b60008083601f840112610e07578182fd5b50813567ffffffffffffffff811115610e1e578182fd5b602083019150836020828501011115610e3657600080fd5b9250929050565b600060208284031215610e4e578081fd5b610e5782610ddf565b9392505050565b600060208284031215610e6f578081fd5b5035919050565b60008060008060608587031215610e8b578283fd5b84359350610e9b60208601610ddf565b9250604085013567ffffffffffffffff811115610eb6578283fd5b610ec287828801610df6565b95989497509550505050565b60008060008060608587031215610ee3578384fd5b8435935060208501359250604085013567ffffffffffffffff811115610eb6578283fd5b60008284528282602086013780602084860101526020601f19601f85011685010190509392505050565b60008151808452815b81811015610f5657602081850181015186830182015201610f3a565b81811115610f675782602083870101525b50601f01601f19169290920160200192915050565b901515815260200190565b600060208252610e576020830184610f31565b60208082526027908201527f43616c6c20746f2063726f73732063616c6c20776974686f75742072657475726040820152666e2076616c756560c81b606082015260800190565b6020808252818101527f5374617274206d7573742062652063616c6c65642066726f6d20616e20454f41604082015260600190565b6020808252601e908201527f5472616e73616374696f6e20616c726561647920726567697374657265640000604082015260600190565b90815260200190565b600086825260018060a01b038616602083015284604083015260806060830152611084608083018486610f07565b979650505050505050565b600086825285602083015260018060a01b038516604083015260806060830152611084608083018486610f07565b888152602081018890526001600160a01b0387811660408301528616606082015260e0608082018190526000906110f690830187610f31565b82810360a0840152611109818688610f07565b905082810360c084015261111d8185610f31565b9b9a505050505050505050505056fea264697066735822122068d6311e73e5fe1b6cd840e7292b789eb6cd85139489977c0e706743dd64dde264736f6c63430007040033";

    public static final String FUNC_ACTIVECALLCROSSBLOCKCHAINTRANSACTIONID = "activeCallCrossBlockchainTransactionId";

    public static final String FUNC_ACTIVECALLGRAPH = "activeCallGraph";

    public static final String FUNC_ACTIVECALLROOTBLOCKCHAINID = "activeCallRootBlockchainId";

    public static final String FUNC_ADDTOLISTOFLOCKEDCONTRACTS = "addToListOfLockedContracts";

    public static final String FUNC_CROSSBLOCKCHAINCALL = "crossBlockchainCall";

    public static final String FUNC_CROSSBLOCKCHAINCALLRETURNSUINT256 = "crossBlockchainCallReturnsUint256";

    public static final String FUNC_GETACTIVECALLCROSSBLOCKCHAINTRANSACTIONID = "getActiveCallCrossBlockchainTransactionId";

    public static final String FUNC_GETACTIVECALLROOTBLOCKCHAINID = "getActiveCallRootBlockchainId";

    public static final String FUNC_ISSINGLEBLOCKCHAINCALL = "isSingleBlockchainCall";

    public static final String FUNC_MYBLOCKCHAINID = "myBlockchainId";

    public static final String FUNC_START = "start";

    public static final String FUNC_TRANSACTIONINFORMATION = "transactionInformation";

    public static final Event CALL_EVENT = new Event("Call", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<DynamicBytes>() {}, new TypeReference<DynamicBytes>() {}, new TypeReference<DynamicBytes>() {}));
    ;

    public static final Event DUMP_EVENT = new Event("Dump", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Address>() {}, new TypeReference<DynamicBytes>() {}));
    ;

    public static final Event NOTENOUGHCALLS_EVENT = new Event("NotEnoughCalls", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event ROOT_EVENT = new Event("Root", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Bool>() {}));
    ;

    public static final Event SEGMENT_EVENT = new Event("Segment", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Bytes32>() {}, new TypeReference<DynamicArray<Uint256>>() {}, new TypeReference<DynamicArray<Address>>() {}, new TypeReference<Bool>() {}, new TypeReference<DynamicBytes>() {}));
    ;

    public static final Event SIGNALLING_EVENT = new Event("Signalling", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event START_EVENT = new Event("Start", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<DynamicBytes>() {}));
    ;

    @Deprecated
    protected CbcSignedEvent(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected CbcSignedEvent(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected CbcSignedEvent(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected CbcSignedEvent(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<CallEventResponse> getCallEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(CALL_EVENT, transactionReceipt);
        ArrayList<CallEventResponse> responses = new ArrayList<CallEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            CallEventResponse typedResponse = new CallEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._expectedBlockchainId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse._actualBlockchainId = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse._expectedContract = (String) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse._actualContract = (String) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse._expectedFunctionCall = (byte[]) eventValues.getNonIndexedValues().get(4).getValue();
            typedResponse._actualFunctionCall = (byte[]) eventValues.getNonIndexedValues().get(5).getValue();
            typedResponse._retVal = (byte[]) eventValues.getNonIndexedValues().get(6).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<CallEventResponse> callEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, CallEventResponse>() {
            @Override
            public CallEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(CALL_EVENT, log);
                CallEventResponse typedResponse = new CallEventResponse();
                typedResponse.log = log;
                typedResponse._expectedBlockchainId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse._actualBlockchainId = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse._expectedContract = (String) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse._actualContract = (String) eventValues.getNonIndexedValues().get(3).getValue();
                typedResponse._expectedFunctionCall = (byte[]) eventValues.getNonIndexedValues().get(4).getValue();
                typedResponse._actualFunctionCall = (byte[]) eventValues.getNonIndexedValues().get(5).getValue();
                typedResponse._retVal = (byte[]) eventValues.getNonIndexedValues().get(6).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<CallEventResponse> callEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CALL_EVENT));
        return callEventFlowable(filter);
    }

    public List<DumpEventResponse> getDumpEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(DUMP_EVENT, transactionReceipt);
        ArrayList<DumpEventResponse> responses = new ArrayList<DumpEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            DumpEventResponse typedResponse = new DumpEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._val1 = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse._val2 = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse._val3 = (String) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse._val4 = (byte[]) eventValues.getNonIndexedValues().get(3).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<DumpEventResponse> dumpEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, DumpEventResponse>() {
            @Override
            public DumpEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(DUMP_EVENT, log);
                DumpEventResponse typedResponse = new DumpEventResponse();
                typedResponse.log = log;
                typedResponse._val1 = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse._val2 = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse._val3 = (String) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse._val4 = (byte[]) eventValues.getNonIndexedValues().get(3).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<DumpEventResponse> dumpEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(DUMP_EVENT));
        return dumpEventFlowable(filter);
    }

    public List<NotEnoughCallsEventResponse> getNotEnoughCallsEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(NOTENOUGHCALLS_EVENT, transactionReceipt);
        ArrayList<NotEnoughCallsEventResponse> responses = new ArrayList<NotEnoughCallsEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            NotEnoughCallsEventResponse typedResponse = new NotEnoughCallsEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._expectedNumberOfCalls = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse._actualNumberOfCalls = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<NotEnoughCallsEventResponse> notEnoughCallsEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, NotEnoughCallsEventResponse>() {
            @Override
            public NotEnoughCallsEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(NOTENOUGHCALLS_EVENT, log);
                NotEnoughCallsEventResponse typedResponse = new NotEnoughCallsEventResponse();
                typedResponse.log = log;
                typedResponse._expectedNumberOfCalls = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse._actualNumberOfCalls = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<NotEnoughCallsEventResponse> notEnoughCallsEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(NOTENOUGHCALLS_EVENT));
        return notEnoughCallsEventFlowable(filter);
    }

    public List<RootEventResponse> getRootEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(ROOT_EVENT, transactionReceipt);
        ArrayList<RootEventResponse> responses = new ArrayList<RootEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            RootEventResponse typedResponse = new RootEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._crossBlockchainTransactionId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse._success = (Boolean) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<RootEventResponse> rootEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, RootEventResponse>() {
            @Override
            public RootEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(ROOT_EVENT, log);
                RootEventResponse typedResponse = new RootEventResponse();
                typedResponse.log = log;
                typedResponse._crossBlockchainTransactionId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse._success = (Boolean) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<RootEventResponse> rootEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ROOT_EVENT));
        return rootEventFlowable(filter);
    }

    public List<SegmentEventResponse> getSegmentEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(SEGMENT_EVENT, transactionReceipt);
        ArrayList<SegmentEventResponse> responses = new ArrayList<SegmentEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            SegmentEventResponse typedResponse = new SegmentEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._crossBlockchainTransactionId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse._hashOfCallGraph = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse._callPath = (List<BigInteger>) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse._lockedContracts = (List<String>) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse._success = (Boolean) eventValues.getNonIndexedValues().get(4).getValue();
            typedResponse._returnValue = (byte[]) eventValues.getNonIndexedValues().get(5).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<SegmentEventResponse> segmentEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, SegmentEventResponse>() {
            @Override
            public SegmentEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(SEGMENT_EVENT, log);
                SegmentEventResponse typedResponse = new SegmentEventResponse();
                typedResponse.log = log;
                typedResponse._crossBlockchainTransactionId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse._hashOfCallGraph = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse._callPath = (List<BigInteger>) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse._lockedContracts = (List<String>) eventValues.getNonIndexedValues().get(3).getValue();
                typedResponse._success = (Boolean) eventValues.getNonIndexedValues().get(4).getValue();
                typedResponse._returnValue = (byte[]) eventValues.getNonIndexedValues().get(5).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<SegmentEventResponse> segmentEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(SEGMENT_EVENT));
        return segmentEventFlowable(filter);
    }

    public List<SignallingEventResponse> getSignallingEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(SIGNALLING_EVENT, transactionReceipt);
        ArrayList<SignallingEventResponse> responses = new ArrayList<SignallingEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            SignallingEventResponse typedResponse = new SignallingEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._rootBcId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse._crossBlockchainTransactionId = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<SignallingEventResponse> signallingEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, SignallingEventResponse>() {
            @Override
            public SignallingEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(SIGNALLING_EVENT, log);
                SignallingEventResponse typedResponse = new SignallingEventResponse();
                typedResponse.log = log;
                typedResponse._rootBcId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse._crossBlockchainTransactionId = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<SignallingEventResponse> signallingEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(SIGNALLING_EVENT));
        return signallingEventFlowable(filter);
    }

    public List<StartEventResponse> getStartEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(START_EVENT, transactionReceipt);
        ArrayList<StartEventResponse> responses = new ArrayList<StartEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            StartEventResponse typedResponse = new StartEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._crossBlockchainTransactionId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse._caller = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse._timeout = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse._callGraph = (byte[]) eventValues.getNonIndexedValues().get(3).getValue();
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
                typedResponse._caller = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse._timeout = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse._callGraph = (byte[]) eventValues.getNonIndexedValues().get(3).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<StartEventResponse> startEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(START_EVENT));
        return startEventFlowable(filter);
    }

    public RemoteFunctionCall<BigInteger> activeCallCrossBlockchainTransactionId() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ACTIVECALLCROSSBLOCKCHAINTRANSACTIONID, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public String getRLP_activeCallCrossBlockchainTransactionId() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ACTIVECALLCROSSBLOCKCHAINTRANSACTIONID, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<byte[]> activeCallGraph() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ACTIVECALLGRAPH, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicBytes>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public String getRLP_activeCallGraph() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ACTIVECALLGRAPH, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<BigInteger> activeCallRootBlockchainId() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ACTIVECALLROOTBLOCKCHAINID, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public String getRLP_activeCallRootBlockchainId() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ACTIVECALLROOTBLOCKCHAINID, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<TransactionReceipt> addToListOfLockedContracts(String _contractToLock) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ADDTOLISTOFLOCKEDCONTRACTS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _contractToLock)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getRLP_addToListOfLockedContracts(String _contractToLock) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ADDTOLISTOFLOCKEDCONTRACTS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _contractToLock)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<TransactionReceipt> crossBlockchainCall(BigInteger _blockchainId, String _contract, byte[] _functionCallData) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CROSSBLOCKCHAINCALL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_blockchainId), 
                new org.web3j.abi.datatypes.Address(160, _contract), 
                new org.web3j.abi.datatypes.DynamicBytes(_functionCallData)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getRLP_crossBlockchainCall(BigInteger _blockchainId, String _contract, byte[] _functionCallData) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CROSSBLOCKCHAINCALL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_blockchainId), 
                new org.web3j.abi.datatypes.Address(160, _contract), 
                new org.web3j.abi.datatypes.DynamicBytes(_functionCallData)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<TransactionReceipt> crossBlockchainCallReturnsUint256(BigInteger _blockchainId, String _contract, byte[] _functionCallData) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CROSSBLOCKCHAINCALLRETURNSUINT256, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_blockchainId), 
                new org.web3j.abi.datatypes.Address(160, _contract), 
                new org.web3j.abi.datatypes.DynamicBytes(_functionCallData)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getRLP_crossBlockchainCallReturnsUint256(BigInteger _blockchainId, String _contract, byte[] _functionCallData) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CROSSBLOCKCHAINCALLRETURNSUINT256, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_blockchainId), 
                new org.web3j.abi.datatypes.Address(160, _contract), 
                new org.web3j.abi.datatypes.DynamicBytes(_functionCallData)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<BigInteger> getActiveCallCrossBlockchainTransactionId() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETACTIVECALLCROSSBLOCKCHAINTRANSACTIONID, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public String getRLP_getActiveCallCrossBlockchainTransactionId() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_GETACTIVECALLCROSSBLOCKCHAINTRANSACTIONID, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<BigInteger> getActiveCallRootBlockchainId() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETACTIVECALLROOTBLOCKCHAINID, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public String getRLP_getActiveCallRootBlockchainId() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_GETACTIVECALLROOTBLOCKCHAINID, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<Boolean> isSingleBlockchainCall() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ISSINGLEBLOCKCHAINCALL, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public String getRLP_isSingleBlockchainCall() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ISSINGLEBLOCKCHAINCALL, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<BigInteger> myBlockchainId() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_MYBLOCKCHAINID, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public String getRLP_myBlockchainId() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_MYBLOCKCHAINID, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
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

    public String getRLP_start(BigInteger _crossBlockchainTransactionId, BigInteger _timeout, byte[] _callGraph) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_START, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_crossBlockchainTransactionId), 
                new org.web3j.abi.datatypes.generated.Uint256(_timeout), 
                new org.web3j.abi.datatypes.DynamicBytes(_callGraph)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<BigInteger> transactionInformation(BigInteger param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_TRANSACTIONINFORMATION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public String getRLP_transactionInformation(BigInteger param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_TRANSACTIONINFORMATION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    @Deprecated
    public static CbcSignedEvent load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new CbcSignedEvent(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static CbcSignedEvent load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new CbcSignedEvent(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static CbcSignedEvent load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new CbcSignedEvent(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static CbcSignedEvent load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new CbcSignedEvent(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<CbcSignedEvent> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, BigInteger _myBlockchainId, String _registrar) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_myBlockchainId), 
                new org.web3j.abi.datatypes.Address(160, _registrar)));
        return deployRemoteCall(CbcSignedEvent.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<CbcSignedEvent> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, BigInteger _myBlockchainId, String _registrar) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_myBlockchainId), 
                new org.web3j.abi.datatypes.Address(160, _registrar)));
        return deployRemoteCall(CbcSignedEvent.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<CbcSignedEvent> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger _myBlockchainId, String _registrar) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_myBlockchainId), 
                new org.web3j.abi.datatypes.Address(160, _registrar)));
        return deployRemoteCall(CbcSignedEvent.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<CbcSignedEvent> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger _myBlockchainId, String _registrar) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_myBlockchainId), 
                new org.web3j.abi.datatypes.Address(160, _registrar)));
        return deployRemoteCall(CbcSignedEvent.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static class CallEventResponse extends BaseEventResponse {
        public BigInteger _expectedBlockchainId;

        public BigInteger _actualBlockchainId;

        public String _expectedContract;

        public String _actualContract;

        public byte[] _expectedFunctionCall;

        public byte[] _actualFunctionCall;

        public byte[] _retVal;
    }

    public static class DumpEventResponse extends BaseEventResponse {
        public BigInteger _val1;

        public byte[] _val2;

        public String _val3;

        public byte[] _val4;
    }

    public static class NotEnoughCallsEventResponse extends BaseEventResponse {
        public BigInteger _expectedNumberOfCalls;

        public BigInteger _actualNumberOfCalls;
    }

    public static class RootEventResponse extends BaseEventResponse {
        public BigInteger _crossBlockchainTransactionId;

        public Boolean _success;
    }

    public static class SegmentEventResponse extends BaseEventResponse {
        public BigInteger _crossBlockchainTransactionId;

        public byte[] _hashOfCallGraph;

        public List<BigInteger> _callPath;

        public List<String> _lockedContracts;

        public Boolean _success;

        public byte[] _returnValue;
    }

    public static class SignallingEventResponse extends BaseEventResponse {
        public BigInteger _rootBcId;

        public BigInteger _crossBlockchainTransactionId;
    }

    public static class StartEventResponse extends BaseEventResponse {
        public BigInteger _crossBlockchainTransactionId;

        public String _caller;

        public BigInteger _timeout;

        public byte[] _callGraph;
    }
}
