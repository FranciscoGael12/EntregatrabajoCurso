package com.example.basicstatecodelab

// Importación de la anotación Composable para definir funciones que representan elementos de la interfaz de usuario en Jetpack Compose.
import androidx.compose.runtime.Composable
// Importación de Modifier, que permite modificar la apariencia o el comportamiento de un Composable.
import androidx.compose.ui.Modifier
// Importación del layout Column para organizar elementos de forma vertical.
import androidx.compose.foundation.layout.Column
// Importación de la función viewModel para obtener una instancia de ViewModel en Compose.
import androidx.lifecycle.viewmodel.compose.viewModel

// Función Composable que representa la pantalla de bienestar.
@Composable
fun WellnessScreen(
    modifier: Modifier = Modifier, // Modificador opcional para personalizar la apariencia de la columna.
    wellnessViewModel: WellnessViewModel = viewModel() // Inicializa o recupera una instancia de WellnessViewModel.
) {
    // Layout en columna que contiene los elementos de la pantalla.
    Column(modifier = modifier) {
        // Llamada al Composable StatefulCounter, que se encarga de mostrar y manejar un contador con estado.
        StatefulCounter()

        // Llamada al Composable WellnessTasksList, que muestra una lista de tareas de bienestar.
        WellnessTasksList(
            list = wellnessViewModel.tasks, // Lista de tareas obtenida del ViewModel.
            // Acción a ejecutar cuando se cambia el estado de una tarea (marcar/desmarcar).
            onCheckedTask = { task, checked ->
                wellnessViewModel.changeTaskChecked(task, checked) // Llama al método del ViewModel para actualizar el estado de la tarea.
            },
            // Acción a ejecutar cuando se cierra/elimina una tarea de la lista.
            onCloseTask = { task ->
                wellnessViewModel.remove(task) // Llama al método del ViewModel para eliminar la tarea de la lista.
            }
        )
    }
}

