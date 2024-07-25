package com.TPDAOS2024.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "patente")
public class Patente {

	@Id
	private String numeroPatente;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario_dni")
	@JsonBackReference
	private Usuario usuario;

	public Patente() {
		super();
	}

	public Patente(String numeroPatente, Usuario usuario) {
		super();
		this.numeroPatente = numeroPatente;
		this.usuario = usuario;
	}
	
    public void actualizar(String nuevoNumeroPatente, Usuario usuario) {
        this.numeroPatente = nuevoNumeroPatente;
        this.usuario = usuario;
    }
    
    public void actualizarNumeroPatente(String nuevoNumeroPatente) {
        this.numeroPatente = nuevoNumeroPatente;
    }

	public String getNumeroPatente() {
		return numeroPatente;
	}

	public void setNumeroPatente(String numeroPatente) {
		this.numeroPatente = numeroPatente;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public String toString() {
		return "Patente [numeroPatente=" + numeroPatente + ", usuario=" + usuario + "]";
	}
}
