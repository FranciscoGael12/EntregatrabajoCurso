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

package com.google.samples.apps.sunflower.adapters
// Importación de clases necesarias para la creación de vistas, manejo de navegación y actualización eficiente de listas.
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
// Importación de clases de navegación para manejar transiciones entre fragmentos.
import androidx.navigation.findNavController
// Importación de clases para comparar elementos en una lista y determinar si han cambiado.
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
// Importación de clases específicas del proyecto como los datos de las plantas y las vistas de los elementos.
import com.google.samples.apps.sunflower.HomeViewPagerFragmentDirections
import com.google.samples.apps.sunflower.PlantListFragment
import com.google.samples.apps.sunflower.data.Plant
import com.google.samples.apps.sunflower.databinding.ListItemPlantBinding

/**
 * Adaptador para el [RecyclerView] en [PlantListFragment].
 * Este adaptador gestiona la visualización de una lista de plantas y maneja la navegación hacia los detalles de una planta al hacer clic.
 */
class PlantAdapter : ListAdapter<Plant, RecyclerView.ViewHolder>(PlantDiffCallback()) {

    // Método que infla el layout para un ítem de la lista y crea el ViewHolder correspondiente.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PlantViewHolder(
            // Inflar el layout de un ítem de la lista utilizando DataBinding.
            ListItemPlantBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    // Método que vincula los datos del ítem en la posición dada al ViewHolder.
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val plant = getItem(position) // Obtiene el objeto de planta en la posición especificada.
        // Llama al método 'bind' del ViewHolder para establecer los datos.
        (holder as PlantViewHolder).bind(plant)
    }

    // Definición del ViewHolder que maneja el enlace de los datos a las vistas de los ítems.
    class PlantViewHolder(
        private val binding: ListItemPlantBinding // Vincula el objeto de DataBinding con el layout de un ítem.
    ) : RecyclerView.ViewHolder(binding.root) {

        // Inicialización del ViewHolder donde se configura un listener para detectar clics en el ítem.
        init {
            // Establece un listener de clics que navega hacia la pantalla de detalles de la planta al hacer clic.
            binding.setClickListener {
                binding.plant?.let { plant -> // Obtiene la planta del objeto de DataBinding.
                    navigateToPlant(plant, it) // Llama a la función de navegación con la planta.
                }
            }
        }

        // Método para realizar la navegación hacia el fragmento de detalles de la planta.
        private fun navigateToPlant(
            plant: Plant, // Planta que será visualizada en el detalle.
            view: View // Vista que fue clickeada.
        ) {
            val direction =
                // Crea la dirección de navegación a la pantalla de detalle de la planta utilizando NavController.
                HomeViewPagerFragmentDirections.actionViewPagerFragmentToPlantDetailFragment(
                    plant.plantId // Pasa el ID de la planta como argumento.
                )
            // Realiza la navegación hacia el destino utilizando NavController.
            view.findNavController().navigate(direction)
        }

        // Método que vincula los datos de la planta al ViewHolder.
        fun bind(item: Plant) {
            binding.apply {
                plant = item // Establece el objeto de planta en el DataBinding.
                executePendingBindings() // Ejecuta cualquier enlace pendiente de DataBinding.
            }
        }
    }
}

// Clase privada para comparar los elementos de la lista y optimizar su actualización utilizando DiffUtil.
private class PlantDiffCallback : DiffUtil.ItemCallback<Plant>() {

    // Método que compara los elementos para determinar si son el mismo ítem (por ID de planta).
    override fun areItemsTheSame(oldItem: Plant, newItem: Plant): Boolean {
        return oldItem.plantId == newItem.plantId // Compara por el ID de la planta.
    }

    // Método que compara el contenido de los elementos para ver si han cambiado.
    override fun areContentsTheSame(oldItem: Plant, newItem: Plant): Boolean {
        return oldItem == newItem // Compara los objetos completos de planta.
    }
}
