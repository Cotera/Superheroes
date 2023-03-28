# Imagen base
FROM openjdk:11

# Directorio de trabajo
WORKDIR /app

# Copiar el archivo JAR al contenedor
COPY target/myapp.jar /app

# Puerto que expone la aplicación
EXPOSE 8080

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "myapp.jar"]
