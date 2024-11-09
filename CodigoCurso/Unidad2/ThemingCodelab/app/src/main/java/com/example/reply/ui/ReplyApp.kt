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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize // Hace que el componente ocupe todo el tamaño disponible en pantalla
import androidx.compose.foundation.layout.fillMaxWidth // Hace que el componente ocupe el ancho total del contenedor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Article // Icono para la sección de Artículos
import androidx.compose.material.icons.filled.Inbox // Icono para la sección de Bandeja de entrada
import androidx.compose.material.icons.filled.People // Icono para la sección de Grupos
import androidx.compose.material.icons.outlined.ChatBubbleOutline // Icono para la sección de Mensajes directos
import androidx.compose.material3.Icon // Composable para mostrar iconos en Material Design 3
import androidx.compose.material3.NavigationBar // Barra de navegación en la parte inferior de la pantalla
import androidx.compose.material3.NavigationBarItem // Elemento de la barra de navegación inferior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf // Estado mutable para cambios dinámicos en Compose
import androidx.compose.runtime.remember // Recordatorio de estado para que persista mientras la composición viva
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector // Tipo de datos para representar iconos vectoriales
import androidx.compose.ui.res.stringResource // Función para obtener recursos de texto por id
import com.example.reply.R

// Función principal que configura la UI de la aplicación Reply
@Composable
fun ReplyApp(
    replyHomeUIState: ReplyHomeUIState, // Estado de la UI que representa los datos de la app
    closeDetailScreen: () -> Unit = {}, // Función de cierre de pantalla de detalles
    navigateToDetail: (Long) -> Unit = {} // Función de navegación a detalles
) {
    // Llama a la función que contiene el contenido principal de la app
    ReplyAppContent(
        replyHomeUIState = replyHomeUIState,
        closeDetailScreen = closeDetailScreen,
        navigateToDetail = navigateToDetail
    )
}

// Función que representa el contenido de la app
@Composable
fun ReplyAppContent(
    modifier: Modifier = Modifier, // Modificador para ajustes de diseño
    replyHomeUIState: ReplyHomeUIState, // Estado de la UI con la información de los emails
    closeDetailScreen: () -> Unit, // Función para cerrar la pantalla de detalles
    navigateToDetail: (Long) -> Unit // Función para navegar a la pantalla de detalles de un email
) {

    // Variable de estado para la pantalla seleccionada actualmente, inicializada en INBOX
    val selectedDestination = remember { mutableStateOf(ReplyRoute.INBOX) }

    // Layout de la columna principal
    Column(
        modifier = modifier
            .fillMaxSize() // Ocupa todo el tamaño vertical
    ) {

        // Muestra la pantalla de Bandeja de entrada o una pantalla vacía dependiendo de la selección
        if (selectedDestination.value == ReplyRoute.INBOX) {
            ReplyInboxScreen(
                replyHomeUIState = replyHomeUIState, // Pasa el estado de la bandeja de entrada
                closeDetailScreen = closeDetailScreen, // Pasa la función para cerrar detalles
                navigateToDetail = navigateToDetail, // Pasa la función para navegar a detalles
                modifier = Modifier.weight(1f) // Ocupa espacio restante en la columna
            )
        } else {
            EmptyComingSoon(modifier = Modifier.weight(1f)) // Pantalla de "Próximamente" para otras secciones
        }

        // Barra de navegación inferior
        NavigationBar(modifier = Modifier.fillMaxWidth()) {
            // Itera sobre cada destino y crea un item en la barra de navegación
            TOP_LEVEL_DESTINATIONS.forEach { replyDestination ->
                NavigationBarItem(
                    selected = selectedDestination.value == replyDestination.route, // Verifica si el item está seleccionado
                    onClick = { selectedDestination.value = replyDestination.route }, // Cambia el destino seleccionado al hacer clic
                    icon = {
                        Icon(
                            imageVector = replyDestination.selectedIcon, // Icono a mostrar
                            contentDescription = stringResource(id = replyDestination.iconTextId) // Descripción accesible del icono
                        )
                    }
                )
            }
        }
    }
}

// Objeto que define las rutas de navegación en la app
object ReplyRoute {
    const val INBOX = "Inbox" // Ruta de la bandeja de entrada
    const val ARTICLES = "Articles" // Ruta de artículos
    const val DM = "DirectMessages" // Ruta de mensajes directos
    const val GROUPS = "Groups" // Ruta de grupos
}

// Clase de datos que representa cada destino en la barra de navegación
data class ReplyTopLevelDestination(
    val route: String, // Nombre de la ruta
    val selectedIcon: ImageVector, // Icono para cuando está seleccionado
    val unselectedIcon: ImageVector, // Icono para cuando no está seleccionado
    val iconTextId: Int // ID de recurso de texto para la descripción del icono
)

// Lista de destinos principales en la app, cada uno tiene su icono y descripción
val TOP_LEVEL_DESTINATIONS = listOf(
    ReplyTopLevelDestination(
        route = ReplyRoute.INBOX, // Ruta de bandeja de entrada
        selectedIcon = Icons.Default.Inbox, // Icono seleccionado para Inbox
        unselectedIcon = Icons.Default.Inbox, // Icono no seleccionado para Inbox
        iconTextId = R.string.tab_inbox // ID del texto de descripción
    ),
    ReplyTopLevelDestination(
        route = ReplyRoute.ARTICLES, // Ruta de artículos
        selectedIcon = Icons.Default.Article, // Icono seleccionado para Artículos
        unselectedIcon = Icons.Default.Article, // Icono no seleccionado para Artículos
        iconTextId = R.string.tab_article // ID del texto de descripción
    ),
    ReplyTopLevelDestination(
        route = ReplyRoute.DM, // Ruta de mensajes directos
        selectedIcon = Icons.Outlined.ChatBubbleOutline, // Icono seleccionado para DM
        unselectedIcon = Icons.Outlined.ChatBubbleOutline, // Icono no seleccionado para DM
        iconTextId = R.string.tab_dm // ID del texto de descripción
    ),
    ReplyTopLevelDestination(
        route = ReplyRoute.GROUPS, // Ruta de grupos
        selectedIcon = Icons.Default.People, // Icono seleccionado para Grupos
        unselectedIcon = Icons.Default.People, // Icono no seleccionado para Grupos
        iconTextId = R.string.tab_groups // ID del texto de descripción
    )
)
