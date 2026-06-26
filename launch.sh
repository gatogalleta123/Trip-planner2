#!/bin/bash

# Función para abrir una nueva terminal y ejecutar un comando
run_in_new_terminal() {
    local title=$1
    local cmd=$2
    osascript -e "tell application \"Terminal\" to do script \"echo -n -e \\\"\\033]0;$title\\007\\\"; cd \\\"$(pwd)\\\"; $cmd\""
}

show_menu() {
    clear
    echo "============================================"
    echo "TRIPPLANNER - MENU PRINCIPAL (macOS)"
    echo "============================================"
    echo "[2] Iniciar todos los servicios (dev)"
    echo "[3] Iniciar todos los servicios (test)"
    echo "[4] Compilar microservicios"
    echo "[5] Reinstalar dependencias Maven"
    echo ""
    echo "--- Servicios individuales ---"
    echo "[6] Iniciar Eureka"
    echo "[7] Iniciar ms-destinos"
    echo "[8] Iniciar ms-reservas"
    echo "[9] Iniciar ms-usuarios"
    echo "[10] Iniciar ms-hoteles"
    echo "[11] Iniciar ms-vuelos"
    echo "[12] Iniciar ms-auth"
    echo "[13] Iniciar ms-notificaciones"
    echo "[14] Iniciar ms-pagos"
    echo "[15] Iniciar ms-resennas"
    echo "[16] Iniciar ms-reportes"
    echo "[17] Iniciar API Gateway"
    echo ""
    echo " Salir"
    echo "============================================"
    read -p "Selecciona una opcion: " opcion
}

while true; do
    show_menu
    case $opcion in
        1)
            echo "Iniciando Eureka Server..."
            run_in_new_terminal "EUREKA" "mvn -f eureka spring-boot:run"
            sleep 10
            echo "Lanzando Microservicios..."
            run_in_new_terminal "MS-DESTINOS" "mvn -f ms-destinos spring-boot:run"
            run_in_new_terminal "MS-RESERVAS" "mvn -f ms-reservas spring-boot:run"
            run_in_new_terminal "MS-USUARIOS" "mvn -f ms-usuarios spring-boot:run"
            run_in_new_terminal "MS-HOTELES" "mvn -f ms-hoteles spring-boot:run"
            run_in_new_terminal "MS-VUELOS" "mvn -f ms-vuelos spring-boot:run"
            run_in_new_terminal "MS-AUTH" "mvn -f ms-auth spring-boot:run"
            run_in_new_terminal "MS-NOTIFICACIONES" "mvn -f ms-notificaciones spring-boot:run"
            run_in_new_terminal "MS-PAGOS" "mvn -f ms-pagos spring-boot:run"
            run_in_new_terminal "MS-RESENNAS" "mvn -f ms-resennas spring-boot:run"
            run_in_new_terminal "MS-REPORTES" "mvn -f ms-reportes spring-boot:run"
            sleep 15
            run_in_new_terminal "API-GATEWAY" "mvn -f api-gateway spring-boot:run"
            ;;
        3)
            mvn clean install -DskipTests
            read -p "Presiona Enter para continuar..."
            ;;
        4)
            rm -rf ~/.m2/repository/cl/tripplanner
            mvn clean install -U -DskipTests
            read -p "Presiona Enter para continuar..."
            ;;
        5) run_in_new_terminal "EUREKA" "mvn -f eureka spring-boot:run" ;;
        16) run_in_new_terminal "API-GATEWAY" "mvn -f api-gateway spring-boot:run" ;;
        0) exit 0 ;;
        *) echo "Opcion no valida" ; sleep 2 ;;
    esac
done