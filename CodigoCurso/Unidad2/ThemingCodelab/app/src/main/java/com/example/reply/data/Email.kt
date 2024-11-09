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

package com.example.reply.data
import androidx.annotation.DrawableRes

/**
 * Una clase de datos simple para representar un Email.
 */
data class Email(
    // Identificador único para el email
    val id: Long,
    // Cuenta que envió el email
    val sender: Account,
    // Lista de cuentas de destinatarios del email, por defecto es una lista vacía
    val recipients: List<Account> = emptyList(),
    // Asunto del email
    val subject: String,
    // Contenido del cuerpo del email
    val body: String,
    // Lista de archivos adjuntos en el email, por defecto es una lista vacía
    val attachments: List<EmailAttachment> = emptyList(),
    // Indicador de si el email está marcado como importante
    var isImportant: Boolean = false,
    // Indicador de si el email está marcado con estrella
    var isStarred: Boolean = false,
    // Tipo de buzón en el que se almacena el email (e.g., INBOX, SENT)
    var mailbox: MailboxType = MailboxType.INBOX,
    // Fecha y hora de creación del email
    val createdAt: String,
    // Lista de emails en el mismo hilo de conversación, por defecto es una lista vacía
    val threads: List<Email> = emptyList()
)

// Enumeración que define los diferentes tipos de buzones para clasificar emails
enum class MailboxType {
    INBOX, DRAFTS, SENT, SPAM, TRASH
}

// Clase de datos que representa un archivo adjunto en un email
data class EmailAttachment(
    // ID de recurso para el icono o imagen del adjunto
    @DrawableRes val resId: Int,
    // Descripción del contenido del archivo adjunto
    val contentDesc: String
)
