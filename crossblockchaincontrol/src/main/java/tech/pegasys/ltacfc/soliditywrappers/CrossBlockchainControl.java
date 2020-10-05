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
import org.web3j.abi.datatypes.DynamicStruct;
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
    public static final String BINARY = "608060405234801561001057600080fd5b50604051611b78380380611b7883398101604081905261002f91610059565b600091909155600180546001600160a01b0319166001600160a01b03909216919091179055610094565b6000806040838503121561006b578182fd5b825160208401519092506001600160a01b0381168114610089578182fd5b809150509250929050565b611ad5806100a36000396000f3fe608060405234801561001057600080fd5b50600436106101375760003560e01c80635c27d307116100b85780639ad8a5b61161007c5780639ad8a5b614610234578063b4c3b75614610254578063d5d6b09c1461025c578063df1bba0114610264578063f3a90df914610277578063f4c1ef2f1461028a57610137565b80635c27d307146101de578063653cf5a6146101f357806366b79f5a146102065780638e22d5341461020e57806392b2c3351461022157610137565b806339ce107e116100ff57806339ce107e1461019d5780633ab56127146101655780633cdc7104146101b0578063439160df146101c35780634c97042e146101cb57610137565b806308148f7a1461013c5780631a26720c1461016557806321de48211461017a5780632af6cdf01461018d578063336d5b0914610195575b600080fd5b61014f61014a366004611523565b61029f565b60405161015c91906118a4565b60405180910390f35b610178610173366004611455565b6102b1565b005b6101786101883660046113f5565b6102b6565b61014f6102f5565b61014f6102fb565b6101786101ab3660046113d3565b610301565b6101786101be36600461153b565b610388565b61014f6106e7565b61014f6101d9366004611523565b6106ed565b6101e6610702565b60405161015c919061176f565b6101e6610201366004611523565b610790565b61014f6107f8565b61014f61021c366004611626565b6107fe565b61017861022f366004611626565b610808565b610247610242366004611523565b61080e565b60405161015c9190611764565b610247610822565b61014f610829565b61017861027236600461167f565b61082f565b61017861028536600461149f565b6108bf565b6102926108ff565b60405161015c9190611782565b60026020526000908152604090205481565b505050565b7fe6763dd99bf894d72f3499dd572aa42876eae7ae028c32fff21654e1bbc4c807601160016040516102e9929190611796565b60405180910390a15050565b60045481565b60045490565b6001600160a01b03811660009081526008602052604090205460ff16610385576009805460018082019092557f6e1540171b6c0c960b71a7020d9f60077f6af931a8bbf590da0223dacf75c7af0180546001600160a01b0319166001600160a01b0384169081179091556000908152600860205260409020805460ff191690911790555b50565b6001546040516306e3dd6f60e11b81526001600160a01b0390911690630dc7bade906103c6908e908d908d908d908d908d908d908d90600401611979565b60206040518083038186803b1580156103de57600080fd5b505afa1580156103f2573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906104169190611435565b5060608089898080601f01602080910402602001604051908101604052809392919081815260200183838082843760009201919091525092935060609250610469915061046490508361090e565b610951565b9050606061048a8260018151811061047d57fe5b6020026020010151610a1a565b90506104b78e7fe071861e96e2f86d7704105f1697b783ce582ea599692363bb7ec62945575fec83610a96565b9050809450505050506104cb816000610ba6565b60055560006104db826060610ba6565b905060606104eb83608084610bdb565b805190915061050190600690602084019061114b565b5060048e9055610513600786866111c9565b506000806060610524848989610c7b565b9250925092507f242105695377d1e0b400ef1c2922c3d732f44bc16f63816d1b4af3da6db5879b83838360405161055d939291906118ad565b60405180910390a1600054831461058f5760405162461bcd60e51b8152600401610586906117d4565b60405180910390fd5b6105998282610d3d565b60006060836001600160a01b0316836040516105b59190611748565b6000604051808303816000865af19150503d80600081146105f2576040519150601f19603f3d011682016040523d82523d6000602084013e6105f7565b606091505b5087516020890120600554604051939550919350917fb01557f1f634b7c5072ab5e36d07a2355ef819faca5a3d321430d71987155b8f916106449184908f908f906009908a908a906118e0565b60405180910390a1600560009055600460009055600660006106669190611204565b61067260076000611248565b60005b6009548110156106c457600860006009838154811061069057fe5b60009182526020808320909101546001600160a01b031683528201929092526040019020805460ff19169055600101610675565b506106d160096000611248565b5050505050505050505050505050505050505050565b60005481565b6000818152600260205260409020545b919050565b6006805460408051602060026001851615610100026000190190941693909304601f810184900484028201840190925281815292918301828280156107885780601f1061075d57610100808354040283529160200191610788565b820191906000526020600020905b81548152906001019060200180831161076b57829003601f168201915b505050505081565b60036020908152600091825260409182902080548351601f6002600019610100600186161502019093169290920491820184900484028101840190945280845290918301828280156107885780601f1061075d57610100808354040283529160200191610788565b60055490565b6000949350505050565b50505050565b600090815260026020526040902054151590565b6004541590565b60055481565b6108388461080e565b156108555760405162461bcd60e51b815260040161058690611828565b60008481526002602090815260408083208690556003909152902061087b908383611266565b507fe071861e96e2f86d7704105f1697b783ce582ea599692363bb7ec62945575fec848484846040516108b19493929190611a47565b60405180910390a150505050565b7fe6763dd99bf894d72f3499dd572aa42876eae7ae028c32fff21654e1bbc4c807600d60016040516108f2929190611796565b60405180910390a1505050565b6001546001600160a01b031681565b6109166112d3565b81518061093857505060408051808201909152600080825260208201526106fd565b6040805180820190915260209384018152928301525090565b606061095c82610da4565b61096557600080fd5b600061097083610dcb565b90508067ffffffffffffffff8111801561098957600080fd5b506040519080825280602002602001820160405280156109c357816020015b6109b06112d3565b8152602001906001900390816109a85790505b5091506109ce6112ed565b6109d784610e2f565b905060005b6109e582610e66565b15610a12576109f382610e85565b8482815181106109ff57fe5b60209081029190910101526001016109dc565b505050919050565b6060610a2582610ec5565b610a2e57600080fd5b600080610a3a84610eeb565b90925090508067ffffffffffffffff81118015610a5657600080fd5b506040519080825280601f01601f191660200182016040528015610a81576020820181803683370190505b509250610a8f828483610f5b565b5050919050565b6060806060610aa76104648561090e565b90506060610ac882600381518110610abb57fe5b6020026020010151610951565b905060005b8151811015610b85576060610ae7838381518110610abb57fe5b90506000610b03610afe8360008151811061047d57fe5b610f99565b9050610b1582600181518110610abb57fe5b9650610b278260028151811061047d57fe5b95506000610b4888600081518110610b3b57fe5b6020026020010151610fa0565b90508981148015610b6a57508a6001600160a01b0316826001600160a01b0316145b15610b7a57505050505050610b9e565b505050600101610acd565b5060405162461bcd60e51b81526004016105869061185f565b935093915050565b60008160200183511015610bcc5760405162461bcd60e51b8152600401610586906117a6565b50818101602001515b92915050565b6060808267ffffffffffffffff81118015610bf557600080fd5b506040519080825280601f01601f191660200182016040528015610c20576020820181803683370190505b50905060005b83811015610c72578581860181518110610c3c57fe5b602001015160f81c60f81b828281518110610c5357fe5b60200101906001600160f81b031916908160001a905350600101610c26565b50949350505050565b600080606080610c8d6104648861090e565b905060005b6000198601811015610cc957610cbf82888884818110610cae57fe5b9050602002013581518110610abb57fe5b9150600101610c92565b506060610ce08288886000198101818110610cae57fe5b9050610cff81600081518110610cf257fe5b6020026020010151610fa7565b9450610d1e81600181518110610d1157fe5b6020026020010151610ffe565b9350610d308160028151811061047d57fe5b9250505093509350939050565b60006060836001600160a01b031683604051610d599190611748565b6000604051808303816000865af19150503d8060008114610d96576040519150601f19603f3d011682016040523d82523d6000602084013e610d9b565b606091505b50505050505050565b6000816020015160001415610dbb575060006106fd565b50515160c060009190911a101590565b6000610dd682610da4565b610de2575060006106fd565b81518051600090811a9190610df68561103f565b6020860151908301915082016000190160005b818311610e2557610e19836110bd565b90920191600101610e09565b9695505050505050565b610e376112ed565b610e4082610da4565b610e4957600080fd5b6000610e548361103f565b83519383529092016020820152919050565b6000610e706112d3565b50508051602080820151915192015191011190565b610e8d6112d3565b610e9682610e66565b156101375760208201516000610eab826110bd565b8284526020808501829052920191840191909152506106fd565b6000816020015160001415610edc575060006106fd565b50515160c060009190911a1090565b600080610ef783610ec5565b610f0057600080fd5b8251805160001a906080821015610f1e57925060019150610f569050565b60b8821015610f3c5760018560200151039250806001019350610f53565b602085015182820160b51901945082900360b60192505b50505b915091565b6020601f820104836020840160005b83811015610f8657602081028381015190830152600101610f6a565b5050505060008251602001830152505050565b6014015190565b6000610bd5825b6000610fb282610ec5565b610fbb57600080fd5b600080610fc784610eeb565b90925090506020811115610fda57600080fd5b80610fea576000925050506106fd565b806020036101000a82510492505050919050565b600061100982610ec5565b61101257600080fd5b60008061101e84610eeb565b90925090506014811461103057600080fd5b5051600160601b900492915050565b6000816020015160001415611056575060006106fd565b8151805160001a906080821015611072576000925050506106fd565b60b882108061108d575060c0821015801561108d575060f882105b1561109d576001925050506106fd565b60c08210156110b2575060b5190190506106fd565b5060f5190192915050565b8051600090811a60808110156110d65760019150611145565b60b88110156110eb57607e1981019150611145565b60c081101561111457600183015160b76020839003016101000a9004810160b519019150611145565b60f88110156111295760be1981019150611145565b600183015160f76020839003016101000a9004810160f5190191505b50919050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061118c57805160ff19168380011785556111b9565b828001600101855582156111b9579182015b828111156111b957825182559160200191906001019061119e565b506111c592915061130d565b5090565b8280548282559060005260206000209081019282156111b9579160200282015b828111156111b95782358255916020019190600101906111e9565b50805460018160011615610100020316600290046000825580601f1061122a5750610385565b601f016020900490600052602060002090810190610385919061130d565b5080546000825590600052602060002090810190610385919061130d565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106112a75782800160ff198235161785556111b9565b828001600101855582156111b957918201828111156111b95782358255916020019190600101906111e9565b604051806040016040528060008152602001600081525090565b60405180604001604052806113006112d3565b8152602001600081525090565b5b808211156111c5576000815560010161130e565b80356001600160a01b0381168114610bd557600080fd5b60008083601f84011261134a578182fd5b50813567ffffffffffffffff811115611361578182fd5b602083019150836020808302850101111561137b57600080fd5b9250929050565b60008083601f840112611393578182fd5b50813567ffffffffffffffff8111156113aa578182fd5b60208301915083602082850101111561137b57600080fd5b600060c08284031215611145578081fd5b6000602082840312156113e4578081fd5b6113ee8383611322565b9392505050565b60008060208385031215611407578081fd5b823567ffffffffffffffff81111561141d578182fd5b61142985828601611339565b90969095509350505050565b600060208284031215611446578081fd5b815180151581146113ee578182fd5b600080600060408486031215611469578081fd5b83359250602084013567ffffffffffffffff811115611486578182fd5b61149286828701611382565b9497909650939450505050565b6000806000606084860312156114b3578283fd5b833567ffffffffffffffff808211156114ca578485fd5b6114d6878388016113c2565b945060208601359150808211156114eb578384fd5b6114f7878388016113c2565b9350604086013591508082111561150c578283fd5b50611519868287016113c2565b9150509250925092565b600060208284031215611534578081fd5b5035919050565b600080600080600080600080600080600060e08c8e03121561155b578687fd5b8b359a5061156c8d60208e01611322565b995060408c0135985067ffffffffffffffff8060608e0135111561158e578788fd5b61159e8e60608f01358f01611382565b909950975060808d01358110156115b3578687fd5b6115c38e60808f01358f01611339565b909750955060a08d01358110156115d8578485fd5b6115e88e60a08f01358f01611339565b909550935060c08d01358110156115fd578283fd5b5061160e8d60c08e01358e01611339565b81935080925050509295989b509295989b9093969950565b6000806000806060858703121561163b578384fd5b8435935061164c8660208701611322565b9250604085013567ffffffffffffffff811115611667578283fd5b61167387828801611382565b95989497509550505050565b60008060008060608587031215611694578384fd5b8435935060208501359250604085013567ffffffffffffffff811115611667578283fd5b81835260006001600160fb1b038311156116d0578081fd5b6020830280836020870137939093016020019283525090919050565b15159052565b60008284528282602086013780602084860101526020601f19601f85011685010190509392505050565b60008151808452611734816020860160208601611a73565b601f01601f19169290920160200192915050565b6000825161175a818460208701611a73565b9190910192915050565b901515815260200190565b6000602082526113ee602083018461171c565b6001600160a01b0391909116815260200190565b9182521515602082015260400190565b602080825260149082015273736c6963696e67206f7574206f662072616e676560601b604082015260600190565b60208082526034908201527f54617267657420626c6f636b636861696e20696420646f6573206e6f74206d616040820152731d18da081b5e48189b1bd8dad8da185a5b881a5960621b606082015260800190565b6020808252601e908201527f5472616e73616374696f6e20616c726561647920726567697374657265640000604082015260600190565b60208082526025908201527f4e6f206576656e7420666f756e6420696e207472616e73616374696f6e20726560408201526418d95a5c1d60da1b606082015260800190565b90815260200190565b8381526001600160a01b03831660208201526060604082018190526000906118d79083018461171c565b95945050505050565b60008882526020888184015260c0604084015261190160c08401888a6116b8565b8381036060850152865480825287845282842091830190845b81811015611948578354611936906001600160a01b0316611a67565b8352600193840193928501920161191a565b505061195760808601886116ec565b84810360a0860152611969818761171c565b9c9b505050505050505050505050565b60008982526020898184015260a0604084015261199a60a08401898b6116f2565b83810360608501526119ad81888a6116b8565b848103608086015285815290508181018286028201830187855b88811015611a3357848303601f190184528135368b9003601e190181126119ec578788fd5b8a01803567ffffffffffffffff811115611a04578889fd5b8036038c1315611a12578889fd5b611a1f85828a85016116f2565b9588019594505050908501906001016119c7565b50909e9d5050505050505050505050505050565b600085825284602083015260606040830152610e256060830184866116f2565b6001600160a01b031690565b60005b83811015611a8e578181015183820152602001611a76565b83811115610808575050600091015256fea264697066735822122082fd09f149aa0aa91aa9661e882abfe8b797e364e4b6ec068b6140a5a373050264736f6c63430007010033";

    public static final String FUNC_ACTIVECALLCROSSBLOCKCHAINTRANSACTIONID = "activeCallCrossBlockchainTransactionId";

    public static final String FUNC_ACTIVECALLGRAPH = "activeCallGraph";

    public static final String FUNC_ACTIVECALLROOTBLOCKCHAINID = "activeCallRootBlockchainId";

    public static final String FUNC_ADDTOLISTOFLOCKEDCONTRACTS = "addToListOfLockedContracts";

    public static final String FUNC_CALLGRAPHS = "callGraphs";

    public static final String FUNC_CLOSE = "close";

    public static final String FUNC_CROSSBLOCKCHAINCALL = "crossBlockchainCall";

    public static final String FUNC_CROSSBLOCKCHAINCALLRETURNSUINT256 = "crossBlockchainCallReturnsUint256";

    public static final String FUNC_CROSSBLOCKCHAINTRANSACTIONEXISTS = "crossBlockchainTransactionExists";

    public static final String FUNC_CROSSBLOCKCHAINTRANSACTIONTIMEOUT = "crossBlockchainTransactionTimeout";

    public static final String FUNC_GETACTIVECALLCROSSBLOCKCHAINTRANSACTIONID = "getActiveCallCrossBlockchainTransactionId";

    public static final String FUNC_GETACTIVECALLROOTBLOCKCHAINID = "getActiveCallRootBlockchainId";

    public static final String FUNC_ISSINGLEBLOCKCHAINCALL = "isSingleBlockchainCall";

    public static final String FUNC_MYBLOCKCHAINID = "myBlockchainId";

    public static final String FUNC_ROOT = "root";

    public static final String FUNC_ROOT1 = "root1";

    public static final String FUNC_SEGMENT = "segment";

    public static final String FUNC_SIGNALLING = "signalling";

    public static final String FUNC_START = "start";

    public static final String FUNC_TIMEOUT = "timeout";

    public static final String FUNC_TXRECEIPTROOTSTORAGE = "txReceiptRootStorage";

    public static final Event CLOSE_EVENT = new Event("Close", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
    ;

    public static final Event DUMP_EVENT = new Event("Dump", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<DynamicBytes>() {}));
    ;

    public static final Event ROOT_EVENT = new Event("Root", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Bool>() {}));
    ;

    public static final Event SEGMENT_EVENT = new Event("Segment", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Bytes32>() {}, new TypeReference<DynamicArray<Uint256>>() {}, new TypeReference<DynamicArray<Address>>() {}, new TypeReference<Bool>() {}, new TypeReference<DynamicBytes>() {}));
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

    public RemoteFunctionCall<TransactionReceipt> root(Info _start, Info _seg0, Info _seg1) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ROOT, 
                Arrays.<Type>asList(_start, 
                _seg0, 
                _seg1), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getRLP_root(Info _start, Info _seg0, Info _seg1) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ROOT, 
                Arrays.<Type>asList(_start, 
                _seg0, 
                _seg1), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<TransactionReceipt> root1(List<List<BigInteger>> _start) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ROOT1, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.DynamicArray>(
                        org.web3j.abi.datatypes.DynamicArray.class,
                        org.web3j.abi.Utils.typeMap(_start, org.web3j.abi.datatypes.DynamicArray.class,
                org.web3j.abi.datatypes.generated.Uint256.class))), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getRLP_root1(List<List<BigInteger>> _start) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ROOT1, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.DynamicArray>(
                        org.web3j.abi.datatypes.DynamicArray.class,
                        org.web3j.abi.Utils.typeMap(_start, org.web3j.abi.datatypes.DynamicArray.class,
                org.web3j.abi.datatypes.generated.Uint256.class))), 
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

    public static class Info extends DynamicStruct {
        public BigInteger blockchainId;

        public String cBCContract;

        public byte[] txReceiptRoot;

        public byte[] txReceipt;

        public List<BigInteger> proofOffset;

        public List<byte[]> proof;

        public Info(BigInteger blockchainId, String cBCContract, byte[] txReceiptRoot, byte[] txReceipt, List<BigInteger> proofOffset, List<byte[]> proof) {
            super(new org.web3j.abi.datatypes.generated.Uint256(blockchainId),new org.web3j.abi.datatypes.Address(cBCContract),new org.web3j.abi.datatypes.generated.Bytes32(txReceiptRoot),new org.web3j.abi.datatypes.DynamicBytes(txReceipt),new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(org.web3j.abi.datatypes.generated.Uint256.class, convertTo_uint256(proofOffset)),new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.DynamicBytes>(org.web3j.abi.datatypes.DynamicBytes.class, convertTo_bytes(proof)));
            this.blockchainId = blockchainId;
            this.cBCContract = cBCContract;
            this.txReceiptRoot = txReceiptRoot;
            this.txReceipt = txReceipt;
            this.proofOffset = proofOffset;
            this.proof = proof;
        }

        public Info(Uint256 blockchainId, Address cBCContract, Bytes32 txReceiptRoot, DynamicBytes txReceipt, DynamicArray<Uint256> proofOffset, DynamicArray<DynamicBytes> proof) {
            super(blockchainId,cBCContract,txReceiptRoot,txReceipt,proofOffset,proof);
            this.blockchainId = blockchainId.getValue();
            this.cBCContract = cBCContract.getValue();
            this.txReceiptRoot = txReceiptRoot.getValue();
            this.txReceipt = txReceipt.getValue();
            this.proofOffset = new java.util.ArrayList<>();;
            for (org.web3j.abi.datatypes.generated.Uint256 z: proofOffset.getValue()) {;
            this.proofOffset.add(z.getValue());;
            };
            this.proof = new java.util.ArrayList<>();;
            for (org.web3j.abi.datatypes.DynamicBytes z: proof.getValue()) {;
            this.proof.add(z.getValue());;
            };
        }

        static List convertTo_uint256(List<BigInteger> param) {
            List<Uint256> z = new java.util.ArrayList<>();
            for (BigInteger a: param) {;
               z.add(new Uint256(a));
            };
            return z;
        }

        static List convertTo_bytes(List<byte[]> param) {
            List<org.web3j.abi.datatypes.DynamicBytes> z = new java.util.ArrayList<>();
            for (byte[] a: param) {;
               z.add(new org.web3j.abi.datatypes.DynamicBytes(a));
            };
            return z;
        }
    }

    public static class CloseEventResponse extends BaseEventResponse {
        public BigInteger _crossBlockchainTransactionId;
    }

    public static class DumpEventResponse extends BaseEventResponse {
        public BigInteger _bcId;

        public String _addr;

        public byte[] _functionCall;
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
        public BigInteger _crossBlockchainTransactionId;
    }

    public static class StartEventResponse extends BaseEventResponse {
        public BigInteger _crossBlockchainTransactionId;

        public BigInteger _timeout;

        public byte[] _callGraph;
    }
}
