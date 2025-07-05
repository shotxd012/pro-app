package com.tokyomc.shot.ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.TrendingUp
import androidx.compose.material.icons.outlined.TrendingDown
import androidx.compose.material.icons.outlined.AccountBalance
import androidx.compose.material.icons.outlined.Savings
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.tokyomc.shot.data.*
import com.tokyomc.shot.ui.theme.*
import java.text.NumberFormat
import java.util.*

@Composable
fun AnalyticsScreen(navController: NavController) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(FinanceBlack, FinanceBlackLight)
                )
            )
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            AnalyticsHeader(navController)
        }
        
        item {
            OverviewCard()
        }
        
        item {
            CategoryBreakdownCard()
        }
        
        item {
            MonthlyTrendCard()
        }
        
        item {
            InsightsCard()
        }
        
        item {
            SpendingPatternsCard()
        }
    }
}

@Composable
fun AnalyticsHeader(navController: NavController) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = { navController.navigateUp() },
            modifier = Modifier
                .size(48.dp)
                .shadow(8.dp, RoundedCornerShape(12.dp), spotColor = FinanceGreenGlow)
                .background(
                    FinanceGray,
                    RoundedCornerShape(12.dp)
                )
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = FinanceGreen,
                modifier = Modifier.size(24.dp)
            )
        }
        
        Text(
            text = "Analytics",
            style = MaterialTheme.typography.headlineSmall,
            color = FinanceWhite,
            fontWeight = FontWeight.Bold
        )
        
        IconButton(
            onClick = { /* Share analytics */ },
            modifier = Modifier
                .size(48.dp)
                .shadow(8.dp, RoundedCornerShape(12.dp), spotColor = FinanceGreenGlow)
                .background(
                    FinanceGray,
                    RoundedCornerShape(12.dp)
                )
        ) {
            Icon(
                imageVector = Icons.Outlined.Share,
                contentDescription = "Share",
                tint = FinanceGreen,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun OverviewCard() {
    val totalIncome = SampleData.sampleTransactions
        .filter { it.type == TransactionType.INCOME }
        .sumOf { it.amount }
    
    val totalExpense = SampleData.sampleTransactions
        .filter { it.type == TransactionType.EXPENSE }
        .sumOf { it.amount }
    
    val netAmount = totalIncome - totalExpense
    val savingsRate = if (totalIncome > 0) (netAmount / totalIncome) * 100 else 0.0
    val formatter = NumberFormat.getCurrencyInstance(Locale.US)
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(12.dp, RoundedCornerShape(16.dp), spotColor = FinanceGreenGlow.copy(alpha = 0.4f)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = FinanceGray
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "Financial Overview",
                style = MaterialTheme.typography.titleLarge,
                color = FinanceWhite,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OverviewItem(
                    label = "Total Income",
                    amount = totalIncome,
                    color = FinanceGreen,
                    icon = Icons.Outlined.TrendingUp
                )
                OverviewItem(
                    label = "Total Expense",
                    amount = totalExpense,
                    color = FinanceRed,
                    icon = Icons.Outlined.TrendingDown
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Divider(color = FinanceGrayLight, thickness = 1.dp)
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OverviewItem(
                    label = "Net Amount",
                    amount = netAmount,
                    color = if (netAmount >= 0) FinanceGreen else FinanceRed,
                    icon = Icons.Outlined.AccountBalance
                )
                OverviewItem(
                    label = "Savings Rate",
                    amount = savingsRate,
                    color = FinanceGreen,
                    icon = Icons.Outlined.Savings,
                    isPercentage = true
                )
            }
        }
    }
}

@Composable
fun OverviewItem(
    label: String,
    amount: Double,
    color: Color,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    isPercentage: Boolean = false
) {
    val formatter = NumberFormat.getCurrencyInstance(Locale.US)
    val displayValue = if (isPercentage) "${String.format("%.1f", amount)}%" else formatter.format(amount)
    
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = color,
            modifier = Modifier.size(24.dp)
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = displayValue,
            style = MaterialTheme.typography.titleMedium,
            color = color,
            fontWeight = FontWeight.Bold
        )
        
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = FinanceWhiteDim,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun CategoryBreakdownCard() {
    val categoryTotals = SampleData.sampleTransactions
        .filter { it.type == TransactionType.EXPENSE }
        .groupBy { it.category }
        .mapValues { (_, transactions) -> transactions.sumOf { it.amount } }
        .toList()
        .sortedByDescending { it.second }
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(8.dp, RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = FinanceGray
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Expense by Category",
                style = MaterialTheme.typography.titleLarge,
                color = FinanceWhite,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            categoryTotals.forEach { (category, amount) ->
                CategoryBreakdownItem(category, amount)
                if (categoryTotals.indexOf(category to amount) < categoryTotals.size - 1) {
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
fun CategoryBreakdownItem(category: TransactionCategory, amount: Double) {
    val formatter = NumberFormat.getCurrencyInstance(Locale.US)
    val totalExpense = SampleData.sampleTransactions
        .filter { it.type == TransactionType.EXPENSE }
        .sumOf { it.amount }
    val percentage = if (totalExpense > 0) (amount / totalExpense) * 100 else 0.0
    
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = category.icon,
            fontSize = 20.sp
        )
        
        Spacer(modifier = Modifier.width(12.dp))
        
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = category.displayName,
                style = MaterialTheme.typography.titleSmall,
                color = FinanceWhite,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = "${String.format("%.1f", percentage)}%",
                style = MaterialTheme.typography.bodySmall,
                color = FinanceWhiteDim
            )
        }
        
        Text(
            text = formatter.format(amount),
            style = MaterialTheme.typography.titleSmall,
            color = FinanceGreen,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun MonthlyTrendCard() {
    val monthlyData = listOf(
        MonthlyData("Jan", 5000.0, 3200.0, 1800.0),
        MonthlyData("Feb", 5200.0, 3100.0, 2100.0),
        MonthlyData("Mar", 4800.0, 3400.0, 1400.0),
        MonthlyData("Apr", 5500.0, 2900.0, 2600.0),
        MonthlyData("May", 5300.0, 3300.0, 2000.0),
        MonthlyData("Jun", 5800.0, 2800.0, 3000.0)
    )
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(8.dp, RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = FinanceGray
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Monthly Trend",
                style = MaterialTheme.typography.titleLarge,
                color = FinanceWhite,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(monthlyData) { data ->
                    MonthlyTrendItem(data)
                }
            }
        }
    }
}

@Composable
fun MonthlyTrendItem(data: MonthlyData) {
    val formatter = NumberFormat.getCurrencyInstance(Locale.US)
    
    Card(
        modifier = Modifier
            .width(120.dp)
            .shadow(4.dp, RoundedCornerShape(8.dp)),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = FinanceGrayLight
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = data.month,
                style = MaterialTheme.typography.titleSmall,
                color = FinanceWhite,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = formatter.format(data.income),
                style = MaterialTheme.typography.bodySmall,
                color = FinanceGreen,
                fontWeight = FontWeight.Medium
            )
            
            Text(
                text = formatter.format(data.expense),
                style = MaterialTheme.typography.bodySmall,
                color = FinanceRed,
                fontWeight = FontWeight.Medium
            )
            
            Text(
                text = formatter.format(data.netAmount),
                style = MaterialTheme.typography.bodySmall,
                color = if (data.netAmount >= 0) FinanceGreen else FinanceRed,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun InsightsCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(8.dp, RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = FinanceGray
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Financial Insights",
                style = MaterialTheme.typography.titleLarge,
                color = FinanceWhite,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            InsightItem(
                icon = Icons.Outlined.TrendingUp,
                title = "Strong Income Growth",
                description = "Your income has increased by 15% this month",
                color = FinanceGreen
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            InsightItem(
                icon = Icons.Outlined.Warning,
                title = "High Food Spending",
                description = "Food expenses are 20% above your budget",
                color = FinanceOrange
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            InsightItem(
                icon = Icons.Outlined.Savings,
                title = "Good Savings Rate",
                description = "You're saving 35% of your income",
                color = FinanceGreen
            )
        }
    }
}

@Composable
fun InsightItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    description: String,
    color: Color
) {
    Row(
        verticalAlignment = Alignment.Top
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = color,
            modifier = Modifier.size(20.dp)
        )
        
        Spacer(modifier = Modifier.width(12.dp))
        
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                color = FinanceWhite,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = FinanceWhiteDim
            )
        }
    }
}

@Composable
fun SpendingPatternsCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(8.dp, RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = FinanceGray
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Spending Patterns",
                style = MaterialTheme.typography.titleLarge,
                color = FinanceWhite,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            PatternItem(
                label = "Most Expensive Day",
                value = "Friday",
                description = "You spend 25% more on Fridays"
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            PatternItem(
                label = "Favorite Category",
                value = "Food & Dining",
                description = "30% of your expenses go to food"
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            PatternItem(
                label = "Best Saving Day",
                value = "Monday",
                description = "You save 40% more on Mondays"
            )
        }
    }
}

@Composable
fun PatternItem(label: String, value: String, description: String) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.titleSmall,
            color = FinanceWhite,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = FinanceGreen,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = description,
            style = MaterialTheme.typography.bodySmall,
            color = FinanceWhiteDim
        )
    }
} 