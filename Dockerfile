FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /build

COPY pom.xml .

# Descargar las dependencias de Maven, pero solo si ha habido cambios en el pom.xml
RUN mvn dependency:go-offline -B

# Copiar el resto del código fuente de la aplicación
COPY src ./src

# Compilar la aplicación, pero saltando las pruebas para acelerar el proceso
RUN mvn package -DskipTests

# Etapa de construcción final: la imagen que servirá la aplicación
FROM registry.access.redhat.com/ubi8/openjdk-17:1.20

# Establecer las variables de entorno
ENV LANGUAGE='en_US:en'
ENV JAVA_OPTS_APPEND="-Dquarkus.http.host=0.0.0.0 -Djava.util.logging.manager=org.jboss.logmanager.LogManager"
ENV JAVA_APP_JAR="/deployments/quarkus-run.jar"

# Copia los archivos compilados desde la etapa de build a la imagen final
COPY --from=build /build/target/quarkus-app/lib/ /deployments/lib/
COPY --from=build /build/target/quarkus-app/*.jar /deployments/
COPY --from=build /build/target/quarkus-app/app/ /deployments/app/
COPY --from=build /build/target/quarkus-app/quarkus/ /deployments/quarkus/

# Expone el puerto 8080 para que la aplicación sea accesible
EXPOSE 8080
ENV QUARKUS_PROFILE=docker
# Usar un usuario no privilegiado
USER 185

# Configura el punto de entrada para ejecutar la aplicación en el contenedor
ENTRYPOINT ["/opt/jboss/container/java/run/run-java.sh"]
