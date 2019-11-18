# CellPhoneUsageReportPrinter

#Brief Description
Read csv files from data folder, generate reports and call system printer interface to print. 

Data from both csv files are imported and processed using Scanner class. Cell phone usage data are then grouped 
based on the user first then further grouped based on the month. Minute and data usage data are aggregated and reported.

The Printer class was adapted from https://docs.oracle.com/javase/tutorial/2d/printing/examples/PaginationExample.java

#How to Run
Main method is within PaginationPrinter class. It will show a simple GUI interface with a button. Click 
the button to print the report. An system printer interface will show and allow the user to specify the 
printer (or print to PDF).

The function is tested on a Macbook laptop and works as expected. 
