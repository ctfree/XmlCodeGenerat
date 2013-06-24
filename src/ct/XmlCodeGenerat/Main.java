package ct.XmlCodeGenerat;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.onest.webifc.data.bucket.DeleteObjects;
import com.onest.webifc.data.bucket.ListObjectsResult;

import freemarker.template.TemplateException;

public class Main {

	public static void main(String[] args) throws IOException, TemplateException {
		ClassInfo classInfo =ClassInfo.createFormClass(ListObjectsResult.class);
        System.out.println(classInfo);
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("elements", classInfo.getElements());
		root.put("innerClasses", classInfo.getInnerClasses());
		root.put("className", classInfo.getClassName());
		root.put("CLASSNAMEUPPER", classInfo.getClassName().toUpperCase());
		root.put("xmlRoot", classInfo.getXmlRoot());
		File file = new File(Env.APPLICATION_REAL_PATH + "/../output/out.txt");
		FreeMarkerUtil.getInstance().createFile("SrcTemplate.txt", root, file);
	}
}
