package model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase representara todos los characters que vayamos metiendo en la tabla characters de nuestra
 * base de datos
 *
 @author tarikii
 */
@Entity
@Access(AccessType.FIELD)
@Table(name = "campeones")
public class Hechizos implements Serializable {

  /**
   * El identificador del character
   *
   */
  @Id
  @Column(name = "id_hechizos")
  String hechizosId;

  /**
   * El identificador del tipo de character
   *
   */
  @Column(name = "nombre")
  String nombre;

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
   @param campeonesId El identificador del character
   @param nombre El identificador del tipo del character, Plant o Zombie
   @param popularidad El identificador del weapon que posee el character
   @param porcentaje_de_victoria El nombre del character
   */
  public Hechizos(String hechizosId, String nombre, String popularidad, String porcentajedevictoria) {
    super();
    this.hechizosId = hechizosId;
    this.nombre = nombre;
    this.popularidad = popularidad;
    this.porcentajedevictoria = porcentajedevictoria;
  }

  /**
   * Devuelve la ID de un character
   *
   @return Su identificador
   */
  public String getHechizosId() {
    return hechizosId;
  }

  /**
   * Editamos el identificador del character
   *
   * @param campeonesId Le pasamos la nueva ID
   */
  public void setHechizosId(String hechizosId) {
    this.hechizosId = hechizosId;
  }

  /**
   * Nos devuelve el identificador del tipo
   *
   * @return ID del tipo
   */
  public String getNombre() {
    return nombre;
  }

  /**
   * Edita el identificador del tipo de character
   *
   @param nombre Su nuevo tipo
   */
  public void setNombre(String nombre) {
    this.nombre = nombre;
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
    return "Hechizos{" +
            "hechizosId=" + hechizosId +
            ", nombre=" + nombre +
            ", popularidad=" + popularidad +
            ", porcentaje_de_victoria='" + porcentajedevictoria + '\'' +
            '}';
  }
}
