CREATE TABLE tb_client_legal (
    id UUID PRIMARY KEY, -- mesmo id da tabela client
    company_name VARCHAR(255) NOT NULL,
    cnpj VARCHAR(18),
    foundation_date DATE,
    trading_name VARCHAR(255),
    father_name VARCHAR(255),
    CONSTRAINT fk_client_natural_id FOREIGN KEY (id) REFERENCES tb_client(id)
);