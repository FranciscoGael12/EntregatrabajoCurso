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
/**
 * Módulo Repository para manejar las operaciones de datos.
 */
class PlantRepository private constructor(private val plantDao: PlantDao) {

    // Función para obtener todas las plantas desde la base de datos usando el DAO (Data Access Object).
    fun getPlants() = plantDao.getPlants()

    // Función para obtener una planta específica de la base de datos, usando el DAO y el identificador único de la planta.
    fun getPlant(plantId: String) = plantDao.getPlant(plantId)

    // Función para obtener plantas que coinciden con un número específico de zona de crecimiento (grow zone).
    fun getPlantsWithGrowZoneNumber(growZoneNumber: Int) =
        plantDao.getPlantsWithGrowZoneNumber(growZoneNumber)

    companion object {

        // Para la creación del Singleton.
        @Volatile private var instance: PlantRepository? = null

        // Función para obtener la instancia única del PlantRepository. Si no existe, la crea de manera segura en un bloque synchronized.
        fun getInstance(plantDao: PlantDao) =
            instance ?: synchronized(this) {
                instance ?: PlantRepository(plantDao).also { instance = it }
            }
    }
}

