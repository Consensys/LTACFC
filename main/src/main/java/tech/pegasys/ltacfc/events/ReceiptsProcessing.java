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


    LOG.info("Simple Proof");
    for (int i = 0; i < 2; i++) {
      Bytes aKey = indexKey(i);
      LOG.info("  key: {}, {}", i, aKey);

      Proof<Bytes> simpleProof = trie.getValueWithSimpleProof(aKey);
      Bytes transactionReceipt = simpleProof.getValue().get();
      Bytes rlpOfNode = transactionReceipt;
      LOG.info("  value: {}", transactionReceipt);
      Bytes nodeRef = transactionReceipt;
      if (nodeRef.size() >= 32) {
        nodeRef = RLP.encodeOne(org.hyperledger.besu.crypto.Hash.keccak256(nodeRef));
        LOG.info("     referencing by hash: {}", nodeRef);
      }

      LOG.info(" RLP dump of transaction receipt:");
      RlpDumper.dump(RLP.input(transactionReceipt));


      List<Bytes> proofList1 = simpleProof.getProofRelatedNodes();
      for (int j = proofList1.size()-1; j >=0; j--) {
        rlpOfNode = proofList1.get(j);
        LOG.info("   rlpOfNode: {}", rlpOfNode);
        LOG.info("    found rlpRef at offset: {}", findOffset(rlpOfNode, nodeRef));

        nodeRef = rlpOfNode;
        if (nodeRef.size() >= 32) {
          nodeRef = RLP.encodeOne(org.hyperledger.besu.crypto.Hash.keccak256(rlpOfNode));
          LOG.info("     referencing by hash: {}", nodeRef);
        }
      }
      LOG.info(" Root Hash: {}", org.hyperledger.besu.crypto.Hash.keccak256(rlpOfNode));
    }




  }

  private static int findOffset(Bytes rlpOfNode, Bytes nodeRef) {
    int sizeNodeRef = nodeRef.size();
    for (int i = 0; i < rlpOfNode.size() - sizeNodeRef; i++) {
      boolean found = true;
      for (int j = 0; j < sizeNodeRef; j++) {
        if (rlpOfNode.get(i+j) != nodeRef.get(j)) {
          found = false;
          break;
        }
      }
      if (found) {
        return i;
      }
    }
    return -1;
  }


  private static MerklePatriciaTrie<Bytes, Bytes> trie() {
    return new SimpleMerklePatriciaTrie<>(b -> b);
  }

  private static Bytes indexKey(final int i) {
    return RLP.encodeOne(UInt256.valueOf(i).toBytes().trimLeadingZeros());
  }

}
