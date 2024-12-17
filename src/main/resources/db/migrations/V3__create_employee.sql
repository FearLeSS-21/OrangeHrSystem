CREATE TABLE  IF NOT EXISTS employee (
                                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                name VARCHAR(255) NOT NULL,
                                gender VARCHAR(10) NOT NULL,
                                date_of_birth DATE NOT NULL,
                                graduation_date DATE NOT NULL,
                                department_id BIGINT NOT NULL,
                                manager_id BIGINT,
                                team_id BIGINT,
                                gross_salary DECIMAL(10, 2) NOT NULL,
                                net_salary DECIMAL(10, 2) NOT NULL,
                                FOREIGN KEY (department_id) REFERENCES department(id),
                                FOREIGN KEY (manager_id) REFERENCES employee(id),
                                FOREIGN KEY (team_id) REFERENCES team(id)
);