CREATE TABLE tb_client_natural (
    id UUID PRIMARY KEY, -- mesmo id da tabela client
    full_name VARCHAR(255) NOT NULL,
    cpf VARCHAR(14),
    birth_date DATE,
    gender VARCHAR(10),
    rg VARCHAR(20),
    mother_name VARCHAR(255),
    father_name VARCHAR(255),
    CONSTRAINT fk_client_natural_id FOREIGN KEY (id) REFERENCES tb_client(id)
);