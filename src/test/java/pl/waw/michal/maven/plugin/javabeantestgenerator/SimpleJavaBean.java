package pl.waw.michal.maven.plugin.javabeantestgenerator;

public class SimpleJavaBean {

	private short shortProperty;
	private int intProperty;
	private long longProperty;

	private BeanWithZeroArgumentConstructor beanWithZeroArgumentConstructor;

	public short getShortProperty() {
		return shortProperty;
	}

	public int getIntProperty() {
		return intProperty;
	}

	public void setIntProperty(int intProperty) {
		this.intProperty = intProperty;
	}

	public void setLongProperty(long longProperty) {
		this.longProperty = longProperty;
	}

	public long getSomeMagickNumberWhichIsNotProperty() {
		return shortProperty+intProperty+longProperty;
	}

	public BeanWithZeroArgumentConstructor getBeanWithZeroArgumentConstructor() {
		return beanWithZeroArgumentConstructor;
	}

	public void setBeanWithZeroArgumentConstructor(BeanWithZeroArgumentConstructor BeanWithZeroArgumentConstructor) {
		this.beanWithZeroArgumentConstructor = BeanWithZeroArgumentConstructor;
	}

}