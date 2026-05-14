package com.iqraai.ui.screens.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.iqraai.ui.theme.Charcoal
import com.iqraai.ui.theme.DeepTeal
import com.iqraai.ui.theme.OffWhite
import com.iqraai.ui.theme.Rose
import com.iqraai.ui.theme.WarmGray

@Composable
fun ParentGateScreen(
    onSuccess: () -> Unit,
    onLockedOut: () -> Unit,
    onBack: () -> Unit = {},
    onSettings: () -> Unit = {}
) {
    var input by remember { mutableStateOf("") }
    var attempts by remember { mutableStateOf(0) }
    val maxAttempts = 3

    // Example static challenge. In real app, generate dynamically.
    val num1 = 12
    val num2 = 7
    val expectedAnswer = (num1 + num2).toString()


    if (input.length == expectedAnswer.length) {
        if (input == expectedAnswer) {
            onSettings() // Actually navigate to settings for Phase 4 test
            // onSuccess() // original behaviour
        } else {
            attempts++
            if (attempts >= maxAttempts) {
                onLockedOut()
            } else {
                input = "" // Reset for next try
            }
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize().semantics { contentDescription = "Parent verification required. Math challenge." },
        color = OffWhite
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(48.dp))

            // Lock Icon placeholder
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(DeepTeal.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                // Here you would use a real lock icon painter
                Box(modifier = Modifier.size(24.dp).background(DeepTeal))
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Grown-ups only!",
                style = MaterialTheme.typography.headlineLarge,
                color = Charcoal
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Solve this to access parent settings:",
                style = MaterialTheme.typography.bodyMedium,
                color = WarmGray
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Math Challenge Card
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = Color.White,
                shadowElevation = 2.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "$num1 + $num2 = ${if(input.isEmpty()) "?" else input}",
                        style = MaterialTheme.typography.displayLarge,
                        color = Charcoal
                    )

                    if (attempts > 0) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Try again! ($attempts/$maxAttempts)",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Rose
                        )
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    // Numpad
                    NumberPad(
                        onNumberClick = { num ->
                            if (input.length < expectedAnswer.length) {
                                input += num
                            }
                        },
                        onDeleteClick = {
                            if (input.isNotEmpty()) {
                                input = input.dropLast(1)
                            }
                        }
                    )
                }
            }
        }
    }
}

private val NUMPAD_ROWS = listOf(
    listOf("1", "2", "3"),
    listOf("4", "5", "6"),
    listOf("7", "8", "9"),
    listOf("", "0", "DEL")
)

@Composable
fun NumberPad(onNumberClick: (String) -> Unit, onDeleteClick: () -> Unit) {
    Column {
        NUMPAD_ROWS.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                row.forEach { num ->
                    if (num.isEmpty()) {
                        Spacer(modifier = Modifier.size(64.dp))
                    } else if (num == "DEL") {
                        NumpadButton(text = "⌫", onClick = onDeleteClick)
                    } else {
                        NumpadButton(text = num, onClick = { onNumberClick(num) })
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun NumpadButton(text: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(64.dp)
            .clip(CircleShape)
            .background(OffWhite)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.headlineLarge,
            color = Charcoal,
            textAlign = TextAlign.Center
        )
    }
}
