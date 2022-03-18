CREATE TABLE companies (
	company_id serial PRIMARY KEY,
	company_name varchar(50),
	company_location varchar(50)
);

CREATE TABLE customers (
	customer_id serial PRIMARY KEY,
	customer_name varchar(50),
	industry varchar(50)
);

CREATE TABLE developers (
	developer_id serial PRIMARY KEY,
	first_name varchar(50),
	last_name varchar(50),
	age integer,
	sex varchar(10),
	company_id integer REFERENCES companies
);

CREATE TABLE skills (
	skill_id serial PRIMARY KEY,
	specialization varchar(50),
	level varchar(50)
);

CREATE TABLE projects (
	project_id serial PRIMARY KEY,
	project_name varchar(50),
	customer_id integer REFERENCES customers,
	company_id integer REFERENCES companies
);

CREATE TABLE developersToProjects (
	project_id integer REFERENCES projects,
	developer_id integer REFERENCES developers
);

CREATE TABLE developersToSkills (
developer_id integer REFERENCES developers,
	skill_id integer REFERENCES skills
);