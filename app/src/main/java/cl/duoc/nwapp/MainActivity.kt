// Archivo: MainActivity.kt
package cl.duoc.nwapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import cl.duoc.nwapp.ui.theme.NWAPPTheme
import cl.duoc.nwapp.ui.theme.Navegacion

/**
 * `MainActivity` es la actividad principal y el punto de entrada de la aplicación.
 * Hereda de `ComponentActivity`, que es la clase base para actividades que usan Jetpack Compose.
 */
class MainActivity : ComponentActivity() {
    /**
     * `onCreate` es un método del ciclo de vida de la actividad. Se llama cuando la actividad
     * se crea por primera vez. Es el lugar ideal para realizar inicializaciones.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        // `super.onCreate` llama a la implementación del método de la clase padre.
        super.onCreate(savedInstanceState)

        // `setContent` establece el contenido de la actividad. En lugar de un archivo XML,
        // se le pasa un bloque de código Composable, que define la UI de la aplicación.
        setContent {
            // `NWAPPTheme` es un Composable que define el tema de la aplicación
            // (colores, tipografía, formas). Envuelve toda la UI para aplicar un estilo consistente.
            NWAPPTheme {
                // `Navegacion` es el Composable que configura el grafo de navegación de la app,
                // gestionando qué pantalla se muestra en cada momento.
                Navegacion()
            }
        }
    }
}
