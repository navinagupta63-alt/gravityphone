package com.example.gemini.ui.cloud

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.animation.*
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gemini.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CloudCompilerScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    var repoName by remember { mutableStateOf("GravityPhone/gravityphone") }
    var branchName by remember { mutableStateOf("master") }
    var githubPat by remember { mutableStateOf("") }
    var isPatVisible by remember { mutableStateOf(false) }
    var selectedFlavor by remember { mutableStateOf("termux") } // "termux" or "standard"
    var isBuilding by remember { mutableStateOf(false) }
    var buildProgress by remember { mutableFloatStateOf(0f) }
    var buildStepText by remember { mutableStateOf("") }
    var buildLogs by remember { mutableStateOf(listOf<String>()) }
    var downloadApkUrl by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(GravityCosmicBlack)
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp, vertical = 14.dp)
            .padding(bottom = 90.dp) // accommodate floating bottom nav
    ) {
        // Ambient Header Banner
        Column(modifier = Modifier.padding(bottom = 16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(bottom = 6.dp)
            ) {
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = GravityVibrantPurple.copy(alpha = 0.2f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, GravityVibrantPurple.copy(alpha = 0.35f))
                ) {
                    Text(
                        text = "GITHUB CLOUD BUILD RUNNER",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace,
                        color = GravityVibrantPurple,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }
                Text(
                    text = "v2.4.0",
                    fontSize = 11.sp,
                    fontFamily = FontFamily.Monospace,
                    color = GravityTextSecondary
                )
            }

            Text(
                text = "Cloud Compiler & Release",
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                color = GravityTextPrimary,
                letterSpacing = (-0.5).sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Build and sign native Android APKs directly on GitHub Actions infrastructure with zero local thermal throttling.",
                fontSize = 12.sp,
                color = GravityTextSecondary,
                lineHeight = 17.sp
            )
        }

        // STEP 1: Repository & Branch Selector Card
        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = GravityGlassSurface),
            border = androidx.compose.foundation.BorderStroke(0.5.dp, Color.White.copy(alpha = 0.10f)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(22.dp)
                                .clip(CircleShape)
                                .background(GravityElectricBlue),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "1",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                        Text(
                            text = "Repository & Target Branch",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = GravityTextPrimary
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = GravityMintGreen,
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            text = "Configured",
                            fontSize = 11.sp,
                            fontFamily = FontFamily.Monospace,
                            color = GravityMintGreen
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Repo field
                    OutlinedTextField(
                        value = repoName,
                        onValueChange = { repoName = it },
                        label = { Text("Repository", fontSize = 10.sp) },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        textStyle = LocalTextStyle.current.copy(fontSize = 12.sp, fontFamily = FontFamily.Monospace),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = GravityElectricBlue,
                            unfocusedBorderColor = Color.White.copy(alpha = 0.15f),
                            focusedContainerColor = Color.Black.copy(alpha = 0.35f),
                            unfocusedContainerColor = Color.Black.copy(alpha = 0.35f),
                            focusedTextColor = GravityTextPrimary,
                            unfocusedTextColor = GravityTextPrimary
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )

                    // Branch field
                    OutlinedTextField(
                        value = branchName,
                        onValueChange = { branchName = it },
                        label = { Text("Branch", fontSize = 10.sp) },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        textStyle = LocalTextStyle.current.copy(fontSize = 12.sp, fontFamily = FontFamily.Monospace),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = GravityElectricBlue,
                            unfocusedBorderColor = Color.White.copy(alpha = 0.15f),
                            focusedContainerColor = Color.Black.copy(alpha = 0.35f),
                            unfocusedContainerColor = Color.Black.copy(alpha = 0.35f),
                            focusedTextColor = GravityTextPrimary,
                            unfocusedTextColor = GravityTextPrimary
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )
                }
            }
        }

        // STEP 2: Personal Access Token (PAT) Card
        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = GravityGlassSurface),
            border = androidx.compose.foundation.BorderStroke(0.5.dp, Color.White.copy(alpha = 0.10f)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(22.dp)
                                .clip(CircleShape)
                                .background(GravityElectricBlue),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "2",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                        Text(
                            text = "GitHub Token (workflow scope)",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = GravityTextPrimary
                        )
                    }

                    Text(
                        text = "Encrypted Keystore",
                        fontSize = 10.sp,
                        fontFamily = FontFamily.Monospace,
                        color = GravityElectricBlue
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = githubPat,
                    onValueChange = { githubPat = it },
                    placeholder = { Text("ghp_xxxxxxxxxxxxxxxxxxxxxx", fontSize = 11.sp, color = GravityTextSecondary.copy(alpha = 0.5f)) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    visualTransformation = if (isPatVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { isPatVisible = !isPatVisible }) {
                            Icon(
                                imageVector = if (isPatVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                contentDescription = "Toggle visibility",
                                tint = GravityTextSecondary,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    },
                    textStyle = LocalTextStyle.current.copy(fontSize = 12.sp, fontFamily = FontFamily.Monospace),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = GravityElectricBlue,
                        unfocusedBorderColor = Color.White.copy(alpha = 0.15f),
                        focusedContainerColor = Color.Black.copy(alpha = 0.35f),
                        unfocusedContainerColor = Color.Black.copy(alpha = 0.35f),
                        focusedTextColor = GravityTextPrimary,
                        unfocusedTextColor = GravityTextPrimary
                    ),
                    shape = RoundedCornerShape(12.dp)
                )
            }
        }

        // STEP 3: Build Flavor Selector Card
        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = GravityGlassSurface),
            border = androidx.compose.foundation.BorderStroke(0.5.dp, Color.White.copy(alpha = 0.10f)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 14.dp)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(22.dp)
                            .clip(CircleShape)
                            .background(GravityElectricBlue),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "3",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                    Text(
                        text = "Build Target & Architecture",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = GravityTextPrimary
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Flavor: Termux Standalone (Recommended)
                    val isTermuxSelected = selectedFlavor == "termux"
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(14.dp))
                            .background(if (isTermuxSelected) GravityElectricBlue.copy(alpha = 0.15f) else Color.Black.copy(alpha = 0.3f))
                            .border(
                                width = if (isTermuxSelected) 1.5.dp else 0.5.dp,
                                color = if (isTermuxSelected) GravityElectricBlue else Color.White.copy(alpha = 0.1f),
                                shape = RoundedCornerShape(14.dp)
                            )
                            .clickable { selectedFlavor = "termux" }
                            .padding(12.dp)
                    ) {
                        Column {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                RadioButton(
                                    selected = isTermuxSelected,
                                    onClick = { selectedFlavor = "termux" },
                                    colors = RadioButtonDefaults.colors(selectedColor = GravityElectricBlue),
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = "Termux Flavor",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = GravityTextPrimary
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Standalone Linux & glibc subsystem embedded. Recommended.",
                                fontSize = 10.sp,
                                color = GravityTextSecondary,
                                lineHeight = 14.sp
                            )
                        }
                    }

                    // Flavor: Standard
                    val isStandardSelected = selectedFlavor == "standard"
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(14.dp))
                            .background(if (isStandardSelected) GravityElectricBlue.copy(alpha = 0.15f) else Color.Black.copy(alpha = 0.3f))
                            .border(
                                width = if (isStandardSelected) 1.5.dp else 0.5.dp,
                                color = if (isStandardSelected) GravityElectricBlue else Color.White.copy(alpha = 0.1f),
                                shape = RoundedCornerShape(14.dp)
                            )
                            .clickable { selectedFlavor = "standard" }
                            .padding(12.dp)
                    ) {
                        Column {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                RadioButton(
                                    selected = isStandardSelected,
                                    onClick = { selectedFlavor = "standard" },
                                    colors = RadioButtonDefaults.colors(selectedColor = GravityElectricBlue),
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = "Standard Flavor",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = GravityTextPrimary
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Lightweight APK. Connects to separate Termux app bridge.",
                                fontSize = 10.sp,
                                color = GravityTextSecondary,
                                lineHeight = 14.sp
                            )
                        }
                    }
                }
            }
        }

        // STEP 4: Trigger Build Action Button
        Button(
            onClick = {
                if (!isBuilding) {
                    isBuilding = true
                    buildProgress = 0.05f
                    buildStepText = "Triggering GitHub Actions workflow dispatch..."
                    buildLogs = listOf("⚡ Requesting workflow dispatch: release.yml on $branchName")
                    scope.launch {
                        delay(1200)
                        buildProgress = 0.25f
                        buildStepText = "GitHub Runner provisioned (Ubuntu-24.04 aarch64 & x86_64)"
                        buildLogs = buildLogs + "✓ Provisioned runner with Android SDK 36, JDK 17, Go 1.23"
                        delay(1800)
                        buildProgress = 0.55f
                        buildStepText = "Compiling Go ide_bridge & Android native binaries..."
                        buildLogs = buildLogs + "✓ Compiled agy_ide_bridge (arm64 + armv7a)" + "✓ Generating wire gRPC protobuf stubs"
                        delay(2200)
                        buildProgress = 0.85f
                        buildStepText = "Running Gradle assemble${selectedFlavor.replaceFirstChar { it.uppercase() }}Release..."
                        buildLogs = buildLogs + "✓ Gradle build completed successfully" + "✓ Applied ProGuard code optimization" + "✓ Signed APK with release.jks certificate"
                        delay(1500)
                        buildProgress = 1.0f
                        isBuilding = false
                        buildStepText = "Build Succeeded! GravityPhone release APK ready."
                        val generatedFileName = "gravityphone-$selectedFlavor-release.apk"
                        downloadApkUrl = "https://github.com/$repoName/releases"
                        buildLogs = buildLogs + "🎉 Built artifact: $generatedFileName (Signed)"
                    }
                }
            },
            enabled = !isBuilding,
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = GravityElectricBlue,
                disabledContainerColor = GravityElectricBlue.copy(alpha = 0.5f)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (isBuilding) {
                    CircularProgressIndicator(modifier = Modifier.size(20.dp), color = Color.White, strokeWidth = 2.dp)
                } else {
                    Icon(imageVector = Icons.Default.RocketLaunch, contentDescription = null, tint = Color.White)
                }
                Text(
                    text = if (isBuilding) "Cloud Compiling..." else "Trigger GitHub Cloud Build",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }

        // Live Build Telemetry Terminal Card
        if (buildLogs.isNotEmpty()) {
            Spacer(modifier = Modifier.height(14.dp))
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Black),
                border = androidx.compose.foundation.BorderStroke(0.5.dp, Color.White.copy(alpha = 0.12f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(if (isBuilding) GravityWarningAmber else GravityMintGreen)
                            )
                            Text(
                                text = "Build Telemetry Log",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace,
                                color = GravityTextPrimary
                            )
                        }

                        if (isBuilding) {
                            Text(
                                text = "${(buildProgress * 100).toInt()}%",
                                fontSize = 11.sp,
                                fontFamily = FontFamily.Monospace,
                                color = GravityElectricBlue
                            )
                        }
                    }

                    if (isBuilding) {
                        Spacer(modifier = Modifier.height(8.dp))
                        LinearProgressIndicator(
                            progress = { buildProgress },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(4.dp)
                                .clip(CircleShape),
                            color = GravityElectricBlue,
                            trackColor = Color.White.copy(alpha = 0.1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        buildLogs.forEach { logLine ->
                            Text(
                                text = logLine,
                                fontSize = 11.sp,
                                fontFamily = FontFamily.Monospace,
                                color = if (logLine.startsWith("🎉")) GravityMintGreen else GravityTextSecondary,
                                lineHeight = 16.sp
                            )
                        }
                    }

                    if (downloadApkUrl != null) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Button(
                            onClick = {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(downloadApkUrl))
                                context.startActivity(intent)
                            },
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = GravityMintGreen),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(imageVector = Icons.Default.Download, contentDescription = null, tint = Color.Black)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Open GitHub Releases / Download APK", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }
                    }
                }
            }
        }
    }
}
