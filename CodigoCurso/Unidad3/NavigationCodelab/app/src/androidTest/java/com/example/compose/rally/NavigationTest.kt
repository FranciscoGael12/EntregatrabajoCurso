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

package com.example.compose.rally
import androidx.compose.ui.platform.LocalContext // Importa el contexto local, que es necesario para inicializar el TestNavHostController
import androidx.compose.ui.test.assertIsDisplayed // Importa la función de aserción para verificar si un elemento está visible en la pantalla
import androidx.compose.ui.test.junit4.createComposeRule // Importa la regla para configurar las pruebas de Compose
import androidx.compose.ui.test.onNodeWithContentDescription // Importa la función que encuentra un nodo por su descripción de contenido (contentDescription)
import androidx.compose.ui.test.performClick // Importa la función que simula un clic sobre un elemento en la interfaz de usuario
import androidx.compose.ui.test.performScrollTo // Importa la función que simula un desplazamiento hacia un elemento en la interfaz de usuario
import androidx.navigation.compose.ComposeNavigator // Importa el ComposeNavigator que permite la navegación en composables dentro de pruebas
import androidx.navigation.testing.TestNavHostController // Importa el TestNavHostController para simular navegación en pruebas de UI
import org.junit.Assert.assertEquals // Importa la función assertEquals para comparar valores en las pruebas
import org.junit.Before // Importa la anotación que indica que el método es para configuración previa a las pruebas
import org.junit.Rule // Importa la anotación para definir reglas en las pruebas unitarias
import org.junit.Test // Importa la anotación para definir un método como una prueba unitaria

class NavigationTest { // Define una clase para probar la navegación en la aplicación

    @get:Rule
    val composeTestRule = createComposeRule() // Crea una regla que configura el entorno de pruebas para Composable
    lateinit var navController: TestNavHostController // Declara una variable para el controlador de navegación en pruebas

    @Before
    fun setupRallyNavHost() { // Método que se ejecuta antes de cada prueba para configurar el entorno de pruebas
        composeTestRule.setContent { // Define el contenido que se utilizará en la prueba
            navController = TestNavHostController(LocalContext.current) // Inicializa el controlador de navegación con el contexto actual
            navController.navigatorProvider.addNavigator(ComposeNavigator()) // Añade el ComposeNavigator al controlador de navegación
            RallyNavHost(navController = navController) // Establece el RallyNavHost para la navegación en las pruebas
        }
    }

    @Test
    fun rallyNavHost_verifyOverviewStartDestination() { // Prueba para verificar que la pantalla de inicio (Overview) se muestra correctamente
        composeTestRule
            .onNodeWithContentDescription("Overview Screen") // Busca el nodo con la descripción "Overview Screen"
            .assertIsDisplayed() // Verifica que el nodo está siendo mostrado
    }

    @Test
    fun rallyNavHost_clickAllAccount_navigatesToAccounts() { // Prueba para verificar que al hacer clic en "All Accounts", se navega a la pantalla de cuentas
        composeTestRule
            .onNodeWithContentDescription("All Accounts") // Busca el nodo con la descripción "All Accounts"
            .performClick() // Realiza un clic sobre el nodo encontrado

        composeTestRule
            .onNodeWithContentDescription("Accounts Screen") // Busca el nodo con la descripción "Accounts Screen"
            .assertIsDisplayed() // Verifica que la pantalla de cuentas se muestra
    }

    @Test
    fun rallyNavHost_clickAllBills_navigateToBills() { // Prueba para verificar que al hacer clic en "All Bills", se navega a la pantalla de facturas
        composeTestRule.onNodeWithContentDescription("All Bills") // Busca el nodo con la descripción "All Bills"
            .performScrollTo() // Desplaza la vista hasta el nodo
            .performClick() // Realiza un clic sobre el nodo

        val route = navController.currentBackStackEntry?.destination?.route // Obtiene la ruta actual del controlador de navegación
        assertEquals(route, "bills") // Verifica que la ruta actual sea "bills"
    }
}
