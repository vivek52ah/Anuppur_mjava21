@echo off
echo ========================================
echo REBUILDING ANUPPUR APPLICATION
echo ========================================
echo.
echo This will:
echo 1. Clean old compiled files
echo 2. Rebuild the entire application
echo 3. Start the application on port 8085
echo.
echo Press Ctrl+C to cancel, or
pause

echo.
echo ========================================
echo STEP 1: Cleaning old build...
echo ========================================
call mvn clean
if %ERRORLEVEL% NEQ 0 (
    echo.
    echo ERROR: Clean failed!
    echo Please check if Maven is installed correctly.
    pause
    exit /b 1
)

echo.
echo ========================================
echo STEP 2: Compiling and packaging...
echo ========================================
call mvn clean install -DskipTests
if %ERRORLEVEL% NEQ 0 (
    echo.
    echo ERROR: Build failed!
    echo Please check the error messages above.
    pause
    exit /b 1
)

echo.
echo ========================================
echo BUILD SUCCESSFUL!
echo ========================================
echo.
echo All fixes have been compiled successfully.
echo.
echo NEXT STEPS:
echo 1. Start the application by running: run.bat
echo 2. Wait for "Started DmsAnuppurApplication" message
echo 3. Open browser and go to: http://localhost:8085
echo 4. Clear browser cache (Ctrl+Shift+Delete)
echo 5. Hard refresh (Ctrl+F5)
echo 6. Test all the fixed features
echo.
echo ========================================
echo.
pause
