import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.Scanner;

public class Employee extends Main {
    // Статический метод main
    protected static void main() {
        try {
            // Запрашиваем имя пользователя
            System.out.print("Введите логин сотрудника: ");
            String username = scan.nextLine();

            // Запрашиваем пароль
            System.out.print("Введите пароль: ");
            String password = scan.nextLine();

            // SQL-запрос для проверки наличия пользователя и пароля
            String sql = "SELECT password FROM " + tablename_employees + " WHERE username = ? LIMIT 1";
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                // Устанавливаем параметры: имя пользователя
                stmt.setString(1, username);

                // Выполняем запрос и получаем результат
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        // Если запись найдена, получаем пароль из базы данных
                        String dbPassword = rs.getString("password");

                        // Сравниваем введенный пароль с паролем в базе данных
                        if (password.equals(dbPassword)) {
                            // Если пароли совпали
                            System.out.println("Вы вошли как сотрудник.");
                            // Передаем citizenId в метод AuthorizedUser.main()
                            AuthorizedEmployee.main();
                        } else {
                            // Если пароли не совпали
                            System.out.println("Неверный пароль.");
                        }
                    } else {
                        // Если записи с таким пользователем не найдено
                        System.out.println("Сотрудник не найден.");
                    }
                }
            } catch (SQLException e) {
                System.out.println("Ошибка при выполнении запроса: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Произошла ошибка: " + e.getMessage());
        }
    }
}
