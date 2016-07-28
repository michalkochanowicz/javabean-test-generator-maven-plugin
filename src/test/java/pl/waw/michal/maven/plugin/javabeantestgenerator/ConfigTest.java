package pl.waw.michal.maven.plugin.javabeantestgenerator;

import org.junit.Assert;
import org.junit.Test;
import pl.waw.michal.maven.plugin.javabeantestgenerator.generator.TestNGTestGenerator;

import java.util.ArrayList;

public class ConfigTest {

	@Test
	public void getClassMasksToSkip() throws Exception {
		ArrayList<String> classMasksToSkip = new ArrayList<String>();

		Assert.assertSame(
			classMasksToSkip,
			new Config(
				"",
				"",
				"",
				new ArrayList<String>(),
				new TestNGTestGenerator(new TestArgumentsGenerator()),
				new ArrayList<PackageClassMask>(),
				classMasksToSkip
			).getClassMasksToSkip());
	}

	@Test
	public void getClassMasksToSkipEmptyWhenNull() throws Exception {
		Assert.assertEquals(
			0,
			new Config(
				"",
				"",
				"",
				new ArrayList<String>(),
				new TestNGTestGenerator(new TestArgumentsGenerator()),
				new ArrayList<PackageClassMask>(),
				null
			).getClassMasksToSkip().size());
	}

}