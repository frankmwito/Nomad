plugins {
  id ("com.android.application")
  id ("org.jetbrains.kotlin.android") // Updated Kotlin version
}

android {
  configurations {
    all {
      resolutionStrategy.force("com.google.protobuf:protobuf-java:3.11.4")
      exclude(module = "proto-google-common-protos")
      exclude(module = "protolite-well-known-types")
      exclude(module = "guava")
      exclude(group = "com.google.protobuf", module = "protobuf-javalite")
      resolutionStrategy.force("org.slf4j:slf4j-log4j12:1.7.32")
      exclude (group = "org.slf4j", module = "slf4j-android")
      exclude (group = "org.slf4j", module = "slf4j-log4j12")
    }
    /*implementation('org.some.library:library-name:version') {
    exclude group: 'org.slf4j', module: 'slf4j-android'
    exclude group: 'org.slf4j', module: 'slf4j-log4j12'
}
implementation 'org.slf4j:slf4j-api:1.7.36'*/
  }
  namespace = "com.example.nomad"
  compileSdk = 34

  defaultConfig {
    applicationId = "com.example.nomad"
    minSdk = 26
    targetSdk = 34
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    vectorDrawables {
      useSupportLibrary = true
    }
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles (getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }
  kotlinOptions {
    jvmTarget = "1.8"
  }
  buildFeatures {
    compose = true
    buildConfig = true
  }
  composeOptions {
    kotlinCompilerExtensionVersion = "1.5.8"
  }

  packagingOptions {
    resources {
      excludes += "/META-INF/{AL2.0,LGPL2.1}"
      // Exclude the io.netty.versions.properties file
      excludes += "/META-INF/io.netty.versions.properties"
      excludes += "/META-INF/INDEX.LIST"
      excludes += "META-INF/androidx.compose.material3_material3.version"
      excludes += "/com.google.type.TimeZoneOrBuilder.class"
      excludes += "/com.google.protobuf"
    }
  }


    buildToolsVersion = "34.0.0"

}

dependencies {

  implementation ("org.slf4j:slf4j-log4j12:1.7.32")
  implementation ("com.fasterxml.jackson.datatype:jackson-datatype-joda:2.13.0")
  implementation("ch.qos.logback:logback-classic:1.2.6")
  implementation("org.apache.logging.log4j:log4j-api:2.14.1")
  implementation("com.google.firebase:protolite-well-known-types:18.0.0")
  annotationProcessor("org.apache.logging.log4j:log4j-core:2.14.1")
  implementation ("androidx.core:core-ktx:1.12.0")
  implementation ("androidx.compose.material3:material3:1.2.0")
  implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
  implementation ("androidx.activity:activity-compose:1.8.2")
  implementation (platform("androidx.compose:compose-bom:2023.08.00"))
  implementation ("androidx.compose.ui:ui")
  implementation ("androidx.compose.material:material:1.6.1")
  implementation ("androidx.compose.ui:ui-graphics")
  implementation ("androidx.compose.ui:ui-tooling:1.6.1")
  implementation ("androidx.compose.runtime:runtime-livedata:1.6.1")
  implementation ("com.squareup.retrofit2:retrofit:2.9.0")
  implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
  implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
  implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
  implementation ("com.google.android.gms:play-services-location:21.1.0")
  implementation ("com.google.android.gms:play-services-maps:18.2.0")
  implementation ("androidx.activity:activity-ktx:1.8.2")
  implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
  implementation("androidx.media3:media3-exoplayer:1.2.1")
  implementation(platform("androidx.compose:compose-bom:2023.08.00"))
  implementation("androidx.compose.ui:ui-tooling-preview")
  implementation(platform("androidx.compose:compose-bom:2023.08.00"))
  implementation(platform("androidx.compose:compose-bom:2023.08.00"))
  implementation(platform("androidx.compose:compose-bom:2023.08.00"))
  testImplementation ("org.junit.jupiter:junit-jupiter-engine:5.8.1")
  testImplementation ("org.junit.jupiter:junit-jupiter:5.8.1")
  androidTestImplementation ("androidx.navigation:navigation-testing:2.7.7")
  implementation ("io.ktor:ktor-client-core:2.3.2")
  implementation ("io.ktor:ktor-client-okhttp:2.3.2")
  implementation ("io.ktor:ktor-client-android:2.1.0")
  implementation ("io.ktor:ktor-gson:1.6.4")
  implementation ("io.ktor:ktor-server-netty:2.1.2")
  implementation ("org.postgresql:postgresql:42.3.1")
  implementation ("org.jetbrains.exposed:exposed-core:0.38.2")
  implementation ("org.jetbrains.exposed:exposed-dao:0.38.2")
  implementation ("org.jetbrains.exposed:exposed-jdbc:0.38.2")
  implementation ("org.jetbrains.exposed:exposed-jodatime:0.38.2")
  implementation ("org.chromium.net:cronet-embedded:119.6045.31")
  implementation ("com.squareup.okhttp3:okhttp:4.11.0")
  implementation ("mysql:mysql-connector-java:8.0.23")
  implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
  implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
  implementation ("androidx.recyclerview:recyclerview:1.3.2")
  implementation ("com.squareup.retrofit2:retrofit:2.9.0")
  implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
  implementation("org.jetbrains.exposed:exposed-jdbc:0.38.2")
  implementation("org.jetbrains.exposed:exposed-dao:0.38.2")
  implementation("io.ktor:ktor-serialization-jackson:2.2.3")
  implementation ("com.fasterxml.jackson.core:jackson-core:2.14.1")
  implementation ("com.fasterxml.jackson.core:jackson-annotations:2.14.1")
  implementation ("com.fasterxml.jackson.core:jackson-databind:2.14.1")
  implementation ("com.fasterxml.jackson.module:jackson-module-kotlin:2.14.1")
  implementation ("com.fasterxml.jackson.core:jackson-databind:2.14.1")
  implementation("io.ktor:ktor-server-content-negotiation-jvm:2.0.2")
  implementation("io.ktor:ktor-server-status-pages-jvm:2.0.2")
  implementation ("androidx.compose.material:material:1.6.1")
  implementation ("androidx.navigation:navigation-fragment-ktx:2.7.7")
  implementation ("androidx.navigation:navigation-ui-ktx:2.7.7")
  implementation ("androidx.compose.material:material-icons-extended-android:1.6.1")
  implementation ("androidx.navigation:navigation-compose:2.7.7")
  implementation ("androidx.compose.foundation:foundation-android:1.6.1")
  implementation ("com.google.ai.client.generativeai:generativeai:0.1.2")
  testImplementation ("junit:junit:4.13.2")
  implementation (platform("org.jetbrains.kotlin:kotlin-bom:1.8.0"))
  androidTestImplementation ("androidx.test.ext:junit:1.1.5")
  androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")
  androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
  androidTestImplementation("androidx.compose.ui:ui-test-junit4")
  androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
  androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
  androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
  androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
  androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
  androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
  androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
  androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
  androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
  debugImplementation ("androidx.compose.ui:ui-tooling")
  debugImplementation ("androidx.compose.ui:ui-test-manifest")

}