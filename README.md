# Evaluación: JAVA 

Desarrolle una aplicación que exponga una API RESTful de creación de usuarios.
Todos los endpoints deben aceptar y retornar solamente JSON, inclusive al para los mensajes de error.
**Todos los mensajes deben seguir el formato:**
> {"mensaje": "mensaje de error"}

***
## Registro

* [ok] - Ese endpoint deberá recibir un usuario con los campos "nombre", "correo", "contraseña", más un listado de objetos "teléfono", respetando el siguiente formato:

        {
            "name": "Juan Rodriguez",
            "email": "juan@rodriguez.org",
            "password": "hunter2",
            "phones": [
                {
                    "number": "1234567",
                    "citycode": "1",			
                    "contrycode": "57"
                }
            ]
        }

* [ok] - Responder el código de status HTTP adecuado
* [Maomenos] En caso de éxito, retorne el usuario y los siguientes campos:
  * id: id del usuario (puede ser lo que se genera por el banco de datos, pero sería más deseable un UUID)
  * created: fecha de creación del usuario
  * modified: fecha de la última actualización de usuario
  * last_login: del último ingreso (en caso de nuevo usuario, va a coincidir con la fecha de creación)
  * token: token de acceso de la API (puede ser UUID o JWT)
  * isactive: Indica si el usuario sigue habilitado dentro del sistema.
* [ok] En caso que el correo exista en la base de datos, deberá retornar un error "El correo ya registrado".
* [ok] - El correo debe seguir una expresión regular para validar que formato sea el correcto. (aaaaaaa@dominio.cl)
* [ok] - La clave debe seguir una expresión regular para validar que formato sea el correcto. (Una Mayúscula, letras minúsculas, y dos números)
  * ^                 # start-of-string
  * (?=.*[0-9])       # a digit must occur at least once
  * (?=.*[a-z])       # a lower case letter must occur at least once
  * (?=.*[A-Z])       # an upper case letter must occur at least once
  * (?=.*[@#$%^&+=])  # a special character must occur at least once
  * (?=\S+$)          # no whitespace allowed in the entire string
  * .{8,}             # anything, at least eight places though
  * $                 # end-of-string
* Se debe hacer traza de logs dentro del aplicativo.
*  El token deberá ser persistido junto con el usuario

***
## Requisitos mandatorios
* Plazo: 2 días.
* Banco de datos en memoria.
* Gradle como herramienta de construcción.
* Pruebas unitarias (Deseable: Spock Framework).
* Persistencia con Hibernate.
* Framework Spring Boot.
* Java 8 o superior. (Usar más de dos características propias de la versión)
* Entrega en un repositorio público (github, gitlab o bitbucket) con el código fuente.
* Entregar diagrama de componentes de la solución y al menos un diagrama de secuencia (ambos diagramas son de carácter obligatorio y deben seguir estándares UML).
* README.md debe contener las instrucciones para levantar y usar el proyecto.

***
## Requisitos deseables
JWT cómo token
