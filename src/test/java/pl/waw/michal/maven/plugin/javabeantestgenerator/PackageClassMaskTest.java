package pl.waw.michal.maven.plugin.javabeantestgenerator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;

public class PackageClassMaskTest {

	private PackageClassMask packageClassMask;

	@Before
	public void setUp() throws Exception {
		packageClassMask = new PackageClassMask("pl.michal", "A*");
	}

	@Test
	public void getPackageDirectory() throws Exception {
		Assert.assertEquals(
			File.separator + "src" + File.separator + "pl" + File.separator + "michal",
			packageClassMask.getPackageDirectory(File.separator + "src")
		);
	}

	@Test
	public void findJavaSources() throws Exception {
		String testSourceDirectoryPath = new File(this.getClass().getResource(this.getClass().getSimpleName()).toURI()).getPath();

		Assert.assertArrayEquals(
			Arrays.asList("pl.michal.AA", "pl.michal.AB").toArray(new String[0]),
			packageClassMask.findJavaClassesInSources(testSourceDirectoryPath + File.separator + "src").toArray(new String[0])
		);
	}

}