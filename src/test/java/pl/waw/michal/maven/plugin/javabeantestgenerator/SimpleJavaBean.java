package pl.waw.michal.maven.plugin.javabeantestgenerator;

public class SimpleJavaBean {

	private byte[] byteArrayProperty;
	private short shortProperty;	// Not a real property - no setter.
	private int intProperty;
	private long longProperty;

	private Float floatWrapperProperty;
	private float floatProperty;

	private Double doubleWrapperProperty;
	private double doubleProperty;

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

	public Float getFloatWrapperProperty() {
		return floatWrapperProperty;
	}

	public void setFloatWrapperProperty(Float floatWrapperProperty) {
		this.floatWrapperProperty = floatWrapperProperty;
	}

	public float getFloatProperty() {
		return floatProperty;
	}

	public void setFloatProperty(float floatProperty) {
		this.floatProperty = floatProperty;
	}

	public Double getDoubleWrapperProperty() {
		return doubleWrapperProperty;
	}

	public void setDoubleWrapperProperty(Double doubleWrapperProperty) {
		this.doubleWrapperProperty = doubleWrapperProperty;
	}

	public double getDoubleProperty() {
		return doubleProperty;
	}

	public void setDoubleProperty(double doubleProperty) {
		this.doubleProperty = doubleProperty;
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