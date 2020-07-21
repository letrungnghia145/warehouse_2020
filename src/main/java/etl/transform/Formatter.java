package etl.transform;

import javax.activation.UnsupportedDataTypeException;

import lombok.NoArgsConstructor;
import utils.DateFormatUtils;

@NoArgsConstructor
public class Formatter {
	@SuppressWarnings("unchecked")
	public <T> T formatValueAsClassType(Class<T> classType, Object valueAsObject) throws UnsupportedDataTypeException {
		String value = String.valueOf(valueAsObject);
		String name_class = classType.getName();
		switch (name_class) {
		case "java.lang.Integer":
			return (T) new Integer(value);
		case "java.lang.String":
			return (T) value;
		case "java.util.Date":
			try {
				if (Integer.parseInt(value) > 0)
					return (T) new java.util.Date(DateFormatUtils.getTimeInMilis(Integer.parseInt(value)));
			} catch (Exception e) {
				return (T) new java.util.Date(DateFormatUtils.getTimeInMilis(value));
			}
		case "java.sql.Date":
			try {
				if (Integer.parseInt(value) > 0)
					return (T) new java.sql.Date(DateFormatUtils.getTimeInMilis(Integer.parseInt(value)));
			} catch (Exception e) {
				return (T) new java.sql.Date(DateFormatUtils.getTimeInMilis(value));
			}
		case "java.sql.Timestamp":
			try {
				if (Integer.parseInt(value) > 0)
					return (T) new java.sql.Timestamp(DateFormatUtils.getTimeInMilis(Integer.parseInt(value)));
			} catch (Exception e) {
				return (T) new java.sql.Timestamp(DateFormatUtils.getTimeInMilis(value));
			}
		default:
			throw new UnsupportedDataTypeException(classType.getName() + " does not supported to cast value");
		}
	}
}
