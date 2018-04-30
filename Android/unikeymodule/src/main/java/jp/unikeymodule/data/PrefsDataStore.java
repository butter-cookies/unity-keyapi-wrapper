package jp.unikeymodule.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import jp.unikeymodule.R;

public class PrefsDataStore implements IDataStore {

    // shared prefs
    private SharedPreferences sharedPrefs;

    /**
     * decline constructor
     * @param context
     */
    public PrefsDataStore(@NonNull Context context) {
        Resources resources = context.getResources();
        String appName = resources.getString(R.string.app_name);
        sharedPrefs = context.getSharedPreferences(appName, Context.MODE_PRIVATE);
    }

    /**
     * get value from shared preference.
     * @param key key
     * @return value. if key does't exist, this func return null.
     */
    @Override
    public String getString(@NonNull String key) {
        return sharedPrefs.getString(key, null);
    }

    /**
     * set value to shared preference.
     * @param key key
     * @param value value
     * @return whether succeed writing key and value to shared prefs or failed.
     */
    @Override
    public boolean setString(@NonNull String key, @NonNull String value) {
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    /**
     * confirm that received key exists in shared preference.
     * @param key key
     * @return whether key exists in shared prefs or not.
     */
    @Override
    public boolean hasKey(@NonNull String key) {
        return sharedPrefs.contains(key);
    }

    /**
     * delete a data which related received key in data store.
     * @param key key
     * @return whether succeed deleting a data from shared prefs or not.
     */
    @Override
    public boolean deleteKey(@NonNull String key) {
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.remove(key);
        return editor.commit();
    }

    /**
     * delete all data in data store.
     * @return whether succeed deleting all data from shared prefs or not.
     */
    @Override
    public boolean deleteAll() {
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.clear();
        return editor.commit();
    }
}
