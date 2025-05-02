CREATE TABLE tb_client_document (
    id UUID DEFAULT uuid_generate_v4() NOT NULL,
    client_document_type VARCHAR(255) NOT NULL,
    file_name VARCHAR(500),
    file_url VARCHAR(500),
    client_id UUID NOT NULL,
    status varchar(255) NOT NULL,
    create_by varchar(255) NOT NULL DEFAULT 'system_user',
    created_date timestamp DEFAULT CURRENT_DATE,
    last_modified_by varchar(255),
    last_modified_date timestamp DEFAULT CURRENT_DATE,
    CONSTRAINT fk_client_document_id FOREIGN KEY (client_id) REFERENCES tb_client(id)
);