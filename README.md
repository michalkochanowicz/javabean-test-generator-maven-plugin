# javabean-test-generator-maven-plugin
Maven plugin generating unit tests for Java Beans

# Rationale

Generally, unit testing simple Java Beans doesn't seem to be of great value. But if you can have
these tests auto-generated, why not to have them?

These tests actually directed my attention to couple of questionable methods.

# Implementation

In theory, easiest way to test Java Beans would be with help of Reflection. Unfortunately, the Cobertura
doesn't see test performed through Reflection.

https://github.com/cobertura/cobertura/issues/225

This is why this plugin has been created. Tests are generated during generate-test-sources phase and then
compiled and executed together with hand-written tests. Test coverage reports can process them.

# Usage

Add following plugin to your `project/build/plugins` section. Check content of `configuration` tag and
adapt it to your needs.

```XML
<plugin>
	<groupId>pl.waw.michal.maven.plugin</groupId>
	<artifactId>javabean-test-generator-maven-plugin</artifactId>
	<version>1.0.0-SNAPSHOT</version>
	<executions>
		<execution>
			<goals>
				<goal>generate-tests</goal>
			</goals>
		</execution>
	</executions>
	<configuration>
		<!-- Required attribute. Specifies packages and classes to process. -->
		<packagesAndClassMasks>
			<pl.waw.michal.demo.command>*Command</pl.waw.michal.demo.command>
			<pl.waw.michal.demo.dsp>*</pl.waw.michal.demo.dsp>
		</packagesAndClassMasks>

		<!--
		Required attribute. Specifies testing library to use in generated tests. Valid
		values are JUnit and TestNG.
		-->
		<testingLibrary>TestNG</testingLibrary>

		<!-- Optional attribute. Specifies classes to be excluded from test generation. -->
		<classMasksToSkip>
			<param>pl.michal.waw.demo.command.SomeCommand</param>
			<param>pl.michal.waw.demo.dsp.*GeneratedDsp</param>
		</classMasksToSkip>
	</configuration>
</plugin>
```