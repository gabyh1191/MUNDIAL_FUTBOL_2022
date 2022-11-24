package Implementacion;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import DAO.SedeDAO;
import Principal.*;
import sql.miConexion;

public class SedeImp implements SedeDAO {
	private Statement sent;

	public SedeImp(Statement sent) {
		this.sent = sent;
	}

	/** Verifica que el id ingresado sea valido **/
	public boolean mostrarSedes() {
		ResultSet resul;
		try {
			resul = sent.executeQuery("SELECT * FROM SEDE ORDER BY idsede ASC");
			if (!resul.next())
				System.out.println("No hay sedes cargadas. Primero ingrese una.");
			else {
				System.out.println("-----------LISTA SEDES-----------\\n");
				System.out.println("\t0-  La sede no se encuentra");
				do {
					System.out.println("\t" + resul.getInt("idsede") + "-	" + resul.getString("nombre")
							+ " (Capacidad: " + resul.getInt("capacidad") + " personas)");
				} while (resul.next());
				System.out.println("\n----------------------------------");
				return true;
			}
		} catch (SQLException e) {
			System.out.println("ERROR de SQL (modificarSede): " + e.getMessage());
		}
		return false;
	}

	/** Verifica que el id ingresado sea valido **/
	public boolean idSedeValido(int id) {
		ResultSet resul;
		try {
			resul = sent.executeQuery("SELECT * FROM SEDE WHERE idsede=" + id);
			return resul.next(); // devuelve true si se encuentra en la base de datos
		} catch (SQLException e) {
			System.out.println("Error en SQL (idSedeValido): " + e.getMessage());
			return false;
		}
	}

	/** Verifica si una sede ya se encuentra en la base de datos **/
	public boolean existeSede(String nom) {
		try {
			ResultSet resul = sent.executeQuery("SELECT * FROM SEDE WHERE nombre='" + nom + "'");
			return resul.next(); // la sede existe
		} catch (SQLException e) {
			return false; // la sede no existe
		}
	}

	/** Modifica el campo seleccionado de una sede especifica **/
	public void modificarNombreSede(int id, String nom) {
		String query = "UPDATE sede SET nombre='" + nom + "' WHERE idsede=" + id;
		int error;
		try {
			error = sent.executeUpdate(query);
			if (error == 1)
				System.out.println("Se ha modificado exitosamente.");
			else
				System.out.println("Error en la actualizacion. No se modifico ningun dato.");
		} catch (SQLException e) {
			System.out.println("");
		} // error tiene la cantidad de filas modificadas.
		}
	public void modificarCapacidadSede(int id, int cap) {
		
	}
	public void modificarPaisSede(int id, int idpais) {
		
	}
	/*
	 * case 2: // modifica la capacidad de la sede System.out.print("Capacidad: ");
	 * int cap = leer.nextInt(); query = "UPDATE sede SET capacidad=" + cap +
	 * " WHERE idsede=" + sede; error = sent.executeUpdate(query); if (error != 0)
	 * System.out.println("Se ha modificado exitosamente."); else
	 * System.out.println("Error en la actualizacion. No se modifico ningun dato.");
	 * 
	 * break; case 3: // modifica el pais de la sede int p = elegirPais(sent); if (p
	 * != -1) { query = "UPDATE sede SET idpais=" + p + " WHERE idsede=" + sede;
	 * error = sent.executeUpdate(query); if (error != 0)
	 * System.out.println("Se ha modificado exitosamente."); else
	 * System.out.println("Error en la actualizacion. No se modifico ningun dato.");
	 * } else System.out.println("ERROR"); break; default:
	 * System.out.println("Opcion no disponible."); }
	 * 
	 * }}
	 */

	/** Elimina la sede ingresada por el usuario **/
	private void eliminarSede() {
		ResultSet resul;
		try {
			resul = sent.executeQuery("SELECT * FROM SEDE ORDER BY idsede ASC");
			if (!resul.next())
				System.out.println("No hay sedes cargadas. Primero ingrese una");
			else {
				System.out.println("LISTADO DE SEDES DISPONIBLES:");
				while (resul.next()) {
					System.out.println("\t" + resul.getInt(1) + "-	" + resul.getString(2) + " (Capacidad: "
							+ resul.getInt(3) + " personas)");
				}
				System.out.print("Sede a eliminar: ");
				int sede = leer.nextInt();
				resul = sent.executeQuery("SELECT * FROM SEDE WHERE idsede=" + sede);
				if (resul.next()) {
					String query = "DELETE FROM sede WHERE idsede=" + sede;
					sent.executeUpdate(query);
					System.out.println("Se ha modificado exitosamente.");
				} else
					System.out.println("No existe el ID ingresado.");
			}
		} catch (SQLException e) {
			System.out.println("ERROR de SQL (eliminarSede): " + e.getMessage());
		}
	}

	/** Ingreso por teclado de una sede. **/
	private void ingresarSede() {
		// Ingreso de datos
		System.out.println("\nINGRESO DE UNA SEDE");
		System.out.print("Nombre: ");
		String nombre = leer.nextLine();
		int idpais = elegirPais(sent);
		if (idpais != -1) { // si el pais es valido
			ResultSet resul;
			try {
				resul = sent.executeQuery("SELECT * FROM SEDE WHERE nombre='" + nombre + "' AND idpais=" + idpais);
				if (!resul.next()) {
					// Si la sede no esta repetida, continua con el ingreso de datos.
					System.out.print("Capacidad: ");
					int capacidad = leer.nextInt();
					leer.nextLine();
					Sede sede = new Sede(nombre, capacidad, idpais);

					resul = sent.executeQuery("SELECT nombre FROM PAIS WHERE idpais=" + idpais); // para el nombre del
																									// pais
					// Confirmacion de los datos ingresados
					System.out
							.print("\nConfirmar los datos ingresados\n" + sede + "\tPais: " + resul.getString("nombre")
									+ "\n¿Desea agregar " + sede.getNombre() + "? (SI para guardar):   ");
					String respuesta = leer.next();
					if (respuesta.equals("SI")) {
						// Almacenamiento en la BD de la sede ingresada
						sent.executeUpdate("INSERT INTO sede (nombre,capacidad,idpais) VALUES ('" + sede.getNombre()
								+ "', " + sede.getCapacidad() + "," + idpais + ");");
						System.out.println("Se ha agregado " + sede.getNombre() + " a la base de datos.\n");
					} else
						System.out.println("Los datos ingresados no han sido almacenados.");
				} else
					System.out.println("La sede ingresada ya se encuentra en la base de datos.");
			} catch (SQLException e) {
				System.out.println("Error en SQL (ingresarSede): " + e.getMessage());
			}
		}
	}
}
