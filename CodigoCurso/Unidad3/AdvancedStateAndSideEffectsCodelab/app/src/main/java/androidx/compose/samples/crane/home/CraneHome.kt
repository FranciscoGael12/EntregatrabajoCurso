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

import androidx.compose.foundation.layout.padding // Importa la función que permite agregar un relleno o margen a un componente
import androidx.compose.foundation.layout.statusBarsPadding // Importa la función para agregar relleno en la parte superior para evitar que el contenido quede oculto por la barra de estado
import androidx.compose.material.BackdropScaffold // Importa el componente BackdropScaffold para crear una interfaz con una capa frontal y trasera
import androidx.compose.material.BackdropValue // Importa los valores posibles para el estado del BackdropScaffold
import androidx.compose.material.ExperimentalMaterialApi // Importa la anotación para usar funciones experimentales de Material Design
import androidx.compose.material.Scaffold // Importa el componente Scaffold, que proporciona una estructura básica para una pantalla, como barra superior, barra de navegación, etc.
import androidx.compose.material.rememberBackdropScaffoldState // Función que permite recordar el estado del BackdropScaffold
import androidx.compose.material.rememberScaffoldState // Función que permite recordar el estado del Scaffold
import androidx.compose.runtime.Composable // Importa la anotación Composable para definir funciones que representan UI en Compose
import androidx.compose.runtime.getValue // Permite obtener el valor de un estado en Compose
import androidx.compose.runtime.mutableStateOf // Crea un estado mutable en Compose
import androidx.compose.runtime.remember // Recuerda el valor de un estado entre recomposiciones
import androidx.compose.runtime.rememberCoroutineScope // Crea y recuerda un scope para lanzar corutinas
import androidx.compose.runtime.setValue // Permite actualizar el valor de un estado mutable
import androidx.compose.samples.crane.base.CraneDrawer // Importa el componente CraneDrawer, que muestra el menú lateral
import androidx.compose.samples.crane.base.CraneTabBar // Importa el componente CraneTabBar, que muestra las pestañas de navegación
import androidx.compose.samples.crane.base.CraneTabs // Importa el componente CraneTabs, que muestra las pestañas de selección
import androidx.compose.samples.crane.base.ExploreSection // Importa el componente ExploreSection, que muestra una lista de elementos para explorar
import androidx.compose.samples.crane.data.ExploreModel // Importa el modelo de datos ExploreModel
import androidx.compose.ui.Modifier // Importa la clase Modifier, que se usa para modificar la apariencia y el comportamiento de los composables
import androidx.compose.ui.graphics.Color // Importa la clase Color, que se usa para definir colores en Compose
import androidx.lifecycle.compose.collectAsStateWithLifecycle // Permite recolectar un flujo y observarlo de acuerdo con el ciclo de vida del componente
import androidx.lifecycle.viewmodel.compose.viewModel // Permite acceder a una instancia del ViewModel dentro de un composable
import kotlinx.coroutines.launch // Importa la función launch para ejecutar corutinas

typealias OnExploreItemClicked = (ExploreModel) -> Unit // Define un alias para un tipo de función que maneja el evento de hacer clic en un ítem de exploración

enum class CraneScreen { // Define una enumeración que representa las pantallas principales
    Fly, Sleep, Eat
}

@Composable // Define una función composable para la pantalla principal
fun CraneHome(
    onExploreItemClicked: OnExploreItemClicked, // Recibe una función como parámetro para manejar el evento de hacer clic en un ítem
    modifier: Modifier = Modifier, // Recibe un modificador opcional para aplicar a la UI
) {
    val scaffoldState = rememberScaffoldState() // Recuerda el estado del Scaffold, que gestiona la barra de navegación y otras configuraciones
    Scaffold( // Crea el componente Scaffold, que proporciona una estructura de pantalla básica
        scaffoldState = scaffoldState, // Asocia el estado del Scaffold
        modifier = Modifier.statusBarsPadding(), // Aplica un relleno para evitar que el contenido quede cubierto por la barra de estado
        drawerContent = { // Define el contenido del menú lateral (drawer)
            CraneDrawer() // Muestra el menú lateral
        }
    ) { padding -> // Recibe los márgenes y rellenos generados por Scaffold
        val scope = rememberCoroutineScope() // Recuerda un scope para lanzar corutinas
        CraneHomeContent( // Muestra el contenido principal de la pantalla
            modifier = modifier.padding(padding), // Aplica el relleno al contenido
            onExploreItemClicked = onExploreItemClicked, // Pasa la función que maneja el clic en los ítems
            openDrawer = { // Pasa la función para abrir el menú lateral
                scope.launch {
                    scaffoldState.drawerState.open() // Abre el menú lateral
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterialApi::class) // Permite usar componentes experimentales de Material
@Composable // Define una función composable para mostrar el contenido principal de Crane
fun CraneHomeContent(
    onExploreItemClicked: OnExploreItemClicked, // Recibe una función como parámetro para manejar el evento de hacer clic en un ítem
    openDrawer: () -> Unit, // Recibe una función que abre el menú lateral
    modifier: Modifier = Modifier, // Recibe un modificador opcional
    viewModel: MainViewModel = viewModel(), // Obtiene el ViewModel para manejar la lógica de datos
) {
    val suggestedDestinations by viewModel.suggestedDestinations.collectAsStateWithLifecycle() // Observa las sugerencias de destinos desde el ViewModel

    val onPeopleChanged: (Int) -> Unit = { viewModel.updatePeople(it) } // Define una función para actualizar la cantidad de personas
    var tabSelected by remember { mutableStateOf(CraneScreen.Fly) } // Mantiene el estado de la pestaña seleccionada

    BackdropScaffold( // Crea un BackdropScaffold, que tiene una capa frontal y una capa trasera
        modifier = modifier, // Aplica el modificador
        scaffoldState = rememberBackdropScaffoldState(BackdropValue.Revealed), // Define el estado del BackdropScaffold
        frontLayerScrimColor = Color.Unspecified, // Define el color del fondo cuando la capa frontal está revelada
        appBar = { // Define la barra de aplicación
            HomeTabBar(openDrawer, tabSelected, onTabSelected = { tabSelected = it }) // Muestra la barra de pestañas
        },
        backLayerContent = { // Define el contenido de la capa trasera
            SearchContent( // Muestra el contenido de búsqueda
                tabSelected,
                viewModel,
                onPeopleChanged
            )
        },
        frontLayerContent = { // Define el contenido de la capa frontal
            when (tabSelected) { // Muestra el contenido según la pestaña seleccionada
                CraneScreen.Fly -> { // Muestra la sección de vuelos
                    ExploreSection(
                        title = "Explore Flights by Destination", // Título de la sección
                        exploreList = suggestedDestinations, // Lista de destinos sugeridos
                        onItemClicked = onExploreItemClicked // Maneja el clic en un ítem
                    )
                }
                CraneScreen.Sleep -> { // Muestra la sección de propiedades
                    ExploreSection(
                        title = "Explore Properties by Destination", // Título de la sección
                        exploreList = viewModel.hotels, // Lista de hoteles
                        onItemClicked = onExploreItemClicked // Maneja el clic en un ítem
                    )
                }
                CraneScreen.Eat -> { // Muestra la sección de restaurantes
                    ExploreSection(
                        title = "Explore Restaurants by Destination", // Título de la sección
                        exploreList = viewModel.restaurants, // Lista de restaurantes
                        onItemClicked = onExploreItemClicked // Maneja el clic en un ítem
                    )
                }
            }
        }
    )
}

@Composable // Define una función composable para la barra de pestañas
private fun HomeTabBar(
    openDrawer: () -> Unit, // Función para abrir el menú lateral
    tabSelected: CraneScreen, // Pestaña seleccionada
    onTabSelected: (CraneScreen) -> Unit, // Función para manejar la selección de una nueva pestaña
    modifier: Modifier = Modifier // Modificador opcional
) {
    CraneTabBar( // Muestra la barra de pestañas
        modifier = modifier, // Aplica el modificador
        onMenuClicked = openDrawer // Asocia la función para abrir el menú lateral
    ) { tabBarModifier -> // Recibe un modificador para aplicar a la barra de pestañas
        CraneTabs( // Muestra las pestañas dentro de la barra
            modifier = tabBarModifier, // Aplica el modificador a las pestañas
            titles = CraneScreen.values().map { it.name }, // Títulos de las pestañas
            tabSelected = tabSelected, // Pestaña seleccionada
            onTabSelected = { newTab -> onTabSelected(CraneScreen.values()[newTab.ordinal]) } // Maneja la selección de una nueva pestaña
        )
    }
}

@Composable // Define una función composable para el contenido de búsqueda
private fun SearchContent(
    tabSelected: CraneScreen, // Pestaña seleccionada
    viewModel: MainViewModel, // ViewModel para obtener los datos
    onPeopleChanged: (Int) -> Unit // Función para manejar el cambio de número de personas
) {
    when (tabSelected) { // Muestra contenido basado en la pestaña seleccionada
        CraneScreen.Fly -> FlySearchContent( // Muestra contenido de búsqueda de vuelos
            onPeopleChanged = onPeopleChanged,
            onToDestinationChanged = { viewModel.toDestinationChanged(it) }
        )
        CraneScreen.Sleep -> SleepSearchContent( // Muestra contenido de búsqueda de propiedades
            onPeopleChanged = onPeopleChanged

        )
        CraneScreen.Eat -> EatSearchContent(
            onPeopleChanged = onPeopleChanged
        )
    }
}
