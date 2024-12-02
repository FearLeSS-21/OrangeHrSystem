
CREATE TABLE employee (
                                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                name VARCHAR(255) NOT NULL,
                                gender VARCHAR(10),
                                date_of_birth DATE,
                                graduation_date DATE,
                                department_id BIGINT,
                                manager_id BIGINT,
                                team_id BIGINT,
                                gross_salary DECIMAL(10, 2),
                                net_salary DECIMAL(10, 2),
                                FOREIGN KEY (department_id) REFERENCES department(id),
                                FOREIGN KEY (manager_id) REFERENCES employee(id),
                                FOREIGN KEY (team_id) REFERENCES team(id)
);
