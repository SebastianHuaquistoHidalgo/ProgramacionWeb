package pe.edu.upc.spring.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "boleta")
public class Boleta {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "fechaboleta")
	@Temporal(TemporalType.DATE)
	private Date fechaboleta;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idReserva", nullable = false)
	private Reserva idReserva;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "boleta_id")
	private List<DetalleBoleta> boletaDetalle;

	public Boleta(int id, Date fechaboleta, Reserva idReserva, List<DetalleBoleta> boletaDetalle) {
		super();
		this.id = id;
		this.fechaboleta = fechaboleta;
		this.idReserva = idReserva;
		this.boletaDetalle = boletaDetalle;
	}

	public Boleta() {
		this.boletaDetalle = new ArrayList<>();
	}

	@PrePersist
	public void prePersist() {
		fechaboleta = new Date();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getFechaboleta() {
		return fechaboleta;
	}

	public void setFechaboleta(Date fechaboleta) {
		this.fechaboleta = fechaboleta;
	}

	public Reserva getIdReserva() {
		return idReserva;
	}

	public void setIdReserva(Reserva idReserva) {
		this.idReserva = idReserva;
	}

	public List<DetalleBoleta> getBoletaDetalle() {
		return boletaDetalle;
	}

	public void setBoletaDetalle(List<DetalleBoleta> boletaDetalle) {
		this.boletaDetalle = boletaDetalle;
	}

	public void addBoletaDetalle(DetalleBoleta item) {
		this.boletaDetalle.add(item);
	}

	public Double getTotal() {

		return boletaDetalle.stream().collect(Collectors.summingDouble(DetalleBoleta::calculateAmount));
	}
	
}

