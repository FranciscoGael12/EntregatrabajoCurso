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
// Importación de clases necesarias para manejar la vista, inflar layouts y realizar la navegación en Android.
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
// Importación de clases de DataBinding para enlazar datos con vistas de manera declarativa.
import androidx.databinding.DataBindingUtil
// Importación de clases para la navegación entre fragmentos en Android.
import androidx.navigation.findNavController
// Importación de clases necesarias para comparar elementos en una lista y determinar si han cambiado.
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
// Importación de clases específicas del proyecto como los datos de las plantas y las vistas de los elementos.
import com.google.samples.apps.sunflower.HomeViewPagerFragmentDirections
import com.google.samples.apps.sunflower.R
import com.google.samples.apps.sunflower.data.PlantAndGardenPlantings
import com.google.samples.apps.sunflower.databinding.ListItemGardenPlantingBinding
import com.google.samples.apps.sunflower.viewmodels.PlantAndGardenPlantingsViewModel

// Definición de la clase adaptadora para el RecyclerView que maneja la lista de plantaciones en el jardín.
class GardenPlantingAdapter :
    ListAdapter<PlantAndGardenPlantings, GardenPlantingAdapter.ViewHolder>( // Utiliza ListAdapter con una clase ViewHolder y un DiffUtil para optimizar la actualización de la lista.
        GardenPlantDiffCallback()
    ) {

    // Método que infla el layout para un ítem de la lista y crea el ViewHolder correspondiente.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            // Inflar el layout de un item de la lista utilizando DataBinding.
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_item_garden_planting, // Layout XML para un ítem.
                parent,
                false
            )
        )
    }

    // Método que vincula los datos del item en la posición dada al ViewHolder.
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)) // Llama a la función 'bind' para enlazar los datos del item con el ViewHolder.
    }

    // Definición de la clase ViewHolder que maneja el enlace de los datos a las vistas.
    class ViewHolder(
        private val binding: ListItemGardenPlantingBinding // Vincula el objeto de DataBinding con el layout de un ítem.
    ) : RecyclerView.ViewHolder(binding.root) {

        // Inicialización del ViewHolder donde se configura un listener para detectar clics en el ítem.
        init {
            // Establece un click listener que navega hacia una pantalla de detalles de la planta al hacer clic.
            binding.setClickListener { view ->
                binding.viewModel?.plantId?.let { plantId -> // Obtiene el ID de la planta desde el ViewModel.
                    navigateToPlant(plantId, view) // Llama a la función de navegación con el ID de la planta.
                }
            }
        }

        // Método para realizar la navegación hacia el fragmento de detalles de la planta.
        private fun navigateToPlant(plantId: String, view: View) {
            val direction = HomeViewPagerFragmentDirections
                .actionViewPagerFragmentToPlantDetailFragment(plantId) // Genera la acción de navegación.
            view.findNavController().navigate(direction) // Realiza la navegación utilizando NavController.
        }

        // Método que vincula los datos de la plantación al ViewHolder.
        fun bind(plantings: PlantAndGardenPlantings) {
            with(binding) {
                // Establece el ViewModel para el ítem y lo vincula al layout.
                viewModel = PlantAndGardenPlantingsViewModel(plantings)
                executePendingBindings() // Ejecuta las vinculaciones pendientes de DataBinding.
            }
        }
    }
}

// Clase privada para comparar los elementos de la lista y optimizar su actualización usando DiffUtil.
private class GardenPlantDiffCallback : DiffUtil.ItemCallback<PlantAndGardenPlantings>() {

    // Método que compara los elementos para determinar si son el mismo ítem (por ID).
    override fun areItemsTheSame(
        oldItem: PlantAndGardenPlantings,
        newItem: PlantAndGardenPlantings
    ): Boolean {
        return oldItem.plant.plantId == newItem.plant.plantId // Compara por el ID de la planta.
    }

    // Método que compara el contenido de los elementos para ver si han cambiado.
    override fun areContentsTheSame(
        oldItem: PlantAndGardenPlantings,
        newItem: PlantAndGardenPlantings
    ): Boolean {
        return oldItem.plant == newItem.plant // Compara los objetos completos de planta.
    }
}
