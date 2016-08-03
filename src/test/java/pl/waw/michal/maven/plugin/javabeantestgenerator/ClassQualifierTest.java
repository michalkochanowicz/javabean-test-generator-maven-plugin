package pl.waw.michal.maven.plugin.javabeantestgenerator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pl.waw.michal.maven.plugin.javabeantestgenerator.generator.TestNGTestGenerator;

import java.util.ArrayList;
import java.util.Arrays;

public class ClassQualifierTest {

	private ClassQualifier classQualifier;

	@Before
	public void setUp() throws Exception {
		classQualifier = new ClassQualifier(new Config(
			"",
			"",
			"",
			new ArrayList<String>(),
			new TestNGTestGenerator(new TestArgumentsGenerator()),
			new ArrayList<PackageClassMask>(),
			Arrays.asList(
				"pl.waw.michal.maven.plugin.javabeantestgenerator.ClassQualifierSamples.a.A",
				"pl.waw.michal.maven.plugin.javabeantestgenerator.ClassQualifierSamples.b.B*"
			)
		));
	}

	@Test
	public void shouldProcessThisClass() throws Exception {
		Assert.assertFalse(classQualifier.shouldProcessThisClass(pl.waw.michal.maven.plugin.javabeantestgenerator.ClassQualifierSamples.a.A.class));
		Assert.assertTrue(classQualifier.shouldProcessThisClass(pl.waw.michal.maven.plugin.javabeantestgenerator.ClassQualifierSamples.b.AA.class));
		Assert.assertFalse(classQualifier.shouldProcessThisClass(pl.waw.michal.maven.plugin.javabeantestgenerator.ClassQualifierSamples.b.BA.class));
		Assert.assertFalse(classQualifier.shouldProcessThisClass(pl.waw.michal.maven.plugin.javabeantestgenerator.ClassQualifierSamples.b.BB.class));
		Assert.assertTrue(classQualifier.shouldProcessThisClass(pl.waw.michal.maven.plugin.javabeantestgenerator.ClassQualifierSamples.c.C.class));
		Assert.assertFalse(classQualifier.shouldProcessThisClass(pl.waw.michal.maven.plugin.javabeantestgenerator.ClassQualifierSamples.z.Abstract.class));
		Assert.assertFalse(classQualifier.shouldProcessThisClass(pl.waw.michal.maven.plugin.javabeantestgenerator.ClassQualifierSamples.BeanWithoutZeroArgumentConstructor.class));
	}

}