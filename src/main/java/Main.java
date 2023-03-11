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
 * Esta clas nos da lo necesario para que el usuario pueda interactuar con nuestra base de datos
 * a partir de un menu que nosotros proveemos, el usuario escoge una opcion con un int, y nuestro programa
 * hara lo que la opcion del menu nos ha indicado
 *
 * @author tarikii
 */
public class Main {
  private static final Scanner scanner = new Scanner(System.in);

  static SessionFactory sessionFactoryObj;

  /**
   * Creamos un constructor vacio de Main (porque simplemente nos da un error en JavaDoc si no lo hacemos)
   *
   */
  public Main(){}
/*
  private static SessionFactory buildSessionFactory() {
    // Creating Configuration Instance & Passing Hibernate Configuration File
    Configuration configObj = new Configuration();
    configObj.configure("hibernate.cfg.xml");
    // Since Hibernate Version 4.x, ServiceRegistry Is Being Used
    ServiceRegistry serviceRegistryObj = new StandardServiceRegistryBuilder().applySettings(configObj.getProperties()).build();
    // Creating Hibernate SessionFactory Instance
    sessionFactoryObj = configObj.buildSessionFactory(serviceRegistryObj);
    return sessionFactoryObj;
  } */

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
   * Aqui basicamente muestra el menu interactuable con el usuario, donde podremos toquetear la base de datos.
   *
   * @param args Los argumentos que le pasamos por consola (no se usa)
   */
  public static void main(String[] args) {
    boolean salirMenu = false;

    ConnectionFactory connectionFactory = ConnectionFactory.getInstance();
    Connection c = connectionFactory.connect();

//    SessionFactory sessionFactory = buildSessionFactory();
    EntityManagerFactory entityManagerFactory = createEntityManagerFactory();
    //sessionObj = buildSessionFactory().openSession();

    //Creamos los 3 controladores que hemos creado para poder usar las tablas de la base de datos
    CampeonController campeonesController = new CampeonController(c, entityManagerFactory);
    HechizosController hechizosController = new HechizosController(c, entityManagerFactory);
    ObjetosController objetosController = new ObjetosController(c, entityManagerFactory);

    Menu menu = new Menu();
    int opcion;


    //Nos saldra el menu infinitas veces, hasta que se presione la tecla 0 que nos cierra el programa.
    while(!salirMenu){
      opcion = menu.mainMenu();

      //Aqui se muestran todas las opciones del menu, cada opcion se encarga de lo que indicamos al usuario
      //por escrito en el menu
      switch (opcion) {

        case 1:

          try{
            campeonesController.createTableCampeones();
            hechizosController.createTableHechizos();
            objetosController.createTableObjetos();
          }catch (Exception e){
            System.out.println("Hay una o varias tablas que quieres crear que ya existen en la base de datos");
          }

          break;

        case 2:

          System.out.println("2!!");
          try {
            List<Objetos> objetos = objetosController.readObjetosFile("src/main/resources/objetos.csv");
            for (Objetos w : objetos) {
              try {
                objetosController.addObjetos(w);
              } catch (Exception e) {
              }
            }


            List<Campeones> campeones = campeonesController.readCampeonesFile("src/main/resources/campeones.csv");
            for (Campeones ct : campeones) {
              try {
                campeonesController.addCampeones(ct);
              } catch (Exception e) {
              }
            }

            List<Hechizos> hechizos = hechizosController.readHechizosFile("src/main/resources/hechizos.csv");
            for (Hechizos ch : hechizos) {
              try {
                hechizosController.addHechizos(ch);
              } catch (Exception e) {
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
          try{
            campeonesController.listAllCampeones();
          }catch (Exception e){
            System.out.println("No se ha encontrado ningun campeon con el nombre que has proporcionado, intentalo de nuevo");
          }
          break;

        case 8:
          System.out.println("Que ID tiene el campeon que quieres cambiar? Del 1 al 30");
          int idCampeon = scanner.nextInt();
          if(idCampeon >= 1 && idCampeon < 31){
            System.out.print("Escribe el nombre nuevo para el campeon que quieres modificar: ");
            String updateName = scanner.nextLine();

            campeonesController.updateCampeones(idCampeon,updateName);
          }
          else{
            System.out.println("La ID que estas intentando buscar no existe, recuerda que tiene que ser del 1 al 30!");
          }
          break;

        case 9:
          System.out.println("Que ID tiene el hechizo que quieres cambiar? Del 1 al 30");
          int idHechizo = scanner.nextInt();
          if(idHechizo >= 1 && idHechizo < 31){
            System.out.print("Escribe el daño nuevo para el weapon que quieres modificar: ");
            String updateHechizos = scanner.next();

            hechizosController.updateHechizos(idHechizo,updateHechizos);
          }
          else{
            System.out.println("La ID que estas intentando buscar no existe, recuerda que tiene que ser del 1 al 30!");
          }
          break;

        case 10:
          System.out.println("Para crear un nuevo campeon, rellena este formulario");
          System.out.println();

          System.out.println("Escribe el nombre del nuevo campeón: ");
          String newCampeon = scanner.next();
          break;

        case 11:
          hechizosController.createTableHechizos();
          break;

        case 12:
          System.out.print("Inserta el nombre del character que quieres borrar: ");
          String deleteCampeonByName = scanner.nextLine();

          campeonesController.deleteCampeonesByName(deleteCampeonByName);
          break;


        case 13:
          System.out.print("Inserta el nombre del weapon que quieres borrar: ");
          String deleteNameHechizo = scanner.nextLine();

          hechizosController.deleteHechizosByName(deleteNameHechizo);
          break;

        case 14:

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
          //System.exit(1);
      }
    }
  }
}
