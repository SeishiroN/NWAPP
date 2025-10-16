package cl.duoc.nwapp


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import cl.duoc.nwapp.ui.*
import cl.duoc.nwapp.ui.theme.NWAPPTheme
import cl.duoc.nwapp.viewmodel.FormularioViewModel
import cl.duoc.nwapp.ui.theme.Formulario
import androidx.activity.viewModels



class MainActivity : ComponentActivity() {

    private val viewModelForm = FormularioViewModel()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NWAPPTheme{ // <- aquí va el nombre de TU TEMA, lo puedes encontrar en ui/theme/Theme.kt


                Formulario()
                //BotonCargando()
                //TextoInvertido()
                //Persistencia()
                //UsuarioFormScreen()
                //Modal()
                // Formulario()
                //Login()
                //Navegacion()
                //CamaraFotos()
                //ModalScreen()
                // GPS()
                //AgregarUsuarios()
                // TocarPantalla()
            }
        }
    }



}