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
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.reply.R
import com.example.reply.data.Email

// Elemento de lista para mostrar un correo en la interfaz de usuario
@Composable
fun ReplyEmailListItem(
    email: Email, // Correo específico a mostrar
    isSelected: Boolean = false, // Indica si el elemento está seleccionado
    modifier: Modifier = Modifier,
    navigateToDetail: (Long) -> Unit // Navegación al detalle del correo
) {
    // Tarjeta para encapsular los detalles del correo
    Card(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 4.dp) // Espaciado en la tarjeta
            .semantics { selected = isSelected } // Indicador de selección para accesibilidad
            .clickable { navigateToDetail(email.id) }, // Acción al hacer clic en la tarjeta
        colors = CardDefaults.cardColors(
            containerColor = if (email.isImportant)
                MaterialTheme.colorScheme.secondaryContainer // Fondo diferente si el correo es importante
            else MaterialTheme.colorScheme.surfaceVariant // Fondo estándar para correos normales
        )
    ) {
        // Columna que organiza la estructura del contenido del correo
        Column(
            modifier = Modifier
                .fillMaxWidth() // Ocupa todo el ancho disponible
                .padding(20.dp) // Espaciado interno del contenido
        ) {
            // Fila con imagen de perfil, nombre y fecha de envío
            Row(modifier = Modifier.fillMaxWidth()) {
                // Imagen de perfil del remitente
                ReplyProfileImage(
                    drawableResource = email.sender.avatar, // Imagen de perfil del remitente
                    description = email.sender.fullName // Descripción accesible del perfil
                )
                // Columna con el nombre y la fecha de creación del correo
                Column(
                    modifier = Modifier
                        .weight(1f) // Ocupa el espacio restante en la fila
                        .padding(horizontal = 12.dp, vertical = 4.dp), // Espaciado alrededor del texto
                    verticalArrangement = Arrangement.Center // Centra verticalmente los elementos
                ) {
                    Text(
                        text = email.sender.firstName, // Nombre del remitente
                    )
                    Text(
                        text = email.createdAt, // Fecha de creación del correo
                    )
                }
                // Botón de ícono para marcar el correo como favorito
                IconButton(
                    onClick = { /*Implementación del clic*/ },
                    modifier = Modifier
                        .clip(CircleShape) // Forma circular para el botón
                ) {
                    // Ícono de estrella para marcar como favorito
                    Icon(
                        imageVector = Icons.Default.StarBorder,
                        contentDescription = stringResource(id = R.string.description_favorite),
                    )
                }
            }

            // Nombre del remitente del correo
            Text(
                text = email.sender.firstName,
                style = MaterialTheme.typography.labelMedium // Estilo de texto del remitente
            )

            // Fecha de envío del correo
            Text(
                text = email.createdAt,
                style = MaterialTheme.typography.labelMedium, // Estilo de texto de la fecha
                color = MaterialTheme.colorScheme.onSurfaceVariant // Color según el esquema de color
            )

            // Asunto del correo
            Text(
                text = email.subject,
                style = MaterialTheme.typography.titleLarge, // Estilo de texto del asunto
                modifier = Modifier.padding(top = 12.dp, bottom = 8.dp) // Espaciado alrededor del asunto
            )

            // Resumen del cuerpo del correo, limitado a dos líneas
            Text(
                text = email.body,
                maxLines = 2, // Limita a dos líneas el texto
                style = MaterialTheme.typography.bodyLarge, // Estilo del cuerpo de texto
                color = MaterialTheme.colorScheme.onSurfaceVariant, // Color del texto
                overflow = TextOverflow.Ellipsis // Muestra "..." si el texto es muy largo
            )
        }
    }
}
