plugins {
  java
  // jhipster-needle-gradle-plugins
}

dependencies {
  // Access to the `libs` object doesn't work in precompiled script plugin
  // https://github.com/gradle/gradle/issues/15383
  //testImplementation(libs.spring.boot.devtools)

  runtimeOnly("org.springframework.boot:spring-boot-devtools")
}

val springProfilesActive by extra("springProfileBar")
