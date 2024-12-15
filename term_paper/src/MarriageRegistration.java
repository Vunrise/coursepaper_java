import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class MarriageRegistration extends AuthorizedEmployee {
    protected static void main() {
        try {
            int x = 0;
            // создаем меню с пунктами
            while (true) {
                System.out.println("1. Добавить свидетельство о заключении брака");
                System.out.println("2. Удалить свидетельство о заключении брака");
                System.out.println("3. Выход");

                // Используем метод getIntInput для получения корректного ввода
                x = getIntInput("Выберите пункт меню: ");

                // Переход к другим методам в зависимости от выбранного пункта пользователем
                switch (x) {
                    case 1:
                        addMarriageRegistration(); // Добавить свидетельство
                        break;
                    case 2:
                        deleteMarriageRegistration(); // Удалить свидетельство
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

    // Метод для добавления свидетельства о заключении брака
    private static void addMarriageRegistration() {
        try {
            System.out.println("Введите данные для нового свидетельства о заключении брака.");

            // Считывание данных
            System.out.print("Дата заключения брака (формат YYYY-MM-DD): ");
            String marriageDateInput = scan.nextLine();

            // Преобразование строки в дату
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date parsedDate = dateFormat.parse(marriageDateInput);
            Date marriageDate = new Date(parsedDate.getTime());

            System.out.print("Место заключения брака: ");
            String marriagePlace = scan.nextLine();

            // Считывание данных о супруге
            int wifeId = getIntInput("Введите ID жены:");
            System.out.print("Фамилия жены после брака: ");
            String wifeLastName = scan.nextLine();

            int husbandId = getIntInput("Введите ID мужа:");
            System.out.print("Фамилия мужа после брака: ");
            String husbandLastName = scan.nextLine();

            // Получение сегодняшней даты для регистрации
            Date registrationDate = new Date(System.currentTimeMillis()); // Текущая дата

            // SQL-запрос для добавления нового свидетельства о заключении брака
            String sqlMarriageRegistration = "INSERT INTO marriage_registration (wife_id, husband_id, marriage_date, marriage_place, marriage_last_name_wife, marriage_last_name_husband, status, registration_date) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            // SQL-запрос для обновления фамилий жены и мужа в таблице citizens
            String sqlUpdateWifeLastName = "UPDATE citizens SET last_name = ? WHERE citizen_id = ?";
            String sqlUpdateHusbandLastName = "UPDATE citizens SET last_name = ? WHERE citizen_id = ?";

            try (PreparedStatement stmtMarriageRegistration = con.prepareStatement(sqlMarriageRegistration);
                 PreparedStatement stmtUpdateWifeLastName = con.prepareStatement(sqlUpdateWifeLastName);
                 PreparedStatement stmtUpdateHusbandLastName = con.prepareStatement(sqlUpdateHusbandLastName)) {

                // Вставка в таблицу marriage_registration
                stmtMarriageRegistration.setInt(1, wifeId);
                stmtMarriageRegistration.setInt(2, husbandId);
                stmtMarriageRegistration.setDate(3, marriageDate);
                stmtMarriageRegistration.setString(4, marriagePlace);
                stmtMarriageRegistration.setString(5, wifeLastName);
                stmtMarriageRegistration.setString(6, husbandLastName);
                stmtMarriageRegistration.setString(7, "active");
                stmtMarriageRegistration.setDate(8, registrationDate);
                stmtMarriageRegistration.executeUpdate();

                // Обновление фамилии жены в таблице citizens
                stmtUpdateWifeLastName.setString(1, wifeLastName); // Новая фамилия жены
                stmtUpdateWifeLastName.setInt(2, wifeId); // ID жены
                stmtUpdateWifeLastName.executeUpdate();

                // Обновление фамилии мужа в таблице citizens
                stmtUpdateHusbandLastName.setString(1, husbandLastName); // Новая фамилия мужа
                stmtUpdateHusbandLastName.setInt(2, husbandId); // ID мужа
                stmtUpdateHusbandLastName.executeUpdate();

                System.out.println("Свидетельство о заключении брака добавлено и фамилии обновлены.");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при добавлении свидетельства о заключении брака или обновлении фамилий: " + e.getMessage());
        } catch (ParseException e) {
            System.out.println("Ошибка при преобразовании даты: " + e.getMessage());
        }
    }

    // Метод для удаления свидетельства о заключении брака
    private static void deleteMarriageRegistration() {
        try {
            // Запрос на удаление свидетельства о заключении брака
            int marriageId = getIntInput("Введите ID свидетельства о заключении брака для удаления: ");

            String sql = "DELETE FROM marriage_registration WHERE marriage_id = ?";

            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setInt(1, marriageId);

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Свидетельство о заключении брака удалено.");
                } else {
                    System.out.println("Не найдено свидетельства для удаления.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при удалении свидетельства о заключении брака: " + e.getMessage());
        }
    }
}
