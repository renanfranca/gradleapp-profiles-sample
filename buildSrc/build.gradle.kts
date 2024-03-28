plugins {
  `kotlin-dsl`
}

repositories {
  gradlePluginPortal()
  mavenCentral()
}

dependencies {
//  implementation(libs.plugins.git.properties)
//  implementation("com.bmuschko:gradle-docker-plugin:6.4.0")
//  implementation("org.sonarsource.scanner.gradle:sonarqube-gradle-plugin:4.4.1.3373")
//  implementation("com.gorylenko.gradle-git-properties:gradle-git-properties:2.4.1")
  implementation(libs.git.properties)
}

