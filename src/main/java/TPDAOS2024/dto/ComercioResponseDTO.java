package TPDAOS2024.dto;

import org.springframework.hateoas.RepresentationModel;

import com.TPDAOS2024.domain.Comercio;


//@AUTHOR Damian Mateo Marengo
public class ComercioResponseDTO extends RepresentationModel<ComercioResponseDTO> {

    private Long cuit;
    private String razonSocial;
    private String direccion;
    private boolean estado;

    public ComercioResponseDTO(Comercio comercio) {
        this.cuit = comercio.getCuit();
        this.razonSocial = comercio.getRazonSocial();
        this.direccion = comercio.getDireccion();
        this.estado = comercio.isEstado();

    }

    // Getters y setters
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
}