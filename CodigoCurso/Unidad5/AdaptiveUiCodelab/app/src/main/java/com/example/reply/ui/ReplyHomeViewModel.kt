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
import androidx.lifecycle.ViewModel // Importa la clase ViewModel, que es la base para la lógica de negocio y manejo de datos en el ciclo de vida de una pantalla.
import androidx.lifecycle.viewModelScope // Importa la extensión viewModelScope, que facilita la ejecución de corutinas dentro del ViewModel.
import com.example.reply.data.Email // Importa el modelo de datos Email, que representa los correos electrónicos en la aplicación.
import com.example.reply.data.EmailsRepository // Importa la interfaz del repositorio que maneja los datos de los correos electrónicos.
import com.example.reply.data.EmailsRepositoryImpl // Importa la implementación del repositorio de correos electrónicos.
import kotlinx.coroutines.flow.MutableStateFlow // Importa la clase MutableStateFlow, que es una forma de manejar el estado mutable en corutinas.
import kotlinx.coroutines.flow.StateFlow // Importa la clase StateFlow, que es una forma de manejar un estado inmutable en corutinas.
import kotlinx.coroutines.flow.asStateFlow // Importa la función asStateFlow para convertir un MutableStateFlow en un StateFlow inmutable.
import kotlinx.coroutines.flow.catch // Importa la función catch para manejar excepciones en los flujos.
import kotlinx.coroutines.flow.update // Importa la función update para actualizar el estado de un MutableStateFlow.
import kotlinx.coroutines.launch // Importa la función launch para iniciar una corutina.

class ReplyHomeViewModel( // Define la clase ReplyHomeViewModel que extiende de ViewModel y maneja la lógica de la UI y los datos.
    private val emailsRepository: EmailsRepository = EmailsRepositoryImpl() // Inyecta la dependencia del repositorio de correos electrónicos, usando la implementación predeterminada.
): ViewModel() { // Extiende de ViewModel para manejar la lógica de negocio asociada a la pantalla.

    // UI state exposed to the UI // Define el estado de la UI que será expuesto a la UI de la aplicación.
    private val _uiState = MutableStateFlow(ReplyHomeUIState(loading = true)) // Define un MutableStateFlow para manejar el estado mutable de la UI, inicializado con un estado de carga.
    val uiState: StateFlow<ReplyHomeUIState> = _uiState.asStateFlow() // Expone un StateFlow inmutable que se usará en la UI para observar cambios en el estado.

    init { // El bloque init se ejecuta cuando se crea la instancia del ViewModel.
        observeEmails() // Llama a la función para comenzar a observar los correos electrónicos al inicializar el ViewModel.
    }

    private fun observeEmails() { // Define la función para observar los correos electrónicos y actualizar el estado de la UI.
        viewModelScope.launch { // Lanza una corutina dentro del viewModelScope para que se ejecute en el ciclo de vida del ViewModel.
            emailsRepository.getAllEmails() // Obtiene todos los correos electrónicos desde el repositorio.
                .catch { ex -> // Maneja cualquier excepción que ocurra durante la recopilación de correos electrónicos.
                    _uiState.value = ReplyHomeUIState(error = ex.message) // Si ocurre un error, actualiza el estado de la UI con el mensaje de error.
                }
                .collect { emails -> // Recoge los correos electrónicos emitidos por el flujo.
                    // If nothing is selected, initially select the first element. // Si no se ha seleccionado un correo, selecciona el primero por defecto.
                    val currentSelected = _uiState.value.selectedEmail // Obtiene el correo seleccionado actualmente.
                    _uiState.value = ReplyHomeUIState( // Actualiza el estado de la UI con los correos electrónicos obtenidos y el correo seleccionado.
                        emails = emails,
                        selectedEmail = currentSelected ?: emails.firstOrNull() // Si no hay correo seleccionado, elige el primero de la lista.
                    )
                }
        }
    }

    fun setSelectedEmail(email: Email) { // Define una función para actualizar el correo seleccionado.
        _uiState.update { // Actualiza el estado de la UI.
            it.copy(selectedEmail = email) // Crea una nueva copia del estado, reemplazando el correo seleccionado.
        }
    }
}

data class ReplyHomeUIState( // Define un data class para el estado de la UI, que contiene los datos que la interfaz necesita mostrar.
    val emails : List<Email> = emptyList(), // Lista de correos electrónicos, inicialmente vacía.
    val selectedEmail: Email? = null, // Correo electrónico seleccionado, inicialmente nulo.
    val loading: Boolean = false, // Indicador de carga, inicialmente falso.
    val error: String? = null // Mensaje de error, inicialmente nulo.
)
