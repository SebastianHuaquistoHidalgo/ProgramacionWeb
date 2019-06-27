package pe.edu.upc.spring.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Table(name="reservahabitacion")
public class ReservaHabitacion implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idReservaHabitacion;
	
	@ManyToOne
	@JoinColumn(name="idReserva",nullable = false)
	private Reserva reserva;
	
	@ManyToOne
	@JoinColumn(name="idHabitacion",nullable = false)
	private Habitacion habitacion;

	private String estadoHabitacion;

	public int getIdReservaHabitacion() {
		return idReservaHabitacion;
	}

	public void setIdReservaHabitacion(int idReservaHabitacion) {
		this.idReservaHabitacion = idReservaHabitacion;
	}

	public Reserva getReserva() {
		return reserva;
	}

	public void setReserva(Reserva reserva) {
		this.reserva = reserva;
	}

	public Habitacion getHabitacion() {
		return habitacion;
	}

	public void setHabitacion(Habitacion habitacion) {
		this.habitacion = habitacion;
	}

	public String getEstadoHabitacion() {
		return estadoHabitacion;
	}

	public void setEstadoHabitacion(String estadoHabitacion) {
		this.estadoHabitacion = estadoHabitacion;
	}
	
}
