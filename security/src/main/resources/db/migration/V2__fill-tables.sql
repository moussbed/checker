INSERT INTO app_role (role_uuid, role_name) VALUES ('09044940-e95a-4c0e-a6c6-03292577e2a7', 'READ-RESOURCE');
INSERT INTO app_role (role_uuid, role_name) VALUES ('52db3712-bd71-4161-b760-ac389e507eb7', 'CHECK-QRCODE');
INSERT INTO app_role (role_uuid, role_name) VALUES ('cbb0b182-e4a4-458d-9c64-e4715c00c0e3', 'UPDATE-RESOURCE');
INSERT INTO app_role (role_uuid, role_name) VALUES ('d2a4ca2a-f286-44e4-94cb-204eed1241c8', 'CREATE-RESOURCE');
INSERT INTO app_role (role_uuid, role_name) VALUES ('ecc27ef1-fe70-4a35-9f26-a4783369bfeb', 'ADMIN');

INSERT INTO app_user (user_uuid, password, username) VALUES ('8ad420e2-ba2c-4eab-b073-3abfdad990eb', '$2a$10$wkKrdbI.zDIODG5wNvwxNeqS7BQWnZOl77.DdJPHoGIX2W8asVNym', 'admin');
INSERT INTO app_user (user_uuid, password, username) VALUES ('c4bbbb51-614b-42d3-b133-eaf54a2f8458', '$2a$10$cxX4BZsLSd8UQ7/gDpdKE.evX/QaIevgn9cBGicKfW71cbCHmPTgW', 'checker');

INSERT INTO app_user_roles (app_user_user_uuid, roles_role_uuid) VALUES ('8ad420e2-ba2c-4eab-b073-3abfdad990eb', '09044940-e95a-4c0e-a6c6-03292577e2a7');
INSERT INTO app_user_roles (app_user_user_uuid, roles_role_uuid) VALUES ('8ad420e2-ba2c-4eab-b073-3abfdad990eb', 'd2a4ca2a-f286-44e4-94cb-204eed1241c8');
INSERT INTO app_user_roles (app_user_user_uuid, roles_role_uuid) VALUES ('8ad420e2-ba2c-4eab-b073-3abfdad990eb', 'ecc27ef1-fe70-4a35-9f26-a4783369bfeb');
INSERT INTO app_user_roles (app_user_user_uuid, roles_role_uuid) VALUES ('c4bbbb51-614b-42d3-b133-eaf54a2f8458', '09044940-e95a-4c0e-a6c6-03292577e2a7');
INSERT INTO app_user_roles (app_user_user_uuid, roles_role_uuid) VALUES ('c4bbbb51-614b-42d3-b133-eaf54a2f8458', 'd2a4ca2a-f286-44e4-94cb-204eed1241c8');
INSERT INTO app_user_roles (app_user_user_uuid, roles_role_uuid) VALUES ('c4bbbb51-614b-42d3-b133-eaf54a2f8458', '52db3712-bd71-4161-b760-ac389e507eb7');