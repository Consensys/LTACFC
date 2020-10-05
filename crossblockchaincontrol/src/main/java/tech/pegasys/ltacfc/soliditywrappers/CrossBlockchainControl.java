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
    public static final String BINARY = "608060405234801561001057600080fd5b506040516200212f3803806200212f8339810160408190526100319161005b565b600091909155600180546001600160a01b0319166001600160a01b03909216919091179055610096565b6000806040838503121561006d578182fd5b825160208401519092506001600160a01b038116811461008b578182fd5b809150509250929050565b61208980620000a66000396000f3fe608060405234801561001057600080fd5b506004361061014d5760003560e01c8063541ac862116100c35780639ad8a5b61161007c5780639ad8a5b614610270578063b4c3b75614610290578063d5d6b09c14610298578063df1bba01146102a0578063ebf0c717146102b3578063f4c1ef2f146102bb5761014d565b8063541ac862146102075780635c27d3071461021a578063653cf5a61461022f57806366b79f5a146102425780638e22d5341461024a57806392b2c3351461025d5761014d565b8063336d5b0911610115578063336d5b09146101be57806339ce107e146101c65780633ab561271461017b5780633cdc7104146101d9578063439160df146101ec5780634c97042e146101f45761014d565b806308148f7a146101525780631a26720c1461017b57806321de4821146101905780632af6cdf0146101a3578063323e32a7146101ab575b600080fd5b6101656101603660046119a2565b6102d0565b6040516101729190611ddc565b60405180910390f35b61018e610189366004611922565b6102e2565b005b61018e61019e3660046118c3565b6102e7565b610165610326565b61018e6101b936600461196b565b61032c565b61016561032f565b61018e6101d43660046118a1565b610335565b61018e6101e7366004611a76565b6103bc565b61016561071b565b6101656102023660046119a2565b610721565b61018e6102153660046119ba565b610736565b610222610af7565b6040516101729190611ca7565b61022261023d3660046119a2565b610b85565b610165610bed565b610165610258366004611b60565b610bf3565b61018e61026b366004611b60565b610bfd565b61028361027e3660046119a2565b610c03565b6040516101729190611c9c565b610283610c17565b610165610c1e565b61018e6102ae366004611bb8565b610c24565b61018e610cb4565b6102c3610cf1565b6040516101729190611cba565b60026020526000908152604090205481565b505050565b7fe6763dd99bf894d72f3499dd572aa42876eae7ae028c32fff21654e1bbc4c8076011600160405161031a929190611cce565b60405180910390a15050565b60045481565b50565b60045490565b6001600160a01b03811660009081526008602052604090205460ff1661032c576009805460018181019092557f6e1540171b6c0c960b71a7020d9f60077f6af931a8bbf590da0223dacf75c7af0180546001600160a01b0384166001600160a01b031990911681179091556000908152600860205260409020805460ff1916909117905550565b6001546040516306e3dd6f60e11b81526001600160a01b0390911690630dc7bade906103fa908e908d908d908d908d908d908d908d90600401611eea565b60206040518083038186803b15801561041257600080fd5b505afa158015610426573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525081019061044a9190611902565b5060608089898080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509293506060925061049d9150610498905083610d00565b610d43565b905060606104be826001815181106104b157fe5b6020026020010151610e0b565b90506104eb8e7fe071861e96e2f86d7704105f1697b783ce582ea599692363bb7ec62945575fec83610e86565b9050809450505050506104ff816000610f96565b600555600061050f826060610f96565b9050606061051f83608084610fcb565b805190915061053590600690602084019061153a565b5060048e9055610547600786866115b8565b50600080606061055884898961106a565b9250925092507f242105695377d1e0b400ef1c2922c3d732f44bc16f63816d1b4af3da6db5879b83838360405161059193929190611e1e565b60405180910390a160005483146105c35760405162461bcd60e51b81526004016105ba90611d0c565b60405180910390fd5b6105cd828261112c565b60006060836001600160a01b0316836040516105e99190611c80565b6000604051808303816000865af19150503d8060008114610626576040519150601f19603f3d011682016040523d82523d6000602084013e61062b565b606091505b5087516020890120600554604051939550919350917fb01557f1f634b7c5072ab5e36d07a2355ef819faca5a3d321430d71987155b8f916106789184908f908f906009908a908a90611e51565b60405180910390a16005600090556004600090556006600061069a91906115f3565b6106a660076000611637565b60005b6009548110156106f85760086000600983815481106106c457fe5b60009182526020808320909101546001600160a01b031683528201929092526040019020805460ff191690556001016106a9565b5061070560096000611637565b5050505050505050505050505050505050505050565b60005481565b6000818152600260205260409020545b919050565b6060836001600160401b038111801561074e57600080fd5b50604051908082528060200260200182016040528015610778578160200160208202803683370190505b50905060005b848110156107b85785858281811061079257fe5b905060200201358282815181106107a557fe5b602090810291909101015260010161077e565b506060826001600160401b03811180156107d157600080fd5b5060405190808252806020026020018201604052801561080557816020015b60608152602001906001900390816107f05790505b50905060005b838110156108865784848281811061081f57fe5b90506020028101906108319190611fd7565b8080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250508451859250849150811061087357fe5b602090810291909101015260010161080b565b507f382b2ad7663172ae333e7eaa8638b0ce1fa16e9ae5de9bd2944a238d37ca59b68b8b8b8b8b6040516108be959493929190611de5565b60405180910390a16108ce611655565b6040518060c001604052808d81526020018c6001600160a01b031681526020018b81526020018a8a8080601f016020809104026020016040519081016040528093929190818152602001838380828437600092018290525093855250505060208083018790526040928301869052600a8054600181018255925283517fc65a7bb8d6351c1cf70c95a316cc6a92839c986682d98bc35f958f4883f9d2a86006909302928301908155848201517fc65a7bb8d6351c1cf70c95a316cc6a92839c986682d98bc35f958f4883f9d2a9840180546001600160a01b0319166001600160a01b03909216919091179055928401517fc65a7bb8d6351c1cf70c95a316cc6a92839c986682d98bc35f958f4883f9d2aa830155606084015180519495508594610a1f937fc65a7bb8d6351c1cf70c95a316cc6a92839c986682d98bc35f958f4883f9d2ab0192919091019061153a565b5060808201518051610a3b916004840191602090910190611697565b5060a08201518051610a579160058401916020909101906116d1565b50506001546040516306e3dd6f60e11b81526001600160a01b039091169150630dc7bade90610a98908f908e908e908e908e908e908e908e90600401611eea565b60206040518083038186803b158015610ab057600080fd5b505afa158015610ac4573d6000803e3d6000fd5b505050506040513d601f19601f82011682018060405250810190610ae89190611902565b50505050505050505050505050565b6006805460408051602060026001851615610100026000190190941693909304601f81018490048402820184019092528181529291830182828015610b7d5780601f10610b5257610100808354040283529160200191610b7d565b820191906000526020600020905b815481529060010190602001808311610b6057829003601f168201915b505050505081565b60036020908152600091825260409182902080548351601f600260001961010060018616150201909316929092049182018490048402810184019094528084529091830182828015610b7d5780601f10610b5257610100808354040283529160200191610b7d565b60055490565b6000949350505050565b50505050565b600090815260026020526040902054151590565b6004541590565b60055481565b610c2d84610c03565b15610c4a5760405162461bcd60e51b81526004016105ba90611d60565b600084815260026020908152604080832086905560039091529020610c7090838361172a565b507fe071861e96e2f86d7704105f1697b783ce582ea599692363bb7ec62945575fec84848484604051610ca69493929190611fb7565b60405180910390a150505050565b7fe6763dd99bf894d72f3499dd572aa42876eae7ae028c32fff21654e1bbc4c807600d6001604051610ce7929190611cce565b60405180910390a1565b6001546001600160a01b031681565b610d08611797565b815180610d2a5750506040805180820190915260008082526020820152610731565b6040805180820190915260209384018152928301525090565b6060610d4e82611193565b610d5757600080fd5b6000610d62836111ba565b9050806001600160401b0381118015610d7a57600080fd5b50604051908082528060200260200182016040528015610db457816020015b610da1611797565b815260200190600190039081610d995790505b509150610dbf6117b1565b610dc88461121e565b905060005b610dd682611255565b15610e0357610de482611274565b848281518110610df057fe5b6020908102919091010152600101610dcd565b505050919050565b6060610e16826112b4565b610e1f57600080fd5b600080610e2b846112da565b9092509050806001600160401b0381118015610e4657600080fd5b506040519080825280601f01601f191660200182016040528015610e71576020820181803683370190505b509250610e7f82848361134a565b5050919050565b6060806060610e9761049885610d00565b90506060610eb882600381518110610eab57fe5b6020026020010151610d43565b905060005b8151811015610f75576060610ed7838381518110610eab57fe5b90506000610ef3610eee836000815181106104b157fe5b611388565b9050610f0582600181518110610eab57fe5b9650610f17826002815181106104b157fe5b95506000610f3888600081518110610f2b57fe5b602002602001015161138f565b90508981148015610f5a57508a6001600160a01b0316826001600160a01b0316145b15610f6a57505050505050610f8e565b505050600101610ebd565b5060405162461bcd60e51b81526004016105ba90611d97565b935093915050565b60008160200183511015610fbc5760405162461bcd60e51b81526004016105ba90611cde565b50818101602001515b92915050565b606080826001600160401b0381118015610fe457600080fd5b506040519080825280601f01601f19166020018201604052801561100f576020820181803683370190505b50905060005b8381101561106157858186018151811061102b57fe5b602001015160f81c60f81b82828151811061104257fe5b60200101906001600160f81b031916908160001a905350600101611015565b50949350505050565b60008060608061107c61049888610d00565b905060005b60001986018110156110b8576110ae8288888481811061109d57fe5b9050602002013581518110610eab57fe5b9150600101611081565b5060606110cf828888600019810181811061109d57fe5b90506110ee816000815181106110e157fe5b6020026020010151611396565b945061110d8160018151811061110057fe5b60200260200101516113ed565b935061111f816002815181106104b157fe5b9250505093509350939050565b60006060836001600160a01b0316836040516111489190611c80565b6000604051808303816000865af19150503d8060008114611185576040519150601f19603f3d011682016040523d82523d6000602084013e61118a565b606091505b50505050505050565b60008160200151600014156111aa57506000610731565b50515160c060009190911a101590565b60006111c582611193565b6111d157506000610731565b81518051600090811a91906111e58561142e565b6020860151908301915082016000190160005b81831161121457611208836114ac565b909201916001016111f8565b9695505050505050565b6112266117b1565b61122f82611193565b61123857600080fd5b60006112438361142e565b83519383529092016020820152919050565b600061125f611797565b50508051602080820151915192015191011190565b61127c611797565b61128582611255565b1561014d576020820151600061129a826114ac565b828452602080850182905292019184019190915250610731565b60008160200151600014156112cb57506000610731565b50515160c060009190911a1090565b6000806112e6836112b4565b6112ef57600080fd5b8251805160001a90608082101561130d579250600191506113459050565b60b882101561132b5760018560200151039250806001019350611342565b602085015182820160b51901945082900360b60192505b50505b915091565b6020601f820104836020840160005b8381101561137557602081028381015190830152600101611359565b5050505060008251602001830152505050565b6014015190565b6000610fc5825b60006113a1826112b4565b6113aa57600080fd5b6000806113b6846112da565b909250905060208111156113c957600080fd5b806113d957600092505050610731565b806020036101000a82510492505050919050565b60006113f8826112b4565b61140157600080fd5b60008061140d846112da565b90925090506014811461141f57600080fd5b5051600160601b900492915050565b600081602001516000141561144557506000610731565b8151805160001a90608082101561146157600092505050610731565b60b882108061147c575060c0821015801561147c575060f882105b1561148c57600192505050610731565b60c08210156114a1575060b519019050610731565b5060f5190192915050565b8051600090811a60808110156114c55760019150611534565b60b88110156114da57607e1981019150611534565b60c081101561150357600183015160b76020839003016101000a9004810160b519019150611534565b60f88110156115185760be1981019150611534565b600183015160f76020839003016101000a9004810160f5190191505b50919050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061157b57805160ff19168380011785556115a8565b828001600101855582156115a8579182015b828111156115a857825182559160200191906001019061158d565b506115b49291506117d1565b5090565b8280548282559060005260206000209081019282156115a8579160200282015b828111156115a85782358255916020019190600101906115d8565b50805460018160011615610100020316600290046000825580601f10611619575061032c565b601f01602090049060005260206000209081019061032c91906117d1565b508054600082559060005260206000209081019061032c91906117d1565b6040518060c001604052806000815260200160006001600160a01b03168152602001600080191681526020016060815260200160608152602001606081525090565b8280548282559060005260206000209081019282156115a857916020028201828111156115a857825182559160200191906001019061158d565b82805482825590600052602060002090810192821561171e579160200282015b8281111561171e578251805161170e91849160209091019061153a565b50916020019190600101906116f1565b506115b49291506117e6565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061176b5782800160ff198235161785556115a8565b828001600101855582156115a857918201828111156115a85782358255916020019190600101906115d8565b604051806040016040528060008152602001600081525090565b60405180604001604052806117c4611797565b8152602001600081525090565b5b808211156115b457600081556001016117d2565b808211156115b45760006117fa82826115f3565b506001016117e6565b80356001600160a01b0381168114610fc557600080fd5b60008083601f84011261182b578182fd5b5081356001600160401b03811115611841578182fd5b602083019150836020808302850101111561185b57600080fd5b9250929050565b60008083601f840112611873578182fd5b5081356001600160401b03811115611889578182fd5b60208301915083602082850101111561185b57600080fd5b6000602082840312156118b2578081fd5b6118bc8383611803565b9392505050565b600080602083850312156118d5578081fd5b82356001600160401b038111156118ea578182fd5b6118f68582860161181a565b90969095509350505050565b600060208284031215611913578081fd5b815180151581146118bc578182fd5b600080600060408486031215611936578081fd5b8335925060208401356001600160401b03811115611952578182fd5b61195e86828701611862565b9497909650939450505050565b60006020828403121561197c578081fd5b81356001600160401b03811115611991578182fd5b820160c081850312156118bc578182fd5b6000602082840312156119b3578081fd5b5035919050565b600080600080600080600080600060c08a8c0312156119d7578485fd5b893598506119e88b60208c01611803565b975060408a0135965060608a01356001600160401b0380821115611a0a578687fd5b611a168d838e01611862565b909850965060808c0135915080821115611a2e578586fd5b611a3a8d838e0161181a565b909650945060a08c0135915080821115611a52578384fd5b50611a5f8c828d0161181a565b915080935050809150509295985092959850929598565b600080600080600080600080600080600060e08c8e031215611a96578182fd5b8b359a50611aa78d60208e01611803565b995060408c013598506001600160401b038060608e01351115611ac8578283fd5b611ad88e60608f01358f01611862565b909950975060808d0135811015611aed578283fd5b611afd8e60808f01358f0161181a565b909750955060a08d0135811015611b12578283fd5b611b228e60a08f01358f0161181a565b909550935060c08d0135811015611b37578283fd5b50611b488d60c08e01358e0161181a565b81935080925050509295989b509295989b9093969950565b60008060008060608587031215611b75578384fd5b84359350611b868660208701611803565b925060408501356001600160401b03811115611ba0578283fd5b611bac87828801611862565b95989497509550505050565b60008060008060608587031215611bcd578384fd5b843593506020850135925060408501356001600160401b03811115611ba0578283fd5b81835260006001600160fb1b03831115611c08578081fd5b6020830280836020870137939093016020019283525090919050565b15159052565b60008284528282602086013780602084860101526020601f19601f85011685010190509392505050565b60008151808452611c6c816020860160208601612027565b601f01601f19169290920160200192915050565b60008251611c92818460208701612027565b9190910192915050565b901515815260200190565b6000602082526118bc6020830184611c54565b6001600160a01b0391909116815260200190565b9182521515602082015260400190565b602080825260149082015273736c6963696e67206f7574206f662072616e676560601b604082015260600190565b60208082526034908201527f54617267657420626c6f636b636861696e20696420646f6573206e6f74206d616040820152731d18da081b5e48189b1bd8dad8da185a5b881a5960621b606082015260800190565b6020808252601e908201527f5472616e73616374696f6e20616c726561647920726567697374657265640000604082015260600190565b60208082526025908201527f4e6f206576656e7420666f756e6420696e207472616e73616374696f6e20726560408201526418d95a5c1d60da1b606082015260800190565b90815260200190565b600086825260018060a01b038616602083015284604083015260806060830152611e13608083018486611c2a565b979650505050505050565b8381526001600160a01b0383166020820152606060408201819052600090611e4890830184611c54565b95945050505050565b60008882526020888184015260c06040840152611e7260c08401888a611bf0565b8381036060850152865480825287845282842091830190845b81811015611eb9578354611ea7906001600160a01b031661201b565b83526001938401939285019201611e8b565b5050611ec86080860188611c24565b84810360a0860152611eda8187611c54565b9c9b505050505050505050505050565b60008982526020898184015260a06040840152611f0b60a08401898b611c2a565b8381036060850152611f1e81888a611bf0565b848103608086015285815290508181018286028201830187855b88811015611fa357848303601f190184528135368b9003601e19018112611f5d578788fd5b8a0180356001600160401b03811115611f74578889fd5b8036038c1315611f82578889fd5b611f8f85828a8501611c2a565b958801959450505090850190600101611f38565b50909e9d5050505050505050505050505050565b600085825284602083015260606040830152611214606083018486611c2a565b6000808335601e19843603018112611fed578283fd5b8301803591506001600160401b03821115612006578283fd5b60200191503681900382131561185b57600080fd5b6001600160a01b031690565b60005b8381101561204257818101518382015260200161202a565b83811115610bfd575050600091015256fea2646970667358221220da5da3fea762e8a52e2bb4be967a9d46cd4600e9f76ba9166dc4d4e4922a8e6f64736f6c63430007010033";

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

    public static final String FUNC_ROOT2 = "root2";

    public static final String FUNC_ROOTPREP = "rootPrep";

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

    public static final Event ROOT2_EVENT = new Event("Root2", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Bytes32>() {}, new TypeReference<DynamicBytes>() {}));
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

    public List<Root2EventResponse> getRoot2Events(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(ROOT2_EVENT, transactionReceipt);
        ArrayList<Root2EventResponse> responses = new ArrayList<Root2EventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            Root2EventResponse typedResponse = new Root2EventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._bcId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse._cbcContract = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse._receiptRoot = (byte[]) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse._encodedTxReceipt = (byte[]) eventValues.getNonIndexedValues().get(3).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<Root2EventResponse> root2EventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, Root2EventResponse>() {
            @Override
            public Root2EventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(ROOT2_EVENT, log);
                Root2EventResponse typedResponse = new Root2EventResponse();
                typedResponse.log = log;
                typedResponse._bcId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse._cbcContract = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse._receiptRoot = (byte[]) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse._encodedTxReceipt = (byte[]) eventValues.getNonIndexedValues().get(3).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<Root2EventResponse> root2EventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ROOT2_EVENT));
        return root2EventFlowable(filter);
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

    public RemoteFunctionCall<TransactionReceipt> root() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ROOT, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getRLP_root() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ROOT, 
                Arrays.<Type>asList(), 
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

    public RemoteFunctionCall<TransactionReceipt> root2(Info param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ROOT2, 
                Arrays.<Type>asList(param0), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getRLP_root2(Info param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ROOT2, 
                Arrays.<Type>asList(param0), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<TransactionReceipt> rootPrep(BigInteger _blockchainId, String _cbcContract, byte[] _txReceiptRoot, byte[] _encodedTxReceipt, List<BigInteger> _proofOffsets, List<byte[]> _proof) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ROOTPREP, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_blockchainId), 
                new org.web3j.abi.datatypes.Address(160, _cbcContract), 
                new org.web3j.abi.datatypes.generated.Bytes32(_txReceiptRoot), 
                new org.web3j.abi.datatypes.DynamicBytes(_encodedTxReceipt), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.datatypes.generated.Uint256.class,
                        org.web3j.abi.Utils.typeMap(_proofOffsets, org.web3j.abi.datatypes.generated.Uint256.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.DynamicBytes>(
                        org.web3j.abi.datatypes.DynamicBytes.class,
                        org.web3j.abi.Utils.typeMap(_proof, org.web3j.abi.datatypes.DynamicBytes.class))), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getRLP_rootPrep(BigInteger _blockchainId, String _cbcContract, byte[] _txReceiptRoot, byte[] _encodedTxReceipt, List<BigInteger> _proofOffsets, List<byte[]> _proof) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ROOTPREP, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_blockchainId), 
                new org.web3j.abi.datatypes.Address(160, _cbcContract), 
                new org.web3j.abi.datatypes.generated.Bytes32(_txReceiptRoot), 
                new org.web3j.abi.datatypes.DynamicBytes(_encodedTxReceipt), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.datatypes.generated.Uint256.class,
                        org.web3j.abi.Utils.typeMap(_proofOffsets, org.web3j.abi.datatypes.generated.Uint256.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.DynamicBytes>(
                        org.web3j.abi.datatypes.DynamicBytes.class,
                        org.web3j.abi.Utils.typeMap(_proof, org.web3j.abi.datatypes.DynamicBytes.class))), 
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

        public String cbcContract;

        public byte[] txReceiptRoot;

        public byte[] txReceipt;

        public List<BigInteger> proofOffset;

        public List<byte[]> proof;

        public Info(BigInteger blockchainId, String cbcContract, byte[] txReceiptRoot, byte[] txReceipt, List<BigInteger> proofOffset, List<byte[]> proof) {
            super(new org.web3j.abi.datatypes.generated.Uint256(blockchainId),new org.web3j.abi.datatypes.Address(cbcContract),new org.web3j.abi.datatypes.generated.Bytes32(txReceiptRoot),new org.web3j.abi.datatypes.DynamicBytes(txReceipt),new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(org.web3j.abi.datatypes.generated.Uint256.class, convertTo_uint256(proofOffset)),new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.DynamicBytes>(org.web3j.abi.datatypes.DynamicBytes.class, convertTo_bytes(proof)));
            this.blockchainId = blockchainId;
            this.cbcContract = cbcContract;
            this.txReceiptRoot = txReceiptRoot;
            this.txReceipt = txReceipt;
            this.proofOffset = proofOffset;
            this.proof = proof;
        }

        public Info(Uint256 blockchainId, Address cbcContract, Bytes32 txReceiptRoot, DynamicBytes txReceipt, DynamicArray<Uint256> proofOffset, DynamicArray<DynamicBytes> proof) {
            super(blockchainId,cbcContract,txReceiptRoot,txReceipt,proofOffset,proof);
            this.blockchainId = blockchainId.getValue();
            this.cbcContract = cbcContract.getValue();
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

    public static class Root2EventResponse extends BaseEventResponse {
        public BigInteger _bcId;

        public String _cbcContract;

        public byte[] _receiptRoot;

        public byte[] _encodedTxReceipt;
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
