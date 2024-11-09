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

package com.example.compose.rally
import androidx.compose.ui.test.assertIsDisplayed // Importa la función assertIsDisplayed que se usa para verificar que un nodo de la interfaz de usuario esté visible en la pantalla
import androidx.compose.ui.test.junit4.createComposeRule // Importa la función createComposeRule que se usa para crear un entorno de prueba para Composables
import androidx.compose.ui.test.onNodeWithText // Importa la función onNodeWithText que se usa para localizar un nodo en la interfaz de usuario que contenga un texto específico
import com.example.compose.rally.ui.overview.OverviewBody // Importa el Composable OverviewBody que representa la parte principal de la pantalla de la vista previa de la aplicación
import org.junit.Rule // Importa la anotación Rule para aplicar una regla de prueba en la clase de pruebas
import org.junit.Test // Importa la anotación Test para marcar un método como una prueba unitaria

class OverviewScreenTest { // Define la clase de pruebas para la pantalla de visión general (Overview)

    @get:Rule
    val composeTestRule = createComposeRule() // Crea una regla que configura el entorno de prueba para los Composables de Jetpack Compose

    @Test
    fun overviewScreen_alertsDisplayed() { // Define una prueba que verifica si la sección de "Alerts" está visible en la pantalla
        composeTestRule.setContent { // Establece el contenido de la pantalla de prueba usando el Composable OverviewBody
            OverviewBody() // Muestra el Composable OverviewBody en la pantalla de prueba
        }

        composeTestRule // Accede a la regla de prueba para realizar las aserciones sobre la interfaz de usuario
            .onNodeWithText("Alerts") // Localiza el nodo que contiene el texto "Alerts" en la interfaz de usuario
            .assertIsDisplayed() // Verifica que el nodo que contiene el texto "Alerts" esté visible en la pantalla
    }
}
