/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package androidx.compose.samples.crane.home
import android.os.Bundle // Importa la clase Bundle, utilizada para pasar datos entre componentes de la app
import androidx.activity.ComponentActivity // Importa la clase base para actividades que usan Jetpack Compose
import androidx.activity.compose.setContent // Permite configurar el contenido de la actividad usando composables de Compose
import androidx.compose.material.MaterialTheme // Importa el tema de Material Design para la UI
import androidx.compose.material.Surface // Un contenedor de diseño que aplica un color de fondo y un tema a los elementos dentro
import androidx.compose.runtime.Composable // Importa la anotación Composable, utilizada para definir funciones que representan UI en Compose
import androidx.compose.runtime.getValue // Permite obtener el valor de un estado en Compose
import androidx.compose.runtime.mutableStateOf // Crea un estado mutable en Compose
import androidx.compose.runtime.remember // Recuerda el valor de un estado entre recomposiciones
import androidx.compose.runtime.setValue // Permite actualizar el valor de un estado mutable
import androidx.compose.samples.crane.details.launchDetailsActivity // Importa la función que lanza una nueva actividad para mostrar los detalles de un elemento
import androidx.compose.samples.crane.ui.CraneTheme // Importa el tema personalizado de la aplicación
import androidx.core.view.WindowCompat // Importa la clase para configurar el comportamiento de la ventana en la actividad
import dagger.hilt.android.AndroidEntryPoint // Anotación de Hilt para inyección de dependencias en la actividad

@AndroidEntryPoint // Anotación para habilitar la inyección de dependencias en la actividad
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) { // Método llamado al crear la actividad
        super.onCreate(savedInstanceState) // Llama al método de la clase base

        WindowCompat.setDecorFitsSystemWindows(window, false) // Configura el comportamiento de la ventana para no ajustar el contenido con el sistema

        setContent { // Define el contenido de la actividad usando Jetpack Compose
            CraneTheme { // Aplica el tema Crane a los composables dentro de este bloque
                MainScreen(onExploreItemClicked = { // Llama a la pantalla principal, pasando la función que maneja la acción de explorar un ítem
                    launchDetailsActivity( // Lanza una nueva actividad para mostrar los detalles del ítem
                        context = this, // Pasa el contexto de la actividad actual
                        item = it // Pasa el ítem seleccionado
                    )
                })
            }
        }
    }
}

@Composable // Define una función composable que representa la UI
private fun MainScreen(onExploreItemClicked: OnExploreItemClicked) { // Recibe una función como parámetro para manejar el evento de explorar
    Surface(color = MaterialTheme.colors.primary) { // Aplica un fondo de color primario del tema de Material
        var showLandingScreen by remember { // Crea un estado mutable para controlar si se muestra la pantalla de inicio o no
            mutableStateOf(true)
        }
        if (showLandingScreen) { // Si el estado showLandingScreen es verdadero, muestra la pantalla de inicio
            LandingScreen(onTimeout = { showLandingScreen = false }) // Llama a la pantalla de inicio y cambia el estado cuando termine el tiempo
        } else { // Si showLandingScreen es falso, muestra la pantalla principal de Crane
            CraneHome(onExploreItemClicked = onExploreItemClicked) // Muestra la pantalla de inicio de Crane y pasa la función de exploración
        }
    }
}
