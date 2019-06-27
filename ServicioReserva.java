package pe.edu.upc.spring.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="servicioreserva")
public class ServicioReserva implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idServicioReserva;
	
	@ManyToOne
	@JoinColumn(name="idReserva",nullable = false)
	private Reserva reserva;
	
	@ManyToOne
	@JoinColumn(name="idServicio",nullable = false)
	private Servicio servicio;

	public int getIdServicioReserva() {
		return idServicioReserva;
	}

	public void setIdServicioReserva(int idServicioReserva) {
		this.idServicioReserva = idServicioReserva;
	}

	public Reserva getReserva() {
		return reserva;
	}

	public void setReserva(Reserva reserva) {
		this.reserva = reserva;
	}

	public Servicio getServicio() {
		return servicio;
	}

	public void setServicio(Servicio servicio) {
		this.servicio = servicio;
	}
}
