package Principal;

public class Pais {
	private String nombre;
	private String idioma;

	public Pais() {
	}

	public Pais(String nombre, String idioma) {
		this.nombre = nombre;
		this.idioma = idioma;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getIdioma() {
		return idioma;
	}

	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

	public String toString() {
		return "\n\tNombre: " + this.nombre + "\n\tIdioma: " + this.getIdioma() + "\n";
	}

}
