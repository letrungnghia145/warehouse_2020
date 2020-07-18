package reader;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import model.RepresentObject;

public class XLSXReader implements Reader {

	public List<RepresentObject> readData(File file) throws Exception {
		List<RepresentObject> data = new ArrayList<>();
		try {
			InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
			@SuppressWarnings("resource")
			Workbook workbook = new XSSFWorkbook(inputStream);
			Sheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rows = sheet.rowIterator();
			Row row = rows.next();
			int index = row.getPhysicalNumberOfCells();
			RepresentObject representObject = null;
			while (rows.hasNext()) {
				representObject = new RepresentObject();
				row = rows.next();
				for (int i = 0; i < index; i++) {
					representObject.addValue(getValue(row.getCell(i)));
				}
				data.add(representObject);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Cannot read data");
		}
		return data;
	}

	private static String getValue(Cell cell) {
		if (cell == null)
			return "blank";

		CellType cellType = cell.getCellType();
		String value = new String();
		switch (cellType) {
		case BOOLEAN:
			value = String.valueOf(cell.getBooleanCellValue());
			break;
		case NUMERIC:
			value = String.valueOf(new BigDecimal(cell.getNumericCellValue()).intValue());
			break;
		case STRING:
			value = cell.getStringCellValue();
			break;
		case FORMULA:
			Workbook workbook = cell.getSheet().getWorkbook();
			FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
			value = String.valueOf(new BigDecimal(evaluator.evaluate(cell).getNumberValue()).intValue());
			break;
		case BLANK:
		case ERROR:
		case _NONE:
			value = "blank";
			break;
		default:
			break;
		}
		return value;
	}
}
