package jp.unikeymodule.controller;

import com.unity3d.player.UnityPlayer;
import jp.unikeymodule.domain.entity.UniKeyModuleBoolean;
import jp.unikeymodule.domain.entity.UniKeyModuleString;
import jp.unikeymodule.domain.usecase.CryptUsecase;
import jp.unikeymodule.domain.usecase.ICryptUsecase;

public class UniKeyModule {

    // service
    private static ICryptUsecase service;

    /**
     * get or create singleton service.
     * @return singleton instance.
     */
    private static synchronized ICryptUsecase getService() {
        if (service == null) {
            service = new CryptUsecase(UnityPlayer.currentActivity);
        }
        return service;
    }

    /**
     * get string from data store via service.
     * @param key key
     * @return value + error code
     */
    public static UniKeyModuleString getString(String key) {
        return getService().getString(key);
    }

    /**
     * set string to data store via service.
     * @param key key
     * @param value value
     * @return error code
     */
    public static int setString(String key, String value) {
        return getService().setString(key, value);
    }

    /**
     * confirm that received key exists in data store via service.
     * @param key key
     * @return value + error code
     */
    public static UniKeyModuleBoolean hasKey(String key) {
        return getService().hasKey(key);
    }

    /**
     * delete a data which related receive key from data store via service.
     * @param key key
     * @return error code
     */
    public static int deleteKey(String key) {
        return getService().deleteKey(key);
    }

    /**
     * delete all data from data store via service
     * @return error code
     */
    public static int deleteAll() {
        return getService().deleteAll();
    }
}
