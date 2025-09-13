# Etapa 1: build
FROM node:22.11.0 AS build

# Establecer directorio de trabajo
WORKDIR /app

# Copiar package.json y package-lock.json (para cachear dependencias)
COPY package*.json ./

# Instalar dependencias
RUN npm install

# Copiar todo el proyecto
COPY . .

# Construir la app para producción
RUN npm run build

# Etapa 2: servir la app
FROM nginx:alpine

# Copiar build de React al directorio que Nginx sirve
COPY --from=build /app/build /usr/share/nginx/html

# Exponer puerto
EXPOSE 80

# Comando por defecto
CMD ["nginx", "-g", "daemon off;"]
