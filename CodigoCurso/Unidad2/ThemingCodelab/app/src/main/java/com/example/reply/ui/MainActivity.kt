/*
 * Copyright 2022 The Android Open Source Project
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

package com.example.reply.ui
import android.content.res.Configuration
import android.content.res.Configuration.UI_MODE_NIGHT_NO // Constante para el modo claro de la UI
import android.content.res.Configuration.UI_MODE_NIGHT_YES // Constante para el modo oscuro de la UI
import android.os.Bundle
import androidx.activity.ComponentActivity // Clase base para actividades que usan Jetpack Compose
import androidx.activity.compose.setContent // Configura el contenido de Compose en la actividad
import androidx.activity.viewModels // Obtiene una instancia de ViewModel asociada con la actividad
import androidx.compose.material3.Surface // Contenedor de fondo en Compose con color y elevación
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState // Convierte un Flow en un estado que Compose puede observar
import androidx.compose.runtime.getValue // Delegado para obtener valores de estados observables
import androidx.compose.ui.tooling.preview.Preview // Permite crear vistas previas en el editor de diseño
import androidx.compose.ui.unit.dp // Unidades de medida para dimensiones
import com.example.reply.data.LocalEmailsDataProvider // Proveedor de datos local para emails
import com.example.reply.ui.theme.AppTheme // Tema de la aplicación

// MainActivity es la actividad principal que muestra la interfaz de usuario de la aplicación
class MainActivity : ComponentActivity() {

    private val viewModel: ReplyHomeViewModel by viewModels() // Inicializa el ViewModel para gestionar datos de UI

    // Se llama al crear la actividad
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configura la pantalla usando Jetpack Compose
        setContent {
            // Convierte el estado de la UI en una variable observable por Compose
            val uiState by viewModel.uiState.collectAsState()

            // Define el tema general de la aplicación
            AppTheme {
                // Superficie con elevación que aplica un color de fondo a toda la UI
                Surface(tonalElevation = 5.dp) {
                    // Composable principal de la app, recibe el estado de UI y funciones de navegación
                    ReplyApp(
                        replyHomeUIState = uiState,
                        closeDetailScreen = {
                            viewModel.closeDetailScreen() // Cierra la pantalla de detalles en el ViewModel
                        },
                        navigateToDetail = { emailId ->
                            viewModel.setSelectedEmail(emailId) // Navega a la pantalla de detalles y selecciona un email
                        }
                    )
                }
            }
        }
    }
}

// Vista previa en modo oscuro
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark"
)
// Vista previa en modo claro
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "DefaultPreviewLight"
)
@Composable
fun ReplyAppPreview() {
    AppTheme { // Aplica el tema de la app a la vista previa
        ReplyApp(
            replyHomeUIState = ReplyHomeUIState(
                emails = LocalEmailsDataProvider.allEmails // Proporciona una lista de emails de ejemplo
            )
        )
    }
}
