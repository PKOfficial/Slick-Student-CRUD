DROP TABLE IF EXISTS students;

CREATE TABLE IF NOT EXISTS students(name varchar(200),email varchar(200),id int PRIMARY KEY auto_increment);

DROP TABLE IF EXISTS subjects;

CREATE TABLE IF NOT EXISTS subjects(name varchar(200),id int PRIMARY KEY auto_increment);

DROP TABLE IF EXISTS subject_allocation;

CREATE TABLE IF NOT EXISTS subject_allocation(stu_id int, sub_id int,id int PRIMARY KEY auto_increment);

