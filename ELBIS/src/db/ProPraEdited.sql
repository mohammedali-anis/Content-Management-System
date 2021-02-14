BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "Role" (
	"role_id"	INTEGER NOT NULL UNIQUE,
	"role_type"	TEXT NOT NULL,
	PRIMARY KEY("role_id" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "Section" (
	"section_id"	INTEGER,
	"section_type"	TEXT,
	"subsection"	TEXT,
	PRIMARY KEY("section_id")
);
CREATE TABLE IF NOT EXISTS "User" (
	"user_id"	INTEGER NOT NULL UNIQUE,
	"first_name"	TEXT,
	"last_name"	TEXT,
	"username"	TEXT UNIQUE,
	"password"	TEXT,
	"email"	TEXT UNIQUE,
	PRIMARY KEY("user_id" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "Media" (
	"media_type"	TEXT,
	"media_id"	INTEGER,
	"blog_id"	INTEGER,
	"file_path"	INTEGER,
	PRIMARY KEY("media_id"),
	FOREIGN KEY("blog_id") REFERENCES "Blog"("blog_id") ON DELETE CASCADE
);
CREATE TABLE IF NOT EXISTS "User_Role" (
	"user_id"	INTEGER UNIQUE,
	"role_id"	INTEGER,
	FOREIGN KEY("role_id") REFERENCES "Role"("role_id"),
	FOREIGN KEY("user_id") REFERENCES "User"("user_id") ON DELETE CASCADE
);
CREATE TABLE IF NOT EXISTS "User_Section" (
	"user_id"	INTEGER,
	"section_id"	INTEGER,
	FOREIGN KEY("section_id") REFERENCES "Section"("section_id") ON DELETE CASCADE,
	FOREIGN KEY("user_id") REFERENCES "User"("user_id")
);
CREATE TABLE IF NOT EXISTS "Blog" (
	"blog_id"	INTEGER NOT NULL UNIQUE,
	"blog_title"	TEXT,
	"blog_description"	TEXT,
	"fromDate"	TEXT,
	"toDate"	TEXT,
	"user_id"	INTEGER,
	"section_id"	INTEGER,
	"website"	TEXT,
	"terminal"	TEXT,
	"approved"	TEXT,
	"writtenby_user_id"	INTEGER,
	"editedby_user_id"	INTEGER,
	"checkedby_user_id"	INTEGER,
	"deletedby_user_id"	INTEGER,
	PRIMARY KEY("blog_id" AUTOINCREMENT),
	FOREIGN KEY("user_id") REFERENCES "User"("user_id") ON DELETE CASCADE,
	FOREIGN KEY("editedby_user_id") REFERENCES "User"("user_id") ON DELETE SET NULL,
	FOREIGN KEY("deletedby_user_id") REFERENCES "User"("user_id") ON DELETE SET NULL,
	FOREIGN KEY("section_id") REFERENCES "Section"("section_id") ON DELETE CASCADE,
	FOREIGN KEY("checkedby_user_id") REFERENCES "User"("user_id") ON DELETE SET NULL,
	FOREIGN KEY("writtenby_user_id") REFERENCES "User"("user_id") ON DELETE SET NULL
);
INSERT INTO "Role" VALUES (1,'Admin');
INSERT INTO "Role" VALUES (2,'Editor');
INSERT INTO "Role" VALUES (3,'User');
INSERT INTO "Section" VALUES (1,'Vereine','Sportverein');
INSERT INTO "Section" VALUES (2,'Vereine','Schützenverein');
INSERT INTO "Section" VALUES (3,'Vereine','Frauengemeinschaft');
INSERT INTO "Section" VALUES (4,'Vereine','Gesangverein');
INSERT INTO "Section" VALUES (5,'Vereine','Fischereiverein');
INSERT INTO "Section" VALUES (6,'Vereine','Kirchenchor');
INSERT INTO "Section" VALUES (7,'Vereine','Jungmännerverein');
INSERT INTO "Section" VALUES (8,'Vereine','Kath. Kirchengemeinde');
INSERT INTO "Section" VALUES (9,'Vereine','Ev. Kirchengemeinde');
INSERT INTO "Section" VALUES (10,'Vereine','Baptisengemeinde');
INSERT INTO "Section" VALUES (12,'Gemeinde','Bürgermeister');
INSERT INTO "Section" VALUES (13,'Gemeinde','Gemeinderat');
INSERT INTO "Section" VALUES (14,'Gemeinde','Freie-Wähler Gruppe (FWG)');
INSERT INTO "Section" VALUES (15,'Gemeinde','Wählergruppe Heidrich');
INSERT INTO "Section" VALUES (16,'Gemeinde','Kindertagesstätte');
INSERT INTO "Section" VALUES (17,'Gemeinde','Grundschule');
INSERT INTO "Section" VALUES (18,'Industrie und Gesundheit','Artzpraxen');
INSERT INTO "Section" VALUES (19,'Industrie und Gesundheit','Zahnarzt');
INSERT INTO "Section" VALUES (20,'Industrie und Gesundheit','Apotheke');
INSERT INTO "Section" VALUES (21,'Industrie und Gesundheit','Unternehmen');
INSERT INTO "User" VALUES (1,'Mohammed Ali','Anis','mohammedali.anis','pass123','f');
INSERT INTO "User" VALUES (529,'Hamza','Ali','hamza.ali','pass321','mohammed.anis@student.uni-siegen.de');
INSERT INTO "User" VALUES (530,'Alexander','Hoffmann','alex.hoffmann','alex','alexander2.hoffmann@student.uni-siegen.de');
INSERT INTO "User" VALUES (531,'Christian','Wenckheim','christian','christian','christian.wenckheim@student.uni-siegen.de');
INSERT INTO "User" VALUES (532,'Sophie','Wittenstein','sophie','sophie','sophie.wittenstein@student.uni-siegen.de');
INSERT INTO "User" VALUES (533,'Aleksander','Root','alex.root','alex','aleksander.root@student.uni-siegen.de');
INSERT INTO "User" VALUES (534,'Andreas','Hoffmann','andreas.hoffmann','andreas','andreas.hoffmann@uni-siegen.de');
INSERT INTO "User" VALUES (536,'Ezgi','Tüfek','ezgi.tüfek','pass','moh.ali.anis@gmail.com');
INSERT INTO "User_Role" VALUES (1,1);
INSERT INTO "User_Role" VALUES (529,2);
INSERT INTO "User_Role" VALUES (530,3);
INSERT INTO "User_Role" VALUES (531,3);
INSERT INTO "User_Role" VALUES (532,3);
INSERT INTO "User_Role" VALUES (533,3);
INSERT INTO "User_Role" VALUES (534,2);
INSERT INTO "User_Role" VALUES (536,3);
INSERT INTO "User_Section" VALUES ('user_id','section_id');
INSERT INTO "User_Section" VALUES (0,0);
INSERT INTO "User_Section" VALUES (0,0);
INSERT INTO "User_Section" VALUES (530,1);
INSERT INTO "User_Section" VALUES (530,2);
INSERT INTO "User_Section" VALUES (530,3);
INSERT INTO "User_Section" VALUES (530,4);
INSERT INTO "User_Section" VALUES (530,5);
INSERT INTO "User_Section" VALUES (530,6);
INSERT INTO "User_Section" VALUES (530,7);
INSERT INTO "User_Section" VALUES (530,8);
INSERT INTO "User_Section" VALUES (530,9);
INSERT INTO "User_Section" VALUES (530,10);
INSERT INTO "User_Section" VALUES (531,12);
INSERT INTO "User_Section" VALUES (531,13);
INSERT INTO "User_Section" VALUES (531,14);
INSERT INTO "User_Section" VALUES (531,15);
INSERT INTO "User_Section" VALUES (531,16);
INSERT INTO "User_Section" VALUES (531,17);
INSERT INTO "User_Section" VALUES (532,18);
INSERT INTO "User_Section" VALUES (532,19);
INSERT INTO "User_Section" VALUES (533,20);
INSERT INTO "User_Section" VALUES (533,21);
INSERT INTO "User_Section" VALUES (529,1);
INSERT INTO "User_Section" VALUES (529,2);
INSERT INTO "User_Section" VALUES (529,3);
INSERT INTO "User_Section" VALUES (529,4);
INSERT INTO "User_Section" VALUES (529,5);
INSERT INTO "User_Section" VALUES (529,6);
INSERT INTO "User_Section" VALUES (529,7);
INSERT INTO "User_Section" VALUES (529,8);
INSERT INTO "User_Section" VALUES (529,9);
INSERT INTO "User_Section" VALUES (529,10);
INSERT INTO "User_Section" VALUES (529,12);
INSERT INTO "User_Section" VALUES (529,13);
INSERT INTO "User_Section" VALUES (529,14);
INSERT INTO "User_Section" VALUES (529,15);
INSERT INTO "User_Section" VALUES (529,16);
INSERT INTO "User_Section" VALUES (529,17);
INSERT INTO "User_Section" VALUES (534,18);
INSERT INTO "User_Section" VALUES (534,19);
INSERT INTO "User_Section" VALUES (534,20);
INSERT INTO "User_Section" VALUES (534,21);
INSERT INTO "User_Section" VALUES (536,1);
INSERT INTO "User_Section" VALUES (536,21);
COMMIT;
