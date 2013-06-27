package ct.XmlCodeGenerat;

public class Element {
	public String type;
	public String name;
	public String xmlName;
	public String childName="";

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

	public String getChildName() {
		return childName;
	}

	public void setChildName(String childName) {
		this.childName = childName;
	}

	
	@Override
	public String toString() {
		return "Element [type=" + type + ", name=" + name + ", xmlName="
				+ xmlName + "]";
	}
}
