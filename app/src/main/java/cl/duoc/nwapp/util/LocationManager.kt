// Archivo: util/LocationManager.kt
package cl.duoc.nwapp.util

import android.annotation.SuppressLint
import android.content.Context
import android.os.Looper
import com.google.android.gms.location.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.util.concurrent.TimeUnit

class LocationManager(private val context: Context) {

    // FusedLocationProviderClient es la API principal para interactuar con el proveedor de ubicación.
    private val fusedLocationClient: com.google.android.gms.location.FusedLocationProviderClient =
        com.google.android.gms.location.LocationServices.getFusedLocationProviderClient(context)

    // callbackFlow nos permite convertir una API basada en callbacks (como la de ubicación) en un Flow de Kotlin.
    @SuppressLint("MissingPermission") // La advertencia se maneja con la verificación de permisos en la UI.
    fun locationFlow(): Flow<com.google.android.gms.location.LocationResult> {
        return callbackFlow {
            // 1. Configurar la solicitud de ubicación
            val locationRequest = com.google.android.gms.location.LocationRequest.Builder(com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY, TimeUnit.SECONDS.toMillis(5))
                .build()

            // 2. Crear el Callback que recibirá las actualizaciones
            val locationCallback = object : com.google.android.gms.location.LocationCallback() {
                override fun onLocationResult(locationResult: com.google.android.gms.location.LocationResult) {
                    // Cuando se recibe una nueva ubicación, se emite al Flow.
                    trySend(locationResult)
                }
            }

            // 3. Empezar a solicitar actualizaciones de ubicación
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper() // Usamos el Looper principal
            )

            // 4. Cuando el Flow se cancela, dejamos de escuchar para ahorrar batería.
            awaitClose {
                fusedLocationClient.removeLocationUpdates(locationCallback)
            }
        }
    }
}
