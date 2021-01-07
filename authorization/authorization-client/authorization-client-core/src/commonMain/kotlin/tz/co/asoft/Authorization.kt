package tz.co.asoft

object Authorization : DaoFactory<AuthorizationDao>() {
    object repo {
        fun claims() = repo { Repo(dao.claims) }
        fun roles() = repo { Repo(dao.roles) }
    }
}