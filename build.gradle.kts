buildscript {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://oss.sonatype.org/content/repositories/snapshots")
        maven(url ="https://plugins.gradle.org/m2/")
    }
    dependencies {
        classpath(libs.hilt.gradlePlugin)
    }
}

plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.ksp) apply false // https://github.com/google/dagger/issues/3965
}
true // Needed to make the Suppress annotation work for the plugins block