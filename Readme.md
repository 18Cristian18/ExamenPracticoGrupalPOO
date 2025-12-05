# Examen Práctico: Juego de Buscaminas en Consola
**Universidad Politécnica Salesiana**
**Asignatura:** Programación Orientada a Objetos (POO)

## Descripción del Proyecto
Este proyecto consiste en el desarrollo de una versión en consola del clásico juego Buscaminas. La aplicación ha sido implementada utilizando el lenguaje de programación Java, siguiendo estrictamente los paradigmas de la Programación Orientada a Objetos (POO) y el patrón de arquitectura de software Modelo-Vista-Controlador (MVC).

El objetivo principal es demostrar la competencia técnica en el diseño de clases, herencia, polimorfismo, encapsulamiento, manejo de excepciones personalizadas, persistencia de datos y pruebas unitarias (TDD).

## Arquitectura y Diseño Técnico

El sistema se ha estructurado en paquetes para asegurar una alta cohesión y un bajo acoplamiento, facilitando la mantenibilidad y escalabilidad del código.

### 1. Patrón Modelo-Vista-Controlador (MVC)
La lógica de negocio está totalmente separada de la interfaz de usuario:
*   **Modelo (`ec.edu.ups.buscaminas.modelo`):** Contiene la lógica del núcleo del juego. Gestiona el tablero, las casillas, la colocación aleatoria de minas y las reglas de victoria/derrota. No tiene ninguna dependencia de la entrada/salida de la consola.
*   **Vista (`ec.edu.ups.buscaminas.vista`):** Responsable únicamente de la presentación de datos al usuario y la captura de entradas mediante teclado.
*   **Controlador (`ec.edu.ups.buscaminas.controlador`):** Actúa como intermediario, recibiendo las entradas de la vista, procesándolas a través del modelo y actualizando la vista según el nuevo estado del juego.

### 2. Programación Orientada a Objetos (POO)
*   **Herencia y Abstracción:** Se define una clase abstracta `Casilla` que establece el comportamiento base. De ella heredan las clases concretas `CasillaMina` y `CasillaVacia`, permitiendo extender la funcionalidad.
*   **Polimorfismo:** El método `obtenerSimbolo(boolean mostrarMinas)` es polimórfico. Cada tipo de casilla determina su propia representación gráfica en el tablero sin necesidad de estructuras condicionales complejas externas.
*   **Encapsulamiento:** Todos los atributos de las clases son privados o protegidos, exponiendo su acceso únicamente a través de métodos públicos (getters/setters) para proteger la integridad del estado del objeto.

### 3. Manejo de Errores y Excepciones
Se han implementado excepciones personalizadas para gestionar reglas específicas del negocio, evitando el uso de excepciones genéricas para el control de flujo:
*   `CasillaYaDescubiertaException`: Se lanza al intentar interactuar con una casilla ya revelada.
*   `CoordenadaInvalidaException`: Gestiona entradas fuera de los límites del tablero (10x10) o formatos de texto incorrectos.
*   Manejo de `InputMismatchException` y `NumberFormatException` para validar la entrada de datos del usuario.

### 4. Persistencia de Datos
El juego implementa la interfaz `Serializable` de Java. Esto permite guardar el estado actual de la partida (objeto `Tablero` completo) en un archivo binario (`.dat`) y recuperarlo posteriormente para continuar el juego.

### 5. Calidad de Código y TDD
Se ha seguido la metodología de Desarrollo Guiado por Pruebas (TDD). Se incluye un paquete de pruebas que valida:
*   La correcta generación de 10 minas.
*   El lanzamiento adecuado de excepciones.
*   La integridad de los datos tras los procesos de guardado y carga.
*   La lógica de detección de victoria y derrota.

## Estructura del Proyecto

El código fuente se organiza bajo la carpeta `src` con la siguiente estructura de paquetes:

```text
ec.edu.ups.buscaminas
├── controlador
│   └── ControladorJuego.java      # Gestión del flujo del programa
├── excepciones
│   ├── CasillaYaDescubiertaException.java
│   └── CoordenadaInvalidaException.java
├── main
│   └── Main.java                  # Punto de entrada (Launcher)
├── modelo
│   ├── Casilla.java               # Clase base abstracta
│   ├── CasillaMina.java           # Lógica específica de minas
│   ├── CasillaVacia.java          # Lógica específica de espacios seguros
│   ├── Juego.java                 # Fachada para persistencia y estado
│   └── Tablero.java               # Matriz 10x10 y algoritmos
├── test
│   └── BuscaminasTest.java        # Suite de pruebas unitarias
└── vista
    └── VistaConsola.java          # Interfaz de usuario (CLI)
```

## Requisitos de Instalación

*   **Java Development Kit (JDK)**
*   **IDE Recomendado:** NetBeans, IntelliJ IDEA o Eclipse.
*   **Librerías:** JUnit 5 (opcional, necesario solo para ejecutar la suite de pruebas automatizadas si se usa el entorno de test estándar).

## Instrucciones de Ejecución

1.  **Clonación/Descarga:** Descargue el código fuente o clone el repositorio en su máquina local.
2.  **Importación:** Abra su IDE y seleccione la opción para abrir un proyecto existente desde la carpeta raíz.
3.  **Compilación:** Asegúrese de que el proyecto compile sin errores.
4.  **Ejecución del Juego:**
    *   Navegue al paquete `ec.edu.ups.buscaminas.main`.
    *   Ejecute el archivo `Main.java`.
5.  **Ejecución de Pruebas:**
    *   Navegue al paquete `ec.edu.ups.buscaminas.test`.
    *   Ejecute el archivo `BuscaminasTest.java` para ver el reporte de validación en consola.

## Manual de Usuario

El juego presenta un tablero de 10x10. Las filas están etiquetadas de la **A** a la **J** y las columnas del **1** al **10**.

### Simbología
*   `-` : Casilla cubierta (estado desconocido).
*   `O` : Casilla vacía descubierta sin minas adyacentes.
*   `1-8` : Número que indica la cantidad de minas en las casillas vecinas.
*   `X` : Bandera colocada por el usuario (marca de sospecha).
*   `*` : Mina (visible solo al perder el juego).

### Controles
El menú principal ofrece las siguientes opciones:

1.  **Descubrir Casilla:** Solicita una coordenada (ejemplo: `A5`, `B10`). Si la casilla contiene una mina, el juego termina. Si está vacía, se revelará el área circundante automáticamente.
2.  **Poner/Quitar Bandera:** Permite marcar una casilla con una `X` para evitar descubrirla accidentalmente.
3.  **Guardar Juego:** Almacena el estado actual en un archivo local.
4.  **Cargar Juego:** Restaura la partida desde el último punto guardado.
5.  **Salir:** Cierra la aplicación.

## Autores y Contribución

Este proyecto fue desarrollado por el equipo de estudiantes para la asignatura de Programación Orientada a Objetos. La gestión del código fuente se realizó mediante Git, asegurando la participación activa de todos los integrantes mediante commits en diferentes fechas.

*   Joniel Ortiz
*   Dyllan Lastra
*   Cristian Rondal
*   Patricio Chato