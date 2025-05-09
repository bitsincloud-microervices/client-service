CREATE TABLE tb_client (
  id UUID DEFAULT uuid_generate_v4() NOT NULL,
  name varchar(255) NOT NULL UNIQUE,
  email varchar(255) NOT NULL UNIQUE,
  whatsapp varchar(255) NOT NULL UNIQUE,
  phone varchar(255) NOT NULL UNIQUE,
  address_id UUID NOT NULL,
  status varchar(255) NOT NULL,
  create_by varchar(255) NOT NULL DEFAULT 'system_user',
  created_date timestamp DEFAULT CURRENT_DATE,
  last_modified_by varchar(255),
  last_modified_date timestamp DEFAULT CURRENT_DATE,
  PRIMARY KEY (id)
);