package ct.XmlCodeGenerat;

public class Element {
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
