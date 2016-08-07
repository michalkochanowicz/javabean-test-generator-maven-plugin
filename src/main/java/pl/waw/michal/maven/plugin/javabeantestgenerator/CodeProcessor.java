package pl.waw.michal.maven.plugin.javabeantestgenerator;

import pl.waw.michal.maven.plugin.javabeantestgenerator.generator.TestGenerator;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

/**
 * Analyses code - classes and sources - and decides for which classes unit test should be generated. For each class
 * invokes {@link JavaBeanProcessor} to find properties which should be tested. If there are any properties to test,
 * {@link TestGenerator} is invoked.
 */
public class CodeProcessor {

	private ClassQualifier classQualifier;
	private JavaBeanProcessor javaBeanProcessor;

	public CodeProcessor(ClassQualifier classQualifier, JavaBeanProcessor javaBeanProcessor) {
		this.classQualifier = classQualifier;
		this.javaBeanProcessor = javaBeanProcessor;
	}

	public void process(Config config) throws IOException, ClassNotFoundException, IntrospectionException, NoSuchMethodException, IllegalAccessException {
		List<String> classNamesToProcess = new ArrayList<>();

		for(PackageClassMask packageClassMask : config.getPackagesAndClassMasks()) {
			classNamesToProcess.addAll(packageClassMask.findJavaClassesInSources(config.getSourceDirectory()));
		}

		URLClassLoader urlClassLoader = new URLClassLoader(getTestClasspathUrls(config), this.getClass().getClassLoader());

		for(String classNameToPRocess : classNamesToProcess) {
			Class classToProcess = urlClassLoader.loadClass(classNameToPRocess);

			if(!classQualifier.shouldProcessThisClass(classToProcess)) {
				continue;
			}

			List<PropertyDescriptor> propertiesToTest = javaBeanProcessor.process(Introspector.getBeanInfo(classToProcess));

			if(propertiesToTest.size() == 0)
				continue;

			File directory = new File(config.getGeneratedTestSourcesDirectory() + File.separator + (classToProcess.getPackage() == null ? "" : classToProcess.getPackage().getName()).replace('.', File.separatorChar));

			if(!directory.exists() && !directory.mkdirs()) {
				throw new IOException("Can't create directory \"" + directory.getPath() + "\"");
			}

			try(FileWriter fileWriter = new FileWriter(directory.getPath() + File.separator + classToProcess.getSimpleName() + "Test.java")) {
				config.getTestGenerator().generateTest(fileWriter, classToProcess, propertiesToTest);
			}
		}
	}

	private URL[] getTestClasspathUrls(Config config) throws MalformedURLException {
		// Order is important here. In case (which should be avoided) when there are two different classes
		// with same package.Class, it is important to keep the order.

		List<URL> urls = new ArrayList<>(config.getTestClasspath().size());
		for(String classPathElement : config.getTestClasspath()) {
			// We don't want to process test classes
			if(!classPathElement.contains("target" + File.separator + "test-classes")) {
				urls.add(new File(classPathElement).toURI().toURL());
			}
		}

		return urls.toArray(new URL[0]);
	}

}