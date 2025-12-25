@echo off
echo Testing BCrypt password hash...
cd karaoke-nnice-api
javac -cp "target/classes;%USERPROFILE%\.m2\repository\org\springframework\security\spring-security-crypto\6.1.5\spring-security-crypto-6.1.5.jar;%USERPROFILE%\.m2\repository\org\slf4j\slf4j-api\2.0.9\slf4j-api-2.0.9.jar" src\main\java\com\nnice\karaoke\util\PasswordHashTest.java
java -cp "src\main\java;target/classes;%USERPROFILE%\.m2\repository\org\springframework\security\spring-security-crypto\6.1.5\spring-security-crypto-6.1.5.jar;%USERPROFILE%\.m2\repository\org\slf4j\slf4j-api\2.0.9\slf4j-api-2.0.9.jar" com.nnice.karaoke.util.PasswordHashTest
pause
