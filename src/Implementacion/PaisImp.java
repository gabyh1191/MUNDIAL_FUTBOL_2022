package Implementacion;

import java.sql.*;
import DAO.PaisDAO;
import Principal.Pais;

public class PaisImp implements PaisDAO {
	private Statement sent;
	
	public PaisImp(Statement sent) {
		this.sent = sent;
	}
	
	/** Verifica si un pais ya se encuentra en la base de datos **/
	public boolean existePais(String nom) {
		try {
			ResultSet resul = sent.executeQuery("SELECT * FROM PAIS WHERE nombre='" + nom + "'");
			return resul.next(); // el pais existe
		} catch (SQLException e) {
			return false; // el pais no existe
		}
	}

	/** Ingresa un pais a la base de datos **/
	public void ingresarPais(String nom, String idioma) {
		try {
			sent.executeUpdate("INSERT INTO pais (nombre,idioma) VALUES ('" + nom + "', '" + idioma + "');");
			System.out.println("Se ha agregado " + nom + " a la base de datos.\n");
		} catch (SQLException e) {
			System.out.println("Error en SQL (ingresarPais): " + e.getMessage());
		}
	}

	/** Muestra listado de paises **/
	public boolean mostrarPaises() {
		try {
			ResultSet resul = sent.executeQuery("SELECT * FROM PAIS ORDER BY idpais ASC");
			if (!resul.next()) {
				System.out.println("No hay paises cargados, primero ingrese un pais.");
				
			} else {
				System.out.println("\n-----------LISTA PAISES-----------\n");
				System.out.println("\t0 EXIT");
				do {
					System.out.println(
							"\t" + resul.getInt(1) + "	" + resul.getString(2) + "(" + resul.getString(3) + ")");
				} while (resul.next());
				System.out.println("\n----------------------------------");
				return true;
			}
		} catch (SQLException e) {
			System.out.println("Error en SQL (mostrarPais): " + e.getMessage());
		}
		return false;
	}

	/**Verifica que el id ingresado coincida con el id de uno de los paises almacenados en la base de datos**/
	public boolean idPaisValido(int id) {
		ResultSet resul;
		try {
			resul = sent.executeQuery("SELECT * FROM PAIS WHERE idpais=" + id);
			return resul.next();//devuelve true si hay coincide
		} catch (SQLException e) {
			System.out.println("Error en SQL (idPaisValido): " + e.getMessage());
			return false; //devuelve false si no coincide o si ha ocurrido  algun problema
		}
	}

	/**Devuelve el Pais, dado el id recibido como parámetro**/
	public Pais getPais(int id) { 
		ResultSet resul;
		try {
			resul = sent.executeQuery("SELECT nombre,idioma FROM PAIS WHERE idpais=" + id);
			if (resul.next()) {
				return new Pais(resul.getString("nombre"), resul.getString("idioma"));
			}
		} catch (SQLException e) {
			System.out.println("Error en SQL (idValido): " + e.getMessage());
		}
		return null;
	}
		
}
