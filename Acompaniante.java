package pe.edu.upc.spring.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="acompaniante")
public class Acompaniante implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idAcompaniante;
	
	@Column(name ="nombreAcompaniante", length=60, nullable=false)
	private String nombreAcompaniante;
	
	@Column(name ="apellidoPaternoAcompaniante", length=60, nullable=false)
	private String apellidoPaternoAcompaniante;
	
	@Column(name ="apellidoMaternoAcompaniante", length=60, nullable=false)
	private String apellidoMaternoAcompaniante;
	
	@Column(name ="DNI", length=60, nullable=false)
	private int DNI;
	
	public Acompaniante() {
		super();

	}
	
		public Acompaniante(int idAcompaniante, String nombreAcompaniante, String apellidoPaternoAcompaniante,
		String apellidoMaternoAcompaniante, int dNI) {
		super();
		
		this.idAcompaniante = idAcompaniante;
		this.nombreAcompaniante = nombreAcompaniante;
		this.apellidoPaternoAcompaniante = apellidoPaternoAcompaniante;
		this.apellidoMaternoAcompaniante = apellidoMaternoAcompaniante;
		DNI = dNI;
	}

	public int getIdAcompaniante() {
		return idAcompaniante;
	}

	public String getNombreAcompaniante() {
		return nombreAcompaniante;
	}

	public void setNombreAcompaniante(String nombreAcompaniante) {
		this.nombreAcompaniante = nombreAcompaniante;
	}

	public String getApellidoPaternoAcompaniante() {
		return apellidoPaternoAcompaniante;
	}

	public void setApellidoPaternoAcompaniante(String apellidoPaternoAcompaniante) {
		this.apellidoPaternoAcompaniante = apellidoPaternoAcompaniante;
	}

	public String getApellidoMaternoAcompaniante() {
		return apellidoMaternoAcompaniante;
	}

	public void setApellidoMaternoAcompaniante(String apellidoMaternoAcompaniante) {
		this.apellidoMaternoAcompaniante = apellidoMaternoAcompaniante;
	}

	public int getDNI() {
		return DNI;
	}

	public void setDNI(int dNI) {
		DNI = dNI;
	}

	public void setIdAcompaniante(int idAcompaniante) {
		this.idAcompaniante = idAcompaniante;
	}

}
