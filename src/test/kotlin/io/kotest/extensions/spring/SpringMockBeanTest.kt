package io.kotest.extensions.spring

import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe
import org.mockito.kotlin.any
import org.mockito.kotlin.argThat
import org.mockito.kotlin.reset
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean

@SpringBootTest(classes = [Components::class])
class SpringMockBeanTest : WordSpec() {

   override fun extensions() = listOf(SpringExtension)

   @Autowired
   lateinit var sut: UserService

   @MockBean
   lateinit var repository: UserRepository

   init {
      "Defining a behaviour on a MockBean" should {
         "work the first time" {
            whenever(repository.findUserByName(argThat { input -> input.length == 4 }))
               .thenReturn(User("testUserOne"))
            val result = sut.repository.findUserByName("test")
            result shouldBe User("testUserOne")
         }

         "and also the second time" {
            whenever(repository.findUserByName(any()))
               .thenReturn(User("test"))

            val result = sut.repository.findUserByName("test")
            result shouldBe User("test")
         }

         "even if resetting the mock manually will solve it" {
            reset(repository)
            whenever(repository.findUserByName(any()))
               .thenReturn(User("test"))

            val result = sut.repository.findUserByName("test")
            result shouldBe User("test")
         }
      }
   }
}
