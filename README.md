
# **Currency Exchange MicroService**

Este proyecto implementa un servicio REST para convertir monedas utilizando resiliencia, caché y pruebas unitarias/mocks. Está diseñado para ser altamente robusto frente a fallos externos mediante el uso de patrones de tolerancia a fallos como `@CircuitBreaker`, `@Retry` y `@Timeout`.

---

## **Tecnologías utilizadas**

- **Java 17**
- **Quarkus Framework**
- **Docker** para contenedorización
- **Mockito** para pruebas unitarias

---

## **Cómo ejecutar**

### **Requisitos previos**

- **Java 17** instalado.
- **Docker** instalado y en funcionamiento.
- **Maven** configurado.

---

### **Opción 1: Ejecutar con Maven**

1. **Compilar y ejecutar el servicio en modo dev:**

   ```bash
   ./mvnw quarkus:dev
   ```

2. **Acceder a los endpoints disponibles:**

   - **POST `/api/exchange`**
     - Body JSON:
       ```json
       {
         "amount": 100,
         "sourceCurrency": "USD",
         "targetCurrency": "EUR"
       }
       ```

---

### **Opción 2: Ejecutar en un contenedor Docker**

1. **Construir el archivo JAR del proyecto:**

   ```bash
   ./mvnw clean package -DskipTests
   ```

2. **Construir la imagen Docker:**

   Asegúrate de que el archivo `Dockerfile` esté configurado correctamente. Ejecuta:

   ```bash
   docker build -t currency-exchange-service .
   ```

3. **Ejecutar el contenedor Docker:**

   ```bash
   docker run -d -p 8080:8080 --name currency-exchange-service currency-exchange-service
   ```

4. **Probar la API en el contenedor:**

   Puedes realizar una solicitud a través de `curl` o Postman:

   ```bash
   curl -X POST -H "Content-Type: application/json" -d '{
       "amount": 100,
       "sourceCurrency": "USD",
       "targetCurrency": "EUR"
   }' http://localhost:8080/api/exchange
   ```

---

## **Pruebas**

1. **Ejecutar pruebas con Maven:**

   ```bash
   ./mvnw test
   ```

2. **Resultados esperados:**
   - Todas las pruebas deben pasar con un reporte detallado.

---

## **Contribuciones**

1. **Clonar el repositorio:**

   ```bash
   git clone <URL del repositorio>
   ```

2. **Crear una rama:**

   ```bash
   git checkout -b feature/tu-rama ```



---
