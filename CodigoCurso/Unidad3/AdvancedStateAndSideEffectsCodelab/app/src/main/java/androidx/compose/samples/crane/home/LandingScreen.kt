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

import androidx.compose.foundation.Image // Importa el componente Image para mostrar imágenes en la interfaz
import androidx.compose.foundation.layout.Box // Importa el componente Box para organizar los elementos dentro de un contenedor de caja
import androidx.compose.foundation.layout.fillMaxSize // Importa la función fillMaxSize para hacer que un elemento ocupe todo el espacio disponible
import androidx.compose.runtime.Composable // Importa la anotación Composable para crear funciones que representan UI en Compose
import androidx.compose.runtime.LaunchedEffect // Importa LaunchedEffect para ejecutar efectos secundarios en la composición de forma controlada
import androidx.compose.runtime.getValue // Importa la función getValue para acceder a valores delegados
import androidx.compose.runtime.rememberUpdatedState // Importa la función rememberUpdatedState para recordar y actualizar el estado dentro de composables
import androidx.compose.samples.crane.R // Importa los recursos como imágenes y cadenas del paquete R
import androidx.compose.ui.Alignment // Importa la clase Alignment para alinear los elementos dentro de un contenedor
import androidx.compose.ui.Modifier // Importa la clase Modifier, que permite modificar la apariencia de los elementos UI
import androidx.compose.ui.res.painterResource // Importa la función painterResource para cargar imágenes desde los recursos
import kotlinx.coroutines.delay // Importa delay de coroutines para pausar la ejecución de la corutina durante un tiempo específico

private const val SplashWaitTime: Long = 2000 // Define el tiempo de espera (en milisegundos) antes de que se ejecute la acción de timeout

@Composable // Define una función composable para la pantalla de bienvenida
fun LandingScreen(onTimeout: () -> Unit, modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) { // Crea un Box que ocupa todo el espacio disponible y alinea el contenido en el centro
        // Adds composition consistency. Use the value when LaunchedEffect is first called
        val currentOnTimeout by rememberUpdatedState(onTimeout) // Guarda la referencia actual de onTimeout para que se actualice si cambia durante la composición

        LaunchedEffect(Unit) { // Ejecuta la acción de efecto lanzado cuando la composición se inicializa
            delay(SplashWaitTime) // Espera el tiempo definido en SplashWaitTime (2 segundos)
            currentOnTimeout() // Llama a la función onTimeout después del tiempo de espera
        }
        Image(painterResource(id = R.drawable.ic_crane_drawer), contentDescription = null) // Muestra la imagen de carga (un ícono) centrada en la pantalla
    }
}

