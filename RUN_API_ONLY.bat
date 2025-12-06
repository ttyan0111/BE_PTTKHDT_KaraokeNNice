@echo off
chcp 65001 >nul
color 0A
title Karaoke NNice - API Server

echo.
echo ╔════════════════════════════════════════════════════════════╗
echo ║        KARAOKE NNICE API - QUICK START                    ║
echo ║        (MySQL container must be running)                  ║
echo ╚════════════════════════════════════════════════════════════╝
echo.

cd "D:\User\File\Code\PTTKHDT_GIT\PTTKHDT_KaraokeNNice\karaoke-nnice-api"

echo [1/2] Building application...
call mvn clean package -DskipTests -q
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
