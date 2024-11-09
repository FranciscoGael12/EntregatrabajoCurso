package com.example.basicstatecodelab

// Importación del componente Checkbox de Material Design para representar casillas de verificación.
import androidx.compose.material3.Checkbox
// Importación de Row para organizar elementos de forma horizontal.
import androidx.compose.foundation.layout.Row
// Importación de Icon para mostrar iconos en la interfaz de usuario.
import androidx.compose.material3.Icon
// Importación de IconButton para crear un botón con un icono.
import androidx.compose.material3.IconButton
// Importación de Text para mostrar texto.
import androidx.compose.material3.Text
// Importación del paquete de íconos de Material Design.
import androidx.compose.material.icons.Icons
// Importación de un ícono específico (ícono de cierre).
import androidx.compose.material.icons.filled.Close
// Importación de la anotación Composable para definir funciones de interfaz de usuario.
import androidx.compose.runtime.Composable
// Importación para alinear elementos en el centro vertical de un contenedor.
import androidx.compose.ui.Alignment
// Importación de Modifier para personalizar y modificar el comportamiento de los Composables.
import androidx.compose.ui.Modifier
// Importación de dp, unidad de medida para tamaños y márgenes.
import androidx.compose.ui.unit.dp
// Importación de la función padding para aplicar márgenes a los Composables.
import androidx.compose.foundation.layout.padding
// Importaciones para manejar variables de estado en Compose.
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable

// Función Composable que representa un elemento de tarea de bienestar.
@Composable
fun WellnessTaskItem(
    taskName: String, // Nombre de la tarea a mostrar.
    checked: Boolean, // Estado de la casilla de verificación (marcada/desmarcada).
    onCheckedChange: (Boolean) -> Unit, // Función lambda que se ejecuta cuando cambia el estado de la casilla.
    onClose: () -> Unit, // Función lambda que se ejecuta cuando se presiona el botón de cerrar.
    modifier: Modifier = Modifier // Modificador opcional para personalizar la apariencia del elemento.
) {
    // Layout en fila que organiza los elementos de forma horizontal y alinea su contenido verticalmente al centro.
    Row(
        modifier = modifier, // Aplica el modificador proporcionado.
        verticalAlignment = Alignment.CenterVertically // Alineación vertical al centro.
    ) {
        // Composable de texto que muestra el nombre de la tarea.
        Text(
            modifier = Modifier
                .weight(1f) // Hace que el texto ocupe el espacio restante disponible en la fila.
                .padding(start = 16.dp), // Aplica un margen de 16 dp al inicio del texto.
            text = taskName // Texto que se muestra.
        )
        // Componente de casilla de verificación que muestra el estado actual y permite cambiarlo.
        Checkbox(
            checked = checked, // Estado de la casilla de verificación.
            onCheckedChange = onCheckedChange // Acción a ejecutar cuando se cambia el estado.
        )
        // Botón con ícono que ejecuta la función 'onClose' cuando se presiona.
        IconButton(onClick = onClose) {
            // Ícono de cierre con una descripción de contenido.
            Icon(Icons.Filled.Close, contentDescription = "Close")
        }
    }
}
