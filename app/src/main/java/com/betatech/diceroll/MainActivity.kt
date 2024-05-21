package com.betatech.diceroll

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.betatech.diceroll.ui.theme.DiceRollTheme
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DiceRollTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    App(
                        name = "Android",
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun App(name: String, modifier: Modifier = Modifier) {
    var numberOfDice by remember {
        mutableStateOf(1.0f)
    }
    var diceNumbers by remember {
        mutableStateOf(IntArray(numberOfDice.toInt()){ 1 })
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.padding(16.dp)
        ) {
            items(diceNumbers.size) { index ->
                Dice(diceNumbers[index])
            }
        }
        Text(text = "Number of dice")
        Slider(
            value = numberOfDice,
            onValueChange = {
                diceNumbers = IntArray(it.toInt()) { (1..6).random() }
                numberOfDice = it.roundToInt().toFloat()
            },
            valueRange = 1f..10f,
            steps = 9,

        )
        Button(onClick = { diceNumbers = IntArray(numberOfDice.toInt()) { (1..6).random() } }) {
            Text(text = stringResource(R.string.spin))
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Total: ${numberOfDice}")
    }
}


@Composable
fun Dice(diceNumber: Int, modifier: Modifier = Modifier) {
    val imageResource = when (diceNumber) {
        1 -> R.drawable.dice_1
        2 -> R.drawable.dice_2
        3 -> R.drawable.dice_3
        4 -> R.drawable.dice_4
        5 -> R.drawable.dice_5
        else -> R.drawable.dice_6
    }
    Image(painter = painterResource(imageResource), contentDescription = null)
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    DiceRollTheme {
        App("Android")
    }
}