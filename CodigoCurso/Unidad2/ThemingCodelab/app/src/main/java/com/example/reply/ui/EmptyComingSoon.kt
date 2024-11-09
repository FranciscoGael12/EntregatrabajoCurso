/*
 * Copyright 2022 The Android Open Source Project
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

package com.example.reply.ui
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.reply.R

// Composable que muestra una pantalla vacía con un mensaje de "Próximamente"
@Composable
fun EmptyComingSoon(
    modifier: Modifier = Modifier // Modificador opcional para personalizar el diseño
) {
    Column(
        modifier = modifier.fillMaxSize(), // La columna ocupa todo el tamaño disponible
        verticalArrangement = Arrangement.Center, // Alinea los elementos verticalmente al centro
        horizontalAlignment = Alignment.CenterHorizontally // Alinea los elementos horizontalmente al centro
    ) {
        Text(
            modifier = Modifier.padding(8.dp), // Aplica un padding de 8dp alrededor del texto
            text = stringResource(id = R.string.empty_screen_title), // Título del mensaje (extraído de recursos)
            style = MaterialTheme.typography.titleLarge, // Aplica estilo de título grande
            textAlign = TextAlign.Center, // Alinea el texto al centro
            color = MaterialTheme.colorScheme.primary // Color principal del tema
        )
        Text(
            modifier = Modifier.padding(horizontal = 8.dp), // Padding horizontal de 8dp alrededor del texto
            text = stringResource(id = R.string.empty_screen_subtitle), // Subtítulo del mensaje (extraído de recursos)
            style = MaterialTheme.typography.bodySmall, // Aplica estilo de cuerpo pequeño
            textAlign = TextAlign.Center, // Alinea el texto al centro
            color = MaterialTheme.colorScheme.outline // Color de contorno del tema
        )
    }
}

// Vista previa del componente EmptyComingSoon
@Preview
@Composable
fun ComingSoonPreview() {
    EmptyComingSoon() // Llama al composable para visualizar la pantalla de "Próximamente"
}
