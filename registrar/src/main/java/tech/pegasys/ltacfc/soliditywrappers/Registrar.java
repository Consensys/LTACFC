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
 * <p>Generated with web3j version 4.5.16.
 */
@SuppressWarnings("rawtypes")
public class Registrar extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b506004805460018181019092557f8a35acfbc15ff81a39ae7d344fd709f28e8600b4aa8c65c6b64bfe7fe36bd19b0180546001600160a01b031916339081179091556000908152600360205260409020819055600280546001600160401b03191690911790556111bd806100856000396000f3fe608060405234801561001057600080fd5b50600436106100cf5760003560e01c8063a130e0451161008c578063cb103ef511610066578063cb103ef514610239578063ea13ec8b14610256578063f5e232ea1461040b578063fc29175e14610428576100cf565b8063a130e045146101e5578063a64ce19914610214578063aa6dc92614610231576100cf565b8063111fd88b146100d457806324d7806c1461010d57806348bcbd2d1461014757806349d9db1814610173578063581cb2dc146101925780639e200f79146101b6575b600080fd5b6100f1600480360360208110156100ea57600080fd5b5035610457565b604080516001600160a01b039092168252519081900360200190f35b6101336004803603602081101561012357600080fd5b50356001600160a01b0316610481565b604080519115158252519081900360200190f35b6101336004803603604081101561015d57600080fd5b50803590602001356001600160a01b031661049e565b61017b6104ca565b6040805161ffff9092168252519081900360200190f35b61019a6104cf565b604080516001600160401b039092168252519081900360200190f35b6101e3600480360360608110156101cc57600080fd5b5061ffff81351690602081013590604001356104de565b005b610202600480360360208110156101fb57600080fd5b503561074e565b60408051918252519081900360200190f35b61019a6004803603602081101561022a57600080fd5b5035610771565b610202610791565b6101e36004803603602081101561024f57600080fd5b5035610797565b6101e3600480360360c081101561026c57600080fd5b81359190810190604081016020820135600160201b81111561028d57600080fd5b82018360208201111561029f57600080fd5b803590602001918460208302840111600160201b831117156102c057600080fd5b919390929091602081019035600160201b8111156102dd57600080fd5b8201836020820111156102ef57600080fd5b803590602001918460208302840111600160201b8311171561031057600080fd5b919390929091602081019035600160201b81111561032d57600080fd5b82018360208201111561033f57600080fd5b803590602001918460208302840111600160201b8311171561036057600080fd5b919390929091602081019035600160201b81111561037d57600080fd5b82018360208201111561038f57600080fd5b803590602001918460208302840111600160201b831117156103b057600080fd5b919390929091602081019035600160201b8111156103cd57600080fd5b8201836020820111156103df57600080fd5b803590602001918460018302840111600160201b8311171561040057600080fd5b50909250905061090c565b61019a6004803603602081101561042157600080fd5b5035610a43565b6101e36004803603606081101561043e57600080fd5b5061ffff81351690602081013590604001351515610a65565b60006004828154811061046657fe5b6000918252602090912001546001600160a01b031692915050565b6001600160a01b0316600090815260036020526040902054151590565b6000828152602081815260408083206001600160a01b0385168452600101909152902054151592915050565b600190565b6002546001600160401b031690565b336000908152600360205260409020546104f757600080fd5b60008361ffff16600781111561050957fe5b90506000808481526005602052604090205460ff16600781111561052957fe5b1461053357600080fd5b8282600183600781111561054357fe5b14156105615761055282610481565b1561055c57600080fd5b6106c3565b600283600781111561056f57fe5b141561059d5761057e82610481565b61058757600080fd5b6001600160a01b03821633141561055c57600080fd5b60048360078111156105ab57fe5b14156106065760008581526020819052604090205461010090046001600160401b0316156105d857600080fd5b60008460018111156105e657fe5b905060018160018111156105f657fe5b1461060057600080fd5b506106c3565b600583600781111561061457fe5b14156106405760008581526020819052604090205461010090046001600160401b031661055c57600080fd5b600683600781111561064e57fe5b1415610682576000858152602081815260408083206001600160a01b03851684526001019091529020541561055c57600080fd5b600783600781111561069057fe5b14156106c3576000858152602081815260408083206001600160a01b03851684526001019091529020546106c357600080fd5b6000858152600560205260409020805484919060ff191660018360078111156106e857fe5b0217905550600180546000878152600560205260409020600160a01b9091046001600160401b0316430160028201558101859055546001600160a01b031661073a57610735856001610b18565b610746565b61074686866001610fc3565b505050505050565b60008181526020819052604081205460ff16600181111561076b57fe5b92915050565b60009081526020819052604090205461010090046001600160401b031690565b60045490565b336000908152600360205260409020546107b057600080fd5b60008181526005602052604081205460ff16908160078111156107cf57fe5b14156107da57600080fd5b60008281526005602052604090206002015443116107f757600080fd5b6001546002546000848152600560209081526040808320600490810154825163a81ce84760e01b81526001600160401b03968716928101929092528086166024830152600160401b90049094166044850152516001600160a01b03909416939192849263a81ce84792606480840193919291829003018186803b15801561087d57600080fd5b505afa158015610891573d6000803e3d6000fd5b505050506040513d60208110156108a757600080fd5b505190507ff36941b213653867ab41db7916ff3975cd4d6cfe42f73e61aeda6257d1eab74b8360078111156108d857fe5b6040805161ffff90921682526020820187905283151582820152519081900360600190a16109068482610b18565b50505050565b8887811461091957600080fd5b80861461092557600080fd5b80841461093157600080fd5b60008c81526020819052604090205461010090046001600160401b031681101561095a57600080fd5b60005b81811015610a345760008d8152602081905260408120600101908d8d8481811061098357fe5b905060200201356001600160a01b03166001600160a01b03166001600160a01b0316815260200190815260200160002054600014156109c157600080fd5b610a238c8c838181106109d057fe5b905060200201356001600160a01b031685858d8d868181106109ee57fe5b905060200201358c8c87818110610a0157fe5b905060200201358b8b88818110610a1457fe5b9050602002013560ff166110bd565b610a2c57600080fd5b60010161095d565b50505050505050505050505050565b600090815260208190526040902054600160481b90046001600160401b031690565b33600090815260036020526040902054610a7e57600080fd5b60008361ffff166007811115610a9057fe5b9050806007811115610a9e57fe5b60008481526005602052604090205460ff166007811115610abb57fe5b14610ac557600080fd5b600083815260056020908152604080832033845260030190915290205460ff1615610aef57600080fd5b600083815260056020526040902060020154431115610b0d57600080fd5b610906848484610fc3565b8015610ee7576000828152600560205260409020805460019182015460ff9091169184908290846007811115610b4a57fe5b1415610bda5760048054600180820183557f8a35acfbc15ff81a39ae7d344fd709f28e8600b4aa8c65c6b64bfe7fe36bd19b90910180546001600160a01b0319166001600160a01b0386169081179091559154600092835260036020526040909220919091556002805467ffffffffffffffff1981166001600160401b0391821690930116919091179055610ee2565b6002846007811115610be857fe5b1415610c75576001600160a01b038216600090815260036020526040902054600480546000198301908110610c1957fe5b600091825260208083209190910180546001600160a01b03191690556001600160a01b0385168252600390526040812055506002805467ffffffffffffffff1981166001600160401b0391821660001901909116179055610ee2565b6003846007811115610c8357fe5b1415610cc857600180546001600160a01b0319166001600160a01b0384161767ffffffffffffffff60a01b1916600160a01b6001600160401b03861602179055610ee2565b6004846007811115610cd657fe5b1415610d3557826001811115610ce857fe5b6000878152602081905260409020805460ff191660018381811115610d0957fe5b02179055506000868152602081905260409020805468ffffffffffffffff001916610100179055610ee2565b6005846007811115610d4357fe5b1415610d7b576000868152602081905260409020805468ffffffffffffffff0019166101006001600160401b03861602179055610ee2565b6006846007811115610d8957fe5b1415610e19576000868152602081815260408083206002810180546001818101835582875285872090910180546001600160a01b0389166001600160a01b0319909116811790915591549186528083018552928520558984529290915281546001600160401b03600160481b80830482169093011690910267ffffffffffffffff60481b19909116179055610ee2565b6007846007811115610e2757fe5b1415610ee2576000868152602081815260408083206001600160a01b0385168452600181018352908320548984529290915260020180546000198301908110610e6c57fe5b6000918252602080832090910180546001600160a01b031916905588825281815260408083206001600160a01b038616845260018101835290832083905589835291905280546000196001600160401b03600160481b808404821692909201160267ffffffffffffffff60481b19909116179055505b505050505b60005b600454811015610f805760006001600160a01b031660048281548110610f0c57fe5b6000918252602090912001546001600160a01b031614610f785760056000848152602001908152602001600020600301600060048381548110610f4b57fe5b60009182526020808320909101546001600160a01b031683528201929092526040019020805460ff191690555b600101610eea565b50506000908152600560205260408120805460ff1916815560018101829055600281019190915560040180546fffffffffffffffffffffffffffffffff19169055565b6040805133815261ffff85166020820152808201849052821515606082015290517f2cf518b4f57f241b79cee0c80f2d18fb40e9a7044c6a5fcad55cb1c74b95bbb49181900360800190a160008281526005602090815260408083203384526003019091529020805460ff19166001179055801561107257600082815260056020526040902060040180546001600160401b038082166001011667ffffffffffffffff199091161790556110b8565b6000828152600560205260409020600401805460016001600160401b03600160401b80840482169290920116026fffffffffffffffff0000000000000000199091161790555b505050565b600080868660405180838380828437604051920182900390912094505050505060ff8316601b148015906110f557508260ff16601c14155b1561110457600091505061117d565b6040805160008152602080820180845284905260ff8616828401526060820188905260808201879052915160019260a0808401939192601f1981019281900390910190855afa15801561115b573d6000803e3d6000fd5b505050602060405103516001600160a01b0316886001600160a01b0316149150505b969550505050505056fea26469706673582212201dde048f2489d814a9a09004efadd66af5f01e2797c6efb8ca59c292bc14510564736f6c63430006090033";

    public static final String FUNC_ACTIONVOTES = "actionVotes";

    public static final String FUNC_ADMINARRAYSIZE = "adminArraySize";

    public static final String FUNC_GETADMIN = "getAdmin";

    public static final String FUNC_GETAPIVERSION = "getApiVersion";

    public static final String FUNC_GETNUMADMINS = "getNumAdmins";

    public static final String FUNC_GETSIGALGORITHM = "getSigAlgorithm";

    public static final String FUNC_GETSIGNINGTHRESHOLD = "getSigningThreshold";

    public static final String FUNC_ISADMIN = "isAdmin";

    public static final String FUNC_ISSIGNER = "isSigner";

    public static final String FUNC_NUMSIGNERS = "numSigners";

    public static final String FUNC_PROPOSEVOTE = "proposeVote";

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

    public RemoteFunctionCall<BigInteger> adminArraySize() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ADMINARRAYSIZE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> getAdmin(BigInteger _index) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETADMIN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_index)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> getApiVersion() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETAPIVERSION, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint16>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> getNumAdmins() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETNUMADMINS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint64>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> getSigAlgorithm(BigInteger _blockchainId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETSIGALGORITHM, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_blockchainId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> getSigningThreshold(BigInteger _blockchainId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETSIGNINGTHRESHOLD, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_blockchainId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint64>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Boolean> isAdmin(String _mightBeAdmin) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ISADMIN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _mightBeAdmin)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<Boolean> isSigner(BigInteger _blockchainId, String _mightBeSigner) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ISSIGNER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_blockchainId), 
                new org.web3j.abi.datatypes.Address(160, _mightBeSigner)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<BigInteger> numSigners(BigInteger _blockchainId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_NUMSIGNERS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_blockchainId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint64>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
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

    public RemoteFunctionCall<TransactionReceipt> vote(BigInteger _action, BigInteger _voteTarget, Boolean _voteFor) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_VOTE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint16(_action), 
                new org.web3j.abi.datatypes.generated.Uint256(_voteTarget), 
                new org.web3j.abi.datatypes.Bool(_voteFor)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
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
