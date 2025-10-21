package cl.duoc.nwapp.util

import android.annotation.SuppressLint // suprime un permiso diciendo que ya lo comprobó antes en otra parte del código
import android.content.Context // permite a la aplicación a usar recursos del sistema
import android.os.Looper // las respuestas específicas de una api se pasen en un sector determnado
import com.google.android.gms.location.* // importa todas las clases del paquete de uvicación de google play services
import kotlinx.coroutines.channels.awaitClose // un bloque de código se ejecutará cuando el flujo se cancela
import kotlinx.coroutines.flow.Flow //trata a los datos como una corriente de datos (Flow)
import kotlinx.coroutines.flow.callbackFlow // permite crear corrientes de datos (Flow) a partir de sistemas que no los tienen originalmente
import java.util.concurrent.TimeUnit // convertidor de tiempo para mayor legibilidad


class LocationManager(private val context: Context) {
    // la clase necesita del context para poder acceder a las funciones de Android

    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    //esta es la parte fundamental de la api de google para conseguir la locación
    // se utilza para fusionar variadas funcionalidades y poder dar mejor ubicación
    // le tenemos que sumar que se da x contexto del cliente y es solo 1

    //LocationFlow es un flujo de datos tipo LocalResults, lo que pasa es que la solicitud
    //o localRequest al responderse se configura como localResult, de ahi se llama al locationCallback
    //para verificar que efectivamente la respuesta esta completa con el onLocationResult y
    // si es true se emite al flujo con el trySend


    @SuppressLint("MissingPermission") // Se pregunta por permisos en la UI antes para poder ocuparlos acá
    fun locationFlow(): Flow<LocationResult> {
        return callbackFlow {
            // construcción de la solicitud de ubicación
            val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, TimeUnit.SECONDS.toMillis(5))
                .build()

            // 2. se reciben las solicitudes de localRequest y se devuelven al flujo como LocalResult
            //gracias al onLocationResult
            val locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    // Emite la nueva ubicación al flujo
                    trySend(locationResult)
                }
            }

            // 3. orden de como se opera, primero se llama al request, despues al locationcallback
            // para devolver localresults al flujo de datos y termina con el looper que hace
            //que toda operación se haga en el UI
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )

            // 4. esto es para que cuando se cierre la app o no se este utilizando se detenga
            awaitClose {
                fusedLocationClient.removeLocationUpdates(locationCallback)
            }
        }
    }
}
