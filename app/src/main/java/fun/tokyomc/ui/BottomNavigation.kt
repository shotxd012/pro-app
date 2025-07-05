package com.tokyomc.shot.ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Dashboard
import androidx.compose.material.icons.outlined.Receipt
import androidx.compose.material.icons.outlined.Analytics
import androidx.compose.material.icons.outlined.AccountBalance
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.tokyomc.shot.ui.theme.*

@Composable
fun FinanceBottomNavigation(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    
    val infiniteTransition = rememberInfiniteTransition()
    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.7f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    NavigationBar(
        modifier = Modifier
            .shadow(
                elevation = 16.dp,
                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
                spotColor = FinanceGreenGlow.copy(alpha = glowAlpha)
            )
            .background(
                Brush.verticalGradient(
                    colors = listOf(FinanceGray, FinanceGrayLight)
                ),
                RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
            ),
        containerColor = Color.Transparent,
        tonalElevation = 0.dp
    ) {
        val items = listOf(
            BottomNavItem(
                route = "dashboard",
                title = "Dashboard",
                icon = Icons.Outlined.Dashboard,
                selectedIcon = Icons.Outlined.Dashboard
            ),
            BottomNavItem(
                route = "transactions",
                title = "Transactions",
                icon = Icons.Outlined.Receipt,
                selectedIcon = Icons.Outlined.Receipt
            ),
            BottomNavItem(
                route = "analytics",
                title = "Analytics",
                icon = Icons.Outlined.Analytics,
                selectedIcon = Icons.Outlined.Analytics
            ),
            BottomNavItem(
                route = "accounts",
                title = "Accounts",
                icon = Icons.Outlined.AccountBalance,
                selectedIcon = Icons.Outlined.AccountBalance
            ),
            BottomNavItem(
                route = "settings",
                title = "Settings",
                icon = Icons.Outlined.Settings,
                selectedIcon = Icons.Outlined.Settings
            )
        )

        items.forEach { item ->
            val isSelected = currentRoute == item.route
            
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = if (isSelected) item.selectedIcon else item.icon,
                        contentDescription = item.title,
                        modifier = Modifier.size(24.dp),
                        tint = if (isSelected) FinanceGreen else FinanceWhiteDim
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.labelSmall,
                        color = if (isSelected) FinanceGreen else FinanceWhiteDim,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                },
                selected = isSelected,
                onClick = {
                    if (!isSelected) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = FinanceGreen,
                    selectedTextColor = FinanceGreen,
                    unselectedIconColor = FinanceWhiteDim,
                    unselectedTextColor = FinanceWhiteDim,
                    indicatorColor = FinanceGreen.copy(alpha = 0.2f)
                )
            )
        }
    }
}

data class BottomNavItem(
    val route: String,
    val title: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val selectedIcon: androidx.compose.ui.graphics.vector.ImageVector
) 