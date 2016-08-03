package pl.waw.michal.maven.plugin.javabeantestgenerator;

import pl.waw.michal.maven.plugin.javabeantestgenerator.generator.TestGenerator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Plugin configuration. Built in {@link JavaBeanTestGeneratorMojo} from pom.xml configuration.
 */
public class Config {

	private String sourceDirectory;

	private String outputDirectory;

	private String generatedTestSourcesDirectory;

	private List<String> testClasspath;

	private TestGenerator testGenerator;

	private Collection<PackageClassMask> packagesAndClassMasks;

	private Collection<String> classMasksToSkip;

	public Config(String sourceDirectory, String outputDirectory, String generatedTestSourcesDirectory, List<String> testClasspath, TestGenerator testGenerator, Collection<PackageClassMask> packagesAndClassMasks, Collection<String> classMasksToSkip) {
		if(sourceDirectory == null)
			throw new IllegalArgumentException("sourceDirectory can not be null");
		if(outputDirectory == null)
			throw new IllegalArgumentException("outputDirectory can not be null");
		if(generatedTestSourcesDirectory == null)
			throw new IllegalArgumentException("generatedTestSourcesDirectory can not be null");
		if(testClasspath == null)
			throw new IllegalArgumentException("testClasspath can not be null");
		if(testGenerator == null)
			throw new IllegalArgumentException("testGenerator can not be null");
		if(packagesAndClassMasks == null)
			throw new IllegalArgumentException("packagesAndClassMasks can not be null");

		this.sourceDirectory = sourceDirectory;
		this.outputDirectory = outputDirectory;
		this.generatedTestSourcesDirectory = generatedTestSourcesDirectory;
		this.testClasspath = testClasspath;
		this.testGenerator = testGenerator;
		this.packagesAndClassMasks = packagesAndClassMasks;
		this.classMasksToSkip = classMasksToSkip != null ? classMasksToSkip : new ArrayList<String>();
	}

	public String getSourceDirectory() {
		return sourceDirectory;
	}

	public String getOutputDirectory() {
		return outputDirectory;
	}

	public String getGeneratedTestSourcesDirectory() {
		return generatedTestSourcesDirectory;
	}

	public List<String> getTestClasspath() {
		return testClasspath;
	}

	public TestGenerator getTestGenerator() {
		return testGenerator;
	}

	public Collection<PackageClassMask> getPackagesAndClassMasks() {
		return packagesAndClassMasks;
	}

	public Collection<String> getClassMasksToSkip() {
		return classMasksToSkip;
	}
}