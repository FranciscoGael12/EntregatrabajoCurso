package com.example.basicstatecodelab
// Importación de LazyColumn que permite mostrar una lista desplazable de elementos.
import androidx.compose.foundation.lazy.LazyColumn
// Importación de items para mostrar los elementos dentro de una LazyColumn.
import androidx.compose.foundation.lazy.items
// Importación de la anotación Composable para definir funciones de interfaz de usuario.
import androidx.compose.runtime.Composable
// Importación de Modifier que permite modificar la apariencia o el comportamiento de los Composables.
import androidx.compose.ui.Modifier

// Función Composable que representa una lista de tareas de bienestar.
@Composable
fun WellnessTasksList(
    list: List<WellnessTask>, // Lista de objetos WellnessTask que se mostrarán en la lista.
    onCheckedTask: (WellnessTask, Boolean) -> Unit, // Función que se ejecuta cuando se cambia el estado de una tarea (marcar/desmarcar).
    onCloseTask: (WellnessTask) -> Unit, // Función que se ejecuta cuando se cierra o elimina una tarea.
    modifier: Modifier = Modifier // Modificador opcional para personalizar el estilo de la lista.
) {
    // LazyColumn permite crear una lista desplazable de elementos.
    LazyColumn(
        modifier = modifier // Aplica el modificador opcional para personalizar la apariencia de la lista.
    ) {
        // La función 'items' se usa para crear elementos dentro de la lista a partir de la lista proporcionada.
        items(
            items = list, // Lista de tareas a mostrar.
            key = { task -> task.id } // Clave única para cada tarea basada en su id, utilizada para optimizar el rendimiento.
        ) { task ->
            // Para cada elemento de la lista, se llama a 'WellnessTaskItem' para mostrar cada tarea con sus detalles.
            WellnessTaskItem(
                taskName = task.label, // Nombre de la tarea.
                checked = task.checked, // Estado de la casilla de verificación (si está marcada o no).
                // Función que se ejecuta cuando el estado de la casilla cambia.
                onCheckedChange = { checked -> onCheckedTask(task, checked) },
                // Función que se ejecuta cuando se cierra o elimina la tarea.
                onClose = { onCloseTask(task) }
            )
        }
    }
}
