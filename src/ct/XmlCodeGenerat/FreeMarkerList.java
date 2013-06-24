package ct.XmlCodeGenerat;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.google.common.collect.Lists;

import freemarker.template.TemplateException;

public class FreeMarkerList {
	public static class Element {
		public String type;
		public String name;

		public Element(String type, String name) {
			super();
			this.type = type;
			this.name = name;
		}
		
		public String getType() {
			return type;
		}

		public String getName() {
			return name;
		}

	}
	
	public static class InerClass {
		public String name;
		public List<Element> elemens;
		public InerClass(String name, List<Element> elemens) {
			super();
			this.name = name;
			this.elemens = elemens;
		}
		public String getName() {
			return name;
		}
		public List<Element> getElemens() {
			return elemens;
		}
	}

	public static void createDoc() throws IOException, TemplateException {
		// List<UnitTestInfo> list = FreeMarkerList.initUserList();
		Element e1 = new Element("test1Type", "test1Name");
		Element e2 = new Element("test1Type", "test2Name");
		List<Element> axesList = Lists.newArrayList();
		List<InerClass> inerList = Lists.newArrayList();
		axesList.add(e1);
		axesList.add(e2);
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("elements", axesList);
		root.put("innerClasses", inerList);
		root.put("XMLROOT", "TestListResult");
		root.put("XMLROOTUPER", "TESTLISTRESULT");
		root.put("XMLROOTUPER", "TESTLISTRESULT");
		File file = new File(Env.APPLICATION_REAL_PATH + "/../output/out.txt");
		FreeMarkerUtil.getInstance().createFile("SrcTemplate.txt", root, file);

	}

	public static void main(String[] args) throws IOException,
			TemplateException {
		createDoc();
	}
}