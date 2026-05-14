package com.iqraai.ui.screens.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.iqraai.R
import com.iqraai.ui.theme.IqraTheme

data class SettingsUiState(
    val language: String = "English",
    val notificationsEnabled: Boolean = true,
    val darkModeEnabled: Boolean = false,
    val reciterVoice: String = "Mishary Al-Afasy",
    val playbackSpeed: String = "Normal",
    val appVersion: String = "v1.0.0"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    state: SettingsUiState,
    onBack: () -> Unit,
    onEditProfile: () -> Unit,
    onChangePin: () -> Unit,
    onLogout: () -> Unit,
    onToggleNotifications: (Boolean) -> Unit,
    onToggleDarkMode: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(R.string.settings_title), fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.settings_back_content_desc)
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                    navigationIconContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.surface
    ) { paddingValues ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            // Account Section
            item { SettingsSectionHeader(title = stringResource(R.string.settings_section_account)) }
            item {
                SettingsListItem(
                    icon = Icons.Default.Person,
                    title = stringResource(R.string.settings_item_edit_profile),
                    onClick = onEditProfile
                )
            }
            item {
                SettingsListItem(
                    icon = Icons.Default.Lock,
                    title = stringResource(R.string.settings_item_change_pin),
                    onClick = onChangePin
                )
            }
            item {
                SettingsListItem(
                    icon = Icons.Default.ExitToApp,
                    title = stringResource(R.string.settings_item_logout),
                    titleColor = MaterialTheme.colorScheme.error,
                    iconColor = MaterialTheme.colorScheme.error,
                    onClick = onLogout,
                    showChevron = false
                )
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }

            // App Preferences Section
            item { SettingsSectionHeader(title = stringResource(R.string.settings_section_preferences)) }
            item {
                SettingsListItem(
                    icon = Icons.Default.Place,
                    title = stringResource(R.string.settings_item_language),
                    value = state.language,
                    onClick = { /* Handle language selection */ }
                )
            }
            item {
                SettingsListItem(
                    icon = Icons.Default.Notifications,
                    title = stringResource(R.string.settings_item_notifications),
                    trailingContent = {
                        Switch(
                            checked = state.notificationsEnabled,
                            onCheckedChange = onToggleNotifications,
                            colors = SwitchDefaults.colors(checkedThumbColor = MaterialTheme.colorScheme.primary)
                        )
                    }
                )
            }
            item {
                SettingsListItem(
                    icon = Icons.Default.Star,
                    title = stringResource(R.string.settings_item_dark_mode),
                    trailingContent = {
                        Switch(
                            checked = state.darkModeEnabled,
                            onCheckedChange = onToggleDarkMode,
                            colors = SwitchDefaults.colors(checkedThumbColor = MaterialTheme.colorScheme.primary)
                        )
                    }
                )
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }

            // Audio Options Section
            item { SettingsSectionHeader(title = stringResource(R.string.settings_section_audio)) }
            item {
                SettingsListItem(
                    icon = Icons.Default.PlayArrow,
                    title = stringResource(R.string.settings_item_reciter_voice),
                    value = state.reciterVoice,
                    onClick = { /* Handle reciter selection */ }
                )
            }
            item {
                SettingsListItem(
                    icon = Icons.Default.PlayArrow,
                    title = stringResource(R.string.settings_item_playback_speed),
                    value = state.playbackSpeed,
                    onClick = { /* Handle playback speed */ }
                )
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }

            // About Section
            item { SettingsSectionHeader(title = stringResource(R.string.settings_section_about)) }
            item {
                SettingsListItem(
                    icon = Icons.Default.Warning,
                    title = stringResource(R.string.settings_item_privacy_policy),
                    onClick = { /* Open Privacy Policy */ }
                )
            }
            item {
                SettingsListItem(
                    icon = Icons.Default.List,
                    title = stringResource(R.string.settings_item_terms_of_service),
                    onClick = { /* Open Terms of Service */ }
                )
            }
            item {
                SettingsListItem(
                    icon = Icons.Default.Info,
                    title = stringResource(R.string.settings_item_app_version),
                    value = state.appVersion,
                    showChevron = false,
                    onClick = { /* App version clicked */ }
                )
            }
        }
    }
}

@Composable
private fun SettingsSectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.primary,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
    )
}

@Composable
private fun SettingsListItem(
    icon: ImageVector,
    title: String,
    modifier: Modifier = Modifier,
    titleColor: Color = MaterialTheme.colorScheme.onSurface,
    iconColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    value: String? = null,
    showChevron: Boolean = true,
    onClick: (() -> Unit)? = null,
    trailingContent: @Composable (() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier)
            .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconColor,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            color = titleColor,
            modifier = Modifier.weight(1f)
        )
        if (value != null) {
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(end = 8.dp)
            )
        }
        if (trailingContent != null) {
            trailingContent()
        } else if (showChevron) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SettingsScreenPreview() {
    IqraTheme {
        SettingsScreen(
            state = SettingsUiState(),
            onBack = {},
            onEditProfile = {},
            onChangePin = {},
            onLogout = {},
            onToggleNotifications = {},
            onToggleDarkMode = {}
        )
    }
}
