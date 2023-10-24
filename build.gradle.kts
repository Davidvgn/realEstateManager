// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.1" apply false
    id("com.android.library") version "8.0.1" apply false
    id("org.jetbrains.kotlin.android") version "1.8.0" apply false
    id("com.google.dagger.hilt.android") version "2.45" apply false
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin") version "2.0.1"
    kotlin("kapt") version "1.8.0"
}
//buildscript {
//    ext {
//        kotlin_version = '1.7.0'
//        hilt_version = '2.42'
//    }
//
//    repositories {
//        google()
//        mavenCentral()
//    }
//
//    dependencies {
//        classpath 'com.android.tools.build:gradle:8.0.2'
//        classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.0"
//    }
//}
//
//plugins {
//    id 'com.android.application' version '8.0.2' apply false
//    id "org.jetbrains.kotlin.kapt" version "1.9.10"
////    id 'com.google.gms.google-services' version '4.3.14' apply false
//    id 'com.google.dagger.hilt.android' version "$hilt_version" apply false
//    id 'com.google.android.libraries.mapsplatform.secrets-gradle-plugin' version '2.0.1' apply false
//}
//
//task clean(type: Delete) {
//    delete rootProject.buildDir
//}