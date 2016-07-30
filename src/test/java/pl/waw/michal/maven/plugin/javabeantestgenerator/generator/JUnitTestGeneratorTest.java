package pl.waw.michal.maven.plugin.javabeantestgenerator.generator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pl.waw.michal.maven.plugin.javabeantestgenerator.SimpleJavaBean;
import pl.waw.michal.maven.plugin.javabeantestgenerator.TestArgumentsGenerator;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class JUnitTestGeneratorTest {

	private JUnitTestGenerator jUnitTestGenerator;

	@Before
	public void setUp() throws Exception {
		jUnitTestGenerator = new JUnitTestGenerator(new TestArgumentsGenerator());
	}

	@Test
	public void generateTest() throws Exception {
		List<PropertyDescriptor> propertyDescriptors = new ArrayList<>();

		for(PropertyDescriptor propertyDescriptor : Introspector.getBeanInfo(SimpleJavaBean.class).getPropertyDescriptors()) {
			switch(propertyDescriptor.getName()) {
				case "byteArrayProperty":
				case "intProperty":
				case "beanWithZeroArgumentConstructor":
					propertyDescriptors.add(propertyDescriptor);
			}
		}
		
//		try(Writer writer = new FileWriter("src" + File.separator + "test" + File.separator + "java" + File.separator + "pl" + File.separator + "waw" + File.separator + "michal" + File.separator + "maven" + File.separator + "plugin" + File.separator + "javabeantestgenerator" + File.separator + "SimpleJavaBeanTest.java")) {
		try(StringWriter writer = new StringWriter()) {
			jUnitTestGenerator.generateTest(writer, SimpleJavaBean.class, propertyDescriptors);

			String testClassSource = writer.toString();

			Assert.assertTrue(testClassSource.contains("byteArrayPropertyValue = new byte[0];"));
			Assert.assertTrue(testClassSource.contains("testSubject.setByteArrayProperty(byteArrayPropertyValue);"));
			Assert.assertTrue(testClassSource.contains("Assert.assertEquals(byteArrayPropertyValue, testSubject.getByteArrayProperty());"));

			Assert.assertTrue(testClassSource.contains("intPropertyValue = -2147483648;"));
			Assert.assertTrue(testClassSource.contains("testSubject.setIntProperty(intPropertyValue);"));
			Assert.assertTrue(testClassSource.contains("Assert.assertEquals(intPropertyValue, testSubject.getIntProperty());"));

			Assert.assertTrue(testClassSource.contains("beanWithZeroArgumentConstructorValue = new pl.waw.michal.maven.plugin.javabeantestgenerator.BeanWithZeroArgumentConstructor();"));
			Assert.assertTrue(testClassSource.contains("testSubject.setBeanWithZeroArgumentConstructor(beanWithZeroArgumentConstructorValue);"));
			Assert.assertTrue(testClassSource.contains("Assert.assertEquals(beanWithZeroArgumentConstructorValue, testSubject.getBeanWithZeroArgumentConstructor());"));
		}
	}

}