plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
//    id("com.google.firebase.crashlytics")
}

android {
    namespace = "com.movieappfinal.core"
    compileSdk = 34
    buildFeatures.buildConfig = true

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        buildConfigField("String", "BASE_URL", "\"https://api.themoviedb.org/3/\"")
        buildConfigField("String","Access_Token", "\"eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJlZTEzYjMxZDU0MzY5Mjk3YzdjMGRkMWU1YzI0NDY2NyIsInN1YiI6IjY1ZDdlZDhmMzk3ZGYwMDE0OThhNDUyZiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.XW7p3ifQ403TgLXjwMGvNIv_IVjzihuRD7ygyxBPrX8\"")
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

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")

    //retrofit
    api("com.squareup.retrofit2:retrofit:2.9.0")
    api("com.squareup.retrofit2:converter-gson:2.9.0")
    api("com.squareup.okhttp3:okhttp:4.9.0")
    api("com.squareup.okhttp3:logging-interceptor:4.9.0")

    //chucker
    debugImplementation("com.github.chuckerteam.chucker:library:3.5.2")

    //Gson
    implementation("com.google.code.gson:gson:2.10.1")

    //room
    api("androidx.room:room-runtime:2.6.1")
    api("androidx.room:room-ktx:2.6.1")
    api("androidx.room:room-paging:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")

    //lifecycle
    api("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    api("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    api("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")

    //navigation
    api("androidx.navigation:navigation-fragment-ktx:2.3.5")
    api("androidx.navigation:navigation-ui-ktx:2.3.5")

    //koin
    api("io.insert-koin:koin-core:3.5.3")
    api("io.insert-koin:koin-android:3.5.3")

    //coroutines
    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    //paging
    api("androidx.paging:paging-runtime:3.2.1")

    //firebase
    api(platform("com.google.firebase:firebase-bom:32.7.2"))
//    api("com.google.firebase:firebase-crashlytics")
    api("com.google.firebase:firebase-analytics")
    api("com.google.firebase:firebase-config-ktx:21.6.1")
    api("com.google.firebase:firebase-messaging-ktx:23.4.1")
    api("com.google.firebase:firebase-auth")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}