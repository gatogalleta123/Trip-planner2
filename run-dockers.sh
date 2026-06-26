#!/bin/bash

clear

echo "===================================================="
echo "     REINICIO TOTAL: INFRAESTRUCTURA Y DATOS"
echo "===================================================="

echo "1. DETENIENDO CONTENEDORES Y LIMPIANDO REDES..."
docker compose down

echo "2. ELIMINANDO VOLUMENES (DATOS DE KAFKA Y POSTGRES)..."
docker volume prune -f

echo "3. INICIANDO INFRAESTRUCTURA (DB, KAFKA, UI)..."
docker compose up -d

echo "4. ESPERANDO A QUE LOS SERVICIOS ESTEN LISTOS..."
sleep 15

echo "5. COMPILANDO BASES DE DATOS ADICIONALES..."

cd init-multi-db || exit

bash docker_compile_dbs.sh

echo ""
echo "===================================================="
echo "             ¡PROCESO FINALIZADO!"
echo "===================================================="
echo "- Kafka UI: http://localhost:8080"
echo "- Postgres: localhost:5433"
echo ""
echo "IMPORTANTE: Antes de lanzar tu App, verifica que"
echo "el cluster aparezca Online."
echo "===================================================="

read -p "Presiona ENTER para continuar..."