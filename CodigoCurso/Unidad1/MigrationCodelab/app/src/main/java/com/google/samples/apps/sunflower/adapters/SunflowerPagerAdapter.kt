/*
 * Copyright 2019 Google LLC
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
// Importación de clases necesarias para manejar Fragmentos y ViewPager2.
import androidx.fragment.app.Fragment // Clase base para los fragmentos en la interfaz de usuario.
import androidx.viewpager2.adapter.FragmentStateAdapter // Adaptador para ViewPager2 que maneja fragmentos.
import com.google.samples.apps.sunflower.GardenFragment // Fragmento que muestra el jardín.
import com.google.samples.apps.sunflower.PlantListFragment // Fragmento que muestra la lista de plantas.

const val MY_GARDEN_PAGE_INDEX = 0 // Índice de la página "Mi jardín" en el ViewPager.
const val PLANT_LIST_PAGE_INDEX = 1 // Índice de la página "Lista de plantas" en el ViewPager.

class SunflowerPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    // Adaptador que proporciona fragmentos para cada página del ViewPager.

    /**
     * Mapeo de los índices de las páginas del ViewPager a sus respectivos fragmentos.
     */
    private val tabFragmentsCreators: Map<Int, () -> Fragment> = mapOf(
        MY_GARDEN_PAGE_INDEX to { GardenFragment() }, // Asocia el índice 0 con el fragmento "GardenFragment".
        PLANT_LIST_PAGE_INDEX to { PlantListFragment() } // Asocia el índice 1 con el fragmento "PlantListFragment".
    )

    override fun getItemCount() = tabFragmentsCreators.size
    // Devuelve el número total de páginas en el ViewPager, que es el tamaño del mapa de fragmentos.

    override fun createFragment(position: Int): Fragment {
        // Crea el fragmento correspondiente al índice de la página.
        return tabFragmentsCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
        // Si el índice está en el mapa, devuelve el fragmento correspondiente.
        // Si no, lanza una excepción indicando que el índice está fuera de los límites.
    }
}
