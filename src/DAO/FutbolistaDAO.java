package DAO;

import Principal.Futbolista;

public interface FutbolistaDAO {
	//declaraci�n de m�todos para acceder a la base de datos
	public boolean existeFutbolista (int dni, int idpais);
	public void ingresarFutbolista(Futbolista fut);
}
