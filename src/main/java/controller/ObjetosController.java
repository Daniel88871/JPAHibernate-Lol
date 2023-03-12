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
 * Esta clase es el controlador del character, que nos ayuda a interactuar con la tabla characters.
 *
 * @author tarikii
 */
public class ObjetosController {

  private Connection connection;
  private EntityManagerFactory entityManagerFactory;

  /**
   * Creamos una nueva instancia del controlador del character usando la conexion de la base de datos
   *
   * @param connection Le pasamos la conexion de la base de datos
   */

  public ObjetosController(Connection connection) {
    this.connection = connection;
  }

  public ObjetosController(Connection connection, EntityManagerFactory entityManagerFactory) {
    this.connection = connection;
    this.entityManagerFactory = entityManagerFactory;
  }


  /**
   * Esta clase se encarga de leer el archivo CSV, y con este archivo rellenarnos toda la tabla de nuestra
   * base de datos con la informacion que saca del archivo.
   *
   * @param objetosFile la ruta del archivo characters que queremos leer
   * @return Una lista de characters, que luego se meteran con ayuda de otros metodos
   * @throws IOException Devuelve este error si hay algun problema al leer los archivos
   */
  public List<Objetos> readObjetosFile(String objetosFile) throws IOException {
    String id;
    String popularidad;
    String  porcentajedevictoria;
    List<Objetos> objetosList = new ArrayList<Objetos>();

    BufferedReader br = new BufferedReader(new FileReader(objetosFile));
    String linea = "";
    while ((linea = br.readLine()) != null) {
      StringTokenizer str = new StringTokenizer(linea, ",");
      id = (str.nextToken());
      popularidad = str.nextToken();
      porcentajedevictoria = (str.nextToken());
      objetosList.add(new Objetos(id, popularidad, porcentajedevictoria));

    }
    br.close();

    return objetosList;
  }

  /**
   * Añade un character (que procesamos con el csv) y lo mete en la base de datos
   *
   * @param objetos El character que queremos añadir
   */
  public void addObjetos(Objetos objetos) {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    Objetos objetosExist = (Objetos) em.find(Objetos.class, objetos.getObjetosId());
    if (objetosExist == null ){
      System.out.println("inserting objetos...");
      em.persist(objetos);
    }
    em.merge(objetos);
    em.getTransaction().commit();
    em.close();
  }

  /**
   * Lista todos los characters de la base de datos
   */
  public void listAllObjetos() {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    List<Objetos> result = em.createQuery("from objetos", Objetos.class)
            .getResultList();

    for (Objetos objetos : result) {
      System.out.println(objetos.toString());
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
  public void orderObjetosByName() {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    List<String> result = em.createQuery("SELECT c.name FROM objetos c ORDER BY c.name", String.class)
            .getResultList();

    for (String nombre : result) {
      System.out.println(nombre);
    }
    em.getTransaction().commit();
    em.close();
  }

  /**
   * Actualiza el nombre del character que buscaras con su ID
   *
   * @param objetoId El ID del character que quieres actualizar
   * @param updateId El nombre nuevo que le quieres poner a tu character
   */
  public void updateObjetos(int objetoId, String updateId) {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    Objetos objetos = (Objetos) em.find(Objetos.class, objetoId);
    objetos.setObjetosId(updateId);
    em.merge(objetos);
    em.getTransaction().commit();

    em.getTransaction().begin();
    objetos = em.find(Objetos.class, objetoId);
    System.out.println("Informacion del hechizo despues de tu Update:");
    System.out.println(objetos.toString());
    em.getTransaction().commit();

    em.close();
  }


  /**
   * Crea la tabla characters con ayuda del schema SQL
   *
   */
  public void createTableObjetos() {
    // crea un EntityManagerFactory utilizando la configuración definida en persistence.xml
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("JPAMagazines");

    // obtiene un EntityManager a partir del EntityManagerFactory
    EntityManager entityManager = entityManagerFactory.createEntityManager();

    // comienza una transacción
    entityManager.getTransaction().begin();

    // crea la tabla Characters
    entityManager.createNativeQuery(
            "CREATE TABLE objetos ( " +
                    "id_objetos serial NOT NULL," +
                    "popularidad character varying(3000) NOT NULL ," +
                    "porcentaje_de_victoria character varying(3000) NOT NULL," +
                    "CONSTRAINT pk_objetos PRIMARY KEY (id_objetos)" +
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
  public void deleteObjetosByName(String name){
    String sql = "FROM objetos WHERE name = :name";

    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    List<Objetos> result = em.createQuery(sql, Objetos.class)
            .setParameter("name", name)
            .getResultList();
    for (Objetos objetos : result) {
      em.remove(objetos);
    }
    em.getTransaction().commit();
    em.close();
  }

  /**
   * Dropea la tabla entera de characters
   *
   @throws javax.persistence.PersistenceException Devuelve este error si hay un problema dropeando la tabla
   */
  public void dropTableObjetos() {
    // crea un EntityManagerFactory utilizando la configuración definida en persistence.xml
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("JPAMagazines");

    // obtiene un EntityManager a partir del EntityManagerFactory
    EntityManager entityManager = entityManagerFactory.createEntityManager();

    // comienza una transacción
    entityManager.getTransaction().begin();

    // dropea la tabla characters
    entityManager.createNativeQuery("DROP TABLE objetos").executeUpdate();

    // finaliza la transacción
    entityManager.getTransaction().commit();

    // cierra el EntityManager y el EntityManagerFactory
    entityManager.close();
    entityManagerFactory.close();
  }
}