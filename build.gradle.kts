import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

// issue https://youtrack.jetbrains.com/issue/KTIJ-19370
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
   java
   `java-library`
   signing
   `maven-publish`
   alias(libs.plugins.kotlin.jvm)
   alias(libs.plugins.kotlin.spring)
}

version = Ci.version

dependencies {
   implementation(libs.kotlin.reflect)
   implementation(libs.kotest.framework.api)
   implementation(libs.spring.context)
   implementation(libs.spring.test)
   implementation(libs.kotlinx.coroutines)
   implementation(libs.byteBuddy)

   testImplementation(libs.kotest.runner.junit5)
   testImplementation(libs.kotest.framework.datatest)
   testImplementation(libs.kotest.property)
   testImplementation(libs.spring.boot.test)
   testImplementation(libs.mockito.kotlin)
}

tasks.withType<Test> {
   useJUnitPlatform()
   testLogging {
      showExceptions = true
      showStandardStreams = true
      exceptionFormat = TestExceptionFormat.FULL
   }
}

tasks.withType<KotlinCompile> {
   kotlinOptions.jvmTarget = "11"
}

java {
   targetCompatibility = JavaVersion.VERSION_11
   sourceCompatibility = JavaVersion.VERSION_11
}

repositories {
   mavenLocal()
   mavenCentral()
   maven(url = "https://s01.oss.sonatype.org/content/repositories/snapshots")
}

apply("./publish.gradle.kts")
