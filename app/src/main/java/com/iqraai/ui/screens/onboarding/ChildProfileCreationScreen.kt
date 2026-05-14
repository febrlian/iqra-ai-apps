package com.iqraai.ui.screens.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.unit.dp
import com.iqraai.ui.components.buttons.PrimaryButton
import com.iqraai.ui.theme.Amber
import com.iqraai.ui.theme.Charcoal
import com.iqraai.ui.theme.DeepTeal
import com.iqraai.ui.theme.OffWhite
import com.iqraai.ui.theme.WarmGray
import com.iqraai.ui.theme.DeepTealLight
import com.iqraai.ui.theme.Rose
import com.iqraai.ui.theme.Sage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChildProfileCreationScreen(
    onContinueClick: (String, Int, Color) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var selectedAge by remember { mutableStateOf<Int?>(null) }

    val avatarColors = listOf(DeepTeal, Amber, Sage, Rose, DeepTealLight, WarmGray)
    var selectedAvatarColor by remember { mutableStateOf(avatarColors[0]) }

    val isContinueEnabled = name.isNotBlank() && selectedAge != null

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = OffWhite
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Who's learning?",
                style = MaterialTheme.typography.headlineLarge,
                color = Charcoal
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Create a profile for your child",
                style = MaterialTheme.typography.bodyMedium,
                color = WarmGray
            )

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("e.g., Aisyah, Ahmad", color = WarmGray) },
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = DeepTeal,
                    unfocusedBorderColor = WarmGray.copy(alpha = 0.5f),
                    cursorColor = DeepTeal
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Age",
                style = MaterialTheme.typography.bodyLarge,
                color = Charcoal
            )
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.horizontalScroll(rememberScrollState())
            ) {
                (3..12).forEach { age ->
                    AgeSelector(
                        age = age,
                        isSelected = age == selectedAge,
                        onClick = { selectedAge = age }
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Avatar Color",
                style = MaterialTheme.typography.bodyLarge,
                color = Charcoal
            )
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.horizontalScroll(rememberScrollState())
            ) {
                avatarColors.forEach { color ->
                    AvatarSelector(
                        color = color,
                        isSelected = color == selectedAvatarColor,
                        onClick = { selectedAvatarColor = color }
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            PrimaryButton(
                text = "Continue",
                onClick = {
                    if (isContinueEnabled) {
                        onContinueClick(name, selectedAge!!, selectedAvatarColor)
                    }
                },
                enabled = isContinueEnabled
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun AgeSelector(age: Int, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(64.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(if (isSelected) DeepTeal else Color.White)
            .border(
                width = if (isSelected) 0.dp else 1.dp,
                color = if (isSelected) Color.Transparent else WarmGray.copy(alpha = 0.5f),
                shape = RoundedCornerShape(16.dp)
            )
            .clickable(onClick = onClick)
            .semantics { contentDescription = "Age $age years" },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = age.toString(),
            style = MaterialTheme.typography.headlineLarge,
            color = if (isSelected) Color.White else Charcoal
        )
    }
}

@Composable
fun AvatarSelector(color: Color, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(72.dp)
            .clip(CircleShape)
            .border(
                width = if (isSelected) 4.dp else 0.dp,
                color = if (isSelected) Amber else Color.Transparent,
                shape = CircleShape
            )
            .clickable(onClick = onClick)
            .padding(if (isSelected) 6.dp else 0.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape)
                .background(color)
        )
    }
}
