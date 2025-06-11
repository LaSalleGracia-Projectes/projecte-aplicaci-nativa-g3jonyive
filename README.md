<a name="readme-top"></a>

# ConnectYourCoach

Plataforma web para conectar entrenadores y clientes en un entorno virtual de entrenamiento.

- [Ver Demo](#)
- [Reportar Bug](#)
- [Solicitar Característica](#)

## Tabla de Contenidos

1. [Sobre el Proyecto](#sobre-el-proyecto)
2. [Construido Con](#construido-con)
3. [Comenzando](#comenzando)
   - [Prerrequisitos](#prerrequisitos)
   - [Instalación](#instalación)
   - [Despliegue](#despliegue)
4. [Uso](#uso)
5. [Pruebas](#pruebas)
6. [Hoja de Ruta](#hoja-de-ruta)
7. [Licencia](#licencia)

## Sobre el Proyecto

ConnectYourCoach es una plataforma web que permite a entrenadores personales gestionar a sus clientes mediante una interfaz intuitiva y funcional. Incluye sesiones de coaching y perfiles de usuario.

**Características destacadas:**

- Gestión de rutinas de coaching personalizados
- Roles de entrenador y cliente diferenciados
- Registro e inicio de sesión de usuarios
- Panel de administración para sesiones
- Diseño limpio y responsive

## Construido Con

- Next.js
- React.js
- Tailwind CSS
- MongoDB
- Prisma
- NextAuth.js
- TypeScript

## Comenzando

Estas son las instrucciones para obtener una copia del proyecto y ponerlo en marcha localmente.

### Prerrequisitos

- Node.js >= 18.x
- MongoDB Atlas (o local)
- NPM o Yarn

```bash
npm install -g npm
```

### Instalación

1. Clonar el repositorio:
   ```bash
   git clone https://github.com/LaSalleGracia-Projectes/projecte-aplicaci-nativa-g3jonyive
   ```
2. Acceder al directorio:
   ```bash
   cd connectyourcoach
   ```
3. Instalar dependencias:
   ```bash
   npm install
   ```
4. Configurar variables de entorno en `.env`:
   ```env
   DATABASE_URL=tu_url_de_mongodb
   NEXTAUTH_SECRET=tu_secreto
   NEXTAUTH_URL=http://localhost:3000
   ```

### Despliegue

Para iniciar en modo desarrollo:

```bash
npm run dev
```

Para producción:

```bash
npm run build
npm start
```

## Uso

La aplicación permite a los entrenadores:

- Crear posts a clientes
- Registrar y editar ejercicios
- Consultar perfiles de clientes

Y a los clientes:

- Ver post asignadas
- Consultar ejercicios
- Seguir sus entrenamientos
``

## Hoja de Ruta

- [x] Autenticación con NextAuth
- [x] Gestión de roles (entrenador/cliente)
- [x] CRUD de rutinas y ejercicios

## Licencia

Distribuido bajo la Licencia MIT. Ver `LICENSE` para más información.

