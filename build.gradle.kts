plugins {
  java
  alias(libs.plugins.sonarqube)
  checkstyle
  alias(libs.plugins.protobuf)
  jacoco
  alias(libs.plugins.spring.boot)
  alias(libs.plugins.jib)
  alias(libs.plugins.git.properties)
  // jhipster-needle-gradle-plugins
}

java {
  toolchain {
    languageVersion = JavaLanguageVersion.of(21)
  }
}

fun loadSonarProperties(): Map<String, List<String>> {
    val properties = mutableMapOf<String, List<String>>()
    File("sonar-project.properties").forEachLine { line ->
        if (!line.startsWith("#") && line.contains("=")) {
            val (key, value) = line.split("=", limit = 2)
            properties[key.trim()] = value.split(",").map { it.trim() }
        }
    }
    return properties
}

sonarqube {
    properties {
      loadSonarProperties().forEach { (key, value) ->
        property(key, value)
      }
      property("sonar.coverage.jacoco.xmlReportPaths", "build/reports/jacoco/test/jacocoTestReport.xml")
      property("sonar.junit.reportPaths", "build/test-results/test,build/test-results/integrationTest")
    }
}


checkstyle {
  configFile = rootProject.file("checkstyle.xml")
  toolVersion = libs.versions.checkstyle.get()
}

// Workaround for https://github.com/gradle/gradle/issues/27035
configurations.checkstyle {
  resolutionStrategy.capabilitiesResolution.withCapability("com.google.collections:google-collections") {
    select("com.google.guava:guava:0")
  }
}


protobuf {
  protoc {
    artifact = "com.google.protobuf:protoc:${libs.versions.protobuf.asProvider().get()}"
  }
}


jacoco {
  toolVersion = libs.versions.jacoco.get()
}

tasks.jacocoTestReport {
  dependsOn("test", "integrationTest")
  reports {
    xml.required.set(true)
    html.required.set(true)
  }
  executionData.setFrom(fileTree(buildDir).include("**/jacoco/test.exec", "**/jacoco/integrationTest.exec"))
}

tasks.jacocoTestCoverageVerification {
  dependsOn("jacocoTestReport")
  violationRules {

      rule {
          element = "CLASS"

          limit {
              counter = "LINE"
              value = "COVEREDRATIO"
              minimum = "1.00".toBigDecimal()
          }

          limit {
              counter = "BRANCH"
              value = "COVEREDRATIO"
              minimum = "1.00".toBigDecimal()
          }
      }
  }
  executionData.setFrom(fileTree(buildDir).include("**/jacoco/test.exec", "**/jacoco/integrationTest.exec"))
}


defaultTasks "bootRun"

springBoot {
  mainClass = "tech.jhipster.gradleapp.GradleappApp"
}


jib {
  from {
    image = "eclipse-temurin:21-jre-jammy"
    platforms {
      platform {
        architecture = "amd64"
        os = "linux"
      }
    }
  }
  to {
    image = "gradleapp:latest"
  }
  container {
    entrypoint = listOf("bash", "-c", "/entrypoint.sh")
    ports = listOf("8081")
    environment = mapOf(
     "SPRING_OUTPUT_ANSI_ENABLED" to "ALWAYS",
     "JHIPSTER_SLEEP" to "0"
    )
    creationTime = "USE_CURRENT_TIMESTAMP"
    user = "1000"
  }
  extraDirectories {
    paths {
      path {
        setFrom("src/main/docker/jib")
      }
    }
    permissions = mapOf("/entrypoint.sh" to "755")
  }
}

gitProperties {
  failOnNoGitDirectory = false
  keys = listOf("git.branch", "git.commit.id.abbrev", "git.commit.id.describe", "git.build.version")
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
  implementation(libs.protobuf.java)
  implementation(platform(libs.spring.boot.dependencies))
  implementation(libs.spring.boot.starter)
  implementation(libs.spring.boot.configuration.processor)
  implementation(libs.commons.lang3)
  implementation(libs.spring.boot.starter.validation)
  implementation(libs.spring.boot.starter.web)
  implementation(libs.spring.boot.starter.actuator)
  implementation(libs.spring.boot.starter.data.jpa)
  implementation(libs.hikariCP)
  implementation(libs.hibernate.core)
  implementation(libs.liquibase.core)
  implementation(libs.spring.boot.starter.security)
  implementation(libs.jjwt.api)
  implementation(libs.springdoc.openapi.starter.webmvc.ui)
  implementation(libs.springdoc.openapi.starter.webmvc.api)
  // jhipster-needle-gradle-implementation-dependencies
  // jhipster-needle-gradle-compile-dependencies
  runtimeOnly(libs.postgresql)
  runtimeOnly(libs.jjwt.impl)
  runtimeOnly(libs.jjwt.jackson)
  // jhipster-needle-gradle-runtime-dependencies

  testImplementation(libs.protobuf.java.util)
  testImplementation(libs.spring.boot.starter.test)
  testImplementation(libs.reflections)
  testImplementation(libs.testcontainers.postgresql)
  testImplementation(libs.h2)
  testImplementation(libs.spring.security.test)
  // jhipster-needle-gradle-test-dependencies
}

tasks.test {
  filter {
    includeTestsMatching("**Test*")
    excludeTestsMatching("**IT*")
    excludeTestsMatching("**CucumberTest*")
  }
  useJUnitPlatform()
  finalizedBy("jacocoTestCoverageVerification")
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
