// Top-level build.gradle.kts

buildscript {
    dependencies {
        // Google Services (Firebase)
        classpath("com.google.gms:google-services:4.4.0")
    }
}

plugins {
    kotlin("android") version "1.9.10" apply false
    id("com.android.application") version "8.1.0" apply false
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}
