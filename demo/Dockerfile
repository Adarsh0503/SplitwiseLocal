# ── Stage 1: Build ──────────────────────────────────────────────────────────
FROM maven:3.9.6-eclipse-temurin-17 AS builder

WORKDIR /app

# Copy pom first for layer caching — dependencies only re-download if pom changes
COPY pom.xml .
RUN mvn dependency:go-offline -q

# Copy source and build
COPY src ./src
RUN mvn clean package -DskipTests -q

# ── Stage 2: Run ─────────────────────────────────────────────────────────────
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Create non-root user for security best practice
RUN addgroup -S splitwise && adduser -S splitwise -G splitwise
USER splitwise

# Copy only the built jar from the builder stage
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]
