package pe.edu.upc.spring.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "detalleboleta")
public class DetalleBoleta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private int cantidad;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idServicio")
	private Servicio idServicio;
		
	public DetalleBoleta(int id, int cantidad, Servicio idServicio) {
		super();
		this.id = id;
		this.cantidad = cantidad;
		this.idServicio = idServicio;
	}

	public DetalleBoleta() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public Servicio getIdServicio() {
		return idServicio;
	}

	public void setIdServicio(Servicio idServicio) {
		this.idServicio = idServicio;
	}
	
	public Double calculateAmount() {
		
		return cantidad*idServicio.getPrecioServicio();
	}
	
}

