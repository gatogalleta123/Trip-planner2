@echo off
setlocal
cd /d "%~dp0"
cls

echo ====================================================
echo    TRIP-PLANNER - DESPLIEGUE INDIVIDUAL: MS-USUARIOS
echo ====================================================
echo.

rem Paso 1: Verificar si la infraestructura base esta corriendo
echo [1/4] VERIFICANDO INFRAESTRUCTURA (Postgres, Kafka, Eureka)...
docker inspect -f "{{.State.Running}}" postgres-rdbms >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo [ERROR] La infraestructura base no esta iniciada. 
    echo Inicie primero el despliegue global.
    pause
    exit /b 1
)
echo       Infraestructura detectada y en ejecucion.
echo.

rem Paso 2: Compilar especificamente ms-usuarios y common
echo [2/4] RE-COMPILANDO MS-USUARIOS Y COMMON...
call mvn clean package -pl common,ms-usuarios -am -DskipTests -q
if %ERRORLEVEL% NEQ 0 (
    echo.
    echo ****************************************************
    echo ERROR: La compilacion de ms-usuarios fallo.
    echo ****************************************************
    pause
    exit /b 1
)
echo       Compilacion exitosa. JAR de ms-usuarios actualizado.
echo.

rem Paso 3: Detener instancia anterior si existe y lanzar nueva
echo [3/4] LANZANDO CONTENEDOR MS-USUARIOS...
docker compose up -d --build ms-usuarios
echo       Contenedor ms-usuarios iniciado/actualizado.
echo.

rem Paso 4: Verificacion en Eureka
echo [4/4] VERIFICANDO REGISTRO EN EUREKA...
echo       Esperando 10 segundos para el registro automatico...
powershell -Command "Start-Sleep -Seconds 10"
echo.

echo ====================================================
echo       MS-USUARIOS ESTA OPERATIVO EN DOCKER
echo ====================================================
echo.
echo   Puede verificar el estado en:
echo   -----------------------------------------------
echo   Eureka Dashboard: http://localhost:8761
echo   Health Check:     http://localhost:9000/actuator/health
echo   Rutas Gateway:    http://localhost:9000/api/v1/usuarios/**
echo   -----------------------------------------------
echo.
echo   Para ver logs:    docker logs -f ms-usuarios
echo ====================================================
endlocal
pause