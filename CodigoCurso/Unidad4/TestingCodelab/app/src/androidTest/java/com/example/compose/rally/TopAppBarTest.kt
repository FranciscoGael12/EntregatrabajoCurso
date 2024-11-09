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
import androidx.compose.ui.test.assertIsSelected // Importa la función para verificar que un nodo esté seleccionado en la prueba
import androidx.compose.ui.test.hasContentDescription // Importa la función para verificar que un nodo tenga una descripción de contenido específica
import androidx.compose.ui.test.hasParent // Importa la función para verificar que un nodo tenga un nodo padre con una característica específica
import androidx.compose.ui.test.hasText // Importa la función para verificar que un nodo tenga un texto específico
import androidx.compose.ui.test.junit4.createComposeRule // Importa la función para crear una regla de prueba Compose
import androidx.compose.ui.test.onNodeWithContentDescription // Importa la función para seleccionar un nodo basado en su descripción de contenido
import com.example.compose.rally.ui.components.RallyTopAppBar // Importa el componente RallyTopAppBar para probarlo en los tests
import org.junit.Rule // Importa la anotación Rule para definir reglas de prueba
import org.junit.Test // Importa la anotación Test para marcar funciones de prueba

class TopAppBarTest { // Define la clase de prueba para el componente RallyTopAppBar

    @get:Rule
    val composeTestRule = createComposeRule() // Crea la regla de prueba para ejecutar pruebas sobre la interfaz de usuario Compose

    @Test
    fun rallyTopAppBarTest_currentTabSelected() { // Define la prueba para verificar si la pestaña actual está seleccionada
        composeTestRule.setContent { // Define el contenido de la prueba
            RallyTopAppBar( // Inicializa el componente RallyTopAppBar
                allScreens = RallyScreen.entries, // Proporciona las pantallas disponibles en el componente
                onTabSelected = { }, // Define el comportamiento cuando se selecciona una pestaña
                currentScreen = RallyScreen.Accounts // Establece la pantalla actual como la pestaña "Accounts"
            )
        }

        composeTestRule // Ejecuta la regla de prueba
            .onNodeWithContentDescription(RallyScreen.Accounts.name) // Busca el nodo que tiene la descripción de contenido correspondiente a "Accounts"
            .assertIsSelected() // Verifica que el nodo encontrado esté seleccionado
    }

    @Test
    fun rallyTopAppBarTest_currentLabelExists() { // Define la prueba para verificar si la etiqueta actual existe en la interfaz
        composeTestRule.setContent { // Define el contenido de la prueba
            RallyTopAppBar( // Inicializa el componente RallyTopAppBar
                allScreens = RallyScreen.entries, // Proporciona las pantallas disponibles en el componente
                onTabSelected = { }, // Define el comportamiento cuando se selecciona una pestaña
                currentScreen = RallyScreen.Accounts // Establece la pantalla actual como la pestaña "Accounts"
            )
        }

        composeTestRule // Ejecuta la regla de prueba
            .onNode( // Busca el nodo con las condiciones siguientes
                hasText(RallyScreen.Accounts.name.uppercase()) and // Verifica que el nodo tenga el texto correspondiente a la pantalla "Accounts" en mayúsculas
                        hasParent( // Verifica que el nodo tenga un padre que cumpla con la siguiente condición
                            hasContentDescription(RallyScreen.Accounts.name) // Verifica que el nodo padre tenga la descripción de contenido correspondiente a "Accounts"
                        ),
                useUnmergedTree = true // Indica que se debe buscar en todo el árbol de nodos, no solo en los nodos directamente visibles
            )
            .assertExists() // Verifica que el nodo con las condiciones anteriores exista
    }
}
