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

/**
 * Clase de utilidad para gestionar el acceso a la ubicación del dispositivo.
 * Encapsula la complejidad de la API de ubicación de Google Play Services.
 *
 * @param context El contexto de la aplicación.
 */
class LocationManager(private val context: Context) {

    // `FusedLocationProviderClient` es la API principal para interactuar con el proveedor de ubicación.
    // Combina señales de GPS, Wi-Fi y redes móviles para obtener la mejor ubicación posible.
    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)


    @SuppressLint("MissingPermission") // La verificación de permisos se realiza en la UI antes de llamar a esta función.
    fun locationFlow(): Flow<LocationResult> {
        return callbackFlow {
            // 1. Configurar la solicitud de ubicación.
            val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, TimeUnit.SECONDS.toMillis(5))
                .build()

            // 2. Crear el Callback que recibirá las actualizaciones de ubicación.
            val locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    // Cuando se recibe una nueva ubicación desde el sistema...
                    // `trySend` la emite al flujo. Es seguro llamarlo desde cualquier hilo.
                    trySend(locationResult)
                }
            }

            // 3. Empezar a solicitar actualizaciones de ubicación al FusedLocationProviderClient.
            fusedLocationClient.requestLocationUpdates(
                locationRequest, // La configuración de la solicitud.
                locationCallback, // El callback que procesará los resultados.
                Looper.getMainLooper() // El hilo en el que se ejecutarán los callbacks.
            )

            // 4. `awaitClose` es un bloque que se ejecuta cuando el flujo es cancelado
            // por el consumidor (por ejemplo, cuando el ViewModel se destruye). Es crucial para
            // limpiar recursos y detener las actualizaciones de ubicación para ahorrar batería.
            awaitClose {
                fusedLocationClient.removeLocationUpdates(locationCallback)
            }
        }
    }
}
