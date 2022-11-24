package DAO;

public interface SedeDAO {
	public boolean mostrarSedes();
	public boolean idSedeValido(int id);
	public void modificarSede();
	public void modificarNombreSede(int id, String nom);
	public void modificarCapacidadSede(int id, int cap);
	public void modificarPaisSede(int id, int idpais);
	public void eliminarSede();
	public void ingresarSede();
}
