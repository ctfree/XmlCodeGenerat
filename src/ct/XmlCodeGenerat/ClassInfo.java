package ct.XmlCodeGenerat;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Lists;
import com.onest.webifc.data.bucket.DeleteObjects;

public class ClassInfo {
	private List<InerClass> innerClasses;
	private List<Element> elements;
	private String className;
	private String xmlRoot;

	public static List<InerClass> staticInnerClasses = Lists.newArrayList();

	public List<InerClass> getInnerClasses() {
		return innerClasses;
	}

	public void setInnerClasses(List<InerClass> innerClasses) {
		this.innerClasses = innerClasses;
	}

	public List<Element> getElements() {
		return elements;
	}

	public void setElements(List<Element> elements) {
		this.elements = elements;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getXmlRoot() {
		return xmlRoot;
	}

	public void setXmlRoot(String xmlRoot) {
		this.xmlRoot = xmlRoot;
	}
	
	

	public static class Element {
		public String type;
		public String name;
		public String xmlName;

		public Element(String type, String name, String xmlName) {
			super();
			this.type = type;
			this.name = name;
			this.xmlName = xmlName;
		}

		public String getType() {
			return type;
		}

		public String getName() {
			return name;
		}

		public String getXmlName() {
			return xmlName;
		}

		@Override
		public String toString() {
			return "Element [type=" + type + ", name=" + name + ", xmlName="
					+ xmlName + "]";
		}

	}

	public static class InerClass {
		public String name;
		public List<Element> elements;

		public InerClass(String name, List<Element> elements) {
			super();
			this.name = name;
			this.elements = elements;
		}

		public String getName() {
			return name;
		}

		public List<Element> getElements() {
			return elements;
		}

		@Override
		public String toString() {
			return "InerClass [name=" + name + ", elements=" + elements + "]";
		}

	}

	public static String fir2Upper(String property) {
		String firChar = property.substring(0, 1);
		String upperChar = firChar.toUpperCase();
		String res = upperChar + property.substring(1);
		return res;
	}

	public static String fir2Lower(String property) {
		String firChar = property.substring(0, 1);
		String lowerChar = firChar.toLowerCase();
		String res = lowerChar + property.substring(1);
		return res;
	}

	public static List<Element> getElementsFromClass(Class<?> clazz) {
		List<Element> elements = Lists.newArrayList();
		Method[] methods = clazz.getMethods();
		Field[] field = clazz.getDeclaredFields();
		String fieldType = null;
		String name = null;
		String xmlName = null;
		for (int i = 0; i < field.length; i++) {
			fieldType = field[i].getGenericType().toString();
			name = field[i].getName();
			System.out.println(name + " " + field[i].getType() + " "
					+ fieldType);
			String methodName = fir2Upper(field[i].getName());

			Method getMethod = null;
			for (Method method : methods) {
				if (method.getName().endsWith(methodName) && method.getParameterTypes().length==0) {
					getMethod = method;
					break;
				}
			}

			if (getMethod == null) {
				continue;
			}

			Annotation[] annos = getMethod.getAnnotations();
			System.out.println(methodName + " " + annos.length);
			if (annos.length > 0) {
				for (Annotation anno : annos) {
				if (anno instanceof XmlElement) {
					XmlElement xmlElement = (XmlElement) anno;
					System.out.println("XmlElement " + xmlElement.name());
					xmlName = xmlElement.name();
				} else if (anno instanceof XmlElements) {
					XmlElements xmlElements = (XmlElements) anno;
					XmlElement xmlElement = xmlElements.value()[0];
					System.out.println("XmlElement " + xmlElement.name());
					xmlName = xmlElement.name();
					List<Element> inerElements = getElementsFromClass(xmlElement
							.type());
					String innerName = xmlElement.type().getSimpleName();
					InerClass inerClass = new InerClass(innerName, inerElements);
					staticInnerClasses.add(inerClass);
				}
				}
			}
			if(fieldType.contains("class com.onest.webifc"))
			{
				List<Element> inerElements = getElementsFromClass(field[i].getType());
				String innerName =field[i].getType().getSimpleName();
				InerClass inerClass = new InerClass(innerName, inerElements);
				staticInnerClasses.add(inerClass);
			}
			Element element = new Element(TypeCover.getCppType(fieldType),
					name, xmlName);
			elements.add(element);
		}
		return elements;

	}

	public static String getXmlRootFromClass(Class<?> clazz) {
		Annotation[] annos = clazz.getAnnotations();
		System.out.println(" " + annos.length);
		if (annos.length > 0) {
			for (Annotation anno : annos) {
				if (anno instanceof XmlRootElement) {
					XmlRootElement xmlRootElement = (XmlRootElement)anno;
					System.out.println("XmlElement " + xmlRootElement.name());
					return xmlRootElement.name();
				}	
			}
		}
		return null;
	}

	public static ClassInfo createFormClass(Class<?> clazz) {
		staticInnerClasses.clear();
		ClassInfo classInfo = new ClassInfo();
		List<Element> elements = getElementsFromClass(clazz);
		String xmlroot = getXmlRootFromClass(clazz);

		classInfo.setElements(elements);
		classInfo.setXmlRoot(xmlroot);
		classInfo.setClassName(clazz.getSimpleName());
		classInfo.setInnerClasses(staticInnerClasses);

		return classInfo;
	}

	public static void main(String[] args) {
		// createFormClass();
		ClassInfo classInfo = createFormClass(DeleteObjects.class);
		
		return;
	}

	@Override
	public String toString() {
		return "ClassInfo [innerClasses=" + innerClasses + ", elements="
				+ elements + ", className=" + className + ", xmlRoot="
				+ xmlRoot + "]";
	}

}
