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
import androidx.activity.compose.BackHandler // Importa BackHandler para manejar el botón de retroceso personalizado
import androidx.compose.foundation.layout.Box // Importa Box para crear un contenedor flexible en la UI
import androidx.compose.foundation.layout.fillMaxSize // Importa fillMaxSize para configurar el tamaño del contenedor
import androidx.compose.foundation.layout.fillMaxWidth // Importa fillMaxWidth para configurar el ancho completo del contenedor
import androidx.compose.foundation.layout.padding // Importa padding para agregar márgenes en la UI
import androidx.compose.foundation.layout.size // Importa size para configurar el tamaño de los elementos UI
import androidx.compose.foundation.lazy.LazyColumn // Importa LazyColumn para crear listas perezosas en Compose
import androidx.compose.foundation.lazy.LazyListState // Importa LazyListState para manejar el estado de desplazamiento de la lista
import androidx.compose.foundation.lazy.items // Importa items para manejar los elementos en una LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState // Importa rememberLazyListState para recordar el estado de desplazamiento de la lista
import androidx.compose.material.icons.Icons // Importa Icons para usar iconos en Compose
import androidx.compose.material.icons.filled.Edit // Importa el icono de edición
import androidx.compose.material3.Icon // Importa Icon para mostrar un icono en la UI
import androidx.compose.material3.LargeFloatingActionButton // Importa LargeFloatingActionButton para crear un botón flotante
import androidx.compose.material3.MaterialTheme // Importa MaterialTheme para aplicar temas de Material Design
import androidx.compose.runtime.Composable // Importa Composable para definir funciones composables en Compose
import androidx.compose.ui.Alignment // Importa Alignment para alinear elementos en la UI
import androidx.compose.ui.Modifier // Importa Modifier para modificar elementos en Compose
import androidx.compose.ui.res.stringResource // Importa stringResource para obtener cadenas de recursos
import androidx.compose.ui.unit.dp // Importa dp para definir tamaños en densidad independiente de píxeles
import com.example.reply.R // Importa recursos del paquete específico
import com.example.reply.data.Email // Importa el modelo de datos Email
import com.example.reply.ui.components.EmailDetailAppBar // Importa el componente EmailDetailAppBar
import com.example.reply.ui.components.ReplyEmailListItem // Importa el componente ReplyEmailListItem
import com.example.reply.ui.components.ReplyEmailThreadItem // Importa el componente ReplyEmailThreadItem
import com.example.reply.ui.components.ReplySearchBar // Importa el componente ReplySearchBar

@Composable
fun ReplyInboxScreen( // Define la función composable para la pantalla de bandeja de entrada
    replyHomeUIState: ReplyHomeUIState, // Recibe el estado de la UI para controlar qué mostrar
    closeDetailScreen: () -> Unit, // Recibe una función para cerrar la pantalla de detalles
    navigateToDetail: (Long) -> Unit, // Recibe una función para navegar a la pantalla de detalles de un correo específico
    modifier: Modifier = Modifier // Modificador opcional para personalizar la UI
) {

    val emailLazyListState = rememberLazyListState() // Define y recuerda el estado de la lista para mantener la posición de desplazamiento

    Box(modifier = modifier.fillMaxSize()) { // Crea un contenedor que ocupa todo el espacio disponible
        ReplyEmailListContent( // Llama a la función que muestra el contenido de la lista de correos o detalles
            replyHomeUIState = replyHomeUIState, // Pasa el estado de la UI
            emailLazyListState = emailLazyListState, // Pasa el estado de desplazamiento de la lista
            modifier = Modifier.fillMaxSize(), // Define que ocupe todo el espacio disponible
            closeDetailScreen = closeDetailScreen, // Pasa la función para cerrar la pantalla de detalles
            navigateToDetail = navigateToDetail // Pasa la función para navegar a los detalles del correo
        )

        LargeFloatingActionButton( // Crea un botón flotante para agregar o editar un correo
            onClick = { /*Click Implementation*/ }, // Acción al hacer clic (sin implementar)
            modifier = Modifier
                .align(Alignment.BottomEnd) // Alinea el botón en la esquina inferior derecha
                .padding(16.dp), // Agrega margen de 16dp
            containerColor = MaterialTheme.colorScheme.tertiaryContainer, // Color de fondo del botón
            contentColor = MaterialTheme.colorScheme.onTertiaryContainer // Color del contenido del botón
        ) {
            Icon( // Muestra un ícono en el botón flotante
                imageVector = Icons.Default.Edit, // Usa el ícono de edición
                contentDescription = stringResource(id = R.string.edit), // Agrega descripción para accesibilidad
                modifier = Modifier.size(28.dp) // Tamaño del ícono
            )
        }
    }
}

@Composable
fun ReplyEmailListContent( // Función para mostrar lista de correos o detalle de un correo específico
    replyHomeUIState: ReplyHomeUIState, // Recibe el estado de la UI
    emailLazyListState: LazyListState, // Recibe el estado de la lista de correos
    modifier: Modifier = Modifier, // Modificador opcional
    closeDetailScreen: () -> Unit, // Función para cerrar la pantalla de detalles
    navigateToDetail: (Long) -> Unit // Función para navegar a detalles del correo
) {
    if (replyHomeUIState.selectedEmail != null && replyHomeUIState.isDetailOnlyOpen) { // Verifica si se debe mostrar solo el detalle del correo
        BackHandler { // Agrega un controlador para el botón de retroceso
            closeDetailScreen() // Cierra la pantalla de detalles al retroceder
        }
        ReplyEmailDetail(email = replyHomeUIState.selectedEmail) { // Muestra los detalles del correo seleccionado
            closeDetailScreen() // Cierra la pantalla de detalles al retroceder
        }
    } else {
        ReplyEmailList( // Muestra la lista completa de correos
            emails = replyHomeUIState.emails, // Pasa la lista de correos
            emailLazyListState = emailLazyListState, // Pasa el estado de desplazamiento de la lista
            modifier = modifier, // Aplica el modificador
            navigateToDetail = navigateToDetail // Pasa la función para navegar a detalles del correo
        )
    }
}

@Composable
fun ReplyEmailList( // Función para mostrar una lista de correos
    emails: List<Email>, // Lista de correos
    emailLazyListState: LazyListState, // Estado de desplazamiento de la lista
    selectedEmail: Email? = null, // Correo seleccionado opcional
    modifier: Modifier = Modifier, // Modificador opcional
    navigateToDetail: (Long) -> Unit // Función para navegar a detalles del correo
) {
    LazyColumn(modifier = modifier, state = emailLazyListState) { // Define una lista perezosa con desplazamiento recordado
        item {
            ReplySearchBar(modifier = Modifier.fillMaxWidth()) // Agrega la barra de búsqueda en la parte superior
        }
        items(items = emails, key = { it.id }) { email -> // Recorre y muestra cada correo en la lista
            ReplyEmailListItem( // Muestra un elemento individual de correo
                email = email, // Pasa los datos del correo
                isSelected = email.id == selectedEmail?.id // Indica si el correo está seleccionado
            ) { emailId ->
                navigateToDetail(emailId) // Navega a los detalles del correo al hacer clic
            }
        }
    }
}

@Composable
fun ReplyEmailDetail( // Función para mostrar los detalles de un correo específico
    email: Email, // Datos del correo
    isFullScreen: Boolean = true, // Bandera para indicar si debe ocupar toda la pantalla
    modifier: Modifier = Modifier.fillMaxSize(), // Modificador opcional para ajustar el tamaño
    onBackPressed: () -> Unit = {} // Función para manejar el botón de retroceso
) {
    LazyColumn( // Usa una lista perezosa para mostrar los detalles del correo
        modifier = modifier
            .padding(top = 16.dp) // Agrega un margen superior
    ) {
        item {
            EmailDetailAppBar(email, isFullScreen) { // Muestra la barra de detalles con el correo
                onBackPressed() // Llama a la función de retroceso al hacer clic
            }
        }
        items(items = email.threads, key = { it.id }) { email -> // Recorre los hilos del correo y los muestra
            ReplyEmailThreadItem(email = email) // Muestra un elemento individual del hilo
        }
    }
}
