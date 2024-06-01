-- Автозаполнение для таблицы `additionally_user`


INSERT INTO additionally_user (date_of_birth, email, gender, name, patronymic, surname, user_id)
VALUES ('1990-01-01', 'example@example.com', 1, 'Иван', 'Иванович', 'Иванов', NULL),
       ('1995-05-05', 'another@example.com', 0, 'Мария', 'Петровна', 'Петрова', NULL);

-- Автозаполнение для таблицы `users`
# пароль 1 логин 1
# пароль 2 логин 2
INSERT INTO users (balance, mobile_balance, password, login, additionally_user_id)
VALUES (1000.00, 500.00, '$2y$10$huKx2H1/i5mBlWb5TodIQ.jjw79DGMLcJgouXFK2aera4OwLTt.jy', '1',
        (SELECT id FROM additionally_user WHERE email = 'example@example.com')),
       (2000.00, 1500.00, '$2y$10$0oY9oAcUBV.0OQP7EKog3uWUXRqKjX7qhSmXpiYAKgZ5BhCehrH8G', '2',
        (SELECT id FROM additionally_user WHERE email = 'another@example.com'));

-- Автозаполнение для таблицы `payments`
INSERT INTO payments (date, number, sum, user_id)
VALUES (NOW(), '1234567890', 100.00, (SELECT id FROM users WHERE login = 'user1')),
       (NOW(), '0987654321', 200.00, (SELECT id FROM users WHERE login = 'user2'));

INSERT INTO users_payments (user_id, payments_id)
SELECT u.id, p.id
FROM users u
         JOIN payments p ON u.id = p.user_id;

UPDATE additionally_user au
SET au.user_id = (SELECT u.id
                  FROM users u
                  WHERE u.additionally_user_id = au.id);

DELIMITER //

CREATE PROCEDURE AddPaymentsAndUpdateUsersPayments()
BEGIN
    DECLARE v_done INT DEFAULT FALSE;
    DECLARE v_user_id INT;
    DECLARE v_payment_id INT;
    DECLARE cur CURSOR FOR SELECT id FROM users;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET v_done = TRUE;

    OPEN cur;

    read_loop:
    LOOP
        FETCH cur INTO v_user_id;
        IF v_done THEN
            LEAVE read_loop;
        END IF;

        -- Добавляем 50 записей для текущего пользователя
        SET @i = 0;
        WHILE @i < 50
            DO
                INSERT INTO payments (date, number, sum, user_id)
                VALUES (NOW(), CONCAT('TXN', LPAD(FLOOR(RAND() * 9999999999), 10, '0')), FLOOR(RAND() * 1000) + 1,
                        v_user_id);
                SET v_payment_id = LAST_INSERT_ID(); -- Получаем ID последней вставленной записи
                INSERT INTO users_payments (user_id, payments_id)
                VALUES (v_user_id, v_payment_id); -- Добавляем связь в таблицу users_payments
                SET @i = @i + 1;
            END WHILE;

        -- Обновляем user_id в таблице additionally_user
        UPDATE additionally_user au
        SET au.user_id = v_user_id
        WHERE au.id = (SELECT additionally_user_id FROM users WHERE id = v_user_id);

    END LOOP;

    CLOSE cur;
END;

//

DELIMITER ;

-- Вызываем процедуру для добавления платежей и обновления связей
CALL AddPaymentsAndUpdateUsersPayments();
