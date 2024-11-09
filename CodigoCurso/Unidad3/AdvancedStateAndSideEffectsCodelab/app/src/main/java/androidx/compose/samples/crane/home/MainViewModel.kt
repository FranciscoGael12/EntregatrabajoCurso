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
import androidx.compose.samples.crane.data.DestinationsRepository // Importa el repositorio que proporciona datos de destinos turísticos
import androidx.compose.samples.crane.data.ExploreModel // Importa el modelo de datos que representa una entidad de exploración (como un destino o una propiedad)
import androidx.compose.samples.crane.di.DefaultDispatcher // Importa el despachador predeterminado para las corutinas
import androidx.lifecycle.ViewModel // Importa la clase base para los viewModels de arquitectura de Android
import androidx.lifecycle.viewModelScope // Importa el scope asociado con el ciclo de vida del ViewModel para manejar corutinas
import dagger.hilt.android.lifecycle.HiltViewModel // Anotación para indicar que este ViewModel debe ser inyectado por Hilt
import kotlinx.coroutines.CoroutineDispatcher // Importa la clase para gestionar despachadores en corutinas
import kotlinx.coroutines.flow.MutableStateFlow // Importa el flujo mutable para manejar estados que pueden ser actualizados
import kotlinx.coroutines.flow.StateFlow // Importa el flujo de estado inmutable, utilizado para exponer solo lectura de los estados
import kotlinx.coroutines.flow.asStateFlow // Convierte MutableStateFlow a StateFlow para la exposición segura del estado
import kotlinx.coroutines.launch // Importa la función que lanza corutinas
import kotlinx.coroutines.withContext // Permite cambiar el contexto de la corutina
import javax.inject.Inject // Importa la anotación de inyección de dependencias para Hilt
import kotlin.random.Random // Importa la clase Random para generar números aleatorios

const val MAX_PEOPLE = 4 // Define el máximo número de personas permitido en la búsqueda de destinos

@HiltViewModel // Anota esta clase como un ViewModel que es inyectado con dependencias a través de Hilt
class MainViewModel @Inject constructor(
    private val destinationsRepository: DestinationsRepository, // Inyecta el repositorio que proporciona datos sobre destinos
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher // Inyecta un despachador de corutinas personalizado
) : ViewModel() { // Hereda de ViewModel, lo que permite gestionar el ciclo de vida del UI de manera eficiente

    val hotels: List<ExploreModel> = destinationsRepository.hotels // Obtiene la lista de hoteles del repositorio
    val restaurants: List<ExploreModel> = destinationsRepository.restaurants // Obtiene la lista de restaurantes del repositorio

    private val _suggestedDestinations = MutableStateFlow<List<ExploreModel>>(emptyList()) // Crea un flujo mutable que contiene los destinos sugeridos
    val suggestedDestinations: StateFlow<List<ExploreModel>> = _suggestedDestinations.asStateFlow() // Expone los destinos sugeridos como un flujo inmutable

    init {
        _suggestedDestinations.value = destinationsRepository.destinations // Inicializa los destinos sugeridos con todos los destinos del repositorio
    }

    // Función para actualizar los destinos sugeridos basado en el número de personas
    fun updatePeople(people: Int) {
        viewModelScope.launch { // Lanza una corutina dentro del scope del ViewModel
            if (people > MAX_PEOPLE) { // Si el número de personas excede el máximo permitido
                _suggestedDestinations.value = emptyList() // Vacia la lista de destinos sugeridos
            } else {
                // Si el número de personas es válido, se actualiza la lista de destinos sugeridos de forma aleatoria
                val newDestinations = withContext(defaultDispatcher) { // Cambia el contexto a un despachador predeterminado
                    destinationsRepository.destinations // Obtiene la lista completa de destinos
                        .shuffled(Random(people * (1..100).shuffled().first())) // Aleatoriza la lista según el número de personas
                }
                _suggestedDestinations.value = newDestinations // Actualiza el estado de destinos sugeridos
            }
        }
    }

    // Función para filtrar los destinos sugeridos por el destino de destino
    fun toDestinationChanged(newDestination: String) {
        viewModelScope.launch { // Lanza una corutina dentro del scope del ViewModel
            val newDestinations = withContext(defaultDispatcher) { // Cambia el contexto a un despachador predeterminado
                destinationsRepository.destinations // Obtiene la lista completa de destinos
                    .filter { it.city.nameToDisplay.contains(newDestination) } // Filtra los destinos por el nombre de la ciudad
            }
            _suggestedDestinations.value = newDestinations // Actualiza el estado de destinos sugeridos con los destinos filtrados
        }
    }
}
