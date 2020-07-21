package etl.transform;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import model.ListData;
import model.RepresentObject;

public class TransformClass {
	private Properties propsToTransform = new Properties();

	@SuppressWarnings("unchecked")
	public ListData transform(ListData draw_data) {
		Map<Integer, Class<?>> indexColumn_castType = (Map<Integer, Class<?>>) propsToTransform
				.get("columns.cast.class");
		try {
			Formatter formatter = new Formatter();
			for (int indexOfObject = 0; indexOfObject < draw_data.size(); indexOfObject++) {
				List<Object> attributes = draw_data.get(indexOfObject).attributes;
				Set<Entry<Integer, Class<?>>> entries = indexColumn_castType.entrySet();
				RepresentObject newObject = new RepresentObject();
				for (Entry<Integer, Class<?>> entry : entries) {
					int indexOfAttribute = entry.getKey() - 1;
					Object draw_format_attribute = attributes.get(indexOfAttribute);
					Class<?> typeClassFormat = entry.getValue();
					Object transformedAttribute = formatter.formatValueAsClassType(typeClassFormat,
							draw_format_attribute);
					attributes.set(indexOfAttribute, transformedAttribute);
					newObject.attributes = attributes;
				}
				draw_data.set(indexOfObject, newObject);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return draw_data;
	}

	public Properties getProperties() {
		return propsToTransform;
	}

	public void setProperties(Properties properties) {
		this.propsToTransform = properties;
	}

}
