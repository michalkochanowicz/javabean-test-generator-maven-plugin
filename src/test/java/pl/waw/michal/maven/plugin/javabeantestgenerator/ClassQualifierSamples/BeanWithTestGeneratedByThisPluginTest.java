package pl.waw.michal.maven.plugin.javabeantestgenerator.ClassQualifierSamples;

import pl.waw.michal.maven.plugin.javabeantestgenerator.generator.TestGenerator;

public class BeanWithTestGeneratedByThisPluginTest {

	public static final String GENERATED_BY = TestGenerator.class.getName();

}