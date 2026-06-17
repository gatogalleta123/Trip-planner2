#!/bin/bash

docker exec -i postgres-db psql -U postgres -d postgres < 01-init.sql
docker exec -i postgres-db psql -U postgres -d postgres < 02-create_destinos.sql
docker exec -i postgres-db psql -U postgres -d postgres < 03-create_reservas.sql
docker exec -i postgres-db psql -U postgres -d postgres < 04-create_usuarios.sql
docker exec -i postgres-db psql -U postgres -d postgres < 05-create_hoteles.sql
docker exec -i postgres-db psql -U postgres -d postgres < 06-create_vuelos.sql
docker exec -i postgres-db psql -U postgres -d postgres < 07-create_auth.sql
docker exec -i postgres-db psql -U postgres -d postgres < 08-create_notificaciones.sql
docker exec -i postgres-db psql -U postgres -d postgres < 09-create_pagos.sql
docker exec -i postgres-db psql -U postgres -d postgres < 10-create_resennas.sql
docker exec -i postgres-db psql -U postgres -d postgres < 11-create_reportes.sql