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
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun TransactionsScreen(navController: NavController) {
    var selectedFilter by remember { mutableStateOf("All") }
    var searchQuery by remember { mutableStateOf("") }
    
    val filters = listOf("All", "Income", "Expense", "Transfer")
    val filteredTransactions = SampleData.sampleTransactions.filter { transaction ->
        val matchesFilter = when (selectedFilter) {
            "All" -> true
            "Income" -> transaction.type == TransactionType.INCOME
            "Expense" -> transaction.type == TransactionType.EXPENSE
            "Transfer" -> transaction.type == TransactionType.TRANSFER
            else -> true
        }
        val matchesSearch = transaction.description.contains(searchQuery, ignoreCase = true) ||
                transaction.category.displayName.contains(searchQuery, ignoreCase = true)
        matchesFilter && matchesSearch
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(FinanceBlack, FinanceBlackLight)
                )
            )
    ) {
        // Header
        TransactionHeader(navController)
        
        // Search and Filter
        SearchAndFilterSection(
            searchQuery = searchQuery,
            onSearchQueryChange = { searchQuery = it },
            selectedFilter = selectedFilter,
            onFilterChange = { selectedFilter = it },
            filters = filters
        )
        
        // Transactions List
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(filteredTransactions) { transaction ->
                TransactionCard(transaction)
            }
        }
    }
}

@Composable
fun TransactionHeader(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
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
            text = "Transactions",
            style = MaterialTheme.typography.headlineSmall,
            color = FinanceWhite,
            fontWeight = FontWeight.Bold
        )
        
        IconButton(
            onClick = { /* Add transaction */ },
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
                contentDescription = "Add Transaction",
                tint = FinanceGreen,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun SearchAndFilterSection(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    selectedFilter: String,
    onFilterChange: (String) -> Unit,
    filters: List<String>
) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        // Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            modifier = Modifier
                .fillMaxWidth()
                .shadow(8.dp, RoundedCornerShape(12.dp), spotColor = FinanceGreenGlow.copy(alpha = 0.3f)),
            placeholder = {
                Text(
                    text = "Search transactions...",
                    color = FinanceWhiteDim
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = FinanceGreen
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = FinanceGreen,
                unfocusedBorderColor = FinanceGrayLight,
                focusedTextColor = FinanceWhite,
                unfocusedTextColor = FinanceWhite,
                cursorColor = FinanceGreen
            ),
            shape = RoundedCornerShape(12.dp)
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        // Filter Chips
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(filters) { filter ->
                FilterChip(
                    selected = selectedFilter == filter,
                    onClick = { onFilterChange(filter) },
                    label = {
                        Text(
                            text = filter,
                            color = if (selectedFilter == filter) FinanceBlack else FinanceWhite
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = FinanceGreen,
                        containerColor = FinanceGray
                    ),
                    shape = RoundedCornerShape(20.dp)
                )
            }
        }
    }
}

@Composable
fun TransactionCard(transaction: Transaction) {
    val formatter = NumberFormat.getCurrencyInstance(Locale.US)
    val dateFormatter = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(8.dp, RoundedCornerShape(12.dp), spotColor = transaction.category.color.copy(alpha = 0.3f)),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = FinanceGray
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Category Icon
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        transaction.category.color.copy(alpha = 0.2f),
                        RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = transaction.category.icon,
                    fontSize = 20.sp
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            // Transaction Details
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = transaction.description,
                    style = MaterialTheme.typography.titleMedium,
                    color = FinanceWhite,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = transaction.category.displayName,
                        style = MaterialTheme.typography.bodySmall,
                        color = FinanceWhiteDim
                    )
                    
                    Spacer(modifier = Modifier.width(8.dp))
                    
                    Text(
                        text = "•",
                        style = MaterialTheme.typography.bodySmall,
                        color = FinanceWhiteDim
                    )
                    
                    Spacer(modifier = Modifier.width(8.dp))
                    
                    Text(
                        text = dateFormatter.format(transaction.date),
                        style = MaterialTheme.typography.bodySmall,
                        color = FinanceWhiteDim
                    )
                }
            }
            
            // Amount
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "${if (transaction.type == TransactionType.INCOME) "+" else "-"}${formatter.format(transaction.amount)}",
                    style = MaterialTheme.typography.titleMedium,
                    color = if (transaction.type == TransactionType.INCOME) FinanceGreen else FinanceRed,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = transaction.type.name,
                    style = MaterialTheme.typography.bodySmall,
                    color = FinanceWhiteDim
                )
            }
        }
    }
}

@Composable
fun TransactionSummaryCard() {
    val totalIncome = SampleData.sampleTransactions
        .filter { it.type == TransactionType.INCOME }
        .sumOf { it.amount }
    
    val totalExpense = SampleData.sampleTransactions
        .filter { it.type == TransactionType.EXPENSE }
        .sumOf { it.amount }
    
    val netAmount = totalIncome - totalExpense
    val formatter = NumberFormat.getCurrencyInstance(Locale.US)
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
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
                text = "This Month",
                style = MaterialTheme.typography.titleMedium,
                color = FinanceWhiteDim,
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                SummaryItem(
                    label = "Income",
                    amount = totalIncome,
                    color = FinanceGreen
                )
                SummaryItem(
                    label = "Expense",
                    amount = totalExpense,
                    color = FinanceRed
                )
                SummaryItem(
                    label = "Net",
                    amount = netAmount,
                    color = if (netAmount >= 0) FinanceGreen else FinanceRed
                )
            }
        }
    }
}

@Composable
fun SummaryItem(label: String, amount: Double, color: Color) {
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