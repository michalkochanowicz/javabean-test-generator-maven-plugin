package pl.waw.michal.maven.plugin.javabeantestgenerator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.List;

public class JavaBeanProcessorTest {

	private JavaBeanProcessor javaBeanProcessor;

	@Before
	public void setUp() throws Exception {
		javaBeanProcessor = new JavaBeanProcessor();
	}

	@Test
	public void process() throws Exception {
		List<PropertyDescriptor> propertiesToTest = javaBeanProcessor.process(Introspector.getBeanInfo(SimpleJavaBean.class));

		Assert.assertEquals(4, propertiesToTest.size());

		int propertyNumber = 0;

		Assert.assertEquals("beanWithZeroArgumentConstructor", propertiesToTest.get(propertyNumber++).getName());
		Assert.assertEquals("byteArrayProperty", propertiesToTest.get(propertyNumber++).getName());
		Assert.assertEquals("innerClassProperty", propertiesToTest.get(propertyNumber++).getName());
		Assert.assertEquals("intProperty", propertiesToTest.get(propertyNumber).getName());
	}

}


