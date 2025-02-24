import { Cliente } from "@/domain/models/cliente";
import { PencilIcon } from "@heroicons/react/24/outline";

const formatCPF = (cpf: string) => {
  return cpf.replace(/(\d{3})(\d{3})(\d{3})(\d{2})/, "$1.$2.$3-$4");
};

const SkeletonRow = () => (
  <tr className="border-b animate-pulse">
    <td className="px-4 py-2 text-sm text-gray-300 bg-gray-200"></td>
    <td className="px-4 py-2 text-sm text-gray-300 bg-gray-200"></td>
    <td className="px-4 py-2 text-sm text-gray-300 bg-gray-200"></td>
    <td className="px-4 py-2 text-sm text-gray-300 bg-gray-200"></td>
    <td className="px-4 py-2 text-sm text-gray-300 bg-gray-200"></td>
    <td className="px-4 py-2 text-sm text-gray-300 bg-gray-200"></td>
  </tr>
);

const ClienteTable = ({
  clientes,
  onEdit,
  isLoading,
}: {
  clientes: Cliente[];
  onEdit: (cliente: Cliente) => void;
  isLoading: boolean;
}) => {
  return (
    <div className="overflow-x-auto">
      <table className="min-w-full table-auto">
        <thead className="bg-gray-100">
          <tr>
            <th className="px-4 py-2 text-left text-sm font-medium text-gray-600">
              Nome
            </th>
            <th className="px-4 py-2 text-left text-sm font-medium text-gray-600">
              Email
            </th>
            <th className="px-4 py-2 text-left text-sm font-medium text-gray-600">
              CPF
            </th>
            <th className="px-4 py-2 text-left text-sm font-medium text-gray-600">
              Cor
            </th>
            <th className="px-4 py-2 text-left text-sm font-medium text-gray-600">
              Observação
            </th>
            <th className="px-4 py-2 text-left text-sm font-medium text-gray-600"></th>
          </tr>
        </thead>
        <tbody>
          {isLoading
            ? // Renderiza esqueleto de carregamento enquanto os dados estão sendo carregados
              Array.from({ length: 5 }).map((_, index) => (
                <SkeletonRow key={index} />
              ))
            : clientes.map((cliente) => (
                <tr key={cliente.id} className="border-b hover:bg-gray-50">
                  <td className="px-4 py-2 text-sm text-gray-700">
                    {cliente.nome}
                  </td>
                  <td className="px-4 py-2 text-sm text-gray-700">
                    {cliente.email}
                  </td>
                  <td className="px-4 py-2 text-sm text-gray-700">
                    {formatCPF(cliente.cpf)}
                  </td>
                  <td className="px-4 py-2 text-sm text-gray-700">
                    {cliente.cor}
                  </td>
                  <td className="px-4 py-2 text-sm text-gray-700">
                    {cliente.observacao}
                  </td>
                  <td className="px-4 py-2 text-sm text-gray-700">
                    <button
                      onClick={() => onEdit(cliente)}
                      className="text-yellow-400 hover:text-yellow-700"
                    >
                      <PencilIcon className="w-6 h-6" />
                    </button>
                  </td>
                </tr>
              ))}
        </tbody>
      </table>
    </div>
  );
};

export default ClienteTable;
