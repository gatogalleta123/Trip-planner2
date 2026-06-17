@echo off
echo Descargando microservicios Spring Boot...
echo.
echo Descargando eureka.zip...
curl -o eureka.zip "https://start.spring.io/starter.zip?type=maven-project&language=java&bootVersion=3.5.13&baseDir=eureka&groupId=cl.tripplanner.turismo&artifactId=cl-tripplanner-turismo-eureka&name=turismo-eureka&description=servicio-eureka&packageName=cl.tripplanner.turismo.eureka&packaging=jar&javaVersion=21&dependencies=cloud-eureka-server,devtools"
echo.
echo Descargando ms-destinos.zip...
curl -o ms-destinos.zip "https://start.spring.io/starter.zip?type=maven-project&language=java&bootVersion=3.5.13&baseDir=ms-destinos&groupId=cl.tripplanner.turismo&artifactId=cl-tripplanner-turismo-destinos&name=turismo-destinos&description=servicio-destinos&packageName=cl.tripplanner.turismo.destinos&packaging=jar&javaVersion=21&dependencies=web,data-jpa,lombok,postgresql,cloud-feign"
echo.
echo Descargando ms-reservas.zip...
curl -o ms-reservas.zip "https://start.spring.io/starter.zip?type=maven-project&language=java&bootVersion=3.5.13&baseDir=ms-reservas&groupId=cl.tripplanner.turismo&artifactId=cl-tripplanner-turismo-reservas&name=turismo-reservas&description=servicio-reservas&packageName=cl.tripplanner.turismo.reservas&packaging=jar&javaVersion=21&dependencies=web,data-jpa,lombok,postgresql,cloud-feign"
echo.
echo Descargando ms-pagos.zip...
curl -o ms-pagos.zip "https://start.spring.io/starter.zip?type=maven-project&language=java&bootVersion=3.5.13&baseDir=ms-pagos&groupId=cl.tripplanner.turismo&artifactId=cl-tripplanner-turismo-pagos&name=turismo-pagos&description=servicio-pagos&packageName=cl.tripplanner.turismo.pagos&packaging=jar&javaVersion=21&dependencies=web,data-jpa,lombok,postgresql,cloud-feign"
echo.
echo Descargando ms-hoteles.zip...
curl -o ms-hoteles.zip "https://start.spring.io/starter.zip?type=maven-project&language=java&bootVersion=3.5.13&baseDir=ms-hoteles&groupId=cl.tripplanner.turismo&artifactId=cl-tripplanner-turismo-hoteles&name=turismo-hoteles&description=servicio-hoteles&packageName=cl.tripplanner.turismo.hoteles&packaging=jar&javaVersion=21&dependencies=web,data-jpa,lombok,postgresql,cloud-feign"
echo.
echo Descargando ms-vuelos.zip...
curl -o ms-vuelos.zip "https://start.spring.io/starter.zip?type=maven-project&language=java&bootVersion=3.5.13&baseDir=ms-vuelos&groupId=cl.tripplanner.turismo&artifactId=cl-tripplanner-turismo-vuelos&name=turismo-vuelos&description=servicio-vuelos&packageName=cl.tripplanner.turismo.vuelos&packaging=jar&javaVersion=21&dependencies=web,data-jpa,lombok,postgresql,cloud-feign"
echo.
echo Descargando ms-auth.zip...
curl -o ms-auth.zip "https://start.spring.io/starter.zip?type=maven-project&language=java&bootVersion=3.5.13&baseDir=ms-auth&groupId=cl.tripplanner.turismo&artifactId=cl-tripplanner-turismo-auth&name=turismo-auth&description=servicio-auth&packageName=cl.tripplanner.turismo.auth&packaging=jar&javaVersion=21&dependencies=web,data-jpa,lombok,postgresql,cloud-feign"
echo.
echo Descargando ms-notificaciones.zip...
curl -o ms-notificaciones.zip "https://start.spring.io/starter.zip?type=maven-project&language=java&bootVersion=3.5.13&baseDir=ms-notificaciones&groupId=cl.tripplanner.turismo&artifactId=cl-tripplanner-turismo-notificaciones&name=turismo-notificaciones&description=servicio-notificaciones&packageName=cl.tripplanner.turismo.notificaciones&packaging=jar&javaVersion=21&dependencies=web,data-jpa,lombok,postgresql,cloud-feign"
echo.
echo Descargando ms-resennas.zip...
curl -o ms-resennas.zip "https://start.spring.io/starter.zip?type=maven-project&language=java&bootVersion=3.5.13&baseDir=ms-resennas&groupId=cl.tripplanner.turismo&artifactId=cl-tripplanner-turismo-resennas&name=turismo-resennas&description=servicio-resennas&packageName=cl.tripplanner.turismo.resennas&packaging=jar&javaVersion=21&dependencies=web,data-jpa,lombok,postgresql,cloud-feign"
echo.
echo Descargando ms-reportes.zip...
curl -o ms-reportes.zip "https://start.spring.io/starter.zip?type=maven-project&language=java&bootVersion=3.5.13&baseDir=ms-reportes&groupId=cl.tripplanner.turismo&artifactId=cl-tripplanner-turismo-reportes&name=turismo-reportes&description=servicio-reportes&packageName=cl.tripplanner.turismo.reportes&packaging=jar&javaVersion=21&dependencies=web,data-jpa,lombok,postgresql,cloud-feign"
echo.
echo Descargando ms-usuarios.zip...
curl -o ms-usuarios.zip "https://start.spring.io/starter.zip?type=maven-project&language=java&bootVersion=3.5.13&baseDir=ms-usuarios&groupId=cl.tripplanner.turismo&artifactId=cl-tripplanner-turismo-usuarios&name=turismo-usuarios&description=servicio-usuarios&packageName=cl.tripplanner.turismo.usuarios&packaging=jar&javaVersion=21&dependencies=web,data-jpa,lombok,postgresql,cloud-feign"
echo.
echo Descarga completada.
pause
