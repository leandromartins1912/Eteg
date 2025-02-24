import axios from "axios";

export const httpClient = axios.create({
  baseURL: "http://localhost:8080/api", // Backend Spring Boot
  timeout: 5000,
  headers: { "Content-Type": "application/json" },
});
