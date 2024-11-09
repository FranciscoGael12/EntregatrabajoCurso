package com.example.basicstatecodelab
// Importación de la clase Bundle, necesaria para manejar los datos de estado de la actividad.
import android.os.Bundle
// Importación de ComponentActivity, base para actividades en Jetpack Compose.
import androidx.activity.ComponentActivity
// Importación de la función setContent, que permite definir el contenido de la actividad usando Composables.
import androidx.activity.compose.setContent
// Importación de la función enableEdgeToEdge para habilitar una visualización completa en la pantalla.
import androidx.activity.enableEdgeToEdge
// Importación de modificadores de Composable para personalizar cómo se presenta un componente.
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
// Importación de Scaffold, un contenedor que permite crear diseños con estructuras de Material Design.
import androidx.compose.material3.Scaffold
// Importación del componente Text para mostrar texto en la pantalla.
import androidx.compose.material3.Text
// Importación de la anotación Composable, que permite definir funciones de interfaz de usuario en Compose.
import androidx.compose.runtime.Composable
// Importación de Modifier, que permite modificar el comportamiento y la apariencia de un Composable.
import androidx.compose.ui.Modifier
// Importación para la herramienta de vista previa de Compose.
import androidx.compose.ui.tooling.preview.Preview
// Importación del tema personalizado.
import com.example.basicstatecodelab.ui.theme.BasicStateCodelabTheme
// Importación de MaterialTheme y Surface para usar componentes de Material Design.
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
// Importación de dp, unidad de medida para definir tamaños en la interfaz.
import androidx.compose.ui.unit.dp

// Definición de la clase MainActivity, que extiende ComponentActivity y actúa como punto de entrada principal de la app.
class MainActivity : ComponentActivity() {
    // Función onCreate que se ejecuta cuando se crea la actividad.
    override fun onCreate(savedInstanceState: Bundle?) {
        // Llamada al método onCreate de la clase padre.
        super.onCreate(savedInstanceState)
        // Establece el contenido de la actividad utilizando composables.
        setContent {
            // Se aplica el tema personalizado a la interfaz.
            BasicStateCodelabTheme {
                // Contenedor Surface que usa el color de fondo definido en el tema.
                Surface(
                    modifier = Modifier.fillMaxSize(), // Modificador para que el contenedor llene toda la pantalla.
                    color = MaterialTheme.colorScheme.background // Color de fondo según el esquema de colores del tema.
                ) {
                    // Llamada a un Composable que representa la pantalla de bienestar.
                    WellnessScreen()
                }
            }
        }
    }
}

// Función para vista previa en el editor de Compose.
@Preview(showBackground = true) // Anotación para mostrar el fondo durante la vista previa.
@Composable // Define una función Composable que puede ser usada para construir la interfaz.
fun GreetingPreview() {
    // Se aplica el tema personalizado a la vista previa.
    BasicStateCodelabTheme {
        // Llamada al Composable WaterCounter, que probablemente muestra un contador de agua en la pantalla.
        WaterCounter()
    }
}
