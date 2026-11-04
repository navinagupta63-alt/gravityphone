package com.example.gemini.theme

import androidx.compose.ui.graphics.Color

// Gravity Phone Design System Tokens
// Dark Mode (Primary Theme)
val GravityCosmicBlack = Color(0xFF0A0C10)
val GravityGlassSurface = Color(0xFF161824)
val GravitySurfaceElevated = Color(0xFF1C1F2E)
val GravityDarkBorder = Color(0x14FFFFFF) // rgba(255, 255, 255, 0.08)
val GravityDarkBorderActive = Color(0x660A84FF) // rgba(10, 132, 255, 0.4)
val GravityTextPrimary = Color(0xFFFFFFFF)
val GravityTextSecondary = Color(0xFF8E8E93) // Muted Silver
val GravityTextTertiary = Color(0xFF636366)

// Accents
val GravityElectricBlue = Color(0xFF0A84FF)
val GravityMintGreen = Color(0xFF30D158)
val GravityVibrantPurple = Color(0xFFBF5AF2)
val GravityWarningAmber = Color(0xFFFF9F0A)
val GravityErrorRose = Color(0xFFFF453A)

// Glassmorphism overlays
val GravityGlassNavbarBg = Color(0xD910121C) // rgba(16, 18, 28, 0.85)
val GravityGlassCardBg = Color(0xBF161824) // rgba(22, 24, 36, 0.75)
val GravityComposerBg = Color(0xF2161824) // rgba(22, 24, 36, 0.95)
val GravityUserBubbleBg = Color(0x330A84FF) // rgba(10, 132, 255, 0.20)
val GravityUserBubbleBorder = Color(0x4D0A84FF) // rgba(10, 132, 255, 0.30)
val GravityCodeBg = Color(0xFF0E1018)

// Light Mode (Secondary Theme)
val GravityLightBg = Color(0xFFF2F2F7)
val GravityLightSurface = Color(0xFFFFFFFF)
val GravityLightBorder = Color(0xFFE5E5EA)
val GravityLightTextPrimary = Color(0xFF1C1C1E)
val GravityLightTextSecondary = Color(0xFF8E8E93)

// Backward-compatibility aliases for existing UI components
val ClaudeTerracotta = GravityElectricBlue
val ClaudeTerracottaDark = Color(0xFF0066CC)
val ClaudeCream = GravityLightBg
val ClaudeWarmSurface = GravityLightSurface
val ClaudeUserBubbleLight = Color(0xFFE5E5EA)
val ClaudeThinkingLight = Color(0xFFF2F2F7)

val ClaudeDarkBg = GravityCosmicBlack
val ClaudeDarkSurface = GravityGlassSurface
val ClaudeUserBubbleDark = GravityUserBubbleBg
val ClaudeThinkingDark = GravitySurfaceElevated

val CodeBlockBgDark = GravityCodeBg
val TextPrimaryLight = GravityLightTextPrimary
val TextSecondaryLight = GravityLightTextSecondary
val TextPrimaryDark = GravityTextPrimary
val TextSecondaryDark = GravityTextSecondary

val QuotaGreen = GravityMintGreen
val QuotaAmber = GravityWarningAmber
val QuotaRed = GravityErrorRose

val GeminiBlue = GravityElectricBlue
val GeminiPurple = GravityVibrantPurple
