package com.tokyomc.shot.data

import androidx.compose.ui.graphics.Color
import java.util.Date

// Transaction Types
enum class TransactionType {
    INCOME, EXPENSE, TRANSFER
}

// Transaction Categories
enum class TransactionCategory(val displayName: String, val icon: String, val color: Color) {
    SALARY("Salary", "💰", Color(0xFF00FF88)),
    FREELANCE("Freelance", "💼", Color(0xFF00B4FF)),
    INVESTMENT("Investment", "📈", Color(0xFF8A2BE2)),
    FOOD("Food & Dining", "🍽️", Color(0xFFFF6B35)),
    TRANSPORT("Transport", "🚗", Color(0xFFFFB74D)),
    SHOPPING("Shopping", "🛍️", Color(0xFFE91E63)),
    ENTERTAINMENT("Entertainment", "🎬", Color(0xFF9C27B0)),
    HEALTH("Healthcare", "🏥", Color(0xFF4CAF50)),
    EDUCATION("Education", "📚", Color(0xFF2196F3)),
    UTILITIES("Utilities", "⚡", Color(0xFFFF9800)),
    RENT("Rent", "🏠", Color(0xFF795548)),
    OTHER("Other", "📌", Color(0xFF607D8B))
}

// Account Types
enum class AccountType(val displayName: String, val icon: String) {
    CHECKING("Checking Account", "🏦"),
    SAVINGS("Savings Account", "💰"),
    CREDIT_CARD("Credit Card", "💳"),
    INVESTMENT("Investment Account", "📈"),
    CASH("Cash", "💵"),
    CRYPTO("Cryptocurrency", "₿")
}

// Data Classes
data class Transaction(
    val id: String = "",
    val amount: Double,
    val type: TransactionType,
    val category: TransactionCategory,
    val description: String,
    val date: Date,
    val accountId: String,
    val isRecurring: Boolean = false,
    val recurringPeriod: String? = null,
    val tags: List<String> = emptyList()
)

data class Account(
    val id: String = "",
    val name: String,
    val type: AccountType,
    val balance: Double,
    val currency: String = "USD",
    val isActive: Boolean = true,
    val color: Color = Color(0xFF00FF88)
)

data class Budget(
    val id: String = "",
    val name: String,
    val amount: Double,
    val spent: Double = 0.0,
    val period: String = "monthly", // monthly, weekly, yearly
    val categories: List<TransactionCategory> = emptyList(),
    val startDate: Date = Date(),
    val endDate: Date? = null
)

data class FinancialGoal(
    val id: String = "",
    val name: String,
    val targetAmount: Double,
    val currentAmount: Double = 0.0,
    val deadline: Date? = null,
    val category: TransactionCategory? = null,
    val isCompleted: Boolean = false
)

data class ExpenseAnalytics(
    val totalIncome: Double = 0.0,
    val totalExpense: Double = 0.0,
    val netAmount: Double = 0.0,
    val categoryBreakdown: Map<TransactionCategory, Double> = emptyMap(),
    val monthlyTrend: List<MonthlyData> = emptyList()
)

data class MonthlyData(
    val month: String,
    val income: Double,
    val expense: Double,
    val netAmount: Double
)

// Sample Data
object SampleData {
    val sampleAccounts = listOf(
        Account("1", "Main Checking", AccountType.CHECKING, 12500.50),
        Account("2", "Savings", AccountType.SAVINGS, 45000.00),
        Account("3", "Credit Card", AccountType.CREDIT_CARD, -1250.75),
        Account("4", "Investment", AccountType.INVESTMENT, 75000.00)
    )

    val sampleTransactions = listOf(
        Transaction(
            id = "1",
            amount = 5000.0,
            type = TransactionType.INCOME,
            category = TransactionCategory.SALARY,
            description = "Monthly Salary",
            date = Date(),
            accountId = "1"
        ),
        Transaction(
            id = "2",
            amount = 85.50,
            type = TransactionType.EXPENSE,
            category = TransactionCategory.FOOD,
            description = "Grocery Shopping",
            date = Date(),
            accountId = "1"
        ),
        Transaction(
            id = "3",
            amount = 45.00,
            type = TransactionType.EXPENSE,
            category = TransactionCategory.TRANSPORT,
            description = "Fuel",
            date = Date(),
            accountId = "1"
        ),
        Transaction(
            id = "4",
            amount = 1200.0,
            type = TransactionType.INCOME,
            category = TransactionCategory.FREELANCE,
            description = "Freelance Project",
            date = Date(),
            accountId = "1"
        )
    )

    val sampleBudgets = listOf(
        Budget("1", "Food & Dining", 500.0, 85.50, "monthly", listOf(TransactionCategory.FOOD)),
        Budget("2", "Transportation", 200.0, 45.0, "monthly", listOf(TransactionCategory.TRANSPORT)),
        Budget("3", "Entertainment", 300.0, 0.0, "monthly", listOf(TransactionCategory.ENTERTAINMENT))
    )

    val sampleGoals = listOf(
        FinancialGoal("1", "Emergency Fund", 10000.0, 5000.0),
        FinancialGoal("2", "Vacation Fund", 5000.0, 1500.0),
        FinancialGoal("3", "New Car", 25000.0, 8000.0)
    )
} 