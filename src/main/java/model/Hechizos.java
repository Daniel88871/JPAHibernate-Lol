package model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase representará todos los hechizos que vayamos metiendo en la tabla hechizos de nuestra
 * base de datos
 *
 @author Daniel88871
 */
@Entity
@Access(AccessType.FIELD)
@Table(name = "hechizos")
public class Hechizos implements Serializable {

  /**
   * El identificador del hechizo
   *
   */
  @Id
  @Column(name = "id_hechizos")
  int hechizosId;

  /**
   * El identificador del nombre
   *
   */
  @Column(name = "nombre")
  String nombre;

  /**
   * El identificador de popularidad
   *
   */
  @Column(name = "popularidad")
  String popularidad;

  /**
   * El identificador del porcentaje de victorias
   *
   */
  @Column(name = "porcentaje_de_victoria")
  String porcentajedevictoria;


  /**
   * Construye un hechizo nuevo con una serie de atributos
   *
   @param hechizosId El identificador del hechizo
   @param nombre El identificador del nombre
   @param popularidad El identificador de popularidad
   @param porcentajedevictoria El identificador del porcentaje de victorias
   */
  public Hechizos(int hechizosId, String nombre, String popularidad, String porcentajedevictoria) {
    super();
    this.hechizosId = hechizosId;
    this.nombre = nombre;
    this.popularidad = popularidad;
    this.porcentajedevictoria = porcentajedevictoria;
  }

  /**
   * Un constructor vacío hechizos
   */
  public Hechizos (){
  }

  /**
   * Devuelve la ID de un hechizo
   *
   @return Su identificador
   */
  public int getHechizosId() {
    return hechizosId;
  }

  /**
   * Editamos el identificador del hechizo
   *
   * @param hechizosId Le pasamos la nueva ID
   */
  public void setHechizosId(int hechizosId) {
    this.hechizosId = hechizosId;
  }

  /**
   * Nos devuelve el identificador del nombre
   *
   * @return ID del nombre
   */
  public String getNombre() {
    return nombre;
  }

  /**
   * Edita el identificador del tipo del nombre del hechizo
   *
   @param nombre Su nuevo nombre
   */
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  /**
   * Nos devuelve el identificador de la popularidad
   *
   * @return El ID de popularidad
   */
  public String getPopularidad() {
    return popularidad;
  }

  /**
   * Edita el identificador de popularidad
   *
   @param popularidad Su nuevo porcentaje
   */
  public void setPopularidad(String popularidad) {
    this.popularidad = popularidad;
  }

  /**
   * Nos devuelve el porcentaje de victoria del hechizo
   *
   * @return devuelve el porcentaje de victoria
   */
  public String getPorcentajedevictoria() {
    return porcentajedevictoria;
  }

  /**
   * Edita el porcentaje de victoria del hechizo
   *
   @param porcentajedevictoria Su nuevo porcentaje
   */
  public void setPorcentajedevictoria(String porcentajedevictoria) {
    this.porcentajedevictoria = porcentajedevictoria;
  }


  /**
   * Devuelve una representación del hechizo en forma de String
   *
   * @return devuelve el string del hechizo con sus atributos
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
