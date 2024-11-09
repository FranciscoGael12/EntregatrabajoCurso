/*
 * Copyright 2020 Google LLC
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

package com.google.samples.apps.sunflower.plantdetail
import android.text.method.LinkMovementMethod // Importa la clase que permite gestionar los enlaces en el texto.
import android.widget.TextView // Importa la clase TextView para mostrar texto en una vista tradicional de Android.
import androidx.compose.foundation.layout.Column // Importa la clase Column para organizar elementos de manera vertical.
import androidx.compose.foundation.layout.fillMaxWidth // Permite que el componente ocupe todo el ancho disponible.
import androidx.compose.foundation.layout.padding // Permite añadir márgenes internos a los elementos.
import androidx.compose.foundation.layout.wrapContentWidth // Hace que el contenido de un componente se ajuste a su tamaño.
import androidx.compose.material3.MaterialTheme // Permite acceder a los temas y estilos de la interfaz de usuario de Material Design 3.
import androidx.compose.material3.Surface // Superficie que envuelve a otros componentes para aplicar un fondo y otros efectos visuales.
import androidx.compose.material3.Text // Componente para mostrar texto en la UI.
import androidx.compose.runtime.Composable // Anotación para declarar funciones que componen la interfaz de usuario en Jetpack Compose.
import androidx.compose.runtime.getValue // Permite observar el valor de una variable con LiveData.
import androidx.compose.runtime.livedata.observeAsState // Observa los cambios en un objeto LiveData y refleja su estado en la UI.
import androidx.compose.runtime.remember // Permite recordar un valor entre recomposiciones.
import androidx.compose.ui.Alignment // Proporciona las opciones de alineación en un contenedor.
import androidx.compose.ui.ExperimentalComposeUiApi // Indica que se está utilizando una API experimental de Compose.
import androidx.compose.ui.Modifier // Permite modificar el comportamiento de los componentes.
import androidx.compose.ui.res.dimensionResource // Obtiene el valor de dimensiones definidas en los recursos.
import androidx.compose.ui.res.pluralStringResource // Obtiene un recurso de cadena plural basado en el número.
import androidx.compose.ui.res.stringResource // Obtiene una cadena desde los recursos.
import androidx.compose.ui.text.font.FontWeight // Define el peso de la fuente en el texto.
import androidx.compose.ui.tooling.preview.Preview // Permite visualizar una previsualización de los componentes en el editor.
import androidx.compose.ui.viewinterop.AndroidView // Permite integrar vistas tradicionales de Android en la jerarquía de Compose.
import androidx.core.text.HtmlCompat // Permite convertir texto en formato HTML a texto plano.
import com.google.samples.apps.sunflower.R // Importa los recursos de la aplicación, como cadenas y dimensiones.
import com.google.samples.apps.sunflower.data.Plant // Importa la clase Plant que representa una planta.
import com.google.samples.apps.sunflower.theme.SunflowerTheme // Aplica el tema de la aplicación a los componentes.
import com.google.samples.apps.sunflower.viewmodels.PlantDetailViewModel // Importa el ViewModel para gestionar el estado de la vista de detalle de la planta.
import android.content.res.Configuration // Permite acceder a la configuración de la UI, como el modo oscuro.

@Composable
fun PlantDetailDescription(plantDetailViewModel: PlantDetailViewModel) {
    // Observa los valores que provienen del LiveData<Plant> del ViewModel
    val plant by plantDetailViewModel.plant.observeAsState()

    // Si la planta no es nula, muestra el contenido de la planta
    plant?.let {
        PlantDetailContent(it)
    }
}

@Composable
fun PlantDetailContent(plant: Plant) {
    Surface {
        Column(Modifier.padding(dimensionResource(R.dimen.margin_normal))) {
            // Muestra el nombre de la planta
            PlantName(plant.name)
            // Muestra el intervalo de riego de la planta
            PlantWatering(plant.wateringInterval)
            // Muestra la descripción de la planta
            PlantDescription(plant.description)
        }
    }
}

@Composable
private fun PlantName(name: String) {
    Text(
        text = name, // Establece el nombre de la planta como texto
        style = MaterialTheme.typography.headlineMedium, // Aplica el estilo de encabezado mediano del tema Material
        modifier = Modifier
            .fillMaxWidth() // Hace que el texto ocupe todo el ancho disponible
            .padding(horizontal = dimensionResource(R.dimen.margin_small)) // Aplica un margen pequeño en los lados
            .wrapContentWidth(Alignment.CenterHorizontally) // Centra el texto horizontalmente
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun PlantWatering(wateringInterval: Int) {
    Column(Modifier.fillMaxWidth()) {
        // Define el modificador común para centrar y añadir márgenes a los textos
        val centerWithPaddingModifier = Modifier
            .padding(horizontal = dimensionResource(R.dimen.margin_small))
            .align(Alignment.CenterHorizontally)

        // Define el margen normal para los textos
        val normalPadding = dimensionResource(R.dimen.margin_normal)

        Text(
            text = stringResource(R.string.watering_needs_prefix), // Obtiene el texto del recurso de cadena
            color = MaterialTheme.colorScheme.primaryContainer, // Establece el color del texto
            fontWeight = FontWeight.Bold, // Aplica negrita al texto
            modifier = centerWithPaddingModifier.padding(top = normalPadding) // Aplica el modificador y margen superior
        )

        val wateringIntervalText = pluralStringResource(
            R.plurals.watering_needs_suffix, wateringInterval, wateringInterval
        ) // Obtiene el texto plural para las necesidades de riego según el intervalo

        Text(
            text = wateringIntervalText, // Muestra el intervalo de riego
            modifier = centerWithPaddingModifier.padding(bottom = normalPadding) // Aplica el modificador y margen inferior
        )
    }
}

@Composable
private fun PlantDescription(description: String) {
    // Recuerda la descripción en formato HTML para que no se reejecute cada vez que se recarga
    val htmlDescription = remember(description) {
        HtmlCompat.fromHtml(description, HtmlCompat.FROM_HTML_MODE_COMPACT)
    }

    // Muestra la descripción como texto HTML usando una vista tradicional de Android (TextView)
    AndroidView(
        factory = { context ->
            TextView(context).apply {
                movementMethod = LinkMovementMethod.getInstance() // Habilita el manejo de enlaces en el texto
            }
        },
        update = {
            it.text = htmlDescription // Actualiza el texto del TextView con la descripción HTML
        }
    )
}

@Preview
@Composable
private fun PlantDetailContentPreview() {
    val plant = Plant("id", "Apple", "HTML<br><br>description", 3, 30, "")
    SunflowerTheme {
        PlantDetailContent(plant) // Muestra una vista previa del contenido de la planta
    }
}

@Preview
@Composable
private fun PlantNamePreview() {
    SunflowerTheme {
        PlantName("Apple") // Muestra una vista previa del nombre de la planta
    }
}

@Preview
@Composable
private fun PlantWateringPreview() {
    SunflowerTheme {
        PlantWatering(7) // Muestra una vista previa del intervalo de riego
    }
}

@Preview
@Composable
private fun PlantDescriptionPreview() {
    SunflowerTheme {
        PlantDescription("HTML<br><br>description") // Muestra una vista previa de la descripción HTML
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PlantDetailContentDarkPreview() {
    val plant = Plant("id", "Apple", "HTML<br><br>description", 3, 30, "")
    SunflowerTheme {
        PlantDetailContent(plant) // Muestra una vista previa en modo oscuro
    }
}
