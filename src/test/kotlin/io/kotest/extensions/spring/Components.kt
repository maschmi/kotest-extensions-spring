package io.kotest.extensions.spring

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Components {

   @Bean
   fun userService(repo: UserRepository) = UserService(repo)

   @Bean
   fun userRepository() = DefaultRepository()
}
