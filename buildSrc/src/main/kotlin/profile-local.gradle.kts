plugins {
  java
  // jhipster-needle-gradle-plugins
}

val libs = versionCatalogs.named("libs")

println("libs: $libs")
// Iterate through the libraries in the version catalog
libs.libraryAliases.forEach { alias ->
    val library = libs.findLibrary(alias).get()
    println("Alias: $alias")
    println("Group: ${library.get().group}")
    println("Group: ${library.get().name}")
    println("Version: ${library.get().version}")
    println()
}
println("spring.boot.devtools:")
println(libs.findLibrary("spring.boot.devtools").get())

dependencies {
  // Access to the `libs` object doesn't work in precompiled script plugin
  // https://github.com/gradle/gradle/issues/15383
  //testImplementation(libs.spring.boot.devtools)

  //runtimeOnly("org.springframework.boot:spring-boot-devtools")
  runtimeOnly(libs.findLibrary("spring.boot.devtools").get())
}

val springProfilesActive by extra("springProfileBar")
