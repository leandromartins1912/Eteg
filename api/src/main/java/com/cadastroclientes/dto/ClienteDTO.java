package com.cadastroclientes.dto;

public class ClienteDTO {
    
	private String nome;
    private String email;
    private String cor;
    private String cpf;
    private String observacao;    
    
    public ClienteDTO(String nome, String email, String cor, String cpf, String observacao) {
        this.nome = nome;
        this.email = email;
        this.cor = cor;
        this.cpf = cpf;
        this.observacao = observacao;
    }
    
    public ClienteDTO() {}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
    	    
}
