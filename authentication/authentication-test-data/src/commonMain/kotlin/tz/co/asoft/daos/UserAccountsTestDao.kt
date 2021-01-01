package tz.co.asoft.daos

import tz.co.asoft.IDao
import tz.co.asoft.InMemoryDao
import tz.co.asoft.UserAccount

class UserAccountsTestDao : IDao<UserAccount> by InMemoryDao("user-account")