package com.example.gemini.ui.settings

import android.content.Context
import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gemini.data.local.LocalEnvironmentManager
import com.example.gemini.theme.*
import com.example.gemini.ui.chat.ChatViewModel

@Composable
fun SettingsDiagnosticsScreen(
    viewModel: ChatViewModel,
    onOpenFullSettings: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    var activeModel by remember { mutableStateOf("Gemini 2.5 Pro") }
    var thinkingBudgetEnabled by remember { mutableStateOf(true) }
    var thinkingTokens by remember { mutableStateOf("2,048 tokens") }
    var showModelDropdown by remember { mutableStateOf(false) }

    val hardwareInfo = remember { LocalEnvironmentManager.getDeviceHardwareInfo() }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(GravityCosmicBlack)
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp, vertical = 14.dp)
            .padding(bottom = 90.dp) // accommodate floating bottom nav
    ) {
        // Header
        Column(modifier = Modifier.padding(bottom = 16.dp)) {
            Text(
                text = "System Settings & Diagnostics",
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                color = GravityTextPrimary,
                letterSpacing = (-0.5).sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Device runtime, model routing & hardware engine telemetry.",
                fontSize = 12.sp,
                color = GravityTextSecondary
            )
        }

        // CARD 1: Google Account Credentials Card
        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = GravityGlassSurface),
            border = BorderStroke(0.5.dp, Color.White.copy(alpha = 0.10f)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 14.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "ACCOUNT CREDENTIALS",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace,
                    color = GravityTextSecondary,
                    letterSpacing = 0.5.sp
                )
                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // User Avatar
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(CircleShape)
                                .background(
                                    androidx.compose.ui.graphics.Brush.linearGradient(
                                        listOf(GravityElectricBlue, GravityVibrantPurple, Color(0xFF6366F1))
                                    )
                                )
                                .padding(2.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(CircleShape)
                                    .background(Color(0xFF10121B)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "A",
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = GravityTextPrimary
                                )
                            }
                        }

                        Column {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    text = "Alex Dev",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = GravityTextPrimary
                                )
                                Icon(
                                    imageVector = Icons.Default.Verified,
                                    contentDescription = null,
                                    tint = GravityElectricBlue,
                                    modifier = Modifier.size(15.dp)
                                )
                            }
                            Text(
                                text = "alex.developer@gmail.com",
                                fontSize = 11.5.sp,
                                fontFamily = FontFamily.Monospace,
                                color = GravityTextSecondary
                            )
                        }
                    }

                    // Switch Account Button
                    OutlinedButton(
                        onClick = onOpenFullSettings,
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(0.5.dp, Color.White.copy(alpha = 0.15f)),
                        colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White.copy(alpha = 0.05f)),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Text("Manage", fontSize = 11.sp, color = GravityTextPrimary)
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(imageVector = Icons.Default.SwapHoriz, contentDescription = null, tint = GravityTextPrimary, modifier = Modifier.size(14.dp))
                    }
                }
            }
        }

        // CARD 2: AI Intelligence Engine Card
        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = GravityGlassSurface),
            border = BorderStroke(0.5.dp, Color.White.copy(alpha = 0.10f)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 14.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "AI INTELLIGENCE ENGINE",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace,
                    color = GravityTextSecondary,
                    letterSpacing = 0.5.sp
                )
                Spacer(modifier = Modifier.height(12.dp))

                // Active Default Model Picker
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Icon(imageVector = Icons.Default.SmartToy, contentDescription = null, tint = GravityElectricBlue, modifier = Modifier.size(16.dp))
                            Text("Active Default Model", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = GravityTextPrimary)
                        }
                        Text("High precision coding & agentic refactoring", fontSize = 10.5.sp, color = GravityTextSecondary)
                    }

                    Box {
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = Color.Black.copy(alpha = 0.4f),
                            border = BorderStroke(0.5.dp, GravityElectricBlue.copy(alpha = 0.5f)),
                            modifier = Modifier.clickable { showModelDropdown = true }
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(activeModel, fontSize = 11.sp, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold, color = GravityElectricBlue)
                                Icon(imageVector = Icons.Default.UnfoldMore, contentDescription = null, tint = GravityTextSecondary, modifier = Modifier.size(14.dp))
                            }
                        }

                        DropdownMenu(
                            expanded = showModelDropdown,
                            onDismissRequest = { showModelDropdown = false },
                            modifier = Modifier.background(GravityGlassSurface)
                        ) {
                            listOf("Gemini 2.5 Pro", "Gemini 2.5 Flash", "Claude 3.7 Sonnet", "Claude 3.5 Haiku").forEach { modelName ->
                                DropdownMenuItem(
                                    text = { Text(modelName, color = GravityTextPrimary, fontSize = 12.sp, fontFamily = FontFamily.Monospace) },
                                    onClick = {
                                        activeModel = modelName
                                        showModelDropdown = false
                                    }
                                )
                            }
                        }
                    }
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = Color.White.copy(alpha = 0.08f))

                // Deep Reasoning (Thinking Budget) Toggle
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Icon(imageVector = Icons.Default.Psychology, contentDescription = null, tint = GravityVibrantPurple, modifier = Modifier.size(16.dp))
                            Text("Deep Reasoning Budget", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = GravityTextPrimary)
                        }
                        Text("Allows autonomous multi-step code exploration", fontSize = 10.5.sp, color = GravityTextSecondary)
                    }

                    Switch(
                        checked = thinkingBudgetEnabled,
                        onCheckedChange = { thinkingBudgetEnabled = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = GravityVibrantPurple,
                            uncheckedThumbColor = GravityTextSecondary,
                            uncheckedTrackColor = Color.White.copy(alpha = 0.1f)
                        )
                    )
                }
            }
        }

        // CARD 3: Subsystem & glibc Sandbox Diagnostics Card
        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = GravityGlassSurface),
            border = BorderStroke(0.5.dp, Color.White.copy(alpha = 0.10f)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 14.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "ON-DEVICE SUBSYSTEM & GLIBC",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace,
                        color = GravityTextSecondary,
                        letterSpacing = 0.5.sp
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(7.dp)
                                .clip(CircleShape)
                                .background(GravityMintGreen)
                        )
                        Text("Active Daemon", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = GravityMintGreen)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.Black.copy(alpha = 0.35f))
                        .padding(12.dp)
                ) {
                    TelemetryRow("OS Environment", "Ubuntu 24.04 PRoot / glibc 2.39")
                    TelemetryRow("Kernel Emulation", "Accelerated syscall shim (ARM64)")
                    TelemetryRow("CPU Architecture", hardwareInfo.arch)
                    TelemetryRow("Virtual Address", if (hardwareInfo.vaBits > 0) "${hardwareInfo.vaBits}-bit" else "48-bit (VA48)")
                    TelemetryRow("LSE Atomics", if (hardwareInfo.hasAtomics == true) "Hardware Supported" else "Emulated")
                    TelemetryRow("Bridge Port", "1234 (gRPC) / 8765 (MCP)")
                }
            }
        }

        // CARD 4: Advanced Settings & Actions Shortcut
        Button(
            onClick = onOpenFullSettings,
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(containerColor = GravityGlassSurface),
            border = BorderStroke(0.5.dp, Color.White.copy(alpha = 0.15f)),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(imageVector = Icons.Default.Tune, contentDescription = null, tint = GravityElectricBlue)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Open Full System Configuration & Plugins", fontSize = 12.5.sp, fontWeight = FontWeight.SemiBold, color = GravityTextPrimary)
            }
        }
    }
}

@Composable
private fun TelemetryRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, fontSize = 11.sp, color = GravityTextSecondary)
        Text(text = value, fontSize = 11.sp, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Medium, color = GravityTextPrimary)
    }
}
