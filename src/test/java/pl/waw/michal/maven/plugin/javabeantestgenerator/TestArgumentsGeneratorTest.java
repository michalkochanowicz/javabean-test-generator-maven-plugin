package pl.waw.michal.maven.plugin.javabeantestgenerator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.util.*;

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

		// Double + double

		Assert.assertEquals(
			Arrays.asList("4.9E-324", "0", "null", "1.7976931348623157E308"),
			testArgumentsGenerator.getArguments(Double.class)
		);

		Assert.assertEquals(
			Arrays.asList("4.9E-324", "0", "1.7976931348623157E308"),
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

		String[] actuals = arguments.toArray(new String[0]);

		Assert.assertArrayEquals(
			new String[] {"\"test\"", "null" },
			actuals
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

		// Unexpected object with no zero-arguments constructor

		Assert.assertArrayEquals(
			new String[] { "null" },
			testArgumentsGenerator.getArguments(BufferedWriter.class).toArray(new String[0])
		);

	}

}

