using System.Collections;
using System.Collections.Generic;
using System.Runtime.InteropServices;
using UnityEngine;

namespace SiegeModule {
    public struct UniKeyModuleString {
        /// <summary>
        /// received string
        /// </summary>
        public string value;
        /// <summary>
        /// received error code
        /// </summary>
        public int errorCode;
    }

    public struct UniKeyModuleBoolean {
        /// <summary>
        /// received boolean
        /// </summary>
        public bool value;
        /// <summary>
        /// received error code
        /// </summary>
        public int errorCode;
    }

    public static class UniKeyModule {
        #if UNITY_IOS && !UNITY_EDITOR
        /// <summary>
        /// get a data that related key in KeyModule.
        /// </summary>
        [DllImport("__Internal")]
        private static extern UniKeyModuleString getString(string key);
        /// <summary>
        /// register or update a data that related key in KeyModule.
        /// </summary>
        [DllImport("__Internal")]
        private static extern int setString(string key, string value);
        /// <summary>
        /// check that key exists in KeyModule.
        /// </summary>
        [DllImport("__Internal")]
        private static extern UniKeyModuleBoolean hasKey(string key);
        /// <summary>
        /// remove a data that related key in KeyModule.
        /// </summary>
        [DllImport("__Internal")]
        private static extern int deleteKey(string key);
        /// <summary>
        /// remove all data in KeyModule.
        /// </summary>
        [DllImport("__Internal")]
        private static extern int deleteAll();
        #elif UNITY_ANDROID && !UNITY_EDITOR
        /// <summary>
        /// package name about aar.
        /// </summary>
        private const string PackageName = "jp.unikeymodule.controller.UniKeyModule";
        #endif

        /// <summary>
        /// get string from native code.
        /// </summary>
        /// <returns>value</returns>
        /// <param name="key">key</param>
        public static string GetString(string key) {
            UniKeyModuleString data;

            #if UNITY_IOS && !UNITY_EDITOR
            data = getString(key);
            #elif UNITY_ANDROID && !UNITY_EDITOR
            using (var module = new AndroidJavaClass(PackageName))
            using (var obj = module.CallStatic<AndroidJavaObject>("getString", key)) {
                data = new UniKeyModuleString();
                data.value = obj.Get<string>("value");
                data.errorCode = obj.Get<int>("errorCode");
            }
            #else
            data = UniKeyModuleOther.GetString(key);
            #endif

            switch((ErrorCode)data.errorCode) {
                case ErrorCode.None : return data.value;
                case ErrorCode.ItemNotFound : return null;
            }
            throw new UniKeyModuleException((ErrorCode)data.errorCode);
        }

        /// <summary>
        /// set string to native code.
        /// </summary>
        /// <param name="key">key</param>
        /// <param name="value">value</param>
        public static void SetString(string key, string value) {
            var code = ErrorCode.None;

            #if UNITY_IOS && !UNITY_EDITOR
            code = (ErrorCode)setString(key, value);
            #elif UNITY_ANDROID && !UNITY_EDITOR
            using (var module = new AndroidJavaClass(PackageName)) {
                code = (ErrorCode)module.CallStatic<int>("setString", key, value);
            }
            #else
            code = (ErrorCode)UniKeyModuleOther.SetString(key, value);
            #endif

            if (code != ErrorCode.None) throw new UniKeyModuleException(code);
        }

        /// <summary>
        /// confirm that received key exists in native code.
        /// </summary>
        /// <returns>whether key contains native code, or not.</returns>
        /// <param name="key">key</param>
        public static bool HasKey(string key) {
            UniKeyModuleBoolean data;

            #if UNITY_IOS && !UNITY_EDITOR
            data = hasKey(key);
            #elif UNITY_ANDROID && !UNITY_EDITOR
            using (var module = new AndroidJavaClass(PackageName))
            using (var obj = module.CallStatic<AndroidJavaObject>("hasKey", key)) {
                data = new UniKeyModuleBoolean();
                data.value = obj.Get<bool>("value");
                data.errorCode = obj.Get<int>("errorCode");
            }
            #else
            data = UniKeyModuleOther.HasKey(key);
            #endif

            switch((ErrorCode)data.errorCode) {
                case ErrorCode.None :
                case ErrorCode.ItemNotFound : return data.value;
            }
            throw new UniKeyModuleException((ErrorCode)data.errorCode);
        }

        /// <summary>
        /// remove data which related received key in native code.
        /// </summary>
        /// <param name="key">key</param>
        public static void DeleteKey(string key) {
            var code = ErrorCode.None;

            #if UNITY_IOS && !UNITY_EDITOR
            code = (ErrorCode)deleteKey(key);
            #elif UNITY_ANDROID && !UNITY_EDITOR
            using (var module = new AndroidJavaClass(PackageName)) {
                code = (ErrorCode)module.CallStatic<int>("deleteKey", key);
            }
            #else
            code = (ErrorCode)UniKeyModuleOther.DeleteKey(key);
            #endif

            switch(code) {
                case ErrorCode.None :
                case ErrorCode.ItemNotFound : return;
            }
            throw new UniKeyModuleException(code);
        }

        /// <summary>
        /// remove all data in data store.
        /// </summary>
        public static void DeleteAll() {
            var code = ErrorCode.None;

            #if UNITY_IOS && !UNITY_EDITOR
            code = (ErrorCode)deleteAll();
            #elif UNITY_ANDROID && !UNITY_EDITOR
            using (var module = new AndroidJavaClass(PackageName)) {
                code = (ErrorCode)module.CallStatic<int>("deleteAll");
            }
            #else
            code = (ErrorCode)UniKeyModuleOther.DeleteAll();
            #endif

            switch(code) {
                case ErrorCode.None : return;
            }
            throw new UniKeyModuleException(code);
        }
    }
}