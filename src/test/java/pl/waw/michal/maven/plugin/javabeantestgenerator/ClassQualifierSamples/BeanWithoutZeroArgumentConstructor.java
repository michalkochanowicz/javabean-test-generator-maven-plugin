package pl.waw.michal.maven.plugin.javabeantestgenerator.ClassQualifierSamples;

public class BeanWithoutZeroArgumentConstructor {

	private int property;

	public BeanWithoutZeroArgumentConstructor(int property) {
		this.property = property;
	}

	public int getProperty() {
		return property;
	}

	public void setProperty(int property) {
		this.property = property;
	}

}