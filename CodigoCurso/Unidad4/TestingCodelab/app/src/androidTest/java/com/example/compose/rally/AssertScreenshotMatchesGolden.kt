/*
 * Copyright 2021 The Android Open Source Project
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

package com.example.compose.rally
import android.graphics.Bitmap // Importa la clase Bitmap de Android para trabajar con imágenes en formato bitmap
import android.graphics.BitmapFactory // Importa la clase BitmapFactory que se usa para decodificar imágenes a partir de un flujo de datos
import android.os.Build // Importa la clase Build para acceder a información sobre la versión del sistema operativo Android
import androidx.compose.ui.graphics.asAndroidBitmap // Extensión que convierte una imagen de Compose a un Bitmap de Android
import androidx.compose.ui.test.SemanticsNodeInteraction // Importa la clase SemanticsNodeInteraction para interactuar con nodos en pruebas de UI
import androidx.compose.ui.test.captureToImage // Importa la función captureToImage para capturar una imagen de un nodo de UI
import androidx.test.filters.SdkSuppress // Importa la anotación SdkSuppress para suprimir pruebas en versiones SDK inferiores a la especificada
import androidx.test.platform.app.InstrumentationRegistry // Importa InstrumentationRegistry para obtener el contexto de la aplicación durante las pruebas
import java.io.FileOutputStream // Importa la clase FileOutputStream para escribir datos a un archivo

/**
 * Simple on-device screenshot comparator that uses golden images present in
 * `androidTest/assets`. It's used to showcase the [AnimationClockTestRule] used in
 * [AnimatingCircleTests].
 *
 * Minimum SDK is O. Densities between devices must match.
 *
 * Screenshots are saved on device in `/data/data/{package}/files`.
 */
@SdkSuppress(minSdkVersion = Build.VERSION_CODES.O) // Anota la función para que solo se ejecute en dispositivos con SDK O o superior
fun assertScreenshotMatchesGolden( // Función principal que compara una captura de pantalla con una imagen dorada (referencia)
    goldenName: String, // Nombre de la imagen dorada a la que se compara
    node: SemanticsNodeInteraction // Nodo de UI que será capturado en una imagen
) {
    val bitmap = node.captureToImage().asAndroidBitmap() // Captura la imagen del nodo y la convierte en un Bitmap de Android

    // Save screenshot to file for debugging
    saveScreenshot(goldenName + System.currentTimeMillis().toString(), bitmap) // Guarda la captura de pantalla en el dispositivo para depuración
    val golden = InstrumentationRegistry.getInstrumentation() // Obtiene el contexto de la aplicación de prueba
        .context.resources.assets.open("$goldenName.png").use { BitmapFactory.decodeStream(it) } // Abre el archivo de la imagen dorada y lo decodifica en un Bitmap

    golden.compare(bitmap) // Compara el bitmap capturado con el bitmap de la imagen dorada
}

private fun saveScreenshot(filename: String, bmp: Bitmap) { // Función privada que guarda la captura de pantalla en el almacenamiento del dispositivo
    val path = InstrumentationRegistry.getInstrumentation().targetContext.filesDir.canonicalPath // Obtiene la ruta de directorio donde se guardará la captura de pantalla
    FileOutputStream("$path/$filename.png").use { out -> // Crea un flujo de salida de archivo para guardar la imagen
        bmp.compress(Bitmap.CompressFormat.PNG, 100, out) // Comprime el bitmap en formato PNG y lo guarda en el archivo
    }
    println("Saved screenshot to $path/$filename.png") // Imprime la ruta del archivo guardado para depuración
}

private fun Bitmap.compare(other: Bitmap) { // Función privada que compara dos bitmaps
    if (this.width != other.width || this.height != other.height) { // Verifica si los tamaños de las imágenes coinciden
        throw AssertionError("Size of screenshot does not match golden file (check device density)") // Lanza un error si las imágenes no tienen el mismo tamaño
    }
    // Compare row by row to save memory on device
    val row1 = IntArray(width) // Crea un arreglo para almacenar una fila de píxeles de la primera imagen
    val row2 = IntArray(width) // Crea un arreglo para almacenar una fila de píxeles de la segunda imagen
    for (column in 0 until height) { // Recorre cada fila de la imagen
        // Read one row per bitmap and compare
        this.getRow(row1, column) // Obtiene los píxeles de una fila de la primera imagen
        other.getRow(row2, column) // Obtiene los píxeles de una fila de la segunda imagen
        if (!row1.contentEquals(row2)) { // Compara las filas de píxeles
            throw AssertionError("Sizes match but bitmap content has differences") // Lanza un error si las filas de píxeles son diferentes
        }
    }
}

private fun Bitmap.getRow(pixels: IntArray, column: Int) { // Función privada que obtiene los píxeles de una fila de la imagen
    this.getPixels(pixels, 0, width, 0, column, width, 1) // Obtiene los píxeles de una fila específica y los almacena en un arreglo
}
