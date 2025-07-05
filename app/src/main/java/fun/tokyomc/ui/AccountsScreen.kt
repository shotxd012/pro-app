package com.tokyomc.shot.ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
fun AccountsScreen(navController: NavController) {
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
            AccountsHeader(navController)
        }
        
        item {
            TotalAssetsCard()
        }
        
        item {
            Text(
                text = "Your Accounts",
                style = MaterialTheme.typography.titleLarge,
                color = FinanceWhite,
                fontWeight = FontWeight.Bold
            )
        }
        
        items(SampleData.sampleAccounts) { account ->
            AccountDetailCard(account)
        }
        
        item {
            AddAccountCard()
        }
    }
}

@Composable
fun AccountsHeader(navController: NavController) {
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
            text = "Accounts",
            style = MaterialTheme.typography.headlineSmall,
            color = FinanceWhite,
            fontWeight = FontWeight.Bold
        )
        
        IconButton(
            onClick = { /* Add account */ },
            modifier = Modifier
                .size(48.dp)
                .shadow(8.dp, RoundedCornerShape(12.dp), spotColor = FinanceGreenGlow)
                .background(
                    FinanceGray,
                    RoundedCornerShape(12.dp)
                )
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add Account",
                tint = FinanceGreen,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun TotalAssetsCard() {
    val totalAssets = SampleData.sampleAccounts.sumOf { it.balance }
    val formatter = NumberFormat.getCurrencyInstance(Locale.US)
    
    val infiniteTransition = rememberInfiniteTransition()
    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.7f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    
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
                    text = "Total Assets",
                    style = MaterialTheme.typography.titleMedium,
                    color = FinanceWhiteDim,
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = formatter.format(totalAssets),
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
                    AssetItem(
                        label = "Accounts",
                        count = SampleData.sampleAccounts.size,
                        color = FinanceBlue
                    )
                    AssetItem(
                        label = "Active",
                        count = SampleData.sampleAccounts.count { it.isActive },
                        color = FinanceGreen
                    )
                }
            }
        }
    }
}

@Composable
fun AssetItem(label: String, count: Int, color: Color) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = count.toString(),
            style = MaterialTheme.typography.titleLarge,
            color = color,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = FinanceWhiteDim
        )
    }
}

@Composable
fun AccountDetailCard(account: Account) {
    val formatter = NumberFormat.getCurrencyInstance(Locale.US)
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
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
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = account.type.icon,
                        fontSize = 24.sp
                    )
                    
                    Spacer(modifier = Modifier.width(12.dp))
                    
                    Column {
                        Text(
                            text = account.name,
                            style = MaterialTheme.typography.titleMedium,
                            color = FinanceWhite,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = account.type.displayName,
                            style = MaterialTheme.typography.bodySmall,
                            color = FinanceWhiteDim
                        )
                    }
                }
                
                if (account.isActive) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(
                                FinanceGreen,
                                RoundedCornerShape(4.dp)
                            )
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Balance",
                        style = MaterialTheme.typography.bodySmall,
                        color = FinanceWhiteDim
                    )
                    Text(
                        text = formatter.format(account.balance),
                        style = MaterialTheme.typography.titleLarge,
                        color = if (account.balance >= 0) FinanceGreen else FinanceRed,
                        fontWeight = FontWeight.Bold
                    )
                }
                
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { /* View transactions */ },
                        modifier = Modifier
                            .size(36.dp)
                            .background(
                                FinanceGrayLight,
                                RoundedCornerShape(8.dp)
                            )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Receipt,
                            contentDescription = "View Transactions",
                            tint = FinanceGreen,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    
                    Spacer(modifier = Modifier.width(8.dp))
                    
                    IconButton(
                        onClick = { /* Edit account */ },
                        modifier = Modifier
                            .size(36.dp)
                            .background(
                                FinanceGrayLight,
                                RoundedCornerShape(8.dp)
                            )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit Account",
                            tint = FinanceGreen,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AddAccountCard() {
    Card(
        onClick = { /* Add new account */ },
        modifier = Modifier
            .fillMaxWidth()
            .shadow(8.dp, RoundedCornerShape(12.dp), spotColor = FinanceGreenGlow.copy(alpha = 0.3f)),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = FinanceGray.copy(alpha = 0.5f)
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        FinanceGreen.copy(alpha = 0.2f),
                        RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Account",
                    tint = FinanceGreen,
                    modifier = Modifier.size(24.dp)
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column {
                Text(
                    text = "Add New Account",
                    style = MaterialTheme.typography.titleMedium,
                    color = FinanceGreen,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Connect your bank account or add manually",
                    style = MaterialTheme.typography.bodySmall,
                    color = FinanceWhiteDim
                )
            }
        }
    }
} 