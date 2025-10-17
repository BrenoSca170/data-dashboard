CREATE TABLE user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role ENUM('ANALISTA', 'CLIENTE') NOT NULL
);

CREATE TABLE dataset (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    file_path VARCHAR(255) NOT NULL, 
    upload_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status ENUM('PROCESSADO', 'ERRO', 'PENDENTE') DEFAULT 'PENDENTE',
    user_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user(id)
);

CREATE TABLE dashboard (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    user_id INT NOT NULL,
    dataset_id INT,
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (dataset_id) REFERENCES dataset(id)
);

CREATE TABLE dados_vendas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    dataset_id INT NOT NULL, -- Identifica o upload
    data_transacao DATE NOT NULL,
    valor_liquido DECIMAL(10, 2) NOT NULL,
    categoria_produto VARCHAR(100),
    regiao VARCHAR(50),
    canal_venda VARCHAR(50),
    FOREIGN KEY (dataset_id) REFERENCES dataset(id),

    INDEX idx_dataset_fk (dataset_id),
    INDEX idx_data_regiao (data_transacao, regiao)
);

CREATE TABLE dashboard_client (
    id INT AUTO_INCREMENT PRIMARY KEY,
    dashboard_id INT NOT NULL,
    client_id INT NOT NULL,
    FOREIGN KEY (dashboard_id) REFERENCES dashboard(id),
    FOREIGN KEY (client_id) REFERENCES user(id)
);
