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

// Se importan las anotaciones necesarias de Room para definir las relaciones entre entidades.
import androidx.room.Embedded // Se importa la anotación Embedded para incluir un objeto completo como un campo dentro de una entidad.
import androidx.room.Relation // Se importa la anotación Relation para definir la relación entre dos entidades en la base de datos.

/**
 * Esta clase captura la relación entre una [Plant] (planta) y las instancias de [GardenPlanting]
 * (plantaciones en el jardín) de un usuario. Room utilizará esta clase para obtener las entidades relacionadas.
 */
data class PlantAndGardenPlantings(
    @Embedded // Se utiliza la anotación Embedded para incluir todos los campos de la clase 'Plant' dentro de esta clase.
    val plant: Plant, // Se incluye la instancia de 'Plant' como un campo de la clase. 'Plant' es una entidad en la base de datos.

    @Relation(parentColumn = "id", entityColumn = "plant_id") // Se establece la relación entre la tabla 'plants' y 'garden_plantings'.
    val gardenPlantings: List<GardenPlanting> = emptyList() // Se define una lista de 'GardenPlanting', que son las instancias relacionadas con la planta, basada en la relación.
)
