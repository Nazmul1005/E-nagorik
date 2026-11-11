# E-Nagorik

E-Nagorik is a citizen-first municipal service portal. Citizens can browse civic
services, submit and track requests, while officer and mayor workspaces provide
review and approval views.

## Project structure

- `e-nagorik-backend/` - dependency-free Java 17 HTTP API
- `e-nagorik-frontend/` - React + TypeScript + Vite portal

## Run locally

### Backend

```bash
cd e-nagorik-backend
mkdir -p out
javac -d out $(find src -name '*.java')
java -cp out com.enagorik.Main
```

The API listens on `http://localhost:8080`.

### Frontend

```bash
cd e-nagorik-frontend
npm ci
npm run dev
```

The frontend runs on `http://localhost:5173` and includes mock data for
standalone development.

## Demo accounts

| Role | User ID |
| --- | ---: |
| Citizen | 1 |
| Officer | 2 |
| Mayor | 3 |

## GitHub

Every push and pull request runs the Java compilation and frontend production
build through GitHub Actions.

