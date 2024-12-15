import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

public class Main {
    // объявляем переменные для классов-наследников
    protected static Scanner scan = new Scanner(System.in);
    protected static String sqlUrl = "jdbc:postgresql://localhost:5432/coursepaper";
    protected static Connection con;
    protected static String tablename_users = "users";
    protected static String tablename_employees = "employees";

    // подключаемся к бд
    static {
        try {
            con = DriverManager.getConnection(sqlUrl, "postgres", "0000");
            System.out.println("Connected to database");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    // Метод для получения правильного числового ввода
    protected static int getIntInput(String prompt) {
        int x = 0;
        while (true) {
            System.out.println(prompt);  // Выводим приглашение для ввода
            String s = scan.nextLine().trim();

            // Проверяем, что введенная строка не пустая
            if (s.isEmpty()) {
                System.out.println("Неверный формат ввода");
                continue;
            }

            // Пытаемся преобразовать строку в число
            try {
                x = Integer.parseInt(s);
                break;  // Выход из цикла, если ввод корректен
            } catch (NumberFormatException e) {
                System.out.println("Неверный формат ввода. Введите число.");
            }
        }
        return x;
    }

    public static void main(String[] args) throws SQLException, IOException {
        try {
            int x = 0;
            // создаем меню с пунктами
            while (true) {
                System.out.println("1. Войти как пользователь");
                System.out.println("2. Войти как сотрудник");
                System.out.println("3. Об авторе");
                System.out.println("4. Выход");

                // Используем метод getIntInput для получения корректного ввода
                x = getIntInput("Выберите пункт меню: ");

                // переход к другим классам-наследникам в зависимости от выбранного пункта пользователем
                switch (x) {
                    case 1:
                        User.main();
                        break;
                    case 2:
                        Employee.main();
                        break;
                    case 3:
                        System.out.println("Семеркова Виктория Сергеевна, ДПИ22-2.");
                        break;
                    case 4:
                        System.out.println("Выход из программы.");
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
}
