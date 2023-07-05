package com.xxmrk888ytxx.customviews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.xxmrk888ytxx.customviews.ui.theme.CustomViewsTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = "main"
            ) {
                composable("main") {
                    Column() {
                        Button(onClick = { navController.navigate("CompoundView") }) {
                            Text(text = "CompoundView")
                        }


                        Button(onClick = { navController.navigate("CustomProgressBar") }) {
                            Text(text = "CustomProgressBar")
                        }
                    }
                }

                composable("CompoundView") {
                    CompoundView()
                }

                composable("CustomProgressBar") {
                    var progress by rememberSaveable {
                        mutableStateOf(0)
                    }

                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        CustomProgressBar(progress)

                        Button(onClick = {
                            progress = Random(System.currentTimeMillis()).nextInt(0,100)
                        }) {
                            Text(text = "Aninate with Random Value")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CustomProgressBar(
    progress:Int = 10
) {

    val animateProcess = animateFloatAsState(
        targetValue = progress.toFloat(),
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioHighBouncy
        )
    )

    Box(
        modifier = Modifier
            .padding(10.dp)
            .size(300.dp)
            .drawBehind {
                this.drawArc(
                    useCenter = false,
                    color = Color.Red.copy(0.4f),
                    startAngle = 360f,
                    sweepAngle = 360f,
                    style = Stroke(
                        cap = StrokeCap.Round,
                        width = 40f
                    )
                )

                this.drawArc(
                    useCenter = false,
                    color = Color.Red,
                    startAngle = 270f,
                    sweepAngle = 360f * (animateProcess.value / 100f),
                    style = Stroke(
                        cap = StrokeCap.Round,
                        width = 40f
                    )
                )
            },
        contentAlignment = Alignment.Center
    ) {
        Text(text = progress.toString(), fontSize = 30.sp)
    }
}

@Composable
fun CompoundView(
    modifier: Modifier = Modifier,
    boxSize: Dp = 100.dp,
) {
    var currentPosition by rememberSaveable() {
        mutableStateOf(0)
    }

    val colorArray = remember {
        listOf(
            Color.Black,
            Color.Red,
            Color.Green,
            Color.DarkGray,
            Color.Red,
            Color.Yellow,
            Color.Cyan,
            Color.LightGray,
            Color.Magenta,
            Color.Blue,
        )
    }

    val currentColor by remember(currentPosition) {
        mutableStateOf(colorArray[currentPosition])
    }


    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(boxSize)
                .background(currentColor)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(onClick = {
                if (currentPosition == 0) {
                    currentPosition = colorArray.lastIndex
                } else {
                    currentPosition -= 1
                }
            }) {
                Text(text = "Previous")
            }

            Text(text = "$currentPosition")

            Button(onClick = {
                if (currentPosition == colorArray.lastIndex) {
                    currentPosition = 0
                } else {
                    currentPosition += 1
                }
            }) {
                Text(text = "Next")
            }


        }
    }
}