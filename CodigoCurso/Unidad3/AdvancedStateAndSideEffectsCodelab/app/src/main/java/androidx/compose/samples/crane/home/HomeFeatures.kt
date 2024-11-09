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
import androidx.compose.foundation.layout.Column // Importa el componente Column, que organiza los elementos de forma vertical
import androidx.compose.foundation.layout.Spacer // Importa el componente Spacer, que se usa para agregar espacio entre elementos
import androidx.compose.foundation.layout.height // Importa la función height que se utiliza para especificar la altura de un Spacer
import androidx.compose.foundation.layout.padding // Importa la función padding que permite agregar un relleno a los elementos
import androidx.compose.runtime.Composable // Importa la anotación Composable para crear funciones que representan UI en Compose
import androidx.compose.samples.crane.R // Importa los recursos como imágenes y cadenas del paquete R
import androidx.compose.samples.crane.base.SimpleUserInput // Importa el componente SimpleUserInput que se utiliza para ingresar texto con íconos
import androidx.compose.ui.Modifier // Importa la clase Modifier, que permite modificar la apariencia de los elementos UI
import androidx.compose.ui.unit.dp // Importa la unidad de medida dp (densidad independiente de píxeles) usada para tamaños

@Composable // Define una función composable para la búsqueda de vuelos
fun FlySearchContent(
    onPeopleChanged: (Int) -> Unit, // Recibe una función que maneja el cambio en el número de personas
    onToDestinationChanged: (String) -> Unit // Recibe una función que maneja el cambio en el destino
) {
    CraneSearch { // Llama al composable CraneSearch que organiza los elementos de búsqueda
        PeopleUserInput( // Muestra un campo de entrada para seleccionar el número de personas
            titleSuffix = ", Economy", // Añade un sufijo al título del campo
            onPeopleChanged = onPeopleChanged // Pasa la función que maneja el cambio de personas
        )
        Spacer(Modifier.height(8.dp)) // Agrega un espacio de 8 dp entre elementos
        FromDestination() // Muestra un campo de entrada para el lugar de salida
        Spacer(Modifier.height(8.dp)) // Agrega un espacio de 8 dp
        ToDestinationUserInput(onToDestinationChanged = onToDestinationChanged) // Muestra un campo de entrada para el destino
        Spacer(Modifier.height(8.dp)) // Agrega un espacio de 8 dp
        DatesUserInput() // Muestra un campo de entrada para las fechas
    }
}

@Composable // Define una función composable para la búsqueda de propiedades (hoteles)
fun SleepSearchContent(onPeopleChanged: (Int) -> Unit) {
    CraneSearch { // Llama al composable CraneSearch para organizar los elementos de búsqueda
        PeopleUserInput(onPeopleChanged = onPeopleChanged) // Muestra un campo de entrada para seleccionar el número de personas
        Spacer(Modifier.height(8.dp)) // Agrega un espacio de 8 dp
        DatesUserInput() // Muestra un campo de entrada para las fechas
        Spacer(Modifier.height(8.dp)) // Agrega un espacio de 8 dp
        SimpleUserInput(caption = "Select Location", vectorImageId = R.drawable.ic_hotel) // Muestra un campo de entrada para seleccionar la ubicación con un ícono de hotel
    }
}

@Composable // Define una función composable para la búsqueda de restaurantes
fun EatSearchContent(onPeopleChanged: (Int) -> Unit) {
    CraneSearch { // Llama al composable CraneSearch para organizar los elementos de búsqueda
        PeopleUserInput(onPeopleChanged = onPeopleChanged) // Muestra un campo de entrada para seleccionar el número de personas
        Spacer(Modifier.height(8.dp)) // Agrega un espacio de 8 dp
        DatesUserInput() // Muestra un campo de entrada para las fechas
        Spacer(Modifier.height(8.dp)) // Agrega un espacio de 8 dp
        SimpleUserInput(caption = "Select Time", vectorImageId = R.drawable.ic_time) // Muestra un campo de entrada para seleccionar la hora con un ícono de reloj
        Spacer(Modifier.height(8.dp)) // Agrega un espacio de 8 dp
        SimpleUserInput(caption = "Select Location", vectorImageId = R.drawable.ic_restaurant) // Muestra un campo de entrada para seleccionar la ubicación con un ícono de restaurante
    }
}

@Composable // Define una función composable privada para organizar los elementos de búsqueda dentro de un contenedor
private fun CraneSearch(content: @Composable () -> Unit) {
    Column(Modifier.padding(start = 24.dp, top = 0.dp, end = 24.dp, bottom = 12.dp)) { // Organiza los elementos en una columna con relleno en los bordes
        content() // Inserta el contenido proporcionado como parámetro
    }
}
