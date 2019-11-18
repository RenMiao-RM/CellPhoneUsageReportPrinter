import java.io.File;
import java.io.FileNotFoundException;
import java.text.*;
import java.util.*;

public class ReportGenerator {
    public String generateReport(File cellPhoneFile, File cellPhoneUsageFile) {

        CellPhoneFileReader cellPhoneFileReader = null;
        CellPhoneUsageFileReader cellPhoneUsageFileReader = null;

        try {
            cellPhoneFileReader = new CellPhoneFileReader(new Scanner(cellPhoneFile), new ArrayList<>());
            cellPhoneUsageFileReader = new CellPhoneUsageFileReader(new Scanner(cellPhoneUsageFile), new ArrayList<>());
        } catch (FileNotFoundException e) {
            System.exit(1);
        }

        cellPhoneFileReader.readFile();
        cellPhoneUsageFileReader.readFile();

        cellPhoneUsageFileReader.processData();

        StringBuilder sb = new StringBuilder();
        sb.append("SUMMARY\n---------------\n");
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        NumberFormat formatter = new DecimalFormat("#0.00");
        Date date = new Date();
        sb.append("Report Run Date: " + dateFormat.format(date) + "\n");
        sb.append("Number of Phones: " + cellPhoneUsageFileReader.getCellPhoneUsageDataMapping().keySet().size() + "\n");
        sb.append("Total Minutes: " + cellPhoneUsageFileReader.getTotalMinutes() + "\n");
        sb.append("Total Data: " + formatter.format(cellPhoneUsageFileReader.getTotalData())+ "\n");
        sb.append("Average Minutes: " + cellPhoneUsageFileReader.getTotalMinutes()/cellPhoneUsageFileReader.getCellPhoneUsageDataMapping().keySet().size() + "\n");
        sb.append("Average Data: " + formatter.format(cellPhoneUsageFileReader.getTotalData()/cellPhoneUsageFileReader.getCellPhoneUsageDataMapping().keySet().size()) + "\n");

        sb.append("\nDETAILS\n---------------\n");
//        sb.append("Employee Id, Employee Name, Model, Purchase Date\n");
        for (Object row : cellPhoneFileReader.getDataList()) {
            CellPhoneData cellPhoneData = (CellPhoneData) row;
            sb.append("Employee Id: " + cellPhoneData.getEmployeeId() + "\n  Employee Name: " + cellPhoneData.getEmployeeName() +
                    "\n  Model: " + cellPhoneData.getModel()
                    + "\n  Purchase Date: " + cellPhoneData.getPurchaseDate() + "\n");

            List<CellPhoneUsageData> list = cellPhoneUsageFileReader.getCellPhoneUsageDataMapping().get(cellPhoneData.getEmployeeId());
            DateFormat yearMonthOnlyDateFormat = new SimpleDateFormat("yyyy/MM");
            Map<String, Integer> minuteMap = new HashMap<>();
            Map<String, Double> dataUsageMap = new HashMap<>();

            for (CellPhoneUsageData data : list) {
                String month = yearMonthOnlyDateFormat.format(data.getDate());
                minuteMap.put(month, minuteMap.getOrDefault(month, 0) + data.getTotalMinute());
                dataUsageMap.put(month, dataUsageMap.getOrDefault(month, 0.0) + data.getTotalData());
            }

            Date[] dateArr = new Date[minuteMap.keySet().size()];
            int index = 0;
            for (String month : minuteMap.keySet()) {
                try {
                    dateArr[index] = yearMonthOnlyDateFormat.parse(month);
                    index++;
                } catch (ParseException e) {
                    continue;
                }
            }

            Arrays.sort(dateArr);
            sb.append("  Minutes Usage Per Month:\n");
            for (Date d : dateArr) {
                sb.append("    " + yearMonthOnlyDateFormat.format(d) + ": " + minuteMap.get(yearMonthOnlyDateFormat.format(d)) + "\n");
            }

            sb.append("  Data Usage Per Month:\n");
            for (Date d : dateArr) {
                sb.append("    " + yearMonthOnlyDateFormat.format(d) + ": " + formatter.format(dataUsageMap.get(yearMonthOnlyDateFormat.format(d))) + "\n");
            }
        }

        return sb.toString();
    }
}
