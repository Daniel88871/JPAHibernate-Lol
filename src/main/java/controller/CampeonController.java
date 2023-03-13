package controller;

import model.Campeones;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.*;

/**
 * Esta clase es la que controla el campeón, que nos ayuda a interactuar con la tabla campeones.
 *
 * @author Daniel88871
 */
public class CampeonController {

  private Connection connection;
  private EntityManagerFactory entityManagerFactory;


  /**
   * El CampeonController con la opción connection
   * @param connection la conexión
   */
  public CampeonController(Connection connection) {
    this.connection = connection;
  }

  /**
   * Creamos una nueva instancia del controlador del campeón usando la conexión de la base de datos
   *
   * @param connection Le pasamos la conexión de la base de datos
   * @param entityManagerFactory Le pasamos tambien el Hibernate que hemos creado
   */
  public CampeonController(Connection connection, EntityManagerFactory entityManagerFactory) {
    this.connection = connection;
    this.entityManagerFactory = entityManagerFactory;
  }

  /**
   * Esta clase lo que hará será de leer el archivo CSV, y con este archivo rellenarnos toda la tabla de nuestra
   * base de datos con la información que saca del archivo.
   *
   * @param campeonesFile será la ruta del archivo campeones que queremos leer
   * @return Una lista de campeones, que luego se meterán con ayuda de otros métodos
   * @throws IOException Devuelve este error si hay algún problema al leer los archivos
   */
  public List<Campeones> readCampeonesFile(String campeonesFile) throws IOException {
    List<String> campeonesLines = Files.readAllLines(Paths.get(campeonesFile), StandardCharsets.UTF_8);

    List<Campeones> campeones = new ArrayList<Campeones>();

    for (String campeonesLine : campeonesLines) {
      String[] fields = campeonesLine.split(",");
      Campeones campeones1 = new Campeones(Integer.parseInt(fields[0]), (Integer.parseInt(fields[1])),
              (Integer.parseInt(fields[2])), fields[3], fields[4], fields[5], fields[6], fields[7], fields[8]);
      campeones.add(campeones1);
    }

    return campeones;
  }

  /**
   * Añade un campeón nuevo y lo mete en la base de datos.
   *
   * @param campeones El campeón que queremos añadir
   */
  public void addCampeones(Campeones campeones) {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    Campeones campeonesExists = (Campeones) em.find(Campeones.class, campeones.getCampeonesId());
    if (campeonesExists == null ){
      System.out.println("insertando campeones...");
      em.persist(campeones);
    }
    em.merge(campeones);
    em.getTransaction().commit();
    em.close();
  }

  /**
   * Lista todos los campeones que hay en la base de datos
   */
  public void listAllCampeones() {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    List<Campeones> result = em.createQuery("from Campeones", Campeones.class)
            .getResultList();

    for (Campeones campeones : result) {
      System.out.println(campeones.toString());
    }
    em.getTransaction().commit();
    em.close();
  }

  /**
   * Ordena los campeones por su nombre y los muestra por pantalla
   */
  public void orderCampeonesByName() {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    List<String> result = em.createQuery("SELECT c.nombre FROM Campeones c ORDER BY c.nombre", String.class)
            .getResultList();

    for (String name : result) {
      System.out.println(name);
    }
    em.getTransaction().commit();
    em.close();
  }

  /**
   * Actualiza el nombre del campeón que buscarás con su nombre
   *
   * @param campeonesId El ID del campeón que quieres actualizar
   * @param updateName El nombre nuevo que le quieres poner a tu campeón
   */
  public void updateCampeones(int campeonesId, String updateName) {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    Campeones campeones = (Campeones) em.find(Campeones.class, campeonesId);
    campeones.setNombre(updateName);
    em.merge(campeones);
    em.getTransaction().commit();

    em.getTransaction().begin();
    campeones = em.find(Campeones.class, campeonesId);
    System.out.println("Informacion del campeon despues de tu update:");
    System.out.println(campeones.toString());
    em.getTransaction().commit();

    em.close();
  }

  /**
   * Crea la tabla campeones con ayuda del schema SQL
   *
   */
  public void createTableCampeones() {
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("JPAMagazines");
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();
    entityManager.createNativeQuery(
            "CREATE TABLE campeones ( " +
                    " id_campeones serial NOT NULL, " +
                    " id_hechizos integer, " +
                    " id_objetos integer, " +
                    " nombre character varying(3000), " +
                    " popularidad character varying(3000), " +
                    " porcentaje_de_victoria character varying(3000), " +
                    " porcentaje_de_baneo character varying(3000), " +
                    " kda character varying(3000), " +
                    " pentas_por_partida character varying(3000), " +
                    " CONSTRAINT pk_campeones PRIMARY KEY (id_campeones), " +
                    " CONSTRAINT fk_hechizos FOREIGN KEY (id_hechizos) " +
                    " REFERENCES hechizos (id_hechizos) MATCH SIMPLE " +
                    " ON UPDATE NO ACTION ON DELETE NO ACTION, " +
                    " CONSTRAINT fk_objetos FOREIGN KEY (id_objetos) " +
                    " REFERENCES objetos (id_objetos) MATCH SIMPLE " +
                    " ON UPDATE NO ACTION ON DELETE CASCADE " +
                    ")"
    ).executeUpdate();
    entityManager.getTransaction().commit();
    entityManager.close();
    entityManagerFactory.close();
  }

  /**
   * Borra el campeón que le indiquemos en la consola
   *
   @param name El nombre del campeón que se borrará
   @throws javax.persistence.PersistenceException Devuelve este error si ha habido un problema mientras se borraba
   */
  public void deleteCampeonesByName(String name){
    String sql = "FROM Campeones WHERE nombre = :nombre";

    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    List<Campeones> result = em.createQuery(sql, Campeones.class)
            .setParameter("nombre", name)
            .getResultList();
    for (Campeones campeones : result) {
      em.remove(campeones);
    }
    try{
      em.getTransaction().commit();
    }catch (Exception e){
      e.printStackTrace();
    }
    em.close();
  }

  /**
   * Borra completamente la tabla campeones
   *
   @throws javax.persistence.PersistenceException Devuelve este error si hay un problema borrando la tabla
   */
  public void dropTableCampeones() {
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("JPAMagazines");
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();
    entityManager.createNativeQuery("DROP TABLE campeones").executeUpdate();
    entityManager.getTransaction().commit();
    entityManager.close();
    entityManagerFactory.close();
  }
}