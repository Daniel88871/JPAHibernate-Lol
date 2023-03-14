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
 * Esta clase es el controlador de los hechizos, que nos ayuda a interactuar con la tabla hechizos.
 *
 * @author Daniel88871
 */
public class HechizosController {

  private Connection connection;
  private EntityManagerFactory entityManagerFactory;

  /**
   * La conexión para los hechizos
   * @param connection la conexión para los hechizos
   */
  public HechizosController(Connection connection) {
    this.connection = connection;
  }

  /**
   * Creamos una nueva instancia del controlador del hechizo usando la conexión de la base de datos
   *
   * @param connection Le pasamos la conexión de la base de datos
   * @param entityManagerFactory Le pasamos también el Hibernate que hemos creado
   */
  public HechizosController(Connection connection, EntityManagerFactory entityManagerFactory) {
    this.connection = connection;
    this.entityManagerFactory = entityManagerFactory;
  }


  /**
   * Esta clase se encarga de leer el archivo CSV, y con este archivo rellenarnos toda la tabla de nuestra
   * base de datos con la información que saca del archivo.
   *
   * @param hechizosFile la ruta del archivo hechizos que queremos leer
   * @return Una lista de hechizos, que luego se meterán con ayuda de otros métodos
   * @throws IOException Devuelve este error si hay algún problema al leer los archivos
   */
  public List<Hechizos> readHechizosFile(String hechizosFile) throws IOException {
    int id;
    String nombre;
    String popularidad;
    String porcentajedevictoria;
    List<Hechizos> hechizosList = new ArrayList<Hechizos>();

    BufferedReader br = new BufferedReader(new FileReader(hechizosFile));
    String linea = "";
    while ((linea = br.readLine()) != null) {
      StringTokenizer str = new StringTokenizer(linea, ",");
      id = Integer.parseInt(str.nextToken());
      nombre = (str.nextToken());
      popularidad = (str.nextToken());
      porcentajedevictoria = (str.nextToken());
      hechizosList.add(new Hechizos(id, nombre, popularidad, porcentajedevictoria));

    }
    br.close();

    return hechizosList;
  }

  /**
   * Añade un hechizo y lo mete en la base de datos
   *
   * @param hechizos El hechizo que queremos añadir
   */
  public void addHechizos(Hechizos hechizos) {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    Hechizos hechizosExists = (Hechizos) em.find(Hechizos.class, hechizos.getHechizosId());
    if (hechizosExists == null ){
      System.out.println("insertando hechizos...");
      em.persist(hechizos);
    }
    em.merge(hechizos);
    em.getTransaction().commit();
    em.close();
  }

  /**
   * Lista todos los hechizos de la base de datos
   */
  public void listAllHechizos() {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    List<Hechizos> result = em.createQuery("from Hechizos", Hechizos.class)
            .getResultList();

    for (Hechizos hechizos : result) {
      System.out.println(hechizos.toString());
    }
    em.getTransaction().commit();
    em.close();
  }

  /**
   * Ordena los hechizos por su nombre y los muestra por pantalla
   */
  public void orderHechizosByName() {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    List<String> result = em.createQuery("SELECT c.nombre FROM Hechizos c ORDER BY c.nombre", String.class)
            .getResultList();

    for (String name : result) {
      System.out.println(name);
    }
    em.getTransaction().commit();
    em.close();
  }

  /**
   * Actualiza el nombre del hechizo que buscaras con su ID
   *
   * @param hechizoId El ID del hechizo que quieres actualizar
   * @param hechizos1 El segundo identificador de hechizos
   */
  public void updateHechizos(int hechizoId, String hechizos1) {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    Hechizos hechizos = (Hechizos) em.find(Hechizos.class, hechizoId);
    hechizos.setNombre(hechizos1);
    em.merge(hechizos);
    em.getTransaction().commit();

    em.getTransaction().begin();
    hechizos = em.find(Hechizos.class, hechizoId);
    System.out.println(hechizos.toString());
    em.getTransaction().commit();
    em.close();
  }


  /**
   * Crea la tabla hechizos con ayuda del schema SQL
   *
   */
  public void createTableHechizos() {
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("JPAMagazines");
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();
    entityManager.createNativeQuery(
            "CREATE TABLE hechizos ( " +
                    " id_hechizos serial NOT NULL, " +
                    " nombre character varying(3000) NOT NULL, " +
                    " popularidad character varying(3000) NOT NULL , " +
                    " porcentaje_de_victoria character varying(3000) NOT NULL, " +
                    " CONSTRAINT pk_hechizos PRIMARY KEY (id_hechizos) " +
                    ")"
    ).executeUpdate();
    entityManager.getTransaction().commit();
    entityManager.close();
    entityManagerFactory.close();
  }

  /**
   * Borra el hechizo ya existente que le pasemos por pantalla
   *
   @param name El nombre del hechizo a borrar
   @throws javax.persistence.PersistenceException Devuelve este error si ha habido un problema borrando
   */
  public void deleteHechizosByName(String name){
    String sql = "FROM Hechizos WHERE nombre = :nombre";

    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    List<Hechizos> result = em.createQuery(sql, Hechizos.class)
            .setParameter("nombre", name)
            .getResultList();
    for (Hechizos hechizos : result) {
      em.remove(hechizos);
    }
    try{
      em.getTransaction().commit();
    }catch (Exception e){
      e.printStackTrace();
    }
    em.close();
  }
  //DELETE FROM campeones WHERE id_hechizos = 1;


  /**
   * Borra completamente la tabla hechizos
   *
   @throws javax.persistence.PersistenceException Devuelve este error si hay un problema borrando la tabla
   */
  public void dropTableHechizos() {
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("JPAMagazines");
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();
    entityManager.createNativeQuery("DROP TABLE hechizos").executeUpdate();
    entityManager.getTransaction().commit();
    entityManager.close();
    entityManagerFactory.close();
  }
}
