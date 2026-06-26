#!/bin/zsh

echo
echo "=== COMPILANDO MICROSERVICIOS ==="
echo

cd /Users/santti/Documents/GitHub/Trip-Planner/ms-destinos
mvn clean install -U

cd /Users/santti/Documents/GitHub/Trip-Planner/ms-reservas
mvn clean install -U

cd /Users/santti/Documents/GitHub/Trip-Planner/ms-pagos
mvn clean install -U

cd /Users/santti/Documents/GitHub/Trip-Planner/ms-hoteles
mvn clean install -U

cd /Users/santti/Documents/GitHub/Trip-Planner/ms-vuelos
mvn clean install -U

cd /Users/santti/Documents/GitHub/Trip-Planner/ms-auth
mvn clean install -U

cd /Users/santti/Documents/GitHub/Trip-Planner/ms-notificaciones
mvn clean install -U

cd /Users/santti/Documents/GitHub/Trip-Planner/ms-resennas
mvn clean install -U

cd /Users/santti/Documents/GitHub/Trip-Planner/ms-reportes
mvn clean install -U

cd /Users/santti/Documents/GitHub/Trip-Planner/ms-usuarios
mvn clean install -U

echo
echo "=== COMPILACION COMPLETADA ==="

read -k 1 -s -r -p "Presiona cualquier tecla para continuar..."
echo