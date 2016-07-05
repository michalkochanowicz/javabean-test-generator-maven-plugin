package pl.waw.michal.maven.plugin.javabeantestgenerator;

import pl.waw.michal.maven.plugin.javabeantestgenerator.generator.JUnitTestGenerator;
import pl.waw.michal.maven.plugin.javabeantestgenerator.generator.TestGenerator;
import pl.waw.michal.maven.plugin.javabeantestgenerator.generator.TestNGTestGenerator;

/**
 * Enumeration of testing libraries supported by this plugin.
 */
public enum TestingLibrary {

	JUnit(JUnitTestGenerator.class),
	TestNG(TestNGTestGenerator.class);

	private Class<? extends TestGenerator> value;

	TestingLibrary(Class<? extends TestGenerator> testGeneratorClass) {
		this.value = testGeneratorClass;
	}

	public Class<? extends TestGenerator> getValue() {
		return value;
	}

}