package ptsi.service.configurations;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import javax.json.bind.config.PropertyVisibilityStrategy;

/**
 * This class implements custom visibility for the private scope with the
 * intention of allowing private fields to be serialized via JAXRS
 * http://adambien.blog/roller/abien/entry/private_fields_serialization_with_json
 * 
 * @author Stephen W. Boyd, dmtoolbox.online
 */
public class PrivateVisibilityStrategy implements PropertyVisibilityStrategy {

	@Override
	public boolean isVisible(Field field) {
		return true;
	}

	@Override
	public boolean isVisible(Method method) {
		return false;
	}

}