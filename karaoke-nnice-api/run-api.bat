@echo off
setlocal enabledelayedexpansion
set JAVA_HOME=C:\Program Files\Java\jdk-21
set PATH=C:\Program Files\Java\jdk-21\bin;C:\Users\giang\.maven\apache-maven-3.9.11\bin;%PATH%
cd /d D:\User\File\Code\PTTKHDT_GIT\PTTKHDT_KaraokeNNice\karaoke-nnice-api
echo Starting Karaoke API...
echo Listening on http://localhost:8080
echo Swagger UI: http://localhost:8080/swagger-ui/index.html
echo.
mvn spring-boot:run
pause
