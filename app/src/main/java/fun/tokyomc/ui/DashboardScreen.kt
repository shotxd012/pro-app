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
import androidx.compose.material.icons.outlined.AccountBalance
import androidx.compose.material.icons.outlined.PieChart
import androidx.compose.material.icons.outlined.Savings
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
fun DashboardScreen(navController: NavController) {
    val infiniteTransition = rememberInfiniteTransition()
    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.7f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

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
            HeaderSection()
        }
        
        item {
            TotalBalanceCard(glowAlpha)
        }
        
        item {
            QuickActionsRow(navController)
        }
        
        item {
            AccountsSection()
        }
        
        item {
            RecentTransactionsSection()
        }
        
        item {
            BudgetsSection()
        }
        
        item {
            FinancialGoalsSection()
        }
    }
}

@Composable
fun HeaderSection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = "Welcome back!",
                style = MaterialTheme.typography.headlineSmall,
                color = FinanceWhite,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Manage your finances",
                style = MaterialTheme.typography.bodyMedium,
                color = FinanceWhiteDim
            )
        }
        
        IconButton(
            onClick = { /* Settings */ },
            modifier = Modifier
                .size(48.dp)
                .shadow(8.dp, RoundedCornerShape(12.dp), spotColor = FinanceGreenGlow)
                .background(
                    FinanceGray,
                    RoundedCornerShape(12.dp)
                )
        ) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Settings",
                tint = FinanceGreen,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun TotalBalanceCard(glowAlpha: Float) {
    val totalBalance = SampleData.sampleAccounts.sumOf { it.balance }
    val formatter = NumberFormat.getCurrencyInstance(Locale.US)
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 16.dp,
                shape = RoundedCornerShape(20.dp),
                spotColor = FinanceGreenGlow.copy(alpha = glowAlpha)
            ),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = FinanceGray
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.linearGradient(
                        colors = listOf(
                            FinanceGray,
                            FinanceGrayLight
                        )
                    )
                )
                .padding(24.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Total Balance",
                    style = MaterialTheme.typography.titleMedium,
                    color = FinanceWhiteDim,
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = formatter.format(totalBalance),
                    style = MaterialTheme.typography.displayLarge.copy(
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    color = FinanceGreen,
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    BalanceItem(
                        label = "Income",
                        amount = 6200.0,
                        color = FinanceGreen
                    )
                    BalanceItem(
                        label = "Expense",
                        amount = 130.50,
                        color = FinanceRed
                    )
                }
            }
        }
    }
}

@Composable
fun BalanceItem(label: String, amount: Double, color: Color) {
    val formatter = NumberFormat.getCurrencyInstance(Locale.US)
    
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = FinanceWhiteDim
        )
        Text(
            text = formatter.format(amount),
            style = MaterialTheme.typography.titleMedium,
            color = color,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun QuickActionsRow(navController: NavController) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            QuickActionButton(
                icon = Icons.Default.Add,
                label = "Add Transaction",
                onClick = { /* Add transaction */ },
                color = FinanceGreen
            )
        }
        item {
            QuickActionButton(
                icon = Icons.Outlined.AccountBalance,
                label = "Accounts",
                onClick = { /* Navigate to accounts */ },
                color = FinanceBlue
            )
        }
        item {
            QuickActionButton(
                icon = Icons.Outlined.PieChart,
                label = "Analytics",
                onClick = { /* Navigate to analytics */ },
                color = FinancePurple
            )
        }
        item {
            QuickActionButton(
                icon = Icons.Outlined.Savings,
                label = "Goals",
                onClick = { /* Navigate to goals */ },
                color = FinanceOrange
            )
        }
    }
}

@Composable
fun QuickActionButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    onClick: () -> Unit,
    color: Color
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .width(100.dp)
            .shadow(8.dp, RoundedCornerShape(12.dp), spotColor = color.copy(alpha = 0.3f)),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = FinanceGray
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = color,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = FinanceWhite,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun AccountsSection() {
    Column {
        Text(
            text = "Your Accounts",
            style = MaterialTheme.typography.titleLarge,
            color = FinanceWhite,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(12.dp))
        
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(SampleData.sampleAccounts) { account ->
                AccountCard(account)
            }
        }
    }
}

@Composable
fun AccountCard(account: Account) {
    val formatter = NumberFormat.getCurrencyInstance(Locale.US)
    
    Card(
        modifier = Modifier
            .width(160.dp)
            .shadow(8.dp, RoundedCornerShape(12.dp), spotColor = account.color.copy(alpha = 0.3f)),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = FinanceGray
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = account.type.icon,
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = account.name,
                    style = MaterialTheme.typography.titleSmall,
                    color = FinanceWhite,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = formatter.format(account.balance),
                style = MaterialTheme.typography.titleMedium,
                color = if (account.balance >= 0) FinanceGreen else FinanceRed,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun RecentTransactionsSection() {
    Column {
        Text(
            text = "Recent Transactions",
            style = MaterialTheme.typography.titleLarge,
            color = FinanceWhite,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(12.dp))
        
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(SampleData.sampleTransactions.take(3)) { transaction ->
                TransactionItem(transaction)
            }
        }
    }
}

@Composable
fun TransactionItem(transaction: Transaction) {
    val formatter = NumberFormat.getCurrencyInstance(Locale.US)
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(8.dp)),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = FinanceGray
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        transaction.category.color.copy(alpha = 0.2f),
                        RoundedCornerShape(8.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = transaction.category.icon,
                    fontSize = 16.sp
                )
            }
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = transaction.description,
                    style = MaterialTheme.typography.titleSmall,
                    color = FinanceWhite,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = transaction.category.displayName,
                    style = MaterialTheme.typography.bodySmall,
                    color = FinanceWhiteDim
                )
            }
            
            Text(
                text = "${if (transaction.type == TransactionType.INCOME) "+" else "-"}${formatter.format(transaction.amount)}",
                style = MaterialTheme.typography.titleSmall,
                color = if (transaction.type == TransactionType.INCOME) FinanceGreen else FinanceRed,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun BudgetsSection() {
    Column {
        Text(
            text = "Budgets",
            style = MaterialTheme.typography.titleLarge,
            color = FinanceWhite,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(12.dp))
        
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(SampleData.sampleBudgets) { budget ->
                BudgetCard(budget)
            }
        }
    }
}

@Composable
fun BudgetCard(budget: Budget) {
    val progress = (budget.spent / budget.amount).coerceIn(0.0, 1.0)
    val formatter = NumberFormat.getCurrencyInstance(Locale.US)
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(8.dp)),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = FinanceGray
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = budget.name,
                    style = MaterialTheme.typography.titleSmall,
                    color = FinanceWhite,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${formatter.format(budget.spent)} / ${formatter.format(budget.amount)}",
                    style = MaterialTheme.typography.bodySmall,
                    color = FinanceWhiteDim
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            LinearProgressIndicator(
                progress = progress.toFloat(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = if (progress > 0.8f) FinanceRed else FinanceGreen,
                trackColor = FinanceGrayLight
            )
        }
    }
}

@Composable
fun FinancialGoalsSection() {
    Column {
        Text(
            text = "Financial Goals",
            style = MaterialTheme.typography.titleLarge,
            color = FinanceWhite,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(12.dp))
        
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(SampleData.sampleGoals) { goal ->
                GoalCard(goal)
            }
        }
    }
}

@Composable
fun GoalCard(goal: FinancialGoal) {
    val progress = (goal.currentAmount / goal.targetAmount).coerceIn(0.0, 1.0)
    val formatter = NumberFormat.getCurrencyInstance(Locale.US)
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(8.dp)),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = FinanceGray
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = goal.name,
                    style = MaterialTheme.typography.titleSmall,
                    color = FinanceWhite,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${(progress * 100).toInt()}%",
                    style = MaterialTheme.typography.bodySmall,
                    color = FinanceGreen,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "${formatter.format(goal.currentAmount)} / ${formatter.format(goal.targetAmount)}",
                style = MaterialTheme.typography.bodySmall,
                color = FinanceWhiteDim
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            LinearProgressIndicator(
                progress = progress.toFloat(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = FinanceGreen,
                trackColor = FinanceGrayLight
            )
        }
    }
} 