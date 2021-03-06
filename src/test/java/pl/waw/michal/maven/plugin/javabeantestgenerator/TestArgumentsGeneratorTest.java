package pl.waw.michal.maven.plugin.javabeantestgenerator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class TestArgumentsGeneratorTest {

	private TestArgumentsGenerator testArgumentsGenerator;

	@Before
	public void setUp() throws Exception {
		testArgumentsGenerator = new TestArgumentsGenerator();
	}

	@Test
	public void getArguments() throws Exception {

		// Boolean + boolean

		Assert.assertEquals(
			Arrays.asList("true", "null", "false"),
			testArgumentsGenerator.getArguments(Boolean.class)
		);

		Assert.assertEquals(
			Arrays.asList("true", "false"),
			testArgumentsGenerator.getArguments(boolean.class)
		);

		// Byte + byte

		Assert.assertEquals(
			Arrays.asList("-128", "0", "null", "127"),
			testArgumentsGenerator.getArguments(Byte.class)
		);

		Assert.assertEquals(
			Arrays.asList("-128", "0", "127"),
			testArgumentsGenerator.getArguments(byte.class)
		);

		// Long + long

		Assert.assertEquals(
			Arrays.asList("-9223372036854775808L", "0L", "null", "9223372036854775807L"),
			testArgumentsGenerator.getArguments(Long.class)
		);

		Assert.assertEquals(
			Arrays.asList("-9223372036854775808L", "0L", "9223372036854775807L"),
			testArgumentsGenerator.getArguments(long.class)
		);

		// Float + float

		Assert.assertEquals(
			Arrays.asList("-3.4028235E38f", "-1.4E-45f", "0.0f", "null", "1.4E-45f", "3.4028235E38f"),
			testArgumentsGenerator.getArguments(Float.class)
		);

		Assert.assertEquals(
			Arrays.asList("-3.4028235E38f", "-1.4E-45f", "0.0f", "1.4E-45f", "3.4028235E38f"),
			testArgumentsGenerator.getArguments(float.class)
		);

		// Double + double

		Assert.assertEquals(
			Arrays.asList("-1.7976931348623157E308d", "-4.9E-324d", "0.0d", "null", "4.9E-324d", "1.7976931348623157E308d"),
			testArgumentsGenerator.getArguments(Double.class)
		);

		Assert.assertEquals(
			Arrays.asList("-1.7976931348623157E308d", "-4.9E-324d", "0.0d", "4.9E-324d", "1.7976931348623157E308d"),
			testArgumentsGenerator.getArguments(double.class)
		);

		// Character + char

		Assert.assertEquals(
			Arrays.asList("'\\u0000'", "0", "null", "'\\uFFFF'"),
			testArgumentsGenerator.getArguments(Character.class)
		);

		Assert.assertEquals(
			Arrays.asList("'\\u0000'", "0", "'\\uFFFF'"),
			testArgumentsGenerator.getArguments(char.class)
		);

		// String

		List<String> arguments = testArgumentsGenerator.getArguments(String.class);

		Assert.assertArrayEquals(
			new String[] {"\"test\"", "null" },
			arguments.toArray(new String[0])
		);

		// Set

		Assert.assertArrayEquals(
			new String[] { "new java.util.HashSet()", "null" },
			testArgumentsGenerator.getArguments(Set.class).toArray(new String[0])
		);

		// List

		Assert.assertArrayEquals(
			new String[] { "new java.util.ArrayList()", "null" },
			testArgumentsGenerator.getArguments(List.class).toArray(new String[0])
		);

		// Collection

		Assert.assertArrayEquals(
			new String[] { "new java.util.ArrayList()", "null" },
			testArgumentsGenerator.getArguments(Collection.class).toArray(new String[0])
		);

		// Array

		Assert.assertArrayEquals(
			new String[] { "new byte[0]", "null" },
			testArgumentsGenerator.getArguments(byte[].class).toArray(new String[0])
		);

		// Unexpected object with zero-argument constructor

		Assert.assertArrayEquals(
			new String[] { "new pl.waw.michal.maven.plugin.javabeantestgenerator.BeanWithZeroArgumentConstructor()", "null" },
			testArgumentsGenerator.getArguments(BeanWithZeroArgumentConstructor.class).toArray(new String[0])
		);

		// Unexpected object with inherited zero-argument constructor

		Assert.assertArrayEquals(
			new String[] { "new pl.waw.michal.maven.plugin.javabeantestgenerator.BeanWithInheritedZeroArgumentConstructor()", "null" },
			testArgumentsGenerator.getArguments(BeanWithInheritedZeroArgumentConstructor.class).toArray(new String[0])
		);

		// Unexpected object of inner class with inherited zero-argument constructor
		// Default zero-argument constructor of inner class is actually one-argument constructor and we don't
		// handle this situation as too rare.

		Assert.assertArrayEquals(
			new String[] { "null" },
			testArgumentsGenerator.getArguments(BeanWithInheritedZeroArgumentConstructor.InnerClass.class).toArray(new String[0])
		);

		// Unexpected object with no zero-arguments constructor

		Assert.assertArrayEquals(
			new String[] { "null" },
			testArgumentsGenerator.getArguments(BufferedWriter.class).toArray(new String[0])
		);

	}

}

