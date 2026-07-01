#!/bin/bash

echo ""
echo "=== REINSTALACION DE DEPENDENCIAS MAVEN ==="
echo ""

# Paso 1: Eliminar repositorio local Maven
echo "Eliminando carpeta .m2 ..."
rm -rf ~/.m2

# Paso 2: Eliminar carpetas target
echo "Eliminando carpetas target ..."

rm -rf /common/target
rm -rf /api-getway/target
rm -rf /eureka/target
rm -rf /ms-destinos/target
rm -rf /ms-reservas/target
rm -rf /ms-pagos/target
rm -rf /ms-hoteles/target
rm -rf /ms-vuelos/target
rm -rf /ms-auth/target
rm -rf /ms-notificaciones/target
rm -rf /ms-resennas/target
rm -rf /ms-reportes/target
rm -rf /ms-usuarios/target

# Paso 3: Reinstalar dependencias
echo "Descargando dependencias nuevamente con Maven ..."
mvn clean install -U -DskipTests

echo ""
echo "=== PROCESO COMPLETADO ==="