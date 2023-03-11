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


  public CampeonController(Connection connection) {
    this.connection = connection;
  }

  /**
   * Creamos una nueva instancia del controlador del campeon usando la conexion de la base de datos
   *
   * @param connection Le pasamos la conexion de la base de datos
   * @param entityManagerFactory Le pasamos tambien el Hibernate que hemos creado
   */
  public CampeonController(Connection connection, EntityManagerFactory entityManagerFactory) {
    this.connection = connection;
    this.entityManagerFactory = entityManagerFactory;
  }


  /**
   * Esta clase se encarga de leer el archivo CSV, y con este archivo rellenarnos toda la tabla de nuestra
   * base de datos con la informacion que saca del archivo.
   *
   * @param campeonesFile la ruta del archivo characters que queremos leer
   * @return Una lista de characters, que luego se meteran con ayuda de otros metodos
   * @throws IOException Devuelve este error si hay algun problema al leer los archivos
   */
  public List<Campeones> readCampeonesFile(String campeonesFile) throws IOException {
    // Lee el archivo de personajes
    List<String> campeonesLines = Files.readAllLines(Paths.get(campeonesFile), StandardCharsets.UTF_8);

    List<Campeones> campeones = new ArrayList<Campeones>();

    // Crea los objetos de personaje con las armas correspondientes
    for (String campeonesLine : campeonesLines) {
      // Separa la línea en campos
      String[] fields = campeonesLine.split(",");
      // Crea un objeto de personaje con los campos correspondientes
      Campeones campeones1 = new Campeones(fields[0], fields[1], fields[2], fields[3], fields[4], fields[5], fields[6]);
      // Agrega el objeto de personaje a la lista
      campeones.add(campeones1);
    }

    return campeones;
  }

  /**
   * Añade un character (que procesamos con el csv) y lo mete en la base de datos
   *
   * @param campeones El character que queremos añadir
   */
  public void addCampeones(Campeones campeones) {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    Campeones campeonesExists = (Campeones) em.find(Campeones.class, campeones.getCampeonesId());
    if (campeonesExists == null ){
      System.out.println("inserting campeones...");
      em.persist(campeones);
    }
    em.merge(campeones);
    em.getTransaction().commit();
    em.close();
  }

  /**
   * Lista todos los characters de la base de datos
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
   * Ordena los characters por su nombre y los lista
   */
  public void orderCampeonesByName() {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    List<String> result = em.createQuery("SELECT c.name FROM Campeones c ORDER BY c.name", String.class)
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
   * @param campeonesId El ID del character que quieres actualizar
   * @param updateName El nombre nuevo que le quieres poner a tu character
   */
  public void updateCampeones(int campeonesId, String updateName) {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    Campeones campeones = (Campeones) em.find(Campeones.class, campeonesId);
    campeones.setPorcentajedevictoria(updateName);
    em.merge(campeones);
    em.getTransaction().commit();

    em.getTransaction().begin();
    campeones = em.find(Campeones.class, campeonesId);
    System.out.println("Informacion del campeon despues de tu Update:");
    System.out.println(campeones.toString());
    em.getTransaction().commit();

    em.close();
  }

  /**
   * Crea la tabla characters con ayuda del schema SQL
   *
   */
  public void createTableCampeones() {
    // crea un EntityManagerFactory utilizando la configuración definida en persistence.xml
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("JPAMagazines");

    // obtiene un EntityManager a partir del EntityManagerFactory
    EntityManager entityManager = entityManagerFactory.createEntityManager();

    // comienza una transacción
    entityManager.getTransaction().begin();

    // crea la tabla Characters
    entityManager.createNativeQuery(
            "CREATE TABLE campeones ( " +
                    "id_campeones serial NOT NULL," +
                    "id_hechizos integer," +
                    "id_objetos integer NOT NULL," +
                    "nombre character varying(3000) NOT NULL," +
                    "popularidad character varying(3000) NOT NULL ," +
                    "porcentaje_de_victoria character varying(3000) NOT NULL," +
                    "porcentaje_de_baneo character varying(3000) NOT NULL," +
                    "kda character varying(3000) NOT NULL," +
                    "pentas_por_partida character varying(3000) NOT NULL," +
                    "CONSTRAINT pk_campeones PRIMARY KEY (id_campeones)," +
                    "CONSTRAINT fk_hechizos FOREIGN KEY (id_hechizos)" +
                    "REFERENCES hechizos (id_hechizos) MATCH SIMPLE" +
                    "ON UPDATE NO ACTION ON DELETE NO ACTION," +
                    "CONSTRAINT fk_objetos KEY (id_objetos)" +
                    "REFERENCES objetos (id_objetos) MATCH SIMPLE" +
                    "ON UPDATE NO ACTION ON DELETE NO ACTION);" +
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
  public void deleteCampeonesByName(String name){
    String sql = "FROM Campeones WHERE name = :name";

    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    List<Campeones> result = em.createQuery(sql, Campeones.class)
            .setParameter("name", name)
            .getResultList();
    for (Campeones campeones : result) {
      em.remove(campeones);
    }
    em.getTransaction().commit();
    em.close();
  }

  /**
   * Dropea la tabla entera de characters
   *
   @throws javax.persistence.PersistenceException Devuelve este error si hay un problema dropeando la tabla
   */
  public void dropTableCampeones() {
    // crea un EntityManagerFactory utilizando la configuración definida en persistence.xml
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("JPAMagazines");

    // obtiene un EntityManager a partir del EntityManagerFactory
    EntityManager entityManager = entityManagerFactory.createEntityManager();

    // comienza una transacción
    entityManager.getTransaction().begin();

    // dropea la tabla characters
    entityManager.createNativeQuery("DROP TABLE campeones").executeUpdate();

    // finaliza la transacción
    entityManager.getTransaction().commit();

    // cierra el EntityManager y el EntityManagerFactory
    entityManager.close();
    entityManagerFactory.close();
  }
}