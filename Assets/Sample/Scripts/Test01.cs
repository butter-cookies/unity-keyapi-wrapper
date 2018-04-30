using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using SiegeModule;

public class Test01 : MonoBehaviour {
    void Start() {
        UniKeyModule.SetString("testKey", "testValue");

        Debug.Log(UniKeyModule.HasKey("testKey"));
        Debug.Log(UniKeyModule.HasKey("testKey2"));
        Debug.Log(UniKeyModule.GetString("testKey"));
    }
}
