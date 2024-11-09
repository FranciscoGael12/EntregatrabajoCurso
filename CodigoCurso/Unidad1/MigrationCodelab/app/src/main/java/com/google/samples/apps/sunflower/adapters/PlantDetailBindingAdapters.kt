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
// Importación de clases necesarias para el manejo de imágenes, textos, y adaptadores de enlace.
import android.text.method.LinkMovementMethod // Permite hacer clic en enlaces dentro de un TextView.
import android.widget.ImageView // Clase para mostrar imágenes en la interfaz de usuario.
import android.widget.TextView // Clase para mostrar textos en la interfaz de usuario.
import androidx.core.text.HtmlCompat // Clase para manejar el renderizado de texto HTML en TextViews.
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_COMPACT // Modo de compatibilidad para renderizar HTML de forma compacta.
import androidx.databinding.BindingAdapter // Anotación para métodos que se vinculan a propiedades de vistas en DataBinding.
import com.bumptech.glide.Glide // Librería para cargar imágenes de manera eficiente.
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions // Opciones para la transición de imágenes con Glide.
import com.google.android.material.floatingactionbutton.FloatingActionButton // Clase para botones flotantes.
import com.google.samples.apps.sunflower.R // Recursos de la aplicación, como strings y drawables.

@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, imageUrl: String?) {
    // Si la URL de la imagen no es nula ni vacía, se carga la imagen en el ImageView.
    if (!imageUrl.isNullOrEmpty()) {
        Glide.with(view.context) // Inicializa Glide con el contexto de la vista.
            .load(imageUrl) // Carga la imagen desde la URL proporcionada.
            .transition(DrawableTransitionOptions.withCrossFade()) // Aplica una transición de fundido al cargar la imagen.
            .into(view) // Establece la imagen cargada en el ImageView.
    }
}

@BindingAdapter("isGone")
fun bindIsGone(view: FloatingActionButton, isGone: Boolean?) {
    // Si el valor de 'isGone' es nulo o verdadero, oculta el botón flotante.
    if (isGone == null || isGone) {
        view.hide() // Oculta el botón flotante.
    } else {
        view.show() // Muestra el botón flotante.
    }
}

@BindingAdapter("renderHtml")
fun bindRenderHtml(view: TextView, description: String?) {
    // Si la descripción no es nula, convierte el HTML en texto y lo asigna al TextView.
    if (description != null) {
        // Convierte el texto HTML en texto legible para el TextView.
        view.text = HtmlCompat.fromHtml(description, FROM_HTML_MODE_COMPACT)
        // Permite que los enlaces dentro del texto HTML sean clickeables.
        view.movementMethod = LinkMovementMethod.getInstance()
    } else {
        // Si la descripción es nula, establece el texto vacío.
        view.text = ""
    }
}

@BindingAdapter("wateringText")
fun bindWateringText(textView: TextView, wateringInterval: Int) {
    // Obtiene los recursos de la aplicación.
    val resources = textView.context.resources
    // Obtiene la cadena de recursos pluralizada para el intervalo de riego.
    val quantityString = resources.getQuantityString(
        R.plurals.watering_needs_suffix, // Recurso pluralizado para las necesidades de riego.
        wateringInterval, // El número de veces que se debe regar.
        wateringInterval // Utiliza el valor del intervalo como cantidad.
    )
    // Establece el texto del TextView con el string pluralizado.
    textView.text = quantityString
}
