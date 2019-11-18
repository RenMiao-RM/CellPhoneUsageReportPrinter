import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CellPhoneUsageFileReader extends FileReader {
    private Map<String, List<CellPhoneUsageData>> cellPhoneUsageDataMapping = new HashMap<>();
    private int totalMinutes;
    private double totalData;

    public CellPhoneUsageFileReader(Scanner fileScanner, ArrayList<CellPhoneUsageData> dataList) {
        super(fileScanner, dataList);
    }

    public Map<String, List<CellPhoneUsageData>> getCellPhoneUsageDataMapping() {
        return cellPhoneUsageDataMapping;
    }

    public int getTotalMinutes() {
        return totalMinutes;
    }

    public double getTotalData() {
        return totalData;
    }

    @Override
    protected void readFile() {
        Scanner scanner = getFileScanner();
        List<DbDataObject> dataList = getDataList();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            String[] lineData = line.split(",");
            if (lineData[0].contains("employeeId"))
                continue;

            try {
                dataList.add(new CellPhoneUsageData(lineData[0].trim(), new SimpleDateFormat("MM/dd/yyyy").parse(lineData[1].trim()),
                        Integer.parseInt(lineData[2].trim()), Double.parseDouble(lineData[3].trim())));
                totalData += Double.parseDouble(lineData[3].trim());
                totalMinutes += Integer.parseInt(lineData[2].trim());
            } catch (ParseException e) {
                continue; // skip the ill-formatted row
            }
        }
    }

    protected void processData() {
        List<DbDataObject> dataList = getDataList();

        dataList.stream().forEach(row -> {
            CellPhoneUsageData usageData = (CellPhoneUsageData) row;
            if (cellPhoneUsageDataMapping.containsKey(usageData.getEmployeeId())) {
                cellPhoneUsageDataMapping.get(usageData.getEmployeeId()).add(usageData);
            } else {
                ArrayList<CellPhoneUsageData> temp = new ArrayList<>();
                temp.add(usageData);
                cellPhoneUsageDataMapping.put(usageData.getEmployeeId(), temp);
            }
        });
    }
}
