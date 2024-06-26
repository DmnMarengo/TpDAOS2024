package com.TPDAOS2024.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuario")
public class Usuario {

	@Id
	private Long DNI;

	private String nombre;
	private String apellido;
	private String domicilio;
	private String mail;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaNacimiento;

	@OneToOne(mappedBy = "usuario", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Patente patenteVehiculo;

	private String contrasenia;

	private String saldoCuenta;

	public Usuario() {
		super();
	}

	public Usuario(Long dNI, String nombre, String apellido, String domicilio, String mail, Date fechaNacimiento,
			Patente patenteVehiculo, String contrasenia, String saldoCuenta) {
		super();
		DNI = dNI;
		this.nombre = nombre;
		this.apellido = apellido;
		this.domicilio = domicilio;
		this.mail = mail;
		this.fechaNacimiento = fechaNacimiento;
		this.patenteVehiculo = patenteVehiculo;
		this.contrasenia = contrasenia;
		this.saldoCuenta = saldoCuenta;
	}

	public Long getDNI() {
		return DNI;
	}

	public void setDNI(Long dNI) {
		DNI = dNI;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public Patente getPatenteVehiculo() {
		return patenteVehiculo;
	}

	public void setPatenteVehiculo(Patente patenteVehiculo) {
		this.patenteVehiculo = patenteVehiculo;
	}

	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	public String getSaldoCuenta() {
		return saldoCuenta;
	}

	public void setSaldoCuenta(String saldoCuenta) {
		this.saldoCuenta = saldoCuenta;
	}

	@Override
	public String toString() {
		return "Usuario [DNI=" + DNI + ", nombre=" + nombre + ", apellido=" + apellido + ", domicilio=" + domicilio
				+ ", mail=" + mail + ", fechaNacimiento=" + fechaNacimiento + ", patenteVehiculo=" + patenteVehiculo
				+ ", contrasenia=" + contrasenia + ", saldoCuenta=" + saldoCuenta + "]";
	}

}
