package pl.waw.michal.maven.plugin.javabeantestgenerator;

import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

/**
 * Analyses Java Bean and decides which properties should be tested. Returns {@link List} of {@link PropertyDescriptor}s
 * for which tests should be generated.
 */
public class JavaBeanProcessor {

	public List<PropertyDescriptor> process(BeanInfo beanInfo) {
		List<PropertyDescriptor> propertiesToTest = new ArrayList<PropertyDescriptor>();

		for(PropertyDescriptor propertyDescriptor : beanInfo.getPropertyDescriptors()) {
			if(propertyDescriptor.getReadMethod() == null || propertyDescriptor.getWriteMethod() == null)
				continue;

			propertiesToTest.add(propertyDescriptor);
		}

		return propertiesToTest;
	}

}