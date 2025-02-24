import { Cliente } from "@/domain/models/cliente";
import { httpClient } from "@/infra/api/httpClient";
import { API_ROUTES } from "@/domain/constants/api-routes";

export class UpdateCliente {
  async execute(cliente: Cliente): Promise<void> {
    await httpClient.put(`${API_ROUTES.CLIENTES}/${cliente.id}`, cliente);
  }
}
