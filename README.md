# UniKeyModule(beta)

> 概要
各モバイルOSが提供しているセキュアにデータを保存するためのApi(Keychain(iOS), Keystore(Android))をUnityで利用するためのラッパーコードです。

> 動作環境
Unity5.6+<br>
iOS5.0+, Android4.3+<br>

> Api
* SetString<br>
キーと値を紐づけて保存します。
* GetString<br>
キーに紐づく値を取得します。
* HasKey<br>
キーが保存されているか調べます。
* DeleteKey<br>
キーとキーに紐づく値を削除します。
* DeleteAll<br>
すべてのキーと値を削除します。
