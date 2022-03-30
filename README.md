# Ejecución del back

## Herramientas necesarias

Para la ejecución del back debe descargar e instalar las herramientas que se realacionan a continuación:

| Herramienta    | Version |
| -------------- | ------- | --------------------------------------------------- |
| Java JDK       | 11      | https://www.oracle.com/java/technologies/downloads/ |
| Spring Tools\* | 4       | https://spring.io/tools                             |
| Lombok         | 1.18.22 | https://projectlombok.org/download                  |
| Postgres       | 12      | https://www.postgresql.org/download/                |
| Postman        | 9.15.6  | https://www.postman.com/downloads/                  |

\* Puede usar también VSCode con los plugins para Java, Lombok y Spring Boot.

### Instalar Lombok

Luego de instalar el JDK y Spring Tools debe instalar Lombok. Para esto haga doble clic en el archivo que descargó (lombok.jar). Cuando se abra la ventana seleccione _Specify Location_, busque la carpeta donde quedó instalado Spring Tools y haga clic en Install Update.

### Crear la base de datos

Después de instalar Postgres cree una nueva base de datos denominada _driver-schedule_. Asegúrese de que el usuario _postgres_ tiene permisos de escritura sobre esa base de datos.

### Importar el proyecto a Spring Tools

Abra Spring Tools. El IDE le pedirá crear un Workspace. Deje la ruta por defecto, o si lo prefiere, seleccione una ruta en su sistema de archivos.

Luego vaya al menú File > Import. Seleccione la opción Git -> Projects from Git (with smart import). Acá puede usar la URL para importarlo o usar la ruta de su repositorio local si lo clonó previamente.

Una vez importado, abra el archivo _src/main/resources/application.properties_ y verifique las credenciales para la conexión con la base de datos. Si sus credenciales son diferentes, haga los cambios necesarios.

### Ejecutar el proyecto

Para la ejecución del proyecto, primero asegúrese de todas las dependencias hayan quedado instaladas. Para esto tome el archivo pom.xml > clic derecho > Maven > Update.

Luego de esto y si el proyecto no tiene ningún error vaya Boot Dashboard, ubique el proyecto _driver-schedule_ y haga click en el botón start. Esto debe iniciar un servidor de aplicaciones (Tomcat) que escucha peticiones en el puerto 8080.

Si luego de instalar dependencias el proyecto contiene errores vaya al menú Project > Clean y haga clic en el botón Clean.

### Datos iniciales

Para cargar los datos iniciales puede usar el script SQL que se encuentra en la ruta _sql/data.sql_. Use el programa PgAdmin o su editor preferido para conectarse a la base de datos y ejecutar el script.

### Pruebas unitarias

Para ejecutar las pruebas unitarias de los servicios seleccione la carpeta _src/test/java_ y dentro de esa carpeta el paquete _com.driverschedule.services.test_. Haga click derecho en el paquete > Run As > JUnit test.

### Pruebas del API

Las pruebas del API están organizadas como colecciones de Postman. Para ejecutarlas abra Postman e mporte la carpeta _collections_ que está en la raiz del proyecto.
