import { Cliente } from "@/domain/models/cliente";
import { httpClient } from "@/infra/api/httpClient";
import { API_ROUTES } from "@/domain/constants/api-routes";

export class GetClientes {
  async execute(): Promise<Cliente[]> {
    const response = await httpClient.get<Cliente[]>(API_ROUTES.CLIENTES);
    return response.data;
  }
}
