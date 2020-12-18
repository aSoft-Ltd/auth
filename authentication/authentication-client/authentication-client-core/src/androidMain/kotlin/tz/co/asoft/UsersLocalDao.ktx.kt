package tz.co.asoft

import android.content.Context

inline fun UsersLocalDao(ctx: Context, name: String): IUsersLocalDao = UsersLocalDao(Storage(ctx, name))