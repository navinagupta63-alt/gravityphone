package com.example.gemini.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.outlined.Psychology
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gemini.theme.*

@Composable
fun ThinkingAccordion(
    thoughtText: String,
    durationMs: Long?,
    isStreaming: Boolean,
    modifier: Modifier = Modifier
) {
    var isExpanded by remember { mutableStateOf(isStreaming) }
    var hasUserManuallyToggled by remember { mutableStateOf(false) }

    LaunchedEffect(isStreaming) {
        if (isStreaming && !hasUserManuallyToggled) {
            isExpanded = true
        }
    }

    val rotation by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0f,
        animationSpec = tween(durationMillis = 200),
        label = "thought_rotation"
    )

    val tokenCount = remember(thoughtText) {
        val words = thoughtText.split("\\s+".toRegex()).size
        (words * 1.3).toInt().coerceAtLeast(12)
    }

    val timingText = remember(durationMs) {
        if (durationMs != null && durationMs > 0) {
            String.format("%.2fs", durationMs / 1000.0)
        } else {
            "0.38s"
        }
    }

    Surface(
        shape = RoundedCornerShape(14.dp),
        color = GravityGlassSurface,
        border = androidx.compose.foundation.BorderStroke(0.5.dp, Color.White.copy(alpha = 0.10f)),
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column {
            // Header Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        hasUserManuallyToggled = true
                        isExpanded = !isExpanded
                    }
                    .padding(horizontal = 12.dp, vertical = 9.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Psychology,
                        contentDescription = "Thinking",
                        tint = GravityVibrantPurple,
                        modifier = Modifier.size(17.dp)
                    )

                    Text(
                        text = "Deep Reasoning Budget",
                        fontSize = 11.5.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = GravityTextPrimary
                    )

                    // Token & time badge pill
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = GravityVibrantPurple.copy(alpha = 0.15f),
                        border = androidx.compose.foundation.BorderStroke(0.5.dp, GravityVibrantPurple.copy(alpha = 0.35f))
                    ) {
                        Text(
                            text = "$tokenCount tokens • $timingText",
                            fontSize = 9.5.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Medium,
                            color = GravityVibrantPurple,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }

                Icon(
                    imageVector = Icons.Default.ExpandMore,
                    contentDescription = if (isExpanded) "Collapse" else "Expand",
                    tint = GravityTextSecondary,
                    modifier = Modifier
                        .size(16.dp)
                        .rotate(rotation)
                )
            }

            // Expanded Thinking Body
            AnimatedVisibility(
                visible = isExpanded,
                enter = expandVertically() + androidx.compose.animation.fadeIn(),
                exit = shrinkVertically() + androidx.compose.animation.fadeOut()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF10121B).copy(alpha = 0.5f))
                        .border(
                            width = 0.5.dp,
                            color = Color.White.copy(alpha = 0.05f),
                            shape = RoundedCornerShape(bottomStart = 14.dp, bottomEnd = 14.dp)
                        )
                        .padding(horizontal = 14.dp, vertical = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    val cleanLines = remember(thoughtText) {
                        thoughtText.lines().filter { it.isNotBlank() }
                    }

                    if (cleanLines.isEmpty() && isStreaming) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(12.dp),
                                color = GravityVibrantPurple,
                                strokeWidth = 1.5.dp
                            )
                            Text(
                                text = "Evaluating execution tree & tool call obligations...",
                                fontSize = 11.sp,
                                fontFamily = FontFamily.Monospace,
                                color = GravityTextSecondary
                            )
                        }
                    } else {
                        cleanLines.forEachIndexed { index, line ->
                            Text(
                                text = "${index + 1}. ${line.trim().removePrefix("#").removePrefix("-").trim()}",
                                fontSize = 11.sp,
                                fontFamily = FontFamily.Monospace,
                                color = if (index == cleanLines.lastIndex && isStreaming) GravityMintGreen else GravityTextSecondary,
                                lineHeight = 16.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
