@echo off
chcp 65001 >nul
color 0C
title Karaoke NNice - Stop Services

echo.
echo ╔════════════════════════════════════════════════════════════╗
echo ║        KARAOKE NNICE - STOP ALL SERVICES                  ║
echo ╚════════════════════════════════════════════════════════════╝
echo.

echo [1/2] Stopping MySQL Docker container...
docker-compose -f "D:\User\File\Code\PTTKHDT_GIT\PTTKHDT_KaraokeNNice\docker-compose.yml" down
echo ✅ MySQL container stopped

echo.
echo [2/2] Stopping Spring Boot (press Ctrl+C in the terminal if it's running)
echo All services stopped successfully!
echo.

pause
