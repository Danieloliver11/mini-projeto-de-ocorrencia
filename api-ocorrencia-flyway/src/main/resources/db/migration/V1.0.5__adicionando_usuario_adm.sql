INSERT INTO cliente (cod_cliente, nme_cliente, dt_nascimento, nmo_cpf, dt_criacao, senha, roles)
SELECT nextval('cliente_seq'), 'Mario Vitar', '1995-01-01', '50398963010', '2024-10-13', 'root', 1
WHERE NOT EXISTS (
    SELECT 1 FROM cliente WHERE nmo_cpf = '50398963010'
);