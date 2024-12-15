import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorizedUser extends User {
    // Статический метод main с передачей citizenId
    protected static void main(int citizenId) {
        try {
            int x = 0;
            // создаем меню с пунктами
            while (true) {
                System.out.println("1. Посмотреть информацию о себе");
                System.out.println("2. Посмотреть свидетельство о рождении");
                System.out.println("3. Посмотреть свидетельство о заключении брака");
                System.out.println("4. Посмотреть свидетельство о расторжении брака");
                System.out.println("5. Посмотреть свидетельство о перемене имени");
                System.out.println("6. Выход");

                // Используем метод getIntInput для получения корректного ввода
                x = getIntInput("Выберите пункт меню: ");

                switch (x) {
                    case 1:
                        // Получаем и показываем информацию о себе
                        showPersonalInfo(citizenId);
                        break;
                    case 2:
                        // Получаем и показываем свидетельство о рождении
                        showBirthCertificate(citizenId);
                        break;
                    case 3:
                        // Получаем и показываем свидетельство о браке
                        showMarriageCertificate(citizenId);
                        break;
                    case 4:
                        // Получаем и показываем свидетельство о расторжении брака
                        showDivorceCertificate(citizenId);
                        break;
                    case 5:
                        // Получаем и показываем свидетельство о перемене имени
                        showNameChangeCertificate(citizenId);
                        break;
                    case 6:
                        System.out.println("Выход из аккаунта.");
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

    // Метод для вывода информации о пользователе
    private static void showPersonalInfo(int citizenId) {
        try {
            String sql = "SELECT * FROM citizens WHERE citizen_id = ?";
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setInt(1, citizenId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        System.out.println("Информация о себе");

                        // Проверка и вывод имени
                        String firstName = rs.getString("first_name");
                        if (firstName != null) {
                            System.out.println("Имя: " + firstName);
                        }

                        // Проверка и вывод фамилии
                        String lastName = rs.getString("last_name");
                        if (lastName != null) {
                            System.out.println("Фамилия: " + lastName);
                        }

                        // Проверка и вывод отчества
                        String middleName = rs.getString("middle_name");
                        if (middleName != null) {
                            System.out.println("Отчество: " + middleName);
                        }

                        // Проверка и вывод пола
                        String gender = rs.getString("gender");
                        if (gender != null) {
                            System.out.println("Пол: " + gender);
                        }

                        // Проверка и вывод даты рождения
                        String birthDate = rs.getString("birth_date");
                        if (birthDate != null) {
                            System.out.println("Дата рождения: " + birthDate);
                        }

                        // Проверка и вывод места рождения
                        String birthPlace = rs.getString("birth_place");
                        if (birthPlace != null) {
                            System.out.println("Место рождения: " + birthPlace);
                        }

                        // Проверка и вывод адреса регистрации
                        String address = rs.getString("address");
                        if (address != null) {
                            System.out.println("Адрес регистрации: " + address);
                        }

                    } else {
                        System.out.println("Не удалось найти информацию о пользователе.");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при получении данных: " + e.getMessage());
        }
    }

    // Метод для получения свидетельства о рождении
    private static void showBirthCertificate(int citizenId) {
        try {
            // SQL-запрос для извлечения данных о свидетельствах о рождении
            String sql = "SELECT b.birth_id, b.birth_date, b.birth_place, b.registration_date, " +
                    "b.birth_first_name, b.birth_last_name, b.birth_middle_name, " +
                    "m.first_name AS mother_first_name, m.last_name AS mother_last_name, m.middle_name AS mother_middle_name, " +
                    "f.first_name AS father_first_name, f.last_name AS father_last_name, f.middle_name AS father_middle_name " +
                    "FROM birth_certificate b " +
                    "LEFT JOIN citizens m ON b.mother_id = m.citizen_id " +
                    "LEFT JOIN citizens f ON b.father_id = f.citizen_id " +
                    "WHERE b.citizen_id = ?";

            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setInt(1, citizenId); // Устанавливаем параметр для запроса

                try (ResultSet rs = stmt.executeQuery()) {
                    boolean found = false; // Флаг, чтобы проверить, были ли записи

                    while (rs.next()) {
                        found = true;
                        System.out.println("Свидетельство о рождении (номер): " + rs.getString("birth_id"));

                        // Вывод информации о ребенке
                        String childFirstName = rs.getString("birth_first_name");
                        String childLastName = rs.getString("birth_last_name");
                        String childMiddleName = rs.getString("birth_middle_name");

                        if (childFirstName != null) {
                            System.out.println("Имя ребенка: " + childFirstName);
                        }
                        if (childLastName != null) {
                            System.out.println("Фамилия ребенка: " + childLastName);
                        }
                        if (childMiddleName != null) {
                            System.out.println("Отчество ребенка: " + childMiddleName);
                        }

                        // Вывод информации о матери
                        String motherFirstName = rs.getString("mother_first_name");
                        String motherLastName = rs.getString("mother_last_name");
                        String motherMiddleName = rs.getString("mother_middle_name");

                        if (motherFirstName != null && motherLastName != null) {
                            System.out.println("Мать: " + motherFirstName + " " + motherLastName +
                                    (motherMiddleName != null ? " " + motherMiddleName : ""));
                        }

                        // Вывод информации об отце
                        String fatherFirstName = rs.getString("father_first_name");
                        String fatherLastName = rs.getString("father_last_name");
                        String fatherMiddleName = rs.getString("father_middle_name");

                        if (fatherFirstName != null && fatherLastName != null) {
                            System.out.println("Отец: " + fatherFirstName + " " + fatherLastName +
                                    (fatherMiddleName != null ? " " + fatherMiddleName : ""));
                        }

                        // Дата и место рождения
                        System.out.println("Дата рождения: " + rs.getString("birth_date"));
                        System.out.println("Место рождения: " + rs.getString("birth_place"));

                        // Дата регистрации свидетельства
                        System.out.println("Дата регистрации свидетельства о рождении: " + rs.getString("registration_date"));

                        // Разделитель для каждого свидетельства
                        System.out.println("---------------------------------------------------");
                    }

                    if (!found) {
                        System.out.println("Не найдено свидетельств о рождении для данного гражданина.");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при получении свидетельства о рождении: " + e.getMessage());
        }
    }

    // Метод для получения свидетельства о заключении брака
    private static void showMarriageCertificate(int citizenId) {
        try {
            String sql = "SELECT m.marriage_id, m.marriage_date, m.marriage_place, m.registration_date, m.status, " +
                    "w.first_name AS wife_first_name, w.last_name AS wife_last_name, w.middle_name AS wife_middle_name, " +
                    "h.first_name AS husband_first_name, h.last_name AS husband_last_name, h.middle_name AS husband_middle_name, " +
                    "m.marriage_last_name_wife, m.marriage_last_name_husband " +
                    "FROM marriage_registration m " +
                    "LEFT JOIN citizens w ON m.wife_id = w.citizen_id " +
                    "LEFT JOIN citizens h ON m.husband_id = h.citizen_id " +
                    "WHERE m.wife_id = ? OR m.husband_id = ?";

            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setInt(1, citizenId);
                stmt.setInt(2, citizenId);

                try (ResultSet rs = stmt.executeQuery()) {
                    boolean found = false; // Флаг, чтобы проверить, есть ли записи

                    while (rs.next()) {
                        found = true;

                        System.out.println("Свидетельство о браке (номер): " + rs.getString("marriage_id"));

                        // Дата и место заключения брака
                        System.out.println("Дата заключения брака: " + rs.getString("marriage_date"));
                        System.out.println("Место заключения брака: " + rs.getString("marriage_place"));

                        // Дата регистрации брака
                        System.out.println("Дата регистрации брака: " + rs.getString("registration_date"));

                        // Статус брака
                        System.out.println("Статус брака: " + rs.getString("status"));

                        // ФИО жены
                        String wifeFirstName = rs.getString("wife_first_name");
                        String wifeLastName = rs.getString("wife_last_name");
                        String wifeMiddleName = rs.getString("wife_middle_name");

                        if (wifeFirstName != null || wifeLastName != null) {
                            System.out.println("Жена: " + (wifeFirstName != null ? wifeFirstName : "") + " " +
                                    (wifeLastName != null ? wifeLastName : "") +
                                    (wifeMiddleName != null ? " " + wifeMiddleName : ""));
                        }

                        // ФИО мужа
                        String husbandFirstName = rs.getString("husband_first_name");
                        String husbandLastName = rs.getString("husband_last_name");
                        String husbandMiddleName = rs.getString("husband_middle_name");

                        if (husbandFirstName != null || husbandLastName != null) {
                            System.out.println("Муж: " + (husbandFirstName != null ? husbandFirstName : "") + " " +
                                    (husbandLastName != null ? husbandLastName : "") +
                                    (husbandMiddleName != null ? " " + husbandMiddleName : ""));
                        }

                        // Фамилия жены после брака
                        String marriageLastNameWife = rs.getString("marriage_last_name_wife");
                        if (marriageLastNameWife != null) {
                            System.out.println("Фамилия жены после брака: " + marriageLastNameWife);
                        }

                        // Фамилия мужа после брака
                        String marriageLastNameHusband = rs.getString("marriage_last_name_husband");
                        if (marriageLastNameHusband != null) {
                            System.out.println("Фамилия мужа после брака: " + marriageLastNameHusband);
                        }

                        // Разделитель для каждого свидетельства о браке
                        System.out.println("---------------------------------------------------");
                    }

                    if (!found) {
                        System.out.println("Не найдено свидетельств о браке для данного гражданина.");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при получении свидетельства о браке: " + e.getMessage());
        }
    }

    // Метод для получения свидетельства о расторжении брака
    private static void showDivorceCertificate(int citizenId) {
        try {
            // SQL-запрос для получения всех разводов для данного гражданина
            String sql = "SELECT d.divorce_id, d.divorce_date, m.marriage_date " +
                    "FROM divorce_registration d " +
                    "JOIN marriage_registration m ON d.marriage_id = m.marriage_id " +
                    "WHERE m.wife_id = ? OR m.husband_id = ?";

            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setInt(1, citizenId);
                stmt.setInt(2, citizenId);

                try (ResultSet rs = stmt.executeQuery()) {
                    boolean found = false; // Флаг для проверки, были ли найдены записи

                    while (rs.next()) {
                        found = true;
                        System.out.println("Свидетельство о разводе (номер): " + rs.getString("divorce_id"));

                        // Дата расторжения брака
                        System.out.println("Дата расторжения брака: " + rs.getString("divorce_date"));

                        // Дата заключения брака
                        System.out.println("Дата заключения брака: " + rs.getString("marriage_date"));

                        // Разделитель для каждого свидетельства
                        System.out.println("---------------------------------------------------");
                    }

                    if (!found) {
                        System.out.println("Не найдено свидетельств о разводе для данного гражданина.");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при получении свидетельства о разводе: " + e.getMessage());
        }
    }

    // Метод для получения свидетельства о перемене имени
    private static void showNameChangeCertificate(int citizenId) {
        try {
            // SQL-запрос для получения всех свидетельств о перемене имени
            String sql = "SELECT * FROM name_change WHERE citizen_id = ?";

            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setInt(1, citizenId);

                try (ResultSet rs = stmt.executeQuery()) {
                    boolean found = false; // Флаг для проверки, были ли найдены записи

                    while (rs.next()) {
                        found = true;
                        System.out.println("Свидетельство о перемене имени (номер): " + rs.getString("name_change_id"));
                        System.out.println("Дата изменения имени: " + rs.getString("change_date"));
                        System.out.println("Старое имя: " + rs.getString("old_name"));
                        System.out.println("Новое имя: " + rs.getString("new_name"));

                        // Разделитель для каждого свидетельства
                        System.out.println("---------------------------------------------------");
                    }

                    if (!found) {
                        System.out.println("Не найдено свидетельств о перемене имени для данного гражданина.");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при получении свидетельства о перемене имени: " + e.getMessage());
        }
    }
}
