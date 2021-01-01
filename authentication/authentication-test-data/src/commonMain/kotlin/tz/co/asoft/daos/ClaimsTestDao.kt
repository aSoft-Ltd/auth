package tz.co.asoft.daos

import tz.co.asoft.Claim
import tz.co.asoft.IDao
import tz.co.asoft.InMemoryDao

class ClaimsTestDao : IDao<Claim> by InMemoryDao("claim")