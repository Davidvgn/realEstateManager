
plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin") version "2.0.1"
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.openclassrooms.realestatemanager"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.openclassrooms.realestatemanager"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
        isCoreLibraryDesugaringEnabled = true
    }
    kotlinOptions {
        jvmTarget = "17"
        freeCompilerArgs = listOf(
            "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
            "-opt-in=kotlinx.coroutines.DelicateCoroutinesApi"
        )
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.1")
    implementation("androidx.activity:activity-ktx:1.7.2")
    implementation("androidx.fragment:fragment-ktx:1.6.1")
    implementation("androidx.navigation:navigation-fragment-ktx:2.5.3")
    implementation("androidx.navigation:navigation-ui-ktx:2.5.3")
    implementation("com.google.android.gms:play-services-awareness:19.0.1")


    // GOOGLE MAPS SDK
    implementation("com.google.android.gms:play-services-maps:18.1.0")
    implementation("com.google.maps.android:android-maps-utils:2.3.0")

    // DESUGARING
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.3")

    // FLEXBOX
    implementation("com.google.android.flexbox:flexbox:3.0.0")

    // GLIDE
    implementation("com.github.bumptech.glide:glide:4.15.1")
    kapt("com.github.bumptech.glide:compiler:4.15.1")

    // CarouselRecyclerView
    implementation("com.github.sparrow007:carouselrecyclerview:1.2.6")

    // HILT
    implementation("com.google.dagger:hilt-android:2.46.1")
    kapt("com.google.dagger:hilt-compiler:2.46.1")

    // ROOM
    implementation("androidx.room:room-runtime:2.5.2")
    kapt("androidx.room:room-compiler:2.5.2")
    implementation("androidx.room:room-ktx:2.5.2")

    // RETROFIT
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.10.0")

    // GSON
    implementation("com.google.code.gson:gson:2.10")

    // WORK
    implementation("androidx.work:work-runtime-ktx:2.8.1")
    implementation("androidx.hilt:hilt-work:1.0.0")
    implementation("androidx.hilt:hilt-compiler:1.0.0")

    // DATASTORE
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // TEST
    testImplementation("junit:junit:4.13.2")

    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}

//// open JaCoCo report after generation
//def openReport(htmlOutDir) {
//    final reportPath = "$htmlOutDir\\index.html"
//
//    println "HTML Report: $reportPath"
//
//    def os = org.gradle.internal.os.OperatingSystem.current()
//    if (os.isWindows()) {
//        exec { commandLine 'cmd', '/c', "start $reportPath" }
//    } else if (os.isLinux()) {
//        try {
//            exec { commandLine 'xdg-open', "$reportPath" }
//        } catch (Exception ignored) {
//            if (localProperties.containsKey("linux-html-cmd")) {
//                exec { commandLine properties.get("linux-html-cmd"), "$reportPath" }
//            } else {
//                println "'linux-html-cmd' property could not be found in 'local.properties'"
//            }
//        }
//    }
//}
//
//// JaCoCo stuff
//android.applicationVariants.all { variant ->
//    def variantName = variant.name.capitalize()
//    def filesToAnalyze = [
//            '**/*ViewModel.class',
//            '**/*Repository.class'
//    ]
//
//    task("jacoco${variantName}Report", type: JacocoReport, dependsOn: "test${variantName}UnitTest") {
//        group 'Reporting'
//        description "Generate ${variantName} Jacoco coverage reports."
//
//        def htmlOutDir = layout.buildDirectory.dir("reports/jacoco/$name/html").get().asFile
//
//        doLast {
//            openReport htmlOutDir
//        }
//
//        reports {
////            xml.enabled = true
//            html {
//                destination htmlOutDir
//            }
//        }
//
//        getSourceDirectories().setFrom(files(android.sourceSets.main.java.srcDirs))
//        getExecutionData().setFrom(files("${buildDir}/jacoco/test${variantName}UnitTest.exec"))
//        getClassDirectories().setFrom(fileTree(dir: variant.javaCompiler.destinationDir, includes: filesToAnalyze))
//    }
//}