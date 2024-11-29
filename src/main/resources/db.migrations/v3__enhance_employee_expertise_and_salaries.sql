CREATE TABLE employee_expertise (
                                    employee_id BIGINT NOT NULL,
                                    expertise VARCHAR(255) NOT NULL,
                                    PRIMARY KEY (employee_id, expertise),
                                    FOREIGN KEY (employee_id) REFERENCES employee_model(id) ON DELETE CASCADE
);

ALTER TABLE employee_model ADD CONSTRAINT check_salary CHECK (gross_salary > 0 AND net_salary >= 0);
