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
// Importaciones necesarias de la biblioteca Room para trabajar con bases de datos.
import androidx.room.ColumnInfo // Anotación para renombrar columnas en la base de datos.
import androidx.room.Entity // Anotación que marca la clase como entidad de Room (tabla).
import androidx.room.ForeignKey // Anotación para definir claves foráneas entre tablas.
import androidx.room.Index // Anotación para definir índices en las columnas de la tabla.
import androidx.room.PrimaryKey // Anotación para definir la clave primaria de la entidad.
import java.util.Calendar // Importación de la clase Calendar para trabajar con fechas.

// Comentario que describe la clase GardenPlanting.
/**
 * [GardenPlanting] representa cuando un usuario agrega una [Plant] a su jardín, con metadatos útiles.
 * Propiedades como [lastWateringDate] se usan para notificaciones (como cuándo regar la planta).
 *
 * Declarar la información de la columna permite renombrar variables sin implementar una
 * migración de base de datos, ya que el nombre de la columna no cambiaría.
 */
@Entity(
    tableName = "garden_plantings", // Define el nombre de la tabla en la base de datos.
    foreignKeys = [ // Define las claves foráneas de la tabla.
        ForeignKey(entity = Plant::class, parentColumns = ["id"], childColumns = ["plant_id"]) // Relaciona la tabla garden_plantings con la tabla Plant, usando la columna plant_id.
    ],
    indices = [Index("plant_id")] // Define un índice en la columna plant_id para mejorar las consultas.
)
data class GardenPlanting(
    // Define la propiedad plantId que será almacenada en la columna plant_id de la tabla garden_plantings.
    @ColumnInfo(name = "plant_id") val plantId: String,

    /**
     * Indica cuándo se plantó la [Plant]. Se utiliza para mostrar notificaciones cuando es tiempo de cosechar la planta.
     */
    // Define la propiedad plantDate que almacena la fecha de plantación en la columna plant_date.
    @ColumnInfo(name = "plant_date") val plantDate: Calendar = Calendar.getInstance(), // Usa la fecha actual si no se proporciona un valor.

    /**
     * Indica cuándo fue la última vez que se regó la [Plant]. Se utiliza para mostrar notificaciones cuando es tiempo de regar la planta.
     */
    // Define la propiedad lastWateringDate que almacena la última fecha de riego en la columna last_watering_date.
    @ColumnInfo(name = "last_watering_date")
    val lastWateringDate: Calendar = Calendar.getInstance() // Usa la fecha actual si no se proporciona un valor.
) {
    // Define la propiedad gardenPlantingId como la clave primaria de la tabla. Se genera automáticamente con valores únicos.
    @PrimaryKey(autoGenerate = true) // La propiedad gardenPlantingId se auto-genera como clave primaria.
    @ColumnInfo(name = "id") // Define la columna id en la base de datos.
    var gardenPlantingId: Long = 0 // Valor inicial de gardenPlantingId (será autogenerado).
}
