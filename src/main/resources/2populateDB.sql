INSERT INTO companies (company_name, company_location) VALUES ('Alphabet','Austria');
INSERT INTO companies (company_name, company_location) VALUES ('Bettarang','Belgium');
INSERT INTO companies (company_name, company_location) VALUES ('Columbus','Croatia');
INSERT INTO companies (company_name, company_location) VALUES ('DeltaIntegrale','Denmark');
INSERT INTO companies (company_name, company_location) VALUES ('Elastomers','Estonia');
INSERT INTO companies (company_name, company_location) VALUES ('Fiction','France');
INSERT INTO companies (company_name, company_location) VALUES ('GeneralComputing','Germany');
INSERT INTO companies (company_name, company_location) VALUES ('Hummering','Hungary');
INSERT INTO companies (company_name, company_location) VALUES ('Interstellar','India');
INSERT INTO companies (company_name, company_location) VALUES ('Kinetics','Kuwait');

INSERT INTO customers (customer_name, industry) VALUES ('Avocado','Fruits');
INSERT INTO customers (customer_name, industry) VALUES ('Banana','Fruits');
INSERT INTO customers (customer_name, industry) VALUES ('Cocoa','Foods');
INSERT INTO customers (customer_name, industry) VALUES ('Dirty Tidy','Cleaning');
INSERT INTO customers (customer_name, industry) VALUES ('Esta','Nothing special');
INSERT INTO customers (customer_name, industry) VALUES ('Fuagra','Foods');
INSERT INTO customers (customer_name, industry) VALUES ('Greinger','Names');
INSERT INTO customers (customer_name, industry) VALUES ('Humanity','Whatever');
INSERT INTO customers (customer_name, industry) VALUES ('Intellect','Mindblowing');
INSERT INTO customers (customer_name, industry) VALUES ('Kinder Garden','Children');

INSERT INTO developers (first_name, last_name, age, sex, company_id) VALUES ('Aaron','Atkinson',34,'male',2);
INSERT INTO developers (first_name, last_name, age, sex, company_id) VALUES ('Bodo','Buschmann',55,'male',8);
INSERT INTO developers (first_name, last_name, age, sex, company_id) VALUES ('Calvina','Clein',23,'female',6);
INSERT INTO developers (first_name, last_name, age, sex, company_id) VALUES ('Dreik','Deboa',18,'male',3);
INSERT INTO developers (first_name, last_name, age, sex, company_id) VALUES ('Eric','Errano',44,'male',4);
INSERT INTO developers (first_name, last_name, age, sex, company_id) VALUES ('Freda','Ferodo',33,'female',7);
INSERT INTO developers (first_name, last_name, age, sex, company_id) VALUES ('George','Ganesha',27,'male',9);
INSERT INTO developers (first_name, last_name, age, sex, company_id) VALUES ('Henry','Hord',19,'male',10);
INSERT INTO developers (first_name, last_name, age, sex, company_id) VALUES ('Indira','Indi',26,'female',1);
INSERT INTO developers (first_name, last_name, age, sex, company_id) VALUES ('Ken','Kanepa',21,'male',5);
INSERT INTO developers (first_name, last_name, age, sex, company_id) VALUES ('Lenox','Luis',22,'male',6);
INSERT INTO developers (first_name, last_name, age, sex, company_id) VALUES ('Milana','Miller',31,'female',4);
INSERT INTO developers (first_name, last_name, age, sex, company_id) VALUES ('Naomi','Neoni',39,'female',5);
INSERT INTO developers (first_name, last_name, age, sex, company_id) VALUES ('Orlando','Oruel',50,'male',7);
INSERT INTO developers (first_name, last_name, age, sex, company_id) VALUES ('Paolo','Pele',29,'male',5);

INSERT INTO skills (specialization, level) VALUES ('C++','Junior');
INSERT INTO skills (specialization, level) VALUES ('Java','Junior');
INSERT INTO skills (specialization, level) VALUES ('JavaScript','Junior');
INSERT INTO skills (specialization, level) VALUES ('C++','Middle');
INSERT INTO skills (specialization, level) VALUES ('Java','Middle');
INSERT INTO skills (specialization, level) VALUES ('JavaScript','Middle');
INSERT INTO skills (specialization, level) VALUES ('C++','Senior');
INSERT INTO skills (specialization, level) VALUES ('Java','Senior');
INSERT INTO skills (specialization, level) VALUES ('JavaScript','Senior');

INSERT INTO projects (project_name, customer_id, company_id) VALUES ('Arisona',5,8);
INSERT INTO projects (project_name, customer_id, company_id) VALUES ('Britain',6,3);
INSERT INTO projects (project_name, customer_id, company_id) VALUES ('Capilso',4,9);
INSERT INTO projects (project_name, customer_id, company_id) VALUES ('Destiny',6,10);
INSERT INTO projects (project_name, customer_id, company_id) VALUES ('Elimination',8,2);
INSERT INTO projects (project_name, customer_id, company_id) VALUES ('Florida',9,5);
INSERT INTO projects (project_name, customer_id, company_id) VALUES ('Georgia',1,7);
INSERT INTO projects (project_name, customer_id, company_id) VALUES ('Honolulu',5,2);
INSERT INTO projects (project_name, customer_id, company_id) VALUES ('Indiana',3,1);
INSERT INTO projects (project_name, customer_id, company_id) VALUES ('Jamaika',6,8);
INSERT INTO projects (project_name, customer_id, company_id) VALUES ('Klaipeda',7,6);

INSERT INTO developersToProjects (project_id, developer_id) VALUES (1,2);
INSERT INTO developersToProjects (project_id, developer_id) VALUES (2,4);
INSERT INTO developersToProjects (project_id, developer_id) VALUES (3,7);
INSERT INTO developersToProjects (project_id, developer_id) VALUES (1,5);
INSERT INTO developersToProjects (project_id, developer_id) VALUES (1,6);
INSERT INTO developersToProjects (project_id, developer_id) VALUES (4,8);
INSERT INTO developersToProjects (project_id, developer_id) VALUES (7,8);
INSERT INTO developersToProjects (project_id, developer_id) VALUES (4,4);
INSERT INTO developersToProjects (project_id, developer_id) VALUES (2,1);
INSERT INTO developersToProjects (project_id, developer_id) VALUES (8,7);
INSERT INTO developersToProjects (project_id, developer_id) VALUES (1,4);
INSERT INTO developersToProjects (project_id, developer_id) VALUES (7,6);
INSERT INTO developersToProjects (project_id, developer_id) VALUES (3,9);
INSERT INTO developersToProjects (project_id, developer_id) VALUES (5,2);
INSERT INTO developersToProjects (project_id, developer_id) VALUES (6,7);
INSERT INTO developersToProjects (project_id, developer_id) VALUES (2,5);
INSERT INTO developersToProjects (project_id, developer_id) VALUES (4,1);
INSERT INTO developersToProjects (project_id, developer_id) VALUES (8,1);
INSERT INTO developersToProjects (project_id, developer_id) VALUES (9,3);
INSERT INTO developersToProjects (project_id, developer_id) VALUES (9,9);
INSERT INTO developersToProjects (project_id, developer_id) VALUES (10,10);
INSERT INTO developersToProjects (project_id, developer_id) VALUES (10,11);
INSERT INTO developersToProjects (project_id, developer_id) VALUES (10,3);
INSERT INTO developersToProjects (project_id, developer_id) VALUES (11,12);
INSERT INTO developersToProjects (project_id, developer_id) VALUES (11,9);
INSERT INTO developersToProjects (project_id, developer_id) VALUES (11,13);
INSERT INTO developersToProjects (project_id, developer_id) VALUES (11,14);
INSERT INTO developersToProjects (project_id, developer_id) VALUES (11,15);

INSERT INTO developersToSkills (developer_id, skill_id) VALUES (1,1);
INSERT INTO developersToSkills (developer_id, skill_id) VALUES (2,1);
INSERT INTO developersToSkills (developer_id, skill_id) VALUES (3,2);
INSERT INTO developersToSkills (developer_id, skill_id) VALUES (4,3);
INSERT INTO developersToSkills (developer_id, skill_id) VALUES (5,3);
INSERT INTO developersToSkills (developer_id, skill_id) VALUES (6,1);
INSERT INTO developersToSkills (developer_id, skill_id) VALUES (7,2);
INSERT INTO developersToSkills (developer_id, skill_id) VALUES (8,2);
INSERT INTO developersToSkills (developer_id, skill_id) VALUES (9,3);
INSERT INTO developersToSkills (developer_id, skill_id) VALUES (10,1);
INSERT INTO developersToSkills (developer_id, skill_id) VALUES (11,3);
INSERT INTO developersToSkills (developer_id, skill_id) VALUES (12,3);
INSERT INTO developersToSkills (developer_id, skill_id) VALUES (13,2);
INSERT INTO developersToSkills (developer_id, skill_id) VALUES (14,1);
INSERT INTO developersToSkills (developer_id, skill_id) VALUES (15,3);
INSERT INTO developersToSkills (developer_id, skill_id) VALUES (1,4);
INSERT INTO developersToSkills (developer_id, skill_id) VALUES (3,5);
INSERT INTO developersToSkills (developer_id, skill_id) VALUES (4,6);
INSERT INTO developersToSkills (developer_id, skill_id) VALUES (5,6);
INSERT INTO developersToSkills (developer_id, skill_id) VALUES (6,5);
INSERT INTO developersToSkills (developer_id, skill_id) VALUES (7,4);
INSERT INTO developersToSkills (developer_id, skill_id) VALUES (9,5);
INSERT INTO developersToSkills (developer_id, skill_id) VALUES (10,4);
INSERT INTO developersToSkills (developer_id, skill_id) VALUES (11,4);
INSERT INTO developersToSkills (developer_id, skill_id) VALUES (12,4);
INSERT INTO developersToSkills (developer_id, skill_id) VALUES (13,6);
INSERT INTO developersToSkills (developer_id, skill_id) VALUES (14,6);
INSERT INTO developersToSkills (developer_id, skill_id) VALUES (15,4);
INSERT INTO developersToSkills (developer_id, skill_id) VALUES (1,7);
INSERT INTO developersToSkills (developer_id, skill_id) VALUES (5,8);
INSERT INTO developersToSkills (developer_id, skill_id) VALUES (7,9);
INSERT INTO developersToSkills (developer_id, skill_id) VALUES (9,7);
INSERT INTO developersToSkills (developer_id, skill_id) VALUES (11,8);