import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Statistics extends AuthorizedEmployee {
    protected static void main() {
        try {
            int x = 0;
            // создаем меню с пунктами
            while (true) {
                System.out.println("1. Количество детей, родившихся за год");
                System.out.println("2. Количество людей, умерших за год");
                System.out.println("3. Количество браков за год");
                System.out.println("4. Количество разводов за год");
                System.out.println("5. Выход");

                // Используем метод getIntInput для получения корректного ввода
                x = getIntInput("Выберите пункт меню: ");

                // Переход к другим методам в зависимости от выбранного пункта пользователем
                switch (x) {
                    case 1:
                        countBirth();
                        break;
                    case 2:
                        countDeath();
                        break;
                    case 3:
                        countMarriages();
                        break;
                    case 4:
                        countDivorces();
                        break;
                    case 5:
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

    // Метод для получения года с проверкой ввода
    private static int getYearInput(String prompt) {
        int year = 0;
        while (true) {
            System.out.println(prompt);
            System.out.println("1. Текущий год");
            System.out.println("2. Ввести свой год");

            int choice = getIntInput("Выберите пункт: ");

            if (choice == 1) {
                year = java.time.Year.now().getValue(); // Получаем текущий год
                break;
            } else if (choice == 2) {
                year = getIntInput("Введите год (например, 2023): ");
                break;
            } else {
                System.out.println("Неверный выбор, пожалуйста, выберите 1 или 2.");
            }
        }
        return year;
    }

    // Метод для подсчета количества детей, родившихся за год
    private static void countBirth() {
        try {
            // Получаем год для фильтрации
            int year = getYearInput("Выберите год для подсчета количества детей, родившихся:");

            // Формируем SQL-запрос для подсчета количества детей, родившихся в выбранный год
            String sql = "SELECT COUNT(*) AS birth_count FROM birth_certificate " +
                    "WHERE EXTRACT(YEAR FROM birth_date) = ?";

            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setInt(1, year); // Подставляем выбранный год

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        int count = rs.getInt("birth_count");
                        System.out.println("Количество детей, родившихся в " + year + " году: " + count);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при подсчете количества детей: " + e.getMessage());
        }
    }

    // Метод для подсчета количества людей, умерших за год
    private static void countDeath() {
        try {
            // Получаем год для фильтрации
            int year = getYearInput("Выберите год для подсчета количества людей, умерших:");

            // Формируем SQL-запрос для подсчета количества людей, умерших в выбранный год
            String sql = "SELECT COUNT(*) AS death_count FROM death_registration " +
                    "WHERE EXTRACT(YEAR FROM death_date) = ?";

            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setInt(1, year); // Подставляем выбранный год

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        int count = rs.getInt("death_count");
                        System.out.println("Количество людей, умерших в " + year + " году: " + count);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при подсчете количества умерших: " + e.getMessage());
        }
    }

    // Метод для подсчета количества браков за год
    private static void countMarriages() {
        try {
            // Получаем год для фильтрации
            int year = getYearInput("Выберите год для подсчета количества браков:");

            // Формируем SQL-запрос для подсчета количества браков, заключенных в выбранный год
            String sql = "SELECT COUNT(*) AS marriage_count FROM marriage_registration " +
                    "WHERE EXTRACT(YEAR FROM marriage_date) = ?";

            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setInt(1, year); // Подставляем выбранный год

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        int count = rs.getInt("marriage_count");
                        System.out.println("Количество браков, заключенных в " + year + " году: " + count);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при подсчете количества браков: " + e.getMessage());
        }
    }

    // Метод для подсчета количества разводов за год
    private static void countDivorces() {
        try {
            // Получаем год для фильтрации
            int year = getYearInput("Выберите год для подсчета количества разводов:");

            // Формируем SQL-запрос для подсчета количества разводов, совершенных в выбранный год
            String sql = "SELECT COUNT(*) AS divorce_count FROM divorce_registration " +
                    "WHERE EXTRACT(YEAR FROM divorce_date) = ?";

            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setInt(1, year); // Подставляем выбранный год

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        int count = rs.getInt("divorce_count");
                        System.out.println("Количество разводов, совершенных в " + year + " году: " + count);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при подсчете количества разводов: " + e.getMessage());
        }
    }
}
