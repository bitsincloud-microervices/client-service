CREATE TABLE tb_social_network (
    id UUID DEFAULT uuid_generate_v4() NOT NULL,
    social_network_type VARCHAR(255) NOT NULL,
    url VARCHAR(500),
    client_id UUID NOT NULL,
    status varchar(255) NOT NULL,
    create_by varchar(255) NOT NULL DEFAULT 'system_user',
    created_date timestamp DEFAULT CURRENT_DATE,
    last_modified_by varchar(255),
    last_modified_date timestamp DEFAULT CURRENT_DATE,
    CONSTRAINT fk_client_social_network_id FOREIGN KEY (client_id) REFERENCES tb_client(id)
);