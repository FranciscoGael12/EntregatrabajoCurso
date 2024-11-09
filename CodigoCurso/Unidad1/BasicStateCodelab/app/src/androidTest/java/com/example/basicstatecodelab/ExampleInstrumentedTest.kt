package com.example.basicstatecodelab

// Importación de la clase InstrumentationRegistry que permite obtener el contexto de la aplicación en pruebas de instrumentación.
import androidx.test.platform.app.InstrumentationRegistry
// Importación del runner AndroidJUnit4, que ejecuta las pruebas en un dispositivo Android.
import androidx.test.ext.junit.runners.AndroidJUnit4

// Importación de las anotaciones necesarias para definir las pruebas.
import org.junit.Test
import org.junit.runner.RunWith

// Importación de las funciones de aserción de JUnit para realizar validaciones en las pruebas.
import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

// Anotación que indica que esta clase debe ser ejecutada con el runner AndroidJUnit4, específico para pruebas en Android.
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    // Anotación que indica que esta función es una prueba que se debe ejecutar.
    @Test
    fun useAppContext() {
        // Obtener el contexto de la aplicación que está siendo probada.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        // Validar que el nombre del paquete de la aplicación sea el esperado.
        assertEquals("com.example.basicstatecodelab", appContext.packageName)
    }
}
