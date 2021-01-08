package tz.co.asoft.daos

import tz.co.asoft.*

class UserRolesTestDao : IDao<UserRole> by UniqueNameInMemoryDao("role")