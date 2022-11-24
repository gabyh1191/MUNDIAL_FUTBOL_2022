package Principal;

import sql.miConexion;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import DAO.*;

public class Menu {
	private static Connection con = miConexion.getCon();
	private static Scanner leer = new Scanner(System.in);

	public static void main(String[] args) {
		try {
			Statement sent = con.createStatement();
			FactoryDAO.sent = sent;
			int i;
			System.out.println("\nBIENVENIDO AL MUNDIAL"); // imprime el menu general
			do {
				System.out.println("\n --------Menu--------");
				System.out.println(
						"¿Con qué desea trabajar? " + "\n(Ingrese un numero segun la opcion que desea seleccionar)");
				System.out.println("0: EXIT " + "\n1: Futbolista " + "\n2: Pais " + "\n3: Sede");
				System.out.print("Opcion elegida: ");
				i = leer.nextInt(); // lee un entero
				leer.nextLine();
				switch (i) { // dado el entero ingresado, comienza una accion
				case 0:
					System.out.println("\n---------Fin del programa---------");
					break;
				case 1:
					menuFutbolista();// va al metodo menuFutbolista
					break;
				case 2:
					menuPais();// va al metodo menuPais
					break;
				case 3:
					menuSede();// va al metodo menuSede
					break;
				default:
					System.out.println("Opcion no valida");
				}
			} while (i != 0);
		} catch (SQLException e) {
			System.out.println("Error al conectar con la base de datos: " + e.getMessage());
		}

	}

	/** Muestra el menu con las opciones para el futbolista **/
	public static void menuFutbolista() {
		int j;
		System.out.println("\n ---Menu Futbolista--");// imprime el menuFutbolista
		System.out.println("¿Qué desea hacer? " + "\n(Ingrese un numero segun la opcion que desea seleccionar)");
		System.out.println("0: BACK " + "\n1: Agregar un futbolista ");
		System.out.print("Opcion elegida: ");
		j = leer.nextInt();// lee un entero
		leer.nextLine();
		switch (j) {// dado el entero ingresado, comienza una accion
		case 0:
			break;
		case 1:
			ingresarDatosFutbolista();
			break;
		default:
			System.out.println("Opcion invalida");
		}
	}

	/** Muestra el menu con las opciones para el pais **/
	public static void menuPais() {
		int j;
		System.out.println("\n ---Menu Pais--");
		System.out.println("¿Qué desea hacer? " + "\n(Ingrese un numero segun la opcion que desea seleccionar)");
		System.out.println("0: BACK " + "\n1: Agregar pais ");
		System.out.print("Opcion elegida: ");
		j = leer.nextInt();// lee un entero
		leer.nextLine();
		switch (j) {//
		case 0:
			break;
		case 1:
			ingresarDatosPais();
			break;
		default:
			System.out.println("Opcion invalida");
		}
	}

	/** Muestra el menu con las opciones para la sede **/
	public static void menuSede() {
		int j;
		System.out.println("\n ---Menu Sede--");// imprime menuSede
		System.out.println("¿Qué desea hacer? " + "\n(Ingrese un numero segun la opcion que desea seleccionar)");
		System.out.println(
				"0: BACK " + "\n1: Agregar sede " + "\n2: Actualizar datos de una sede" + "\n3: Eliminar una sede");
		System.out.print("Opcion elegida: ");
		j = leer.nextInt();// lee un entero
		leer.nextLine();
		switch (j) {
		case 0:
			break;
		case 1:
			ingresarDatosSede();// va al metodo ingresarDatosSede
			break;
		case 2:
			modificarSede();// va al metodo modificarSede
			break;
		case 3:
			eliminarSede();// va al metodo eliminarSede
			break;
		default:
			System.out.println("Opcion invalida");
		}
	}

	/**
	 * Ingresa los datos de un futbolista y los manda a la base de datos (usando el
	 * dao) para ser almacenados
	 **/
	public static void ingresarDatosFutbolista() {
		// ingreso de datos del futbolista
		System.out.println("\nINGRESO DE UN FUTBOLISTA");
		System.out.print("Nombre: ");
		String nom = leer.nextLine();// lee un string
		System.out.print("Apellido: ");
		String ape = leer.nextLine();// lee un string
		System.out.print("Ingrese dni: ");
		int dni = leer.nextInt();// lee un dni
		leer.nextLine();
		PaisDAO p = FactoryDAO.getPaisDAO();
		int idpais = elegirUnPais(p); // elige entre los paises previamente ingresados.
		// En caso de no haber, retorna -1
		if (idpais != -1 && idpais != 0) {
			FutbolistaDAO f = FactoryDAO.getFutbolistaDAO();
			if (!f.existeFutbolista(dni, idpais)) { // verifica que el futbolista no exista ya en la
				// base de datos (a traves de su dni y pais)
				System.out.print("Ingrese tel: ");
				long tel = leer.nextLong();// lee un long
				leer.nextLine();
				System.out.print("Ingrese email: ");
				String mail = leer.nextLine();// lee un string
				Futbolista fut = new Futbolista(nom, ape, dni, tel, mail, idpais); // instancia un
				// futbolista con los datos ingresados
				Pais pa = p.getPais(idpais);// trae el pais con el id correspondiente
				System.out.println("Confirme los datos ingresados\n" + fut + "\tPais: " + pa);
				System.out.print("¿Desea agregar a " + fut.getNombre() + " " + fut.getApellido() + " ("
						+ pa.getNombre() + ") (SI para guardar):   ");// confirma los datos ingresados
				String respuesta = leer.nextLine(); // lee un string
				if (respuesta.equals("SI")) {// si la respuesta coincide, manda al futbolista para que se
					// almacene en la base de datos
					f.ingresarFutbolista(fut);
				} else
					System.out.println("Los datos ingresados no han sido almacenados.");
			} else
				System.out.println("El futbolista ya se encuentra almacenado en la base de datos.");

		}
	}

	/**
	 * Ingresa los datos de un pais y los manda a la base de datos (usando el dao)
	 * para ser almacenados
	 **/
	public static void ingresarDatosPais() {
		// ingreso de datos de un pais
		System.out.println("\nINGRESO DE UN PAIS");
		System.out.print("Nombre: ");
		String nom = leer.nextLine(); // lee un string
		PaisDAO p = FactoryDAO.getPaisDAO();
		if (!p.existePais(nom)) { // verifica que el pais no exista ya en la base de datos (a traves del nombre)
			System.out.print("Idioma: ");
			String idioma = leer.next(); // lee un String
			Pais country = new Pais(nom, idioma); // instancia un Pais con los datos ingresados
			System.out.print("Confirme los datos ingresados\n" + country + "\n¿Desea agregar a " + country.getNombre()
					+ "? (SI para guardar):   ");// confirma los datos ingresados
			String respuesta = leer.next();
			if (respuesta.equals("SI")) {// si la respuesta coincide, manda el Pais para que se
				// almacene en la base de datos
				p.ingresarPais(nom, idioma);
			} else
				System.out.println("El pais no fue almacenado en la base de datos.");
		} else
			System.out.println("El pais ingresado ya se encuentra en la base de datos.");
	}

	/**
	 * Muestra los paises almacenados en la base de datos y se elige uno entre ellos
	 **/
	public static int elegirUnPais(PaisDAO p) {
		int id = -1;
		boolean valido = false;
		if (p.mostrarPaises()) { // si hay paises los imprime y devuelve true, sino avisa que
			// no hay existencias y devuelve false
			do {
				System.out.print("\nID del pais (): ");
				id = leer.nextInt(); // lee un entero
				if (id != 0) {
					valido = p.idPaisValido(id); // verifica que el id, correspondiente a un pais,
					// sea valido (true si lo es, false si no)
					if (!valido)
						System.out.println("ID invalido");
				} else
					return 0;
			} while (!valido); // mientras que no se ingrese un id valido para un pais, no sale
		}
		return id;
	}

	public static void ingresarDatosSede() {
		SedeDAO s = FactoryDAO.getSedeDAO();
		if (s.mostrarSedes()) {
			System.out.print("Sede a modificar: ");
			int id = leer.nextInt();// lee unn entero
			s.idSedeValido(id);

		}
	}

	public static void eliminarSede() {
		SedeDAO s = FactoryDAO.getSedeDAO();
		if (s.mostrarSedes()) {
			System.out.print("Sede a modificar: ");
			int id = leer.nextInt();
			s.idSedeValido(id);

		}
	}

	public static int elegirUnaSede(SedeDAO s) {
		int id = -1;
		boolean valido = false;
		if (s.mostrarSedes()) { // si hay sedes los imprime y devuelve true, sino avisa que
			// no hay existencias y devuelve false
			do {
				System.out.print("\nSede a modificar(): ");
				id = leer.nextInt(); // lee un entero
				if (id != 0) { // si es 0, el usuario no encontro la sede que queria
					valido = s.idSedeValido(id); // verifica que el id, correspondiente a un pais,
					// sea valido (true si lo es, false si no)
					if (!valido)
						System.out.println("ID invalido");
				} else
					return 0;
			} while (!valido); // mientras que no se ingrese un id valido para un pais, no sale
		}
		return id;
	}

	public static void menuModificarSede() {

	}

	public static void modificarSede() {
		System.out.println("Elija el numero de la sede que desea modificar: ");
		SedeDAO s = FactoryDAO.getSedeDAO();
		int id = elegirUnaSede(s);
		if (id != -1 && id != 0) {
			System.out.println("\nSeleccione el campo que desea modificar");
			System.out.println("1-\tNombre\n2-\tCapacidad\n3-\tPais");
			System.out.print("Opcion elegida: ");
			int respuesta = leer.nextInt();
			leer.nextLine();
			switch (respuesta) {
			case 1:
				System.out.print("Nuevo nombre: ");
				String nombre = leer.nextLine();
				System.out.println(nombre);
				s.modificarNombreSede(id, nombre);
				break;
			case 2:
				
				
			}
		} // muestra el listado de sedes (avisa si no hay ninguna)
	}
	/*
	 * switch(respuesta)
	 * 
	 * { case 1:{ //modifica el nombre de la sede
	 * System.out.print("Nuevo nombre: "); 
	 * String nombre = leer.nextLine();
	 * System.out.println(nombre); 
	 * query = "UPDATE sede SET nombre='" + nombre + "' WHERE idsede=" + sede; error = sent.executeUpdate(query); // error tiene
	 * la cantidad de filas modificadas. 
	 * if (error == 1)
	 * System.out.println("Se ha modificado exitosamente."); 
	 * else
	 * System.out.println("Error en la actualizacion. No se modifico ningun dato.");
	 * break; } case 2: // modifica la capacidad de la sede
	 * System.out.print("Capacidad: "); int cap = leer.nextInt(); query =
	 * "UPDATE sede SET capacidad=" + cap + " WHERE idsede=" + sede; error =
	 * .executeUpdate(query); if (error != 0)
	 * System.out.println("Se ha modificado exitosamente."); else
	 * System.out.println("Error en la actualizacion. No se modifico ningun dato.");
	 * 
	 * break; case 3: // modifica el pais de la sede int p = elegirPais(); if (p !=
	 * -1) { query = "UPDATE sede SET idpais=" + p + " WHERE idsede=" + sede; error
	 * = .executeUpdate(query); if (error != 0)
	 * System.out.println("Se ha modificado exitosamente."); else
	 * System.out.println("Error en la actualizacion. No se modifico ningun dato.");
	 * } else System.out.println("ERROR"); break; default:
	 * System.out.println("Opcion no disponible."); } }}}}
	 */
}
