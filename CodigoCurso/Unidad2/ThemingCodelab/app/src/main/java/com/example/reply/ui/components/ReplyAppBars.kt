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

package com.example.reply.ui.components
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.reply.R
import com.example.reply.data.Email

// Barra de búsqueda personalizada para la aplicación
@Composable
fun ReplySearchBar(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth() // La fila ocupa el ancho completo del contenedor
            .padding(16.dp) // Espaciado alrededor de la barra
            .background(
                MaterialTheme.colorScheme.background, // Fondo según el esquema de colores
                CircleShape // Forma circular para la barra de búsqueda
            ),
        verticalAlignment = Alignment.CenterVertically // Centra los elementos verticalmente
    ) {
        // Ícono de búsqueda al inicio de la barra
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = stringResource(id = R.string.search),
            modifier = Modifier.padding(start = 16.dp) // Espaciado del ícono
        )
        // Texto de "buscar respuestas" en la barra
        Text(
            text = stringResource(id = R.string.search_replies),
            modifier = Modifier
                .weight(1f) // Usa el espacio restante en la fila
                .padding(16.dp), // Espaciado alrededor del texto
            style = MaterialTheme.typography.bodyMedium // Estilo del texto
        )
        // Imagen de perfil a la derecha de la barra de búsqueda
        ReplyProfileImage(
            drawableResource = R.drawable.avatar_6,
            description = stringResource(id = R.string.profile),
            modifier = Modifier
                .padding(12.dp) // Espaciado alrededor de la imagen de perfil
                .size(32.dp) // Tamaño de la imagen
        )
    }
}

// Barra de herramientas superior para el detalle de un email
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailDetailAppBar(
    email: Email, // Objeto de tipo Email con los detalles del correo
    isFullScreen: Boolean, // Indica si la vista es de pantalla completa
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit // Acción al presionar el botón de retroceso
) {
    TopAppBar(
        modifier = modifier,
        title = {
            // Columna para organizar el asunto y el número de mensajes
            Column(
                modifier = Modifier.fillMaxWidth(), // Ocupa todo el ancho
                horizontalAlignment = if (isFullScreen) Alignment.CenterHorizontally
                else Alignment.Start // Centrado en pantalla completa, alineado a la izquierda en vista normal
            ) {
                // Asunto del email
                Text(
                    text = email.subject,
                    style = MaterialTheme.typography.titleMedium, // Estilo de texto del título
                    color = MaterialTheme.colorScheme.onSurfaceVariant // Color de acuerdo con el tema
                )
                // Número de mensajes en el hilo de conversación
                Text(
                    modifier = Modifier.padding(top = 4.dp), // Espaciado superior
                    text = "${email.threads.size} ${stringResource(id = R.string.messages)}",
                    style = MaterialTheme.typography.labelMedium, // Estilo de texto
                    color = MaterialTheme.colorScheme.outline // Color de acuerdo con el esquema
                )
            }
        },
        // Ícono de navegación para retroceder
        navigationIcon = {
            if (isFullScreen) {
                FilledIconButton(
                    onClick = onBackPressed, // Acción al presionar el botón
                    modifier = Modifier.padding(8.dp), // Espaciado del botón
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = MaterialTheme.colorScheme.surface, // Color de fondo del botón
                        contentColor = MaterialTheme.colorScheme.onSurface // Color del ícono
                    )
                ) {
                    // Ícono de flecha hacia atrás dentro del botón
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(id = R.string.back_button),
                        modifier = Modifier.size(14.dp) // Tamaño del ícono
                    )
                }
            }
        },
        actions = {
            // Botón para más opciones en la barra de acciones
            IconButton(
                onClick = { /*Implementación de acción*/ },
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = stringResource(id = R.string.more_options_button),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant // Color del ícono según el tema
                )
            }
        }
    )
}
