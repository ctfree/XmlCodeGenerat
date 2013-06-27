package ct.XmlCodeGenerat;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.google.common.collect.Lists;
import com.onest.webifc.data.bucket.DeleteObjects;

public class ClassInfo {
	private List<ClassInfo> innerClasses= Lists.newArrayList();
	private List<Element> elements = Lists.newArrayList();
	private String className;
	private String xmlRoot;
	private Class<?> clazz;
	
	public ClassInfo(Class<?> clazz)
	{
		this.clazz = clazz;
	}

	public List<ClassInfo> getInnerClasses() {
		return innerClasses;
	}

	public void setInnerClasses(List<ClassInfo> innerClasses) {
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
	
	
//	public static class InerClass {
//		public String name;
//		public List<Element> elements;
//
//		public InerClass(String name, List<Element> elements) {
//			super();
//			this.name = name;
//			this.elements = elements;
//		}
//
//		public String getName() {
//			return name;
//		}
//
//		public List<Element> getElements() {
//			return elements;
//		}
//
//		@Override
//		public String toString() {
//			return "InerClass [name=" + name + ", elements=" + elements + "]";
//		}
//
//	}

	public  String fir2Upper(String property) {
		String firChar = property.substring(0, 1);
		String upperChar = firChar.toUpperCase();
		String res = upperChar + property.substring(1);
		return res;
	}

	public  String fir2Lower(String property) {
		String firChar = property.substring(0, 1);
		String lowerChar = firChar.toLowerCase();
		String res = lowerChar + property.substring(1);
		return res;
	}
	
	public Class<?> classFromName(String name)
	{
		Class clazz1 = null;
		try {
			clazz1 = Class.forName(name);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("exception"+name);
			e.printStackTrace();
			System.exit(-1);
		}
		return clazz1;
	}

	public void analyClassInfo() {
		Method[] methods = clazz.getMethods();
		Field[] field = clazz.getDeclaredFields();
		String fieldType = null;
		for (int i = 0; i < field.length; i++) {
			String name = null;
			String xmlName = null;
			String childName = null;
			boolean noNeed = false;
			fieldType = field[i].getGenericType().toString();
			name = field[i].getName();
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
				if(anno instanceof XmlTransient )
				{
					     noNeed = true; 
                         break;
				}	
					
				if(anno instanceof XmlElementWrapper )
				{
					XmlElementWrapper xmlElementWrapper = (XmlElementWrapper) anno;
					xmlName =xmlElementWrapper.name();
				}
				if (anno instanceof XmlElement) {
					XmlElement xmlElement = (XmlElement) anno;
					System.out.println("XmlElement " + xmlElement.name());
					xmlName = xmlElement.name();
					if(fieldType.contains("java.util.List"))
					{
						String[] entys=fieldType.split("[<>]");
						String className=entys[entys.length-1];
						System.out.println("XmlElement type " +fieldType);
						ClassInfo inerClass = new ClassInfo(classFromName(className));
						inerClass.analyClassInfo();
						innerClasses.add(inerClass);
					}
					
					if(fieldType.contains("class com.onest.webifc"))
					{
						ClassInfo inerClass = new ClassInfo(field[i].getType());
						inerClass.analyClassInfo();
						innerClasses.add(inerClass);
					}
				} else if (anno instanceof XmlElements) {
					XmlElements xmlElements = (XmlElements) anno;
					XmlElement xmlElement = xmlElements.value()[0];
					System.out.println("XmlElement " + xmlElement.name());
					if(xmlName == null)
					{
						xmlName="";
					}
					childName =xmlElement.name();
					ClassInfo inerClass = new ClassInfo(xmlElement.type());
					inerClass.analyClassInfo();
					inerClass.setXmlRoot(xmlElement.name());
					innerClasses.add(inerClass);
					
				}
				}
			}
			if(noNeed)
			{
				continue;
			}
	
			Element element = new Element(TypeCover.getCppType(fieldType),
					name, xmlName);
			if(childName!=null)
				element.setChildName(childName);
			elements.add(element);
		}
		
		xmlRoot = getXmlRootFromClass();
		className = clazz.getSimpleName();
	}

	public String getXmlRootFromClass() {
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
		return clazz.getSimpleName();
	}

	public static ClassInfo createFormClass(Class<?> clazz) {
		ClassInfo classInfo = new ClassInfo(clazz);
		classInfo.analyClassInfo();
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
