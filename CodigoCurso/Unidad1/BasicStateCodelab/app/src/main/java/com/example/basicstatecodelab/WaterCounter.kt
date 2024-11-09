package com.example.basicstatecodelab
// Importación para suprimir advertencias específicas del compilador.
import android.annotation.SuppressLint
// Importación de Modifier y componentes de diseño de Jetpack Compose.
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Button
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.saveable.rememberSaveable

// Composable que representa un contador de vasos de agua con estado propio.
@Composable
fun WaterCounter(modifier: Modifier = Modifier) {
    // Layout en columna que organiza sus elementos verticalmente con un margen de 16 dp.
    Column(modifier = modifier.padding(16.dp)) {
        // Variable de estado 'count' que se recuerda mientras la función está en ejecución.
        var count by remember { mutableStateOf(0) }

        // Si 'count' es mayor que 0, muestra un texto indicando cuántos vasos se han tomado.
        if (count > 0) {
            Text("You've had $count glasses.")
        }

        // Botón que incrementa la variable 'count' en 1 cuando se presiona.
        Button(
            onClick = { count++ }, // Acción que se ejecuta al hacer clic.
            Modifier.padding(top = 8.dp), // Margen superior de 8 dp para separación visual.
            enabled = count < 10 // El botón se desactiva si 'count' es 10 o más.
        ) {
            // Texto dentro del botón.
            Text("Add one")
        }
    }
}

// Composable que representa un contador sin estado (la lógica de estado se pasa como parámetros).
@Composable
fun StatelessCounter(count: Int, onIncrement: () -> Unit, modifier: Modifier = Modifier) {
    // Layout en columna con un margen de 16 dp.
    Column(modifier = modifier.padding(16.dp)) {
        // Muestra el texto si 'count' es mayor que 0.
        if (count > 0) {
            Text("You've had $count glasses.")
        }

        // Botón que llama a la función 'onIncrement' pasada como parámetro.
        Button(
            onClick = onIncrement, // Acción recibida como parámetro.
            Modifier.padding(top = 8.dp), // Margen superior de 8 dp.
            enabled = count < 10 // El botón se desactiva si 'count' es 10 o más.
        ) {
            // Texto dentro del botón.
            Text("Add one")
        }
    }
}

// Composable con estado que usa 'StatelessCounter' y maneja su propio estado.
@Composable
fun StatefulCounter(modifier: Modifier = Modifier) {
    // Variable de estado 'count' que se recuerda y se guarda durante la navegación o recreación.
    var count by rememberSaveable { mutableStateOf(0) }

    // Llamada al Composable 'StatelessCounter' con el estado y la función de incremento.
    StatelessCounter(count, { count++ }, modifier)
}
