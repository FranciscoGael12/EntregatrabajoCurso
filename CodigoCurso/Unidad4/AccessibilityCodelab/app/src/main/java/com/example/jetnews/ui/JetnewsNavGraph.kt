/*
 * Copyright 2021 The Android Open Source Project
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

package com.example.jetnews.ui
import androidx.compose.material3.DrawerState // Importa la clase DrawerState para controlar el estado del cajón de navegación
import androidx.compose.material3.DrawerValue // Importa la clase DrawerValue que tiene valores posibles como "abierto" o "cerrado" para el cajón de navegación
import androidx.compose.material3.rememberDrawerState // Importa la función para recordar el estado del cajón de navegación
import androidx.compose.runtime.Composable // Importa la anotación Composable que indica que esta función representa un componente de UI
import androidx.compose.runtime.remember // Importa la función remember que se usa para almacenar valores de forma persistente durante las recomposiciones
import androidx.compose.runtime.rememberCoroutineScope // Importa la función para recordar un scope de corrutinas que se puede usar en la UI
import androidx.navigation.NavHostController // Importa la clase NavHostController que gestiona las rutas de navegación en una aplicación
import androidx.navigation.compose.NavHost // Importa el componente NavHost que define las rutas de navegación
import androidx.navigation.compose.composable // Importa la función composable que define qué pantalla se muestra en una ruta específica
import androidx.navigation.compose.rememberNavController // Importa la función para recordar el controlador de navegación
import com.example.jetnews.data.AppContainer // Importa la clase AppContainer, que parece gestionar los repositorios y otros recursos
import com.example.jetnews.ui.MainDestinations.ARTICLE_ID_KEY // Importa una constante que se usa como clave para obtener el ID de un artículo
import com.example.jetnews.ui.article.ArticleScreen // Importa el componente para mostrar los detalles de un artículo
import com.example.jetnews.ui.home.HomeScreen // Importa el componente para mostrar la pantalla de inicio
import com.example.jetnews.ui.interests.InterestsScreen // Importa el componente para mostrar la pantalla de intereses
import kotlinx.coroutines.launch // Importa la función launch para crear corrutinas y ejecutar operaciones asincrónicas

/**
 * Destinations used in the ([JetnewsApp]). // Un objeto que contiene las rutas utilizadas en la aplicación.
 */
object MainDestinations {
    const val HOME_ROUTE = "home" // Ruta para la pantalla de inicio
    const val INTERESTS_ROUTE = "interests" // Ruta para la pantalla de intereses
    const val ARTICLE_ROUTE = "post" // Ruta para la pantalla del artículo
    const val ARTICLE_ID_KEY = "postId" // Clave usada para pasar el ID de un artículo en la URL
}

@Composable // Marca la función como un componente de interfaz de usuario en Jetpack Compose
fun JetnewsNavGraph( // Define la función que gestiona las rutas de navegación dentro de la aplicación
    appContainer: AppContainer, // Recibe el contenedor de la aplicación que tiene los repositorios necesarios
    navController: NavHostController = rememberNavController(), // Crea un controlador de navegación que se recuerda entre recomposiciones
    drawerState: DrawerState = rememberDrawerState(DrawerValue.Closed), // Inicializa el estado del cajón de navegación como cerrado
    startDestination: String = MainDestinations.HOME_ROUTE // Define la ruta inicial de la navegación (pantalla de inicio)
) {
    val actions = remember(navController) { MainActions(navController) } // Recuerda las acciones de navegación para el controlador
    val coroutineScope = rememberCoroutineScope() // Recuerda el scope de corrutinas para poder usarlo en la navegación
    val openDrawer: () -> Unit = { coroutineScope.launch { drawerState.open() } } // Define una acción para abrir el cajón de navegación cuando sea necesario

    NavHost( // Crea un host de navegación que manejará la navegación entre pantallas
        navController = navController, // Asocia el controlador de navegación con el NavHost
        startDestination = startDestination // Establece la pantalla de inicio
    ) {
        composable(MainDestinations.HOME_ROUTE) { // Define la ruta y la pantalla para la pantalla de inicio
            HomeScreen( // Componente que muestra la pantalla principal
                postsRepository = appContainer.postsRepository, // Pasa el repositorio de publicaciones para mostrar los datos
                navigateToArticle = actions.navigateToArticle, // Pasa la acción para navegar a un artículo
                openDrawer = openDrawer // Pasa la acción para abrir el cajón de navegación
            )
        }
        composable(MainDestinations.INTERESTS_ROUTE) { // Define la ruta y la pantalla para la pantalla de intereses
            InterestsScreen( // Componente que muestra los intereses
                interestsRepository = appContainer.interestsRepository, // Pasa el repositorio de intereses
                openDrawer = openDrawer // Pasa la acción para abrir el cajón de navegación
            )
        }
        composable("${MainDestinations.ARTICLE_ROUTE}/{$ARTICLE_ID_KEY}") { backStackEntry -> // Define la ruta para los artículos, pasando el ID del artículo como parte de la URL
            ArticleScreen( // Componente que muestra la pantalla de detalles del artículo
                postId = backStackEntry.arguments?.getString(ARTICLE_ID_KEY), // Obtiene el ID del artículo de los parámetros de la URL
                onBack = actions.upPress, // Acción para volver a la pantalla anterior
                postsRepository = appContainer.postsRepository // Pasa el repositorio de publicaciones para obtener los datos del artículo
            )
        }
    }
}

/**
 * Models the navigation actions in the app. // Esta clase define las acciones de navegación de la aplicación.
 */
class MainActions(navController: NavHostController) { // Clase que encapsula las acciones de navegación
    val navigateToArticle: (String) -> Unit = { postId: String -> // Acción para navegar a la pantalla de detalles de un artículo, pasando su ID
        navController.navigate("${MainDestinations.ARTICLE_ROUTE}/$postId") // Navega a la ruta del artículo con el ID
    }
    val upPress: () -> Unit = { // Acción para navegar hacia atrás (pantalla anterior)
        navController.navigateUp() // Regresa a la pantalla anterior en la pila de navegación
    }
}
