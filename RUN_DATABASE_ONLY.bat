@echo off
chcp 65001 >nul
color 09
title Karaoke NNice - Database Only

echo.
echo ╔════════════════════════════════════════════════════════════╗
echo ║     KARAOKE NNICE - DATABASE SETUP ONLY                   ║
echo ║     (No API server - for testing/querying)                ║
echo ╚════════════════════════════════════════════════════════════╝
echo.

cd "D:\User\File\Code\PTTKHDT_GIT\PTTKHDT_KaraokeNNice"

echo [1/3] Checking Docker status...
docker ps >nul 2>&1
if errorlevel 1 (
    echo ❌ Docker is not running!
    echo Please start Docker Desktop first
    pause
    exit /b 1
)
echo ✅ Docker is running

echo.
echo [2/3] Starting MySQL Docker container...
docker-compose up -d >nul 2>&1
if errorlevel 1 (
    echo ❌ Failed to start Docker container
    pause
    exit /b 1
)
echo ✅ MySQL container started

echo.
echo [3/3] Waiting for MySQL to be ready...
timeout /t 3 /nobreak

echo.
echo ╔════════════════════════════════════════════════════════════╗
echo ║  ✅ DATABASE IS READY                                      ║
echo ║                                                            ║
echo ║  💻 Connect via MySQL Workbench:                           ║
echo ║     Host: localhost                                        ║
echo ║     Port: 3306                                             ║
echo ║     User: karaoke_user                                     ║
echo ║     Pass: karaoke_pass                                     ║
echo ║     DB: KaraokeNiceDB                                      ║
echo ║                                                            ║
echo ║  📋 To run SQL script: RUN_SQL_ONLY.bat                    ║
echo ║  🚀 To run API: RUN_API_ONLY.bat                           ║
echo ║  🛑 To stop: STOP_ALL.bat                                  ║
echo ╚════════════════════════════════════════════════════════════╝
echo.

pause
