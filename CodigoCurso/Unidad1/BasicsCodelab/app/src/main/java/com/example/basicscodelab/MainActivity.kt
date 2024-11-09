// Este archivo pertenece al paquete com.example.basicscodelab
package com.example.basicscodelab

// Importa configuraciones de la UI y utilidades para manejar el ciclo de vida
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle

// Importa ComponentActivity para actividades de Jetpack Compose y setContent para establecer contenido en la UI
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

// Importa clases para animaciones, incluyendo `animateContentSize` para animar cambios de tamaño
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring

// Importa componentes de disposición y lista, incluyendo LazyColumn para listas eficientes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

// Importa íconos y utilidades para mostrar íconos en la UI
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore

// Importa componentes de Material3, como Button, Card y Surface para crear una UI con estilo Material
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text

// Importa funciones relacionadas con el estado de Compose y utilidades de composición
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

// Importa utilidades de la interfaz de usuario, incluyendo Alignment y unidades de medida como dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// Importa el tema personalizado de la aplicación
import com.example.basicscodelab.ui.theme.BasicsCodelabTheme

// Define la actividad principal MainActivity que hereda de ComponentActivity
class MainActivity : ComponentActivity() {
    // Sobrescribe el método onCreate para establecer el contenido de la UI
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Aplica el tema BasicsCodelabTheme y muestra MyApp como contenido principal
            BasicsCodelabTheme {
                MyApp(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

// Composable principal MyApp que controla si se muestra la pantalla de bienvenida
@Composable
fun MyApp(modifier: Modifier = Modifier) {
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }

    // Define Surface con un fondo basado en el tema. Muestra OnboardingScreen o Greetings
    Surface(modifier, color = MaterialTheme.colorScheme.background) {
        if (shouldShowOnboarding) {
            OnboardingScreen(onContinueClicked = { shouldShowOnboarding = false })
        } else {
            Greetings()
        }
    }
}

// Composable que muestra la pantalla de bienvenida
@Composable
fun OnboardingScreen(
    onContinueClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome to the Basics Codelab!")
        // Botón que ejecuta onContinueClicked para ocultar la pantalla de bienvenida
        Button(
            modifier = Modifier.padding(vertical = 24.dp),
            onClick = onContinueClicked
        ) {
            Text("Continue")
        }
    }
}

// Composable que genera una lista de saludos usando LazyColumn
@Composable
private fun Greetings(
    modifier: Modifier = Modifier,
    names: List<String> = List(1000) { "$it" }
) {
    LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
        // Para cada nombre en la lista, se genera un Greeting
        items(items = names) { name ->
            Greeting(name = name)
        }
    }
}

// Composable que muestra una tarjeta con el nombre y una opción para expandir contenido
@Composable
private fun Greeting(name: String, modifier: Modifier = Modifier) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        modifier = modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        CardContent(name)
    }
}

// Composable que controla el contenido de la tarjeta con un botón para expandir
@Composable
private fun CardContent(name: String) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .padding(12.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        // Define una columna con texto "Hello, " y el nombre
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(12.dp)
        ) {
            Text(text = "Hello, ")
            Text(
                text = name, style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.ExtraBold
                )
            )
            if (expanded) {
                Text(
                    text = ("Composem ipsum color sit lazy, " +
                            "padding theme elit, sed do bouncy. ").repeat(4),
                )
            }
        }
        // Botón de ícono que alterna el estado expandido para mostrar más o menos texto
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = if (expanded) Filled.ExpandLess else Filled.ExpandMore,
                contentDescription = if (expanded) "Show less" else "Show more"
            )
        }
    }
}

// Vista previa de Greetings en modo oscuro y claro
@Preview(
    showBackground = true,
    widthDp = 320,
    uiMode = UI_MODE_NIGHT_YES,
    name = "GreetingPreviewDark"
)
@Preview(showBackground = true, widthDp = 320)
@Composable
fun GreetingPreview() {
    BasicsCodelabTheme {
        Greetings()
    }
}

// Vista previa de la pantalla de bienvenida
@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnboardingPreview() {
    BasicsCodelabTheme {
        OnboardingScreen(onContinueClicked = {})
    }
}

// Vista previa de MyApp en pantalla completa
@Preview
@Composable
fun MyAppPreview() {
    BasicsCodelabTheme {
        MyApp(Modifier.fillMaxSize())
    }
}






