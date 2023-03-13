package model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Esta clase representara todos los objetos que vayamos metiendo en la tabla objetos de nuestra
 * base de datos
 *
 @author Daniel88871
 */
@Entity
@Access(AccessType.FIELD)
@Table(name = "objetos")
public class Objetos implements Serializable {

  /**
   * El identificador del objeto
   *
   */
  @Id
  @Column(name = "id_objetos")
  int objetosId;

  /**
   * El identificador de popularidad
   *
   */
  @Column(name = "popularidad")
  String popularidad;

  /**
   * El identificador del porcentaje de victoria
   *
   */
  @Column(name = "porcentaje_de_victoria")
  String porcentajedevictoria;



  /**
   * Construye un objeto nuevo con una serie de atributos
   *
   @param objetosId El identificador del objeto
   @param popularidad El identificador de popularidad que posee el objeto
   @param porcentajedevictoria El identificador de porcentaje de victoria de los objetos
   */
  public Objetos(int objetosId, String popularidad, String porcentajedevictoria) {
    super();
    this.objetosId = objetosId;
    this.popularidad = popularidad;
    this.porcentajedevictoria = porcentajedevictoria;
  }

  /**
   * Un constructor vacío objetos
   */
  public Objetos() {
  }

  /**
   * Devuelve la ID de un objeto
   *
   @return Su identificador
   */
  public int getObjetosId() {
    return objetosId;
  }

  /**
   * Editamos el identificador del objeto
   *
   * @param objetosId Le pasamos la nueva ID
   */
  public void setObjetosId(int objetosId) {
    this.objetosId = objetosId;
  }

  /**
   * Nos devuelve el identificador de popularidad
   *
   * @return El ID de popularidad
   */
  public String getPopularidad() {
    return popularidad;
  }

  /**
   * Edita el identificador de popularidad del objeto
   *
   @param popularidad Su nuevo porcentaje
   */
  public void setPopularidad(String popularidad) {
    this.popularidad = popularidad;
  }

  /**
   * Nos da el porcentaje de victoria
   *
   * @return devuelve el porcentaje de victoria
   */
  public String getPorcentajedevictoria() {
    return porcentajedevictoria;
  }

  /**
   * Edita el porcentaje de victoria
   *
   @param porcentajedevictoria Su nuevo porcentaje
   */
  public void setPorcentajedevictoria(String porcentajedevictoria) {
    this.porcentajedevictoria = porcentajedevictoria;
  }

  /**
   * Devuelve una representación del objeto en forma de String
   *
   * @return devuelve el string del objeto con sus atributos
   */
  @Override
  public String toString() {
    return "Objetos{" +
            "objetosId=" + objetosId +
            ", popularidad=" + popularidad +
            ", porcentaje_de_victoria='" + porcentajedevictoria + '\'' +
            '}';
  }
}
