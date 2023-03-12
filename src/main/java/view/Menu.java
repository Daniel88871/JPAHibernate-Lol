package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * La clase menu representa un texto/menu que hace posible la interactuacion del usuario con la base de datos.
 *
 * @author Daniel88871
 */
public class Menu {
    private int option;

    /**
     *
     * Un constructor del menu vacio, donde le pasamos la clase super (Object)
     *
     */
    public Menu() {
        super();
    }

    /**
     * Muestra las opciones del menu a base de souts para poder seleccionar una opcion.
     *
     @return La opcion que ha escogido el usuario
     */
    public int mainMenu() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        do {

            System.out.println(" \nMENU PRINCIPAL\n");

            System.out.println("1. Crear todas las tablas.");
            System.out.println("2. Rellenar los datos de las tablas con los CSV.");
            System.out.println("3. Listar toda la tabla campeones.");
            System.out.println("4. Listar toda la tabla hechizos.");
            System.out.println("5. Listar toda la tabla objetos.");
            System.out.println("6. Listar campeones y ordenar por nombre");
            System.out.println("7. Listar hechizos y ordenar por nombre");
            System.out.println("8. Listar objetos y ordenar por nombre");
            System.out.println("9. Modificar un campeon");
            System.out.println("10. Modificar un hechizo");
            System.out.println("11. Modificar un objeto");
            System.out.println("12. Borrar un campeon por su nombre.");
            System.out.println("13. Borrar un hechizo por su nombre.");
            System.out.println("14. Borrar un objeto por su nombre.");
            System.out.println("15. Borrar todas las tablas.");

            System.out.println("0. Salir. ");

            System.out.println("Escoje una opción: ");
            try {
                option = Integer.parseInt(br.readLine());
            } catch (NumberFormatException | IOException e) {
                System.out.println("valor no válido");
                e.printStackTrace();
            }
        } while (option != 1  && option != 2 && option != 3 && option != 4 && option != 5 && option != 6
                && option != 7 && option != 8 && option != 9 && option != 10 && option != 11 && option != 12
                && option != 13 && option != 14 && option != 15 && option != 0);

        return option;
    }
}