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
import android.annotation.SuppressLint // Importa la anotación para suprimir advertencias de lint en el código
import androidx.compose.foundation.layout.WindowInsets // Importa la clase WindowInsets para manejar márgenes y ajustes del sistema en los layouts
import androidx.compose.foundation.layout.width // Importa el modificador para establecer el ancho de un elemento
import androidx.compose.material3.DrawerValue // Importa la clase DrawerValue para controlar el estado del cajón de navegación
import androidx.compose.material3.ModalDrawerSheet // Importa el componente ModalDrawerSheet que representa el contenido del cajón de navegación
import androidx.compose.material3.ModalNavigationDrawer // Importa el componente ModalNavigationDrawer para crear un cajón de navegación modal
import androidx.compose.material3.Scaffold // Importa el componente Scaffold para estructurar la interfaz de usuario con barra superior, contenido principal, etc.
import androidx.compose.material3.rememberDrawerState // Importa la función que recuerda el estado del cajón de navegación
import androidx.compose.runtime.Composable // Importa la anotación Composable que indica que la función puede ser usada como una composición de UI
import androidx.compose.runtime.SideEffect // Importa la función SideEffect que permite ejecutar efectos secundarios dentro de una composición
import androidx.compose.runtime.getValue // Importa la extensión para obtener valores de una propiedad de tipo State
import androidx.compose.runtime.rememberCoroutineScope // Importa la función para recordar un scope de corrutinas dentro de una composición
import androidx.compose.ui.Modifier // Importa la clase Modifier para aplicar modificaciones a los componentes de UI
import androidx.compose.ui.graphics.Color // Importa la clase Color para trabajar con colores
import androidx.compose.ui.unit.dp // Importa la unidad dp (píxeles independientes de densidad) para establecer tamaños
import androidx.navigation.compose.currentBackStackEntryAsState // Importa la función para obtener el estado actual de la pila de navegación
import androidx.navigation.compose.rememberNavController // Importa la función para recordar el controlador de navegación
import com.example.jetnews.data.AppContainer // Importa la clase AppContainer, que probablemente maneja las dependencias de la aplicación
import com.example.jetnews.ui.theme.JetnewsTheme // Importa el tema personalizado para la aplicación
import com.google.accompanist.systemuicontroller.rememberSystemUiController // Importa la función para controlar la apariencia de las barras del sistema
import kotlinx.coroutines.launch // Importa la función launch para iniciar corrutinas

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter") // Suprime las advertencias de lint sobre parámetros no utilizados en Scaffold
@Composable // Anotación que indica que la función JetnewsApp es un componente Composable de Jetpack Compose
fun JetnewsApp( // Función principal de la aplicación que recibe un AppContainer como parámetro
    appContainer: AppContainer // Parámetro que contiene las dependencias de la aplicación
) {
    JetnewsTheme { // Aplica el tema personalizado a toda la interfaz de usuario
        val systemUiController = rememberSystemUiController() // Recuerda el controlador de la interfaz de usuario del sistema (para cambiar colores de barras del sistema)
        SideEffect { // Efecto secundario que se ejecuta cuando la UI cambia
            systemUiController.setSystemBarsColor(Color.Transparent, darkIcons = false) // Configura las barras del sistema con color transparente y con íconos oscuros
        }

        val navController = rememberNavController() // Recuerda el controlador de navegación para gestionar las rutas de la aplicación
        val coroutineScope = rememberCoroutineScope() // Recuerda el scope de corrutinas para lanzar tareas asincrónicas

        val navBackStackEntry by navController.currentBackStackEntryAsState() // Obtiene la entrada actual de la pila de navegación
        val currentRoute = navBackStackEntry?.destination?.route ?: MainDestinations.HOME_ROUTE // Establece la ruta actual de la navegación, con una ruta predeterminada si no hay ninguna

        val drawerState = rememberDrawerState(DrawerValue.Closed) // Recuerda el estado del cajón de navegación, inicializado en cerrado
        val scope = rememberCoroutineScope() // Recuerda otro scope de corrutinas para controlar el cierre del cajón

        ModalNavigationDrawer( // Crea un cajón de navegación modal
            drawerState = drawerState, // Asocia el estado del cajón con el controlador de estado
            drawerContent = { // Contenido del cajón de navegación
                ModalDrawerSheet(modifier = Modifier.width(300.dp), windowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp)) { // Define el ancho y márgenes del cajón
                    AppDrawer( // Componente que muestra los elementos dentro del cajón de navegación
                        currentRoute = currentRoute, // Pasa la ruta actual para que se pueda resaltar el item activo
                        navigateToHome = { navController.navigate(MainDestinations.HOME_ROUTE) }, // Navega a la pantalla principal
                        navigateToInterests = { navController.navigate(MainDestinations.INTERESTS_ROUTE) }, // Navega a la pantalla de intereses
                        closeDrawer = { coroutineScope.launch { drawerState.close() } } // Cierra el cajón de navegación al invocar este método
                    )
                }
            },
            content = { // Contenido principal de la aplicación que se muestra cuando el cajón está cerrado
                Scaffold { // Crea una estructura básica de UI con barra superior, contenido, etc.
                    JetnewsNavGraph( // Llama al gráfico de navegación, pasándole el contenedor de dependencias y el controlador de navegación
                        appContainer = appContainer,
                        navController = navController,
                        drawerState = drawerState
                    )
                }
            }
        )
    }
}
