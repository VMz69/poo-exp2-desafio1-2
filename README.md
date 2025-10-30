# 📚 Sistema de Gestión de Mediateca

Proyecto académico basado en los **Desafíos 1 y 2** del curso de *Programación y Bases de Datos*.  
El objetivo es diseñar y desarrollar un sistema que gestione materiales disponibles en una mediateca (libros, revistas, CDs y DVDs), aplicando conceptos de **modelado relacional**, **UML** y **Programación Orientada a Objetos (POO)** en Java.

---

## 🧩 Descripción General

El sistema permite administrar materiales de una mediateca mediante una interfaz de escritorio desarrollada con **Java Swing**.  
Se implementan las buenas prácticas de diseño, persistencia de datos y manejo de errores.

### 🔹 Fase 1 – Análisis y Diseño
- Elaboración de diagramas de **Casos de Uso** y **Clases UML**.
- Creación del **modelo relacional** en **MySQL**.
- Identificación y modelado de las entidades que representan materiales.

### 🔹 Fase 2 – Implementación
- Desarrollo del sistema con **Swing**.
- Uso de **herencia, abstracción y polimorfismo** para la jerarquía de materiales.
- Conexión con **MySQL** mediante **JDBC**.
- Manejo de errores y registros con **Log4J**.

---

## ⚙️ Requisitos Funcionales

El sistema permite:
- Agregar nuevos materiales
- Modificar o eliminar materiales existentes
- Buscar materiales por tipo o palabra clave
- Consultar detalles de materiales
- Listar todos los materiales
- Salir del sistema de forma segura

---

## 💾 Estructura de Datos

Cada tipo de material tiene atributos particulares:

| Tipo de material | Atributos principales |
|------------------|-----------------------|
| **Libro** | Código, título, autor, número de páginas, editorial, ISBN, año, unidades disponibles |
| **Revista** | Código, título, editorial, periodicidad, fecha de publicación, unidades disponibles |
| **CD de Audio** | Código, título, artista, género, duración, número de canciones, unidades disponibles |
| **DVD** | Código, título, director, duración, género |

📌 **Generación de códigos automáticos:**  
`LIB####` para libros, `REV####` para revistas, `CDA####` para CDs, y `DVD####` para DVDs.

---

## 🧠 Conceptos Aplicados
- **UML:** modelado visual de casos de uso y clases.
- **POO:** abstracción, herencia y reutilización de código.
- **JDBC:** conexión y operaciones con MySQL.
- **Swing:** desarrollo de interfaces gráficas.
- **Log4J:** manejo de errores y trazas de ejecución.

---

## 🛠️ Tecnologías Utilizadas
| Componente | Tecnología |
|-------------|-------------|
| **Lenguaje** | Java |
| **Interfaz** | Swing |
| **Base de datos** | MySQL |
| **Persistencia** | JDBC |
| **Logs** | Log4J |
| **Modelado** | UML |

---

## 🧩 Estructura de Paquetes Final

```bash
ProyectoMediateca/
├── src/
│   └── com/
│       └── mediateca/
│           ├── modelo/
│           │   ├── Material.java
│           │   ├── MaterialEscrito.java
│           │   ├── MaterialAudiovisual.java
│           │   ├── Libro.java
│           │   ├── Revista.java
│           │   ├── Dvd.java
│           │   └── CdAudio.java
│           │
│           ├── dao/
│           │   ├── ConexionBD.java
│           │   ├── MaterialDAO.java
│           │   ├── LibroDAO.java
│           │   ├── RevistaDAO.java
│           │   ├── DvdDAO.java
│           │   └── CdAudioDAO.java
│           │
│           ├── vista/
│           │   ├── MainFrame.java
│           │   ├── panelBuscar.java
│           │   ├── panelGestion.java
│           │   ├── panelConsulta.java
│           │   ├── panelLibro.java
│           │   ├── panelRevista.java
│           │   ├── panelDvd.java
│           │   ├── panelCdAudio.java
│           │   ├── tabLibro.java
│           │   ├── tabRevista.java
│           │   ├── tabDVD.java
│           │   ├── tabCdAudio.java
│           │   ├── DialogBuscar.java
│           │   ├── DialogGestion.java
│           │   ├── DialogConsulta.java
│           │   ├── DialogLibro.java
│           │   ├── DialogRevista.java
│           │   ├── DialogDvd.java
│           │   └── DialogCdAudio.java
│           │
│           ├── util/
│           │   ├── CodigoGenerator.java
│           │   └── log4j2.properties
│           │
│           ├── resources/
│           │   └── (imágenes, íconos, etc.)
│           │
│           └── Main.java
│
├── docs/
│   └── (documentación del proyecto)
│
├── sql/
│   └── (scripts SQL de creación y prueba de la BD)
│
├── README.md
├── .gitignore
├── MediatecaLogs.log
└── PruebaMediateca.iml
```

---

## 👤 Autoría y Créditos

Proyecto académico desarrollado como parte de los **Desafíos 1 y 2** del curso de *Programación y Bases de Datos*.  
Aplicación de principios de **POO**, **modelado UML** y **persistencia de datos en Java**.

---
