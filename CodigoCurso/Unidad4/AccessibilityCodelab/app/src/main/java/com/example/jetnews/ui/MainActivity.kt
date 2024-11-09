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
import android.os.Bundle // Importa la clase Bundle, utilizada para pasar datos entre actividades o guardar el estado de la actividad
import androidx.activity.ComponentActivity // Importa la clase base para actividades que usan el enfoque de actividades de componentes en Jetpack
import androidx.activity.compose.setContent // Importa la función para establecer el contenido composable en la actividad
import androidx.core.view.WindowCompat // Importa la clase para controlar la compatibilidad de la ventana con el sistema operativo
import com.example.jetnews.JetnewsApplication // Importa la clase JetnewsApplication para acceder a la lógica de la aplicación

class MainActivity : ComponentActivity() { // Define la clase MainActivity que hereda de ComponentActivity

    override fun onCreate(savedInstanceState: Bundle?) { // Método que se llama cuando la actividad es creada
        super.onCreate(savedInstanceState) // Llama al método onCreate de la clase base para inicializar la actividad

        WindowCompat.setDecorFitsSystemWindows(window, false) // Configura la ventana para que no ajuste automáticamente el contenido a los bordes del sistema, permitiendo una interfaz más personalizada

        val appContainer = (application as JetnewsApplication).container // Obtiene el contenedor de dependencias de la aplicación a partir de la clase JetnewsApplication

        setContent { // Establece el contenido composable que se mostrará en la actividad
            JetnewsApp(appContainer) // Llama a JetnewsApp pasando el contenedor de la aplicación para su inicialización
        }
    }
}

