import java.util.Scanner;

public class Main {

    public static MonthlyReport monthlyReport = new MonthlyReport();
    public static YearlyReport yearlyReport = new YearlyReport();

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("«Автоматизация бухгалтерии»");
        System.out.println("Для работы необходимо загрузить отчеты");
        System.out.println(" ");

        while (true) {
            printMenu();
            int userInput = scanner.nextInt();
            if (userInput == 1) {
                monthlyReport.downloadMonthlyReports();
            } else if (userInput == 2) {
                yearlyReport.downloadYearlyReports();
            } else if (userInput == 3) {
                checkingReportsExists();
            } else if (userInput == 4) {
                monthlyReport.printMonthlyStatistic();
            } else if (userInput == 5) {
                yearlyReport.printYearlyStatistic();
            } else if (userInput == 0) {
                System.out.println("Вы вышли из программы");
                break;
            } else {
                System.out.println("Извините, такой команды пока нет.\n ");
            }
        }
    }
        public static void checkingReportsExists() {
            if (yearlyReport.isYearlyReportsExists() && monthlyReport.monthlyRecords.isEmpty()) {
                System.out.println("Для сверки необходимо загрузить годовой и месячные отчеты\n ");
            } else if (yearlyReport.isYearlyReportsExists()) {
                System.out.println("Без годового отчета сверку выполнить нельзя. Загрузите его.\n ");
            } else if (monthlyReport.monthlyRecords.isEmpty()) {
                System.out.println("Без месячных отчетов сверку выполнить нельзя. Загрузите их.\n ");
            } else {
                reportsCompare();
            }
        }
        public static void reportsCompare() {
            boolean isValid = true;
            for (YearlyRecord yearlyRecord : yearlyReport.yearlyRecords) {
                if (yearlyRecord.IS_EXPENSE) {
                    if (monthlyReport.getSumGainOrCostForMonth(yearlyRecord.MONTH, true)
                            != yearlyRecord.AMOUNT) {
                        System.out.println("Убыток за " + monthlyReport.monthTitle[yearlyRecord.MONTH - 1] +
                                " несоответствует убытку за тот же месяц в годовом отчете\n ");
                        isValid = false;
                    }
                } else {
                    if (monthlyReport.getSumGainOrCostForMonth(yearlyRecord.MONTH, false)
                            != yearlyRecord.AMOUNT) {
                        System.out.println("Доход за " + monthlyReport.monthTitle[yearlyRecord.MONTH - 1] +
                                " несоответствует доходу за тот же месяц в годовом отчете\n ");
                        isValid = false;
                    }
                }
            } if (isValid) {
                System.out.println("Сверка отчетов успешно завершена. Расхождений не выявлено.\n ");
            }
        }

        public static void printMenu() {
            System.out.println("Выберите действие - введите цифру:");
            System.out.println("1. Загрузить все месячные отчёты");
            System.out.println("2. Загрузить годовой отчёт");
            System.out.println("3. Сверить загруженные отчёты");
            System.out.println("4. Вывести статистику по всем месячным отчётам");
            System.out.println("5. Вывести статистику по годовому отчёту");
            System.out.println("0. Выйти из приложения");
        }
    }

