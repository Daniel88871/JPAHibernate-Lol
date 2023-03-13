package controller;

import model.Objetos;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.util.*;

/**
 * Esta clase es el controlador del objeto, que nos ayuda a interactuar con la tabla objetos.
 *
 * @author Daniel88871
 */
public class ObjetosController {

  private Connection connection;
  private EntityManagerFactory entityManagerFactory;

  /**
   * Creamos una nueva instancia del controlador del objeto usando la conexion de la base de datos
   *
   * @param connection Le pasamos la conexion de la base de datos
   */

  public ObjetosController(Connection connection) {
    this.connection = connection;
  }

  /**
   * El controlador de objetos con la conexión a la base de datos
   * @param connection la conexión para los objetos
   * @param entityManagerFactory El param de entityManagerFactory
   */
  public ObjetosController(Connection connection, EntityManagerFactory entityManagerFactory) {
    this.connection = connection;
    this.entityManagerFactory = entityManagerFactory;
  }


  /**
   * Esta clase se encarga de leer el archivo CSV, y con este archivo rellenarnos toda la tabla de nuestra
   * base de datos con la información que saca del archivo.
   *
   * @param objetosFile la ruta del archivo objetos que queremos leer
   * @return Una lista de objetos, que luego se meterán con ayuda de otros métodos
   * @throws IOException Devuelve este error si hay algún problema al leer los archivos
   */
  public List<Objetos> readObjetosFile(String objetosFile) throws IOException {
    int id;
    String popularidad;
    String  porcentajedevictoria;
    List<Objetos> objetosList = new ArrayList<Objetos>();

    BufferedReader br = new BufferedReader(new FileReader(objetosFile));
    String linea = "";
    while ((linea = br.readLine()) != null) {
      StringTokenizer str = new StringTokenizer(linea, "\",");
      id = Integer.parseInt(str.nextToken());
      popularidad = str.nextToken();
      porcentajedevictoria = (str.nextToken());
      objetosList.add(new Objetos(id, popularidad, porcentajedevictoria));

    }
    br.close();

    return objetosList;
  }

  /**
   * Añade un objeto y lo mete en la base de datos
   *
   * @param objetos El objeto que queremos añadir
   */
  public void addObjetos(Objetos objetos) {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    Objetos objetosExist = (Objetos) em.find(Objetos.class, objetos.getObjetosId());
    if (objetosExist == null ){
      System.out.println("insertando objetos...");
      em.persist(objetos);
    }
    em.merge(objetos);
    em.getTransaction().commit();
    em.close();
  }

  /**
   * Lista todos los objetos de la base de datos
   */
  public void listAllObjetos() {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    List<Objetos> result = em.createQuery("from Objetos", Objetos.class)
            .getResultList();

    for (Objetos objetos : result) {
      System.out.println(objetos.toString());
    }
    em.getTransaction().commit();
    em.close();
  }

  /**
   * Ordena los objetos por su porcentaje de victoria y los muestra por pantalla
   */
  public void orderObjetosByName() {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    List<String> result = em.createQuery("SELECT c.porcentajedevictoria FROM Objetos c ORDER BY c.porcentajedevictoria", String.class)
            .getResultList();

    for (String nombre : result) {
      System.out.println(nombre);
    }
    em.getTransaction().commit();
    em.close();
  }

  /**
   * Actualiza el porcentaje de victoria del objeto que buscarás con su ID
   *
   * @param objetoPV El ID del objeto que quieres actualizar
   * @param updatePV El ID nuevo que le quieres poner a tu objeto
   */
  public void updateObjetos(int objetoPV, String updatePV) {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    Objetos objetos = (Objetos) em.find(Objetos.class, objetoPV);
    objetos.setPorcentajedevictoria(updatePV);
    em.merge(objetos);
    em.getTransaction().commit();

    em.getTransaction().begin();
    objetos = em.find(Objetos.class, objetoPV);
    System.out.println(objetos.toString());
    em.getTransaction().commit();
    em.close();
  }


  /**
   * Crea la tabla objetos con ayuda del schema SQL
   *
   */
  public void createTableObjetos() {
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("JPAMagazines");
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();
    entityManager.createNativeQuery(
            "CREATE TABLE objetos ( " +
                    " id_objetos serial NOT NULL, " +
                    " popularidad character varying(3000) NOT NULL , " +
                    " porcentaje_de_victoria character varying(3000) NOT NULL, " +
                    " CONSTRAINT pk_objetos PRIMARY KEY (id_objetos) " +
                    ")"
    ).executeUpdate();
    entityManager.getTransaction().commit();
    entityManager.close();
    entityManagerFactory.close();
  }

  /**
   * Borra el objeto que le pases por pantalla
   *
   @param objetosId El ID del objeto a borrar
   @throws javax.persistence.PersistenceException Devuelve este error si ha habido un problema borrando
   */
  public void deleteObjetosByName(int objetosId){
    String sql = "FROM Objetos WHERE objetosId = :objetosId";

    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    List<Objetos> result = em.createQuery(sql, Objetos.class)
            .setParameter("objetosId", objetosId)
            .getResultList();
    for (Objetos objetos : result) {
      em.remove(objetos);
    }
    em.getTransaction().commit();
    em.close();
  }

  /**
   * Borra completamente la tabla objetos
   *
   @throws javax.persistence.PersistenceException Devuelve este error si hay un problema borrando la tabla
   */
  public void dropTableObjetos() {
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("JPAMagazines");
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();
    entityManager.createNativeQuery("DROP TABLE objetos").executeUpdate();
    entityManager.getTransaction().commit();
    entityManager.close();
    entityManagerFactory.close();
  }
}