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
import androidx.activity.compose.BackHandler // Importa la función BackHandler para manejar la acción de retroceso del dispositivo en Compose
import androidx.compose.material3.Icon // Importa la función Icon para mostrar iconos en la interfaz
import androidx.compose.material3.Text // Importa la función Text para mostrar texto en la interfaz
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi // Importa una anotación para usar características experimentales de adaptabilidad de Material 3
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo // Importa la función que obtiene información sobre la adaptabilidad de la ventana
import androidx.compose.material3.adaptive.currentWindowSize // Importa la función que obtiene el tamaño actual de la ventana
import androidx.compose.material3.adaptive.layout.AnimatedPane // Importa la función AnimatedPane para crear transiciones animadas entre elementos
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold // Importa el componente que estructura el diseño de lista y detalle adaptativo
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole // Importa las funciones relacionadas con los roles de los paneles en ListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator // Importa la función para recordar y manejar la navegación dentro de ListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold // Importa el componente que estructura un marco de navegación adaptativo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults // Importa las configuraciones predeterminadas para el scaffold de navegación
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType // Importa las configuraciones de tipo de navegación para adaptarse a diferentes tamaños de ventana
import androidx.compose.runtime.Composable // Importa la anotación Composable para marcar funciones que definen la UI
import androidx.compose.runtime.getValue // Importa la función getValue para leer propiedades observables
import androidx.compose.runtime.mutableStateOf // Importa la función mutableStateOf para crear un estado mutable en Compose
import androidx.compose.runtime.remember // Importa la función remember para recordar valores entre recomposiciones
import androidx.compose.runtime.setValue // Importa la función setValue para actualizar el valor de un estado mutable
import androidx.compose.ui.platform.LocalDensity // Importa LocalDensity para obtener la densidad de la pantalla
import androidx.compose.ui.res.stringResource // Importa la función stringResource para acceder a los recursos de cadena
import androidx.compose.ui.unit.dp // Importa dp para trabajar con unidades de medida en pantalla
import androidx.compose.ui.unit.toSize // Importa la función toSize para convertir unidades a tamaño
import com.example.reply.data.Email // Importa el modelo de datos Email

private val WINDOW_WIDTH_LARGE = 1200.dp // Define un valor umbral para el ancho de ventana considerado grande

@Composable
fun ReplyApp( // Componente principal que encapsula la UI de la aplicación
    replyHomeUIState: ReplyHomeUIState, // Recibe el estado de la UI de la pantalla principal
    onEmailClick: (Email) -> Unit, // Recibe una función que maneja el clic en un correo electrónico
) {
    ReplyNavigationWrapperUI { // Llama a un componente de contenedor para la navegación
        ReplyAppContent( // Llama al contenido de la aplicación pasando el estado y el manejador de clics
            replyHomeUIState = replyHomeUIState,
            onEmailClick = onEmailClick
        )
    }
}

@Composable
private fun ReplyNavigationWrapperUI( // Función para envolver el contenido con un componente de navegación
    content: @Composable () -> Unit = {} // Función composable que representa el contenido que se envuelve
) {
    var selectedDestination: ReplyDestination by remember { // Define el destino seleccionado en la navegación
        mutableStateOf(ReplyDestination.Inbox) // Inicializa con el destino de la bandeja de entrada
    }

    val windowSize = with(LocalDensity.current) { // Obtiene el tamaño actual de la ventana adaptado a la densidad de la pantalla
        currentWindowSize().toSize().toDpSize() // Convierte el tamaño de la ventana a unidades dp
    }
    val navLayoutType = if (windowSize.width >= WINDOW_WIDTH_LARGE) { // Determina el tipo de navegación según el tamaño de la ventana
        NavigationSuiteType.NavigationDrawer // Usa un panel de navegación (drawer) cuando el ancho de la ventana es grande
    } else {
        NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(currentWindowAdaptiveInfo()) // Usa la configuración predeterminada cuando el ancho es pequeño
    }

    NavigationSuiteScaffold( // Componente de Scaffold que gestiona la navegación y la UI adaptativa
        navigationSuiteItems = { // Define los elementos de la suite de navegación
            ReplyDestination.entries.forEach { // Recorre todos los destinos de navegación
                item( // Define un ítem de navegación
                    label = { Text(stringResource(it.labelRes)) }, // Muestra el texto del destino usando un recurso de cadena
                    icon = { Icon(it.icon, stringResource(it.labelRes)) }, // Muestra el ícono del destino
                    selected = it == selectedDestination, // Marca como seleccionado el destino actual
                    onClick = { /*TODO update selection*/ }, // Acción para manejar el clic (pendiente de implementación)
                )
            }
        },
        layoutType = navLayoutType // Define el tipo de diseño de navegación basado en el tamaño de la ventana
    ) {
        content() // Llama al contenido que se pasa como parámetro
    }
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class) // Habilita el uso de APIs experimentales relacionadas con la adaptabilidad
@Composable
fun ReplyAppContent( // Componente que contiene la UI de la aplicación, con la lógica de navegación y la visualización de correos electrónicos
    replyHomeUIState: ReplyHomeUIState, // Recibe el estado de la UI para la pantalla principal
    onEmailClick: (Email) -> Unit, // Recibe la función que maneja el clic en un correo electrónico
) {
    val selectedEmail = replyHomeUIState.selectedEmail // Obtiene el correo electrónico seleccionado del estado
    val navigator = rememberListDetailPaneScaffoldNavigator<Long>() // Crea un navegador para la estructura de lista y detalle

    BackHandler(navigator.canNavigateBack()) { // Maneja el comportamiento de retroceso cuando se puede navegar hacia atrás
        navigator.navigateBack() // Realiza la navegación hacia atrás
    }

    ListDetailPaneScaffold( // Componente que maneja la estructura de lista y detalle en la UI
        directive = navigator.scaffoldDirective, // Define las directivas para la estructura de la interfaz
        value = navigator.scaffoldValue, // Proporciona el valor de la estructura del scaffold
        listPane = { // Define el panel de lista
            AnimatedPane { // Aplica una animación a la transición del panel de lista
                ReplyListPane( // Muestra la lista de correos electrónicos
                    replyHomeUIState = replyHomeUIState,
                    onEmailClick = { email -> // Define la acción cuando se hace clic en un correo
                        onEmailClick(email) // Llama a la función que maneja el clic
                        navigator.navigateTo(ListDetailPaneScaffoldRole.Detail, email.id) // Navega al detalle del correo seleccionado
                    }
                )
            }
        },
        detailPane = { // Define el panel de detalle
            AnimatedPane { // Aplica una animación a la transición del panel de detalle
                if (selectedEmail != null) { // Si hay un correo seleccionado, muestra su detalle
                    ReplyDetailPane(selectedEmail) // Muestra el detalle del correo
                }
            }
        }
    )
}
