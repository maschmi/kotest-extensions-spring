package io.kotest.extensions.spring

import org.springframework.stereotype.Component

data class User(val name: String)

interface UserRepository {
  fun findUser(): User

  fun findUserByName(by: String): User?
}

class DefaultRepository : UserRepository {
  override fun findUser(): User = User("system_user")
   override fun findUserByName(by: String): User? = null
}

@Component
class UserService(val repository: UserRepository)
