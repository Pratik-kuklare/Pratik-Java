Write-Host "Testing Expense Tracker API on http://localhost:8081" -ForegroundColor Green
Write-Host ""

# Test Categories API
Write-Host "1. Testing Categories API..." -ForegroundColor Yellow
try {
    $response = Invoke-RestMethod -Uri "http://localhost:8081/api/categories" -Method GET
    Write-Host "SUCCESS - Categories found:" -ForegroundColor Green
    $response | ForEach-Object { 
        Write-Host "  - ID: $($_.id), Name: $($_.name), Description: $($_.description)" 
    }
} catch {
    Write-Host "ERROR:" -ForegroundColor Red
    Write-Host $_.Exception.Message
    if ($_.Exception.Response) {
        $reader = New-Object System.IO.StreamReader($_.Exception.Response.GetResponseStream())
        Write-Host "Response:" -ForegroundColor Red
        Write-Host $reader.ReadToEnd()
    }
}

Write-Host ""

# Test Transactions API
Write-Host "2. Testing Transactions API..." -ForegroundColor Yellow
try {
    $response = Invoke-RestMethod -Uri "http://localhost:8081/api/transactions" -Method GET
    Write-Host "SUCCESS - Transactions found:" -ForegroundColor Green
    $response | ForEach-Object { 
        Write-Host "  - ID: $($_.id), Description: $($_.description), Amount: $($_.amount), Category: $($_.categoryName)" 
    }
} catch {
    Write-Host "ERROR:" -ForegroundColor Red
    Write-Host $_.Exception.Message
}

Write-Host ""

# Test Reports API
Write-Host "3. Testing Reports API..." -ForegroundColor Yellow
try {
    $response = Invoke-RestMethod -Uri "http://localhost:8081/api/reports/monthly/2025/7" -Method GET
    Write-Host "SUCCESS - Monthly report generated:" -ForegroundColor Green
    Write-Host "  - Total Amount: $($response.totalAmount)"
    Write-Host "  - Transaction Count: $($response.transactionCount)"
    Write-Host "  - Categories:"
    $response.categoryBreakdown.PSObject.Properties | ForEach-Object {
        Write-Host "    * $($_.Name): $($_.Value)"
    }
} catch {
    Write-Host "ERROR:" -ForegroundColor Red
    Write-Host $_.Exception.Message
}

Write-Host ""
Write-Host "API Testing Complete!" -ForegroundColor Green
Read-Host "Press Enter to close"