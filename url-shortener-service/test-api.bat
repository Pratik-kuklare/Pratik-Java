@echo off
echo ========================================
echo    URL Shortener API Test Script
echo ========================================
echo.

echo Testing URL Shortening API...
echo.

powershell -Command "try { $response = Invoke-RestMethod -Uri 'http://localhost:8080/api/shorten' -Method POST -ContentType 'application/json' -Body '{\"url\": \"https://example.com\", \"expirationDays\": 30}'; Write-Host 'SUCCESS: URL Shortened!' -ForegroundColor Green; Write-Host 'Original URL:' $response.originalUrl; Write-Host 'Short URL:' $response.shortUrl; Write-Host 'Short Code:' $response.shortCode; Write-Host 'Created:' $response.createdAt; Write-Host 'Expires:' $response.expiresAt; $env:SHORT_CODE = $response.shortCode } catch { Write-Host 'ERROR: Failed to connect to API. Make sure the application is running.' -ForegroundColor Red }"

echo.
echo ========================================
echo Test completed. Check results above.
echo ========================================
pause