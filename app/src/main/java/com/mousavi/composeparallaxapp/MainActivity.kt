package com.mousavi.composeparallaxapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mousavi.composeparallaxapp.ui.theme.ComposeParallaxAppTheme
import com.mousavi.composeparallaxapp.ui.theme.Shapes
import java.lang.Float.max
import java.lang.Integer.min

val AppBarExpandedHeight: Dp = 300.dp
val AppBarCollapsedHeight: Dp = 60.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeParallaxAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    MainFragment()
                }
            }
        }
    }
}

@Composable
fun MainFragment() {
    val scrollState = rememberLazyListState()

    Box {
        Content(scrollState)
        ParallaxToolbar(scrollState)
    }
}

@Composable
fun ParallaxToolbar(scrollState: LazyListState) {
    val imageHeight = AppBarExpandedHeight - AppBarCollapsedHeight

    val maxOffset = with(LocalDensity.current) { imageHeight.roundToPx() }
    val offset = scrollState.firstVisibleItemScrollOffset.coerceAtMost(maxOffset)

    Log.d("offffff", scrollState.firstVisibleItemScrollOffset.toString())

    val offsetProgress = 0f.coerceAtLeast(offset * 3f - 2f * maxOffset) / maxOffset

    TopAppBar(
        contentPadding = PaddingValues(),
        backgroundColor = Color.White,
        modifier = Modifier
            .height(AppBarExpandedHeight)
            .offset { IntOffset(x = 0, -offset) },
        elevation = if (offset == maxOffset) 4.dp else 0.dp
    ) {
        Column {
            Box(
                modifier = Modifier.height(imageHeight)
            ) {
                if (offset != maxOffset) {
                    Image(
                        painter = painterResource(id = R.drawable.food),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.offset { IntOffset(0, offset / 2) },
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .offset { IntOffset(0, offset / 2) }
                        .background(
                            Brush.verticalGradient(
                                colorStops = arrayOf(
                                    Pair(0.4f, Color.Transparent),
                                    Pair(1f, Color.White)
                                ),
                            )
                        )

                )
            }
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .height(AppBarCollapsedHeight),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Delicious Food",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(horizontal = (16 + 28 * offsetProgress).dp)
                        .scale(1f - 0.25f * offsetProgress)
                )
            }
        }
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .height(AppBarCollapsedHeight)
            .padding(horizontal = 16.dp)
    ) {
        TopButton(Icons.Default.ArrowBack)
        TopButton(Icons.Default.Favorite)
    }
}

@Composable
fun TopButton(icon: ImageVector) {
    Button(
        onClick = {},
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.White,
            contentColor = Color.Gray
        ),
        contentPadding = PaddingValues(),
        shape = Shapes.small,
        elevation = ButtonDefaults.elevation(),
        modifier = Modifier.size(35.dp)
    ) {
        Icon(imageVector = icon, contentDescription = null)
    }
}

@Composable
fun Content(scrollState: LazyListState) {
    LazyColumn(contentPadding = PaddingValues(top = AppBarExpandedHeight), state = scrollState) {
        item {
            repeat(30) {
                Text(
                    modifier = Modifier.padding(20.dp),
                    text = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum."
                )
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 300, heightDp = 800)
@Composable
fun DefaultPreview() {
    ComposeParallaxAppTheme {
        MainFragment()
    }
}