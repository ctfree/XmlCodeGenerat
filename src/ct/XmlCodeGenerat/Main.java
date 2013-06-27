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
	private static boolean test=true;
	private static boolean formatH=true;
	private static boolean formatCpp=true;
	private static boolean formatTest=true;
	private static String testPath= Env.APPLICATION_REAL_PATH+"/../output/";
	private static String workPath= "E:/onest/Onest4ClientLib/src/OnestClient/";

	private static int Delp= 0;
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
		File file = new File(workPath+parentDir+classInfo.getClassName()+".h");
		FreeMarkerUtil.getInstance().createFile("SrcTemplate.ftl", root, file);
		
		file = new File(workPath+parentDir+classInfo.getClassName()+".cpp");
		FreeMarkerUtil.getInstance().createFile("SrcCpp.ftl", root, file);
		
		file = new File(workPath+"../unittest/"+"Test"+classInfo.getClassName()+".cpp");
		FreeMarkerUtil.getInstance().createFile("UnitTest.ftl", root, file);
	}
	
	public static void formatAlone(ClassInfo classInfo,String parentDir) throws IOException, TemplateException
	{
        System.out.println(classInfo);
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("elements", classInfo.getElements());
		root.put("innerClasses", classInfo.getInnerClasses());
		root.put("className", classInfo.getClassName());
		root.put("CLASSNAMEUPPER", classInfo.getClassName().toUpperCase());
		root.put("xmlRoot", classInfo.getXmlRoot());
		root.put("parentDir", parentDir);
		root.put("this", classInfo);
		root.put("ComMap", TypeCover.getCppValueMap());
		parentDir = parentDir+"/";
		File file = null;
		if(formatH)
		{
			file = new File(workPath+parentDir+classInfo.getClassName()+".h");
			FreeMarkerUtil.getInstance().createFile("SrcTemplateAlone.ftl", root, file);
		}
		if(formatCpp)
		{
			file = new File(workPath+parentDir+classInfo.getClassName()+".cpp");
			FreeMarkerUtil.getInstance().createFile("SrcCpp.ftl", root, file);
		}
		if(formatTest&&Delp==0)
		{
			file = new File(workPath+"../unittest/"+"Test"+classInfo.getClassName()+".cpp");
			FreeMarkerUtil.getInstance().createFile("UnitTest.ftl", root, file);
		}
		Delp++;
		for (ClassInfo innerClass : classInfo.getInnerClasses()) {
			formatAlone(innerClass,"model");
		}
	}
	
	public static void formatAlone(Class<?> clazz,String parentDir) throws IOException, TemplateException
	{
		ClassInfo classInfo =ClassInfo.createFormClass(clazz);
		formatAlone(classInfo,parentDir);
	}
	
	public static void format(List<Class<?>> classList,String parentDir) throws IOException, TemplateException
	{    for (Class<?> class1 : classList) {
		    System.out.println("format class "+class1.getName());
		    Delp = 0;
		    formatAlone(class1,parentDir);
        	includeStr +="#include \""+parentDir+"/"+class1.getSimpleName()+".h\"\n";
		}
    }
	
	public static void formatAll() throws IOException, TemplateException
	{
		List<Class<?>> classList = Lists.newArrayList();
		classList.add(DeleteObjects.class);
		classList.add(VersioningConfiguration.class);
		format(classList,"model");
		classList.clear();
	
		classList.add(LifecycleConfiguration.class);
		format(classList,"req");
		classList.clear();
		
//		classList.add(ListMultiPartResult.class);
		classList.add(DeleteObjectsResult.class);
//		classList.add(ListObjectsResult.class);
		classList.add(ListObjectVersionsResult.class);
		classList.add(CopyObjectResult.class);
		format(classList,"result");
		classList.clear();
        System.out.println(includeStr);
	}

	
	public static void formatConflict() throws IOException, TemplateException
	{
		List<Class<?>> classList = Lists.newArrayList();

		classList.add(ListObjectsResult.class);
		classList.add(ListObjectVersionsResult.class);
		format(classList,"result");
		classList.clear();
        System.out.println(includeStr);
	}

	public static void main(String[] args) throws IOException, TemplateException {
		test=false;
		formatH=true;
	    formatCpp=true;
		formatTest=true;
		if(test)
        {
        	workPath= testPath;
        	formatAlone(ListObjectVersionsResult.class,"req");

        }
        else
        {
    		formatAll();	
        }
	}
}
