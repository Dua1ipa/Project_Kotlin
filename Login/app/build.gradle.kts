plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.login"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.login"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.play.services.auth)
    implementation(libs.google.firebase.auth)
    implementation(platform(libs.firebase.bom))

    implementation(libs.oauth) // jdk 11 (네이버 로그인)

    implementation (libs.v2.all)    //전체 모듈 설치, 2.11.0 버전부터 지원
    implementation (libs.v2.user)   //카카오 로그인 API 모듈
    implementation (libs.v2.share)  //카카오톡 공유 API 모듈
    implementation (libs.v2.talk)   //카카오톡 채널, 카카오톡 소셜, 카카오톡 메시지 API 모듈
    implementation (libs.v2.friend) //피커 API 모듈
    implementation (libs.v2.navi)   //카카오내비 API 모듈
    implementation (libs.v2.cert)   //카카오톡 인증 서비스 API 모듈

    implementation (libs.lottie)    //로티 라이브러리

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.firebase.auth)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation (libs.kotlin.stdlib)
    implementation (libs.kotlinx.coroutines.android)
    implementation (libs.androidx.legacy.support.core.utils)
    implementation (libs.androidx.browser)
    implementation (libs.androidx.security.crypto)
    implementation (libs.androidx.fragment.ktx)
    implementation (libs.androidx.lifecycle.viewmodel.ktx)
    implementation (libs.retrofit)
    implementation (libs.converter.gson)
    implementation (libs.moshi.kotlin)
    implementation (libs.logging.interceptor)

}