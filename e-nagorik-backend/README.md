# E-Nagorik backend

Dependency-free Java 17 HTTP service. Run `javac -d out $(find src -name '*.java') && java -cp out com.enagorik.Main`.

The service persists JSON data under `data/`, seeds demo users and civic services, and exposes JSON APIs with CORS.
