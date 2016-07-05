package pl.waw.michal.maven.plugin.javabeantestgenerator.generator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pl.waw.michal.maven.plugin.javabeantestgenerator.SimpleJavaBean;
import pl.waw.michal.maven.plugin.javabeantestgenerator.TestArgumentsGenerator;
import pl.waw.michal.maven.plugin.javabeantestgenerator.generator.TestNGTestGenerator;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class TestNGTestGeneratorTest {

	private TestNGTestGenerator testNGTestGenerator;

	@Before
	public void setUp() throws Exception {
		testNGTestGenerator = new TestNGTestGenerator(new TestArgumentsGenerator());
	}

	@Test
	public void generateTest() throws Exception {
		List<PropertyDescriptor> propertyDescriptors = new ArrayList<PropertyDescriptor>();

		for(PropertyDescriptor propertyDescriptor : Introspector.getBeanInfo(SimpleJavaBean.class).getPropertyDescriptors()) {
			if("intProperty".equals(propertyDescriptor.getName()) ||
				"beanWithZeroArgumentConstructor".equals(propertyDescriptor.getName()))
				propertyDescriptors.add(propertyDescriptor);
		}

//		Writer writer = new FileWriter("SimpleJavaBeanTest.java");
		StringWriter writer = new StringWriter();
		try {
			testNGTestGenerator.generateTest(writer, SimpleJavaBean.class, propertyDescriptors);

			String testClassSource = writer.toString();

			Assert.assertTrue(testClassSource.contains("beanWithZeroArgumentConstructorValue = new pl.waw.michal.maven.plugin.javabeantestgenerator.BeanWithZeroArgumentConstructor();"));
			Assert.assertTrue(testClassSource.contains("testSubject.setBeanWithZeroArgumentConstructor(beanWithZeroArgumentConstructorValue);"));
			Assert.assertTrue(testClassSource.contains("Assert.assertEquals(testSubject.getBeanWithZeroArgumentConstructor(), beanWithZeroArgumentConstructorValue);"));

			Assert.assertTrue(testClassSource.contains("intPropertyValue = -2147483648;"));
			Assert.assertTrue(testClassSource.contains("testSubject.setIntProperty(intPropertyValue);"));
			Assert.assertTrue(testClassSource.contains("Assert.assertEquals(testSubject.getIntProperty(), intPropertyValue);"));
		} finally {
			writer.close();
		}
	}

}