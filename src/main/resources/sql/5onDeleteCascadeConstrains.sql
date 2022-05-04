ALTER TABLE developerstoprojects
DROP CONSTRAINT developerstoprojects_developer_id_fkey,
ADD CONSTRAINT developerstoprojects_developer_id_fkey
   FOREIGN KEY (developer_id)
   REFERENCES developers(developer_id)
   ON DELETE CASCADE;

ALTER TABLE developerstoprojects
   DROP CONSTRAINT developerstoprojects_project_id_fkey,
   ADD CONSTRAINT developerstoprojects_project_id_fkey
      FOREIGN KEY (project_id)
      REFERENCES projects(project_id)
      ON DELETE CASCADE;