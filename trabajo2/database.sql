
CREATE SEQUENCE SEQ_PLATOS;
CREATE SEQUENCE SEQ_MENUS;
CREATE SEQUENCE SEQ_INGREDIENTES;


CREATE TABLE ingredientes (
                              id VARCHAR(10) PRIMARY KEY,
                              nombre VARCHAR(150) UNIQUE NOT NULL
);


CREATE TABLE platos (
                        id VARCHAR(10) PRIMARY KEY,
                        nombre VARCHAR(150) UNIQUE NOT NULL,
                        descripcion VARCHAR(450) NOT NULL,
                        precio NUMERIC(5, 2) NOT NULL,
                        tipo VARCHAR(20) NOT NULL
);


CREATE TABLE menus (
                       id VARCHAR(10) PRIMARY KEY,
                       nombre VARCHAR(100) UNIQUE NOT NULL,
                       desde DATE NOT NULL,
                       hasta DATE NOT NULL,
                       precio NUMERIC(6, 2) NOT NULL
);


CREATE TABLE menu_platos (
                             id_menu VARCHAR(10) REFERENCES menus(id),
                             id_plato VARCHAR(10) REFERENCES platos(id),
                             PRIMARY KEY (id_menu, id_plato)
);


CREATE TABLE compuestos (
                            id_plato VARCHAR(10) REFERENCES platos(id),
                            id_ingrediente VARCHAR(10) REFERENCES ingredientes(id),
                            cantidad INTEGER NOT NULL,
                            unidad VARCHAR(20) NOT NULL,
                            PRIMARY KEY (id_plato, id_ingrediente)
);
