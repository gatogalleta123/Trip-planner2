@echo off
setlocal

:MENU
cls
echo.
echo ============================================
echo TRIPPLANNER - MENU PRINCIPAL
echo ============================================
echo.
echo [1] Iniciar todos los servicios (dev)
echo [2] Iniciar todos los servicios (test)
echo [3] Compilar microservicios
echo [4] Reinstalar dependencias Maven
echo.
echo — Servicios individuales —
echo [5] Iniciar Eureka
echo [6] Iniciar ms-destinos
echo [7] Iniciar ms-reservas
echo [8] Iniciar ms-usuarios
echo [9] Iniciar ms-hoteles
echo [10] Iniciar ms-vuelos
echo [11] Iniciar ms-auth
echo [12] Iniciar ms-notificaciones
echo [13] Iniciar ms-pagos
echo [14] Iniciar ms-resennas
echo [15] Iniciar ms-reportes
echo [16] Iniciar API Gateway
echo.
echo [0] Salir
echo.
echo ============================================
set /p opcion=” Selecciona una opcion: “

if “%opcion%”==“1” goto RUN_ALL
if “%opcion%”==“2” goto RUN_TEST
if “%opcion%”==“3” goto COMPILE
if “%opcion%”==“4” goto INSTALL
if “%opcion%”==“5” goto RUN_EUREKA
if “%opcion%”==“6” goto RUN_DESTINOS
if “%opcion%”==“7” goto RUN_RESERVAS
if “%opcion%”==“8” goto RUN_USUARIOS
if “%opcion%”==“9” goto RUN_HOTELES
if “%opcion%”==“10” goto RUN_VUELOS
if “%opcion%”==“11” goto RUN_AUTH
if “%opcion%”==“12” goto RUN_NOTIFICACIONES
if “%opcion%”==“13” goto RUN_PAGOS
if “%opcion%”==“14” goto RUN_RESENNAS
if “%opcion%”==“15” goto RUN_REPORTES
if “%opcion%”==“16” goto RUN_GATEWAY
if “%opcion%”==“0” goto SALIR

echo.
echo Opcion invalida. Intenta de nuevo.
timeout /t 2 /nobreak > nul
goto MENU

REM ============================================
:RUN_ALL
cls
echo.
echo ===== Iniciando Eureka Server =====
start “EUREKA” mvn -f eureka spring-boot:run
timeout /t 10 /nobreak > nul

echo ===== Iniciando Microservicios de Negocio =====
start “MS-DESTINOS” mvn -f ms-destinos spring-boot:run
start “MS-RESERVAS” mvn -f ms-reservas spring-boot:run
start “MS-USUARIOS” mvn -f ms-usuarios spring-boot:run
start “MS-HOTELES” mvn -f ms-hoteles spring-boot:run
start “MS-VUELOS” mvn -f ms-vuelos spring-boot:run
start “MS-AUTH” mvn -f ms-auth spring-boot:run
start “MS-NOTIFICACIONES” mvn -f ms-notificaciones spring-boot:run
start “MS-PAGOS” mvn -f ms-pagos spring-boot:run
start “MS-RESENNAS” mvn -f ms-resennas spring-boot:run
start “MS-REPORTES” mvn -f ms-reportes spring-boot:run

timeout /t 15 /nobreak > nul
echo ===== Iniciando API Gateway =====
start “API-GATEWAY” mvn -f api-gateway spring-boot:run

echo Todos los servicios han sido lanzados.
pause
goto MENU

:RUN_TEST
cls
echo.
echo ===== Iniciando Infraestructura (test) =====
start “EUREKA” java -jar eureka\target\cl-tripplanner-eureka-1.0-SNAPSHOT.jar –spring.profiles.active=test
timeout /t 10 /nobreak > nul

echo ===== Iniciando Microservicios (test) =====
start “MS-DESTINOS” java -jar ms-destinos\target\cl-tripplanner-destinos-0.0.1-SNAPSHOT.jar –spring.profiles.active=test
start “MS-USUARIOS” java -jar ms-usuarios\target\cl-tripplanner-usuarios-0.0.1-SNAPSHOT.jar –spring.profiles.active=test
start “MS-AUTH” java -jar ms-auth\target\cl-tripplanner-auth-0.0.1-SNAPSHOT.jar –spring.profiles.active=test

timeout /t 10 /nobreak > nul
start “API-GATEWAY” java -jar api-gateway\target\cl-tripplanner-gateway-0.0.1-SNAPSHOT.jar –spring.profiles.active=test
pause
goto MENU

:COMPILE
cls
echo ===== Compilando Proyecto y Modulo Common =====
call mvn clean install -DskipTests
echo.
echo Compilacion global completada.
pause
goto MENU

:INSTALL
cls
echo.
echo === REINSTALACION DE DEPENDENCIAS MAVEN ===
echo Eliminando carpetas target y cache local…
rmdir /s /q %USERPROFILE%.m2\repository\cl\tripplanner
mvn clean install -U -DskipTests
echo === PROCESO COMPLETADO ===
pause
goto MENU

:RUN_EUREKA
cls
start “EUREKA” mvn -f eureka spring-boot:run
goto MENU

:RUN_DESTINOS
cls
start “MS-DESTINOS” mvn -f ms-destinos spring-boot:run
goto MENU

:RUN_RESERVAS
cls
start “MS-RESERVAS” mvn -f ms-reservas spring-boot:run
goto MENU

:RUN_USUARIOS
cls
start “MS-USUARIOS” mvn -f ms-usuarios spring-boot:run
goto MENU

:RUN_HOTELES
cls
start “MS-HOTELES” mvn -f ms-hoteles spring-boot:run
goto MENU

:RUN_VUELOS
cls
start “MS-VUELOS” mvn -f ms-vuelos spring-boot:run
goto MENU

:RUN_AUTH
cls
start “MS-AUTH” mvn -f ms-auth spring-boot:run
goto MENU

:RUN_NOTIFICACIONES
cls
start “MS-NOTIFICACIONES” mvn -f ms-notificaciones spring-boot:run
goto MENU

:RUN_PAGOS
cls
start “MS-PAGOS” mvn -f ms-pagos spring-boot:run
goto MENU

:RUN_RESENNAS
cls
start “MS-RESENNAS” mvn -f ms-resennas spring-boot:run
goto MENU

:RUN_REPORTES
cls
start “MS-REPORTES” mvn -f ms-reportes spring-boot:run
goto MENU

:RUN_GATEWAY
cls
start “API-GATEWAY” mvn -f api-gateway spring-boot:run
goto MENU

:SALIR
exit /b