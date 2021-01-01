package services

import tz.co.asoft.*

class UsersFrontEndServiceTest {
    val claimsDao: IDao<Claim> = InMemoryDao("claim")
    val accountsDao: IDao<UserAccount> = InMemoryDao("user-account")
    val localDao: IUsersLocalDao = InMemoryUsersLocalDao()
    val service: IUsersFrontendService = InMemoryUserFrontEndService(claimsDao, accountsDao, localDao)


}