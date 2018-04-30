#if !UNITY_EDITOR && UNITY_IOS
#elif !UNITY_EDITOR && UNITY_ANDROID
#else
using System;
using System.Collections;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Xml;
using System.Xml.Serialization;
using UnityEngine;

namespace SiegeModule
{
    public static class UniKeyModuleOther {
        /// <summary>
        /// key-value data structure.
        /// </summary>
        public struct KV {
            /// <summary>
            /// key
            /// </summary>
            public string key;
            /// <summary>
            /// value
            /// </summary>
            public string value;

            /// <summary>
            /// constructor
            /// </summary>
            public KV(string key, string value) {
                this.key = key;
                this.value = value;
            }
        }

        /// <summary>
        /// cache on memory.
        /// </summary>
        private static List<KV> cache = new List<KV>();

        /// <summary>
        /// static constructor
        /// when program is starting, it deserialize data from xml-file,
        /// if xml-file existed.
        /// </summary>
        static UniKeyModuleOther() {
            if (!File.Exists(GetFilePath())) {
                return;
            }
            try {
                Deserialize();
            } catch (Exception e) {
                Debug.LogError(e.Message);
            }
        }

        /// <summary>
        /// get value from cache.
        /// </summary>
        /// <returns>value + error code.</returns>
        /// <param name="key">key</param>
        public static UniKeyModuleString GetString(string key) {
            var data = new UniKeyModuleString();
            if (!File.Exists(GetFilePath())) {
                data.errorCode = (int)ErrorCode.ItemNotFound;
                return data;
            }
            var index = cache.FindIndex((d) => { return d.key == key; });
            if (index == -1) {
                data.value = null;
                data.errorCode = (int)ErrorCode.ItemNotFound;
            } else {
                data.value = cache[index].value;
                data.errorCode = (int)ErrorCode.None;
            }
            return data;
        }

        /// <summary>
        /// set value to cache and xml.
        /// </summary>
        /// <returns>error code</returns>
        /// <param name="key">key</param>
        /// <param name="value">value</param>
        public static int SetString(string key, string value) {
            var backup = new List<KV>(cache);
            var index = cache.FindIndex((kv) => { return kv.key == key; });
            if (index == -1) {
                cache.Add(new KV(key, value));
            } else {
                var kv = cache[index];
                kv.value = value;
                cache[index] = kv;
            }
            try {
                Serialize();
                return (int)ErrorCode.None;
            } catch (Exception e) {
                Debug.LogError(e.Message);
                cache = backup;
                return (int)ErrorCode.Unknown;
            }
        }

        /// <summary>
        /// confirm that cache contains key
        /// </summary>
        /// <returns>value + error code</returns>
        /// <param name="key">key</param>
        public static UniKeyModuleBoolean HasKey(string key) {
            var data = new UniKeyModuleBoolean();
            if (!File.Exists(GetFilePath())) {
                data.errorCode = (int)ErrorCode.ItemNotFound;
                return data;
            }
            var index = cache.FindIndex((d) => { return d.key == key; });
            if (index == -1) {
                data.value = false;
                data.errorCode = (int)ErrorCode.ItemNotFound;
            } else {
                data.value = true;
                data.errorCode = (int)ErrorCode.None;
            }
            return data;
        }

        /// <summary>
        /// delete a data from cache and xml.
        /// </summary>
        /// <returns>error code</returns>
        /// <param name="key">key</param>
        public static ErrorCode DeleteKey(string key) {
            var backup = new List<KV>(cache);
            var index = cache.FindIndex((kv) => { return kv.key == key; });
            if (index == -1) {
                return ErrorCode.ItemNotFound;
            } else {
                cache.RemoveAt(index);
            }
            try {
                Serialize();
                return ErrorCode.None;
            } catch (Exception e) {
                Debug.LogError(e.Message);
                cache = backup;
                return ErrorCode.Unknown;
            }
        }

        /// <summary>
        /// delete all data from cache and xml.
        /// </summary>
        /// <returns>error code</returns>
        public static int DeleteAll()
        {
            var backup = new List<KV>(cache);
            cache.Clear();
            try {
                Serialize();
                return (int)ErrorCode.None;
            } catch (Exception e) {
                Debug.LogError(e.Message);
                cache = backup;
                return (int)ErrorCode.Unknown;
            }
        }

        /// <summary>
        /// serialize cache and create xml file.
        /// </summary>
        private static void Serialize() {
            using(var stream = new FileStream(GetFilePath(), FileMode.Create)) {
                var serializer = new XmlSerializer(typeof(List<KV>));
                serializer.Serialize(stream, cache);
            }
        }

        /// <summary>
        /// deserialize xml file and arrange to cache.
        /// </summary>
        private static void Deserialize() {
            using(var stream = new FileStream(GetFilePath(), FileMode.Open)) {
                var serializer = new XmlSerializer(typeof(List<KV>));
                cache = (List<KV>)serializer.Deserialize(stream);
            }
        }

        /// <summary>
        /// get path about destination xml file.
        /// </summary>
        /// <returns>file path</returns>
        private static string GetFilePath() {
            return Application.persistentDataPath + "/UniKeyModule.xml";
        }
    }
}
#endif
