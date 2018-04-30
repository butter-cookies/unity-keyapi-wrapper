namespace SiegeModule {
    #if UNITY_IOS && !UNITY_EDITOR
    // ios error code
    public enum ErrorCode {
        // No error.
        None = 0,
        // Function or operation not implemented.
        Unimplemented = -4,
        // One or more parameters passed to a function where not valid.
        Param = -50,
        // Failed to allocate memory.
        Allocate = -108,
        // No keychain is available. You may need to restart your computer.
        NotAvailable = -25291,
        // The specified item already exists in the keychain.
        DuplicateItem = -25299,
        // The specified item could not be found in the keychain.
        ItemNotFound = -25300,
        // User interaction is not allowed.
        InteractionNotAllowed = -25308,
        // Unable to decode the provided data.
        Decode = -26275,
        // The user name or passphrase you entered is not correct.
        AuthFailed = -25293,
    }
    #elif UNITY_ANDROID && !UNITY_EDITOR
    // android error code
    public enum ErrorCode {
        // No error.
        None = 0,
        // Unknown error.
        Unknown = -1,
        // ItemNotFound error.
        ItemNotFound = 1,
        // NoSuchAlgorithm error.
        NoSuchAlgorithm = 2,
        // NoSuchPadding error.
        NoSuchPadding = 3,
        // KeyStore error.
        KeyStore = 4,
        // UnrecoverableKey error.
        UnrecoverableKey = 5,
        // NoSuchProvider error.
        NoSuchProvider = 6,
        // InvalidAlgorithmParameter error.
        InvalidAlgorithmParameter = 7,
        // InvalidKey error.
        InvalidKey = 8,
        // BadPadding error.
        BadPadding = 9,
        // IllegalBlockSize error.
        IllegalBlockSize = 10,
    }
    #else
    // other error code
    public enum ErrorCode {
        // No error.
        None = 0,
        // Unknown error.
        Unknown = -1,
        // ItemNotFound error.
        ItemNotFound = 1,
    }
    #endif
}
