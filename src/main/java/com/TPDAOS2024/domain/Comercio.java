package com.TPDAOS2024.domain;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="comercio")

public class Comercio { 
	

	@Id
	private Long cuit;
    
    
    private String razonSocial;
    private String direccion;
    private boolean estado;
    
    public Comercio() {
    	
    }
    
	public Comercio(Long cuit, String razonSocial, String direccion, boolean estado) {
		super();
		cuit = this.cuit;
		this.razonSocial = razonSocial;
		this.direccion = direccion;
		this.estado = estado;
	}


	public Long getCuit() {
		return cuit;
	}

	public void setCuit(Long cuit) {
		this.cuit = cuit;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public boolean getEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}
    

}
