package model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase representará todos los campeones que vayamos metiendo en la tabla campeones de nuestra
 * base de datos
 *
 @author Daniel88871
 */
@Entity
@Access(AccessType.FIELD)
@Table(name = "campeones")
public class Campeones implements Serializable {

  /**
   * El identificador del campeón
   *
   */
  @Id
  @Column(name = "id_campeones")
  int campeonesId;

  /**
   * El identificador de los hechizos
   *
   */
  @Column(name = "id_hechizos")
  int hechizosId;

  /**
   * El identificador de los objetos
   *
   */
  @Column(name = "id_objetos")
  int objetosId;

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
   * El identificador del porcentaje de victoria
   *
   */
  @Column(name = "porcentaje_de_victoria")
  String porcentajedevictoria;

  /**
   * El identificador del porcentaje de baneo
   *
   */
  @Column(name = "porcentaje_de_baneo")
  String porcentajedebaneo;

  /**
   * El identificador del KDA
   *
   */
  @Column(name = "kda")
  String kda;

  /**
   * El identificador de los pentas por partida
   *
   */
  @Column(name = "pentas_por_partida")
  String pentasporpartida;


  /**
   * Construye un campeón nuevo con una serie de atributos
   *
   @param campeonesId El identificador del campeón
   @param nombre El identificador de nombre del campeón
   @param popularidad El identificador de popularidad del campeón
   @param porcentaje_de_victoria El identificador de porcentaje de victoria del campeón
   @param porcentaje_de_baneo El identificador de porcentaje de baneo del campeón
   @param kda El identificador del KDA del campeón
   @param pentas_por_partida El identificador de las pentas por partida
   */
  public Campeones(int campeonesId, int hechizosId, int objetosId, String nombre, String popularidad,
                   String porcentaje_de_victoria, String porcentaje_de_baneo, String kda, String pentas_por_partida) {
    super();
    this.hechizosId = hechizosId;
    this.objetosId = objetosId;
    this.campeonesId = campeonesId;
    this.nombre = nombre;
    this.popularidad = popularidad;
    this.porcentajedevictoria = porcentaje_de_victoria;
    this.porcentajedebaneo = porcentaje_de_baneo;
    this.kda = kda;
    this.pentasporpartida = pentas_por_partida;
  }

  public Campeones (){
  }

  /**
   * Devuelve la ID de un campeón
   *
   @return Su identificador
   */
  public int getCampeonesId() {
    return campeonesId;
  }

  /**
   * Editamos el identificador del campeón
   *
   * @param campeonesId Le pasamos la nueva ID
   */
  public void setCampeonesId(int campeonesId) {
    this.campeonesId = campeonesId;
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
   * Edita el identificador del nombre del campeón
   *
   @param nombre Su nuevo nombre
   */
  public void setNombre(String nombre) {
    this.nombre = nombre;
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
   * Edita el identificador de popularidad del campeón
   *
   @param popularidad El identificador de popularidad
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
   * Edita el porcentaje de victoria del campeón
   *
   @param porcentajedevictoria El porcentaje de victorias del campeón
   */
  public void setPorcentajedevictoria(String porcentajedevictoria) {
    this.porcentajedevictoria = porcentajedevictoria;
  }

  /**
   * El identificador del porcentaje de baneo
   *
   * @return devuelve el porcentaje de baneo del campeón
   */
  public String getPorcentajedebaneo() {
    return porcentajedebaneo;
  }

  /**
   * El set del porcentaje de baneo
   *
   @param porcentajedebaneo Su nuevo porcentaje
   */
  public void setPorcentajedebaneo(String porcentajedebaneo) {
    this.porcentajedebaneo = porcentajedebaneo;
  }

  /**
   * Nos da el KDA del campeón
   *
   * @return Nos devuelve el KDA
   */
  public String getKda() {
    return kda;
  }

  /**
   * El set del KDA
   *
   @param kda El KDA del campeón
   */
  public void setKda(String kda) {
    this.kda = kda;
  }

  /**
   * Nos devuelve las pentas por partida
   *
   * @return Nos returnea los pentas por partida
   */
  public String getPentasporpartida() {
    return pentasporpartida;
  }

  /**
   * El set de los pentas por partida
   *
   @param pentasporpartida Los pentas por partida
   */
  public void setPentasporpartida(String pentasporpartida) {
    this.pentasporpartida = pentasporpartida;
  }

  /**
   * Devuelve una representación del campeón en forma de String
   *
   * @return devuelve el string del campeón con sus atributos
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
