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
// Importación de las clases necesarias para trabajar con corutinas en Kotlin.
import kotlinx.coroutines.CoroutineDispatcher // Se importa CoroutineDispatcher para manejar diferentes tipos de despachadores de corutinas.
import kotlinx.coroutines.Dispatchers // Se importa Dispatchers para acceder a los despachadores predeterminados (como IO, Main, etc.).
import kotlinx.coroutines.withContext // Se importa withContext para cambiar el contexto de ejecución dentro de una corutina.

// Definición de la clase GardenPlantingRepository como un repositorio para manejar la lógica de datos.
class GardenPlantingRepository private constructor(
    // Se recibe un objeto de tipo GardenPlantingDao, que es responsable de las operaciones de base de datos.
    private val gardenPlantingDao: GardenPlantingDao,
    // Se recibe un CoroutineDispatcher para gestionar el despacho de las corutinas, específicamente en hilos de entrada/salida (IO).
    private val ioDispatcher: CoroutineDispatcher
) {

    // Función suspendida que crea una nueva plantación de jardín con un ID de planta específico.
    suspend fun createGardenPlanting(plantId: String) {
        // withContext cambia el contexto de ejecución a un hilo de IO para realizar la operación de base de datos sin bloquear el hilo principal.
        withContext(ioDispatcher) {
            // Crea un nuevo objeto GardenPlanting utilizando el ID de la planta proporcionado.
            val gardenPlanting = GardenPlanting(plantId)
            // Inserta la plantación del jardín en la base de datos a través del DAO.
            gardenPlantingDao.insertGardenPlanting(gardenPlanting)
        }
    }

    // Función suspendida que elimina una plantación de jardín específica de la base de datos.
    suspend fun removeGardenPlanting(gardenPlanting: GardenPlanting) {
        // Elimina la plantación del jardín de la base de datos usando el DAO.
        gardenPlantingDao.deleteGardenPlanting(gardenPlanting)
    }

    // Función que verifica si una planta con el ID proporcionado ya está plantada en el jardín.
    fun isPlanted(plantId: String) =
        // Llama a un método del DAO para verificar si la planta está plantada en el jardín.
        gardenPlantingDao.isPlanted(plantId)

    // Función que obtiene todas las plantaciones de jardín actualmente presentes en la base de datos.
    fun getPlantedGardens() = gardenPlantingDao.getPlantedGardens()

    companion object {
        // Variable que mantiene una referencia única del repositorio en el patrón Singleton.
        @Volatile private var instance: GardenPlantingRepository? = null

        // Función para obtener la única instancia del repositorio, creando un nuevo objeto si no existe.
        fun getInstance(gardenPlantingDao: GardenPlantingDao, ioDispatcher: CoroutineDispatcher) =
            instance ?: synchronized(this) {
                // Si no hay una instancia existente, crea una nueva de manera segura y asigna a la variable instance.
                instance ?: GardenPlantingRepository(gardenPlantingDao, ioDispatcher).also {
                    instance = it
                }
            }
    }
}
