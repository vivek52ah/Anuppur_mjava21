@echo off
echo Building project...
call mvn clean compile -DskipTests
if %ERRORLEVEL% NEQ 0 (
    echo Build failed!
    pause
    exit /b 1
)

echo.
echo Build successful! Starting application...
echo.
call mvn spring-boot:run
pause
