package pl.waw.michal.maven.plugin.javabeantestgenerator;

import pl.waw.michal.maven.plugin.javabeantestgenerator.generator.TestGenerator;

import java.beans.*;
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

	public void process(Config config) throws IOException, ClassNotFoundException, IntrospectionException, NoSuchMethodException {
		List<String> classNamesToProcess = new ArrayList<String>();

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
		URL url = new File(config.getOutputDirectory()).toURI().toURL();

		URL[] urls = new URL[config.getTestClasspath().size()+1];
		int idx = 0;
		urls[idx++] = url;
		for(String s : config.getTestClasspath()) {
			urls[idx++] = new File(s).toURI().toURL();
		}
		return urls;
	}


}