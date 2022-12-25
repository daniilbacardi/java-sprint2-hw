import java.util.Scanner;

public class Main {

    public static YearlyReport yearlyReport = new YearlyReport("resources/y.2021.csv");
    public static MonthlyReport[] monthlyReport = new MonthlyReport[3];

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        for (int i = 0; i < monthlyReport.length; i++) {
            monthlyReport[i] = new MonthlyReport("resources/m.20210" + (i + 1) + ".csv");
        }

        System.out.println("«Автоматизация бухгалтерии»");
        System.out.println(" ");

        while (true) {
            printMenu();
            int userInput = scanner.nextInt();
            if (userInput == 1) {
                readMonthlyReport();
            } else if (userInput == 2) {
                readYearlyReport();
            } else if (userInput == 3) {
                compareMonthlyAndYearlyReports();
            } else if (userInput == 4) {
                printMonthlyReports();
            } else if (userInput == 5) {
                yearlyReport.printYearlyReport();
             } else if (userInput == 0) {
                System.out.println("Вы вышли из программы");
                break;
            } else {
                System.out.println("Извините, такой команды пока нет.");
            }
        }
    }

    public static void printMenu() {
        System.out.println("Выберите действие:");
        System.out.println("1. Считать все месячные отчёты");
        System.out.println("2. Считать годовой отчёт");
        System.out.println("3. Сверить отчёты");
        System.out.println("4. Вывести информацию о всех месячных отчётах");
        System.out.println("5. Вывести информацию о годовом отчёте");
        System.out.println("0. Выход");
    }

    private static void readYearlyReport() {
        if (!yearlyReport.data.isEmpty()) {
            System.out.println("Годовой отчет считан.");
        } else {
            System.out.println("Годовой отчет пустой");
        }
    }

    private static void readMonthlyReport() {
        for (int i = 0; i < monthlyReport.length; i++) {
            if (!monthlyReport[i].data.isEmpty()) {
                System.out.println("Месячный отчет " + (i + 1) + " считан");
            } else {
                System.out.println("Месячный отчет пустой");
            }
        }
    }

    private static void compareMonthlyAndYearlyReports() {
        for (int i = 0; i < monthlyReport.length; i++) {
            if (!monthlyReport[i].data.isEmpty() || !yearlyReport.data.isEmpty()) {
                if (monthlyReport[i].sumCosts() == yearlyReport.sumCostsMonth(i)) {
                    System.out.println("Расходы по месяцу " + (i + 1) + " совпадают.");
                    System.out.println("Сумма: " + monthlyReport[i].sumCosts());
                } else {
                    System.out.println("Расходы по месяцу " + (i + 1) + " не совпадают!!!");
                }
                if (monthlyReport[i].sumGain() == yearlyReport.sumGainMonth(i)) {
                    System.out.println("Доходы по месяцу " + (i + 1) + " совпадают.");
                    System.out.println("Сумма: " + monthlyReport[i].sumGain());
                    System.out.println(" ");
                } else {
                    System.out.println("Доходы по месяцу " + (i + 1) + " не совпадают!!!");
                    System.out.println(" ");
                }
            } else {
                System.out.println("Отчет не считан, выполнить сверку не возможно.");
                break;
            }
        }
    }

    private static void printMonthlyReports() {
        for (int i = 0; i < monthlyReport.length; i++) {
            System.out.println("Отчет за месяц " + (i + 1));
            monthlyReport[i].printMonthlyReport();
            System.out.println(" ");
        }
    }
}
