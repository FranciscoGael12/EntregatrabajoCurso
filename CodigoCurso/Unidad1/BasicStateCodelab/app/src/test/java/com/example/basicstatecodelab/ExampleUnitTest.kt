package com.example.basicstatecodelab

// Importación de la anotación Test de JUnit, que indica que la función será una prueba unitaria.
import org.junit.Test

// Importación de las funciones de aserción de JUnit para realizar validaciones en las pruebas.
import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

// Definición de la clase de prueba unitaria.
class ExampleUnitTest {

    // Anotación que indica que esta función es una prueba que se debe ejecutar.
    @Test
    fun addition_isCorrect() {
        // Verificación de que la suma de 2 + 2 es igual a 4.
        assertEquals(4, 2 + 2)
    }
}
