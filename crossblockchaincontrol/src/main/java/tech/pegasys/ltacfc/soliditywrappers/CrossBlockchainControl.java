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
public class CrossBlockchainControl extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b5060405161166938038061166983398101604081905261002f91610059565b600091909155600180546001600160a01b0319166001600160a01b03909216919091179055610094565b6000806040838503121561006b578182fd5b825160208401519092506001600160a01b0381168114610089578182fd5b809150509250929050565b6115c6806100a36000396000f3fe608060405234801561001057600080fd5b506004361061012c5760003560e01c80635c27d307116100ad5780639ad8a5b6116100715780639ad8a5b61461021e578063b4c3b7561461023e578063d5d6b09c14610246578063df1bba011461024e578063f4c1ef2f146102615761012c565b80635c27d307146101c8578063653cf5a6146101dd57806366b79f5a146101f05780638e22d534146101f857806392b2c3351461020b5761012c565b806336646ef4116100f457806336646ef4146101925780633ab561271461016f5780633cdc71041461019a578063439160df146101ad5780634c97042e146101b55761012c565b806308148f7a146101315780630df973611461015a5780631a26720c1461016f5780632af6cdf014610182578063336d5b091461018a575b600080fd5b61014461013f36600461112e565b610276565b6040516101519190611449565b60405180910390f35b61016d6101683660046110c2565b610288565b005b61016d61017d3660046110e4565b6102f7565b6101446102fc565b610144610302565b610144610309565b61016d6101a8366004611146565b61030f565b6101446104e1565b6101446101c336600461112e565b6104e7565b6101d06104fc565b6040516101519190611378565b6101d06101eb36600461112e565b61058a565b6101446105f2565b610144610206366004611231565b6105f8565b61016d610219366004611231565b610602565b61023161022c36600461112e565b610608565b604051610151919061136d565b61023161061c565b610144610623565b61016d61025c366004611296565b610629565b6102696106c2565b604051610151919061138b565b60026020526000908152604090205481565b6001600160a01b03811660009081526009602052604090205460ff166102f457600a80546001810182556000919091527fc65a7bb8d6351c1cf70c95a316cc6a92839c986682d98bc35f958f4883f9d2a80180546001600160a01b0319166001600160a01b0383161790555b50565b505050565b60045481565b6004545b90565b60075481565b6001546040516306e3dd6f60e11b81526001600160a01b0390911690630dc7bade9061034d908e908d908d908d908d908d908d908d90600401611485565b600060405180830381600087803b15801561036757600080fd5b505af115801561037b573d6000803e3d6000fd5b5050505060608089898080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929350606092506103d191506103cc9050836106d1565b610714565b905060606103f2826001815181106103e557fe5b60200260200101516107dd565b90506104128e60405161040490611344565b604051809103902083610859565b90508094505050505061042681600061096a565b60055561043481602061096a565b600755600061044482606061096a565b905060606104548360808461099f565b805190915061046a906006906020840190610ea8565b5060048e905561047c60088686610f26565b50600080606061048d848989610a3f565b9250925092507f242105695377d1e0b400ef1c2922c3d732f44bc16f63816d1b4af3da6db5879b8383836040516104c693929190611452565b60405180910390a15050505050505050505050505050505050565b60005481565b6000818152600260205260409020545b919050565b6006805460408051602060026001851615610100026000190190941693909304601f810184900484028201840190925281815292918301828280156105825780601f1061055757610100808354040283529160200191610582565b820191906000526020600020905b81548152906001019060200180831161056557829003601f168201915b505050505081565b60036020908152600091825260409182902080548351601f6002600019610100600186161502019093169290920491820184900484028101840190945280845290918301828280156105825780601f1061055757610100808354040283529160200191610582565b60055490565b6000949350505050565b50505050565b600090815260026020526040902054151590565b6004541590565b60055481565b61063284610608565b156106585760405162461bcd60e51b815260040161064f906113cd565b60405180910390fd5b60008481526002602090815260408083208690556003909152902061067e908383610f61565b507fe071861e96e2f86d7704105f1697b783ce582ea599692363bb7ec62945575fec848484846040516106b49493929190611570565b60405180910390a150505050565b6001546001600160a01b031681565b6106d9610fce565b8151806106fb57505060408051808201909152600080825260208201526104f7565b6040805180820190915260209384018152928301525090565b606061071f82610b01565b61072857600080fd5b600061073383610b28565b90508067ffffffffffffffff8111801561074c57600080fd5b5060405190808252806020026020018201604052801561078657816020015b610773610fce565b81526020019060019003908161076b5790505b509150610791610fe8565b61079a84610b8c565b905060005b6107a882610bc3565b156107d5576107b682610be2565b8482815181106107c257fe5b602090810291909101015260010161079f565b505050919050565b60606107e882610c22565b6107f157600080fd5b6000806107fd84610c48565b90925090508067ffffffffffffffff8111801561081957600080fd5b506040519080825280601f01601f191660200182016040528015610844576020820181803683370190505b509250610852828483610cb8565b5050919050565b606080606061086a6103cc856106d1565b9050606061088b8260038151811061087e57fe5b6020026020010151610714565b905060005b81518110156109495760606108aa83838151811061087e57fe5b905060006108c66108c1836000815181106103e557fe5b610cf6565b90506108d88260018151811061087e57fe5b96506108ea826002815181106103e557fe5b9550600061090b886000815181106108fe57fe5b6020026020010151610cfd565b9050898114801561092d57508a6001600160a01b0316826001600160a01b0316145b1561093e5750610962945050505050565b505050600101610890565b5060405162461bcd60e51b815260040161064f90611404565b935093915050565b600081602001835110156109905760405162461bcd60e51b815260040161064f9061139f565b50818101602001515b92915050565b6060808267ffffffffffffffff811180156109b957600080fd5b506040519080825280601f01601f1916602001820160405280156109e4576020820181803683370190505b50905060005b83811015610a36578581860181518110610a0057fe5b602001015160f81c60f81b828281518110610a1757fe5b60200101906001600160f81b031916908160001a9053506001016109ea565b50949350505050565b600080606080610a516103cc886106d1565b905060005b6000198601811015610a8d57610a8382888884818110610a7257fe5b905060200201358151811061087e57fe5b9150600101610a56565b506060610aa48288886000198101818110610a7257fe5b9050610ac381600081518110610ab657fe5b6020026020010151610d04565b9450610ae281600181518110610ad557fe5b6020026020010151610d5b565b9350610af4816002815181106103e557fe5b9250505093509350939050565b6000816020015160001415610b18575060006104f7565b50515160c060009190911a101590565b6000610b3382610b01565b610b3f575060006104f7565b81518051600090811a9190610b5385610d9c565b6020860151908301915082016000190160005b818311610b8257610b7683610e1a565b90920191600101610b66565b9695505050505050565b610b94610fe8565b610b9d82610b01565b610ba657600080fd5b6000610bb183610d9c565b83519383529092016020820152919050565b6000610bcd610fce565b50508051602080820151915192015191011190565b610bea610fce565b610bf382610bc3565b1561012c5760208201516000610c0882610e1a565b8284526020808501829052920191840191909152506104f7565b6000816020015160001415610c39575060006104f7565b50515160c060009190911a1090565b600080610c5483610c22565b610c5d57600080fd5b8251805160001a906080821015610c7b57925060019150610cb39050565b60b8821015610c995760018560200151039250806001019350610cb0565b602085015182820160b51901945082900360b60192505b50505b915091565b6020601f820104836020840160005b83811015610ce357602081028381015190830152600101610cc7565b5050505060008251602001830152505050565b6014015190565b6000610999825b6000610d0f82610c22565b610d1857600080fd5b600080610d2484610c48565b90925090506020811115610d3757600080fd5b80610d47576000925050506104f7565b806020036101000a82510492505050919050565b6000610d6682610c22565b610d6f57600080fd5b600080610d7b84610c48565b909250905060148114610d8d57600080fd5b5051600160601b900492915050565b6000816020015160001415610db3575060006104f7565b8151805160001a906080821015610dcf576000925050506104f7565b60b8821080610dea575060c08210158015610dea575060f882105b15610dfa576001925050506104f7565b60c0821015610e0f575060b5190190506104f7565b5060f5190192915050565b8051600090811a6080811015610e335760019150610ea2565b60b8811015610e4857607e1981019150610ea2565b60c0811015610e7157600183015160b76020839003016101000a9004810160b519019150610ea2565b60f8811015610e865760be1981019150610ea2565b600183015160f76020839003016101000a9004810160f5190191505b50919050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10610ee957805160ff1916838001178555610f16565b82800160010185558215610f16579182015b82811115610f16578251825591602001919060010190610efb565b50610f22929150611008565b5090565b828054828255906000526020600020908101928215610f16579160200282015b82811115610f16578235825591602001919060010190610f46565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10610fa25782800160ff19823516178555610f16565b82800160010185558215610f165791820182811115610f16578235825591602001919060010190610f46565b604051806040016040528060008152602001600081525090565b6040518060400160405280610ffb610fce565b8152602001600081525090565b61030691905b80821115610f22576000815560010161100e565b80356001600160a01b038116811461099957600080fd5b60008083601f84011261104a578182fd5b50813567ffffffffffffffff811115611061578182fd5b602083019150836020808302850101111561107b57600080fd5b9250929050565b60008083601f840112611093578182fd5b50813567ffffffffffffffff8111156110aa578182fd5b60208301915083602082850101111561107b57600080fd5b6000602082840312156110d3578081fd5b6110dd8383611022565b9392505050565b6000806000604084860312156110f8578182fd5b83359250602084013567ffffffffffffffff811115611115578283fd5b61112186828701611082565b9497909650939450505050565b60006020828403121561113f578081fd5b5035919050565b600080600080600080600080600080600060e08c8e031215611166578687fd5b8b359a506111778d60208e01611022565b995060408c0135985067ffffffffffffffff8060608e01351115611199578788fd5b6111a98e60608f01358f01611082565b909950975060808d01358110156111be578687fd5b6111ce8e60808f01358f01611039565b909750955060a08d01358110156111e3578485fd5b6111f38e60a08f01358f01611039565b909550935060c08d0135811015611208578283fd5b506112198d60c08e01358e01611039565b81935080925050509295989b509295989b9093969950565b60008060008060608587031215611246578384fd5b8435935060208501356001600160a01b0381168114611263578384fd5b9250604085013567ffffffffffffffff81111561127e578283fd5b61128a87828801611082565b95989497509550505050565b600080600080606085870312156112ab578384fd5b8435935060208501359250604085013567ffffffffffffffff81111561127e578283fd5b60008284528282602086013780602084860101526020601f19601f85011685010190509392505050565b60008151808452815b8181101561131e57602081850181015186830182015201611302565b8181111561132f5782602083870101525b50601f01601f19169290920160200192915050565b7f53746172742875696e743235362c75696e743235362c627974657329000000008152601c0190565b901515815260200190565b6000602082526110dd60208301846112f9565b6001600160a01b0391909116815260200190565b602080825260149082015273736c6963696e67206f7574206f662072616e676560601b604082015260600190565b6020808252601e908201527f5472616e73616374696f6e20616c726561647920726567697374657265640000604082015260600190565b60208082526025908201527f4e6f206576656e7420666f756e6420696e207472616e73616374696f6e20726560408201526418d95a5c1d60da1b606082015260800190565b90815260200190565b8381526001600160a01b038316602082015260606040820181905260009061147c908301846112f9565b95945050505050565b60008982526020898184015260a060408401526114a660a08401898b6112cf565b83810360608501528681526001600160fb1b038711156114c4578283fd5b8187028089848401370183810382016080850152818101859052604085830282018101919081019087855b8881101561155c57828503603f190184528135368b9003601e19018112611514578788fd5b8a0180359067ffffffffffffffff82111561152d578889fd5b8136038c131561153b578889fd5b61154887838a84016112cf565b9650505092850192908501906001016114ef565b50929e9d5050505050505050505050505050565b600085825284602083015260606040830152610b826060830184866112cf56fea26469706673582212200cc85cd75a458b41c3812e041f76c24b767c5bde54e03afd3e94eb285cad61e264736f6c634300060b0033";

    public static final String FUNC_ACTIVECALLCROSSBLOCKCHAINTRANSACTIONID = "activeCallCrossBlockchainTransactionId";

    public static final String FUNC_ACTIVECALLGRAPH = "activeCallGraph";

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

    public static final String FUNC_TEMPACTIVETIMEOUT = "tempActiveTimeout";

    public static final String FUNC_TIMEOUT = "timeout";

    public static final String FUNC_TXRECEIPTROOTSTORAGE = "txReceiptRootStorage";

    public static final Event CLOSE_EVENT = new Event("Close", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
    ;

    public static final Event DUMP_EVENT = new Event("Dump", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<DynamicBytes>() {}));
    ;

    public static final Event SEGMENT_EVENT = new Event("Segment", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Bytes32>() {}, new TypeReference<DynamicArray<Uint256>>() {}, new TypeReference<DynamicArray<Address>>() {}, new TypeReference<DynamicBytes>() {}));
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

    public List<DumpEventResponse> getDumpEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(DUMP_EVENT, transactionReceipt);
        ArrayList<DumpEventResponse> responses = new ArrayList<DumpEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            DumpEventResponse typedResponse = new DumpEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._bcId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse._addr = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse._functionCall = (byte[]) eventValues.getNonIndexedValues().get(2).getValue();
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
                typedResponse._bcId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse._addr = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse._functionCall = (byte[]) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<DumpEventResponse> dumpEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(DUMP_EVENT));
        return dumpEventFlowable(filter);
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
                typedResponse._crossBlockchainTransactionId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse._hashOfCallGraph = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
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

    public RemoteFunctionCall<BigInteger> tempActiveTimeout() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_TEMPACTIVETIMEOUT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public String getRLP_tempActiveTimeout() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_TEMPACTIVETIMEOUT, 
                Arrays.<Type>asList(), 
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

    public static class DumpEventResponse extends BaseEventResponse {
        public BigInteger _bcId;

        public String _addr;

        public byte[] _functionCall;
    }

    public static class SegmentEventResponse extends BaseEventResponse {
        public BigInteger _crossBlockchainTransactionId;

        public byte[] _hashOfCallGraph;

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
