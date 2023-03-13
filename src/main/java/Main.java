import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import controller.CampeonController;
import controller.HechizosController;
import controller.ObjetosController;
import database.ConnectionFactory;
import model.*;
import model.Campeones;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import view.Menu;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Esta clase nos da lo necesario para que el usuario pueda interactuar con nuestra base de datos
 * a partir de un menu que nosotros proveemos, el usuario escoge una opción con un int, y nuestro programa
 * hará lo que la opción del menu nos ha indicado
 *
 * @author Daniel88871
 */
public class Main {
  private static final Scanner scanner = new Scanner(System.in);

  static SessionFactory sessionFactoryObj;

  /**
   * Creamos un constructor vacio de Main
   *
   */
  public Main(){}

  /**
   * Construye un Object Hibernate para que podamos empezar a interactuar con la base de datos.
   *
   * @return Nos devuelve el Hibernate que hemos construido con una clase SessionFactory.
   */
  private static SessionFactory buildSessionFactory() {
    try {
      StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
              .configure("hibernate.cfg.xml").build();
      Metadata metadata = new MetadataSources(standardRegistry).getMetadataBuilder().build();
      return metadata.getSessionFactoryBuilder().build();

    } catch (HibernateException he) {
      System.out.println("Session Factory creation failure");
      throw he;
    }
  }

  /**
   * Crea un EntityManagerFactory para interactuar con el framework.
   *
   * @return Devuelve el EntityManagerFactory.
   */
  public static EntityManagerFactory createEntityManagerFactory(){
    EntityManagerFactory emf;
    try {
      emf = Persistence.createEntityManagerFactory("JPAMagazines");
    } catch (Throwable ex) {
      System.err.println("Failed to create EntityManagerFactory object."+ ex);
      throw new ExceptionInInitializerError(ex);
    }
    return emf;
  }

  /**
   * Aqui mostramos el menu interactuable con el usuario, donde podremos toquetear la base de datos.
   *
   * @param args Los argumentos que le pasamos por consola
   */
  public static void main(String[] args) {
    boolean salirMenu = false;

    ConnectionFactory connectionFactory = ConnectionFactory.getInstance();
    Connection c = connectionFactory.connect();

//    SessionFactory sessionFactory = buildSessionFactory();
    EntityManagerFactory entityManagerFactory = createEntityManagerFactory();
    //sessionObj = buildSessionFactory().openSession();

    CampeonController campeonesController = new CampeonController(c, entityManagerFactory);
    HechizosController hechizosController = new HechizosController(c, entityManagerFactory);
    ObjetosController objetosController = new ObjetosController(c, entityManagerFactory);

    Menu menu = new Menu();
    int opcion;

    while(!salirMenu){
      opcion = menu.mainMenu();

      switch (opcion) {

        case 1:
          try{
            hechizosController.createTableHechizos();
            objetosController.createTableObjetos();
            campeonesController.createTableCampeones();
          }catch (Exception e){
            System.out.println("Hay una o varias tablas que quieres crear que ya existen en la base de datos");
          }

          break;

        case 2:
          try {
            List<Hechizos> hechizos = hechizosController.readHechizosFile("src/main/resources/hechizos.csv");
            for (Hechizos summons : hechizos) {
              try {
                hechizosController.addHechizos(summons);
              } catch (Exception e) {
              }
            }

            List<Objetos> objetos = objetosController.readObjetosFile("src/main/resources/objetos.csv");
            for (Objetos object : objetos) {
              try {
                objetosController.addObjetos(object);
              } catch (Exception e) {
              }
            }

            List<Campeones> campeones = campeonesController.readCampeonesFile("src/main/resources/campeones.csv");
            for (Campeones champs : campeones) {
              try {
                campeonesController.addCampeones(champs);
              } catch (Exception e) {
                e.printStackTrace();
              }
            }


          } catch (NumberFormatException | IOException e) {

            e.printStackTrace();
          }

          break;

        case 3:
          campeonesController.listAllCampeones();
          break;

        case 4:
          hechizosController.listAllHechizos();
          break;

        case 5:
          objetosController.listAllObjetos();
          break;

        case 6:
          campeonesController.orderCampeonesByName();
          break;

        case 7:
          hechizosController.orderHechizosByName();
          break;

        case 8:
          objetosController.orderObjetosByName();
          break;

        case 9:
          System.out.println("Que ID tiene el campeon que quieres cambiar? Del 1 al 162");
          int idCampeon = scanner.nextInt();
          scanner.nextLine();
          if(idCampeon >= 1 && idCampeon < 163){
            System.out.print("Escribe el nuevo nombre para el campeon que quieras modificar: ");
            String updateName = scanner.nextLine();

            campeonesController.updateCampeones(idCampeon,updateName);
          }
          else{
            System.out.println("La ID que estas intentando buscar no existe, tiene que ser del 1 al 162.");
          }
          break;

        case 10:
          System.out.println("Que nombre tiene el hechizo que quieres cambiar? Del 1 al 10");
            int idHechizo = scanner.nextInt();
            scanner.nextLine();
          if(idHechizo >= 1 && idHechizo < 11){
            System.out.print("Escribe el nuevo nombre para el hechizo que quieras modificar: ");
            String updateHechizos = scanner.next();

            hechizosController.updateHechizos(idHechizo,updateHechizos);
          }
          else{
            System.out.println("La ID que estas intentando buscar no existe, tiene que ser del 1 al 10.");
          }
          break;

        case 11:
          System.out.println("Que ID tiene el objeto que quieres cambiar? Del 1 al 5");
            int idObjeto = scanner.nextInt();
            scanner.nextLine();
          if(idObjeto >= 1 && idObjeto < 6){
            System.out.print("Escribe el nuevo porcentaje de victoria para el objeto que quieras modificar: ");
            String updatePorcentaje = scanner.nextLine();

            objetosController.updateObjetos(idObjeto,updatePorcentaje);
          }
          else{
            System.out.println("La ID que estas intentando buscar no existe, tiene que ser del 1 al 5.");
          }
          break;

        case 12:
          System.out.print("Inserta el nombre del campeon que quieres borrar: ");
          String deleteCampeonByName = scanner.nextLine();

          campeonesController.deleteCampeonesByName('"' + deleteCampeonByName + '"');
          break;

        case 13:
          System.out.print("Inserta el nombre del hechizo que quieres borrar: ");
          System.out.println("Para borrar un hechizo tienes que eliminar primero el campeón que tenga ese hechizo.");
          String deleteNameHechizo = scanner.nextLine();

          hechizosController.deleteHechizosByName('"' + deleteNameHechizo + '"');
          break;

        case 14:
          System.out.print("Inserta el ID del objeto que quieres borrar: ");
          int deleteNameObjeto = scanner.nextInt();

          objetosController.deleteObjetosByName(deleteNameObjeto);
          break;

        case 15:
          try{
            campeonesController.dropTableCampeones();
            hechizosController.dropTableHechizos();
            objetosController.dropTableObjetos();
          }catch (Exception e){
            System.out.println("Hay una o varias tablas que quieres borrar que no existen en la base de datos");
          }

          break;
          default:
          System.out.println("Deu");
          salirMenu = true;
      }
    }
  }
}
