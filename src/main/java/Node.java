import org.jetbrains.annotations.NotNull;

public class Node extends MerkleTree {
    private MerkleTree left;
    private MerkleTree right;

    public Node(@NotNull MerkleHash merkleHash, @NotNull MerkleTree left, @NotNull MerkleTree right) {
        super(merkleHash);
        this.left = left;
        this.right = right;
    }
}