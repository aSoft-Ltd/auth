package tz.co.asoft

import kotlinx.coroutines.delay

class InMemoryUsersDao : InMemoryDao<User>("user"), IUsersDao {
    override suspend fun load(email: Email, pwd: String): User? = all().find {
        it.emails.contains(email.value) && it.password == pwd
    }

    override suspend fun load(phone: Phone, pwd: String): User? = all().find {
        it.phones.contains(phone.value) && it.password == pwd
    }

    override suspend fun loadUsers(ua: UserAccount): List<User> = all().filter {
        it.accounts.contains(ua)
    }

    override suspend fun loadUsersWithOneOf(emails: List<String>, phones: List<String>): List<User> {
        val users = all()
        val withEmails = users.filter { emails.any { email -> it.emails.contains(email) } }
        val withPhones = users.filter { phones.any { phone -> it.phones.contains(phone) } }
        return withEmails + withPhones
    }

    override suspend fun page(no: Int, size: Int): List<User> {
        delay(5000)
        return super.page(no, size)
    }
}