package com.example.basicstatecodelab

// Importación de la función 'getValue' que permite obtener el valor de una propiedad delegada en Compose.
import androidx.compose.runtime.getValue
// Importación de 'mutableStateOf' que se usa para crear variables de estado que se pueden observar en Composables.
import androidx.compose.runtime.mutableStateOf
// Importación de la función 'setValue' que permite asignar un valor a una propiedad delegada en Compose.
import androidx.compose.runtime.setValue

// Clase que representa una tarea de bienestar.
class WellnessTask(
    val id: Int, // Identificador único de la tarea.
    val label: String, // Descripción o nombre de la tarea.
    initialChecked: Boolean = false // Estado inicial de la tarea (marcada/desmarcada), con un valor predeterminado de 'false'.
) {
    // Propiedad 'checked' que representa si la tarea está marcada o no.
    // Esta propiedad es observable y su cambio provoca la recomposición de los Composables que la usen.
    var checked by mutableStateOf(initialChecked)
}
