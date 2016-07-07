package pl.waw.michal.maven.plugin.javabeantestgenerator;

import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;
import pl.waw.michal.maven.plugin.javabeantestgenerator.generator.TestNGTestGenerator;

import java.beans.IntrospectionException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * Maven Mojo for generating Java Bean unit tests.
 */
@Mojo(name = "generate-tests",
	defaultPhase = LifecyclePhase.GENERATE_TEST_SOURCES,
	requiresDependencyResolution = ResolutionScope.COMPILE_PLUS_RUNTIME
)
public class JavaBeanTestNGGeneratorMojo extends AbstractMojo {

	/**
	 * The Maven project.
	 *
	 * @parameter property="project"
	 * @required
	 * @readonly
	 */
	@Parameter(property = "project")
	private MavenProject project;

	/**
	 * Map of package names to class name masks.
	 * 
	 * <pre>
	 * &lt;configuration&gt;
	 * 	&lt;packagesAndClassMasks&gt;
	 *		&lt;pl.waw.michal.demo.command&gt;*Command&lt;/pl.waw.michal.demo.command&gt;
	 *		&lt;pl.waw.michal.demo.dsp&gt;*&lt;/pl.waw.michal.demo.dsp&gt;
	 *	&lt;/packagesAndClassMasks&gt;
	 * &lt;/configuration&gt;
	 * </pre>
	 */
	@Parameter(required = true)
	private Map<String, String> packagesAndClassMasks;

	/**
	 * Collection of full class names (package.Class). Matching classes will be excluded from testing. "*" may be
	 * used to match any number of characters.
	 *
	 * <pre>
	 * &lt;configuration&gt;
	 * 	&lt;classMasksToSkip&gt;
	 * 	    &lt;param&gt;pl.michal.waw.demo.command.SomeCommand&lt;/param&gt;
	 * 	    &lt;param&gt;pl.michal.waw.demo.dsp.*GeneratedDsp&lt;/param&gt;
	 * 	&lt;/classMasksToSkip&gt;
	 * &lt;/configuration&gt;
	 * </pre>
	 */
	@Parameter(required = false)
	private Collection<String> classMasksToSkip;

	/**
	 * Specifies which testing library should be used in generated test. JUnit and TestNG are available.
	 */
	@Parameter(required = true)
	private TestingLibrary testingLibrary;

	public void execute() throws MojoExecutionException {

		String generatedTestSourcesDirectory = project.getBuild().getDirectory() + File.separator + "generated-test-sources";
		project.getExecutionProject().getTestCompileSourceRoots().add(generatedTestSourcesDirectory);

		try {
			Collection<PackageClassMask> packageClassMaskCollection = new ArrayList<PackageClassMask>();
			for(Map.Entry<String, String> entry : packagesAndClassMasks.entrySet()) {
				packageClassMaskCollection.add(new PackageClassMask(entry.getKey(), entry.getValue()));
			}

			Config config = new Config(
				project.getBuild().getSourceDirectory(),
				project.getBuild().getOutputDirectory(),
				generatedTestSourcesDirectory,
				project.getExecutionProject().getTestClasspathElements(),
				new TestNGTestGenerator(new TestArgumentsGenerator()),
				packageClassMaskCollection,
				classMasksToSkip
			);

			new CodeProcessor(new ClassQualifier(config), new JavaBeanProcessor()).process(config);
		} catch(IOException e) {
			getLog().error(e.getMessage(), e);
			throw new MojoExecutionException(e.getMessage(), e);
		} catch(ClassNotFoundException e) {
			getLog().error(e.getMessage(), e);
			throw new MojoExecutionException(e.getMessage(), e);
		} catch(IntrospectionException e) {
			getLog().error(e.getMessage(), e);
			throw new MojoExecutionException(e.getMessage(), e);
		} catch(NoSuchMethodException e) {
			getLog().error(e.getMessage(), e);
			throw new MojoExecutionException(e.getMessage(), e);
		} catch(DependencyResolutionRequiredException e) {
			getLog().error(e.getMessage(), e);
			throw new MojoExecutionException(e.getMessage(), e);
		} catch(IllegalAccessException e) {
			getLog().error(e.getMessage(), e);
			throw new MojoExecutionException(e.getMessage(), e);
		} catch(NoSuchFieldException e) {
			getLog().error(e.getMessage(), e);
			throw new MojoExecutionException(e.getMessage(), e);
		}

	}

}