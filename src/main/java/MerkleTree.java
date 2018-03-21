import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * https://en.wikipedia.org/wiki/Merkle_tree
 */
public class MerkleTree {

    private MerkleHash merkleHash;

    public MerkleTree(@NotNull MerkleHash merkleHash) {
        this.merkleHash = merkleHash;
    }

    public static MerkleTree getMerkleTree(@NotNull List<MerkleHash> allLeavesHashes) {
        if (allLeavesHashes.isEmpty())
            throw new IllegalArgumentException("Cannot calculate Merkle root on empty hash list.");
        if (!isPow2(allLeavesHashes.size())) {
            throw new IllegalArgumentException("allLeavesHashes must be Pow of 2.");
        }
        List<MerkleTree> result = allLeavesHashes.stream().map(new Function<MerkleHash, MerkleTree>() {
            @Override
            public MerkleTree apply(MerkleHash merkleHash) {
                return new Leaf(merkleHash);
            }
        }).collect(Collectors.toList());
        return buildMerkleTree(result);
    }

    private static boolean isPow2(int num) {
        return (num & (num - 1)) == 0;
    }

    private static MerkleTree buildMerkleTree(@NotNull List<MerkleTree> lastNodesList) {
        if (lastNodesList.size() == 1) {
            return lastNodesList.get(0); // Root reached.
        } else {
            int n = lastNodesList.size();
            List<MerkleTree> newLevelHashes = new ArrayList<>();

            int i = 0;
            while (n - 2 > i) {
                MerkleTree left = lastNodesList.get(i);
                MerkleTree right = lastNodesList.get(i + 1);
                // 結合
                MerkleHash newHash = left.merkleHash.hashConcat(right.merkleHash);
                newLevelHashes.add(new Node(newHash, left, right));
                i += 2;
            }
            return buildMerkleTree(newLevelHashes);
        }
    }

    /**
     * MerkleHashを取得
     *
     * @return MerkleTree
     */
    public MerkleHash getMerkleHash() {
        return merkleHash;
    }

}
