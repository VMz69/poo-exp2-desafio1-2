# 📚 Sistema de Gestión de Mediateca

Proyecto académico basado en los **Desafíos 1 y 2** del curso de Programación y Bases de Datos.  
Su objetivo es diseñar y desarrollar un sistema que permita la gestión de materiales disponibles en una mediateca (libros, revistas, CDs y DVDs), aplicando conceptos de **modelado relacional**, **UML** y **Programación Orientada a Objetos (POO)** en Java.

---

## 🧩 **Descripción General**

La mediateca cuenta con diferentes tipos de materiales que pueden ser gestionados por el sistema.  
El proyecto se desarrolla en dos fases principales:

### 🔹 Desafío 1 – Análisis y Diseño
- Elaboración de diagramas de **Casos de Uso** utilizando **UML**.  
- Creación del **modelo relacional de base de datos** en **MySQL**, según los requisitos del enunciado.  
- Modelado de entidades que representan libros, revistas, CDs de audio y DVDs.

### 🔹 Desafío 2 – Implementación
- Desarrollo de un sistema de escritorio en **Java** con **Swing**.  
- Uso de **herencia y clases abstractas** para representar la jerarquía de materiales.  
- Conexión a base de datos mediante **JDBC**.  
- Manejo de errores con **Log4J**.  
- Persistencia de datos en archivos y base de datos.

---

## 🧱 **Requisitos Funcionales**

El sistema debe permitir:
- Agregar nuevo material  
- Modificar material existente  
- Listar materiales disponibles  
- Eliminar material  
- Buscar material por criterios  
- Salir del sistema

---

## 💾 **Estructura de Datos**

Cada tipo de material contiene atributos específicos:

| Tipo de material | Atributos principales |
|------------------|-----------------------|
| **Libro** | Código interno, título, autor, número de páginas, editorial, ISBN, año de publicación, unidades disponibles |
| **Revista** | Código interno, título, editorial, periodicidad, fecha de publicación, unidades disponibles |
| **CD de Audio** | Código interno, título, artista, género, duración, número de canciones, unidades disponibles |
| **DVD** | Código interno, título, director, duración, género |

El código interno se genera automáticamente con prefijos:
- `LIB` para libros  
- `REV` para revistas  
- `CDA` para CDs  
- `DVD` para DVDs  

---

## 🧠 **Conceptos Aplicados**
- **UML:** diagramas de casos de uso y modelo relacional.  
- **POO:** herencia, abstracción y reutilización de código.  
- **JDBC:** conexión con MySQL.  
- **Swing:** creación de interfaces gráficas.  
- **Log4J:** registro y manejo de errores.  

---

## ⚙️ **Tecnologías Utilizadas**
- **Lenguaje:** Java  
- **Interfaz:** Swing  
- **Base de datos:** MySQL  
- **Gestión de errores:** Log4J  
- **Modelado:** UML  

---

## 🧩 **Estructura de Carpetas (preliminar)**

```
mediateca/
│
├── src/
│   ├── modelo/         # Clases: Material, Libro, Revista, CD, DVD
│   ├── vista/          # Ventanas Swing
│   └── controlador/    # Lógica y conexión a la BD
│
├── sql/
│   └── mediateca.sql   # Script del modelo relacional
│
├── docs/
│   └── diagramas/      # Casos de uso y modelo E/R
│
└── README.md
```

---

## 🧩 **Autoría y Créditos**
Proyecto académico desarrollado como parte de los **Desafíos 1 y 2** del curso de **Programación y Bases de Datos**, con el propósito de aplicar los conocimientos de modelado, POO y persistencia de datos.

---
