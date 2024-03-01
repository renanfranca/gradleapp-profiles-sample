plugins {
  java
  // jhipster-needle-gradle-plugins
}

java {
  toolchain {
    languageVersion = JavaLanguageVersion.of(21)
  }
}
// jhipster-needle-gradle-plugins-configurations

repositories {
  mavenCentral()
  // jhipster-needle-gradle-repositories
}

group = "tech.jhipster.gradleapp"
version = "0.0.1-SNAPSHOT"

ext {
  // jhipster-needle-gradle-properties
}

dependencies {
  // jhipster-needle-gradle-implementation-dependencies
  // jhipster-needle-gradle-compile-dependencies
  // jhipster-needle-gradle-runtime-dependencies
  testImplementation(libs.junit.engine)
  testImplementation(libs.junit.params)
  testImplementation(libs.assertj)
  testImplementation(libs.mockito)
  // jhipster-needle-gradle-test-dependencies
}

tasks.test {
  filter {
    includeTestsMatching("**Test*")
    excludeTestsMatching("**IT*")
    excludeTestsMatching("**CucumberTest*")
  }
  useJUnitPlatform()
}

val integrationTest = task<Test>("integrationTest") {
  description = "Runs integration tests."
  group = "verification"
  shouldRunAfter("test")
  filter {
    includeTestsMatching("**IT*")
    includeTestsMatching("**CucumberTest*")
    excludeTestsMatching("**Test*")
  }
  useJUnitPlatform()
}
