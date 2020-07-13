package reader;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
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

public class XLSXReader<T> implements Reader<T> {
	private Class<T> instanceClass;

	public XLSXReader(Class<T> instanceClass) {
		this.instanceClass = instanceClass;
	}

	public List<T> readData(File file) throws Exception {
		List<T> data = new ArrayList<T>();
		try {
			InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
			@SuppressWarnings("resource")
			Workbook workbook = new XSSFWorkbook(inputStream);
			Sheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rows = sheet.rowIterator();
			T instance = null;
			Field[] fields = instanceClass.getDeclaredFields();
			while (rows.hasNext()) {
				instance = instanceClass.newInstance();
				Row row = rows.next();
				if (row.getRowNum() == 0) {
					continue;
				}
				Iterator<Cell> cells = row.cellIterator();
				while (cells.hasNext()) {
					Cell cell = cells.next();
					int index = cell.getColumnIndex();
					String fieldname = fields[index].getName();
					fieldname = fieldname.replace(fieldname.charAt(0), Character.toUpperCase(fieldname.charAt(0)));
					Method method = instanceClass.getMethod("set" + fieldname, fields[index].getType());
					method.invoke(instance, getValue(cell));
				}
				data.add(instance);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Cannot read data");
		}
		return data;
	}

	private static String getValue(Cell cell) {
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
