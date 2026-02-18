# PowerFit API - Sistema de Gestión de Gimnasio

## Descripción
PowerFit es una API REST desarrollada en Java puro que permite gestionar un gimnasio, incluyendo planes de entrenamiento y socios. La aplicación utiliza MySQL como base de datos sin frameworks asistentes.

## Características
- ✅ API REST completa (CRUD) para Planes y Socios
- ✅ Base de datos MySQL con 2 tablas relacionadas
- ✅ Servidor HTTP implementado sin frameworks
- ✅ Manejo de JSON manual
- ✅ Validación de datos
- ✅ Gestión de conexiones a BD

## Tecnologías
- **Lenguaje**: Java 11+
- **Base de Datos**: MySQL
- **Servidor Web**: Socket HTTP nativo (sin frameworks)
- **Build Tool**: Maven

## Estructura del Proyecto
```
powerfit-api/
├── src/
│   └── main/
│       ├── java/com/powerfit/
│       │   ├── models/      (Plan, Socio)
│       │   ├── dao/         (PlanDAO, SocioDAO)
│       │   ├── utils/       (DatabaseConnection, JsonResponse, QueryParser)
│       │   └── server/      (PowerFitAPIServer)
│       └── resources/
└── pom.xml
```

## Instalación y Configuración

### Requisitos Previos
- Java 11 o superior
- MySQL Server
- Maven 3.6+

### Pasos de Instalación

1. **Crear la base de datos**
   ```sql
   -- Ejecutar el script techstore_db.sql en MySQL
   ```

2. **Configurar conexión a BD** (en `DatabaseConnection.java`)
   ```java
   private static final String DB_URL = "jdbc:mysql://localhost:3306/powerfit_db";
   private static final String DB_USER = "root";
   private static final String DB_PASSWORD = "";
   ```

3. **Compilar el proyecto**
   ```bash
   mvn clean compile
   ```

4. **Empaquetar**
   ```bash
   mvn package
   ```

5. **Ejecutar la aplicación**
   ```bash
   java -cp target/powerfit-api.jar com.powerfit.server.PowerFitAPIServer
   ```

## Endpoints API

### Planes
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/planes` | Obtener todos los planes |
| GET | `/api/planes?id=1` | Obtener plan por ID |
| POST | `/api/planes` | Crear nuevo plan |
| PUT | `/api/planes?id=1` | Actualizar plan |
| DELETE | `/api/planes?id=1` | Eliminar plan |

### Socios
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/socios` | Obtener todos los socios |
| GET | `/api/socios?id=1` | Obtener socio por ID |
| GET | `/api/socios?dni=77665544` | Obtener socio por DNI |
| POST | `/api/socios` | Crear nuevo socio |
| PUT | `/api/socios?id=1` | Actualizar socio |
| DELETE | `/api/socios?id=1` | Eliminar socio |

## Ejemplos de Uso

### Crear un Plan
```bash
curl -X POST http://localhost:8080/api/planes \
  -H "Content-Type: application/json" \
  -d '{
    "nombrePlan": "Basico",
    "descripcion": "Acceso a maquinas cardio",
    "precio": "80.00",
    "duracionMeses": "1"
  }'
```

### Crear un Socio
```bash
curl -X POST http://localhost:8080/api/socios \
  -H "Content-Type: application/json" \
  -d '{
    "nombreCompleto": "Juan Perez",
    "dni": "77665544",
    "email": "juan@email.com",
    "telefono": "987654321",
    "idPlan": "1",
    "fechaVencimiento": "2026-03-20"
  }'
```

### Obtener todos los planes
```bash
curl http://localhost:8080/api/planes
```

### Obtener socio por DNI
```bash
curl "http://localhost:8080/api/socios?dni=77665544"
```

## Base de Datos

### Tabla: planes
| Campo | Tipo | Descripción |
|-------|------|-------------|
| id_plan | INT | PK Auto-increment |
| nombre_plan | VARCHAR(50) | Nombre del plan |
| descripcion | VARCHAR(200) | Descripción |
| precio | DECIMAL(10,2) | Precio mensual |
| duracion_meses | INT | Duración en meses |
| fecha_creacion | TIMESTAMP | Fecha de creación |

### Tabla: socios
| Campo | Tipo | Descripción |
|-------|------|-------------|
| id_socio | INT | PK Auto-increment |
| nombre_completo | VARCHAR(100) | Nombre completo |
| dni | VARCHAR(8) | DNI (único) |
| email | VARCHAR(100) | Email |
| telefono | VARCHAR(15) | Teléfono |
| id_plan | INT | FK a planes |
| estado | VARCHAR(20) | activo/inactivo |
| fecha_registro | TIMESTAMP | Fecha de registro |
| fecha_vencimiento | DATE | Vencimiento de membresía |

## Entregas

### Entrega 1: Modelos y Conexión
- ✅ Modelos: Plan y Socio
- ✅ Conexión a base de datos
- ✅ Utilidades de conexión

### Entrega 2: Data Access Objects
- ✅ PlanDAO (CRUD)
- ✅ SocioDAO (CRUD)
- ✅ Métodos de consulta avanzados

### Entrega 3: API REST
- ✅ Servidor HTTP nativo
- ✅ Endpoints REST completos
- ✅ Manejo de JSON
- ✅ Control de errores

## Notas Importantes
- No se utilizaron frameworks asistentes (como Spring)
- El servidor HTTP se implementó usando Sockets nativos de Java
- El parseo de JSON se realizó manualmente
- Todas las conexiones a BD se manejan con try-with-resources
- Se implementó arquitectura en capas: Modelos → DAO → Servidor

## Autor
Proyecto desarrollado como práctica académica

## Licencia
MIT
