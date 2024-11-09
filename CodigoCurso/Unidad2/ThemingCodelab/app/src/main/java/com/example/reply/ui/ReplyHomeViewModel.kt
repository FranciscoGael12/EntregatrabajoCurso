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
import androidx.lifecycle.ViewModel // Base class para ViewModel que maneja datos de UI de forma segura
import androidx.lifecycle.viewModelScope // Scope de coroutines vinculado al ciclo de vida del ViewModel
import com.example.reply.data.Email // Importa la clase de datos Email
import com.example.reply.data.LocalEmailsDataProvider // Proveedor de datos local para obtener la lista de emails
import kotlinx.coroutines.flow.MutableStateFlow // Flow mutable que permite actualizaciones de estado en tiempo real
import kotlinx.coroutines.flow.StateFlow // Flow de solo lectura para exponer el estado a la UI
import kotlinx.coroutines.flow.catch // Manejo de errores para flujos de coroutines
import kotlinx.coroutines.launch // Lanzador de coroutines para operaciones asíncronas

// ViewModel que maneja el estado de la pantalla principal en la app de correos
class ReplyHomeViewModel : ViewModel() {

    // Estado de UI privado que se puede modificar internamente
    private val _uiState = MutableStateFlow(ReplyHomeUIState(loading = true))
    // Estado de UI de solo lectura que se expone a la UI
    val uiState: StateFlow<ReplyHomeUIState> = _uiState

    // Inicialización de ViewModel al cargar datos de correos
    init {
        initEmailList() // Llama al método para cargar la lista inicial de correos
    }

    // Método para cargar la lista de emails y configurar el primer email seleccionado
    private fun initEmailList() {
        val emails = LocalEmailsDataProvider.allEmails // Obtiene la lista de correos del proveedor local
        _uiState.value = ReplyHomeUIState( // Actualiza el estado de la UI con la lista de correos y el primero seleccionado
            emails = emails,
            selectedEmail = emails.first() // Selecciona el primer email de la lista
        )
    }

    // Función para seleccionar un correo específico por ID
    fun setSelectedEmail(emailId: Long) {
        /**
         * Configura `isDetailOnlyOpen` como verdadero solo en el modo de pantalla única
         */
        val email = uiState.value.emails.find { it.id == emailId } // Busca el correo con el ID dado
        _uiState.value = _uiState.value.copy( // Copia el estado actual y actualiza `selectedEmail` y `isDetailOnlyOpen`
            selectedEmail = email,
            isDetailOnlyOpen = true // Activa la vista de detalle en modo solo detalle
        )
    }

    // Función para cerrar la pantalla de detalle y regresar al primer email en la lista
    fun closeDetailScreen() {
        _uiState.value = _uiState // Copia el estado actual y actualiza `isDetailOnlyOpen` y `selectedEmail`
            .value.copy(
                isDetailOnlyOpen = false, // Desactiva el modo de solo detalle
                selectedEmail = _uiState.value.emails.first() // Vuelve a seleccionar el primer correo
            )
    }
}

// Data class que representa el estado de la pantalla principal con correos
data class ReplyHomeUIState(
    val emails: List<Email> = emptyList(), // Lista de correos cargados, vacía por defecto
    val selectedEmail: Email? = null, // Email actualmente seleccionado
    val isDetailOnlyOpen: Boolean = false, // Indica si solo se está mostrando el detalle de un correo
    val loading: Boolean = false, // Indicador de carga
    val error: String? = null // Mensaje de error si ocurre alguno
)
