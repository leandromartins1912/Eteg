import React, { useEffect, useState } from "react";
import { Cliente } from "@/domain/models/cliente";
import { CreateCliente } from "@/data/usecases/create-clientes";
import { UpdateCliente } from "@/data/usecases/update-clientes";

const coresArcoIris = [
  "Vermelho",
  "Laranja",
  "Amarelo",
  "Verde",
  "Azul",
  "Anil",
  "Violeta",
]; 

const ClienteForm = ({
  onClienteCriado,
  clienteEditando,
  setClienteEditando,
}: {
  onClienteCriado: () => void;
  clienteEditando: Cliente | null;
  setClienteEditando: (cliente: Cliente | null) => void;
}) => {
  const clienteInicial: Cliente = {
    id: 0,
    nome: "",
    email: "",
    cpf: "",
    cor: "",
    observacao: "",
  };
  const [cliente, setCliente] = useState<Cliente>(clienteInicial);
  const [isFormValid, setIsFormValid] = useState(false);
  const [errors, setErrors] = useState<{ [key: string]: string }>({});

  useEffect(() => {
    if (clienteEditando) {
      setCliente(clienteEditando);
      setErrors({});
    }
  }, [clienteEditando]);

  useEffect(() => {
    const isValid =
      cliente.nome.trim() !== "" &&
      cliente.email.trim() !== "" &&
      cliente.cpf.trim() !== "" &&
      cliente.cor.trim() !== "" &&
      cliente.observacao.trim() !== "";
    setIsFormValid(isValid);
  }, [cliente]);

  const handleChange = (
    e: React.ChangeEvent<
      HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement
    >
  ) => {
    setCliente({ ...cliente, [e.target.name]: e.target.value });
    setErrors({ ...errors, [e.target.name]: "" });
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    setErrors({}); 
    const service =
      cliente.id === 0 ? new CreateCliente() : new UpdateCliente();

    try {
      await service.execute(cliente);
      alert(cliente.id === 0 ? "Cliente criado com sucesso!" : "Cliente editado com sucesso!");
      handleClear();
      onClienteCriado();
    } catch (error: unknown) {
      if (
        error &&
        typeof error === "object" &&
        "response" in error &&
        error.response &&
        typeof error.response === "object" &&
        "data" in error.response &&
        error.response.data &&
        typeof error.response.data === "object" &&
        "erro" in error.response.data
      ) {
        const mensagemErro = (error.response.data as { erro: string }).erro;
        alert(mensagemErro);
      } else {
        alert("Ocorreu um erro inesperado.");
      }
    }
  }; 

  const handleClear = () => {
    setCliente(clienteInicial);
    setClienteEditando(null);
    setErrors({});
  };

  return (
    <form
      onSubmit={handleSubmit}
      className="max-w-md mx-auto p-6 bg-white shadow-md rounded-lg"
    >
      <div className="mb-4">
        <label
          htmlFor="nome"
          className="block text-sm font-medium text-gray-700"
        >
          Nome
        </label>
        <input
          type="text"
          name="nome"
          id="nome"
          placeholder="Nome"
          onChange={handleChange}
          value={cliente.nome}
          className="mt-2 p-3 w-full border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-indigo-500"
        />
      </div>

      <div className="mb-4">
        <label
          htmlFor="email"
          className="block text-sm font-medium text-gray-700"
        >
          Email
        </label>
        <input
          type="email"
          name="email"
          id="email"
          placeholder="Email"
          onChange={handleChange}
          value={cliente.email}
          className="mt-2 p-3 w-full border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-indigo-500"
        />
      </div>

      <div className="mb-4">
        <label
          htmlFor="cpf"
          className="block text-sm font-medium text-gray-700"
        >
          CPF
        </label>
        <input
          type="text"
          name="cpf"
          id="cpf"
          placeholder="CPF"
          onChange={handleChange}
          value={cliente.cpf}
          className="mt-2 p-3 w-full border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-indigo-500"
        />
      </div>

      <div className="mb-4">
        <label
          htmlFor="cor"
          className="block text-sm font-medium text-gray-700"
        >
          Cor Preferida
        </label>
        <select
          name="cor"
          id="cor"
          onChange={handleChange}
          value={cliente.cor}
          className="mt-2 p-3 w-full border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-indigo-500"
        >
          <option value="">Selecione uma cor</option>
          {coresArcoIris.map((cor) => (
            <option key={cor} value={cor}>
              {cor}
            </option>
          ))}
        </select>
      </div>

      <div className="mb-4">
        <label
          htmlFor="observacao"
          className="block text-sm font-medium text-gray-700"
        >
          Observação
        </label>
        <textarea
          name="observacao"
          id="observacao"
          placeholder="Adicione observações..."
          onChange={handleChange}
          value={cliente.observacao}
          className="mt-2 p-3 w-full border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-indigo-500"
        />
      </div>

      <button
        type="submit"
        disabled={!isFormValid}
        className={`w-full py-3 mt-6 font-semibold rounded-md shadow-md focus:outline-none focus:ring-2
          ${
            cliente.id === 0
              ? "bg-indigo-600 text-white hover:bg-indigo-700 focus:ring-indigo-500"
              : "bg-yellow-500 text-white hover:bg-yellow-600 focus:ring-yellow-400"
          }
          ${!isFormValid ? "opacity-50 cursor-not-allowed" : ""}
        `}
      >
        {cliente.id === 0 ? "Cadastrar" : "Editar"}
      </button>
      <button
        type="button"
        onClick={handleClear}
        className="w-full mt-2 py-3 text-gray-700 bg-gray-200 rounded-md shadow-md hover:bg-gray-300 focus:outline-none focus:ring-2 focus:ring-gray-400"
      >
        Limpar
      </button>
    </form>
  );
};

export default ClienteForm;
