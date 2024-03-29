import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.ArrayList;

public class MonthlyReport {
    HashMap<Integer, ArrayList<MonthlyRecord>> monthlyRecords = new HashMap<>();
    String[] monthTitle = {"январь", "февраль", "март", "апрель", "май", "июнь", "июль", "август", "сентябрь",
            "октябрь", "ноябрь", "декабрь"};

    void readMonthlyReports() {
        for (int month = 1; month <= 12; month++) {
            if (Files.exists(Path.of("resources/m.20210" + month + ".csv"))){
                ArrayList<MonthlyRecord> recordMonth = new ArrayList<>();
                String monthRecord = readFileContentsOrNull("resources/m.20210" + month + ".csv");
                String[] lines = monthRecord.split("\r?\n");
                for (int i = 1; i < lines.length; i++) {
                    String line = lines[i];
                    String[] parts = line.split(",");
                    String itemName = parts[0];
                    boolean isExpense = Boolean.parseBoolean(parts[1]);
                    int quantity = Integer.parseInt(parts[2]);
                    int sumOfOne = Integer.parseInt(parts[3]);
                    MonthlyRecord monthlyRecord = new MonthlyRecord(itemName, isExpense, quantity, sumOfOne);
                    recordMonth.add(monthlyRecord);
                    monthlyRecords.put(month, recordMonth);
                }
            }
        }
    }
    //добавил обработку NullPointerException, если нет файла месячного отчета, данные по которому есть в годовом отчете
    int getSumGainOrCostForMonth(int monthKey, boolean isExpense) {
        int sum = 0;
        try{
            for (MonthlyRecord month : monthlyRecords.get(monthKey)) {
                if (month.IS_EXPENSE == isExpense) {
                    sum += month.QUANTITY * month.SUM_OF_ONE;
                }
            }
        }catch (NullPointerException e){}
        return sum;
    }

    String getMaxGain(int monthKey) {
        int maxGain = 0;
        String item;
        String maxItem = "";
        int sum;
        for (MonthlyRecord monthInfo : monthlyRecords.get(monthKey)) {
            if (!monthInfo.IS_EXPENSE) {
                sum = monthInfo.QUANTITY * monthInfo.SUM_OF_ONE;
                item = monthInfo.ITEM_NAME;
                if (sum > maxGain) {
                    maxGain = sum;
                    maxItem = item;
                }
            }
        }
        return "Самый прибыльный товар: " + maxItem + "\nСумма: " + maxGain;
    }

    String getMaxCost(int monthKey) {
        int maxCost = 0;
        String item;
        String maxCostItem = "";
        int sum;
        for (MonthlyRecord monthInfo : monthlyRecords.get(monthKey)) {
            if (monthInfo.IS_EXPENSE) {
                sum = monthInfo.QUANTITY * monthInfo.SUM_OF_ONE;
                item = monthInfo.ITEM_NAME;
                if (sum > maxCost) {
                    maxCost = sum;
                    maxCostItem = item;
                }
            }
        }
        return "Самая большая трата: " + maxCostItem + "\nСумма: " + maxCost + "\n ";
    }

    void downloadMonthlyReports() {
        if (monthlyRecords.isEmpty()) {
            readMonthlyReports();
            System.out.println("Месячные отчеты успешно загружены\n ");
        } else {
            System.out.println("Данные по месячным отчетам уже имеются. Повторная загрузка не требуется.\n ");
        }
    }

    void printMonthlyStatistic() {
        if (!monthlyRecords.isEmpty()) {
            for (int month : monthlyRecords.keySet()) {
                System.out.println("Отчет за " + monthTitle[month - 1] + ":");
                System.out.println(getMaxGain(month));
                System.out.println(getMaxCost(month));
            }
        } else {
            System.out.println("Для вывода статистики необходимо загрузить месячные отчеты.\n ");
        }
    }

    public String readFileContentsOrNull(String path) {
        try {
            return Files.readString(Path.of(path));
        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл с месячным отчётом. Возможно, файл не находится в " +
                    "директории: resources\\\n ");
            return null;
        }
    }
}
