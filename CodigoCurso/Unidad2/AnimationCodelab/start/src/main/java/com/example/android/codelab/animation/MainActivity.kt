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

package com.example.android.codelab.animation
//Se importa la clase Bundle, que es utilizada para almacenar datos en la actividad, como el estado de la interfaz de usuario o la información pasada entre actividades.
import android.os.Bundle
// Se importa la clase ComponentActivity que proporciona la base para crear actividades en aplicaciones Android, permitiendo integrar Jetpack Compose en lugar de la vista tradicional basada en XML.
import androidx.activity.ComponentActivity
//Se importa la función setContent, que se utiliza para establecer el contenido de la actividad utilizando composables de Jetpack Compose.
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
// Se importa la función enableEdgeToEdge, que permite que la interfaz de usuario de la aplicación ocupe toda la pantalla, eliminando los márgenes del sistema (como la barra de estado y la barra de navegación).
import com.example.android.codelab.animation.ui.AnimationCodelabTheme
//Se importa el tema personalizado AnimationCodelabTheme desde el paquete de la aplicación, el cual se aplica a la interfaz de usuario para proporcionar un estilo consistente.
import com.example.android.codelab.animation.ui.home.Home
//Se declara la clase MainActivity, que hereda de ComponentActivity. Esta clase es la actividad principal que se ejecutará cuando la aplicación se inicie.
class MainActivity : ComponentActivity() {
//Se sobrescribe el método onCreate, que es llamado cuando la actividad se crea. Recibe un parámetro savedInstanceState que contiene el estado anterior de la actividad, si existe.
    override fun onCreate(savedInstanceState: Bundle?) {
        //Se llama al método onCreate de la clase base (ComponentActivity) para asegurar que se ejecuten las funcionalidades estándar de la actividad, como la inicialización del ciclo de vida.
        super.onCreate(savedInstanceState)
    //Se llama a esta función para permitir que la interfaz de usuario ocupe toda la pantalla, lo que elimina los márgenes alrededor de la actividad, incluyendo la barra de estado y la barra de navegación.
        enableEdgeToEdge()
    //Inicia el bloque de código donde se define el contenido visual de la actividad utilizando Jetpack Compose. Dentro de este bloque, se especifica la interfaz de usuario que se mostrará.
        setContent {
            //Se aplica el tema personalizado AnimationCodelabTheme a la interfaz de usuario. Este tema envuelve el contenido que se mostrará, asegurando que todos los componentes respeten los estilos definidos en el tema.
            AnimationCodelabTheme {
                //Se invoca el composable Home, que es el componente principal de la pantalla. Este composable representa la interfaz de usuario que se mostrará al usuario al iniciar la actividad.
                Home()
            }
        }
    }
}
