import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CellPhoneFileReader extends FileReader {

    public CellPhoneFileReader(Scanner fileScanner, ArrayList<CellPhoneData> dataList) {
        super(fileScanner, dataList);
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

            dataList.add(new CellPhoneData(lineData[0].trim(), lineData[1].trim(), lineData[2].trim(), lineData[3].trim()));
        }
    }
}
