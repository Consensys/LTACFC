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
    public static final String BINARY = "608060405234801561001057600080fd5b50604051611b7b380380611b7b83398101604081905261002f91610059565b600091909155600180546001600160a01b0319166001600160a01b03909216919091179055610094565b6000806040838503121561006b578182fd5b825160208401519092506001600160a01b0381168114610089578182fd5b809150509250929050565b611ad8806100a36000396000f3fe608060405234801561001057600080fd5b506004361061012c5760003560e01c8063653cf5a6116100ad5780639ad8a5b6116100715780639ad8a5b614610229578063b4c3b75614610249578063d5d6b09c14610251578063df1bba0114610259578063f4c1ef2f1461026c5761012c565b8063653cf5a6146101d557806366b79f5a146101e857806373b5844a146101f05780638e22d5341461020357806392b2c335146102165761012c565b80633ab56127116100f45780633ab561271461015a5780633cdc710414610192578063439160df146101a55780634c97042e146101ad5780635c27d307146101c05761012c565b806308148f7a146101315780631a26720c1461015a5780632af6cdf01461016f578063336d5b091461017757806339ce107e1461017f575b600080fd5b61014461013f36600461152a565b610281565b60405161015191906118a8565b60405180910390f35b61016d6101683660046114e1565b610293565b005b610144610298565b61014461029e565b61016d61018d366004611369565b6102a4565b61016d6101a0366004611542565b61032b565b61014461068a565b6101446101bb36600461152a565b610690565b6101c86106a5565b6040516101519190611773565b6101c86101e336600461152a565b610733565b61014461079b565b61016d6101fe36600461138b565b6107a1565b61014461021136600461162c565b6107ea565b61016d61022436600461162c565b6107f4565b61023c61023736600461152a565b6107fa565b6040516101519190611768565b61023c61080e565b610144610815565b61016d610267366004611684565b61081b565b6102746108ab565b6040516101519190611786565b60026020526000908152604090205481565b505050565b60045481565b60045490565b6001600160a01b03811660009081526008602052604090205460ff16610328576009805460018082019092557f6e1540171b6c0c960b71a7020d9f60077f6af931a8bbf590da0223dacf75c7af0180546001600160a01b0319166001600160a01b0384169081179091556000908152600860205260409020805460ff191690911790555b50565b6001546040516306e3dd6f60e11b81526001600160a01b0390911690630dc7bade90610369908e908d908d908d908d908d908d908d9060040161197d565b60206040518083038186803b15801561038157600080fd5b505afa158015610395573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906103b991906114c1565b5060608089898080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509293506060925061040c91506104079050836108ba565b6108fd565b9050606061042d8260018151811061042057fe5b60200260200101516109c5565b905061045a8e7fe071861e96e2f86d7704105f1697b783ce582ea599692363bb7ec62945575fec83610a40565b90508094505050505061046e816000610b50565b600555600061047e826060610b50565b9050606061048e83608084610b85565b80519091506104a49060069060208401906110f4565b5060048e90556104b660078686611172565b5060008060606104c7848989610c24565b9250925092507f242105695377d1e0b400ef1c2922c3d732f44bc16f63816d1b4af3da6db5879b838383604051610500939291906118b1565b60405180910390a160005483146105325760405162461bcd60e51b8152600401610529906117d8565b60405180910390fd5b61053c8282610ce6565b60006060836001600160a01b031683604051610558919061174c565b6000604051808303816000865af19150503d8060008114610595576040519150601f19603f3d011682016040523d82523d6000602084013e61059a565b606091505b5087516020890120600554604051939550919350917fb01557f1f634b7c5072ab5e36d07a2355ef819faca5a3d321430d71987155b8f916105e79184908f908f906009908a908a906118e4565b60405180910390a16005600090556004600090556006600061060991906111ad565b610615600760006111f1565b60005b60095481101561066757600860006009838154811061063357fe5b60009182526020808320909101546001600160a01b031683528201929092526040019020805460ff19169055600101610618565b50610674600960006111f1565b5050505050505050505050505050505050505050565b60005481565b6000818152600260205260409020545b919050565b6006805460408051602060026001851615610100026000190190941693909304601f8101849004840282018401909252818152929183018282801561072b5780601f106107005761010080835404028352916020019161072b565b820191906000526020600020905b81548152906001019060200180831161070e57829003601f168201915b505050505081565b60036020908152600091825260409182902080548351601f60026000196101006001861615020190931692909204918201849004840281018401909452808452909183018282801561072b5780601f106107005761010080835404028352916020019161072b565b60055490565b7fe6763dd99bf894d72f3499dd572aa42876eae7ae028c32fff21654e1bbc4c807600760016040516107d492919061179a565b60405180910390a1505050505050505050505050565b6000949350505050565b50505050565b600090815260026020526040902054151590565b6004541590565b60055481565b610824846107fa565b156108415760405162461bcd60e51b81526004016105299061182c565b60008481526002602090815260408083208690556003909152902061086790838361120f565b507fe071861e96e2f86d7704105f1697b783ce582ea599692363bb7ec62945575fec8484848460405161089d9493929190611a4a565b60405180910390a150505050565b6001546001600160a01b031681565b6108c261127c565b8151806108e457505060408051808201909152600080825260208201526106a0565b6040805180820190915260209384018152928301525090565b606061090882610d4d565b61091157600080fd5b600061091c83610d74565b9050806001600160401b038111801561093457600080fd5b5060405190808252806020026020018201604052801561096e57816020015b61095b61127c565b8152602001906001900390816109535790505b509150610979611296565b61098284610dd8565b905060005b61099082610e0f565b156109bd5761099e82610e2e565b8482815181106109aa57fe5b6020908102919091010152600101610987565b505050919050565b60606109d082610e6e565b6109d957600080fd5b6000806109e584610e94565b9092509050806001600160401b0381118015610a0057600080fd5b506040519080825280601f01601f191660200182016040528015610a2b576020820181803683370190505b509250610a39828483610f04565b5050919050565b6060806060610a51610407856108ba565b90506060610a7282600381518110610a6557fe5b60200260200101516108fd565b905060005b8151811015610b2f576060610a91838381518110610a6557fe5b90506000610aad610aa88360008151811061042057fe5b610f42565b9050610abf82600181518110610a6557fe5b9650610ad18260028151811061042057fe5b95506000610af288600081518110610ae557fe5b6020026020010151610f49565b90508981148015610b1457508a6001600160a01b0316826001600160a01b0316145b15610b2457505050505050610b48565b505050600101610a77565b5060405162461bcd60e51b815260040161052990611863565b935093915050565b60008160200183511015610b765760405162461bcd60e51b8152600401610529906117aa565b50818101602001515b92915050565b606080826001600160401b0381118015610b9e57600080fd5b506040519080825280601f01601f191660200182016040528015610bc9576020820181803683370190505b50905060005b83811015610c1b578581860181518110610be557fe5b602001015160f81c60f81b828281518110610bfc57fe5b60200101906001600160f81b031916908160001a905350600101610bcf565b50949350505050565b600080606080610c36610407886108ba565b905060005b6000198601811015610c7257610c6882888884818110610c5757fe5b9050602002013581518110610a6557fe5b9150600101610c3b565b506060610c898288886000198101818110610c5757fe5b9050610ca881600081518110610c9b57fe5b6020026020010151610f50565b9450610cc781600181518110610cba57fe5b6020026020010151610fa7565b9350610cd98160028151811061042057fe5b9250505093509350939050565b60006060836001600160a01b031683604051610d02919061174c565b6000604051808303816000865af19150503d8060008114610d3f576040519150601f19603f3d011682016040523d82523d6000602084013e610d44565b606091505b50505050505050565b6000816020015160001415610d64575060006106a0565b50515160c060009190911a101590565b6000610d7f82610d4d565b610d8b575060006106a0565b81518051600090811a9190610d9f85610fe8565b6020860151908301915082016000190160005b818311610dce57610dc283611066565b90920191600101610db2565b9695505050505050565b610de0611296565b610de982610d4d565b610df257600080fd5b6000610dfd83610fe8565b83519383529092016020820152919050565b6000610e1961127c565b50508051602080820151915192015191011190565b610e3661127c565b610e3f82610e0f565b1561012c5760208201516000610e5482611066565b8284526020808501829052920191840191909152506106a0565b6000816020015160001415610e85575060006106a0565b50515160c060009190911a1090565b600080610ea083610e6e565b610ea957600080fd5b8251805160001a906080821015610ec757925060019150610eff9050565b60b8821015610ee55760018560200151039250806001019350610efc565b602085015182820160b51901945082900360b60192505b50505b915091565b6020601f820104836020840160005b83811015610f2f57602081028381015190830152600101610f13565b5050505060008251602001830152505050565b6014015190565b6000610b7f825b6000610f5b82610e6e565b610f6457600080fd5b600080610f7084610e94565b90925090506020811115610f8357600080fd5b80610f93576000925050506106a0565b806020036101000a82510492505050919050565b6000610fb282610e6e565b610fbb57600080fd5b600080610fc784610e94565b909250905060148114610fd957600080fd5b5051600160601b900492915050565b6000816020015160001415610fff575060006106a0565b8151805160001a90608082101561101b576000925050506106a0565b60b8821080611036575060c08210158015611036575060f882105b15611046576001925050506106a0565b60c082101561105b575060b5190190506106a0565b5060f5190192915050565b8051600090811a608081101561107f57600191506110ee565b60b881101561109457607e19810191506110ee565b60c08110156110bd57600183015160b76020839003016101000a9004810160b5190191506110ee565b60f88110156110d25760be19810191506110ee565b600183015160f76020839003016101000a9004810160f5190191505b50919050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061113557805160ff1916838001178555611162565b82800160010185558215611162579182015b82811115611162578251825591602001919060010190611147565b5061116e9291506112b6565b5090565b828054828255906000526020600020908101928215611162579160200282015b82811115611162578235825591602001919060010190611192565b50805460018160011615610100020316600290046000825580601f106111d35750610328565b601f01602090049060005260206000209081019061032891906112b6565b508054600082559060005260206000209081019061032891906112b6565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106112505782800160ff19823516178555611162565b828001600101855582156111625791820182811115611162578235825591602001919060010190611192565b604051806040016040528060008152602001600081525090565b60405180604001604052806112a961127c565b8152602001600081525090565b5b8082111561116e57600081556001016112b7565b80356001600160a01b0381168114610b7f57600080fd5b60008083601f8401126112f3578182fd5b5081356001600160401b03811115611309578182fd5b602083019150836020808302850101111561132357600080fd5b9250929050565b60008083601f84011261133b578182fd5b5081356001600160401b03811115611351578182fd5b60208301915083602082850101111561132357600080fd5b60006020828403121561137a578081fd5b61138483836112cb565b9392505050565b60008060008060008060008060008060008060c08d8f0312156113ac578788fd5b6001600160401b038d3511156113c0578788fd5b6113cd8e8e358f016112e2565b909c509a506001600160401b0360208e013511156113e9578788fd5b6113f98e60208f01358f016112e2565b909a5098506001600160401b0360408e01351115611415578788fd5b6114258e60408f01358f016112e2565b90985096506001600160401b0360608e01351115611441578586fd5b6114518e60608f01358f016112e2565b90965094506001600160401b0360808e0135111561146d578384fd5b61147d8e60808f01358f016112e2565b90945092506001600160401b0360a08e01351115611499578081fd5b6114a98e60a08f01358f016112e2565b81935080925050509295989b509295989b509295989b565b6000602082840312156114d2578081fd5b81518015158114611384578182fd5b6000806000604084860312156114f5578283fd5b8335925060208401356001600160401b03811115611511578283fd5b61151d8682870161132a565b9497909650939450505050565b60006020828403121561153b578081fd5b5035919050565b600080600080600080600080600080600060e08c8e031215611562578081fd5b8b359a506115738d60208e016112cb565b995060408c013598506001600160401b038060608e01351115611594578182fd5b6115a48e60608f01358f0161132a565b909950975060808d01358110156115b9578182fd5b6115c98e60808f01358f016112e2565b909750955060a08d01358110156115de578182fd5b6115ee8e60a08f01358f016112e2565b909550935060c08d0135811015611603578182fd5b506116148d60c08e01358e016112e2565b81935080925050509295989b509295989b9093969950565b60008060008060608587031215611641578384fd5b8435935061165286602087016112cb565b925060408501356001600160401b0381111561166c578283fd5b6116788782880161132a565b95989497509550505050565b60008060008060608587031215611699578384fd5b843593506020850135925060408501356001600160401b0381111561166c578283fd5b81835260006001600160fb1b038311156116d4578081fd5b6020830280836020870137939093016020019283525090919050565b15159052565b60008284528282602086013780602084860101526020601f19601f85011685010190509392505050565b60008151808452611738816020860160208601611a76565b601f01601f19169290920160200192915050565b6000825161175e818460208701611a76565b9190910192915050565b901515815260200190565b6000602082526113846020830184611720565b6001600160a01b0391909116815260200190565b9182521515602082015260400190565b602080825260149082015273736c6963696e67206f7574206f662072616e676560601b604082015260600190565b60208082526034908201527f54617267657420626c6f636b636861696e20696420646f6573206e6f74206d616040820152731d18da081b5e48189b1bd8dad8da185a5b881a5960621b606082015260800190565b6020808252601e908201527f5472616e73616374696f6e20616c726561647920726567697374657265640000604082015260600190565b60208082526025908201527f4e6f206576656e7420666f756e6420696e207472616e73616374696f6e20726560408201526418d95a5c1d60da1b606082015260800190565b90815260200190565b8381526001600160a01b03831660208201526060604082018190526000906118db90830184611720565b95945050505050565b60008882526020888184015260c0604084015261190560c08401888a6116bc565b8381036060850152865480825287845282842091830190845b8181101561194c57835461193a906001600160a01b0316611a6a565b8352600193840193928501920161191e565b505061195b60808601886116f0565b84810360a086015261196d8187611720565b9c9b505050505050505050505050565b60008982526020898184015260a0604084015261199e60a08401898b6116f6565b83810360608501526119b181888a6116bc565b848103608086015285815290508181018286028201830187855b88811015611a3657848303601f190184528135368b9003601e190181126119f0578788fd5b8a0180356001600160401b03811115611a07578889fd5b8036038c1315611a15578889fd5b611a2285828a85016116f6565b9588019594505050908501906001016119cb565b50909e9d5050505050505050505050505050565b600085825284602083015260606040830152610dce6060830184866116f6565b6001600160a01b031690565b60005b83811015611a91578181015183820152602001611a79565b838111156107f4575050600091015256fea2646970667358221220fcef539207cb26d083b1fd0ecd2238ef2ca6923b4bef550d0d15e633e6f6fb2964736f6c63430007010033";

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

    public RemoteFunctionCall<TransactionReceipt> root(List<BigInteger> _rootAndSegmentBlockchainIds, List<String> _rootAndSegmentCBCContracts, List<byte[]> _startAndSegmentTxReceiptRoots, List<byte[]> _startAndSegmentTxReceipts, List<List<BigInteger>> _startAndSegmentProofOffsets, List<List<byte[]>> _startAndSegmentProofs) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ROOT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.datatypes.generated.Uint256.class,
                        org.web3j.abi.Utils.typeMap(_rootAndSegmentBlockchainIds, org.web3j.abi.datatypes.generated.Uint256.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(_rootAndSegmentCBCContracts, org.web3j.abi.datatypes.Address.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Bytes32>(
                        org.web3j.abi.datatypes.generated.Bytes32.class,
                        org.web3j.abi.Utils.typeMap(_startAndSegmentTxReceiptRoots, org.web3j.abi.datatypes.generated.Bytes32.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.DynamicBytes>(
                        org.web3j.abi.datatypes.DynamicBytes.class,
                        org.web3j.abi.Utils.typeMap(_startAndSegmentTxReceipts, org.web3j.abi.datatypes.DynamicBytes.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.DynamicArray>(
                        org.web3j.abi.datatypes.DynamicArray.class,
                        org.web3j.abi.Utils.typeMap(_startAndSegmentProofOffsets, org.web3j.abi.datatypes.DynamicArray.class,
                org.web3j.abi.datatypes.generated.Uint256.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.DynamicArray>(
                        org.web3j.abi.datatypes.DynamicArray.class,
                        org.web3j.abi.Utils.typeMap(_startAndSegmentProofs, org.web3j.abi.datatypes.DynamicArray.class,
                org.web3j.abi.datatypes.DynamicBytes.class))), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getRLP_root(List<BigInteger> _rootAndSegmentBlockchainIds, List<String> _rootAndSegmentCBCContracts, List<byte[]> _startAndSegmentTxReceiptRoots, List<byte[]> _startAndSegmentTxReceipts, List<List<BigInteger>> _startAndSegmentProofOffsets, List<List<byte[]>> _startAndSegmentProofs) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ROOT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.datatypes.generated.Uint256.class,
                        org.web3j.abi.Utils.typeMap(_rootAndSegmentBlockchainIds, org.web3j.abi.datatypes.generated.Uint256.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(_rootAndSegmentCBCContracts, org.web3j.abi.datatypes.Address.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Bytes32>(
                        org.web3j.abi.datatypes.generated.Bytes32.class,
                        org.web3j.abi.Utils.typeMap(_startAndSegmentTxReceiptRoots, org.web3j.abi.datatypes.generated.Bytes32.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.DynamicBytes>(
                        org.web3j.abi.datatypes.DynamicBytes.class,
                        org.web3j.abi.Utils.typeMap(_startAndSegmentTxReceipts, org.web3j.abi.datatypes.DynamicBytes.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.DynamicArray>(
                        org.web3j.abi.datatypes.DynamicArray.class,
                        org.web3j.abi.Utils.typeMap(_startAndSegmentProofOffsets, org.web3j.abi.datatypes.DynamicArray.class,
                org.web3j.abi.datatypes.generated.Uint256.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.DynamicArray>(
                        org.web3j.abi.datatypes.DynamicArray.class,
                        org.web3j.abi.Utils.typeMap(_startAndSegmentProofs, org.web3j.abi.datatypes.DynamicArray.class,
                org.web3j.abi.datatypes.DynamicBytes.class))), 
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
