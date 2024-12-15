import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DivorceRegistration extends AuthorizedEmployee {
    protected static void main() {
        try {
            int x = 0;
            // создаем меню с пунктами
            while (true) {
                System.out.println("1. Добавить свидетельство о расторжении брака");
                System.out.println("2. Удалить свидетельство о расторжении брака");
                System.out.println("3. Выход");

                // Используем метод getIntInput для получения корректного ввода
                x = getIntInput("Выберите пункт меню: ");

                // Переход к другим методам в зависимости от выбранного пункта пользователем
                switch (x) {
                    case 1:
                        addDivorceRegistration(); // Добавить свидетельство
                        break;
                    case 2:
                        deleteDivorceRegistration(); // Удалить свидетельство
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

    // Метод для добавления свидетельства о расторжении брака
    private static void addDivorceRegistration() {
        try {
            // Запрос на добавление нового свидетельства о расторжении брака
            int marriageId = getIntInput("Введите ID брака для расторжения:");
            System.out.println("Введите дату расторжения брака (формат YYYY-MM-DD):");
            String divorceDateInput = scan.nextLine();

            // Преобразование строки в дату
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date parsedDate = dateFormat.parse(divorceDateInput);
            Date divorceDate = new Date(parsedDate.getTime()); // Преобразуем в java.sql.Date

            // Получение сегодняшней даты для регистрации
            Date registrationDate = new Date(System.currentTimeMillis()); // Текущая дата

            // SQL-запрос для добавления нового свидетельства о расторжении брака
            String sqlDivorceRegistration = "INSERT INTO divorce_registration (marriage_id, divorce_date, registration_date, status) " +
                    "VALUES (?, ?, ?, ?)";

            // SQL-запрос для обновления статуса брака на inactive в таблице marriage_registration
            String sqlUpdateMarriageStatus = "UPDATE marriage_registration SET status = 'inactive' WHERE marriage_id = ?";

            try (PreparedStatement stmtDivorceRegistration = con.prepareStatement(sqlDivorceRegistration);
                 PreparedStatement stmtUpdateMarriageStatus = con.prepareStatement(sqlUpdateMarriageStatus)) {

                // Вставка в таблицу divorce_registration
                stmtDivorceRegistration.setInt(1, marriageId);
                stmtDivorceRegistration.setDate(2, divorceDate);
                stmtDivorceRegistration.setDate(3, registrationDate);
                stmtDivorceRegistration.setString(4, "active");
                stmtDivorceRegistration.executeUpdate();

                // Обновление статуса брака на inactive в таблице marriage_registration
                stmtUpdateMarriageStatus.setInt(1, marriageId);
                stmtUpdateMarriageStatus.executeUpdate();

                System.out.println("Свидетельство о расторжении брака добавлено и статус брака изменен на inactive.");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при добавлении свидетельства о расторжении брака: " + e.getMessage());
        } catch (ParseException e) {
            System.out.println("Ошибка при преобразовании даты: " + e.getMessage());
        }
    }

    // Метод для удаления свидетельства о расторжении брака
    private static void deleteDivorceRegistration() {
        try {
            // Запрос на удаление свидетельства о расторжении брака
            int divorceId = getIntInput("Введите ID свидетельства о расторжении брака для удаления:");

            // SQL-запрос для получения marriage_id по divorce_id
            String sqlGetMarriageId = "SELECT marriage_id FROM divorce_registration WHERE divorce_id = ?";

            // SQL-запрос для обновления статуса брака на active в таблице marriage_registration
            String sqlUpdateMarriageStatus = "UPDATE marriage_registration SET status = 'active' WHERE marriage_id = ?";

            // SQL-запрос для удаления свидетельства о расторжении брака
            String sqlDeleteDivorce = "DELETE FROM divorce_registration WHERE divorce_id = ?";

            try (
                    PreparedStatement stmtGetMarriageId = con.prepareStatement(sqlGetMarriageId);
                    PreparedStatement stmtUpdateMarriageStatus = con.prepareStatement(sqlUpdateMarriageStatus);
                    PreparedStatement stmtDeleteDivorce = con.prepareStatement(sqlDeleteDivorce)
            ) {
                // Получаем marriage_id по divorce_id
                stmtGetMarriageId.setInt(1, divorceId);
                try (ResultSet rs = stmtGetMarriageId.executeQuery()) {
                    if (rs.next()) {
                        int marriageId = rs.getInt("marriage_id");

                        // Обновление статуса брака на active в таблице marriage_registration
                        stmtUpdateMarriageStatus.setInt(1, marriageId);
                        stmtUpdateMarriageStatus.executeUpdate();

                        // Удаление свидетельства о расторжении брака
                        stmtDeleteDivorce.setInt(1, divorceId);
                        int rowsDeleted = stmtDeleteDivorce.executeUpdate();
                        if (rowsDeleted > 0) {
                            System.out.println("Свидетельство о расторжении брака удалено и статус брака возвращен на active.");
                        } else {
                            System.out.println("Не найдено свидетельства для удаления.");
                        }
                    } else {
                        System.out.println("Не найдено брака для данного свидетельства о разводе.");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при удалении свидетельства о расторжении брака: " + e.getMessage());
        }
    }
}
