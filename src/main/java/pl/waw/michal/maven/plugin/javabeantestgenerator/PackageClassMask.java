package pl.waw.michal.maven.plugin.javabeantestgenerator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Model holding a package name and mask of classes from this package. It also offers
 * method {@link #findJavaClassesInSources(String, String)} to find matching classes in source directories.
 */
public class PackageClassMask {

	private String packageName;
	private String classNameMask;

	public PackageClassMask(String packageName, String classNameMask) {
		this.packageName = packageName;
		this.classNameMask = classNameMask;
	}

	public String getPackageName() {
		return packageName;
	}

	public String getClassNameMask() {
		return classNameMask;
	}

	public String getPackageDirectory(String sourceDirectory) {
		return sourceDirectory + File.separator + packageName.replace('.', File.separatorChar);
	}

	/**
	 * Searches for Java source files matching {@link #packageName} and {@link #classNameMask} in specified
	 * directory.
	 * <p>
	 * Skips classes for which there is a manually written test (a source file with same name and <code>Test</code>
	 * suffix). This method of detecting manually written test is not perfect, but for now there is no better one.
	 *
	 * @param sourceDirectory	root of Java sources.
	 * @return			{@link List} of {@link String}s containing class names (package.Class).
	 * @throws IOException		in case of IO error
	 */
	public List<String> findJavaClassesInSources(String sourceDirectory, String testSourceDirectory) throws IOException {
		List<String> javaSources = new ArrayList<String>();

		String testSourcePackageDurectory = getPackageDirectory(testSourceDirectory);

		File[] allEntriesInSourceDirectory = new File(getPackageDirectory(sourceDirectory)).listFiles();

		if(allEntriesInSourceDirectory == null)
			throw new IOException("Error listing files in directory \"" + getPackageDirectory(sourceDirectory) + "\"");

		Pattern javaClassSourcePattern = Pattern.compile(classNameMask.replaceAll("\\*", ".*") + "\\.java");

		for(File directoryEntry : allEntriesInSourceDirectory) {
			if(!directoryEntry.isFile() || !javaClassSourcePattern.matcher(directoryEntry.getName()).matches())
				continue;

			if(new File(testSourcePackageDurectory + File.separatorChar + directoryEntry.getName().replace(".java", "Test.java")).exists())
				continue;

			// Make sure there is no mess with path separators.
			assert directoryEntry.getPath().startsWith(sourceDirectory);

			// Make sure there is ".java" extension.
			assert directoryEntry.getPath().endsWith(".java");

			String javaClass = directoryEntry.getPath().substring(sourceDirectory.length() + 1, directoryEntry.getPath().length() - ".java".length());

			javaSources.add(javaClass.replace(File.separatorChar, '.'));
		}

		return javaSources;
	}

}