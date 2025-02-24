import { Cliente } from "@/domain/models/cliente";
import { httpClient } from "../../infra/api/httpClient";
import { API_ROUTES } from "../../domain/constants/api-routes";

export class CreateCliente {
  async execute(cliente: Cliente): Promise<void> {
    await httpClient.post(API_ROUTES.CLIENTES, cliente);
  }
}
