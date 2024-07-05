package TPDAOS2024.dto;

import org.springframework.hateoas.RepresentationModel;

import com.TPDAOS2024.domain.Comercio;



public class comercioResponseDTO extends RepresentationModel<comercioResponseDTO> {

	private Long cuit;
	private String razonSocial;
	private String direccion;
	private boolean estado;
	
	public comercioResponseDTO (Comercio pojo) {
		super();
		this.cuit=pojo.getCuit();
		this.razonSocial=pojo.getDireccion();
		this.estado=pojo.getEstado();
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

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "comercioResponseDTO [cuit=" + cuit + ", razonSocial=" + razonSocial + ", direccion=" + direccion
				+ ", estado=" + estado + "]";
	}
	

}