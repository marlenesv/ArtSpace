package com.example.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artspace.ui.theme.ArtSpaceTheme

// Definimos una paleta de colores mágica de Cenicienta
val MagicBlueLight = Color(0xFFE3F2FD) // Azul cielo muy pálido
val MagicPinkLight = Color(0xFFFCE4EC) // Rosa pastel muy pálido
val CinderellaGold = Color(0xFFE6C56A) // Dorado suave mate
val CinderellaGlassBlue = Color(0xFFB3E5FC) // Azul un poco más intenso para acentos
val StorybookCream = Color(0xFFFFFDF7) // Color crema para las páginas
val DeepRoyalText = Color(0xFF455A64) // Azul grisáceo oscuro para el texto (más suave que el negro)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtSpaceTheme {
                // Contenedor principal con un DEGRADADO MÁGICO de fondo
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(MagicBlueLight, MagicPinkLight)
                            )
                        )
                ) {
                    CinderellaStoryApp()
                }
            }
        }
    }
}

@Composable
fun CinderellaStoryApp(modifier: Modifier = Modifier) {
    var currentPage by remember { mutableIntStateOf(0) }
    val totalPages = 17

    if (currentPage == 0) {
        CoverScreen(onStartReading = { currentPage = 1 })
    } else {
        StoryScreen(
            page = currentPage,
            totalPages = totalPages,
            onNext = { if (currentPage < totalPages) currentPage++ },
            onPrevious = { currentPage-- }
        )
    }
}

@Composable
fun CoverScreen(onStartReading: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Decoración superior sutil
        Text(text = "✧･ﾟ: *✧･ﾟ:*", color = CinderellaGold, fontSize = 20.sp)

        Text(
            text = "Colección clásica",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF90A4AE),
            letterSpacing = 3.sp,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        // Título principal en CURSIVA mágica
        Text(
            text = "La Cenicienta",
            fontSize = 48.sp, // Mucho más grande
            fontFamily = FontFamily.Cursive, // Fuente cursiva de cuento de hadas
            color = DeepRoyalText,
            textAlign = TextAlign.Center,
            style = TextStyle(
                shadow = androidx.compose.ui.graphics.Shadow(
                    color = MagicPinkLight,
                    blurRadius = 12f
                )
            )
        )

        Spacer(modifier = Modifier.height(40.dp))

        // Imagen de portada con marco precioso
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .shadow(
                    elevation = 24.dp,
                    shape = RoundedCornerShape(32.dp), // Esquinas muy redondeadas
                    spotColor = CinderellaGlassBlue // Sombra azulada brillante
                ),
            shape = RoundedCornerShape(32.dp),
            border = BorderStroke(2.dp, CinderellaGold), // Borde dorado
            color = Color.Transparent
        ) {
            Image(
                painter = painterResource(id = R.drawable.portada),
                contentDescription = "Portada mágica de Cenicienta",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(56.dp))

        // Botón de "Cristal"
        Button(
            onClick = onStartReading,
            colors = ButtonDefaults.buttonColors(
                containerColor = CinderellaGlassBlue,
                contentColor = DeepRoyalText
            ),
            shape = RoundedCornerShape(50), // Botón completamente redondo (píldora)
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .shadow(8.dp, RoundedCornerShape(50), spotColor = CinderellaGlassBlue),
            border = BorderStroke(1.dp, Color.White.copy(alpha = 0.5f))
        ) {
            Text(text = " ABRIR EL LIBRO ", fontSize = 16.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
        }
    }
}

@Composable
fun StoryScreen(page: Int, totalPages: Int, onNext: () -> Unit, onPrevious: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Área de la ilustración (Tipo espejo mágico)
        Box(
            modifier = Modifier
                .weight(1.1f) // Un poco más de espacio para la imagen
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            StoryImageDisplay(page)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Área de texto y controles (Dentro de una "hoja de libro")
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            color = StorybookCream.copy(alpha = 0.85f), // Crema semitransparente
            shape = RoundedCornerShape(24.dp),
            border = BorderStroke(1.dp, MagicPinkLight),
            shadowElevation = 4.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                StoryTextContent(page)
                StoryControls(page, totalPages, onNext, onPrevious)
            }
        }
    }
}

@Composable
fun StoryImageDisplay(page: Int) {
    val imageResource = when (page) {
        1 -> R.drawable.cenicienta1
        2 -> R.drawable.cenicienta2
        3 -> R.drawable.cenicienta3
        4 -> R.drawable.cenicienta4
        5 -> R.drawable.cenicienta5
        6 -> R.drawable.cenicienta6
        7 -> R.drawable.cenicienta7
        8 -> R.drawable.cenicienta8
        9 -> R.drawable.cenicienta9
        10 -> R.drawable.cenicienta10
        11 -> R.drawable.cenicienta11
        12 -> R.drawable.cenicienta12
        13 -> R.drawable.cenicienta13
        14 -> R.drawable.cenicienta14
        15 -> R.drawable.cenicienta15
        16 -> R.drawable.cenicienta16
        17 -> R.drawable.cenicienta17
        else -> R.drawable.cenicienta1
    }

    // Marco precioso para las páginas internas
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(350.dp)
            .shadow(
                elevation = 16.dp,
                shape = RoundedCornerShape(24.dp),
                spotColor = CinderellaGlassBlue
            ),
        color = StorybookCream,
        shape = RoundedCornerShape(24.dp),
        border = BorderStroke(1.5.dp, CinderellaGold.copy(alpha = 0.7f)) // Borde dorado más fino
    ) {
        Image(
            painter = painterResource(id = imageResource),
            contentDescription = "Ilustración de la página $page",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun StoryTextContent(page: Int) {
    val textContent = when (page) {
        1 -> "Hace muchos, muchos años, en un país muy lejano, vivía en un castillo un caballero y su preciosa hija, llamada Cenicienta. El padre de Cenicienta sintió que él necesitaba el amor de una mujer y al poco tiempo, se volvió a casar con una mujer viuda que tenía dos hijas."
        2 -> "Su madrastra estaba empeñada en dar todas las atenciones a sus propias hijas y al morir el padre, obligó a Cenicienta a trabajar como sirvienta en la casa. La recluyó en una pequeña habitación y los animalitos de la casa, se convirtieron en sus únicos amigos."
        3 -> "Un día, mientras Cenicienta barría los suelos del castillo, llamaron a la puerta. Era un mensajero de Palacio que llevaba una carta del Rey. Anunciaba la celebración de un baile de gala en honor al Príncipe e incluía una invitación para asistir."
        4 -> "Cuando Cenicienta mostró la invitación a su madrastra, sus hermanastras se rieron de ella.\n—No veo razón por la que no puedas ir —interrumpió su madrastra—. Siempre y cuando termines todo tu trabajo."
        5 -> "Cenicienta recuperó uno de los vestidos de gala de su madre, y pensó que con unos arreglos, quedaría perfecto para el baile. En ese momento, su madrastra la llamó.\nPero mientras ella terminaba, sus amigos los animales aprovecharon para preparar el vestido."
        6 -> "Cenicienta terminó su tarea y fue a vestirse. Al entrar vio el precioso vestido. Pero cuando sus hermanastras la vieron, se pusieron muy celosas y comenzaron a rasgar su vestido hasta dejarlo hecho jirones.\n\nLas tres salieron burlándose."
        7 -> "Cenicienta corrió al jardín y llorando sin parar se dejó caer. Cuando miró hacia arriba, encontró a una viejecita a su lado.\n\n—Soy tu Hada, deja las lágrimas, no puedes ir a un baile con esa cara —dijo la viejecita."
        8 -> "Con un movimiento de su varita mágica, el Hada Madrina transformó una calabaza en un hermoso carruaje, y vistió a Cenicienta con un maravilloso vestido y unos delicados zapatos de cristal. Pero la avisó de que debía volver a casa antes de dar las doce campanadas."
        9 -> "Al llegar al salón de baile, Cenicienta estaba muy nerviosa. Nada más verla el Príncipe, se quedó enamorado de ella y la invitó a bailar durante toda la noche."
        10 -> "Al bailar junto a él, Cenicienta sentía que estaba flotando en el aire. De repente, escuchó las primeras campanadas de medianoche.\n—¡Oh, debo irme! —, y corrió escaleras abajo. Ni siquiera se detuvo cuando perdió uno de sus zapatos."
        11 -> "Al regresar a casa y escuchar la última campanada, se encontró de nuevo vestida con el traje que sus hermanastras le habían roto. Mientras, en Palacio, el Príncipe declaró desolado que sólo se casaría con la mujer que calzara el zapato de cristal."
        12 -> "Por orden real, el Gran Duque comenzó la búsqueda. Todas las mujeres del reino debían probarse el zapato, hasta encontrar el amor verdadero del Príncipe. Pero la madrastra, encerró a Cenicienta en su habitación para que no pudiera salir."
        13 -> "Por fin, el Gran Duque llegó a casa de Cenicienta. Sus hermanastras estaban muy inquietas y una a una trataron de apretujar sus pies dentro del zapato. ¡Pero tenían unos pies enormes!"
        14 -> "Mientras tanto, los ratones se desesperaban por ayudar a Cenicienta. Lograron sacar la llave del bolsillo de la madrastra, y con cuidado consiguieron llegar a la puerta. Finalmente, alcanzaron la cerradura y pudieron liberar a su amiga."
        15 -> "Cuando Cenicienta apareció en el salón, el Gran Duque le cedió el asiento y llamó al zapatero real. La madrastra le puso la zancadilla y el delicado zapato se rompió en mil pedazos."
        16 -> "—¡Oh, no! —exclamó el Gran Duque.\n—Quizás esto pueda ayudar... —susurró Cenicienta.\nY buscando entre su ropa, sacó el otro zapato de cristal. Sus hermanastras miraban veían cómo su delicado pie encajaba perfectamente en el zapato."
        17 -> "Al poco tiempo, Cenicienta y el Príncipe se casaron, y vivieron felices para siempre. Sus fieles amigos fueron testigos de cómo un sueño puede convertirse en realidad."
        else -> ""
    }

    // Número de página decorativo
    Text(
        text = "- $page -",
        color = CinderellaGold,
        fontSize = 14.sp,
        modifier = Modifier.padding(bottom = 8.dp)
    )

    // Texto del cuento
    Text(
        text = textContent,
        fontSize = 17.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = FontFamily.Serif, // Mantenemos Serif para leer fácil, pero el color cambia
        color = DeepRoyalText,
        lineHeight = 26.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(horizontal = 8.dp)
    )
}

@Composable
fun StoryControls(page: Int, totalPages: Int, onNext: () -> Unit, onPrevious: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Botón Anterior (Estilo zapatilla delicada)
        OutlinedButton(
            onClick = onPrevious,
            modifier = Modifier
                .weight(1f)
                .height(48.dp),
            shape = RoundedCornerShape(50),
            border = BorderStroke(1.dp, CinderellaGold),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = DeepRoyalText,
                containerColor = Color.White.copy(alpha = 0.5f)
            )
        ) {
            Text(text = if (page == 1) "Inicio" else "Anterior", fontSize = 13.sp, fontWeight = FontWeight.Medium)
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Botón Siguiente (Estilo carruaje real)
        Button(
            onClick = onNext,
            enabled = page < totalPages,
            modifier = Modifier
                .weight(1f)
                .height(48.dp),
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(
                containerColor = CinderellaGlassBlue, // Azul cristal
                contentColor = DeepRoyalText,
                disabledContainerColor = Color(0xFFE0E0E0),
                disabledContentColor = Color(0xFF9E9E9E)
            ),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
            border = BorderStroke(1.dp, Color.White.copy(alpha = 0.3f))
        ) {
            Text(text = "Siguiente ✧", fontSize = 13.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AppPreview() {
    ArtSpaceTheme {
        CinderellaStoryApp()
    }
}