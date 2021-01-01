package tz.co.asoft.repos

import tz.co.asoft.IRepo
import tz.co.asoft.Repo
import tz.co.asoft.UserRole
import tz.co.asoft.daos.UserRolesTestDao

class UserRolesTestRepo : IRepo<UserRole> by Repo(UserRolesTestDao())