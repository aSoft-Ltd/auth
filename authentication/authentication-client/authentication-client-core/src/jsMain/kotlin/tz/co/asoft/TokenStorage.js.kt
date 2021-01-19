package tz.co.asoft

fun TokenStorage(namespace: String): ITokenStorage = TokenStorage(LocalKeyValueStorage(namespace))