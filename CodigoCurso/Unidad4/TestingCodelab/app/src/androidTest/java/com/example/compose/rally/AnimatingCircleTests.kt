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
import android.os.Build // Importa la clase Build, que proporciona acceso a la información sobre la versión de Android en la que se ejecuta la aplicación
import androidx.compose.foundation.background // Importa el modificador background que se usa para aplicar un color de fondo a un Composable
import androidx.compose.foundation.layout.size // Importa el modificador size que se usa para establecer el tamaño de un Composable
import androidx.compose.ui.Modifier // Importa la clase Modifier, que permite modificar las propiedades de los Composables en Jetpack Compose
import androidx.compose.ui.graphics.Color // Importa la clase Color para definir colores en la interfaz
import androidx.compose.ui.test.ExperimentalTestApi // Importa la anotación ExperimentalTestApi para indicar que las API experimentales de pruebas están en uso
import androidx.compose.ui.test.MainTestClock // Importa la clase MainTestClock para controlar el tiempo en las pruebas y avanzar el reloj en simulaciones
import androidx.compose.ui.test.junit4.createComposeRule // Importa la regla para configurar un entorno de pruebas para Composables en Jetpack Compose
import androidx.compose.ui.test.onRoot // Importa la función onRoot para acceder al nodo raíz del Composable en las pruebas
import androidx.compose.ui.unit.dp // Importa la clase dp para definir medidas en densidad independiente de píxeles
import androidx.test.filters.SdkSuppress // Importa la anotación SdkSuppress para suprimir pruebas en versiones de SDK inferiores al mínimo especificado
import com.example.compose.rally.ui.components.AnimatedCircle // Importa el componente AnimatedCircle desde la UI de la aplicación
import com.example.compose.rally.ui.theme.RallyTheme // Importa el tema de la aplicación para aplicar estilos globales
import org.junit.Rule // Importa la anotación Rule para definir reglas en las pruebas unitarias
import org.junit.Test // Importa la anotación Test para marcar un método como una prueba unitaria

/**
 * Test to showcase [MainTestClock] present in [ComposeTestRule]. It allows for animation
 * testing at specific points in time.
 *
 * For assertions, a simple screenshot testing framework is used. It requires SDK 26+ and to
 * be run on a device with 420dpi, as that the density used to generate the golden images
 * present in androidTest/assets. It runs bitmap comparisons on device.
 *
 * Note that different systems can produce slightly different screenshots making the test fail.
 */
@ExperimentalTestApi // Marca la clase como que utiliza API experimentales de pruebas
@SdkSuppress(minSdkVersion = Build.VERSION_CODES.O) // Supone que las pruebas solo se ejecuten en dispositivos con una versión de SDK superior o igual a Android O
class AnimatingCircleTests { // Define la clase de pruebas para la animación del círculo

    @get:Rule
    val composeTestRule = createComposeRule() // Crea una regla para configurar el entorno de pruebas para Composables

    @Test
    fun circleAnimation_idle_screenshot() { // Define una prueba para verificar el estado final de la animación (idle)
        composeTestRule.mainClock.autoAdvance = true // Habilita el avance automático del reloj durante las pruebas
        showAnimatedCircle() // Llama a la función que muestra el círculo animado
        assertScreenshotMatchesGolden("circle_done", composeTestRule.onRoot()) // Toma una captura de pantalla y la compara con la imagen dorada (golden image)
    }

    @Test
    fun circleAnimation_initial_screenshot() { // Define una prueba para verificar el estado inicial de la animación
        compareTimeScreenshot(0, "circle_initial") // Llama a la función de comparación de captura de pantalla en el tiempo 0 (inicio de la animación)
    }

    @Test
    fun circleAnimation_beforeDelay_screenshot() { // Define una prueba para verificar el estado de la animación antes de un retraso
        compareTimeScreenshot(499, "circle_initial") // Llama a la función de comparación de captura de pantalla a los 499ms
    }

    @Test
    fun circleAnimation_midAnimation_screenshot() { // Define una prueba para verificar el estado de la animación en el medio
        compareTimeScreenshot(600, "circle_100") // Llama a la función de comparación de captura de pantalla a los 600ms (al 100% de la animación)
    }

    @Test
    fun circleAnimation_animationDone_screenshot() { // Define una prueba para verificar el estado final de la animación
        compareTimeScreenshot(1500, "circle_done") // Llama a la función de comparación de captura de pantalla cuando la animación está completa (1500ms)
    }

    private fun compareTimeScreenshot(timeMs: Long, goldenName: String) { // Función privada que compara una captura de pantalla en un tiempo específico
        composeTestRule.mainClock.autoAdvance = false // Desactiva el avance automático del reloj
        showAnimatedCircle() // Muestra el círculo animado
        composeTestRule.mainClock.advanceTimeBy(timeMs) // Avanza el reloj por el tiempo especificado en milisegundos
        assertScreenshotMatchesGolden(goldenName, composeTestRule.onRoot()) // Compara la captura de pantalla con la imagen dorada en el tiempo especificado
    }

    private fun showAnimatedCircle() { // Función privada que establece el contenido de prueba con un círculo animado
        composeTestRule.setContent { // Establece el contenido de prueba en Compose
            RallyTheme { // Aplica el tema de la aplicación
                AnimatedCircle( // Inserta el componente AnimatedCircle en la interfaz de usuario
                    modifier = Modifier.background(Color.White).size(320.dp), // Aplica un fondo blanco y un tamaño de 320dp al círculo
                    proportions = listOf(0.25f, 0.5f, 0.25f), // Establece las proporciones de las secciones del círculo
                    colors = listOf(Color.Red, Color.DarkGray, Color.Black) // Establece los colores de las secciones del círculo
                )
            }
        }
    }
}
