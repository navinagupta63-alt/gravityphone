package com.example.gemini.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.Code
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Terminal
import androidx.compose.material.icons.outlined.CloudUpload
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gemini.AppViewMode
import com.example.gemini.theme.*

@Composable
fun GravityBottomNavBar(
    currentMode: AppViewMode,
    onSelectMode: (AppViewMode) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .navigationBarsPadding(),
        contentAlignment = Alignment.Center
    ) {
        // Floating glassmorphic rounded pill container
        Row(
            modifier = Modifier
                .height(62.dp)
                .fillMaxWidth()
                .shadow(elevation = 18.dp, shape = RoundedCornerShape(31.dp), spotColor = Color.Black)
                .clip(RoundedCornerShape(31.dp))
                .background(GravityGlassNavbarBg)
                .border(0.5.dp, Color.White.copy(alpha = 0.12f), RoundedCornerShape(31.dp))
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            GravityNavItem(
                label = "Chat",
                selectedIcon = Icons.Filled.ChatBubble,
                unselectedIcon = Icons.Outlined.ChatBubbleOutline,
                isSelected = currentMode == AppViewMode.CHAT,
                onClick = { onSelectMode(AppViewMode.CHAT) }
            )

            GravityNavItem(
                label = "Code",
                selectedIcon = Icons.Filled.Code,
                unselectedIcon = Icons.Outlined.Code,
                isSelected = currentMode == AppViewMode.IDE,
                onClick = { onSelectMode(AppViewMode.IDE) }
            )

            GravityNavItem(
                label = "Terminal",
                selectedIcon = Icons.Filled.Terminal,
                unselectedIcon = Icons.Outlined.Terminal,
                isSelected = currentMode == AppViewMode.TERMINAL,
                onClick = { onSelectMode(AppViewMode.TERMINAL) }
            )

            GravityNavItem(
                label = "Cloud",
                selectedIcon = Icons.Filled.CloudUpload,
                unselectedIcon = Icons.Outlined.CloudUpload,
                isSelected = currentMode == AppViewMode.CLOUD,
                onClick = { onSelectMode(AppViewMode.CLOUD) }
            )

            GravityNavItem(
                label = "Settings",
                selectedIcon = Icons.Filled.Settings,
                unselectedIcon = Icons.Outlined.Settings,
                isSelected = currentMode == AppViewMode.SETTINGS,
                onClick = { onSelectMode(AppViewMode.SETTINGS) }
            )
        }
    }
}

@Composable
private fun GravityNavItem(
    label: String,
    selectedIcon: ImageVector,
    unselectedIcon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val tintColor by animateColorAsState(
        targetValue = if (isSelected) GravityElectricBlue else GravityTextSecondary,
        animationSpec = tween(150),
        label = "navItemColor"
    )

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(horizontal = 10.dp, vertical = 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = if (isSelected) selectedIcon else unselectedIcon,
            contentDescription = label,
            tint = tintColor,
            modifier = Modifier.size(22.dp)
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = label,
            fontSize = 10.sp,
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
            color = tintColor,
            letterSpacing = (-0.2).sp
        )
    }
}
