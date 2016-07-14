package pl.waw.michal.maven.plugin.javabeantestgenerator.generator;

import pl.waw.michal.maven.plugin.javabeantestgenerator.TestArgumentsGenerator;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

/**
 * Generator of unit test using JUnit 4.x.
 */
public class JUnitTestGenerator implements TestGenerator {

	private static final String CLASS_HEADER =
		"package @PACKAGE@;\n" +
			"\n" +
			"import org.junit.Assert;\n" +
			"import org.junit.Before;\n" +
			"import org.junit.Test;\n" +
			"\n" +
			"public class @CLASS@Test {\n" +
			"\n" +
			"\tprivate @PACKAGE@.@CLASS@ testSubject;\n" +
			"\n" +
			"\t@Before\n" +
			"\tpublic void setUp() throws Exception {\n" +
			"\t\ttestSubject = new @PACKAGE@.@CLASS@();\n" +
			"\t}\n" +
			"\n";

	private static final String TEST_METHOD_HEADER =
		"\t@Test\n" +
			"\tpublic void @PROPERTY@Test() throws Exception {\n" +
			"\t\t@PROPERTY_TYPE@ @PROPERTY@Value;\n" +
			"\n";

	private static final String TEST_METHOD_TEST =
			"\t\t@PROPERTY@Value = @VALUE@;\n" +
			"\t\ttestSubject.@SETTER@(@PROPERTY@Value);\n" +
			"\t\tAssert.assertEquals(@PROPERTY@Value, testSubject.@GETTER@());\n" +
			"\n";

	private static final String TEST_METHOD_FOOTER =
			"\t}\n" +
			"\n";

	private static final String CLASS_FOOTER =
		"}";

	private TestArgumentsGenerator testArgumentsGenerator;

	public JUnitTestGenerator(TestArgumentsGenerator testArgumentsGenerator) {
		this.testArgumentsGenerator = testArgumentsGenerator;
	}

	@Override
	public void generateTest(Writer writer, Class testedClass, List<PropertyDescriptor> propertyDescriptors) throws IOException, NoSuchMethodException {
		writer.write(substitute(CLASS_HEADER, testedClass, propertyDescriptors.get(0), null));

		for(PropertyDescriptor propertyDescriptor : propertyDescriptors) {
			writer.write(substitute(TEST_METHOD_HEADER, testedClass, propertyDescriptor, null));

			// TODO: Log warning if only null argument is offered
			for(String testArgument : testArgumentsGenerator.getArguments(propertyDescriptor.getPropertyType())) {
				writer.write(substitute(TEST_METHOD_TEST, testedClass, propertyDescriptor, testArgument));
			}

			writer.write(substitute(TEST_METHOD_FOOTER, testedClass, propertyDescriptor, null));
		}

		writer.write(substitute(CLASS_FOOTER, testedClass, propertyDescriptors.get(0), null));
	}

	private String substitute(String s, Class testedClass, PropertyDescriptor propertyDescriptor, String value) {
		String result =  s.
			replace("@PACKAGE@", testedClass.getPackage().getName()).
			replace("@CLASS@", testedClass.getSimpleName()).
			replace("@PROPERTY@", propertyDescriptor.getName()).
			replace("@PROPERTY_TYPE@", propertyDescriptor.getPropertyType().getName()).
			replace("@GETTER@", propertyDescriptor.getReadMethod().getName()).
			replace("@SETTER@", propertyDescriptor.getWriteMethod().getName());

		if(value != null)
			result = result.replace("@VALUE@", value);

		return result;
	}

}