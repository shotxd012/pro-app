package com.tokyomc.shot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tokyomc.shot.ui.*
import com.tokyomc.shot.ui.theme.ShotAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShotAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FinanceApp()
                }
            }
        }
    }
}

@Composable
fun FinanceApp() {
    val navController = rememberNavController()
    
    Scaffold(
        bottomBar = {
            FinanceBottomNavigation(navController = navController)
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "dashboard",
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("dashboard") {
                DashboardScreen(navController = navController)
            }
            composable("transactions") {
                TransactionsScreen(navController = navController)
            }
            composable("analytics") {
                AnalyticsScreen(navController = navController)
            }
            composable("accounts") {
                AccountsScreen(navController = navController)
            }
            composable("settings") {
                SettingsScreen(navController = navController)
            }
            composable("bankBalance") {
                BankBalanceScreen(navController = navController)
            }
        }
    }
}