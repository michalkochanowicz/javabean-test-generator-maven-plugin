package pl.waw.michal.maven.plugin.javabeantestgenerator;

import java.util.*;

/**
 * Generates list of arguments which should be passed to tested getter/setter pair. It is important to test at least
 * two values to make sure that changes are actually performed.
 */
public class TestArgumentsGenerator {

	private Map<Class, List<String>> classArgumentsMap;

	public TestArgumentsGenerator() throws NoSuchFieldException, IllegalAccessException {
		classArgumentsMap = new HashMap<Class, List<String>>();

		for(Class c : new Class[] { Byte.class, Short.class, Integer.class, Float.class, Double.class } ) {

			classArgumentsMap.put(c,
				Arrays.asList(
					"" + c.getField("MIN_VALUE").get(c),
					"0",
					"null",
					"" + c.getField("MAX_VALUE").get(c)
				)
			);

			classArgumentsMap.put((Class)c.getField("TYPE").get(c),
				Arrays.asList(
					"" + c.getField("MIN_VALUE").get(c),
					"0",
					"" + c.getField("MAX_VALUE").get(c)
				)
			);
		}

		classArgumentsMap.put(Long.class,
			Arrays.asList(
				Long.MIN_VALUE + "L",
				"0L",
				"null",
				Long.MAX_VALUE + "L"
			)
		);

		classArgumentsMap.put(long.class,
			Arrays.asList(
				Long.MIN_VALUE + "L",
				"0L",
				Long.MAX_VALUE + "L"
			)
		);

		classArgumentsMap.put(Character.class,
			Arrays.asList(
				"'\\u0000'",
				"0",
				"null",
				"'\\uFFFF'"
			)
		);

		classArgumentsMap.put(char.class,
			Arrays.asList(
				"'\\u0000'",
				"0",
				"'\\uFFFF'"
			)
		);

		classArgumentsMap.put(Boolean.class,
			Arrays.asList(
				"true",
				"null",
				"false"
			)
		);

		classArgumentsMap.put(boolean.class,
			Arrays.asList(
				"true",
				"false"
			)
		);

		classArgumentsMap.put(String.class, Arrays.asList("\"test\"", "null"));
	}

	/**
	 * <p>Generates list of argument suitable for testing setter and getter of specified property.
	 *
	 * <p>Arguments are returned as {@link String}s ready to be placed in Java source code as literal initializer.
	 * Values are generated as follows:
	 * <ul>
	 *         <li>For primitive types: min value, 0 and max value</li>
	 *         <li>For wrappers for primitive types: min value, 0, null, max value </li>
	 *         <li>For char: minimum unicode code point, '0' and maximum unicode code point</li>
	 *         <li>For Character: min value, '0', null and max value</li>
	 *         <li>For java.lang.String: "test" literal and null</li>
	 *         <li>For Collection, List and Set: empty collection (ArrayList or HashSet) and null</li>
	 *         <li>For other classes: simply constructed object and null</li>
	 * </ul>
	 * The values may represent primitives or objects. In case of object they must be proper constructor calls with
	 * any arguments if necessary.
	 *
	 * <p>For objects the generator looks first for zero-argument constructor.
	 *
	 * <p>If it is not possible to offer more arguments, list with only one value, "null", will be returned and
	 * warning should be logged by the caller.
	 *
	 * <p> Examples of return values:
	 * <ul>
	 *         <li>For "int" property: "-123", "0", "345"</li>
	 *         <li>For "java.lang.Integer" property: "-123", "0", "null", "345"</li>
	 *         <li>For "java.lang.String" property: "\"test\"", "\"\""</li>
	 *         <li>For "java.util.Date" property: "new Date()", "new Date(123)"</li>
	 * </ul>
	 *
	 * @param type	type of property for which test arguments are needed.
	 * @return	{@link List} of {@link String}s containing Java source code-compatible values
	 */
	public List<String> getArguments(Class type) throws NoSuchMethodException {
		List<String> arguments = classArgumentsMap.get(type);

		if(arguments != null) {
			return arguments;
		}

		if(Set.class.isAssignableFrom(type)) {
			return Arrays.asList("new java.util.HashSet()", "null");
		} else if(List.class.isAssignableFrom(type)) {
			return Arrays.asList("new java.util.ArrayList()", "null");
		} else if(Collection.class.isAssignableFrom(type)) {
			return Arrays.asList("new java.util.ArrayList()", "null");
		} else {
			try {
				type.getConstructor();
				return Arrays.asList(
					"new " + type.getName() + "()",
					"null"
				);
			} catch(NoSuchMethodException e) {
				// OK, we will go on.
			}
		}

		return Collections.singletonList("null");
	}

}