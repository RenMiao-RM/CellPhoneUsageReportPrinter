import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class FileReader <T extends DbDataObject> {
    private Scanner fileScanner;
    private ArrayList<T> dataList;

    protected FileReader(Scanner fileScanner, ArrayList<T> dataList) {
        this.fileScanner = fileScanner;
        this.dataList = dataList;
    }

    protected Scanner getFileScanner() {
        return fileScanner;
    }

    public List<T> getDataList() {
        return dataList;
    }

    protected abstract void readFile(); // this method read file with Scanner and populate the dataList
}
