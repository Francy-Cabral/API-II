package AllData;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class readExcel {
	public static FileInputStream file;
	public static String[][] excelData;

	public readExcel() {

		try {
			file = new FileInputStream(new File(".\\src\\test\\resources\\api2_DataTest.xlsx"));
			Workbook workbook = new XSSFWorkbook(file);
			Sheet sheet = workbook.getSheetAt(0);

			
			int i = 0;
			int rowNum = sheet.getLastRowNum();
			int cellNum = sheet.getRow(0).getPhysicalNumberOfCells();
			excelData = new String[rowNum][cellNum];
			
			for (Row row : sheet) {
				int j = 0;
				if (row.getRowNum() == 0) {
					continue;
				}
				for (Cell cell : row) {
					switch (cell.getCellType()) {
					case STRING:
						excelData[i][j] = cell.getRichStringCellValue().getString();
						break;
					case NUMERIC:
						if (DateUtil.isCellDateFormatted(cell)) {
							excelData[i][j] = cell.getDateCellValue() + "";
						} else {
							excelData[i][j] = cell.getNumericCellValue() + "";
						}
						break;
					

					default:
					}
					j++;
				}
				i++;
			}

		} catch (Exception e) {
			
			e.printStackTrace();
		} 

	}

}
