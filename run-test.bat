@echo off
echo ===== Iniciando Eureka Server =====
start "EUREKA" java -jar eureka\target\cl.tripplanner.turismo-eureka-1.0-SNAPSHOT.jar --spring.profiles.active=test

timeout /t 5 /nobreak > nul

echo ===== Iniciando Microservicios =====
start "MS-DESTINOS" java -jar ms-destinos\target\cl.tripplanner.turismo-destinos-0.0.1-SNAPSHOT.jar --spring.profiles.active=test
start "MS-RESERVAS" java -jar ms-reservas\target\cl.tripplanner.turismo-reservas-0.0.1-SNAPSHOT.jar --spring.profiles.active=test
start "MS-PAGOS" java -jar ms-pagos\target\cl.tripplanner.turismo-pagos-0.0.1-SNAPSHOT.jar --spring.profiles.active=test
start "MS-HOTELES" java -jar ms-hoteles\target\cl.tripplanner.turismo-hoteles-0.0.1-SNAPSHOT.jar --spring.profiles.active=test
start "MS-VUELOS" java -jar ms-vuelos\target\cl.tripplanner.turismo-vuelos-0.0.1-SNAPSHOT.jar --spring.profiles.active=test
start "MS-AUTH" java -jar ms-auth\target\cl.tripplanner.turismo-auth-0.0.1-SNAPSHOT.jar --spring.profiles.active=test
start "MS-NOTIFICACIONES" java -jar ms-notificaciones\target\cl.tripplanner.turismo-notificaciones-0.0.1-SNAPSHOT.jar --spring.profiles.active=test
start "MS-RESENNAS" java -jar ms-resennas\target\cl.tripplanner.turismo-resennas-0.0.1-SNAPSHOT.jar --spring.profiles.active=test
start "MS-REPORTES" java -jar ms-reportes\target\cl.tripplanner.turismo-reportes-0.0.1-SNAPSHOT.jar --spring.profiles.active=test
start "MS-USUARIOS" java -jar ms-usuarios\target\cl.tripplanner.turismo-usuarios-0.0.1-SNAPSHOT.jar --spring.profiles.active=test
rem Agrega aqui los demas microservicios si necesitas

echo Todos los servicios han sido lanzados.
