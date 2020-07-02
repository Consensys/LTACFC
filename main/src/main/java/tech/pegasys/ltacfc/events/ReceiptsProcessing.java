package tech.pegasys.ltacfc.events;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tuweni.bytes.Bytes;
import org.apache.tuweni.units.bigints.UInt256;
import org.hyperledger.besu.ethereum.core.Hash;
import org.hyperledger.besu.ethereum.core.TransactionReceipt;
import org.hyperledger.besu.ethereum.rlp.RLP;
import org.hyperledger.besu.ethereum.rlp.RLPInput;
import tech.pegasys.ltacfc.rlp.RlpDumper;
import tech.pegasys.poc.witnesscodeanalysis.trie.ethereum.trie.MerklePatriciaTrie;
import tech.pegasys.poc.witnesscodeanalysis.trie.ethereum.trie.MultiMerkleProof;
import tech.pegasys.poc.witnesscodeanalysis.trie.ethereum.trie.Proof;
import tech.pegasys.poc.witnesscodeanalysis.trie.ethereum.trie.SimpleMerklePatriciaTrie;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReceiptsProcessing {
  private static final Logger LOG = LogManager.getLogger(ReceiptsProcessing.class);


  /**
   * Generates the receipt root for a list of receipts
   *
   * @param receipts the receipts
   * @return the receipt root
   */
  public static Hash receiptsRoot(final List<TransactionReceipt> receipts) {
    final MerklePatriciaTrie<Bytes, Bytes> trie = trie();

    for (int i = 0; i < receipts.size(); ++i) {
      trie.put(indexKey(i), RLP.encode(receipts.get(i)::writeTo));
    }

    return Hash.wrap(trie.getRootHash());
  }

  public static void logTrie(final List<TransactionReceipt> receipts) {
    LOG.info("Receipts Trie");
    final MerklePatriciaTrie<Bytes, Bytes> trie = trie();

    for (int i = 0; i < receipts.size(); ++i) {
      Bytes rlpEncoding = RLP.encode(receipts.get(i)::writeTo);
      LOG.info(" Key index {} RLP encoding: {}", i, rlpEncoding);
      trie.put(indexKey(i), rlpEncoding);
    }

    Bytes key1 = indexKey(0);
    Proof<Bytes> proofAndValue = trie.getValueWithProof(key1);
    RLPInput rlpInput = RLP.input(proofAndValue.getValue().get());
    RlpDumper.dump(rlpInput);

    // Create a proof
    List<Bytes> keys = new ArrayList<>();
    keys.add(key1);
    MultiMerkleProof<Bytes> proof = trie.getValuesWithMultiMerkleProof(keys);
    LOG.info("Root hash from multi Merkle Proof: {}", proof.computeRootHash());
    LOG.info(" Print: {}", proof.toString());
    proof.printStats();

    Bytes multiMerkleRlp = proof.getRlp();
    LOG.info("Proof RLP: {}", multiMerkleRlp);
    RlpDumper.dump(RLP.input(multiMerkleRlp));
  }

  private static MerklePatriciaTrie<Bytes, Bytes> trie() {
    return new SimpleMerklePatriciaTrie<>(b -> b);
  }

  private static Bytes indexKey(final int i) {
    return RLP.encodeOne(UInt256.valueOf(i).toBytes().trimLeadingZeros());
  }

}
