import org.jetbrains.annotations.NotNull;

/*
 * Leaf
 */
public class Leaf extends MerkleTree {
    public Leaf(@NotNull MerkleHash merkleHash) {
        super(merkleHash);
    }
}
