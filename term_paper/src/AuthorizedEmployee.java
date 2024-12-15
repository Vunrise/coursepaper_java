public class AuthorizedEmployee extends Employee {
    protected static void main() {
        try {
            int x = 0;
            // создаем меню с пунктами
            while (true) {
                System.out.println("1. Действия со свидетельством о рождении");
                System.out.println("2. Действия со свидетельством о заключении брака");
                System.out.println("3. Действия со свидетельством о расторжении брака");
                System.out.println("4. Действия со свидетельством о смерти");
                System.out.println("5. Действия со свидетельством о перемене имени");
                System.out.println("6. Статистика");
                System.out.println("7. Выход");

                // Используем метод getIntInput для получения корректного ввода
                x = getIntInput("Выберите пункт меню: ");

                // Переход к другим классам-наследникам в зависимости от выбранного пункта пользователем
                switch (x) {
                    case 1:
                        BirthCertificate.main();
                        break;
                    case 2:
                        MarriageRegistration.main();
                        break;
                    case 3:
                        DivorceRegistration.main();
                        break;
                    case 4:
                        DeathRegistration.main();
                        break;
                    case 5:
                        NameChange.main();
                        break;
                    case 6:
                        Statistics.main();
                        break;
                    case 7:
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
}
