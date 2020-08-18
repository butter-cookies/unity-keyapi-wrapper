# unity-keymodule-wrapper

## 説明

Unityで大事な情報をモバイル端末に保存するために作りました。
KeyChain(iOS),KeyStore(Android)のラッパーです。

## Required

> Unity5.6+<br>
> iOS5.0+, Android4.3+

## Library

#### LuKeychainAccess

https://github.com/TheLevelUp/LUKeychainAccess

## Note

#### Editorでの挙動

Application.persistentDataPath直下にKeyModule.xmlファイルを生成して管理します。<br>
このファイルは本番では生成されない想定なので、暗号化を施さずに平文のままにしています。<br>
Id/Passといったデータの書き換えを行うデバッグを容易に行えます。<br>
