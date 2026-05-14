package com.iqraai.ui.screens.parent

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ParentOverviewDashboardScreen() {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = { ParentBottomNavBar() }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            item { HeaderSection() }
            item { MetricsGrid() }
            item { ProgressChartSection() }
            item { RecentActivitySection() }
        }
    }
}

@Composable
fun HeaderSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Mocking user avatar
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
                .background(Color.LightGray)
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 12.dp)
        ) {
            Text(
                text = "Yusuf's Progress",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold),
                color = Color.Black
            )
            Text(
                text = "LEVEL 3 • INTERMEDIATE",
                style = MaterialTheme.typography.labelSmall,
                color = Color.Gray
            )
        }

        IconButton(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .background(Color(0xFFF8FAFC), CircleShape)
        ) {
            Icon(imageVector = Icons.Default.Notifications, contentDescription = "Notifications", tint = Color.Gray)
        }
    }
}

@Composable
fun MetricsGrid() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            MetricCard("Time", "12h 30m", Color(0xFF14A098))
            MetricCard("Accuracy", "88%", Color(0xFF3B82F6))
        }
        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(12.dp)) {
             MetricCard("Lessons", "45/60", MaterialTheme.colorScheme.primary)
             MetricCard("Streak", "7 Days", Color(0xFFF97316))
        }
    }
}

@Composable
fun MetricCard(title: String, value: String, iconColor: Color) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFF1F5F9))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Mock icon placeholder
                Box(modifier = Modifier.size(16.dp).background(iconColor, CircleShape))
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = title.uppercase(),
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                    color = Color.Gray
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.ExtraBold),
                color = Color.Black
            )
        }
    }
}

@Composable
fun ProgressChartSection() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFF1F5F9))
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Column {
                    Text(
                        text = "Weekly Activity",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = Color.Black
                    )
                    Text(
                        text = "Minutes spent learning per day",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "12.5 hrs",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold),
                        color = Color(0xFF14A098)
                    )
                    Text(
                        text = "+15% vs last week",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = Color(0xFF22C55E)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Mock Bar Chart
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(128.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                 val heights = listOf(0.9f, 0.6f, 0.25f, 0.15f, 0.5f, 0.4f, 0.85f)
                 val days = listOf("MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN")

                 heights.zip(days).forEach { (height, day) ->
                     Column(
                         horizontalAlignment = Alignment.CenterHorizontally,
                         modifier = Modifier.weight(1f)
                     ) {
                         Box(
                             modifier = Modifier
                                 .fillMaxWidth(0.8f)
                                 .fillMaxHeight(height)
                                 .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                                 .background(Color(0xFF14A098))
                         )
                         Spacer(modifier = Modifier.height(8.dp))
                         Text(
                             text = day,
                             style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp, fontWeight = FontWeight.Bold),
                             color = Color.Gray
                         )
                     }
                 }
            }
        }
    }
}

@Composable
fun RecentActivitySection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Recent Activity",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = Color.Black
            )
            TextButton(onClick = { /*TODO*/ }) {
                Text("View All", fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        ActivityItem("Surah Al-Fatiha Memorization", "Perfect Score! • 5 mins ago", Color(0xFF7CB342))
        Spacer(modifier = Modifier.height(12.dp))
        ActivityItem("Arabic Alphabet Quiz", "Completed • 2 hours ago", MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.height(12.dp))
        ActivityItem("Pronunciation Practice", "Improved by 12% • Yesterday", Color(0xFF14A098))
    }
}

@Composable
fun ActivityItem(title: String, subtitle: String, iconColor: Color) {
    Card(
         modifier = Modifier.fillMaxWidth(),
         colors = CardDefaults.cardColors(containerColor = Color.White),
         elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
         shape = RoundedCornerShape(16.dp),
         border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFF1F5F9))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(iconColor.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                 Box(modifier = Modifier.size(24.dp).background(iconColor, CircleShape))
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    color = Color.Black
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }

            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun ParentBottomNavBar() {
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 8.dp
    ) {
        NavigationBarItem(
            icon = { Box(modifier = Modifier.size(24.dp).background(MaterialTheme.colorScheme.primary, CircleShape)) },
            label = { Text("Overview") },
            selected = true,
            onClick = { /*TODO*/ }
        )
        NavigationBarItem(
            icon = { Box(modifier = Modifier.size(24.dp).background(Color.Gray, CircleShape)) },
            label = { Text("Lessons") },
            selected = false,
            onClick = { /*TODO*/ }
        )
        NavigationBarItem(
             icon = { Box(modifier = Modifier.size(24.dp).background(Color.Gray, CircleShape)) },
             label = { Text("Activity") },
             selected = false,
             onClick = { /*TODO*/ }
        )
        NavigationBarItem(
             icon = { Box(modifier = Modifier.size(24.dp).background(Color.Gray, CircleShape)) },
             label = { Text("Settings") },
             selected = false,
             onClick = { /*TODO*/ }
        )
    }
}
