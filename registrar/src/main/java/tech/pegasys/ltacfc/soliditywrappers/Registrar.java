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
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint16;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint64;
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
public class Registrar extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b507f67be87c3ff9960ca1e9cfac5cab2ff4747269cf9ed20c9b7306235ac35a491c5805460ff199081166001908117909255600580548084019091557f036b6384b5eca791c62761152d0c79bb0604c104a5fb6f4eb0703f3154bb3db00180546001600160a01b0319163390811790915560009081526004602090815260408220849055600380546001600160401b03191685179055633e91675f60e01b8252527f4d4e1c0cc2d8597ab8286ed90ef15f5a91d6e2a00f1417c66899e074fe82a3bb805490911690911790556111ec806100eb6000396000f3fe608060405234801561001057600080fd5b50600436106100cf5760003560e01c8063a130e0451161008c578063cb103ef511610066578063cb103ef514610241578063ea13ec8b1461025e578063f5e232ea14610413578063fc29175e14610430576100cf565b8063a130e045146101ed578063a64ce1991461021c578063aa6dc92614610239576100cf565b806301ffc9a7146100d4578063111fd88b1461010f57806324d7806c1461014857806348bcbd2d1461016e578063581cb2dc1461019a5780639e200f79146101be575b600080fd5b6100fb600480360360208110156100ea57600080fd5b50356001600160e01b03191661045f565b604080519115158252519081900360200190f35b61012c6004803603602081101561012557600080fd5b503561047e565b604080516001600160a01b039092168252519081900360200190f35b6100fb6004803603602081101561015e57600080fd5b50356001600160a01b03166104a8565b6100fb6004803603604081101561018457600080fd5b50803590602001356001600160a01b03166104c5565b6101a26104f2565b604080516001600160401b039092168252519081900360200190f35b6101eb600480360360608110156101d457600080fd5b5061ffff8135169060208101359060400135610501565b005b61020a6004803603602081101561020357600080fd5b5035610773565b60408051918252519081900360200190f35b6101a26004803603602081101561023257600080fd5b5035610797565b61020a6107b7565b6101eb6004803603602081101561025757600080fd5b50356107bd565b6101eb600480360360c081101561027457600080fd5b81359190810190604081016020820135600160201b81111561029557600080fd5b8201836020820111156102a757600080fd5b803590602001918460208302840111600160201b831117156102c857600080fd5b919390929091602081019035600160201b8111156102e557600080fd5b8201836020820111156102f757600080fd5b803590602001918460208302840111600160201b8311171561031857600080fd5b919390929091602081019035600160201b81111561033557600080fd5b82018360208201111561034757600080fd5b803590602001918460208302840111600160201b8311171561036857600080fd5b919390929091602081019035600160201b81111561038557600080fd5b82018360208201111561039757600080fd5b803590602001918460208302840111600160201b831117156103b857600080fd5b919390929091602081019035600160201b8111156103d557600080fd5b8201836020820111156103e757600080fd5b803590602001918460018302840111600160201b8311171561040857600080fd5b509092509050610932565b6101a26004803603602081101561042957600080fd5b5035610a69565b6101eb6004803603606081101561044657600080fd5b5061ffff81351690602081013590604001351515610a8b565b6001600160e01b03191660009081526020819052604090205460ff1690565b60006005828154811061048d57fe5b6000918252602090912001546001600160a01b031692915050565b6001600160a01b0316600090815260046020526040902054151590565b60008281526001602081815260408084206001600160a01b03861685529092019052902054151592915050565b6003546001600160401b031690565b3360009081526004602052604090205461051a57600080fd5b60008361ffff16600781111561052c57fe5b90506000808481526006602052604090205460ff16600781111561054c57fe5b1461055657600080fd5b8282600183600781111561056657fe5b141561058457610575826104a8565b1561057f57600080fd5b6106e8565b600283600781111561059257fe5b14156105c0576105a1826104a8565b6105aa57600080fd5b6001600160a01b03821633141561057f57600080fd5b60048360078111156105ce57fe5b14156106295760008581526001602052604090205461010090046001600160401b0316156105fb57600080fd5b600084600181111561060957fe5b9050600181600181111561061957fe5b1461062357600080fd5b506106e8565b600583600781111561063757fe5b14156106635760008581526001602052604090205461010090046001600160401b031661057f57600080fd5b600683600781111561067157fe5b14156106a65760008581526001602081815260408084206001600160a01b038616855290920190529020541561057f57600080fd5b60078360078111156106b457fe5b14156106e85760008581526001602081815260408084206001600160a01b038616855290920190529020546106e857600080fd5b6000858152600660205260409020805484919060ff1916600183600781111561070d57fe5b0217905550600280546000878152600660205260409020600160a01b9091046001600160401b0316430181830155600101859055546001600160a01b031661075f5761075a856001610b3e565b61076b565b61076b86866001610ff2565b505050505050565b600081815260016020819052604082205460ff169081111561079157fe5b92915050565b60009081526001602052604090205461010090046001600160401b031690565b60055490565b336000908152600460205260409020546107d657600080fd5b60008181526006602052604081205460ff16908160078111156107f557fe5b141561080057600080fd5b600082815260066020526040902060020154431161081d57600080fd5b6002546003546000848152600660209081526040808320600490810154825163a81ce84760e01b81526001600160401b03968716928101929092528086166024830152600160401b90049094166044850152516001600160a01b03909416939192849263a81ce84792606480840193919291829003018186803b1580156108a357600080fd5b505afa1580156108b7573d6000803e3d6000fd5b505050506040513d60208110156108cd57600080fd5b505190507ff36941b213653867ab41db7916ff3975cd4d6cfe42f73e61aeda6257d1eab74b8360078111156108fe57fe5b6040805161ffff90921682526020820187905283151582820152519081900360600190a161092c8482610b3e565b50505050565b8887811461093f57600080fd5b80861461094b57600080fd5b80841461095757600080fd5b60008c81526001602052604090205461010090046001600160401b031681101561098057600080fd5b60005b81811015610a5a5760008d8152600160208190526040822001908d8d848181106109a957fe5b905060200201356001600160a01b03166001600160a01b03166001600160a01b0316815260200190815260200160002054600014156109e757600080fd5b610a498c8c838181106109f657fe5b905060200201356001600160a01b031685858d8d86818110610a1457fe5b905060200201358c8c87818110610a2757fe5b905060200201358b8b88818110610a3a57fe5b9050602002013560ff166110ec565b610a5257600080fd5b600101610983565b50505050505050505050505050565b600090815260016020526040902054600160481b90046001600160401b031690565b33600090815260046020526040902054610aa457600080fd5b60008361ffff166007811115610ab657fe5b9050806007811115610ac457fe5b60008481526006602052604090205460ff166007811115610ae157fe5b14610aeb57600080fd5b600083815260066020908152604080832033845260030190915290205460ff1615610b1557600080fd5b600083815260066020526040902060020154431115610b3357600080fd5b61092c848484610ff2565b8015610f16576000828152600660205260409020805460019182015460ff9091169184908290846007811115610b7057fe5b1415610c005760058054600180820183557f036b6384b5eca791c62761152d0c79bb0604c104a5fb6f4eb0703f3154bb3db090910180546001600160a01b0319166001600160a01b0386169081179091559154600092835260046020526040909220919091556003805467ffffffffffffffff1981166001600160401b0391821690930116919091179055610f11565b6002846007811115610c0e57fe5b1415610c9b576001600160a01b038216600090815260046020526040902054600580546000198301908110610c3f57fe5b600091825260208083209190910180546001600160a01b03191690556001600160a01b0385168252600490526040812055506003805467ffffffffffffffff1981166001600160401b0391821660001901909116179055610f11565b6003846007811115610ca957fe5b1415610cee57600280546001600160a01b0319166001600160a01b0384161767ffffffffffffffff60a01b1916600160a01b6001600160401b03861602179055610f11565b6004846007811115610cfc57fe5b1415610d6157826001811115610d0e57fe5b60008781526001602081905260409091208054909160ff19909116908381811115610d3557fe5b02179055506000868152600160205260409020805468ffffffffffffffff001916610100179055610f11565b6005846007811115610d6f57fe5b1415610da7576000868152600160205260409020805468ffffffffffffffff0019166101006001600160401b03861602179055610f11565b6006846007811115610db557fe5b1415610e4657600086815260016020818152604080842060028101805480860182558187528487200180546001600160a01b0389166001600160a01b0319909116811790915590549086528185018452918520919091559289905281905281546001600160401b03600160481b80830482169093011690910267ffffffffffffffff60481b19909116179055610f11565b6007846007811115610e5457fe5b1415610f115760008681526001602081815260408084206001600160a01b0386168552808401835290842054938a905291905260020180546000198301908110610e9a57fe5b6000918252602080832090910180546001600160a01b0319169055888252600180825260408084206001600160a01b03871685528083018452908420849055928a9052905280546000196001600160401b03600160481b808404821692909201160267ffffffffffffffff60481b19909116179055505b505050505b60005b600554811015610faf5760006001600160a01b031660058281548110610f3b57fe5b6000918252602090912001546001600160a01b031614610fa75760066000848152602001908152602001600020600301600060058381548110610f7a57fe5b60009182526020808320909101546001600160a01b031683528201929092526040019020805460ff191690555b600101610f19565b50506000908152600660205260408120805460ff1916815560018101829055600281019190915560040180546fffffffffffffffffffffffffffffffff19169055565b6040805133815261ffff85166020820152808201849052821515606082015290517f2cf518b4f57f241b79cee0c80f2d18fb40e9a7044c6a5fcad55cb1c74b95bbb49181900360800190a160008281526006602090815260408083203384526003019091529020805460ff1916600117905580156110a157600082815260066020526040902060040180546001600160401b038082166001011667ffffffffffffffff199091161790556110e7565b6000828152600660205260409020600401805460016001600160401b03600160401b80840482169290920116026fffffffffffffffff0000000000000000199091161790555b505050565b600080868660405180838380828437604051920182900390912094505050505060ff8316601b1480159061112457508260ff16601c14155b156111335760009150506111ac565b6040805160008152602080820180845284905260ff8616828401526060820188905260808201879052915160019260a0808401939192601f1981019281900390910190855afa15801561118a573d6000803e3d6000fd5b505050602060405103516001600160a01b0316886001600160a01b0316149150505b969550505050505056fea2646970667358221220c565c98b4496ed8b16e129b412064e29cfb856a008b2e779561f674277f9c13264736f6c634300060b0033";

    public static final String FUNC_ACTIONVOTES = "actionVotes";

    public static final String FUNC_ADMINARRAYSIZE = "adminArraySize";

    public static final String FUNC_GETADMIN = "getAdmin";

    public static final String FUNC_GETNUMADMINS = "getNumAdmins";

    public static final String FUNC_GETSIGALGORITHM = "getSigAlgorithm";

    public static final String FUNC_GETSIGNINGTHRESHOLD = "getSigningThreshold";

    public static final String FUNC_ISADMIN = "isAdmin";

    public static final String FUNC_ISSIGNER = "isSigner";

    public static final String FUNC_NUMSIGNERS = "numSigners";

    public static final String FUNC_PROPOSEVOTE = "proposeVote";

    public static final String FUNC_SUPPORTSINTERFACE = "supportsInterface";

    public static final String FUNC_VERIFY = "verify";

    public static final String FUNC_VOTE = "vote";

    public static final Event VOTERESULT_EVENT = new Event("VoteResult", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint16>() {}, new TypeReference<Uint256>() {}, new TypeReference<Bool>() {}));
    ;

    public static final Event VOTED_EVENT = new Event("Voted", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint16>() {}, new TypeReference<Uint256>() {}, new TypeReference<Bool>() {}));
    ;

    @Deprecated
    protected Registrar(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Registrar(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Registrar(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Registrar(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<VoteResultEventResponse> getVoteResultEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(VOTERESULT_EVENT, transactionReceipt);
        ArrayList<VoteResultEventResponse> responses = new ArrayList<VoteResultEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            VoteResultEventResponse typedResponse = new VoteResultEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._action = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse._voteTarget = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse._result = (Boolean) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<VoteResultEventResponse> voteResultEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, VoteResultEventResponse>() {
            @Override
            public VoteResultEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(VOTERESULT_EVENT, log);
                VoteResultEventResponse typedResponse = new VoteResultEventResponse();
                typedResponse.log = log;
                typedResponse._action = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse._voteTarget = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse._result = (Boolean) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<VoteResultEventResponse> voteResultEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(VOTERESULT_EVENT));
        return voteResultEventFlowable(filter);
    }

    public List<VotedEventResponse> getVotedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(VOTED_EVENT, transactionReceipt);
        ArrayList<VotedEventResponse> responses = new ArrayList<VotedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            VotedEventResponse typedResponse = new VotedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._participant = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse._action = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse._voteTarget = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse._votedFor = (Boolean) eventValues.getNonIndexedValues().get(3).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<VotedEventResponse> votedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, VotedEventResponse>() {
            @Override
            public VotedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(VOTED_EVENT, log);
                VotedEventResponse typedResponse = new VotedEventResponse();
                typedResponse.log = log;
                typedResponse._participant = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse._action = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse._voteTarget = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse._votedFor = (Boolean) eventValues.getNonIndexedValues().get(3).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<VotedEventResponse> votedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(VOTED_EVENT));
        return votedEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> actionVotes(BigInteger _voteTarget) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ACTIONVOTES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_voteTarget)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getRLP_actionVotes(BigInteger _voteTarget) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ACTIONVOTES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_voteTarget)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<BigInteger> adminArraySize() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ADMINARRAYSIZE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public String getRLP_adminArraySize() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ADMINARRAYSIZE, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<String> getAdmin(BigInteger _index) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETADMIN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_index)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public String getRLP_getAdmin(BigInteger _index) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_GETADMIN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_index)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<BigInteger> getNumAdmins() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETNUMADMINS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint64>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public String getRLP_getNumAdmins() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_GETNUMADMINS, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<BigInteger> getSigAlgorithm(BigInteger _blockchainId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETSIGALGORITHM, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_blockchainId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public String getRLP_getSigAlgorithm(BigInteger _blockchainId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_GETSIGALGORITHM, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_blockchainId)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<BigInteger> getSigningThreshold(BigInteger _blockchainId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETSIGNINGTHRESHOLD, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_blockchainId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint64>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public String getRLP_getSigningThreshold(BigInteger _blockchainId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_GETSIGNINGTHRESHOLD, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_blockchainId)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<Boolean> isAdmin(String _mightBeAdmin) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ISADMIN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _mightBeAdmin)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public String getRLP_isAdmin(String _mightBeAdmin) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ISADMIN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _mightBeAdmin)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<Boolean> isSigner(BigInteger _blockchainId, String _mightBeSigner) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ISSIGNER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_blockchainId), 
                new org.web3j.abi.datatypes.Address(160, _mightBeSigner)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public String getRLP_isSigner(BigInteger _blockchainId, String _mightBeSigner) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ISSIGNER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_blockchainId), 
                new org.web3j.abi.datatypes.Address(160, _mightBeSigner)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<BigInteger> numSigners(BigInteger _blockchainId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_NUMSIGNERS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_blockchainId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint64>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public String getRLP_numSigners(BigInteger _blockchainId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_NUMSIGNERS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_blockchainId)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<TransactionReceipt> proposeVote(BigInteger _action, BigInteger _voteTarget, BigInteger _additionalInfo) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_PROPOSEVOTE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint16(_action), 
                new org.web3j.abi.datatypes.generated.Uint256(_voteTarget), 
                new org.web3j.abi.datatypes.generated.Uint256(_additionalInfo)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getRLP_proposeVote(BigInteger _action, BigInteger _voteTarget, BigInteger _additionalInfo) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_PROPOSEVOTE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint16(_action), 
                new org.web3j.abi.datatypes.generated.Uint256(_voteTarget), 
                new org.web3j.abi.datatypes.generated.Uint256(_additionalInfo)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<Boolean> supportsInterface(byte[] interfaceID) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_SUPPORTSINTERFACE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes4(interfaceID)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public String getRLP_supportsInterface(byte[] interfaceID) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SUPPORTSINTERFACE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes4(interfaceID)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<TransactionReceipt> verify(BigInteger _blockchainId, List<String> _signers, List<byte[]> _sigR, List<byte[]> _sigS, List<BigInteger> _sigV, byte[] _plainText) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_VERIFY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_blockchainId), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(_signers, org.web3j.abi.datatypes.Address.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Bytes32>(
                        org.web3j.abi.datatypes.generated.Bytes32.class,
                        org.web3j.abi.Utils.typeMap(_sigR, org.web3j.abi.datatypes.generated.Bytes32.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Bytes32>(
                        org.web3j.abi.datatypes.generated.Bytes32.class,
                        org.web3j.abi.Utils.typeMap(_sigS, org.web3j.abi.datatypes.generated.Bytes32.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint8>(
                        org.web3j.abi.datatypes.generated.Uint8.class,
                        org.web3j.abi.Utils.typeMap(_sigV, org.web3j.abi.datatypes.generated.Uint8.class)), 
                new org.web3j.abi.datatypes.DynamicBytes(_plainText)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getRLP_verify(BigInteger _blockchainId, List<String> _signers, List<byte[]> _sigR, List<byte[]> _sigS, List<BigInteger> _sigV, byte[] _plainText) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_VERIFY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_blockchainId), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(_signers, org.web3j.abi.datatypes.Address.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Bytes32>(
                        org.web3j.abi.datatypes.generated.Bytes32.class,
                        org.web3j.abi.Utils.typeMap(_sigR, org.web3j.abi.datatypes.generated.Bytes32.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Bytes32>(
                        org.web3j.abi.datatypes.generated.Bytes32.class,
                        org.web3j.abi.Utils.typeMap(_sigS, org.web3j.abi.datatypes.generated.Bytes32.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint8>(
                        org.web3j.abi.datatypes.generated.Uint8.class,
                        org.web3j.abi.Utils.typeMap(_sigV, org.web3j.abi.datatypes.generated.Uint8.class)), 
                new org.web3j.abi.datatypes.DynamicBytes(_plainText)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<TransactionReceipt> vote(BigInteger _action, BigInteger _voteTarget, Boolean _voteFor) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_VOTE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint16(_action), 
                new org.web3j.abi.datatypes.generated.Uint256(_voteTarget), 
                new org.web3j.abi.datatypes.Bool(_voteFor)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String getRLP_vote(BigInteger _action, BigInteger _voteTarget, Boolean _voteFor) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_VOTE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint16(_action), 
                new org.web3j.abi.datatypes.generated.Uint256(_voteTarget), 
                new org.web3j.abi.datatypes.Bool(_voteFor)), 
                Collections.<TypeReference<?>>emptyList());
        return org.web3j.abi.FunctionEncoder.encode(function);
    }

    @Deprecated
    public static Registrar load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Registrar(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Registrar load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Registrar(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Registrar load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Registrar(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Registrar load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Registrar(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Registrar> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Registrar.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<Registrar> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Registrar.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Registrar> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Registrar.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Registrar> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Registrar.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class VoteResultEventResponse extends BaseEventResponse {
        public BigInteger _action;

        public BigInteger _voteTarget;

        public Boolean _result;
    }

    public static class VotedEventResponse extends BaseEventResponse {
        public String _participant;

        public BigInteger _action;

        public BigInteger _voteTarget;

        public Boolean _votedFor;
    }
}
