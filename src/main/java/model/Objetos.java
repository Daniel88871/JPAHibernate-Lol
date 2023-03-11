package model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Esta clase representara todos los characters que vayamos metiendo en la tabla characters de nuestra
 * base de datos
 *
 @author tarikii
 */
@Entity
@Access(AccessType.FIELD)
@Table(name = "objetos")
public class Objetos implements Serializable {

  /**
   * El identificador del character
   *
   */
  @Id
  @Column(name = "id_objetos")
  String objetosId;

  /**
   * El identificador del weapon
   *
   */
  @Column(name = "popularidad")
  String popularidad;

  /**
   * El nombre del character
   *
   */
  @Column(name = "porcentaje_de_victoria")
  String porcentajedevictoria;



  /**
   * Construye un character nuevo con una serie de atributos
   *
   @param objetosId El identificador del character
   @param popularidad El identificador del weapon que posee el character
   */
  public Objetos(String objetosId, String popularidad, String porcentajedevictoria) {
    super();
    this.objetosId = objetosId;
    this.popularidad = popularidad;
    this.porcentajedevictoria = porcentajedevictoria;

  }

  /**
   * Devuelve la ID de un character
   *
   @return Su identificador
   */
  public String getObjetosId() {
    return objetosId;
  }

  /**
   * Editamos el identificador del character
   *
   * @param objetosId Le pasamos la nueva ID
   */
  public void setObjetosId(String objetosId) {
    this.objetosId = objetosId;
  }

  /**
   * Nos devuelve el identificador del weapon
   *
   * @return El ID del weapon
   */
  public String getPopularidad() {
    return popularidad;
  }

  /**
   * Edita el identificador del weapon de character
   *
   @param popularidad Su nuevo weapon
   */
  public void setPopularidad(String popularidad) {
    this.popularidad = popularidad;
  }

  /**
   * Nos da el nombre del character
   *
   * @return devuelve el nombre del character
   */
  public String getPorcentajedevictoria() {
    return porcentajedevictoria;
  }

  /**
   * Edita el nombre del character
   *
   @param porcentajedevictoria Su nuevo nombre
   */
  public void setPorcentajedevictoria(String porcentajedevictoria) {
    this.porcentajedevictoria = porcentajedevictoria;
  }

  /**
   * Devuelve una representacion del character en forma de String
   *
   * @return devuelve el string del character con sus atributos
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
