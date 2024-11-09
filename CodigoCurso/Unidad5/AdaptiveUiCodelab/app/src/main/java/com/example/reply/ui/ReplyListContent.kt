/*
 * Copyright 2022 Google LLC
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

import androidx.compose.foundation.Image // Importa el componente Image para mostrar imágenes en la interfaz.
import androidx.compose.foundation.background // Importa la función background para aplicar un color de fondo a los componentes.
import androidx.compose.foundation.layout.Arrangement // Importa Arrangement para organizar los elementos en la fila o columna.
import androidx.compose.foundation.layout.Column // Importa Column para organizar los elementos verticalmente.
import androidx.compose.foundation.layout.Row // Importa Row para organizar los elementos horizontalmente.
import androidx.compose.foundation.layout.fillMaxWidth // Importa la función fillMaxWidth para que los elementos llenen el ancho disponible.
import androidx.compose.foundation.layout.padding // Importa la función padding para agregar márgenes a los componentes.
import androidx.compose.foundation.layout.size // Importa la función size para definir el tamaño de los componentes.
import androidx.compose.foundation.lazy.LazyColumn // Importa LazyColumn para crear listas que cargan elementos de manera perezosa.
import androidx.compose.foundation.lazy.items // Importa items para generar un listado a partir de una lista de elementos.
import androidx.compose.foundation.shape.CircleShape // Importa CircleShape para crear formas circulares.
import androidx.compose.material.icons.Icons // Importa la biblioteca de iconos predeterminados de Material Design.
import androidx.compose.material.icons.filled.Search // Importa el icono de búsqueda.
import androidx.compose.material.icons.filled.StarBorder // Importa el icono de estrella vacío.
import androidx.compose.material3.Button // Importa el componente Button de la librería Material 3.
import androidx.compose.material3.ButtonDefaults // Importa ButtonDefaults para modificar las propiedades predeterminadas del botón.
import androidx.compose.material3.Card // Importa el componente Card para crear una tarjeta con sombra.
import androidx.compose.material3.CardDefaults // Importa CardDefaults para modificar las propiedades predeterminadas de la tarjeta.
import androidx.compose.material3.Icon // Importa el componente Icon para mostrar iconos.
import androidx.compose.material3.IconButton // Importa el componente IconButton para crear un botón con un icono.
import androidx.compose.material3.MaterialTheme // Importa MaterialTheme para acceder al tema visual.
import androidx.compose.material3.Text // Importa el componente Text para mostrar texto en la interfaz.
import androidx.compose.runtime.Composable // Importa la anotación Composable para definir funciones que se usan en la UI de Compose.
import androidx.compose.ui.Alignment // Importa Alignment para definir la alineación de los elementos en un contenedor.
import androidx.compose.ui.Modifier // Importa Modifier para aplicar modificadores a los componentes (tamaño, relleno, etc.).
import androidx.compose.ui.draw.clip // Importa clip para recortar los componentes con una forma determinada.
import androidx.compose.ui.res.painterResource // Importa painterResource para cargar imágenes desde recursos.
import androidx.compose.ui.res.stringResource // Importa stringResource para cargar cadenas de texto desde los recursos.
import androidx.compose.ui.text.style.TextOverflow // Importa TextOverflow para manejar el desbordamiento de texto.
import androidx.compose.ui.unit.dp // Importa dp para definir unidades de medida en densidad independiente de píxeles.
import com.example.reply.R // Importa el archivo de recursos de la aplicación.
import com.example.reply.data.Email // Importa el modelo de datos Email que representa un correo electrónico.

@Composable
fun ReplyListPane( // Define la función ReplyListPane como una función composable para crear una lista de correos electrónicos.
    replyHomeUIState: ReplyHomeUIState, // Recibe el estado de la UI que contiene los correos electrónicos y otro estado relevante.
    onEmailClick: (Email) -> Unit, // Recibe una función que maneja el clic sobre un correo electrónico.
    modifier: Modifier = Modifier // Recibe un modificador para personalizar el diseño del componente.
) {
    LazyColumn(modifier = modifier.fillMaxWidth()) { // Crea una LazyColumn que llena el ancho disponible y despliega los elementos de manera perezosa.
        item { // Crea un ítem único, que es la barra de búsqueda en la parte superior.
            ReplySearchBar(modifier = Modifier.fillMaxWidth()) // Llama a la función ReplySearchBar para mostrar una barra de búsqueda.
        }
        items(replyHomeUIState.emails) { email -> // Itera sobre la lista de correos electrónicos y genera un ítem para cada uno.
            ReplyEmailListItem( // Llama a la función ReplyEmailListItem para mostrar cada correo en la lista.
                email = email, // Pasa el correo a la función.
                onEmailClick = onEmailClick // Pasa la función onEmailClick para manejar los clics.
            )
        }
    }
}

@Composable
fun ReplyDetailPane( // Define la función ReplyDetailPane para mostrar los detalles de un correo electrónico seleccionado.
    email: Email, // Recibe el correo electrónico seleccionado.
    modifier: Modifier = Modifier // Recibe un modificador para personalizar el diseño.
) {
    LazyColumn(modifier = modifier.fillMaxWidth()) { // Crea una LazyColumn para mostrar el hilo de respuestas del correo electrónico.
        item { // Crea un ítem único para mostrar el correo principal en el hilo.
            ReplyEmailThreadItem(email) // Llama a la función ReplyEmailThreadItem para mostrar el correo principal.
        }
        items(email.replies) { reply -> // Itera sobre las respuestas al correo y las muestra.
            ReplyEmailThreadItem(reply) // Llama a la función ReplyEmailThreadItem para mostrar cada respuesta.
        }
    }
}

@Composable
fun ReplyEmailListItem( // Define la función ReplyEmailListItem para mostrar un ítem de correo electrónico en la lista.
    email: Email, // Recibe el correo electrónico a mostrar.
    onEmailClick: (Email) -> Unit, // Recibe la función onEmailClick para manejar el clic en el correo.
    modifier: Modifier = Modifier // Recibe un modificador para personalizar el diseño del ítem.
) {
    Card( // Crea una tarjeta que contiene el correo electrónico.
        onClick = { onEmailClick(email) }, // Define la acción a realizar al hacer clic en el correo.
        modifier = modifier.padding(horizontal = 16.dp, vertical = 4.dp) // Aplica márgenes a la tarjeta.
    ) {
        Column( // Crea una columna que organiza los elementos dentro de la tarjeta de manera vertical.
            modifier = Modifier
                .fillMaxWidth() // Asegura que la columna llene todo el ancho disponible.
                .padding(20.dp) // Aplica relleno interno a la columna.
        ) {
            Row(modifier = Modifier.fillMaxWidth()) { // Crea una fila para mostrar la imagen del perfil del remitente y la información del correo.
                ReplyProfileImage( // Muestra la imagen de perfil del remitente.
                    drawableResource = email.sender.avatar, // Usa el recurso de imagen del avatar del remitente.
                    description = email.sender.fullName, // Proporciona una descripción de la imagen.
                )
                Column( // Crea una columna para mostrar el nombre y la fecha del correo.
                    modifier = Modifier
                        .weight(1f) // Hace que la columna ocupe el espacio restante disponible.
                        .padding(horizontal = 12.dp, vertical = 4.dp), // Aplica relleno a la columna.
                    verticalArrangement = Arrangement.Center // Alinea verticalmente los elementos al centro.
                ) {
                    Text( // Muestra el nombre del remitente.
                        text = email.sender.firstName,
                        style = MaterialTheme.typography.labelMedium
                    )
                    Text( // Muestra la fecha de creación del correo.
                        text = email.createAt,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
                IconButton( // Crea un botón con un icono para marcar el correo como favorito.
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .clip(CircleShape) // Aplica un recorte circular al icono.
                        .background(MaterialTheme.colorScheme.surface) // Establece el fondo del icono.
                ) {
                    Icon( // Muestra un icono de estrella vacío.
                        imageVector = Icons.Default.StarBorder,
                        contentDescription = "Favorite", // Descripción del icono.
                        tint = MaterialTheme.colorScheme.outline // Establece el color del icono.
                    )
                }
            }

            Text( // Muestra el asunto del correo electrónico.
                text = email.subject,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(top = 12.dp, bottom = 8.dp),
            )
            Text( // Muestra el cuerpo del correo electrónico.
                text = email.body,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2, // Limita el texto a dos líneas.
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                overflow = TextOverflow.Ellipsis // Muestra puntos suspensivos si el texto es demasiado largo.
            )
        }
    }
}

// Similar logic continues for the other components like ReplyEmailThreadItem, ReplyProfileImage, ReplySearchBar, etc.
@Composable
fun ReplyEmailThreadItem(
    email: Email,
    modifier: Modifier = Modifier
) {
    // Crea una tarjeta (Card) que contiene los detalles de un correo electrónico en un hilo de conversación.
    Card(
        modifier = modifier.padding(horizontal = 16.dp, vertical = 4.dp), // Aplica relleno (padding) alrededor de la tarjeta.
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface) // Establece el color de fondo de la tarjeta.
    ) {
        // Sección que organiza el contenido en una columna dentro de la tarjeta.
        Column(
            modifier = Modifier
                .fillMaxWidth() // Hace que la columna ocupe todo el ancho disponible.
                .padding(20.dp) // Agrega un relleno de 20dp dentro de la columna.
        ) {
            // Fila que contiene la imagen del perfil del remitente y su información.
            Row(modifier = Modifier.fillMaxWidth()) {
                ReplyProfileImage(
                    drawableResource = email.sender.avatar, // Carga la imagen del avatar del remitente.
                    description = email.sender.fullName, // Descripción de la imagen para accesibilidad.
                )
                // Columna que contiene el nombre del remitente y el tiempo transcurrido desde el envío.
                Column(
                    modifier = Modifier
                        .weight(1f) // Hace que la columna ocupe todo el espacio restante.
                        .padding(horizontal = 12.dp, vertical = 4.dp), // Aplica relleno a la columna.
                    verticalArrangement = Arrangement.Center // Centra el contenido verticalmente.
                ) {
                    // Muestra el primer nombre del remitente.
                    Text(
                        text = email.sender.firstName,
                        style = MaterialTheme.typography.labelMedium // Establece el estilo de texto.
                    )
                    // Muestra el tiempo transcurrido desde el envío del correo.
                    Text(
                        text = "20 mins ago",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.outline // Establece el color del texto.
                    )
                }
                // Botón de icono que permite marcar el correo como favorito (aunque está incompleto).
                IconButton(
                    onClick = { /*TODO*/ }, // Acción a realizar cuando se hace clic (pendiente de implementar).
                    modifier = Modifier
                        .clip(CircleShape) // Aplica una forma circular al botón.
                        .background(MaterialTheme.colorScheme.surface) // Establece el color de fondo del botón.
                ) {
                    Icon(
                        imageVector = Icons.Default.StarBorder, // Usa el ícono de estrella vacía.
                        contentDescription = "Favorite", // Descripción del ícono.
                        tint = MaterialTheme.colorScheme.outline // Establece el color del ícono.
                    )
                }
            }

            // Muestra el asunto del correo.
            Text(
                text = email.subject,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.outline,
                modifier = Modifier.padding(top = 12.dp, bottom = 8.dp), // Aplica relleno arriba y abajo.
            )

            // Muestra el cuerpo del correo con un límite de líneas para evitar desbordamiento.
            Text(
                text = email.body,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            // Fila que contiene los botones de respuesta.
            Row(
                modifier = Modifier
                    .fillMaxWidth() // Hace que la fila ocupe todo el ancho disponible.
                    .padding(vertical = 20.dp), // Agrega relleno vertical.
                horizontalArrangement = Arrangement.spacedBy(4.dp), // Separa los botones por 4dp.
            ) {
                // Botón para responder al correo.
                Button(
                    onClick = { /*TODO*/ }, // Acción pendiente.
                    modifier = Modifier.weight(1f), // Hace que el botón ocupe todo el espacio disponible.
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.inverseOnSurface) // Establece el color de fondo.
                ) {
                    Text(
                        text = stringResource(id = R.string.reply), // Muestra el texto de "Reply".
                        color = MaterialTheme.colorScheme.onSurfaceVariant // Establece el color del texto.
                    )
                }
                // Botón para responder a todos los participantes del correo.
                Button(
                    onClick = { /*TODO*/ }, // Acción pendiente.
                    modifier = Modifier.weight(1f), // Hace que el botón ocupe todo el espacio disponible.
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.inverseOnSurface) // Establece el color de fondo.
                ) {
                    Text(
                        text = stringResource(id = R.string.reply_all), // Muestra el texto de "Reply All".
                        color = MaterialTheme.colorScheme.onSurfaceVariant // Establece el color del texto.
                    )
                }
            }
        }
    }
}

@Composable
fun ReplyProfileImage(
    drawableResource: Int,
    description: String,
    modifier: Modifier = Modifier,
) {
    // Crea una imagen circular de perfil con el recurso especificado.
    Image(
        modifier = modifier
            .size(40.dp) // Establece el tamaño de la imagen.
            .clip(CircleShape), // Recorta la imagen en forma circular.
        painter = painterResource(id = drawableResource), // Carga la imagen usando el recurso proporcionado.
        contentDescription = description, // Descripción de la imagen para accesibilidad.
    )
}

@Composable
fun ReplySearchBar(modifier: Modifier = Modifier) {
    // Crea una barra de búsqueda para filtrar los correos electrónicos.
    Row(
        modifier = modifier
            .fillMaxWidth() // Hace que la fila ocupe todo el ancho disponible.
            .padding(16.dp) // Agrega un relleno de 16dp alrededor de la fila.
            .background(MaterialTheme.colorScheme.surface, CircleShape), // Aplica un fondo con forma circular.
        verticalAlignment = Alignment.CenterVertically // Centra verticalmente los elementos en la fila.
    ) {
        // Icono de búsqueda (lupa).
        Icon(
            imageVector = Icons.Default.Search, // Usa el ícono de búsqueda predeterminado.
            contentDescription = stringResource(id = R.string.search), // Descripción para accesibilidad.
            modifier = Modifier.padding(start = 16.dp), // Aplica relleno a la izquierda.
            tint = MaterialTheme.colorScheme.outline // Establece el color del ícono.
        )
        // Texto de la barra de búsqueda.
        Text(
            text = stringResource(id = R.string.search_replies), // Muestra el texto de "Search Replies".
            modifier = Modifier
                .weight(1f) // Hace que el texto ocupe el espacio disponible.
                .padding(16.dp), // Aplica relleno de 16dp.
            style = MaterialTheme.typography.bodyMedium, // Establece el estilo del texto.
            color = MaterialTheme.colorScheme.outline // Establece el color del texto.
        )
        // Imagen del perfil del usuario.
        ReplyProfileImage(
            drawableResource = R.drawable.avatar_6, // Recurso de la imagen del avatar.
            description = stringResource(id = R.string.profile), // Descripción de la imagen para accesibilidad.
            modifier = Modifier
                .padding(12.dp) // Aplica un relleno de 12dp alrededor de la imagen.
                .size(32.dp) // Establece el tamaño de la imagen.
        )
    }
}
