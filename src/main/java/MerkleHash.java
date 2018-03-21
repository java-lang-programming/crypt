import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * https://github.com/corda/corda/blob/master/core/src/main/kotlin/net/corda/core/crypto/SecureHash.kt
 */
public class MerkleHash {

    private byte[] bytes;

    /**
     * バイト配列を設定するコンストラクタ
     *
     * @param bytes バイト配列
     */
    public MerkleHash(final @NotNull byte[] bytes) {
        this.bytes = bytes;
    }

    /**
     * 文字列をバイト配列にするコンストラクタ
     *
     * @param str 文字列
     */
    public MerkleHash(final @NotNull String str) {
        this.bytes = str.getBytes();
    }

    /**
     * バイト配列をSHA-256でハッシュ化して16進数文字列にして返す
     *
     * @return 16進数文字列
     */
    public String sha256HexBinary() {
        try {
            byte[] bytes = MessageDigest.getInstance("SHA-256").digest(this.bytes);
            return printHexBinary(bytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static final char[] hexCode = "0123456789ABCDEF".toCharArray();

    /**
     * 2. バイト配列を16進数にする(バイトを16進数にする)
     * https://github.com/openforis/android-ports/blob/master/android-jaxb/src/main/java/org/arbonaut/xml/bind/DatatypeConverterImpl.java
     *
     * @param data バイト配列
     * @return 16進数文字列に変換したバイト配列
     */
    public static String printHexBinary(byte[] data) {
        StringBuilder r = new StringBuilder(data.length * 2);
        for (byte b : data) {
            r.append(hexCode[(b >> 4) & 0xF]);
            r.append(hexCode[(b & 0xF)]);
        }
        return r.toString();
    }

    /**
     * ハッシュの結合
     *
     * @param add
     * @return 結合したMerkleHash
     */
    public MerkleHash hashConcat(final @NotNull MerkleHash add) {
        ByteBuffer byteBuf = ByteBuffer.allocate(bytes.length + add.bytes.length);
        byteBuf.put(bytes);
        byteBuf.put(add.bytes);
        byte[] newBytes = byteBuf.array();
        return new MerkleHash(newBytes);
    }
}