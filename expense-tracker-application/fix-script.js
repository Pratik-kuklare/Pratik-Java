// Missing functions to add to script.js

// Add transaction
async function addTransaction(event) {
    event.preventDefault();
    
    const formData = {
        description: document.getElementById('description').value,
        amount: parseFloat(document.getElementById('amount').value),
        transactionDate: document.getElementById('date').value,
        categoryId: parseInt(document.getElementById('category').value)
    };
    
    try {
        const response = await fetch(`${API_BASE}/transactions`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(formData)
        });
        
        if (response.ok) {
            showNotification('Transaction added successfully!', 'success');
            document.getElementById('transaction-form').reset();
            setDefaultDate();
            loadTransactions();
        } else {
            showNotification('Error adding transaction', 'error');
        }
    } catch (error) {
        showNotification('Error adding transaction', 'error');
    }
}

// Add quick expense
async function addQuickExpense(event) {
    event.preventDefault();
    
    const formData = {
        description: document.getElementById('quick-description').value,
        amount: parseFloat(document.getElementById('quick-amount').value),
        transactionDate: new Date().toISOString().split('T')[0],
        categoryId: parseInt(document.getElementById('quick-category').value)
    };
    
    try {
        const response = await fetch(`${API_BASE}/transactions`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(formData)
        });
        
        if (response.ok) {
            showNotification('Expense added successfully!', 'success');
            document.getElementById('quick-expense-form').reset();
            loadDashboard();
        } else {
            showNotification('Error adding expense', 'error');
        }
    } catch (error) {
        showNotification('Error adding expense', 'error');
    }
}

// Add category
async function addCategory(event) {
    event.preventDefault();
    
    const formData = {
        name: document.getElementById('category-name').value,
        description: document.getElementById('category-description').value
    };
    
    try {
        const response = await fetch(`${API_BASE}/categories`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(formData)
        });
        
        if (response.ok) {
            showNotification('Category added successfully!', 'success');
            document.getElementById('category-form').reset();
            loadCategories();
        } else {
            showNotification('Error adding category', 'error');
        }
    } catch (error) {
        showNotification('Error adding category', 'error');
    }
}

// Delete functions
async function deleteTransaction(id) {
    if (!confirm('Are you sure you want to delete this transaction?')) return;
    
    try {
        const response = await fetch(`${API_BASE}/transactions/${id}`, { method: 'DELETE' });
        if (response.ok) {
            showNotification('Transaction deleted successfully!', 'success');
            loadTransactions();
            loadDashboard();
        } else {
            showNotification('Error deleting transaction', 'error');
        }
    } catch (error) {
        showNotification('Error deleting transaction', 'error');
    }
}

async function deleteCategory(id) {
    if (!confirm('Are you sure you want to delete this category?')) return;
    
    try {
        const response = await fetch(`${API_BASE}/categories/${id}`, { method: 'DELETE' });
        if (response.ok) {
            showNotification('Category deleted successfully!', 'success');
            loadCategories();
        } else {
            showNotification('Error deleting category', 'error');
        }
    } catch (error) {
        showNotification('Error deleting category', 'error');
    }
}

// Generate report
async function generateReport() {
    const monthInput = document.getElementById('report-month').value;
    if (!monthInput) {
        showNotification('Please select a month', 'error');
        return;
    }
    
    const [year, month] = monthInput.split('-');
    
    try {
        const response = await fetch(`${API_BASE}/reports/monthly/${year}/${month}`);
        const report = await response.json();
        displayReport(report);
    } catch (error) {
        showNotification('Error generating report', 'error');
    }
}

// Display report
function displayReport(report) {
    const reportContent = document.getElementById('report-content');
    
    const categoryBreakdownHtml = Object.entries(report.categoryBreakdown || {})
        .map(([category, amount]) => {
            const percentage = ((amount / report.totalAmount) * 100).toFixed(1);
            return `
                <div class="breakdown-item">
                    <div class="category-info">
                        <span class="category-name">${category}</span>
                        <div class="category-bar">
                            <div class="category-fill" style="width: ${percentage}%"></div>
                        </div>
                        <span class="category-percentage">${percentage}%</span>
                    </div>
                    <span class="category-amount">₹${amount.toFixed(2)}</span>
                </div>
            `;
        }).join('');
    
    const avgPerTransaction = report.transactionCount > 0 ? 
        (report.totalAmount / report.transactionCount).toFixed(2) : '0.00';
    
    const topCategory = Object.entries(report.categoryBreakdown || {})
        .sort(([,a], [,b]) => b - a)[0];
    
    reportContent.innerHTML = `
        <div class="report-summary">
            <div class="report-card">
                <h4>💰 Total Spent</h4>
                <div class="value">₹${report.totalAmount.toFixed(2)}</div>
            </div>
            <div class="report-card">
                <h4>📊 Transactions</h4>
                <div class="value">${report.transactionCount}</div>
            </div>
            <div class="report-card">
                <h4>📈 Average/Transaction</h4>
                <div class="value">₹${avgPerTransaction}</div>
            </div>
            <div class="report-card">
                <h4>🏆 Top Category</h4>
                <div class="value">${topCategory ? topCategory[0] : 'N/A'}</div>
            </div>
        </div>
        
        <div class="category-breakdown">
            <h4>📋 Spending by Category</h4>
            <div class="breakdown-container">
                ${categoryBreakdownHtml || '<p>No transactions found for this period</p>'}
            </div>
        </div>
    `;
}

// Categorized expenses
async function loadCategorizedExpenses() {
    await loadTransactions();
    displayCategorizedExpenses();
}

function displayCategorizedExpenses() {
    const categorizedExpensesList = document.getElementById('categorized-expenses-list');
    
    if (transactions.length === 0) {
        categorizedExpensesList.innerHTML = '<div class="no-expenses">No expenses found. Add your first expense!</div>';
        return;
    }
    
    const groupedTransactions = {};
    transactions.forEach(transaction => {
        const categoryName = transaction.categoryName;
        if (!groupedTransactions[categoryName]) {
            groupedTransactions[categoryName] = [];
        }
        groupedTransactions[categoryName].push(transaction);
    });
    
    const sortedCategories = Object.keys(groupedTransactions).sort((a, b) => {
        const totalA = groupedTransactions[a].reduce((sum, t) => sum + t.amount, 0);
        const totalB = groupedTransactions[b].reduce((sum, t) => sum + t.amount, 0);
        return totalB - totalA;
    });
    
    let html = '';
    sortedCategories.forEach(categoryName => {
        const categoryTransactions = groupedTransactions[categoryName];
        const categoryTotal = categoryTransactions.reduce((sum, t) => sum + t.amount, 0);
        
        html += `
            <div class="category-header">📁 ${categoryName}</div>
            <div class="category-total">Total: ₹${categoryTotal.toFixed(2)} (${categoryTransactions.length} transactions)</div>
        `;
        
        const sortedTransactions = categoryTransactions.sort((a, b) => 
            new Date(b.transactionDate) - new Date(a.transactionDate)
        );
        
        sortedTransactions.forEach(transaction => {
            html += `
                <div class="categorized-expense-item">
                    <div class="expense-details">
                        <h4>${transaction.description}</h4>
                        <div class="expense-meta">
                            <span>📅 ${formatDate(transaction.transactionDate)}</span>
                            <span>🏷️ ${transaction.categoryName}</span>
                        </div>
                    </div>
                    <div class="expense-amount">₹${transaction.amount.toFixed(2)}</div>
                </div>
            `;
        });
    });
    
    categorizedExpensesList.innerHTML = html;
}

function filterExpensesByCategory() {
    const selectedCategoryId = document.getElementById('category-filter').value;
    const categorySummary = document.getElementById('category-summary');
    const expensesTitle = document.getElementById('categorized-expenses-title');
    
    if (!selectedCategoryId) {
        expensesTitle.textContent = 'All Categorized Expenses';
        categorySummary.innerHTML = '<p>Select a category to view detailed summary</p>';
        displayCategorizedExpenses();
        return;
    }
    
    const filteredTransactions = transactions.filter(t => t.categoryId == selectedCategoryId);
    const selectedCategory = categories.find(c => c.id == selectedCategoryId);
    
    if (!selectedCategory) return;
    
    expensesTitle.textContent = `${selectedCategory.name} Expenses`;
    
    const totalAmount = filteredTransactions.reduce((sum, t) => sum + t.amount, 0);
    const transactionCount = filteredTransactions.length;
    const avgAmount = transactionCount > 0 ? (totalAmount / transactionCount) : 0;
    
    categorySummary.innerHTML = `
        <h4>📊 ${selectedCategory.name} Summary</h4>
        <div class="category-summary-stats">
            <div class="category-stat">
                <span class="category-stat-label">Total Spent</span>
                <span class="category-stat-value">₹${totalAmount.toFixed(2)}</span>
            </div>
            <div class="category-stat">
                <span class="category-stat-label">Transactions</span>
                <span class="category-stat-value">${transactionCount}</span>
            </div>
            <div class="category-stat">
                <span class="category-stat-label">Average Amount</span>
                <span class="category-stat-value">₹${avgAmount.toFixed(2)}</span>
            </div>
        </div>
    `;
    
    const categorizedExpensesList = document.getElementById('categorized-expenses-list');
    
    if (filteredTransactions.length === 0) {
        categorizedExpensesList.innerHTML = `<div class="no-expenses">No expenses found for ${selectedCategory.name}</div>`;
        return;
    }
    
    const sortedTransactions = filteredTransactions.sort((a, b) => 
        new Date(b.transactionDate) - new Date(a.transactionDate)
    );
    
    const html = sortedTransactions.map(transaction => `
        <div class="categorized-expense-item">
            <div class="expense-details">
                <h4>${transaction.description}</h4>
                <div class="expense-meta">
                    <span>📅 ${formatDate(transaction.transactionDate)}</span>
                    <span>🏷️ ${transaction.categoryName}</span>
                </div>
            </div>
            <div class="expense-amount">₹${transaction.amount.toFixed(2)}</div>
        </div>
    `).join('');
    
    categorizedExpensesList.innerHTML = html;
}

// Utility functions
function formatDate(dateString) {
    return new Date(dateString).toLocaleDateString();
}

function showNotification(message, type) {
    const notification = document.getElementById('notification');
    notification.textContent = message;
    notification.className = `notification ${type} show`;
    
    setTimeout(() => {
        notification.classList.remove('show');
    }, 3000);
}