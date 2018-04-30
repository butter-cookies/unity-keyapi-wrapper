# UniKeyModule(beta)

## What is UniKeyModule?
UniKeyModule is implemention that can save safety important infomation such as UserId, Password or UserToken in Unity Mobile Application.<br>

## Why Secure?
UniKeyModule binds Android/iOS secure API.<br>
If you run App in Android, UniKeyModule uses KeyStore API. Otherwise, If you run App in iOS, UniKeyModule uses KeychainAPI.<br>
These API is Generally speaking of the most secure in Each OS.<br>

## Requirements
UniKeyModule requires iOS5.0+, Android4.3+.<br>

## Introduction
UniKeyModule provides simple API.<br>

* GetString
* SetString
* HasKey
* DeleteKey
* DeleteAll
