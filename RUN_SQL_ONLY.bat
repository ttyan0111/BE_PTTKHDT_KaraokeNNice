@echo off
chcp 65001 >nul
color 0B
title Karaoke NNice - SQL Script Runner

echo.
echo ╔════════════════════════════════════════════════════════════╗
echo ║        KARAOKE NNICE - SQL SCRIPT RUNNER                  ║
echo ║        (MySQL container must be running)                  ║
echo ╚════════════════════════════════════════════════════════════╝
echo.

set MYSQL_HOST=localhost
set MYSQL_PORT=3306
set MYSQL_USER=karaoke_user
set MYSQL_PASS=karaoke_pass
set MYSQL_DB=KaraokeNiceDB
set SQL_FILE=D:\User\File\Code\PTTKHDT_GIT\PTTKHDT_KaraokeNNice\ScriptPTTKHDT.sql

echo Checking MySQL connection...
mysql -h %MYSQL_HOST% -P %MYSQL_PORT% -u %MYSQL_USER% -p%MYSQL_PASS% -e "SELECT 1;" >nul 2>&1

if errorlevel 1 (
    echo.
    echo ❌ Cannot connect to MySQL!
    echo Make sure Docker MySQL container is running
    echo.
    echo Connection details:
    echo   Host: %MYSQL_HOST%
    echo   Port: %MYSQL_PORT%
    echo   User: %MYSQL_USER%
    echo.
    pause
    exit /b 1
)

echo ✅ MySQL connection successful!
echo.
echo Running SQL script: %SQL_FILE%
echo.

mysql -h %MYSQL_HOST% -P %MYSQL_PORT% -u %MYSQL_USER% -p%MYSQL_PASS% < %SQL_FILE%

if errorlevel 1 (
    echo.
    echo ❌ SQL script execution failed!
    pause
    exit /b 1
)

echo.
echo ✅ SQL script executed successfully!
echo.
echo You can now:
echo   - Open MySQL Workbench to view data
echo   - Run Spring Boot API
echo.

pause
