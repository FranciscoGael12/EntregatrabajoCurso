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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.reply.R
import com.example.reply.data.Email

// Composable para mostrar un ítem de correo en un hilo de conversaciones
@Composable
fun ReplyEmailThreadItem(
    email: Email, // Datos del correo
    modifier: Modifier = Modifier // Modificador opcional
) {
    // Columna que contiene el diseño completo del ítem del correo
    Column(
        modifier = modifier
            .fillMaxWidth() // La columna ocupa el ancho completo
            .padding(8.dp) // Margen alrededor del ítem
            .background(
                MaterialTheme.colorScheme.background, // Fondo según el tema actual
                MaterialTheme.shapes.medium // Bordes redondeados
            )
            .padding(20.dp) // Espaciado interno del contenido
    ) {
        // Fila que muestra la imagen de perfil, nombre y botón de estrella
        Row(modifier = Modifier.fillMaxWidth()) {
            // Imagen de perfil del remitente del correo
            ReplyProfileImage(
                drawableResource = email.sender.avatar, // Recurso de imagen del remitente
                description = email.sender.fullName, // Descripción accesible del perfil
            )
            // Columna con el nombre y tiempo transcurrido desde el envío
            Column(
                modifier = Modifier
                    .weight(1f) // Ocupa el espacio restante en la fila
                    .padding(horizontal = 12.dp, vertical = 4.dp), // Espaciado interno
                verticalArrangement = Arrangement.Center // Centra el contenido verticalmente
            ) {
                Text(
                    text = email.sender.firstName, // Nombre del remitente
                )
                Text(
                    text = stringResource(id = R.string.twenty_mins_ago), // Tiempo de envío
                )
            }
            // Botón de estrella para marcar o desmarcar el correo como favorito
            IconButton(
                onClick = { /*Implementación de clic*/ },
                modifier = Modifier
                    .clip(CircleShape) // Forma circular del botón
            ) {
                Icon(
                    imageVector = if (email.isStarred) Icons.Default.Star else Icons.Default.StarBorder, // Icono de estrella lleno o vacío
                    contentDescription = stringResource(id = R.string.description_favorite), // Descripción accesible
                    tint = if (email.isStarred) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.outline // Color según estado
                )
            }
        }

        // Nombre del remitente del correo
        Text(
            text = email.sender.firstName,
            style = MaterialTheme.typography.labelMedium // Estilo de texto para el nombre
        )

        // Tiempo transcurrido desde el envío del correo
        Text(
            text = stringResource(id = R.string.twenty_mins_ago),
            style = MaterialTheme.typography.labelMedium // Estilo de texto para la fecha
        )

        // Asunto del correo
        Text(
            text = email.subject,
            style = MaterialTheme.typography.bodyMedium, // Estilo de texto para el asunto
            modifier = Modifier.padding(top = 12.dp, bottom = 8.dp), // Espaciado superior e inferior
        )

        // Cuerpo del correo
        Text(
            text = email.body,
            style = MaterialTheme.typography.bodyLarge, // Estilo de texto para el cuerpo del mensaje
            color = MaterialTheme.colorScheme.onSurfaceVariant // Color del texto en el tema
        )

        // Fila con botones para responder y responder a todos
        Row(
            modifier = Modifier
                .fillMaxWidth() // La fila ocupa el ancho completo
                .padding(vertical = 20.dp), // Margen vertical
            horizontalArrangement = Arrangement.spacedBy(4.dp), // Espaciado entre botones
        ) {
            // Botón para responder al correo
            Button(
                onClick = { /*Implementación de clic*/ },
                modifier = Modifier.weight(1f), // Ocupa el 50% del ancho disponible
            ) {
                Text(
                    text = stringResource(id = R.string.reply), // Texto del botón "Responder"
                )
            }
            // Botón para responder a todos los destinatarios del correo
            Button(
                onClick = { /*Implementación de clic*/ },
                modifier = Modifier.weight(1f), // Ocupa el 50% del ancho disponible
            ) {
                Text(
                    text = stringResource(id = R.string.reply_all), // Texto del botón "Responder a todos"
                )
            }
        }
    }
}
