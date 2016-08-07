package pl.waw.michal.maven.plugin.javabeantestgenerator.generator;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

/**
 * Generates test class for specified properties of a Java Bean.
 * <p>
 * Generated classes must have a marker field as specified by {@link #MARKER_FIELD}.
 * <p>
 * Note that field must be public. In other case it may be removed by compiler as not used.
 * <p>
 * We don't use {@link javax.annotation.Generated} because it is not preserved by compiler and we don't use custom
 * annotation because we would have to provide it in generated sources.
 * <p>
 * Using custom field is simpler.
 */
public interface TestGenerator {

	String MARKER_FIELD_NAME = "GENERATED_BY";
	String MARKER_FIELD_VALUE = TestGenerator.class.getName();

	String MARKER_FIELD = "public static final String " + MARKER_FIELD_NAME + " = \"" + MARKER_FIELD_VALUE + "\";";

	void generateTest(Writer writer, Class testedClass, List<PropertyDescriptor> propertyDescriptors) throws IOException, NoSuchMethodException;

}