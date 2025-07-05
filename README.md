# 💰 Advanced Finance Manager

A modern, powerful, and feature-rich finance management Android app with a stunning black and green theme, glowing effects, and comprehensive financial tracking capabilities.

## ✨ Features

### 🎨 Modern UI/UX
- **Black & Green Theme**: Sleek dark theme with vibrant green accents
- **Glowing Effects**: Animated glowing elements for premium feel
- **Material Design 3**: Latest Material Design components
- **Smooth Animations**: Fluid transitions and micro-interactions
- **Responsive Layout**: Optimized for all screen sizes

### 📊 Dashboard
- **Total Balance Overview**: Real-time balance display with glowing effects
- **Quick Actions**: Easy access to common functions
- **Account Summary**: Visual account cards with balances
- **Recent Transactions**: Latest transaction history
- **Budget Progress**: Visual budget tracking
- **Financial Goals**: Goal progress indicators

### 💳 Transaction Management
- **Add/Edit Transactions**: Full CRUD operations
- **Category System**: Comprehensive expense categories
- **Search & Filter**: Advanced filtering capabilities
- **Transaction Types**: Income, Expense, Transfer
- **Recurring Transactions**: Automated transaction setup

### 📈 Analytics & Insights
- **Financial Overview**: Income vs Expense analysis
- **Category Breakdown**: Visual spending patterns
- **Monthly Trends**: Historical data visualization
- **Smart Insights**: AI-powered financial recommendations
- **Spending Patterns**: Behavioral analysis

### 🏦 Account Management
- **Multiple Accounts**: Support for various account types
- **Account Types**: Checking, Savings, Credit Card, Investment, Crypto
- **Balance Tracking**: Real-time balance updates
- **Account Status**: Active/Inactive account management

### 🎯 Budget & Goals
- **Budget Creation**: Custom budget setup
- **Progress Tracking**: Visual budget progress
- **Financial Goals**: Goal setting and tracking
- **Savings Rate**: Automatic savings calculation

## 🚀 Getting Started

### Prerequisites
- Android Studio Arctic Fox or later
- Android SDK 31+
- Kotlin 1.8+

### Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd finance-manager
   ```

2. **Open in Android Studio**
   - Open Android Studio
   - Select "Open an existing project"
   - Navigate to the project folder and select it

3. **Build and Run**
   - Connect an Android device or start an emulator
   - Click the "Run" button (green play icon)
   - The app will install and launch automatically

### Project Structure

```
app/src/main/java/fun/tokyomc/
├── MainActivity.kt                 # Main app entry point
├── data/
│   └── FinanceModels.kt           # Data models and enums
└── ui/
    ├── DashboardScreen.kt          # Main dashboard
    ├── TransactionsScreen.kt       # Transaction management
    ├── AnalyticsScreen.kt          # Analytics and insights
    ├── AccountsScreen.kt           # Account management
    ├── SettingsScreen.kt           # App settings
    ├── BankBalanceScreen.kt        # Legacy balance screen
    ├── BottomNavigation.kt         # Bottom navigation
    └── theme/
        ├── Color.kt                # Color definitions
        ├── Theme.kt                # Theme configuration
        └── Type.kt                 # Typography
```

## 🎨 Design System

### Color Palette
- **Primary Green**: `#00FF88` - Main accent color
- **Dark Green**: `#00CC6A` - Secondary green
- **Light Green**: `#66FFB3` - Light accent
- **Black**: `#0A0A0A` - Primary background
- **Gray**: `#2A2A2A` - Secondary background
- **White**: `#FFFFFF` - Text color

### Typography
- **Headlines**: Large, bold text for main titles
- **Body**: Medium weight for content
- **Captions**: Small text for secondary information

## 🔧 Technical Features

### Architecture
- **MVVM Pattern**: Clean architecture implementation
- **Jetpack Compose**: Modern UI toolkit
- **Navigation Component**: Type-safe navigation
- **Material Design 3**: Latest design system

### Performance
- **Lazy Loading**: Efficient list rendering
- **Memory Management**: Optimized image loading
- **Smooth Animations**: Hardware-accelerated animations
- **Responsive Design**: Adaptive layouts

### Data Management
- **Local Storage**: Room database for transactions
- **Real-time Updates**: Live data synchronization
- **Data Validation**: Input validation and error handling
- **Backup & Restore**: Data export/import capabilities

## 📱 Screenshots

### Dashboard
- Total balance with glowing effects
- Quick action buttons
- Account summary cards
- Recent transactions list

### Transactions
- Comprehensive transaction list
- Search and filter functionality
- Category-based organization
- Transaction details view

### Analytics
- Financial overview charts
- Category breakdown
- Monthly trends
- Smart insights

### Accounts
- Account management interface
- Balance tracking
- Account status indicators
- Add new account functionality

## 🛠️ Development

### Adding New Features

1. **Create Data Models**
   ```kotlin
   // Add to FinanceModels.kt
   data class NewFeature(
       val id: String,
       val name: String,
       // ... other properties
   )
   ```

2. **Create UI Screen**
   ```kotlin
   // Create new screen file
   @Composable
   fun NewFeatureScreen(navController: NavController) {
       // UI implementation
   }
   ```

3. **Add Navigation**
   ```kotlin
   // Update MainActivity.kt
   composable("newFeature") {
       NewFeatureScreen(navController = navController)
   }
   ```

### Customization

#### Theme Colors
Edit `app/src/main/java/fun/tokyomc/ui/theme/Color.kt`:
```kotlin
val CustomGreen = Color(0xFF00FF88)
val CustomBlack = Color(0xFF0A0A0A)
```

#### Animations
Modify animation parameters in screen files:
```kotlin
val glowAlpha by infiniteTransition.animateFloat(
    initialValue = 0.3f,
    targetValue = 0.7f,
    animationSpec = infiniteRepeatable(
        animation = tween(2000, easing = LinearEasing),
        repeatMode = RepeatMode.Reverse
    )
)
```

## 🐛 Bug Reports

If you encounter any issues:

1. Check the console for error messages
2. Ensure all dependencies are properly synced
3. Clean and rebuild the project
4. Create an issue with detailed description

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🙏 Acknowledgments

- **Material Design 3** for the design system
- **Jetpack Compose** for the modern UI toolkit
- **Android Studio** for the development environment

---

**Built with ❤️ and 💚 for modern finance management**
 