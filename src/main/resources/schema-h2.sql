
DROP TABLE IF EXISTS customers;
CREATE TABLE public.customers (
                               id INT AUTO_INCREMENT PRIMARY KEY,
                               name VARCHAR(250) NOT NULL,
                               surname VARCHAR(250) NOT NULL,
                               email VARCHAR(250) NOT NULL,
                               birthdate DATE NOT NULL,
                               address VARCHAR(250),
                               phone VARCHAR(20),
                               created_date TIMESTAMP,
                               last_modified_date TIMESTAMP
);

