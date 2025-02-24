
import ClienteTable from "@/presentation/components/ClienteTable";
import ClienteForm from "@/presentation/components/ClienteForm";
import { useEffect, useState } from "react";
import { Cliente } from "@/domain/models/cliente";
import { GetClientes } from "@/data/usecases/get-clientes";

const ClientesPage = () => {

  const [clientes, setClientes] = useState<Cliente[]>([]);
  const [clienteEditando, setClienteEditando] = useState<Cliente | null>(null);
  const [isLoading, setIsLoading] = useState(true);

  const fetchClientes = async () => {
    const service = new GetClientes();
    const data = await service.execute();
    setClientes(data);
    setIsLoading(false);
  };

 useEffect(() => {
    fetchClientes();
  }, []);

  const handleEdit = (cliente: Cliente) => {
    setClienteEditando(cliente);
  };
  
  return (
    <div className="min-h-screen bg-gray-100 p-6">
      <div className="max-w-4xl mx-auto bg-white shadow-lg rounded-lg p-6">
        <h1 className="text-2xl font-bold text-gray-800 mb-4 text-center">
          Gerenciamento de Clientes
        </h1>
        <ClienteForm
          onClienteCriado={fetchClientes}
          clienteEditando={clienteEditando}
          setClienteEditando={setClienteEditando}
        />
        <div className="mt-6">
          <ClienteTable
            clientes={clientes}
            onEdit={handleEdit}
            isLoading={isLoading}
          />
        </div>
      </div>
    </div>
  );
};

export default ClientesPage;
