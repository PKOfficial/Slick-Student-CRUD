
CREATE TABLE students(name varchar(200),email varchar(200),id int PRIMARY KEY auto_increment);

CREATE TABLE subjects(name varchar(200),id int PRIMARY KEY auto_increment);

CREATE TABLE subject_allocation(stu_id int, sub_id int,id int PRIMARY KEY auto_increment);

