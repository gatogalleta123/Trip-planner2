@echo off
echo ===== Iniciando Eureka Server =====
start "eureka" mvn -f eureka spring-boot:run

timeout /t 5 /nobreak > nul

echo ===== Iniciando Microservicios =====
start "ms-destinos" mvn -f ms-destinos spring-boot:run
start "ms-reservas" mvn -f ms-reservas spring-boot:run
start "ms-pagos" mvn -f ms-pagos spring-boot:run
start "ms-hoteles" mvn -f ms-hoteles spring-boot:run
start "ms-vuelos" mvn -f ms-vuelos spring-boot:run
start "ms-auth" mvn -f ms-auth spring-boot:run
start "ms-notificaciones" mvn -f ms-notificaciones spring-boot:run
start "ms-resennas" mvn -f ms-resennas spring-boot:run
start "ms-reportes" mvn -f ms-reportes spring-boot:run
start "ms-usuarios" mvn -f ms-usuarios spring-boot:run
rem Agrega aqui los demas microservicios si necesitas

echo Todos los servicios han sido lanzados.
