# Prueba Técnica PTM
Prueba técnica para el proceso de ingreso a PTM.

**Desarrollado por:** Juan Pablo Solarte

# Descripción
Esta prueba técnica consiste en el desarrollo de una API `REST` que permite realizar operaciones CRUD sobre productos y una funcionalidad especial para obtener combinaciones de productos de acuerdo con un valor dado.  
Adicionalmente, se desarrolla una página web con React que consume los servicios de la API e integra algunos servicios externos.

# Arquitectura Backend
Se implementa una adaptación de la arquitectura hexagonal con la siguiente estructura de paquetes:

~~~
src/main/java/globus/cargo/pruebaTecnica
├── application
│   ├── DTOs
│   ├── ports
│   │   ├── in
│   │   └── out
│   ├── exceptions
│   └── services
├── domain
├── infrastructure
│   ├── exceptionHandler
│   ├── httpController
│   └── persistence
└── PruebaTecnicaPtmApplication.java
~~~

- El paquete **domain** contiene la entidad de dominio `Producto`.
- El paquete **application** define las interfaces (puertos de entrada y salida), la implementación de los servicios y algunos DTOs necesarios.
- El paquete **infrastructure** gestiona la comunicación HTTP, el manejo global de excepciones y la implementación de persistencia con MySQL.

# Arquitectura Frontend
Se utiliza una arquitectura sencilla con la siguiente estructura de carpetas:

~~~
src/main/java/globus/cargo/pruebaTecnica
├── components
├── models
├── services
└── App.tsx
~~~


- El paquete **components** contiene los componentes usados en la página web.
- El paquete **models** define los modelos utilizados en las tablas y servicios.
- El paquete **services** contiene los servicios que consumen la API creada y los servicios externos.

# Instrucciones de ejecución

## Requisitos previos
- Tener instalado **Git**.
- Para ejecución con contenedores: **Docker**.
- Para ejecución directa:
  - **Java 17** o superior (API).
  - **Maven 3.8.6** (API).
  - **Node.js** y **npm**.
  - **MySQL 8.0.32** o Docker (para levantar la base de datos en un contenedor).

---

## 1. Descarga del proyecto

1. Clonar el repositorio:
```bash
   git clone https://github.com/juansolarteh/Prueba-T-cnica-PTM.git
```

2. Navegar al directorio del proyecto:
```bash
   cd prueba-T-cnica-PTM
```

## 1. Ejecución con Docker Compose

1. Asegúrate de que los puertos 3306 (MySQL), 8080 (Spring Boot) y 3000 (React) no estén en uso. Si lo están, modifica los puertos en el archivo docker-compose.yml.

2. Construir y levantar los contenedores:
```bash
   docker-compose up
```

¡Listo! Ahora el servicio está corriendo y puedes interactuar con él.

---

## Ejecución directa del proyecto

### Pasos para levantar la base de datos

1. Ejecuta el script que se encuentra en la raíz del proyecto (`script.sql`), el cual crea la base de datos, el usuario y otorga los permisos necesarios.  
   Desde la terminal (sin necesidad de entrar a la consola de MySQL), usa:

```bash
   mysql -u root -p < script.sql
```

Esto pedirá la contraseña de tu usuario root de MySQL y ejecutará todo el contenido del archivo.

**Nota:** Si prefieres hacerlo manualmente, puedes crear la base de datos con:

```bash
   CREATE DATABASE crud_app;
```

**Importante:** Asegúrate de que las credenciales del usuario creado coincidan con las variables de entorno SPRING_DATASOURCE_USERNAME y SPRING_DATASOURCE_PASSWORD, o configúralas directamente en el archivo application.properties del proyecto ServicioREST.

---

### Pasos para levantar la API

3. Navegar al directorio del backend:
```bash
   cd ServicioREST
```

6. Ejecutar el proyecto con Maven:
```bash
   mvn spring-boot:run
```

La API REST ahora estará disponible en:
👉 http://localhost:8080

---

### Pasos para levantar la aplicación web

7. En otra terminal, desde la raíz del proyecto, navegar al directorio frontend:
```bash
   cd app-front
```

8. Instalar dependencias:
```bash
   npm install
```

9. Ejecutar el proyecto React:
```bash
   npm start
```

La aplicación web ahora estará disponible en:
👉 http://localhost:3000

---

**Nota:** Si durante la ejecución directa se presentan problemas de compatibilidad de versiones, se recomienda usar los contenedores Docker suministrados para asegurar el correcto funcionamiento.

# Endpoints

## Crear producto

**Url:** http://localhost:8080/productos

**Tipo petición:** POST

**Ejemplo body**:
```json
   {
      "nombre": "Nombre producto",
      "descripcion": "Descripción producto",
      "precio": 10000,
      "cantidadStock": 20
   }
```

**Ejemplo respuesta API**
```json
   {
      "id": 5,
      "nombre": "Nombre producto",
      "descripcion": "Descripción producto",
      "precio": 10000,
      "cantidadStock": 20,
      "valorInventario": 200000
   }
```

## Editar producto

**url:** http://localhost:8080/productos/{id}

**Tipo petición:** PUT

**ejemplo body**:
```json
   {
      "nombre": "Nuevo nombre producto",
      "descripcion": "Descripción producto",
      "precio": 10000,
      "cantidadStock": 20
   }
```

**Ejemplo respuesta API**
```json
   {
      "id": 5,
      "nombre": "Nuevo nombre producto",
      "descripcion": "Descripción producto",
      "precio": 10000,
      "cantidadStock": 20,
      "valorInventario": 200000
   }
```

## Obtener todos los productos

**url:** http://localhost:8080/productos

**Tipo petición:** GET

**ejemplo body**: NO APLICA

**Ejemplo respuesta API**
```json
   [
      {
         "id": 5,
         "nombre": "Nuevo nombre producto",
         "descripcion": "Descripción producto",
         "precio": 10000,
         "cantidadStock": 20,
         "valorInventario": 200000
      },
      {
         "id": 6,
         "nombre": "Nombre producto 2",
         "descripcion": "Descripción producto 2",
         "precio": 10000,
         "cantidadStock": 20,
         "valorInventario": 200000
      }
   ]
```

## Eliminar producto

**url:** http://localhost:8080/productos/{id}

**Tipo petición:** DELETE

**ejemplo body**: NO APLICA

**Respuesta:** Respuesta 204 (Sin contenido)

## Obtener combinaciones de productos

**url:** http://localhost:8080/productos?valorComparacion={valor}

**Tipo petición:** GET

**ejemplo body:** NO APLICA

**Params**
- valorComparacion

**Ejemplo respuesta API**
```json
   [
      {
         "productos": [
               {
                  "id": 4,
                  "nombre": "Manta",
                  "descripcion": "Manta",
                  "precio": 2000.0,
                  "cantidadStock": 10,
                  "valorInventario": 20000.0
               },
               {
                  "id": 3,
                  "nombre": "Lapiz",
                  "descripcion": "Lapiz",
                  "precio": 800.0,
                  "cantidadStock": 43,
                  "valorInventario": 34400.0
               }
         ],
         "valorSumatoria": 2800.0
      }
   ]
```

# App Web

La aplicación web estará disponible en:
👉 http://localhost:3000


# Postman
En la raíz del proyecto se incluye una colección de **Postman** que puede importarse para probar fácilmente los servicios expuestos por la API.