package DAO;

import Principal.Pais;

public interface PaisDAO {
	public boolean existePais (String nom);
	public void ingresarPais(String nom, String idioma);
	public boolean mostrarPaises();
	public boolean idPaisValido(int id);
	public Pais getPais(int id);
}
