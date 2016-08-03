package pl.waw.michal.maven.plugin.javabeantestgenerator;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Pattern;

/**
 * This class makes decision if given class should be tested. Decision is based on configuration given in
 * {@link Config} and other rules:
 * <ul>
 *         <li>Abstract classes are rejected.</li>
 *         <li>Non-public classes are rejected.</li>
 * </ul>
 */
public class ClassQualifier {

	private Collection<Pattern> classMasksToSkipRegexp;

	public ClassQualifier(Config config) {
		classMasksToSkipRegexp = new ArrayList<Pattern>(config.getClassMasksToSkip().size());

		for(String classMAskToSkip : config.getClassMasksToSkip()) {
			classMasksToSkipRegexp.add(Pattern.compile(classMAskToSkip.replaceAll("\\*", ".*")));
		}
	}

	public boolean shouldProcessThisClass(Class classToProcess) {
		if(!Modifier.isPublic(classToProcess.getModifiers()) || Modifier.isAbstract(classToProcess.getModifiers())) {
			return false;
		}

		try {
			classToProcess.getConstructor();
		} catch(NoSuchMethodException e) {
			return false;
		}

		for(Pattern pattern : classMasksToSkipRegexp) {
			if(pattern.matcher(classToProcess.getName()).matches()) {
				return false;
			}
		}

		return true;
	}

}