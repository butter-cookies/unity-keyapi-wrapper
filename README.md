# unity-keymodule-wrapper

## Introduction

モバイルOSが提供しているセキュアにデータを保存するためのApiをUnityで利用するためのラッパーコードです。

## Required

> Unity5.6+<br>
> iOS5.0+, Android4.3+

## Keychain(iOS)

## KeyStore(Android)

## Api

| Api | Description |
| --- | --- |
| SetString | キーと値を紐づけて保存します。 |
| GetString | キーに紐づく値を取得します。 |
| HasKey | キーが保存されているか調べます。 |
| DeleteKey | キーとキーに紐づく値を削除します。 |
| DeleteAll | すべてのキーと値を削除します。 |

## Library

#### LuKeychainAccess

https://github.com/TheLevelUp/LUKeychainAccess

## Note

#### Editorでの挙動

Application.persistentDataPath直下にKeyModule.xmlファイルを生成して管理します。<br>
このファイルは本番では生成されない想定なので、暗号化を施さずに平文のままにしています。<br>
Id/Passといったデータの書き換えを行うデバッグを容易に行えます。<br>
