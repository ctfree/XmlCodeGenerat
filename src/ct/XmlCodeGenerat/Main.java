package ct.XmlCodeGenerat;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.onest.webifc.data.bucket.DeleteObjects;
import com.onest.webifc.data.bucket.DeleteObjectsResult;
import com.onest.webifc.data.bucket.LifeCycle;
import com.onest.webifc.data.bucket.LifecycleConfiguration;
import com.onest.webifc.data.bucket.ListMultiPartResult;
import com.onest.webifc.data.bucket.ListObjectVersionsResult;
import com.onest.webifc.data.bucket.ListObjectsResult;
import com.onest.webifc.data.bucket.VersioningConfiguration;
import com.onest.webifc.data.object.CopyObjectResult;

import freemarker.template.TemplateException;

public class Main {
	private static String includeStr = "";

	public static void format(Class<?> clazz,String parentDir) throws IOException, TemplateException
	{
		ClassInfo classInfo =ClassInfo.createFormClass(clazz);
        System.out.println(classInfo);
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("elements", classInfo.getElements());
		root.put("innerClasses", classInfo.getInnerClasses());
		root.put("className", classInfo.getClassName());
		root.put("CLASSNAMEUPPER", classInfo.getClassName().toUpperCase());
		root.put("xmlRoot", classInfo.getXmlRoot());
		root.put("parentDir", parentDir);
		parentDir = parentDir+"/";
		File file = new File(Env.APPLICATION_REAL_PATH + "/../output/"+parentDir+classInfo.getClassName()+".h");
		FreeMarkerUtil.getInstance().createFile("SrcTemplate.ftl", root, file);
		
		file = new File(Env.APPLICATION_REAL_PATH + "/../output/"+parentDir+classInfo.getClassName()+".cpp");
		FreeMarkerUtil.getInstance().createFile("SrcCpp.ftl", root, file);
		
		file = new File(Env.APPLICATION_REAL_PATH + "/../output/Test"+classInfo.getClassName()+".cpp");
		FreeMarkerUtil.getInstance().createFile("UnitTest.ftl", root, file);
	}
	
	public static void format(List<Class<?>> classList,String parentDir) throws IOException, TemplateException
	{    for (Class<?> class1 : classList) {
		    System.out.println("format class "+class1.getName());
        	format(class1,parentDir);
        	includeStr +="#include "+parentDir+"/"+class1.getSimpleName()+".h\n";
		}
    }
	
	public static void formatAll() throws IOException, TemplateException
	{
		List<Class<?>> classList = Lists.newArrayList();
		classList.add(DeleteObjects.class);
		classList.add(DeleteObjectsResult.class);
		format(classList,"model");
		classList.clear();
		
		classList.add(LifeCycle.class);
		classList.add(LifecycleConfiguration.class);
		format(classList,"req");
		classList.clear();
		
		classList.add(ListMultiPartResult.class);
		classList.add(DeleteObjectsResult.class);
		classList.add(ListObjectsResult.class);
		classList.add(ListObjectVersionsResult.class);
		classList.add(CopyObjectResult.class);
		format(classList,"result");
		classList.clear();
        System.out.println(includeStr);
	}


	public static void main(String[] args) throws IOException, TemplateException {

//		formatAll();
		format(DeleteObjectsResult.class,"result");
	}
}
