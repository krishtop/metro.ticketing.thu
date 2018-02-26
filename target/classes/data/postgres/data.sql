-- sdf
INSERT INTO Action (name, description)
VALUES ('ADD', '');

INSERT INTO Action (name, description)
VALUES ('REMOVE', '');

INSERT INTO Action (name, description)
VALUES ('ACCEPT_REQUEST', '');

INSERT INTO Action (name, description)
VALUES ('REJECT_REQUEST', '');

INSERT INTO Action (name, description)
VALUES ('SET_TARIFF', '');

INSERT INTO Action (name, description)
VALUES ('CHANGE_TARIFF', '');

INSERT INTO Role(name)
VALUES ('role');

INSERT INTO Role(name)
VALUES ('role2');

INSERT INTO Action_Role( action_id, role_id)
VALUES(1,1);
INSERT INTO Action_Role( action_id, role_id)
VALUES(2,1);
INSERT INTO Action_Role( action_id, role_id)
VALUES(3,1);
INSERT INTO Action_Role( action_id, role_id)
VALUES(4,1);
INSERT INTO Action_Role( action_id, role_id)
VALUES(5,1);
INSERT INTO Action_Role( action_id, role_id)
VALUES(6,1);

INSERT INTO Request( request_status,name, email,  role_id)
VALUES ('ACCEPTED', 'req', 'req@gmail.com', 1);

INSERT INTO Request( request_status,name, email,  role_id)
VALUES ('NEW', 'req', 'req@gmail2.com', 2);


INSERT  INTO tariff (tariff_type, day_Count, trips_Count)
VALUES ('TIME', 10, NULL);

INSERT  INTO tariff (tariff_type, day_Count, trips_Count)
VALUES ('TRIP', NULL , 15);

INSERT INTO ticket(activated, remining_trips, exp_Time, tariff_id)
VALUES (TRUE, 10, NULL, 2 );

INSERT INTO ticket(activated, remining_trips, exp_Time, tariff_id)
VALUES (TRUE, NULL , CURRENT_DATE , 1 );