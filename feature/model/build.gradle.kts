plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}
java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}