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
// Importación de la clase View, que representa una vista en la interfaz de usuario de Android.
import android.view.View
// Importación de la anotación BindingAdapter de DataBinding, que permite vincular propiedades de vistas con valores en el código.
import androidx.databinding.BindingAdapter

// Definición de la función 'bindIsGone' que es utilizada como un adaptador de enlace (BindingAdapter).
@BindingAdapter("isGone")
// Función que recibe una vista (View) y un valor booleano ('isGone') para determinar su visibilidad.
fun bindIsGone(view: View, isGone: Boolean) {
    // Se establece la visibilidad de la vista dependiendo del valor de 'isGone'.
    view.visibility = if (isGone) {
        // Si 'isGone' es true, se oculta la vista (View.GONE).
        View.GONE
    } else {
        // Si 'isGone' es false, se hace visible la vista (View.VISIBLE).
        View.VISIBLE
    }
}

