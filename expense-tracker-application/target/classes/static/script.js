console.log('🚀 Script loaded!');

// API Base URL
const API_BASE = 'http://localhost:8081/api';

// Global variables
let categories = [];
let transactions = [];

// Wait for DOM to be ready
document.addEventListener('DOMContentLoaded', function() {
    console.log('📄 DOM loaded, starting initialization...');
    
    // Add a small delay to ensure everything is ready
    setTimeout(() => {
        initApp();
    }, 100);
});

async function initApp() {
    console.log('🔄 Initializing app...');
    
    try {
        // Test if we can reach the API
        console.log('🌐 Testing API connection...');
        
        // Load categories first
        await loadCategories();
        
        // Load transactions
        await loadTransactions();
        
        // Setup forms
        setupForms();
        
        // Set default date
        setDefaultDate();
        
        console.log('✅ App initialization complete!');
        
    } catch (error) {
        console.error('❌ App initialization failed:', error);
        showError('Failed to initialize app: ' + error.message);
    }
}

async function loadCategories() {
    console.log('📂 Loading categories...');
    
    try {
        const response = await fetch(`${API_BASE}/categories`);
        console.log('📂 Categories response status:', response.status);
        
        if (!response.ok) {
            throw new Error(`HTTP ${response.status}: ${response.statusText}`);
        }
        
        categories = await response.json();
        console.log('📂 Categories loaded:', categories);
        
        // Populate dropdowns immediately
        populateDropdowns();
        
        return categories;
        
    } catch (error) {
        console.error('❌ Error loading categories:', error);
        showError('Failed to load categories: ' + error.message);
        categories = [];
        populateDropdowns(); // Still populate with empty array
        throw error;
    }
}

async function loadTransactions() {
    console.log('💰 Loading transactions...');
    
    try {
        const response = await fetch(`${API_BASE}/transactions`);
        console.log('💰 Transactions response status:', response.status);
        
        if (!response.ok) {
            throw new Error(`HTTP ${response.status}: ${response.statusText}`);
        }
        
        transactions = await response.json();
        console.log('💰 Transactions loaded:', transactions);
        
        // Update displays immediately
        updateRecentTransactions();
        updateStats();
        
        return transactions;
        
    } catch (error) {
        console.error('❌ Error loading transactions:', error);
        showError('Failed to load transactions: ' + error.message);
        transactions = [];
        updateRecentTransactions(); // Still update with empty array
        throw error;
    }
}

function populateDropdowns() {
    console.log('🔽 Populating dropdowns with', categories.length, 'categories');
    
    // Find all category dropdowns
    const dropdownIds = ['category', 'quick-category', 'category-filter'];
    
    dropdownIds.forEach(id => {
        const dropdown = document.getElementById(id);
        if (dropdown) {
            console.log(`🔽 Populating dropdown: ${id}`);
            
            // Clear existing options
            dropdown.innerHTML = '<option value="">Select Category</option>';
            
            // Add categories
            categories.forEach(category => {
                const option = document.createElement('option');
                option.value = category.id;
                option.textContent = category.name;
                dropdown.appendChild(option);
                console.log(`  ➕ Added option: ${category.name} (${category.id})`);
            });
            
            console.log(`✅ Dropdown ${id} populated with ${categories.length} categories`);
        } else {
            console.warn(`⚠️ Dropdown ${id} not found in DOM`);
        }
    });
}

function updateRecentTransactions() {
    console.log('📋 Updating recent transactions...');
    
    const container = document.getElementById('recent-transactions');
    if (!container) {
        console.warn('⚠️ Recent transactions container not found');
        return;
    }
    
    if (transactions.length === 0) {
        container.innerHTML = '<p>No transactions yet. Add your first expense!</p>';
        console.log('📋 No transactions to display');
        return;
    }
    
    // Sort by date (newest first) and take first 5
    const recent = transactions
        .sort((a, b) => new Date(b.transactionDate) - new Date(a.transactionDate))
        .slice(0, 5);
    
    const html = recent.map(t => `
        <div class="transaction-item">
            <div class="transaction-info">
                <h4>${t.description}</h4>
                <p>${t.categoryName} • ${formatDate(t.transactionDate)}</p>
            </div>
            <div class="transaction-amount">₹${t.amount.toFixed(2)}</div>
        </div>
    `).join('');
    
    container.innerHTML = html;
    console.log(`📋 Displayed ${recent.length} recent transactions`);
}

function updateStats() {
    console.log('📊 Updating stats...');
    
    const total = transactions.reduce((sum, t) => sum + t.amount, 0);
    
    // Calculate this month's total
    const now = new Date();
    const thisMonth = transactions.filter(t => {
        const tDate = new Date(t.transactionDate);
        return tDate.getMonth() === now.getMonth() && tDate.getFullYear() === now.getFullYear();
    });
    const monthTotal = thisMonth.reduce((sum, t) => sum + t.amount, 0);
    
    // Update DOM
    const totalEl = document.getElementById('total-expenses');
    const monthEl = document.getElementById('month-expenses');
    const countEl = document.getElementById('transaction-count');
    
    if (totalEl) {
        totalEl.textContent = `₹${total.toFixed(2)}`;
        console.log('📊 Updated total expenses:', totalEl.textContent);
    }
    
    if (monthEl) {
        monthEl.textContent = `₹${monthTotal.toFixed(2)}`;
        console.log('📊 Updated month expenses:', monthEl.textContent);
    }
    
    if (countEl) {
        countEl.textContent = transactions.length;
        console.log('📊 Updated transaction count:', countEl.textContent);
    }
}

function setupForms() {
    console.log('📝 Setting up forms...');
    
    // Quick expense form
    const quickForm = document.getElementById('quick-expense-form');
    if (quickForm) {
        quickForm.addEventListener('submit', async (e) => {
            e.preventDefault();
            console.log('⚡ Quick expense form submitted');
            
            const description = document.getElementById('quick-description').value;
            const amount = parseFloat(document.getElementById('quick-amount').value);
            const categoryId = parseInt(document.getElementById('quick-category').value);
            
            if (!description || !amount || !categoryId) {
                showError('Please fill all fields');
                return;
            }
            
            try {
                await addTransaction({
                    description,
                    amount,
                    categoryId,
                    transactionDate: new Date().toISOString().split('T')[0]
                });
                
                quickForm.reset();
                showSuccess('Expense added successfully!');
                
            } catch (error) {
                showError('Failed to add expense: ' + error.message);
            }
        });
        console.log('✅ Quick expense form setup complete');
    }
    
    // Regular transaction form
    const transactionForm = document.getElementById('transaction-form');
    if (transactionForm) {
        transactionForm.addEventListener('submit', async (e) => {
            e.preventDefault();
            console.log('💰 Transaction form submitted');
            
            const description = document.getElementById('description').value;
            const amount = parseFloat(document.getElementById('amount').value);
            const categoryId = parseInt(document.getElementById('category').value);
            const date = document.getElementById('date').value;
            
            if (!description || !amount || !categoryId || !date) {
                showError('Please fill all fields');
                return;
            }
            
            try {
                await addTransaction({
                    description,
                    amount,
                    categoryId,
                    transactionDate: date
                });
                
                transactionForm.reset();
                setDefaultDate();
                showSuccess('Transaction added successfully!');
                
            } catch (error) {
                showError('Failed to add transaction: ' + error.message);
            }
        });
        console.log('✅ Transaction form setup complete');
    }
}

async function addTransaction(data) {
    console.log('💾 Adding transaction:', data);
    
    const response = await fetch(`${API_BASE}/transactions`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    });
    
    if (!response.ok) {
        throw new Error(`HTTP ${response.status}: ${response.statusText}`);
    }
    
    // Reload data
    await loadTransactions();
    
    console.log('✅ Transaction added successfully');
}

function setDefaultDate() {
    const dateInput = document.getElementById('date');
    if (dateInput) {
        dateInput.value = new Date().toISOString().split('T')[0];
    }
}

function formatDate(dateString) {
    return new Date(dateString).toLocaleDateString('en-IN');
}

function showError(message) {
    console.error('❌', message);
    alert('Error: ' + message); // Simple alert for now
}

function showSuccess(message) {
    console.log('✅', message);
    alert('Success: ' + message); // Simple alert for now
}

// Tab switching
function showTab(tabName) {
    console.log('📑 Switching to tab:', tabName);
    
    // Hide all tabs
    document.querySelectorAll('.tab-content').forEach(tab => {
        tab.classList.remove('active');
    });
    
    // Remove active from all buttons
    document.querySelectorAll('.tab-btn').forEach(btn => {
        btn.classList.remove('active');
    });
    
    // Show selected tab
    const tab = document.getElementById(tabName);
    if (tab) {
        tab.classList.add('active');
    }
    
    // Mark button as active
    if (event && event.target) {
        event.target.classList.add('active');
    }
}

// Make functions available globally
window.showTab = showTab;
window.loadCategories = loadCategories;
window.loadTransactions = loadTransactions;

console.log('📜 Script setup complete!');