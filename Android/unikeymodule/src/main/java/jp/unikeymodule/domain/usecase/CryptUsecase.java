package jp.unikeymodule.domain.usecase;
import android.content.Context;
import android.security.KeyPairGeneratorSpec;
import android.support.annotation.NonNull;
import android.util.Log;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.util.Calendar;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.security.auth.x500.X500Principal;
import jp.unikeymodule.R;
import jp.unikeymodule.data.IDataStore;
import jp.unikeymodule.data.PrefsDataStore;
import jp.unikeymodule.domain.entity.UniKeyModuleBoolean;
import jp.unikeymodule.domain.entity.UniKeyModuleString;
import jp.unikeymodule.domain.enums.ErrorCode;
import jp.unikeymodule.exception.ItemNotFoundException;

public class CryptUsecase implements ICryptUsecase {

    // Context
    private Context context;
    // KeyStore
    private KeyStore keyStore;
    // DataStore
    private IDataStore dataStore;

    /**
     * declare constructor
     * @param context context
     */
    public CryptUsecase(@NonNull Context context) {
        this.context = context;
        this.dataStore = new PrefsDataStore(context);
        try {
            this.keyStore = KeyStore.getInstance(getResString(R.string.key_provider));
            keyStore.load(null);
        } catch (Exception e) {
            Log.e(getResString(R.string.app_name), e.getMessage());
        }
    }

    /**
     * get value from data store. after, decrypt and return got value.
     * @param key key
     * @return value + error code
     */
    @Override
    public UniKeyModuleString getString(@NonNull String key) {
        UniKeyModuleString data = new UniKeyModuleString();
        try {
            String cipher = dataStore.getString(ToSha256(key));
            if (cipher == null) throw new ItemNotFoundException();
            data.value = new String(decrypt(cipher.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            data.errorCode = ErrorCode.NoSuchAlgorithm.getId();
        } catch (ItemNotFoundException e) {
            data.errorCode = ErrorCode.ItemNotFound.getId();
        } catch (NoSuchPaddingException e) {
            data.errorCode = ErrorCode.NoSuchPadding.getId();
        } catch (KeyStoreException e) {
            data.errorCode = ErrorCode.KeyStore.getId();
        } catch (UnrecoverableKeyException e) {
            data.errorCode = ErrorCode.UnrecoverableKey.getId();
        } catch (NoSuchProviderException e) {
            data.errorCode = ErrorCode.NoSuchProvider.getId();
        } catch (InvalidAlgorithmParameterException e) {
            data.errorCode = ErrorCode.InvalidAlgorithmParameter.getId();
        } catch (InvalidKeyException e) {
            data.errorCode = ErrorCode.InvalidKey.getId();
        } catch (BadPaddingException e) {
            data.errorCode = ErrorCode.BadPadding.getId();
        } catch (IllegalBlockSizeException e) {
            data.errorCode = ErrorCode.IllegalBlockSize.getId();
        } catch (Exception e) {
            data.errorCode = ErrorCode.Unknown.getId();
        }
        return data;
    }

    /**
     * register or update received key and value.
     * key's string is transformed hash(SHA256) string.
     * value's string is transformed cipher(RSA) string.
     * @param key key
     * @param value value
     * @return error code
     */
    @Override
    public int setString(@NonNull String key, @NonNull String value) {
        try {
            dataStore.setString(ToSha256(key), new String(encrypt(value.getBytes())));
        } catch (NoSuchAlgorithmException e) {
            return ErrorCode.NoSuchAlgorithm.getId();
        } catch (NoSuchPaddingException e) {
            return ErrorCode.NoSuchPadding.getId();
        } catch (KeyStoreException e) {
            return ErrorCode.KeyStore.getId();
        } catch (NoSuchProviderException e) {
            return ErrorCode.NoSuchProvider.getId();
        } catch (InvalidAlgorithmParameterException e) {
            return ErrorCode.InvalidAlgorithmParameter.getId();
        } catch (InvalidKeyException e) {
            return ErrorCode.InvalidKey.getId();
        } catch (BadPaddingException e) {
            return ErrorCode.BadPadding.getId();
        } catch (IllegalBlockSizeException e) {
            return ErrorCode.IllegalBlockSize.getId();
        } catch (Exception e) {
            return ErrorCode.Unknown.getId();
        }
        return ErrorCode.None.getId();
    }

    /**
     * confirm that received key exists in data store.
     * @param key key
     * @return value + error code.
     */
    @Override
    public UniKeyModuleBoolean hasKey(@NonNull String key) {
        UniKeyModuleBoolean data = new UniKeyModuleBoolean();
        try {
            data.value = dataStore.hasKey(ToSha256(key));
        } catch (NoSuchAlgorithmException e) {
            data.errorCode = ErrorCode.NoSuchAlgorithm.getId();
        } catch (Exception e) {
            data.errorCode = ErrorCode.Unknown.getId();
        }
        return data;
    }

    /**
     * remove data which related received key in data store.
     * @param key key
     * @return error code.
     */
    @Override
    public int deleteKey(@NonNull String key) {
        try {
            dataStore.deleteKey(ToSha256(key));
        } catch (NoSuchAlgorithmException e) {
            return ErrorCode.NoSuchAlgorithm.getId();
        } catch (Exception e) {
            return ErrorCode.Unknown.getId();
        }
        return ErrorCode.None.getId();
    }

    /**
     * remove all data in data store.
     * @return errorcode.
     */
    @Override
    public int deleteAll() {
        try {
            dataStore.deleteAll();
        } catch (Exception e) {
            return ErrorCode.Unknown.getId();
        }
        return ErrorCode.None.getId();
    }

    /**
     * encrypt
     * @param bytes
     * @return
     * @throws NoSuchPaddingException
     * @throws KeyStoreException
     * @throws NoSuchProviderException
     * @throws NoSuchAlgorithmException
     * @throws InvalidAlgorithmParameterException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    private byte[] encrypt(byte[] bytes)
            throws NoSuchPaddingException, KeyStoreException, NoSuchProviderException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance(getResString(R.string.cipher_algorithm));
        cipher.init(Cipher.ENCRYPT_MODE, getPublicKey());
        return cipher.doFinal(bytes);
    }

    /**
     * decrypt
     * @param bytes
     * @return
     * @throws NoSuchPaddingException
     * @throws KeyStoreException
     * @throws UnrecoverableKeyException
     * @throws NoSuchProviderException
     * @throws NoSuchAlgorithmException
     * @throws InvalidAlgorithmParameterException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    private byte[] decrypt(byte[] bytes)
            throws NoSuchPaddingException, KeyStoreException, UnrecoverableKeyException, NoSuchProviderException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance(getResString(R.string.cipher_algorithm));
        cipher.init(Cipher.DECRYPT_MODE, getPrivateKey());
        return cipher.doFinal(bytes);
    }

    /**
     * get public key.
     * @return public key
     * @throws KeyStoreException
     * @throws NoSuchProviderException
     * @throws NoSuchAlgorithmException
     * @throws InvalidAlgorithmParameterException
     */
    private PublicKey getPublicKey()
            throws KeyStoreException, NoSuchProviderException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        if (keyStore.containsAlias(getResString(R.string.keystore_alias))) {
            return keyStore.getCertificate(getResString(R.string.keystore_alias)).getPublicKey();
        } else {
            return createKeyPair().getPublic();
        }
    }

    /**
     * get private key.
     * @return private key
     * @throws KeyStoreException
     * @throws UnrecoverableKeyException
     * @throws NoSuchProviderException
     * @throws NoSuchAlgorithmException
     * @throws InvalidAlgorithmParameterException
     */
    private PrivateKey getPrivateKey()
            throws KeyStoreException, UnrecoverableKeyException, NoSuchProviderException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        if (keyStore.containsAlias(getResString(R.string.keystore_alias))) {
            return (PrivateKey)keyStore.getKey(getResString(R.string.keystore_alias), null);
        } else {
            return createKeyPair().getPrivate();
        }
    }

    /**
     * create key pair.
     * @return key pair.
     * @throws NoSuchProviderException
     * @throws NoSuchAlgorithmException
     * @throws InvalidAlgorithmParameterException
     */
    private KeyPair createKeyPair()
            throws NoSuchProviderException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        KeyPairGenerator kpg;
        kpg = KeyPairGenerator.getInstance(getResString(R.string.keystore_algorithm), getResString(R.string.key_provider));
        kpg.initialize(createKeyPairGeneratorSpec());
        return kpg.generateKeyPair();
    }

    /**
     * create key pair generator spec.
     * @return key pair generator spec
     */
    private KeyPairGeneratorSpec createKeyPairGeneratorSpec() {
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        end.add(Calendar.YEAR, 10000);
        return new KeyPairGeneratorSpec.Builder(context)
                .setAlias(getResString(R.string.keystore_alias))
                .setSubject(new X500Principal(String.format("CN=%s", getResString(R.string.keystore_alias))))
                .setSerialNumber(BigInteger.valueOf(100000))
                .setStartDate(start.getTime())
                .setEndDate(end.getTime())
                .build();
    }

    /**
     * transform received string to hash string.
     * @param str string
     * @return hash string
     * @throws NoSuchAlgorithmException
     */
    private String ToSha256(String str) throws NoSuchAlgorithmException {
        byte[] cipher;
        MessageDigest md = MessageDigest.getInstance(getResString(R.string.hash_algorithm));
        md.update(str.getBytes());
        cipher = md.digest();
        StringBuilder sb = new StringBuilder(2 * cipher.length);
        for (byte b : cipher) {
            sb.append(String.format("%02x", b & 0xff));
        }
        return sb.toString();
    }

    /**
     * get string which related id from resources.
     * @param id id
     * @return string
     */
    private String getResString(int id) {
        return context.getResources().getString(id);
    }
}
