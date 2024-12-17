ALTER TABLE department MODIFY COLUMN name VARCHAR(255) NOT NULL;
ALTER TABLE team MODIFY COLUMN name VARCHAR(255) NOT NULL;


CREATE INDEX idx_department_id ON employee(department_id);
CREATE INDEX idx_manager_id ON employee(manager_id);
CREATE INDEX idx_team_id ON employee(team_id);