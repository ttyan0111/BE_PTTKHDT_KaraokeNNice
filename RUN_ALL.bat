@echo off
chcp 65001 >nul
color 0A
title Karaoke NNice - API Server

echo.
echo ╔════════════════════════════════════════════════════════════╗
echo ║        KARAOKE NNICE API - STARTUP SCRIPT                 ║
echo ╚════════════════════════════════════════════════════════════╝
echo.

REM Check if Docker is running
echo [1/3] Checking Docker status...
docker ps >nul 2>&1
if errorlevel 1 (
    echo ❌ Docker is not running!
    echo Please start Docker Desktop first
    pause
    exit /b 1
)
echo ✅ Docker is running

REM Start MySQL Container
echo.
echo [2/3] Starting MySQL Docker container...
docker-compose up -d >nul 2>&1
if errorlevel 1 (
    echo ❌ Failed to start Docker container
    pause
    exit /b 1
)
echo ✅ MySQL container started

REM Wait for MySQL to be ready
echo.
echo Waiting for MySQL to be ready...
timeout /t 3 /nobreak

REM Build and Start Spring Boot
echo.
echo [3/3] Building and starting Spring Boot API...
cd "%~dp0karaoke-nnice-api"

REM Clean target directory first
if exist target (
    echo Cleaning target directory...
    rmdir /s /q target >nul 2>&1
)

REM Build with Maven
call mvn clean compile package -DskipTests -Dmaven.compiler.useIncrementalCompilation=false
if errorlevel 1 (
    echo ❌ Build failed
    pause
    exit /b 1
)
echo ✅ Build successful

echo.
echo ╔════════════════════════════════════════════════════════════╗
echo ║  🎉 STARTING KARAOKE NNICE API SERVER                      ║
echo ║  📍 Swagger UI: http://localhost:8080/swagger-ui.html     ║
echo ║  📍 API Base: http://localhost:8080/api/v1/              ║
echo ║  ⚙️  Port: 8080                                            ║
echo ╚════════════════════════════════════════════════════════════╝
echo.

java -jar target/karaoke-nnice-api-1.0.0.jar

pause
