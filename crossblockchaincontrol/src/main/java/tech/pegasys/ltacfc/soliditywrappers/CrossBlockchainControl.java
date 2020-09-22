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
public class CrossBlockchainControl extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b506040516113eb3803806113eb83398101604081905261002f91610059565b600091909155600180546001600160a01b0319166001600160a01b03909216919091179055610094565b6000806040838503121561006b578182fd5b825160208401519092506001600160a01b0381168114610089578182fd5b809150509250929050565b611348806100a36000396000f3fe608060405234801561001057600080fd5b50600436106101215760003560e01c8063653cf5a6116100ad5780639ad8a5b6116100715780639ad8a5b61461020b578063b4c3b7561461022b578063d5d6b09c14610233578063df1bba011461023b578063f4c1ef2f1461024e57610121565b8063653cf5a6146101b557806366b79f5a146101d55780637cd03cb6146101dd5780638e22d534146101e557806392b2c335146101f857610121565b8063336d5b09116100f4578063336d5b091461017f5780633ab56127146101645780633cdc710414610187578063439160df1461019a5780634c97042e146101a257610121565b806308148f7a146101265780630df973611461014f5780631a26720c146101645780632af6cdf014610177575b600080fd5b610139610134366004610eee565b610263565b60405161014691906111fe565b60405180910390f35b61016261015d366004610e82565b610275565b005b610162610172366004610ea4565b6102e4565b6101396102e9565b6101396102ef565b610162610195366004610f06565b6102f6565b610139610440565b6101396101b0366004610eee565b610446565b6101c86101c3366004610eee565b61045b565b60405161014691906110ed565b6101396104f6565b6101c86104fc565b6101396101f3366004610ff1565b610557565b610162610206366004610ff1565b610561565b61021e610219366004610eee565b610567565b60405161014691906110e2565b61021e61057b565b610139610582565b610162610249366004611056565b610588565b610256610621565b6040516101469190611140565b60026020526000908152604090205481565b6001600160a01b03811660009081526008602052604090205460ff166102e157600980546001810182556000919091527f6e1540171b6c0c960b71a7020d9f60077f6af931a8bbf590da0223dacf75c7af0180546001600160a01b0319166001600160a01b0383161790555b50565b505050565b60045481565b6004545b90565b6001546040516306e3dd6f60e11b81526001600160a01b0390911690630dc7bade90610334908e908d908d908d908d908d908d908d90600401611207565b600060405180830381600087803b15801561034e57600080fd5b505af1158015610362573d6000803e3d6000fd5b50505050606088888080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929350606092506103b791506103b2905083610630565b610673565b905060606103d8826001815181106103cb57fe5b602002602001015161073c565b90506060806103fb8f6040516103ed906110b9565b6040518091039020856107b8565b9150915061040a8160006108c9565b6005558051610420906006906020840190610c68565b5061042d60078888610ce6565b5050505050505050505050505050505050565b60005481565b6000818152600260205260409020545b919050565b60036020908152600091825260409182902080548351601f6002600019610100600186161502019093169290920491820184900484028101840190945280845290918301828280156104ee5780601f106104c3576101008083540402835291602001916104ee565b820191906000526020600020905b8154815290600101906020018083116104d157829003601f168201915b505050505081565b60055490565b6006805460408051602060026001851615610100026000190190941693909304601f810184900484028201840190925281815292918301828280156104ee5780601f106104c3576101008083540402835291602001916104ee565b6000949350505050565b50505050565b600090815260026020526040902054151590565b6004541590565b60055481565b61059184610567565b156105b75760405162461bcd60e51b81526004016105ae90611182565b60405180910390fd5b6000848152600260209081526040808320869055600390915290206105dd908383610d21565b507fe071861e96e2f86d7704105f1697b783ce582ea599692363bb7ec62945575fec8484848460405161061394939291906112f2565b60405180910390a150505050565b6001546001600160a01b031681565b610638610d8e565b81518061065a5750506040805180820190915260008082526020820152610456565b6040805180820190915260209384018152928301525090565b606061067e826108fe565b61068757600080fd5b600061069283610925565b90508067ffffffffffffffff811180156106ab57600080fd5b506040519080825280602002602001820160405280156106e557816020015b6106d2610d8e565b8152602001906001900390816106ca5790505b5091506106f0610da8565b6106f984610989565b905060005b610707826109c0565b1561073457610715826109df565b84828151811061072157fe5b60209081029190910101526001016106fe565b505050919050565b606061074782610a1f565b61075057600080fd5b60008061075c84610a45565b90925090508067ffffffffffffffff8111801561077857600080fd5b506040519080825280601f01601f1916602001820160405280156107a3576020820181803683370190505b5092506107b1828483610ab5565b5050919050565b60608060606107c96103b285610630565b905060606107ea826003815181106107dd57fe5b6020026020010151610673565b905060005b81518110156108a85760606108098383815181106107dd57fe5b90506000610825610820836000815181106103cb57fe5b610af3565b9050610837826001815181106107dd57fe5b9650610849826002815181106103cb57fe5b9550600061086a8860008151811061085d57fe5b6020026020010151610afa565b9050898114801561088c57508a6001600160a01b0316826001600160a01b0316145b1561089d57506108c1945050505050565b5050506001016107ef565b5060405162461bcd60e51b81526004016105ae906111b9565b935093915050565b600081602001835110156108ef5760405162461bcd60e51b81526004016105ae90611154565b50818101602001515b92915050565b600081602001516000141561091557506000610456565b50515160c060009190911a101590565b6000610930826108fe565b61093c57506000610456565b81518051600090811a919061095085610b05565b6020860151908301915082016000190160005b81831161097f5761097383610b83565b90920191600101610963565b9695505050505050565b610991610da8565b61099a826108fe565b6109a357600080fd5b60006109ae83610b05565b83519383529092016020820152919050565b60006109ca610d8e565b50508051602080820151915192015191011190565b6109e7610d8e565b6109f0826109c0565b156101215760208201516000610a0582610b83565b828452602080850182905292019184019190915250610456565b6000816020015160001415610a3657506000610456565b50515160c060009190911a1090565b600080610a5183610a1f565b610a5a57600080fd5b8251805160001a906080821015610a7857925060019150610ab09050565b60b8821015610a965760018560200151039250806001019350610aad565b602085015182820160b51901945082900360b60192505b50505b915091565b6020601f820104836020840160005b83811015610ae057602081028381015190830152600101610ac4565b5050505060008251602001830152505050565b6014015190565b60006108f882610c11565b6000816020015160001415610b1c57506000610456565b8151805160001a906080821015610b3857600092505050610456565b60b8821080610b53575060c08210158015610b53575060f882105b15610b6357600192505050610456565b60c0821015610b78575060b519019050610456565b5060f5190192915050565b8051600090811a6080811015610b9c5760019150610c0b565b60b8811015610bb157607e1981019150610c0b565b60c0811015610bda57600183015160b76020839003016101000a9004810160b519019150610c0b565b60f8811015610bef5760be1981019150610c0b565b600183015160f76020839003016101000a9004810160f5190191505b50919050565b6000610c1c82610a1f565b610c2557600080fd5b600080610c3184610a45565b90925090506020811115610c4457600080fd5b80610c5457600092505050610456565b806020036101000a82510492505050919050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10610ca957805160ff1916838001178555610cd6565b82800160010185558215610cd6579182015b82811115610cd6578251825591602001919060010190610cbb565b50610ce2929150610dc8565b5090565b828054828255906000526020600020908101928215610cd6579160200282015b82811115610cd6578235825591602001919060010190610d06565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10610d625782800160ff19823516178555610cd6565b82800160010185558215610cd65791820182811115610cd6578235825591602001919060010190610d06565b604051806040016040528060008152602001600081525090565b6040518060400160405280610dbb610d8e565b8152602001600081525090565b6102f391905b80821115610ce25760008155600101610dce565b80356001600160a01b03811681146108f857600080fd5b60008083601f840112610e0a578182fd5b50813567ffffffffffffffff811115610e21578182fd5b6020830191508360208083028501011115610e3b57600080fd5b9250929050565b60008083601f840112610e53578182fd5b50813567ffffffffffffffff811115610e6a578182fd5b602083019150836020828501011115610e3b57600080fd5b600060208284031215610e93578081fd5b610e9d8383610de2565b9392505050565b600080600060408486031215610eb8578182fd5b83359250602084013567ffffffffffffffff811115610ed5578283fd5b610ee186828701610e42565b9497909650939450505050565b600060208284031215610eff578081fd5b5035919050565b600080600080600080600080600080600060e08c8e031215610f26578687fd5b8b359a50610f378d60208e01610de2565b995060408c0135985067ffffffffffffffff8060608e01351115610f59578788fd5b610f698e60608f01358f01610e42565b909950975060808d0135811015610f7e578687fd5b610f8e8e60808f01358f01610df9565b909750955060a08d0135811015610fa3578485fd5b610fb38e60a08f01358f01610df9565b909550935060c08d0135811015610fc8578283fd5b50610fd98d60c08e01358e01610df9565b81935080925050509295989b509295989b9093969950565b60008060008060608587031215611006578384fd5b8435935060208501356001600160a01b0381168114611023578384fd5b9250604085013567ffffffffffffffff81111561103e578283fd5b61104a87828801610e42565b95989497509550505050565b6000806000806060858703121561106b578384fd5b8435935060208501359250604085013567ffffffffffffffff81111561103e578283fd5b60008284528282602086013780602084860101526020601f19601f85011685010190509392505050565b7f53746172742875696e743235362c75696e743235362c627974657329000000008152601c0190565b901515815260200190565b6000602080835283518082850152825b81811015611119578581018301518582016040015282016110fd565b8181111561112a5783604083870101525b50601f01601f1916929092016040019392505050565b6001600160a01b0391909116815260200190565b602080825260149082015273736c6963696e67206f7574206f662072616e676560601b604082015260600190565b6020808252601e908201527f5472616e73616374696f6e20616c726561647920726567697374657265640000604082015260600190565b60208082526025908201527f4e6f206576656e7420666f756e6420696e207472616e73616374696f6e20726560408201526418d95a5c1d60da1b606082015260800190565b90815260200190565b60008982526020898184015260a0604084015261122860a08401898b61108f565b83810360608501528681526001600160fb1b03871115611246578283fd5b8187028089848401370183810382016080850152818101859052604085830282018101919081019087855b888110156112de57828503603f190184528135368b9003601e19018112611296578788fd5b8a0180359067ffffffffffffffff8211156112af578889fd5b8136038c13156112bd578889fd5b6112ca87838a840161108f565b965050509285019290850190600101611271565b50929e9d5050505050505050505050505050565b60008582528460208301526060604083015261097f60608301848661108f56fea2646970667358221220b737c7bb8f97ad7d68008a6b7c248ec005744183bae69c58180a4177e446903364736f6c634300060b0033";

    public static final String FUNC_ACTIVECALL = "activeCall";

    public static final String FUNC_ACTIVECALLCROSSBLOCKCHAINTRANSACTIONID = "activeCallCrossBlockchainTransactionId";

    public static final String FUNC_ACTIVECALLROOTBLOCKCHAINID = "activeCallRootBlockchainId";

    public static final String FUNC_CALLGRAPHS = "callGraphs";

    public static final String FUNC_CLOSE = "close";

    public static final String FUNC_CROSSBLOCKCHAINCALL = "crossBlockchainCall";

    public static final String FUNC_CROSSBLOCKCHAINCALLRETURNSUINT256 = "crossBlockchainCallReturnsUint256";

    public static final String FUNC_CROSSBLOCKCHAINTRANSACTIONEXISTS = "crossBlockchainTransactionExists";

    public static final String FUNC_CROSSBLOCKCHAINTRANSACTIONTIMEOUT = "crossBlockchainTransactionTimeout";

    public static final String FUNC_GETACTIVECALLCROSSBLOCKCHAINTRANSACTIONID = "getActiveCallCrossBlockchainTransactionId";

    public static final String FUNC_GETACTIVECALLROOTBLOCKCHAINID = "getActiveCallRootBlockchainId";

    public static final String FUNC_ISSINGLEBLOCKCHAINCALL = "isSingleBlockchainCall";

    public static final String FUNC_LOCKCONTRACT = "lockContract";

    public static final String FUNC_MYBLOCKCHAINID = "myBlockchainId";

    public static final String FUNC_SEGMENT = "segment";

    public static final String FUNC_SIGNALLING = "signalling";

    public static final String FUNC_START = "start";

    public static final String FUNC_TIMEOUT = "timeout";

    public static final String FUNC_TXRECEIPTROOTSTORAGE = "txReceiptRootStorage";

    public static final Event CLOSE_EVENT = new Event("Close", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
    ;

    public static final Event SEGMENT_EVENT = new Event("Segment", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<DynamicArray<Uint256>>() {}, new TypeReference<DynamicArray<Address>>() {}, new TypeReference<DynamicBytes>() {}));
    ;

    public static final Event SIGNALLING_EVENT = new Event("Signalling", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
    ;

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

    public List<CloseEventResponse> getCloseEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(CLOSE_EVENT, transactionReceipt);
        ArrayList<CloseEventResponse> responses = new ArrayList<CloseEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            CloseEventResponse typedResponse = new CloseEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._crossBlockchainTransactionId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<CloseEventResponse> closeEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, CloseEventResponse>() {
            @Override
            public CloseEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(CLOSE_EVENT, log);
                CloseEventResponse typedResponse = new CloseEventResponse();
                typedResponse.log = log;
                typedResponse._crossBlockchainTransactionId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<CloseEventResponse> closeEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CLOSE_EVENT));
        return closeEventFlowable(filter);
    }

    public List<SegmentEventResponse> getSegmentEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(SEGMENT_EVENT, transactionReceipt);
        ArrayList<SegmentEventResponse> responses = new ArrayList<SegmentEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            SegmentEventResponse typedResponse = new SegmentEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._rootBlockchain = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse._crossBlockchainTransactionId = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse._callPath = (List<BigInteger>) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse._lockedContracts = (List<String>) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse._returnValue = (byte[]) eventValues.getNonIndexedValues().get(4).getValue();
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
                typedResponse._rootBlockchain = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse._crossBlockchainTransactionId = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse._callPath = (List<BigInteger>) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse._lockedContracts = (List<String>) eventValues.getNonIndexedValues().get(3).getValue();
                typedResponse._returnValue = (byte[]) eventValues.getNonIndexedValues().get(4).getValue();
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
            typedResponse._crossBlockchainTransactionId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
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
                typedResponse._crossBlockchainTransactionId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
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

    public RemoteFunctionCall<byte[]> activeCall() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ACTIVECALL, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicBytes>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public String getRLP_activeCall() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ACTIVECALL, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
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

    public RemoteFunctionCall<byte[]> callGraphs(BigInteger param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_CALLGRAPHS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicBytes>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public String getRLP_callGraphs(BigInteger param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CALLGRAPHS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public String getRLP_close(byte[] param0, byte[] param1) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CLOSE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(param0), 
                new org.web3j.abi.datatypes.DynamicBytes(param1)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<TransactionReceipt> crossBlockchainCall(BigInteger param0, String param1, byte[] param2) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CROSSBLOCKCHAINCALL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0), 
                new org.web3j.abi.datatypes.Address(160, param1), 
                new org.web3j.abi.datatypes.DynamicBytes(param2)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getRLP_crossBlockchainCall(BigInteger param0, String param1, byte[] param2) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CROSSBLOCKCHAINCALL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0), 
                new org.web3j.abi.datatypes.Address(160, param1), 
                new org.web3j.abi.datatypes.DynamicBytes(param2)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<BigInteger> crossBlockchainCallReturnsUint256(BigInteger param0, String param1, byte[] param2) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_CROSSBLOCKCHAINCALLRETURNSUINT256, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0), 
                new org.web3j.abi.datatypes.Address(160, param1), 
                new org.web3j.abi.datatypes.DynamicBytes(param2)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public String getRLP_crossBlockchainCallReturnsUint256(BigInteger param0, String param1, byte[] param2) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CROSSBLOCKCHAINCALLRETURNSUINT256, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0), 
                new org.web3j.abi.datatypes.Address(160, param1), 
                new org.web3j.abi.datatypes.DynamicBytes(param2)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<Boolean> crossBlockchainTransactionExists(BigInteger _crossBlockchainTransactionId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_CROSSBLOCKCHAINTRANSACTIONEXISTS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_crossBlockchainTransactionId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public String getRLP_crossBlockchainTransactionExists(BigInteger _crossBlockchainTransactionId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CROSSBLOCKCHAINTRANSACTIONEXISTS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_crossBlockchainTransactionId)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<BigInteger> crossBlockchainTransactionTimeout(BigInteger _crossBlockchainTransactionId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_CROSSBLOCKCHAINTRANSACTIONTIMEOUT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_crossBlockchainTransactionId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public String getRLP_crossBlockchainTransactionTimeout(BigInteger _crossBlockchainTransactionId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CROSSBLOCKCHAINTRANSACTIONTIMEOUT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_crossBlockchainTransactionId)), 
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

    public RemoteFunctionCall<TransactionReceipt> lockContract(String _contractToLock) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_LOCKCONTRACT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _contractToLock)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getRLP_lockContract(String _contractToLock) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_LOCKCONTRACT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _contractToLock)), 
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

    public RemoteFunctionCall<TransactionReceipt> segment(BigInteger _rootBlockchainId, String _rootCBCContract, byte[] _startEventTxReceiptRoot, byte[] _encodedStartTxReceipt, List<BigInteger> _proofOffsets, List<byte[]> _proof, List<BigInteger> _callPath) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SEGMENT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_rootBlockchainId), 
                new org.web3j.abi.datatypes.Address(160, _rootCBCContract), 
                new org.web3j.abi.datatypes.generated.Bytes32(_startEventTxReceiptRoot), 
                new org.web3j.abi.datatypes.DynamicBytes(_encodedStartTxReceipt), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.datatypes.generated.Uint256.class,
                        org.web3j.abi.Utils.typeMap(_proofOffsets, org.web3j.abi.datatypes.generated.Uint256.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.DynamicBytes>(
                        org.web3j.abi.datatypes.DynamicBytes.class,
                        org.web3j.abi.Utils.typeMap(_proof, org.web3j.abi.datatypes.DynamicBytes.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.datatypes.generated.Uint256.class,
                        org.web3j.abi.Utils.typeMap(_callPath, org.web3j.abi.datatypes.generated.Uint256.class))), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getRLP_segment(BigInteger _rootBlockchainId, String _rootCBCContract, byte[] _startEventTxReceiptRoot, byte[] _encodedStartTxReceipt, List<BigInteger> _proofOffsets, List<byte[]> _proof, List<BigInteger> _callPath) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SEGMENT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_rootBlockchainId), 
                new org.web3j.abi.datatypes.Address(160, _rootCBCContract), 
                new org.web3j.abi.datatypes.generated.Bytes32(_startEventTxReceiptRoot), 
                new org.web3j.abi.datatypes.DynamicBytes(_encodedStartTxReceipt), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.datatypes.generated.Uint256.class,
                        org.web3j.abi.Utils.typeMap(_proofOffsets, org.web3j.abi.datatypes.generated.Uint256.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.DynamicBytes>(
                        org.web3j.abi.datatypes.DynamicBytes.class,
                        org.web3j.abi.Utils.typeMap(_proof, org.web3j.abi.datatypes.DynamicBytes.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.datatypes.generated.Uint256.class,
                        org.web3j.abi.Utils.typeMap(_callPath, org.web3j.abi.datatypes.generated.Uint256.class))), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public String getRLP_signalling(byte[] param0, byte[] param1) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SIGNALLING, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(param0), 
                new org.web3j.abi.datatypes.DynamicBytes(param1)), 
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

    public RemoteFunctionCall<BigInteger> timeout(BigInteger param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_TIMEOUT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public String getRLP_timeout(BigInteger param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_TIMEOUT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<String> txReceiptRootStorage() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_TXRECEIPTROOTSTORAGE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public String getRLP_txReceiptRootStorage() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_TXRECEIPTROOTSTORAGE, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
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

    public static RemoteCall<CrossBlockchainControl> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, BigInteger _myBlockchainId, String _txReceiptRootStorage) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_myBlockchainId), 
                new org.web3j.abi.datatypes.Address(160, _txReceiptRootStorage)));
        return deployRemoteCall(CrossBlockchainControl.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<CrossBlockchainControl> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, BigInteger _myBlockchainId, String _txReceiptRootStorage) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_myBlockchainId), 
                new org.web3j.abi.datatypes.Address(160, _txReceiptRootStorage)));
        return deployRemoteCall(CrossBlockchainControl.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<CrossBlockchainControl> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger _myBlockchainId, String _txReceiptRootStorage) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_myBlockchainId), 
                new org.web3j.abi.datatypes.Address(160, _txReceiptRootStorage)));
        return deployRemoteCall(CrossBlockchainControl.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<CrossBlockchainControl> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger _myBlockchainId, String _txReceiptRootStorage) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_myBlockchainId), 
                new org.web3j.abi.datatypes.Address(160, _txReceiptRootStorage)));
        return deployRemoteCall(CrossBlockchainControl.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static class CloseEventResponse extends BaseEventResponse {
        public BigInteger _crossBlockchainTransactionId;
    }

    public static class SegmentEventResponse extends BaseEventResponse {
        public BigInteger _rootBlockchain;

        public BigInteger _crossBlockchainTransactionId;

        public List<BigInteger> _callPath;

        public List<String> _lockedContracts;

        public byte[] _returnValue;
    }

    public static class SignallingEventResponse extends BaseEventResponse {
        public BigInteger _crossBlockchainTransactionId;
    }

    public static class StartEventResponse extends BaseEventResponse {
        public BigInteger _crossBlockchainTransactionId;

        public BigInteger _timeout;

        public byte[] _callGraph;
    }
}
