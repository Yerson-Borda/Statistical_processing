package com.example.statisticalprocessing.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.statisticalprocessing.navigation.AppScreens

@Composable
fun RandomEvents(navController: NavController){
    MyProbabilities(navController)
}

@Composable
fun MyProbabilities(navController: NavController){

    var prob1 by remember { mutableFloatStateOf(0.0F) }
    var prob2 by remember { mutableFloatStateOf(0.0F) }
    var prob3 by remember { mutableFloatStateOf(0.0F) }
    var prob4 by remember { mutableFloatStateOf(0.0F) }
    var prob5 by remember { mutableFloatStateOf(0.0F) }
    var trials by remember { mutableIntStateOf(0) }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .width(300.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        fun updateProb5() {
            prob5 = 1.0F - prob1 - prob2 - prob3 - prob4
        }

        fun parseInput(text: String): Float {
            return text.toFloatOrNull() ?: 0.0F
        }

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ){
            Text(
                text = "Prob 1: ",
                fontSize = 25.sp
            )

            Spacer(modifier = Modifier.width(20.dp))

            TextField(
                value = prob1.toString(),
                onValueChange = { newValue ->
                    if (newValue.isNotEmpty()) {
                        prob1 = parseInput(newValue)
                        updateProb5()
                    } else {
                        prob1 = 0.0F
                        updateProb5()
                    }
                },
                modifier = Modifier.width(90.dp),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ){
            Text(
                text = "Prob 2: ",
                fontSize = 25.sp
            )

            Spacer(modifier = Modifier.width(20.dp))

            TextField(
                value = prob2.toString(),
                onValueChange = { newValue ->
                    if (newValue.isNotEmpty()) {
                        prob2 = parseInput(newValue)
                        updateProb5()
                    } else {
                        prob2 = 0.0F
                        updateProb5()
                    }
                },
                modifier = Modifier.width(90.dp),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ){
            Text(
                text = "Prob 3: ",
                fontSize = 25.sp
            )

            Spacer(modifier = Modifier.width(20.dp))

            TextField(
                value = prob3.toString(),
                onValueChange = { newValue ->
                    if (newValue.isNotEmpty()) {
                        prob3 = parseInput(newValue)
                        updateProb5()
                    } else {
                        prob3 = 0.0F
                        updateProb5()
                    }
                },
                modifier = Modifier.width(90.dp),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ){
            Text(
                text = "Prob 4: ",
                fontSize = 25.sp
            )

            Spacer(modifier = Modifier.width(20.dp))

            TextField(
                value = prob4.toString(),
                onValueChange = { newValue ->
                    if (newValue.isNotEmpty()) {
                        prob4 = parseInput(newValue)
                        updateProb5()
                    } else {
                        prob4 = 0.0F
                        updateProb5()
                    }
                },
                modifier = Modifier.width(90.dp),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ){
            Text(
                text = "Prob 5: ",
                fontSize = 25.sp
            )

            Spacer(modifier = Modifier.width(20.dp))

            TextField(
                value = prob5.toString(),
                onValueChange = { },
                modifier = Modifier.width(90.dp),
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Number of trials: ",
                fontSize = 25.sp
            )
            TextField(
                value = trials.toString(),
                onValueChange = { trials = it.toInt() },
                modifier = Modifier
                    .width(90.dp),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            modifier = Modifier
                .width(120.dp)
                .shadow(10.dp),
            shape = RectangleShape,
            colors = ButtonDefaults.buttonColors(
                contentColor = Color(0xff004D40),
                containerColor = Color(0xff9ed6df)),
            onClick = {
                navController.navigate(route = "${AppScreens.Graph.route}/$prob1/$prob2/$prob3/$prob4/$prob5/$trials")
            }
        ) {
            Text(
                text = "Start",
                fontSize = 20.sp
            )
        }
    }
}