

plugins {
    // Plugin esencial para construir una aplicación Android.
    alias(libs.plugins.android.application)
    // Plugin para habilitar el soporte de Kotlin en Android.
    alias(libs.plugins.kotlin.android)
    // Plugin específico para habilitar Jetpack Compose.
    alias(libs.plugins.kotlin.compose)
    // Plugin para el procesador de anotaciones de Kotlin (kapt), necesario para Room.
    id("org.jetbrains.kotlin.kapt")
}

android {
    namespace = "cl.duoc.nwapp"
    compileSdk = 36 // La versión del SDK de Android contra la que se compila tu app.

    defaultConfig {
        applicationId = "cl.duoc.nwapp"
        minSdk = 24 // La versión mínima de Android que tu app soporta.
        targetSdk = 36 // La versión de Android para la que tu app está diseñada.
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11 // Versión de Java del código fuente.
        targetCompatibility = JavaVersion.VERSION_11 // Versión de Java del código compilado.
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true // Habilita explícitamente el uso de Jetpack Compose.
    }
    // --- FIX ---
    // Este bloque resuelve conflictos cuando múltiples librerías JAR incluyen
    // archivos con el mismo nombre y ruta (común en archivos META-INF).
    packaging {
        // Le dice a Gradle que excluya estos archivos específicos para evitar el error de duplicados.
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "/META-INF/INDEX.LIST"
            excludes += "/META-INF/DEPENDENCIES"
            excludes += "/META-INF/io.netty.versions.properties"
        }
    }
}

val nav_version = "2.7.7" // Variable para unificar la versión de todas las dependencias de Navigation.
val coil_version = "2.6.0" // Variable para unificar la versión de Coil.

dependencies {

    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    // --- Core & Lifecycle ---
    implementation(libs.androidx.core.ktx) // Extensiones de Kotlin para las APIs core de Android.
    implementation(libs.androidx.lifecycle.runtime.ktx) // Provee el `lifecycleScope` y otras utilidades de ciclo de vida.
    implementation(libs.androidx.activity.compose) // Integración de Jetpack Compose en Activities.

    // --- Compose ---
    // `compose-bom` (Bill of Materials) gestiona las versiones de las librerías de Compose para que sean compatibles entre sí.
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui) // Componentes fundamentales de la UI de Compose (Modifiers, Layouts, etc.).
    implementation(libs.androidx.compose.ui.graphics) // Clases de gráficos para Compose (Color, Brush, etc.).
    implementation(libs.androidx.compose.ui.tooling.preview) // Permite previsualizar Composables en el editor de Android Studio.
    implementation(libs.androidx.compose.material3)
    implementation(libs.firebase.appdistribution.gradle) // La última versión de los componentes de Material Design para Compose.
    debugImplementation(libs.androidx.compose.ui.tooling) // Herramientas de inspección de la UI de Compose.

    // --- Navigation ---
    // La biblioteca principal para la navegación entre Composables.
    implementation("androidx.navigation:navigation-compose:$nav_version")

    // --- Room (Base de Datos) ---
    implementation("androidx.room:room-runtime:2.6.1") // La biblioteca principal de Room.
    implementation("androidx.room:room-ktx:2.6.1") // Extensiones de Kotlin para Room (soporte para Coroutines y Flow).
    kapt("androidx.room:room-compiler:2.6.1") // El procesador que lee tus clases @Dao y @Database y genera el código necesario.

    // --- Google Maps & Location ---
    implementation("com.google.maps.android:maps-compose:2.11.4") // La biblioteca para integrar Google Maps en Jetpack Compose.
    implementation("com.google.android.gms:play-services-maps:18.2.0") // Los servicios de Google Play subyacentes para Mapas.
    implementation("com.google.android.gms:play-services-location:21.0.1") // Para obtener la ubicación del dispositivo.
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.6.4") // Para usar `await()` con tareas de Google Play Services en corrutinas.

    // --- Coil (Carga de Imágenes y GIFs) ---
    implementation("io.coil-kt:coil-compose:$coil_version") // La biblioteca para cargar imágenes en Compose.
    implementation("io.coil-kt:coil-gif:$coil_version") // La extensión específica para poder decodificar y mostrar GIFs.

    // --- Testing ---
    testImplementation(libs.junit) // JUnit para tests unitarios.
    androidTestImplementation(libs.androidx.junit) // JUnit para tests instrumentados en Android.
    androidTestImplementation(libs.androidx.espresso.core) // Espresso para tests de UI.
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4) // Para hacer tests de UI específicos de Compose.
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}
