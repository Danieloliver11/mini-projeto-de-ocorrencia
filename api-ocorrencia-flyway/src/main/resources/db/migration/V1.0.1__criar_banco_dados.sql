CREATE TABLE CLIENTE (
	cod_cliente BIGINT NOT NULL PRIMARY KEY,
	nme_cliente varchar(30) NOT NULL,
	dt_nascimento DATE NOT NULL,
	nmo_cpf VARCHAR(11) not null unique,
	dt_criacao DATE NOT NULL
);
CREATE SEQUENCE IF NOT EXISTS cliente_seq INCREMENT 1 START 1;


CREATE TABLE ENDERECO (
	cod_endereco BIGINT NOT NULL PRIMARY KEY,
	nme_locradouro varchar(60) NOT NULL,
	nme_bairro varchar(30) NOT NULL,
	nmr_cep VARCHAR(8) NOT NULL,
	nme_cidade varchar(30) NOT NULL,
	nme_estado varchar(30) NOT NULL
	
);
CREATE SEQUENCE IF NOT EXISTS endereco_seq INCREMENT 1 START 1;


CREATE TABLE OCORRENCIA (
	cod_ocorrencia BIGINT NOT NULL PRIMARY KEY,
	cod_cliente BIGINT NOT NULL,
	cod_endereco BIGINT NOT NULL,
	dta_ocorrencia DATE NOT NULL,
	sta_ocorrencia INTEGER not null,
	
CONSTRAINT fk_ocorrencia_cliente FOREIGN KEY (cod_cliente) REFERENCES cliente(cod_cliente),	
CONSTRAINT fk_ocorrencia_endereco FOREIGN KEY (cod_endereco) REFERENCES endereco(cod_endereco)	

);
CREATE SEQUENCE IF NOT EXISTS ocorrencia_seq INCREMENT 1 START 1;

CREATE TABLE FOTO_OCORRENCIA (
	cod_foto_ocorrencia BIGINT NOT NULL PRIMARY KEY,
	cod_ocorrencia BIGINT NOT NULL,
	dt_criacao DATE NOT NULL,
	disc_path_bucket VARCHAR(255) not null,
	disc_hash VARCHAR(36) not null unique,
	
	CONSTRAINT fk_foto_ocorrencia_ocorrencia FOREIGN KEY (cod_ocorrencia) REFERENCES ocorrencia(cod_ocorrencia)	
	
);
CREATE SEQUENCE IF NOT EXISTS foto_ocorrencia_seq INCREMENT 1 START 1;