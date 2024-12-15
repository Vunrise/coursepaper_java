import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.Scanner;

public class User extends Main {
    // Статический метод main
    protected static void main() {
        try {
            // Запрашиваем имя пользователя
            System.out.print("Введите имя пользователя: ");
            String username = scan.nextLine();

            // Запрашиваем пароль
            System.out.print("Введите пароль: ");
            String password = scan.nextLine();

            // SQL-запрос для проверки наличия пользователя и пароля
            String sql = "SELECT citizen_id, password FROM " + tablename_users + " WHERE username = ? LIMIT 1";
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                // Устанавливаем параметры: имя пользователя
                stmt.setString(1, username);

                // Выполняем запрос и получаем результат
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        // Если запись найдена, получаем пароль из базы данных
                        String dbPassword = rs.getString("password");
                        int citizenId = rs.getInt("citizen_id");

                        // Сравниваем введенный пароль с паролем в базе данных
                        if (password.equals(dbPassword)) {
                            // Если пароли совпали
                            System.out.println("Вы вошли как пользователь.");
                            // Передаем citizenId в метод AuthorizedUser.main()
                            AuthorizedUser.main(citizenId);
                        } else {
                            // Если пароли не совпали
                            System.out.println("Неверный пароль.");
                        }
                    } else {
                        // Если записи с таким пользователем не найдено
                        System.out.println("Пользователь не найден.");
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
