package jp.unikeymodule.domain.usecase;

import android.support.annotation.NonNull;
import jp.unikeymodule.domain.entity.UniKeyModuleBoolean;
import jp.unikeymodule.domain.entity.UniKeyModuleString;

public interface ICryptUsecase {
    /**
     * get value from any data store.
     * after, decrypt and return got value
     * @param key
     * @return
     */
    UniKeyModuleString getString(@NonNull String key);

    /**
     * crypt received value.
     * after, set value to any data store.
     * @param key
     * @param value
     */
    int setString(@NonNull String key, @NonNull String value);

    /**
     * confirm that received key exists in data store.
     * @param key
     * @return
     */
    UniKeyModuleBoolean hasKey(@NonNull String key);

    /**
     * remove data which related received key in data store.
     * @param key
     * @return
     */
    int deleteKey(@NonNull String key);

    /**
     * remove all data in data store.
     * @return
     */
    int deleteAll();
}
