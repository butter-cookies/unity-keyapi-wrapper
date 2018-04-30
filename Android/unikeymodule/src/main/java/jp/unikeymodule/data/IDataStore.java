package jp.unikeymodule.data;

import android.support.annotation.NonNull;

public interface IDataStore {
    /**
     * get value from shared preference.
     * @param key key
     * @return value. if key does't exist, this func return null.
     */
    String getString(@NonNull String key);

    /**
     * set value to shared preference.
     * @param key key
     * @param value value
     * @return whether succeed writing key and value to shared prefs or failed.
     */
    boolean setString(@NonNull String key, @NonNull String value);

    /**
     * confirm that received key exists in shared preference.
     * @param key key
     * @return whether key exists in shared prefs or not.
     */
    boolean hasKey(@NonNull String key);

    /**
     * delete a data which related received key in data store.
     * @param key key
     * @return whether succeed deleting a data from shared prefs or not.
     */
    boolean deleteKey(@NonNull String key);

    /**
     * delete all data in data store.
     * @return whether succeed deleting all data from shared prefs or not.
     */
    boolean deleteAll();
}
