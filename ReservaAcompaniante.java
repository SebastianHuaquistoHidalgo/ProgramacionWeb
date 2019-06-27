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
@Table(name="ReservaAcompaniante")
public class ReservaAcompaniante implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idReservaAcompaniante;
	
	@ManyToOne
	@JoinColumn(name="idAcompaniante")
	private Acompaniante acompaniante;
	
	@ManyToOne
	@JoinColumn(name="idReserva")
	private Reserva reserva;
	
	
	public int getIdReservaAcompaniante() {
		return idReservaAcompaniante;
	}

	public void setIdReservaAcompaniante(int idReservaAcompaniante) {
		this.idReservaAcompaniante = idReservaAcompaniante;
	}

	public Acompaniante getAcompaniante() {
		return acompaniante;
	}

	public void setAcompaniante(Acompaniante acompaniante) {
		this.acompaniante = acompaniante;
	}

	public Reserva getReserva() {
		return reserva;
	}

	public void setReserva(Reserva reserva) {
		this.reserva = reserva;
	}
}
