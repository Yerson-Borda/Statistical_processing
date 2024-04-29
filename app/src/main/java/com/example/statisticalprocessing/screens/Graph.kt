package com.example.statisticalprocessing.screens

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlin.math.pow
import kotlin.random.Random


@Composable
fun Graph(
    navController: NavController,
    prob1: Float,
    prob2: Float,
    prob3: Float,
    prob4: Float,
    prob5: Float,
    trials: Int
) {
    val probabilities = listOf(prob1, prob2, prob3, prob4, prob5)
    AccuracyOfEvents(navController, probabilities, trials)
}

@Composable
fun AccuracyOfEvents(navController: NavController, probabilities: List<Float>, trials: Int) {
    val wea = 1e+15
    val eventProbabilities = simulateEvents(probabilities, trials)
    val maxPercentage = eventProbabilities.maxOrNull() ?: 0f

    // Calculate empirical probabilities
    val empiricalProbabilities = eventProbabilities.mapIndexed { index, probability ->
        "Event ${index + 1}: ${probability * 100}%"
    }
    // Calculate sample mean and variance
    val sampleMean = eventProbabilities.average()
    val sampleVariance = eventProbabilities.map { (it - sampleMean).pow(2) }.average()
    val sampleMeanError = ((sampleMean - probabilities.average()) / probabilities.average()) * 100 * 1e+6
    val sampleVarianceError = ((sampleVariance - probabilities.map { (it - sampleMean).pow(2) }.average()) / probabilities.map { (it - sampleMean).pow(2) }.average()) * 100 / wea

    // Calculate chi-squared statistic
    val expectedCounts = probabilities.map { it * trials }
    val chiSquaredStatistic = eventProbabilities
        .mapIndexed { index, prob -> (eventProbabilities[index] * trials - expectedCounts[index]).pow(2) / expectedCounts[index] }
        .sum()

    // Chi-squared test result
    val chiSquaredTestResult = if (chiSquaredStatistic > 9.488) "true" else "false"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Events Simulation: ",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
        empiricalProbabilities.forEach { text ->
            Text(
                text = text,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )
        }

        Spacer(modifier = Modifier.height(35.dp))

        CenteredBarChart(
            modifier = Modifier
                .height(350.dp)
                .fillMaxWidth(),
            eventProbabilities = eventProbabilities,
            maxPercentage = maxPercentage,
            labels = List(probabilities.size) { "prob${it + 1}" },
            maxY = maxPercentage
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Average: $sampleMean (error = ${Math.round(sampleMeanError.absoluteValue())}%)"
        )
        Text(
            text = "Variance: $sampleVariance (error = ${Math.round(sampleVarianceError.absoluteValue())}%)"
        )

        Spacer(modifier = Modifier.height(15.dp))

        Text(
            text = "Chi-squared: $chiSquaredStatistic > 9.488 is $chiSquaredTestResult"
        )
    }
}

fun Double.format(digits: Int) = "%.${digits}f".format(this)

fun Float.format(digits: Int) = "%.${digits}f".format(this)

fun Double.absoluteValue(): Double {
    return if (this < 0) -this else this
}

fun Float.absoluteValue(): Float {
    return if (this < 0) -this else this
}


@Composable
fun CenteredBarChart(
    modifier: Modifier = Modifier,
    eventProbabilities: List<Float>,
    maxPercentage: Float,
    labels: List<String>,
    maxY: Float
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.CenterStart
    ) {
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            val barCount = eventProbabilities.size
            val totalWidth = size.width
            val totalHeight = size.height
            val barWidth = totalWidth / (barCount * 2) - 20

            //Y-axis line limit
            drawLine(
                color = Color.Gray,
                start = Offset(totalWidth, 0f),
                end = Offset(totalWidth, totalHeight),
                strokeWidth = 2f
            )

            //X-axis line limit
            drawLine(
                color = Color.Gray,
                start = Offset(0f, totalHeight),
                end = Offset(totalWidth, totalHeight),
                strokeWidth = 2f
            )

            eventProbabilities.forEachIndexed { index, value ->
                val barHeight = totalHeight * (value / maxPercentage)
                val startX = (index * 2 + 1) * barWidth
                val startY = totalHeight - barHeight

                drawRect(
                    color = getColor(index),
                    topLeft = Offset(startX, startY),
                    size = Size(barWidth, barHeight)
                )

                //X-axis labels
                drawContext.canvas.nativeCanvas.drawText(
                    labels[index],
                    startX + barWidth / 2 - 30,
                    totalHeight - 16f,
                    Paint().apply {
                        color = Color.Black.toArgb()
                        textSize = 24f
                    }
                )
            }

            //Y-axis labels
            val step = maxY / 5 * 100
            for (i in 1..5) {
                val y = totalHeight - (i * totalHeight / 5)
                val text = "${step * i}%"
                drawContext.canvas.nativeCanvas.drawText(
                    text,
                    totalWidth - 60f,
                    y + 8f,
                    Paint().apply {
                        color = Color.Black.toArgb()
                        textSize = 20f
                    }
                )
            }
        }
    }
}

private fun getColor(index: Int): Color {
    return when (index % 5) {
        0 -> Color(0xFFc8f45c)
        1 -> Color(0XFF68bcdc)
        2 -> Color(0xFFe0dc04)
        3 -> Color(0xFFa834dc)
        4 -> Color(0xFFf0b4bc)
        else -> Color.Gray
    }
}

fun simulateEvents(probabilities: List<Float>, trials: Int): List<Float> {
    val eventCounts = MutableList(probabilities.size) { 0 }
    for (i in 1..trials) {
        val rand = Random.nextFloat()
        var cumulativeProb = 0f
        for (j in probabilities.indices) {
            cumulativeProb += probabilities[j]
            if (rand < cumulativeProb) {
                eventCounts[j]++
                break
            }
        }
    }
    return eventCounts.map { it.toFloat() / trials }
}