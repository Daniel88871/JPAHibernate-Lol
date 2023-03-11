CREATE TABLE campeones (
id_campeones serial NOT NULL,
id_hechizos integer,
id_objetos integer NOT NULL,
nombre character varying(3000) NOT NULL,
popularidad character varying(3000) NOT NULL ,
porcentaje_de_victoria character varying(3000) NOT NULL,
porcentaje_de_baneo character varying(3000) NOT NULL,
kda character varying(3000) NOT NULL,
pentas_por_partida character varying(3000) NOT NULL,
CONSTRAINT pk_campeones PRIMARY KEY (id_campeones),
CONSTRAINT fk_hechizos FOREIGN KEY (id_hechizos)
REFERENCES hechizos (id_hechizos) MATCH SIMPLE
ON UPDATE NO ACTION ON DELETE NO ACTION,
CONSTRAINT fk_objetos KEY (id_objetos)
REFERENCES objetos (id_objetos) MATCH SIMPLE
ON UPDATE NO ACTION ON DELETE NO ACTION);


CREATE TABLE hechizos (
id_hechizos integer,
nombre character varying(3000) NOT NULL,
popularidad character varying(3000) NOT NULL ,
porcentaje_de_victoria character varying(3000) NOT NULL,
CONSTRAINT pk_campeones PRIMARY KEY (id_campeones),
CONSTRAINT fk_hechizos FOREIGN KEY (id_hechizos)
REFERENCES hechizos (id_hechizos) MATCH SIMPLE
ON UPDATE NO ACTION ON DELETE NO ACTION,
CONSTRAINT fk_objetos KEY (id_objetos)
REFERENCES objetos (id_objetos) MATCH SIMPLE
ON UPDATE NO ACTION ON DELETE NO ACTION);


CREATE TABLE objetos (
id_objetos integer NOT NULL,
popularidad character varying(3000) NOT NULL ,
porcentaje_de_victoria character varying(3000) NOT NULL,
CONSTRAINT pk_campeones PRIMARY KEY (id_campeones),
CONSTRAINT fk_hechizos FOREIGN KEY (id_hechizos)
REFERENCES hechizos (id_hechizos) MATCH SIMPLE
ON UPDATE NO ACTION ON DELETE NO ACTION,
CONSTRAINT fk_objetos KEY (id_objetos)
REFERENCES objetos (id_objetos) MATCH SIMPLE
ON UPDATE NO ACTION ON DELETE NO ACTION);