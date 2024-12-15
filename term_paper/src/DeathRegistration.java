import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DeathRegistration extends AuthorizedEmployee {
    protected static void main() {
        try {
            int x = 0;
            // создаем меню с пунктами
            while (true) {
                System.out.println("1. Добавить свидетельство о смерти");
                System.out.println("2. Удалить свидетельство о смерти");
                System.out.println("3. Выход");

                // Используем метод getIntInput для получения корректного ввода
                x = getIntInput("Выберите пункт меню: ");

                // Переход к другим методам в зависимости от выбранного пункта пользователем
                switch (x) {
                    case 1:
                        addDeathRegistration(); // Добавить свидетельство
                        break;
                    case 2:
                        deleteDeathRegistration(); // Удалить свидетельство
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

    // Метод для добавления свидетельства о смерти
    private static void addDeathRegistration() {
        try {
            // Запрос на добавление нового свидетельства о смерти
            int citizenId = getIntInput("Введите ID гражданина:");
            System.out.println("Введите дату смерти (формат YYYY-MM-DD):");
            String deathDateInput = scan.nextLine();

            // Преобразование строки в дату
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date parsedDate = dateFormat.parse(deathDateInput);
            Date deathDate = new Date(parsedDate.getTime()); // Преобразуем в java.sql.Date

            System.out.println("Введите место смерти:");
            String deathPlace = scan.nextLine();

            // Получение сегодняшней даты для регистрации
            Date registrationDate = new Date(System.currentTimeMillis()); // Текущая дата

            // SQL-запрос для добавления нового свидетельства о смерти
            String sqlInsertDeathRegistration = "INSERT INTO death_registration (citizen_id, death_date, death_place, registration_date, status) " +
                    "VALUES (?, ?, ?, ?, ?)";

            // SQL-запрос для обновления статуса гражданина на 'inactive'
            String sqlUpdateCitizenStatus = "UPDATE citizens SET status = 'inactive' WHERE citizen_id = ?";

            try (PreparedStatement stmtInsertDeathRegistration = con.prepareStatement(sqlInsertDeathRegistration);
                 PreparedStatement stmtUpdateCitizenStatus = con.prepareStatement(sqlUpdateCitizenStatus)) {

                // Вставка в таблицу death_registration
                stmtInsertDeathRegistration.setInt(1, citizenId);
                stmtInsertDeathRegistration.setDate(2, deathDate);
                stmtInsertDeathRegistration.setString(3, deathPlace);
                stmtInsertDeathRegistration.setDate(4, registrationDate);
                stmtInsertDeathRegistration.setString(5, "active");
                stmtInsertDeathRegistration.executeUpdate();

                // Обновление статуса гражданина на 'inactive'
                stmtUpdateCitizenStatus.setInt(1, citizenId); // citizen_id
                stmtUpdateCitizenStatus.executeUpdate();

                System.out.println("Свидетельство о смерти добавлено и статус гражданина обновлен на 'inactive'.");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при добавлении свидетельства о смерти: " + e.getMessage());
        } catch (ParseException e) {
            System.out.println("Ошибка при преобразовании даты: " + e.getMessage());
        }
    }

    // Метод для удаления свидетельства о смерти
    private static void deleteDeathRegistration() {
        try {
            // Запрос на удаление свидетельства о смерти
            int deathId = getIntInput("Введите ID свидетельства о смерти для удаления:");

            // SQL-запрос для получения citizen_id из таблицы death_registration
            String sqlGetCitizenId = "SELECT citizen_id FROM death_registration WHERE death_id = ?";

            // SQL-запрос для удаления свидетельства о смерти
            String sqlDeleteDeathRegistration = "DELETE FROM death_registration WHERE death_id = ?";

            // SQL-запрос для обновления статуса гражданина на 'active'
            String sqlUpdateCitizenStatus = "UPDATE citizens SET status = 'active' WHERE citizen_id = ?";

            try (
                    PreparedStatement stmtGetCitizenId = con.prepareStatement(sqlGetCitizenId);
                    PreparedStatement stmtDeleteDeathRegistration = con.prepareStatement(sqlDeleteDeathRegistration);
                    PreparedStatement stmtUpdateCitizenStatus = con.prepareStatement(sqlUpdateCitizenStatus)
            ) {
                // Получаем citizen_id из таблицы death_registration
                stmtGetCitizenId.setInt(1, deathId);
                try (ResultSet rs = stmtGetCitizenId.executeQuery()) {
                    if (rs.next()) {
                        int citizenId = rs.getInt("citizen_id");

                        // Удаление записи из таблицы death_registration
                        stmtDeleteDeathRegistration.setInt(1, deathId);
                        int rowsDeleted = stmtDeleteDeathRegistration.executeUpdate();
                        if (rowsDeleted > 0) {
                            // Обновление статуса гражданина на 'active'
                            stmtUpdateCitizenStatus.setInt(1, citizenId);
                            stmtUpdateCitizenStatus.executeUpdate();

                            System.out.println("Свидетельство о смерти удалено и статус гражданина обновлен на 'active'.");
                        } else {
                            System.out.println("Не найдено свидетельства для удаления.");
                        }
                    } else {
                        System.out.println("Не найдено гражданина для данного свидетельства о смерти.");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при удалении свидетельства о смерти: " + e.getMessage());
        }
    }
}
