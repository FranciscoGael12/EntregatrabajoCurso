/*
 * Copyright 2018 Google LLC
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

package com.google.samples.apps.sunflower.data
// Importación de las clases necesarias de Room y Java para trabajar con la base de datos y fechas.
import androidx.room.ColumnInfo // Se importa ColumnInfo para definir cómo se deben mapear las propiedades de la clase a las columnas de la base de datos.
import androidx.room.Entity // Se importa Entity para definir la clase como una entidad de base de datos en Room.
import androidx.room.PrimaryKey // Se importa PrimaryKey para indicar que la propiedad 'plantId' será la clave primaria de la tabla.
import java.util.Calendar // Se importa Calendar para trabajar con fechas y calcular los intervalos de tiempo.
import java.util.Calendar.DAY_OF_YEAR // Se importa DAY_OF_YEAR para agregar días a la fecha en el cálculo de los intervalos de riego.

@Entity(tableName = "plants") // Se define la clase Plant como una entidad en la base de datos Room, con el nombre de tabla "plants".
data class Plant(
    @PrimaryKey @ColumnInfo(name = "id") // Se marca 'plantId' como la clave primaria de la entidad y se especifica el nombre de la columna como "id".
    val plantId: String, // Propiedad para el identificador único de la planta.
    val name: String, // Propiedad para el nombre de la planta.
    val description: String, // Propiedad para la descripción de la planta.
    val growZoneNumber: Int, // Propiedad para el número de zona de crecimiento de la planta.
    val wateringInterval: Int = 7, // Propiedad que define el intervalo de riego de la planta en días, con un valor predeterminado de 7 días.
    val imageUrl: String = "" // Propiedad para la URL de la imagen de la planta, con un valor predeterminado de cadena vacía.
) {

    /**
     * Determina si la planta necesita ser regada. Devuelve true si la fecha 'since' es mayor que la fecha
     * de último riego + el intervalo de riego; false en caso contrario.
     */
    fun shouldBeWatered(since: Calendar, lastWateringDate: Calendar) =
        // La función compara la fecha actual ('since') con la fecha de último riego más el intervalo de riego.
        since > lastWateringDate.apply { add(DAY_OF_YEAR, wateringInterval) } // Si la fecha 'since' es mayor que la fecha calculada, retorna true.

    // Sobreescribe el método toString para que al imprimir un objeto Plant se muestre su nombre.
    override fun toString() = name
}
