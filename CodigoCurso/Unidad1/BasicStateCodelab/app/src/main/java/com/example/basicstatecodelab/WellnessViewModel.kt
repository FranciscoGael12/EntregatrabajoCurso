package com.example.basicstatecodelab

// Importación de la extensión 'toMutableStateList' que convierte una lista en una lista mutable con soporte para Compose.
import androidx.compose.runtime.toMutableStateList
// Importación de ViewModel, que es una clase base para los modelos de vista en arquitectura MVVM.
import androidx.lifecycle.ViewModel

// Definición del ViewModel 'WellnessViewModel', que gestiona el estado de las tareas de bienestar.
class WellnessViewModel : ViewModel() {
    // Creación de una lista mutable de tareas de bienestar usando una función que simula obtener las tareas.
    private val _tasks = getWellnessTasks().toMutableStateList()
    // Exposición pública de la lista de tareas (solo lectura).
    val tasks: List<WellnessTask>
        get() = _tasks // Retorna la lista de tareas de bienestar.

    // Función que elimina una tarea de la lista de tareas.
    fun remove(item: WellnessTask) {
        _tasks.remove(item) // Elimina la tarea especificada de la lista.
    }

    // Función que cambia el estado de marcado (checked) de una tarea.
    fun changeTaskChecked(item: WellnessTask, checked: Boolean) =
        // Busca la tarea con el mismo id que el ítem y actualiza su estado de 'checked'.
        _tasks.find { it.id == item.id }?.let { task ->
            task.checked = checked // Modifica el estado de la tarea (marcada o desmarcada).
        }
}

// Función que simula obtener una lista de tareas de bienestar.
private fun getWellnessTasks() = List(30) { i -> WellnessTask(i, "Task # $i") }
// Genera una lista de 30 tareas, cada una con un id único y un nombre de tarea como "Task # <i>".
