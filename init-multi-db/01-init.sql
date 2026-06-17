-- ES FUNDAMENTAL EJECUTAR ESTE SCRIPT QUE PERMITE ELIMINAR LAS BASES DE DATOS
-- SI ES QUE EXISTEN, PARA LUEGO CREARLAS LIMPIAS SIN TABLAS Y DESDE CERO

SELECT 'CREATE DATABASE destinos'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'destinos') \gexec

SELECT 'CREATE DATABASE reservas'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'reservas') \gexec

SELECT 'CREATE DATABASE usuarios'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'usuarios') \gexec

SELECT 'CREATE DATABASE hoteles'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'hoteles') \gexec

SELECT 'CREATE DATABASE vuelos'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'vuelos') \gexec

SELECT 'CREATE DATABASE auth'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'auth') \gexec

SELECT 'CREATE DATABASE notificaciones'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'notificaciones') \gexec

SELECT 'CREATE DATABASE pagos'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'pagos') \gexec

SELECT 'CREATE DATABASE resennas'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'resennas') \gexec

SELECT 'CREATE DATABASE reportes'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'reportes') \gexec