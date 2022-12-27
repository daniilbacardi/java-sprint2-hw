import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.ArrayList;

public class YearlyReport {
    ArrayList<YearlyRecord> yearlyRecords;
    HashMap<Integer, Integer> gainRecord;
    HashMap<Integer, Integer> costRecord;
    MonthlyReport monthlyReport = new MonthlyReport();
    String[] yearTitle = {"2021"};

    public YearlyReport() {
        yearlyRecords = new ArrayList<>();
        gainRecord = new HashMap<>();
        costRecord = new HashMap<>();
    }

    public void readYearlyReport() {
        String yearRecord = readFileContentsOrNull();
        String[] lines = yearRecord.split("\r?\n");
        for (int i = 1; i < lines.length; i++) {
            String line = lines[i];
            String[] parts = line.split(",");
            int month = Integer.parseInt(parts[0]);
            int amount = Integer.parseInt(parts[1]);
            boolean isExpense = Boolean.parseBoolean(parts[2]);
            YearlyRecord yearlyRecord = new YearlyRecord(month, amount, isExpense);
            yearlyRecords.add(yearlyRecord);
        }
    }

    public void readYearlyStatistic() {
        for (YearlyRecord record : yearlyRecords) {
            if (record.isExpense) {
                costRecord.put(record.month, record.amount);
            } else {
                gainRecord.put(record.month, record.amount);
            }
            if (costRecord.get(record.month) != null && gainRecord.get(record.month) != null) {
                int profitOfTheMonth;
                profitOfTheMonth = gainRecord.get(record.month) - costRecord.get(record.month);
                if (profitOfTheMonth < 0) {
                    System.out.println("Убыток за " + monthlyReport.monthTitle[record.month - 1] + " месяц составил: "
                            + profitOfTheMonth + " руб.");
                } else {
                    System.out.println("Прибыль за " + monthlyReport.monthTitle[record.month - 1] + " месяц составила: "
                            + profitOfTheMonth + " руб.");
                }
            }
        }
    }


    public void getAverageAmounts() {
        int sumIncome = 0;
        for (Integer record : gainRecord.values()) {
            sumIncome += record;
        }
        int avgIncome = sumIncome / gainRecord.size();
        System.out.println("Средняя прибыль за год составила: " + avgIncome + " руб.");

        int sumExpense = 0;
        for (Integer record : costRecord.values()) {
            sumExpense += record;
        }
        int avgExpense = sumExpense / costRecord.size();
        System.out.println("Средний расход за год составил: " + avgExpense + " руб.\n ");
    }

    public void downloadYearlyReports() {
        if (yearlyRecords.isEmpty()) {
            readYearlyReport();
            System.out.println("Годовой отчет успешно загружен\n ");
        } else {
            System.out.println("Данные по годовому отчету уже имеются. Повторная загрузка не требуется.\n ");
        }
    }

    public void printYearlyStatistic() {
        if (!yearlyRecords.isEmpty()) {
            System.out.println("Отчет за " + yearTitle[0] + " год:");
            readYearlyStatistic();
            getAverageAmounts();
        } else {
            System.out.println("Для вывода статистики необходимо загрузить годовой отчет.\n ");
        }
    }
    public boolean isYearlyReportsExists(){
        return yearlyRecords.isEmpty();
    }

    private String readFileContentsOrNull() {
        try {
            return Files.readString(Path.of("resources/y.2021.csv"));
        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл с отчётом. Возможно, файл не находится в нужной директории.\n ");
            return null;
        }
    }
}

