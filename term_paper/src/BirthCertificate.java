import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class BirthCertificate extends AuthorizedEmployee {
    protected static void main() {
        try {
            int x = 0;
            // создаем меню с пунктами
            while (true) {
                System.out.println("1. Добавить свидетельство о рождении");
                System.out.println("2. Изменить свидетельство о рождении");
                System.out.println("3. Удалить свидетельство о рождении");
                System.out.println("4. Фильтрация записей");
                System.out.println("5. Сортировка записей");
                System.out.println("6. Выход");

                // Используем метод getIntInput для получения корректного ввода
                x = getIntInput("Выберите пункт меню: ");

                // Переход к другим методам в зависимости от выбранного пункта пользователем
                switch (x) {
                    case 1:
                        addBirthCertificate(); // Добавить свидетельство
                        break;
                    case 2:
                        updateBirthCertificate(); // Изменить свидетельство
                        break;
                    case 3:
                        deleteBirthCertificate(); // Удалить свидетельство
                        break;
                    case 4:
                        filter();
                        break;
                    case 5:
                        sort();
                        break;
                    case 6:
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

    // Метод для добавления свидетельства о рождении
    private static void addBirthCertificate() {
        try {
            // Запрос на добавление нового свидетельства о рождении
            System.out.println("Введите данные для нового свидетельства о рождении:");

            // Считывание данных
            System.out.print("Дата рождения (формат YYYY-MM-DD): ");
            String birthDateInput = scan.nextLine();

            // Преобразование строки в дату
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date parsedDate = dateFormat.parse(birthDateInput);
            Date birthDate = new Date(parsedDate.getTime()); // Преобразуем в java.sql.Date

            System.out.print("Место рождения: ");
            String birthPlace = scan.nextLine();

            // Получение сегодняшней даты для регистрации
            Date registrationDate = new Date(System.currentTimeMillis()); // Текущая дата

            // Считывание данных о родителях (если нужны)
            System.out.print("ID матери (можно оставить пустым): ");
            String motherIdInput = scan.nextLine();
            Integer motherId = motherIdInput.isEmpty() ? null : Integer.parseInt(motherIdInput);

            System.out.print("ID отца (можно оставить пустым): ");
            String fatherIdInput = scan.nextLine();
            Integer fatherId = fatherIdInput.isEmpty() ? null : Integer.parseInt(fatherIdInput);

            // Считывание ФИО ребенка
            System.out.print("Имя ребенка: ");
            String birthFirstName = scan.nextLine();

            System.out.print("Фамилия ребенка: ");
            String birthLastName = scan.nextLine();

            System.out.print("Отчество ребенка (можно оставить пустым): ");
            String birthMiddleName = scan.nextLine();

            // SQL-запрос для добавления нового свидетельства
            String sql = "INSERT INTO birth_certificate (birth_date, birth_place, registration_date, " +
                    "mother_id, father_id, birth_first_name, birth_last_name, birth_middle_name, status) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setDate(1, birthDate); // birth_date
                stmt.setString(2, birthPlace); // birth_place
                stmt.setDate(3, registrationDate); // registration_date
                stmt.setObject(4, motherId); // mother_id (может быть null)
                stmt.setObject(5, fatherId); // father_id (может быть null)
                stmt.setString(6, birthFirstName); // birth_first_name
                stmt.setString(7, birthLastName); // birth_last_name
                stmt.setString(8, birthMiddleName); // birth_middle_name
                stmt.setString(9, "active");

                // Выполнение запроса
                stmt.executeUpdate();
                System.out.println("Свидетельство о рождении добавлено.");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при добавлении свидетельства о рождении: " + e.getMessage());
        } catch (ParseException e) {
            System.out.println("Ошибка при преобразовании даты: " + e.getMessage());
        }
    }

    // Метод для изменения свидетельства о рождении
    private static void updateBirthCertificate() {
        try {
            // Запрос на изменение существующего свидетельства о рождении
            int certificateId = getIntInput("Введите ID свидетельства о рождении: ");
            System.out.println("Введите новую дату рождения (формат YYYY-MM-DD):");
            String birthDateInput = scan.nextLine();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date parsedDate = dateFormat.parse(birthDateInput);
            Date newBirthDate = new Date(parsedDate.getTime());
            System.out.println("Введите новое место рождения:");
            String newBirthPlace = scan.nextLine();

            String sql = "UPDATE birth_certificate SET birth_date = ?, birth_place = ? WHERE birth_id = ?";
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setDate(1, newBirthDate);
                stmt.setString(2, newBirthPlace);
                stmt.setInt(3, certificateId);
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Свидетельство о рождении обновлено.");
                } else {
                    System.out.println("Не найдено свидетельства для обновления.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при изменении свидетельства о рождении: " + e.getMessage());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    // Метод для удаления свидетельства о рождении
    private static void deleteBirthCertificate() {
        try {
            // Запрос на удаление свидетельства о рождении
            int certificateId = getIntInput("Введите ID свидетельства о рождении: ");

            String sql = "DELETE FROM birth_certificate WHERE birth_id = ?";
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setInt(1, certificateId);
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Свидетельство о рождении удалено.");
                } else {
                    System.out.println("Не найдено свидетельства для удаления.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при удалении свидетельства о рождении: " + e.getMessage());
        }
    }

    // Метод для фильтрации записей
    private static void filter() {
        try {
            // Формируем строку с фильтрами
            StringBuilder filterQuery = new StringBuilder("WHERE 1=1");

            // Получаем данные для фильтрации
            System.out.print("Введите имя ребенка (или оставьте пустым для пропуска): ");
            String firstName = scan.nextLine().trim();
            if (!firstName.isEmpty()) {
                filterQuery.append(" AND birth_first_name ILIKE '%").append(firstName).append("%'");
            }

            System.out.print("Введите фамилию ребенка (или оставьте пустым для пропуска): ");
            String lastName = scan.nextLine().trim();
            if (!lastName.isEmpty()) {
                filterQuery.append(" AND birth_last_name ILIKE '%").append(lastName).append("%'");
            }

            System.out.print("Введите минимальную дату рождения (формат YYYY-MM-DD, или оставьте пустым для пропуска): ");
            String birthDateMin = scan.nextLine().trim();
            if (!birthDateMin.isEmpty()) {
                filterQuery.append(" AND birth_date >= '").append(birthDateMin).append("'");
            }

            System.out.print("Введите максимальную дату рождения (формат YYYY-MM-DD, или оставьте пустым для пропуска): ");
            String birthDateMax = scan.nextLine().trim();
            if (!birthDateMax.isEmpty()) {
                filterQuery.append(" AND birth_date <= '").append(birthDateMax).append("'");
            }

            // Формируем итоговый запрос с фильтрами
            String sql = "SELECT * FROM birth_certificate " + filterQuery.toString();

            try (PreparedStatement stmt = con.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {

                boolean hasResults = false;

                // Выводим результаты
                while (rs.next()) {
                    hasResults = true;
                    int birthId = rs.getInt("birth_id");
                    Date birthDate = rs.getDate("birth_date");
                    String birthPlace = rs.getString("birth_place");
                    String firstNameResult = rs.getString("birth_first_name");
                    String lastNameResult = rs.getString("birth_last_name");
                    String middleName = rs.getString("birth_middle_name");

                    System.out.println("ID: " + birthId + ", Имя: " + firstNameResult + ", Фамилия: " + lastNameResult +
                            ", Отчество: " + middleName + ", Дата рождения: " + birthDate + ", Место рождения: " + birthPlace);
                }

                if (!hasResults) {
                    System.out.println("Не найдено записей, удовлетворяющих критериям.");
                }

            }
        } catch (SQLException e) {
            System.out.println("Ошибка при фильтрации записей: " + e.getMessage());
        }
    }

    // Метод для сортировки записей
    private static void sort() {
        try {
            // Запрос на выбор столбца для сортировки
            System.out.println("Выберите столбец для сортировки:");
            System.out.println("1. Дата рождения");
            System.out.println("2. Место рождения");
            System.out.println("3. Имя ребенка");

            int sortColumn = getIntInput("Введите номер столбца для сортировки: ");
            String sortColumnName = "";
            switch (sortColumn) {
                case 1:
                    sortColumnName = "birth_date";
                    break;
                case 2:
                    sortColumnName = "birth_place";
                    break;
                case 3:
                    sortColumnName = "birth_first_name";
                    break;
                default:
                    System.out.println("Неверный выбор. Будет использован столбец по умолчанию (Дата рождения).");
                    sortColumnName = "birth_date";
                    break;
            }

            // Запрос на выбор порядка сортировки
            System.out.println("Выберите порядок сортировки:");
            System.out.println("1. По возрастанию");
            System.out.println("2. По убыванию");

            int sortOrder = getIntInput("Введите номер порядка сортировки: ");
            String order = (sortOrder == 1) ? "ASC" : "DESC";

            // Формируем SQL-запрос для сортировки
            String sql = "SELECT * FROM birth_certificate ORDER BY " + sortColumnName + " " + order;

            try (PreparedStatement stmt = con.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {

                boolean hasResults = false;

                // Выводим результаты
                while (rs.next()) {
                    hasResults = true;
                    int birthId = rs.getInt("birth_id");
                    Date birthDate = rs.getDate("birth_date");
                    String birthPlace = rs.getString("birth_place");
                    String firstName = rs.getString("birth_first_name");
                    String lastName = rs.getString("birth_last_name");
                    String middleName = rs.getString("birth_middle_name");

                    System.out.println("ID: " + birthId + ", Имя: " + firstName + ", Фамилия: " + lastName +
                            ", Отчество: " + middleName + ", Дата рождения: " + birthDate + ", Место рождения: " + birthPlace);
                }

                if (!hasResults) {
                    System.out.println("Не найдено записей для сортировки.");
                }

            }
        } catch (SQLException e) {
            System.out.println("Ошибка при сортировке записей: " + e.getMessage());
        }
    }
}
