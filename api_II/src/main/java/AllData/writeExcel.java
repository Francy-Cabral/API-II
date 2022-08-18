package AllData;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class writeExcel {
	
	XSSFWorkbook workbook = new XSSFWorkbook();
	XSSFSheet sheet = workbook.createSheet("Output");
	
	public writeExcel(List<Object[]> output){
		int rowNum = 0;
		for(Object[] op: output) {
			Row row = sheet.createRow(rowNum++);
			int cellNum = 0;
			for(Object type : op) {
				Cell cell = row.createCell(cellNum++);
				if (type instanceof String) {
					cell.setCellValue((String) type);
				} else if (type instanceof Integer) {
					cell.setCellValue((Integer) type);
				}
			}
		}
		try {
			FileOutputStream outputFile = new FileOutputStream("Output.xlsx");
			workbook.write(outputFile);
			workbook.close();
		} catch (Exception e) {
			
			e.printStackTrace();
		} 
	}

}
