# SuperShop Backend

Grupo de Proyecto: GRUPO-1-SEM-1-2023

## Integrantes

* Federico Bentancor
* Santiago Lorenzo
* Leonardo Perez
* Matias Bentancur
* Bruno Espalter
* Kevin Gamenthaler
* Facundo Laborde

## Características

El componente Backend de SuperShop es responsable de gestionar la lógica de negocio del sistema y manejar las solicitudes recibidas desde SuperShop Front Office y SuperShop Back Office a través de servicios web REST.

## Tecnologías utilizadas

Lenguaje de programación: Java 17, una versión LTS (soporte a largo plazo).

Mapeo de entidades: Hibernate 6, un ORM (Mapeo Objeto-Relacional) utilizado para la conexión y manipulación de datos entre las entidades Java y la base de datos.

Comunicación con la base de datos: JDBC de PostgreSQL 42, un componente que permite la interacción entre Hibernate y el motor de base de datos PostgreSQL.

Base de datos: PostgreSQL 14, elegido por su confiabilidad y capacidad de almacenamiento y manipulación de datos.

Envío de correos electrónicos: Jakarta Mail, para el envío de notificaciones y comunicaciones por correo electrónico.

Envío de notificaciones push: Firebase Cloud Messaging, para el envío de notificaciones push web y móvil.

Pruebas unitarias: JUnit, un marco de pruebas utilizado para verificar el funcionamiento correcto de los componentes individuales.

Almacenamiento de imágenes: Cloudinary, una plataforma en la nube especializada en el almacenamiento y gestión de imágenes, integrada mediante su librería para Java.

Construcción y organización del proyecto: Maven, una herramienta de gestión de proyectos que facilita la compilación y administración de dependencias.

* [Java 17](https://openjdk.org/projects/jdk/17/)
* [Hibernate 6](https://hibernate.org/orm/releases/6.0/)
* [JDBC de PostgreSQL 42](https://jdbc.postgresql.org/download/)
* [PostgreSQL 14](https://www.postgresql.org/download/)
* [Jakarta Mail](https://github.com/jakartaee/mail-api/releases)
* [Firebase Cloud Messaging](https://github.com/firebase/firebase-admin-java)
* [JUnit 5](https://github.com/junit-team/junit5/)
* [Cloudinary](https://cloudinary.com/)
* [Apache Maven](https://maven.apache.org/)

## Instalación y ejecución del proyecto

### Modo desarrollo

* Clonar repositorio

```sh
git clone https://github.com/GRUPO-1-SEM-1-2023/backend.git
```

* Instalar

```sh
mvn clean install
```

* Ejecutar

```sh
java -jar target/supershop-0.0.1-SNAPSHOT.jar
```

### Modo producción

* Cambiar a la rama produccion

```sh
git checkout produccion
```

* Instalar

```sh
mvn clean install
```

* Ejecutar

```sh
java -jar target/supershop-0.0.1-SNAPSHOT.jar
```
