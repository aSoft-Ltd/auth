package tz.co.asoft

fun UsersLocalDao(name: String): IUsersLocalDao = UsersLocalDao(Storage(name))