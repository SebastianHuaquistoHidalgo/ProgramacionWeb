package pe.edu.upc.spring.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="reserva")
public class Reserva implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idReserva;
	
	@NotEmpty(message = "No puede estar vacío")
	@NotBlank(message = "No puede estar en blanco")
	@Column(name = "nombreTitularReserva", length = 60, nullable = false)
	private String nombreReserva;
	
	@NotNull
	@Past(message="No puedes seleccionar un dia que todavia NO existe")
	@Temporal(TemporalType.DATE)
	@Column(name="fechaInicioReserva")
	@DateTimeFormat(pattern="dd-MM-yyyy") 
	private Date fechaInicioReserva;
	
	@NotNull
	@Past(message="No puedes seleccionar un dia que todavia NO existe")
	@Temporal(TemporalType.DATE)
	@Column(name="fechaFinReserva")
	@DateTimeFormat(pattern="dd-MM-yyyy") 
	private Date fechaFinReserva;
	
	@NotEmpty(message ="No puede estar vacio")
	@NotBlank(message ="No puede estar en blanco")
	@Column(name="estadoReserva",length =60,nullable=false)
	private String estadoReserva;
	
	@ManyToOne
	@JoinColumn(name="idCliente",nullable = false)
	private Cliente cliente;
	
	@ManyToOne
	@JoinColumn(name="idTipoPago",nullable = false)
	private TipoPago tipoPago;

	@OneToMany(mappedBy = "idReserva", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Boleta> boletas;
	
	public Reserva(int idReserva, String nombreReserva, Date fechaInicioReserva, Date fechaFinReserva,
			String estadoReserva, Cliente cliente, TipoPago tipoPago, List<Boleta> boletas) {
		super();
		this.idReserva = idReserva;
		this.nombreReserva = nombreReserva;
		this.fechaInicioReserva = fechaInicioReserva;
		this.fechaFinReserva = fechaFinReserva;
		this.estadoReserva = estadoReserva;
		this.cliente = cliente;
		this.tipoPago = tipoPago;
		this.boletas = boletas;
	}

	public Reserva() {
		boletas = new ArrayList<>();
	}

	public int getIdReserva() {
		return idReserva;
	}

	public void setIdReserva(int idReserva) {
		this.idReserva = idReserva;
	}

	public String getNombreReserva() {
		return nombreReserva;
	}

	public void setNombreReserva(String nombreReserva) {
		this.nombreReserva = nombreReserva;
	}

	public Date getFechaInicioReserva() {
		return fechaInicioReserva;
	}

	public void setFechaInicioReserva(Date fechaInicioReserva) {
		this.fechaInicioReserva = fechaInicioReserva;
	}

	public Date getFechaFinReserva() {
		return fechaFinReserva;
	}

	public void setFechaFinReserva(Date fechaFinReserva) {
		this.fechaFinReserva = fechaFinReserva;
	}

	public String getEstadoReserva() {
		return estadoReserva;
	}

	public void setEstadoReserva(String estadoReserva) {
		this.estadoReserva = estadoReserva;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public TipoPago getTipoPago() {
		return tipoPago;
	}

	public void setTipoPago(TipoPago tipoPago) {
		this.tipoPago = tipoPago;
	}

	public List<Boleta> getBoletas() {
		return boletas;
	}

	public void setBoletas(List<Boleta> boletas) {
		this.boletas = boletas;
	}

}
