package pl.waw.michal.maven.plugin.javabeantestgenerator;

public class SimpleJavaBean {

	private byte[] byteArrayProperty;
	private short shortProperty;	// Not a real property - no setter.
	private int intProperty;
	private long longProperty;

	private BeanWithZeroArgumentConstructor beanWithZeroArgumentConstructor;

	private InnerClass innerClassProperty;

	public byte[] getByteArrayProperty() {
		return byteArrayProperty;
	}

	public void setByteArrayProperty(byte[] byteArrayProperty) {
		this.byteArrayProperty = byteArrayProperty;
	}

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

	public InnerClass getInnerClassProperty() {
		return innerClassProperty;
	}

	public void setInnerClassProperty(InnerClass innerClassProperty) {
		this.innerClassProperty = innerClassProperty;
	}

	class InnerClass {

	}

}