import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

public class MonthlyReport {
    public HashMap<String, MonthlyStat> data = new HashMap<>();

    public MonthlyReport(String path) {
        String content = readFileContentsOrNull(path);
        String[] lines = content.split("\r?\n");
        for (int i = 1; i < lines.length; i++) {
            String line = lines[i];
            String[] parts = line.split(",");
            String itemName = parts[0];
            boolean isExpense = Boolean.parseBoolean(parts[1]);
            int quantity = Integer.parseInt(parts[2]);
            int sumOfOne = Integer.parseInt(parts[3]);
            if (!data.containsKey(itemName)) {
                data.put(itemName, new MonthlyStat());
            }
            MonthlyStat stat = data.get(itemName);
            if (isExpense) {
                stat.costs = quantity * sumOfOne;
            } else {
                stat.gain = quantity * sumOfOne;
            }
        }
    }

    public void printMonthlyReport() {
        System.out.println("Самый прибыльный товар: ");
        calcMaxGain();
        System.out.println("Самая большая трата: ");
        calcMaxCosts();
    }

    public void calcMaxGain() {
        int maxGain = 0;
        String nameMaxGain = "";
        for (String itemName : data.keySet()) {
            MonthlyStat stat = data.get(itemName);
            if (maxGain < stat.gain) {
                maxGain = stat.gain;
                nameMaxGain = itemName;
            }
        }
        System.out.println(nameMaxGain + " " + maxGain);
    }

    public void calcMaxCosts() {
        int maxCosts = 0;
        String nameMaxCosts = "";
        for (String itemName : data.keySet()) {
            MonthlyStat stat = data.get(itemName);
            if (maxCosts < stat.costs) {
                maxCosts = stat.costs;
                nameMaxCosts = itemName;
            }
        }
        System.out.println(nameMaxCosts + " " + maxCosts);
    }

    public int sumCosts() {
        int sumCosts = 0;
        for (String itemName : data.keySet()) {
            MonthlyStat stat = data.get(itemName);
            sumCosts += stat.costs;
        }
        return sumCosts;
    }

    public int sumGain() {
        int sumGain = 0;
        for (String itemName : data.keySet()) {
            MonthlyStat stat = data.get(itemName);
            sumGain += stat.gain;
        }
        return sumGain;
    }

    private String readFileContentsOrNull(String path) {
        try {
            return Files.readString(Path.of(path));
        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл с отчётом. Возможно, файл не находится в нужной директории.");
            return null;
        }
    }
}