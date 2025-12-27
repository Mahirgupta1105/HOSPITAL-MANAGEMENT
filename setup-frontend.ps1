# Setup script for Hospital Management System Frontend

Write-Host "Setting up Hospital Management System Frontend..." -ForegroundColor Green

# Navigate to frontend directory
Set-Location -Path "$PSScriptRoot\frontend"

# Install dependencies
Write-Host "Installing dependencies..." -ForegroundColor Cyan
npm install
npm install @stomp/stompjs @types/stompjs
npm install @material-ui/core @material-ui/icons @emotion/react @emotion/styled
npm install react-router-dom @types/react-router-dom
npm install @types/react @types/react-dom @types/node
npm install axios notistack

# Create necessary directories
$directories = @(
    "src\components\layout",
    "src\pages",
    "src\services",
    "src\utils",
    "src\assets",
    "src\styles"
)

foreach ($dir in $directories) {
    $fullPath = Join-Path -Path "$PSScriptRoot\frontend\src" -ChildPath $dir
    if (-not (Test-Path -Path $fullPath)) {
        New-Item -ItemType Directory -Path $fullPath | Out-Null
        Write-Host "Created directory: $fullPath" -ForegroundColor Green
    }
}

Write-Host "\nFrontend setup completed successfully!" -ForegroundColor Green
Write-Host "To start the development server, run: cd frontend && npm start" -ForegroundColor Yellow
