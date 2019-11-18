import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.print.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

// note: this class is adapted from https://docs.oracle.com/javase/tutorial/2d/printing/examples/PaginationExample.java
public class PaginationPrinter implements Printable, ActionListener {
    int[] pageBreaks;  // array of page break line positions.
    String[] textLines;

    private void initTextLines() {
        String cellPhoneFilePath = "src/data/cellPhone.csv";
        String cellPhoneUsageFilePath = "src/data/CellPhoneUsageByMonth.csv";

        File cellPhoneFile = new File(cellPhoneFilePath);
        File cellPhoneUsageFile = new File(cellPhoneUsageFilePath);

        ReportGenerator reportGenerator = new ReportGenerator();
        String report = reportGenerator.generateReport(cellPhoneFile, cellPhoneUsageFile);

        Scanner scanner = new Scanner(report);
        ArrayList<String> textList = new ArrayList<>();
        while (scanner.hasNextLine()) {
            textList.add(scanner.nextLine());
        }

        textLines = new String[textList.size()];
        for (int i = 0; i < textList.size(); i++) {
            textLines[i] = textList.get(i);
        }
    }

    public int print(Graphics g, PageFormat pf, int pageIndex)
            throws PrinterException {

        Font font = new Font("Serif", Font.PLAIN, 10);
        FontMetrics metrics = g.getFontMetrics(font);
        int lineHeight = metrics.getHeight();

        if (pageBreaks == null) {
            initTextLines();
            int linesPerPage = (int)(pf.getImageableHeight()/lineHeight);
            int numBreaks = (textLines.length-1)/linesPerPage;
            pageBreaks = new int[numBreaks];
            for (int b=0; b<numBreaks; b++) {
                pageBreaks[b] = (b+1)*linesPerPage;
            }
        }

        if (pageIndex > pageBreaks.length) {
            return NO_SUCH_PAGE;
        }

        /* User (0,0) is typically outside the imageable area, so we must
         * translate by the X and Y values in the PageFormat to avoid clipping
         * Since we are drawing text we
         */
        Graphics2D g2d = (Graphics2D)g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());

        /* Draw each line that is on this page.
         * Increment 'y' position by lineHeight for each line.
         */
        int y = 0;
        int start = (pageIndex == 0) ? 0 : pageBreaks[pageIndex-1];
        int end   = (pageIndex == pageBreaks.length)
                ? textLines.length : pageBreaks[pageIndex];
        for (int line=start; line<end; line++) {
            y += lineHeight;
            g.drawString(textLines[line], 0, y);
        }

        /* tell the caller that this page is part of the printed document */
        return PAGE_EXISTS;
    }

    public void actionPerformed(ActionEvent e) {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(this);
        boolean ok = job.printDialog();
        if (ok) {
            try {
                job.print();
            } catch (PrinterException ex) {
                /* The job did not successfully complete */
            }
        }
    }

    public static void main(String args[]) {

        try {
            String cn = UIManager.getSystemLookAndFeelClassName();
            UIManager.setLookAndFeel(cn); // Use the native L&F
        } catch (Exception cnf) {
        }
        JFrame f = new JFrame("Printing Phone Usage Report");
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {System.exit(0);}
        });
        JButton printButton = new JButton("Click to Print Phone Usage Report");
        printButton.addActionListener(new PaginationPrinter());
        f.add("Center", printButton);
        f.pack();
        f.setVisible(true);
    }
}