#!/bin/bash

echo ""
echo "=== REINSTALACION DE DEPENDENCIAS MAVEN ==="
echo ""

# Paso 1: Eliminar repositorio local Maven
#echo "Eliminando carpeta .m2 ..."
#rm -rf ~/.m2

# Paso 2: Eliminar carpetas target
echo "Eliminando carpetas target ..."

rm -rf ~/Users/santti/Documents/GitHub/Trip-Planner/eureka/target
rm -rf ~/Users/santti/Documents/GitHub/Trip-Planner/ms-destinos/target
rm -rf ~/Users/santti/Documents/GitHub/Trip-Planner/ms-reservas/target
rm -rf ~/Users/santti/Documents/GitHub/Trip-Planner/ms-pagos/target
rm -rf ~/Users/santti/Documents/GitHub/Trip-Planner/ms-hoteles/target
rm -rf ~/Users/santti/Documents/GitHub/Trip-Planner/ms-vuelos/target
rm -rf ~/Users/santti/Documents/GitHub/Trip-Planner/ms-auth/target
rm -rf ~/Users/santti/Documents/GitHub/Trip-Planner/ms-notificaciones/target
rm -rf ~/Users/santti/Documents/GitHub/Trip-Planner/ms-resennas/target
rm -rf ~/Users/santti/Documents/GitHub/Trip-Planner/ms-reportes/target
rm -rf ~/Users/santti/Documents/GitHub/Trip-Planner/ms-usuarios/target

# Paso 3: Reinstalar dependencias
echo "Descargando dependencias nuevamente con Maven ..."
mvn clean install -U -DskipTests

echo ""
echo "=== PROCESO COMPLETADO ==="