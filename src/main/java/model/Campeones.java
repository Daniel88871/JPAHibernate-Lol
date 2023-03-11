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
public class Campeones implements Serializable {

  /**
   * El identificador del character
   *
   */
  @Id
  @Column(name = "id_campeones")
  String campeonesId;

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
   * La imagen del character
   *
   */
  @Column(name = "porcentaje_de_baneo")
  String porcentajedebaneo;

  /**
   * La vida del character
   *
   */
  @Column(name = "kda")
  String kda;

  /**
   * La variant del character
   *
   */
  @Column(name = "pentas_por_partida")
  String pentasporpartida;


  /**
   * Construye un character nuevo con una serie de atributos
   *
   @param campeonesId El identificador del character
   @param nombre El identificador del tipo del character, Plant o Zombie
   @param popularidad El identificador del weapon que posee el character
   @param porcentaje_de_victoria El nombre del character
   @param porcentaje_de_baneo El archivo que contiene una imagen del character
   @param kda La vida del character
   @param pentas_por_partida La variante del character
   */
  public Campeones(String campeonesId, String nombre, String popularidad, String porcentaje_de_victoria, String porcentaje_de_baneo, String kda, String pentas_por_partida) {
    super();
    this.campeonesId = campeonesId;
    this.nombre = nombre;
    this.popularidad = popularidad;
    this.porcentajedevictoria = porcentaje_de_victoria;
    this.porcentajedebaneo = porcentaje_de_baneo;
    this.kda = kda;
    this.pentasporpartida = pentas_por_partida;
  }


  /**
   * Devuelve la ID de un character
   *
   @return Su identificador
   */
  public String getCampeonesId() {
    return campeonesId;
  }

  /**
   * Editamos el identificador del character
   *
   * @param campeonesId Le pasamos la nueva ID
   */
  public void setCampeonesId(String campeonesId) {
    this.campeonesId = campeonesId;
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
   * Nos devuelve la imagen del character
   *
   * @return la imagen
   */
  public String getPorcentajedebaneo() {
    return porcentajedebaneo;
  }

  /**
   * Edita la imagen del character
   *
   @param porcentajedebaneo Su nueva imagen
   */
  public void setPorcentajedebaneo(String porcentajedebaneo) {
    this.porcentajedebaneo = porcentajedebaneo;
  }

  /**
   * Nos devuelve los puntos de vida del character
   *
   * @return la vida
   */
  public String getKda() {
    return kda;
  }

  /**
   * Edita los puntos de vida del character
   *
   @param kda Su nueva vida
   */
  public void setKda(String kda) {
    this.kda = kda;
  }

  /**
   * Nos devuelve el variant del campeón
   *
   * @return la variant
   */
  public String getPentasporpartida() {
    return pentasporpartida;
  }

  /**
   * Edita el variant del character
   *
   @param pentasporpartida Su nueva variant
   */
  public void setPentasporpartida(String pentasporpartida) {
    this.pentasporpartida = pentasporpartida;
  }

  /**
   * Devuelve una representacion del character en forma de String
   *
   * @return devuelve el string del character con sus atributos
   */

  @Override
  public String toString() {
    return "Campeones{" +
            "campeonesId=" + campeonesId +
            ", nombre=" + nombre +
            ", popularidad=" + popularidad +
            ", porcentaje_de_victoria='" + porcentajedevictoria + '\'' +
            ", porcentaje_de_baneo='" + porcentajedebaneo + '\'' +
            ", kda='" + kda + '\'' +
            ", pentas_por_partida='" + pentasporpartida + '\'' +
            '}';
  }
}
