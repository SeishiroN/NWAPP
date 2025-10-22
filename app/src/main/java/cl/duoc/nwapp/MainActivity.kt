// Archivo: MainActivity.kt
package cl.duoc.nwapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import cl.duoc.nwapp.ui.theme.NWAPPTheme
import cl.duoc.nwapp.ui.theme.Navegacion


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)


        setContent {

            NWAPPTheme {
                Navegacion()
            }
        }
    }
}
