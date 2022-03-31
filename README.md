# Test Backend Senior

## Ejecución del back

### Herramientas

Para la ejecución del back debe descargar e instalar las herramientas que se realacionan a continuación:

| Herramienta    | Version | Enlace de descarga                                  |
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

Luego vaya al menú File > Import. Seleccione la opción Git -> Projects from Git (with smart import). Acá puede usar la URL de este repositorio para importarlo o usar la ruta de su repositorio local si lo clonó previamente.

Una vez importado, abra el archivo _src/main/resources/application.properties_ y verifique las credenciales para la conexión con la base de datos. Si sus credenciales son diferentes, haga los cambios necesarios.

### Ejecutar el proyecto

Para la ejecución del proyecto, primero asegúrese de todas las dependencias hayan quedado instaladas. Para esto tome el archivo pom.xml > clic derecho > Maven > Update.

Luego de esto y si el proyecto no tiene ningún error vaya al Boot Dashboard, ubique el proyecto _driver-schedule_ y haga click en el botón start. Esto debe iniciar un servidor de aplicaciones (Tomcat) que escucha peticiones en el puerto 8080.

Si luego de instalar dependencias el proyecto contiene errores vaya al menú Project > Clean y haga clic en el botón Clean.

### Datos iniciales

Para cargar los datos iniciales (conductores y agendas) puede usar el script SQL que se encuentra en la ruta _sql/data.sql_. Use el programa PgAdmin o su editor preferido para conectarse a la base de datos y ejecutar el script.

### Pruebas unitarias

Para ejecutar las pruebas unitarias de los servicios seleccione la carpeta _src/test/java_ y dentro de esa carpeta el paquete _com.driverschedule.services.test_. Haga click derecho en el paquete > Run As > JUnit test.

### Pruebas del API

Las pruebas del API están organizadas como colecciones de Postman. Para ejecutarlas abra Postman e importe la carpeta _collections_ que está en la raiz del proyecto.

## Respuesta a los retos

### Agendar un pedido a un conductor en una fecha y hora, y especificar su lugar de recogida (latitud y longitud) y destino.

En este endpoint se agenda el pedido al conductor con el id 1. En el cuerpo de la petición se indica la hora del pedido y los detales (coordenadas para recoger y dejar el pedido).

```
Request
POST /api/drivers/1/schedules
Body
{
    "date": "2022-03-29T10:00:00-05",
    "service": {
        "dragLat": 10,
        "dragLng": 20,
        "dropLat": 20,
        "dropLng": 30
    }
}
```

En la respuesta se devuelve la entrada de la agenda con el id del servicio (pedido) asignado. También se actualiza el estado (_isAvailable_) de la entrada para evitar que se pueda asignar otro pedido en esa misma fecha y hora con el mismo conductor.

```
Response
{
    "id": 1,
    "date": "2022-03-29T13:00:00.000+00:00",
    "isAvailable": false,
    "service": {
        "id": 651,
        "dragLat": 10,
        "dragLng": 20,
        "dropLat": 20,
        "dropLng": 30
    }
}
```

### Consultar todos los pedidos asignados en un día en específico ordenados por la hora

En este request se consultan los pedidos del día 2022-03-30. El parámetro _isAvailable_, con los valores _true_ o _false_ permite filtrar la disponibilidad o no de ese espacio.

```
Rquest
GET /api/schedules?date=2022-03-30?isAvailable=false
```

En la respuesta de ejemplo se indica que para la fecha de consulta hay un servicio agendado (con el id 651) y asignado al conductor con el id 1.

```
Response
[
    {
        "id": 1,
        "date": "2022-03-29T13:00:00.000+00:00",
        "isAvailable": false,
        "service": {
            "id": 651,
            "dragLat": 10,
            "dragLng": 20,
            "dropLat": 20,
            "dropLng": 30
        },
        "driver": {
            "id": 1,
            "name": "Juan Alvarez",
            "lat": "1",
            "lng": "5",
            "lastUpdate": "2021-12-10T00:00:00.000+00:00"
        }
    },
...
]
```

### Consultar todos los pedidos de un conductor en un día en específico ordenados por la hora.

En este endpoint se consultan los pedidos para el conductor con el id 1 en la fecha 2022-03-29

```
Request
GET /api/drivers/1/schedules?date=2022-03-29
```

En la respuesta se muestran las entradas de la agenda, ordenadas por hora, indicando si el espacio está disponible o no. En caso de no estar disponible se muestra el servicio (pedido) asociado.

```
Response
[
    {
        "id": 1,
        "date": "2022-03-29T13:00:00.000+00:00",
        "isAvailable": false,
        "service": {
            "id": 651,
            "dragLat": 10,
            "dragLng": 20,
            "dropLat": 20,
            "dropLng": 30
        }
    },
    {
        "id": 2,
        "date": "2022-03-29T14:00:00.000+00:00",
        "isAvailable": true,
        "service": null
    },
...
]
```

### Hacer búsquedas del conductor que esté más cerca de un punto geográfico en una fecha y hora. (Tener en consideración los pedidos ya asignados al conductor).

En este enpoint se buscan los conductores que estén más cerca a la coordenada proporcionada en el _path_ del request.

```
Request
GET /api/drivers/byDistance/?lat=20&lng=30
```

En la respuesta se listan los conductores que están más cerca a la coordenada proporcionada. El listado se organiza por la distancia (de menor a mayor). También se muestrán los pedidos que tienen asignados.

```
Response
[
    {
        "id": 18,
        "name": "Elena Marquez",
        "lat": "30",
        "lng": "30",
        "lastUpdate": "2021-12-10T10:00:00.000+00:00",
        "distance": 10.0,
        "schedules": []
    },
    {
        "id": 12,
        "name": "Lida Trujillo",
        "lat": "12",
        "lng": "16",
        "lastUpdate": "2021-12-10T00:00:00.000+00:00",
        "distance": 16.124516,
        "schedules": []
    },
...
]
```

## Documentación del API

La siguiente es la documentación del API

| Recurso         | URL                                                     |
| --------------- | ------------------------------------------------------- |
| Driver Schedule | https://documenter.getpostman.com/view/8840688/UVyrTvr4 |
| Driver Service  | https://documenter.getpostman.com/view/8840688/UVyrTwTM |
| Driver          | https://documenter.getpostman.com/view/8840688/UVyrTvr5 |
| Schedule        | https://documenter.getpostman.com/view/8840688/UVyrTvr6 |

## Calidad del código

El análisis estático del código en SonarQube presenta las siguientes métricas:

| Metric              | Value         |
| ------------------- | ------------- |
| Quality Gate Status | Passed        |
| Bugs                | 0             |
| Vulnerabilities     | 0             |
| Security Hotspots   | 100% reviewed |
| TechDebt            | 0 min         |
| Code Smells         | 0             |
| Coverage            | 88.1%         |
| Unit Tests          | 26            |
| Duplications        | 0.0%          |
| Duplicated Blocks   | 0             |
