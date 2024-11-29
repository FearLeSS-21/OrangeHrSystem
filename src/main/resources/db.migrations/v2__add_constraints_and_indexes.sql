
ALTER TABLE department_model MODIFY COLUMN name VARCHAR(255) NOT NULL;
ALTER TABLE team_model MODIFY COLUMN name VARCHAR(255) NOT NULL;

-- Create indexes on foreign keys for better query performance
CREATE INDEX idx_department_id ON employee_model(department_id);
CREATE INDEX idx_manager_id ON employee_model(manager_id);
CREATE INDEX idx_team_id ON employee_model(team_id);
