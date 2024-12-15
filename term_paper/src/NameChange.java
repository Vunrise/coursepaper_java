import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NameChange extends AuthorizedEmployee {
    protected static void main() {
        try {
            int x = 0;
            // создаем меню с пунктами
            while (true) {
                System.out.println("1. Добавить свидетельство о перемене имени");
                System.out.println("2. Удалить свидетельство о перемене имени");
                System.out.println("3. Выход");

                // Используем метод getIntInput для получения корректного ввода
                x = getIntInput("Выберите пункт меню: ");

                // Переход к другим методам в зависимости от выбранного пункта пользователем
                switch (x) {
                    case 1:
                        addNameChange(); // Добавить свидетельство
                        break;
                    case 2:
                        deleteNameChange(); // Удалить свидетельство
                        break;
                    case 3:
                        System.out.println("Возврат в главное меню...");
                        return;
                    default:
                        System.out.println("Неверный выбор. Пожалуйста, выберите пункт из меню.");
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println("Произошла ошибка: " + e.getMessage());
        }
    }

    // Метод для добавления свидетельства о перемене имени
    private static void addNameChange() {
        try {
            // Запрос на добавление нового свидетельства о перемене имени
            int citizenId = getIntInput("Введите ID гражданина:");
            System.out.print("Введите новое полное имя (Фамилия Имя Отчество): ");
            String newFullName = scan.nextLine().trim();

            // Разбиваем новое полное имя на части
            String[] nameParts = newFullName.split(" ");
            String newLastName = nameParts[0]; // Фамилия
            String newFirstName = nameParts[1]; // Имя
            String newMiddleName = (nameParts.length > 2) ? nameParts[2] : null; // Отчество (может быть пустым)

            // Получение старого полного имени для возможного восстановления
            String sqlGetOldName = "SELECT first_name, last_name, middle_name FROM citizens WHERE citizen_id = ?";
            String oldFirstName = null, oldLastName = null, oldMiddleName = null;

            try (PreparedStatement stmtGetOldName = con.prepareStatement(sqlGetOldName)) {
                stmtGetOldName.setInt(1, citizenId);
                try (ResultSet rs = stmtGetOldName.executeQuery()) {
                    if (rs.next()) {
                        oldFirstName = rs.getString("first_name");
                        oldLastName = rs.getString("last_name");
                        oldMiddleName = rs.getString("middle_name");
                    }
                }
            }

            // SQL-запрос для добавления нового свидетельства о перемене имени
            String sqlInsertNameChange = "INSERT INTO name_change (citizen_id, old_name, new_name, change_date, registration_date, status) " +
                    "VALUES (?, ?, ?, CURRENT_DATE, CURRENT_DATE, 'active')";

            // SQL-запрос для обновления ФИО гражданина в таблице citizens
            String sqlUpdateCitizenName = "UPDATE citizens SET first_name = ?, last_name = ?, middle_name = ? WHERE citizen_id = ?";

            try (PreparedStatement stmtInsertNameChange = con.prepareStatement(sqlInsertNameChange);
                 PreparedStatement stmtUpdateCitizenName = con.prepareStatement(sqlUpdateCitizenName)) {

                // Вставка в таблицу name_change
                stmtInsertNameChange.setInt(1, citizenId);
                stmtInsertNameChange.setString(2, oldLastName + " " + oldFirstName + " " + oldMiddleName);
                stmtInsertNameChange.setString(3, newLastName + " " + newFirstName + " " + newMiddleName);
                stmtInsertNameChange.executeUpdate();

                // Обновление ФИО в таблице citizens
                stmtUpdateCitizenName.setString(1, newFirstName); // Новое имя
                stmtUpdateCitizenName.setString(2, newLastName); // Новая фамилия
                stmtUpdateCitizenName.setString(3, newMiddleName);// Новое отчество
                stmtUpdateCitizenName.setInt(4, citizenId);
                stmtUpdateCitizenName.executeUpdate();

                System.out.println("Свидетельство о перемене имени добавлено и ФИО гражданина обновлено.");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при добавлении свидетельства о перемене имени: " + e.getMessage());
        }
    }

    // Метод для удаления свидетельства о перемене имени
    private static void deleteNameChange() {
        try {
            // Запрос на удаление свидетельства о перемене имени
            int nameChangeId = getIntInput("Введите ID свидетельства о перемене имени для удаления:");

            // Получение старого ФИО из таблицы name_change
            String sqlGetOldName = "SELECT citizen_id, old_name FROM name_change WHERE name_change_id = ?";
            int citizenId = 0;
            String oldName = null;

            try (PreparedStatement stmtGetOldName = con.prepareStatement(sqlGetOldName)) {
                stmtGetOldName.setInt(1, nameChangeId);
                try (ResultSet rs = stmtGetOldName.executeQuery()) {
                    if (rs.next()) {
                        citizenId = rs.getInt("citizen_id");
                        oldName = rs.getString("old_name");
                    }
                }
            }

            if (oldName != null) {
                // Разделяем старое полное имя на части
                String[] oldNameParts = oldName.split(" ");
                String oldFirstName = oldNameParts[0]; // Имя
                String oldLastName = oldNameParts[1]; // Фамилия
                String oldMiddleName = (oldNameParts.length > 2) ? oldNameParts[2] : null; // Отчество

                // SQL-запрос для удаления свидетельства о перемене имени
                String sqlDeleteNameChange = "DELETE FROM name_change WHERE name_change_id = ?";

                // SQL-запрос для восстановления старого ФИО в таблице citizens
                String sqlUpdateCitizenName = "UPDATE citizens SET first_name = ?, last_name = ?, middle_name = ? WHERE citizen_id = ?";

                try (PreparedStatement stmtDeleteNameChange = con.prepareStatement(sqlDeleteNameChange);
                     PreparedStatement stmtUpdateCitizenName = con.prepareStatement(sqlUpdateCitizenName)) {

                    // Удаление из таблицы name_change
                    stmtDeleteNameChange.setInt(1, nameChangeId);
                    stmtDeleteNameChange.executeUpdate();

                    // Восстановление старого ФИО в таблице citizens
                    stmtUpdateCitizenName.setString(1, oldFirstName);// Старое имя
                    stmtUpdateCitizenName.setString(2, oldLastName);// Старая фамилия
                    stmtUpdateCitizenName.setString(3, oldMiddleName);// Старое отчество
                    stmtUpdateCitizenName.setInt(4, citizenId);// citizen_id
                    stmtUpdateCitizenName.executeUpdate();

                    System.out.println("Свидетельство о перемене имени удалено, и ФИО гражданина восстановлено.");
                }
            } else {
                System.out.println("Не найдено свидетельства о перемене имени с таким ID.");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при удалении свидетельства о перемене имени: " + e.getMessage());
        }
    }
}
