package jp.unikeymodule.domain.enums;

public enum ErrorCode {
    // Success
    None(0),
    // Unknown
    Unknown(-1),
    // ItemNotFound
    ItemNotFound(1),
    // NoSuchAlgorithm
    NoSuchAlgorithm(2),
    // NoSuchPadding
    NoSuchPadding(3),
    // KeyStore
    KeyStore(4),
    // UnrecoverableKey
    UnrecoverableKey(5),
    // NoSuchProvider
    NoSuchProvider(6),
    // InvalidAlgorithmParameter
    InvalidAlgorithmParameter(7),
    // InvalidKey
    InvalidKey(8),
    // BadPadding
    BadPadding(9),
    // IllegalBlockSize
    IllegalBlockSize(10);

    // ID
    private final int id;

    /**
     * Constructor
     * @param id
     */
    ErrorCode(int id) { this.id = id; }

    /**
     * get id
     * @return identify
     */
    public int getId() { return id; }
}
