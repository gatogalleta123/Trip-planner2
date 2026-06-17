@echo off
echo.
echo === COMPILANDO MICROSERVICIOS ===
echo.

call cd C:\Users\AO-Alumno\Documents\GitHub\Trip-Planner\common

call mvn clean install -U

call cd C:\Users\AO-Alumno\Documents\GitHub\Trip-Planner\ms-destinos

call mvn clean install -U

call cd C:\Users\AO-Alumno\Documents\GitHub\Trip-Planner\ms-reservas

call mvn clean install -U

call cd C:\Users\AO-Alumno\Documents\GitHub\Trip-Planner\ms-pagos

call mvn clean install -U

call cd C:\Users\AO-Alumno\Documents\GitHub\Trip-Planner\ms-hoteles

call mvn clean install -U

call cd C:\Users\AO-Alumno\Documents\GitHub\Trip-Planner\ms-vuelos

call mvn clean install -U

call cd C:\Users\AO-Alumno\Documents\GitHub\Trip-Planner\ms-auth

call mvn clean install -U

call cd C:\Users\AO-Alumno\Documents\GitHub\Trip-Planner\ms-notificaciones

call mvn clean install -U

call cd C:\Users\AO-Alumno\Documents\GitHub\Trip-Planner\ms-resennas

call mvn clean install -U

call cd C:\Users\AO-Alumno\Documents\GitHub\Trip-Planner\ms-reportes

call mvn clean install -U

call cd C:\Users\AO-Alumno\Documents\GitHub\Trip-Planner\ms-usuarios

call mvn clean install -U
echo.
echo === COMPILACION COMPLETADA ===
pause
