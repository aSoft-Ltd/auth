package tz.co.asoft

import kotlinx.coroutines.delay

class InMemoryUsersDao : InMemoryDao<User>("user"), IUsersDao {
    override fun load(email: Email, pwd: String) = scope.later {
        all().await().find {
            it.emails.contains(email.value) && it.password == pwd
        }
    }

    override fun load(phone: Phone, pwd: String) = scope.later {
        all().await().find {
            it.phones.contains(phone.value) && it.password == pwd
        }
    }

    override fun loadUsers(ua: UserAccount) = scope.later {
        all().await().filter {
            it.accounts.contains(ua)
        }
    }

    override fun loadUsersWithOneOf(emails: List<String>, phones: List<String>) = scope.later {
        val users = all().await()
        val withEmails = users.filter { emails.any { email -> it.emails.contains(email) } }
        val withPhones = users.filter { phones.any { phone -> it.phones.contains(phone) } }
        withEmails + withPhones
    }

    override fun page(no: Int, size: Int) = scope.later {
        delay(500)
        super.page(no, size).await()
    }
}