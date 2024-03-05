    import io.gitlab.arturbosch.detekt.Detekt
    import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask

    plugins {
        id("com.android.application")
        id("org.jetbrains.kotlin.android")
        id("androidx.navigation.safeargs")
        id("com.google.gms.google-services")
        id("com.google.firebase.crashlytics")
        id("io.gitlab.arturbosch.detekt")
        id("jacoco")
    }

    private val coverageExclusions = listOf(
        "**/R.class",
        "**/R\$*.class",
        "**/BuildConfig.*",
        "**/Manifest*.*"
    )

    android {
        namespace = "com.movieappfinal"
        compileSdk = 34
        configure<JacocoPluginExtension> {
            toolVersion = "0.8.10"
        }

        val jacocoTestReport = tasks.create("jacocoTestReport")

        androidComponents.onVariants { variant ->
            val testTaskName = "test${variant.name.capitalize()}UnitTest"

            val reportTask =
                tasks.register("jacoco${testTaskName.capitalize()}Report", JacocoReport::class) {
                    dependsOn(testTaskName)

                    reports {
                        html.required.set(true)
                    }

                    classDirectories.setFrom(
                        fileTree("$buildDir/tmp/kotlin-classes/${variant.name}") {
                            exclude(coverageExclusions)
                        }
                    )

                    sourceDirectories.setFrom(
                        files("$projectDir/src/main/java")
                    )
                    executionData.setFrom(file("$buildDir/jacoco/$testTaskName.exec"))
    //                executionData.setFrom(file("$buildDir/outputs/unit_test_code_coverage/${variant.name}UnitTest/$testTaskName.exec"))
                }

            jacocoTestReport.dependsOn(reportTask)
        }

        tasks.withType<Test>().configureEach {
            configure<JacocoTaskExtension> {
                isIncludeNoLocationClasses = true
                excludes = listOf("jdk.internal.*")
            }
        }


        defaultConfig {
            applicationId = "com.movieappfinal"
            minSdk = 24
            targetSdk = 34
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
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }
        kotlinOptions {
            jvmTarget = "17"
        }
        buildFeatures {
            viewBinding = true
        }

        detekt {
            toolVersion = "1.23.3"
            buildUponDefaultConfig = true // preconfigure defaults
            allRules = false // activate all available (even unstable) rules.
            config.setFrom("$projectDir/config/detekt.yml") // point to your custom config defining rules to run, overwriting default behavior
            baseline =
                file("$projectDir/config/baseline.xml") // a way of suppressing issues before introducing detekt

            tasks.withType<Detekt>().configureEach {
                reports {
                    html.required.set(true) // observe findings in your browser with structure and code snippets
                    xml.required.set(true) // checkstyle like format mainly for integrations like Jenkins
                    txt.required.set(true) // similar to the console output, contains issue signature to manually edit baseline files
                    sarif.required.set(true) // standardized SARIF format (https://sarifweb.azurewebsites.net/) to support integrations with GitHub Code Scanning
                    md.required.set(true) // simple Markdown format
                }
            }
            tasks.withType<Detekt>().configureEach {
                jvmTarget = "17"
            }
            tasks.withType<DetektCreateBaselineTask>().configureEach {
                jvmTarget = "17"
            }
        }
    }

    dependencies {

        api(project(":core"))
        implementation("androidx.core:core-ktx:1.12.0")
        implementation("androidx.appcompat:appcompat:1.6.1")
        implementation("com.google.android.material:material:1.11.0")
        implementation("androidx.constraintlayout:constraintlayout:2.1.4")

        //lottie
        implementation("com.airbnb.android:lottie:3.6.1")

        //recycleView
        implementation("androidx.recyclerview:recyclerview:1.3.2")

        //coil
        implementation("io.coil-kt:coil:2.5.0")

        //Swipe Refresh Layout
        implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")

        //Shimmer Effect
        implementation("com.facebook.shimmer:shimmer:0.5.0")

        //window
        implementation("androidx.window:window:1.2.0")
        implementation("androidx.annotation:annotation:1.7.1")

        //testing
        testImplementation("junit:junit:4.13.2")
        androidTestImplementation("androidx.test.ext:junit:1.1.5")
        androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

        // unit testing
        testImplementation("androidx.arch.core:core-testing:2.2.0")
        testImplementation("org.mockito:mockito-core:5.4.0")
        testImplementation("org.mockito:mockito-inline:3.12.4")
        testImplementation("org.mockito.kotlin:mockito-kotlin:5.0.0")
        testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")

        //detekt
    //    implementation("io.gitlab.arturbosch.detekt:detekt-formatting:1.23.5")
        detektPlugins("io.gitlab.arturbosch.detekt:detekt-rules-libraries:1.23.5")
        detektPlugins("io.gitlab.arturbosch.detekt:detekt-rules-ruleauthors:1.23.5")

        testImplementation("junit:junit:4.13.2")
        androidTestImplementation("androidx.test.ext:junit:1.1.5")
        androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    }