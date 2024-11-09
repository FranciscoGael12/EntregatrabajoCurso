/*
 * Copyright 2022 Google LLC
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
import android.os.Bundle // Importa la clase Bundle que permite pasar información entre actividades
import androidx.activity.ComponentActivity // Importa la clase ComponentActivity que es la base para actividades en Jetpack Compose
import androidx.activity.compose.setContent // Importa la función setContent para establecer la interfaz de usuario utilizando Compose
import androidx.activity.viewModels // Importa la función viewModels para obtener una instancia del ViewModel asociada a la actividad
import androidx.compose.runtime.Composable // Importa la anotación Composable para marcar funciones que componen la UI
import androidx.compose.runtime.getValue // Importa la función getValue para leer valores de propiedades observables
import androidx.compose.ui.tooling.preview.Preview // Importa la anotación Preview para previsualizar componentes en Android Studio
import androidx.lifecycle.compose.collectAsStateWithLifecycle // Importa la extensión collectAsStateWithLifecycle para recopilar el estado del ViewModel en Compose
import com.example.reply.data.local.LocalEmailsDataProvider // Importa la fuente local de datos de correos electrónicos
import com.example.reply.ui.theme.ReplyTheme // Importa el tema visual de la aplicación para la interfaz

class MainActivity : ComponentActivity() { // Define la actividad principal de la aplicación

    private val viewModel: ReplyHomeViewModel by viewModels() // Inicializa el ViewModel asociado a esta actividad, usado para gestionar el estado de la UI

    override fun onCreate(savedInstanceState: Bundle?) { // Método llamado cuando la actividad se crea
        super.onCreate(savedInstanceState)

        setContent { // Establece el contenido de la actividad utilizando Jetpack Compose
            ReplyTheme { // Aplica el tema de la aplicación
                val uiState by viewModel.uiState.collectAsStateWithLifecycle() // Observa el estado del ViewModel y lo recopila, asegurando que solo se actualice cuando la actividad esté en un estado activo
                ReplyApp( // Llama al componente principal de la UI
                    replyHomeUIState = uiState, // Pasa el estado de la UI gestionado por el ViewModel
                    onEmailClick = viewModel::setSelectedEmail // Define qué acción realizar cuando se hace clic en un correo electrónico (cambiar el correo seleccionado)
                )
            }
        }
    }
}

@Preview(showBackground = true) // Define una previsualización de la interfaz en Android Studio con un fondo visible
@Composable
fun ReplyAppPreview() { // Componente para previsualizar la aplicación en un entorno de desarrollo
    ReplyTheme { // Aplica el tema visual
        ReplyApp( // Llama al componente principal de la UI con un conjunto de correos electrónicos locales para la vista previa
            replyHomeUIState = ReplyHomeUIState(
                emails = LocalEmailsDataProvider.allEmails // Proporciona los correos electrónicos locales para la vista previa
            ),
            onEmailClick = {} // Acción vacía para la vista previa
        )
    }
}

@Preview(showBackground = true, widthDp = 700) // Previsualización con un ancho de 700dp, para dispositivos como tabletas
@Composable
fun ReplyAppPreviewTablet() { // Componente para previsualizar la aplicación en una pantalla de tablet
    ReplyTheme { // Aplica el tema visual
        ReplyApp( // Llama al componente principal de la UI con un conjunto de correos electrónicos locales para la vista previa
            replyHomeUIState = ReplyHomeUIState(
                emails = LocalEmailsDataProvider.allEmails // Proporciona los correos electrónicos locales para la vista previa
            ),
            onEmailClick = {} // Acción vacía para la vista previa
        )
    }
}

@Preview(showBackground = true, widthDp = 1000) // Previsualización con un ancho de 1000dp, para dispositivos de escritorio
@Composable
fun ReplyAppPreviewDesktop() { // Componente para previsualizar la aplicación en una pantalla de escritorio
    ReplyTheme { // Aplica el tema visual
        ReplyApp( // Llama al componente principal de la UI con un conjunto de correos electrónicos locales para la vista previa
            replyHomeUIState = ReplyHomeUIState(
                emails = LocalEmailsDataProvider.allEmails // Proporciona los correos electrónicos locales para la vista previa
            ),
            onEmailClick = {} // Acción vacía para la vista previa
        )
    }
}
