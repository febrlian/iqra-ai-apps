package com.iqraai.ui.components.buttons

import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.iqraai.ui.theme.DeepTeal
import androidx.compose.material3.MaterialTheme

@Composable
fun GhostButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentDescriptionText: String = text
) {
    TextButton(
        onClick = onClick,
        modifier = modifier
            .height(48.dp)
            .semantics { contentDescription = contentDescriptionText },
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium.copy(color = DeepTeal)
        )
    }
}
