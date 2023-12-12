# Utiliza la imagen oficial de OpenJDK 17
FROM openjdk:17-jdk-alpine

# Establece el directorio de trabajo en la imagen
WORKDIR /app

# Copia solo el archivo JAR construido de tu aplicación en la imagen
COPY target/demo-0.0.1-SNAPSHOT.jar /app/

# Exponer el puerto en el que se ejecutará la aplicación
EXPOSE 8080

# Comando para ejecutar la aplicación cuando el contenedor se inicie
CMD ["java", "-Xms256m", "-Xmx512m", "-jar", "demo-0.0.1-SNAPSHOT.jar"]
