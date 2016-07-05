package pl.waw.michal.maven.plugin.javabeantestgenerator.generator;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

/**
 * Generates test class for specified properties of a Java Bean.
 */
public interface TestGenerator {

	void generateTest(Writer writer, Class testedClass, List<PropertyDescriptor> propertyDescriptors) throws IOException, NoSuchMethodException;

}