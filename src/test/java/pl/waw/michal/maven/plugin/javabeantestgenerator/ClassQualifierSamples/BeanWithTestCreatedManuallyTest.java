package pl.waw.michal.maven.plugin.javabeantestgenerator.ClassQualifierSamples;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BeanWithTestCreatedManuallyTest {

	private BeanWithTestCreatedManually beanWithTestCreatedManually;

	@Before
	public void setUp() throws Exception {
		beanWithTestCreatedManually = new BeanWithTestCreatedManually();
	}

	@Test
	public void name() throws Exception {
		int testValue = 123;

		beanWithTestCreatedManually.setProperty(testValue);

		Assert.assertEquals(testValue, beanWithTestCreatedManually.getProperty());
	}
}