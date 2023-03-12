package controller;

import model.Campeones;
import model.Hechizos;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.*;

/**
 * Esta clase es el controlador del character, que nos ayuda a interactuar con la tabla characters.
 *
 * @author tarikii
 */
public class HechizosController {

  private Connection connection;
  private EntityManagerFactory entityManagerFactory;


  public HechizosController(Connection connection) {
    this.connection = connection;
  }

  /**
   * Creamos una nueva instancia del controlador del character usando la conexion de la base de datos
   *
   * @param connection Le pasamos la conexion de la base de datos
   * @param entityManagerFactory Le pasamos tambien el Hibernate que hemos creado
   */
  public HechizosController(Connection connection, EntityManagerFactory entityManagerFactory) {
    this.connection = connection;
    this.entityManagerFactory = entityManagerFactory;
  }


  /**
   * Esta clase se encarga de leer el archivo CSV, y con este archivo rellenarnos toda la tabla de nuestra
   * base de datos con la informacion que saca del archivo.
   *
   * @param hechizosFile la ruta del archivo characters que queremos leer
   * @return Una lista de characters, que luego se meteran con ayuda de otros metodos
   * @throws IOException Devuelve este error si hay algun problema al leer los archivos
   */
  public List<Hechizos> readHechizosFile(String hechizosFile) throws IOException {
    String id;
    String nombre;
    String popularidad;
    String porcentajedevictoria;
    List<Hechizos> hechizosList = new ArrayList<Hechizos>();

    BufferedReader br = new BufferedReader(new FileReader(hechizosFile));
    String linea = "";
    while ((linea = br.readLine()) != null) {
      StringTokenizer str = new StringTokenizer(linea, ",");
      id = (str.nextToken());
      nombre = str.nextToken();
      popularidad = (str.nextToken());
      porcentajedevictoria = (str.nextToken());
      hechizosList.add(new Hechizos(id,nombre,popularidad, porcentajedevictoria));

    }
    br.close();

    return hechizosList;
  }

  /**
   * Añade un character (que procesamos con el csv) y lo mete en la base de datos
   *
   * @param hechizos El character que queremos añadir
   */
  public void addHechizos(Hechizos hechizos) {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    Hechizos hechizosExists = (Hechizos) em.find(Hechizos.class, hechizos.getHechizosId());
    if (hechizosExists == null ){
      System.out.println("inserting hechizos...");
      em.persist(hechizos);
    }
    em.merge(hechizos);
    em.getTransaction().commit();
    em.close();
  }

  /**
   * Lista todos los characters de la base de datos
   */
  public void listAllHechizos() {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    List<Hechizos> result = em.createQuery("from hechizos", Hechizos.class)
            .getResultList();

    for (Hechizos hechizos : result) {
      System.out.println(hechizos.toString());
    }
    em.getTransaction().commit();
    em.close();
  }

  /**
   * Lista todos los characters que sean Plant
   */

  /**
   * Ordena los characters por su nombre y los lista
   */
  public void orderHechizosByName() {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    List<String> result = em.createQuery("SELECT c.name FROM hechizos c ORDER BY c.name", String.class)
            .getResultList();

    for (String name : result) {
      System.out.println(name);
    }
    em.getTransaction().commit();
    em.close();
  }

  /**
   * Actualiza el nombre del character que buscaras con su ID
   *
   * @param hechizoId El ID del character que quieres actualizar
   */
  public void updateHechizos(int hechizoId, String hechizos1) {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    Hechizos hechizos = (Hechizos) em.find(Hechizos.class, hechizoId);
    hechizos.setNombre(hechizos1);;
    em.merge(hechizos);
    em.getTransaction().commit();

    em.getTransaction().begin();
    hechizos = em.find(Hechizos.class, hechizoId);
    System.out.println("Informacion del hechizo despues de tu Update:");
    System.out.println(hechizos.toString());
    em.getTransaction().commit();

    em.close();
  }


  /**
   * Crea la tabla characters con ayuda del schema SQL
   *
   */
  public void createTableHechizos() {
    // crea un EntityManagerFactory utilizando la configuración definida en persistence.xml
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("JPAMagazines");

    // obtiene un EntityManager a partir del EntityManagerFactory
    EntityManager entityManager = entityManagerFactory.createEntityManager();

    // comienza una transacción
    entityManager.getTransaction().begin();

    // crea la tabla Characters
    entityManager.createNativeQuery(
            "CREATE TABLE hechizos ( " +
                    "id_hechizos serial NOT NULL," +
                    "nombre character varying(3000) NOT NULL," +
                    "popularidad character varying(3000) NOT NULL ," +
                    "porcentaje_de_victoria character varying(3000) NOT NULL," +
                    "CONSTRAINT pk_hechizos PRIMARY KEY (id_hechizos)" +
                    ")"
    ).executeUpdate();

    // finaliza la transacción
    entityManager.getTransaction().commit();

    // cierra el EntityManager y el EntityManagerFactory
    entityManager.close();
    entityManagerFactory.close();
  }

  /**
   * Borra el character o los characters que poseen el mismo nombre que pone nuestro usuario por pantalla
   *
   @param name El nombre del character a borrar
   @throws javax.persistence.PersistenceException Devuelve este error si ha habido un problema borrando
   */
  public void deleteHechizosByName(String name){
    String sql = "FROM hechizos WHERE name = :name";

    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    List<Hechizos> result = em.createQuery(sql, Hechizos.class)
            .setParameter("name", name)
            .getResultList();
    for (Hechizos hechizos : result) {
      em.remove(hechizos);
    }
    em.getTransaction().commit();
    em.close();
  }

  /**
   * Dropea la tabla entera de characters
   *
   @throws javax.persistence.PersistenceException Devuelve este error si hay un problema dropeando la tabla
   */
  public void dropTableHechizos() {
    // crea un EntityManagerFactory utilizando la configuración definida en persistence.xml
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("JPAMagazines");

    // obtiene un EntityManager a partir del EntityManagerFactory
    EntityManager entityManager = entityManagerFactory.createEntityManager();

    // comienza una transacción
    entityManager.getTransaction().begin();

    // dropea la tabla characters
    entityManager.createNativeQuery("DROP TABLE hechizos").executeUpdate();

    // finaliza la transacción
    entityManager.getTransaction().commit();

    // cierra el EntityManager y el EntityManagerFactory
    entityManager.close();
    entityManagerFactory.close();
  }
}
