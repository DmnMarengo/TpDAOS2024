package com.TPDAOS2024.rest;

import com.TPDAOS2024.domain.Comercio;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

//@AUTHOR Damian Mateo Marengo
public class ComercioForm {
	


		@NotNull(message = "el CUIT no puede ser nulo")
		@Min(170000000)
		private Long cuit;
		@NotNull(message = "ingrese el nombre del local")
		private String razonSocial;
		@NotNull (message = "ingrese direccion del local")
		private String direccion;
		@NotNull
		private boolean estado;
		
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
		
		public Comercio toPojo()
		{
			Comercio c = new Comercio();
			c.setCuit(this.cuit);
			c.setRazonSocial(this.razonSocial);
		    c.setDireccion(this.direccion);
			c.setEstado(this.estado);
			return c;
		}
		
	}
	


