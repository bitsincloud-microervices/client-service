CREATE TABLE tb_order (
  id UUID DEFAULT uuid_generate_v4() NOT NULL,
  order_id varchar(255) NOT NULL UNIQUE,
  amount numeric(19,2) NOT NULL,
  status varchar(255) NOT NULL,
  create_by varchar(255) NOT NULL DEFAULT 'system_user',
  created_date timestamp DEFAULT CURRENT_DATE,
  last_modified_by varchar(255),
  last_modified_date timestamp DEFAULT CURRENT_DATE,
  PRIMARY KEY (id)
);