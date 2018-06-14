# unity-keymodule-wrapper

## Introduction

各モバイルOSが提供しているセキュアにデータを保存するためのApi(Keychain(iOS), Keystore(Android))をUnityで利用するためのラッパーコードです。

## Required

> Unity5.6+<br>
> iOS5.0+, Android4.3+

## Api

> SetString

キーと値を紐づけて保存します。

> GetString

キーに紐づく値を取得します。

> HasKey
キーが保存されているか調べます。

> DeleteKey
キーとキーに紐づく値を削除します。

> DeleteAll
すべてのキーと値を削除します。

## Library

LuKeychainAccess<br>
https://github.com/TheLevelUp/LUKeychainAccess

## Note

> Editorでの挙動

Application.persistentDataPath直下にKeyModule.xmlファイルを生成して管理します。<br>
このファイルは本番では生成されない想定なので、暗号化を施さずに平文のままにしています。<br>
Id/Passといったデータの書き換えを行うデバッグを容易に行えます。<br>
