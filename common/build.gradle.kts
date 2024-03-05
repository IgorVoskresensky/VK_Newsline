@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.devtools.ksp)
}

android {
    namespace = "com.ivos.common"
    compileSdk = 34

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)

    //Dagger
    implementation(libs.dagger.hilt)
    ksp(libs.dagger.hilt.compiler)
    implementation(libs.hilt.navigation)

    //Ktor
    api(libs.ktor.client.core)
    api(libs.ktor.client.android)
    api(libs.ktor.negotiation)
    api(libs.ktor.json)
    api(libs.ktor.client.cio)
    api(libs.ktor.client.logging)
    api(libs.ktor.client.resources)
    api(libs.ktor.client.okhttp)

    //Room
    implementation(libs.room)
    ksp(libs.room.compiler)

    implementation(libs.compose.foundation)

    //Test
    testImplementation(libs.test.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.mockk.agent)
    testImplementation(libs.mockk.android)
    testImplementation(libs.kotest.assertions)
    testImplementation(libs.kotest.assertions.arrow)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.test.robo)

    androidTestImplementation(libs.ui.test)
    androidTestImplementation(libs.ui.test.junit4)
    androidTestImplementation(libs.ui.test.manifest)
    androidTestImplementation(libs.test.espresso.core)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.test.core)
    androidTestImplementation(platform(libs.compose.bom))
}
