import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<MerkleHash> merkleHashList = new ArrayList<>();
        merkleHashList.add(new MerkleHash("L1"));
        merkleHashList.add(new MerkleHash("L2"));
        merkleHashList.add(new MerkleHash("L3"));
        merkleHashList.add(new MerkleHash("L4"));

        MerkleTree merkleTree = MerkleTree.getMerkleTree(merkleHashList);
        System.out.println("root : " + merkleTree.getMerkleHash().sha256HexBinary());
    }
}
