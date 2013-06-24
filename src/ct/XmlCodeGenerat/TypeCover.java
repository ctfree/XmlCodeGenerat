package ct.XmlCodeGenerat;

import java.util.Map;

import com.google.common.collect.Maps;

public class TypeCover {
	private static Map<String, String> java2cpp = Maps.newHashMap();

	static {
		java2cpp.put("class java.lang.String", "string");
		java2cpp.put("boolean", "bool");
	}
	
	public static String getListType(String name)
	{
		String[] entys=name.split("[\\.\\$<>]");
		return "list<"+entys[entys.length-1]+">";
	}

	public static String getCppType(String name) {
		if(name.indexOf("java.util")!=-1)
		{
			return getListType(name);
		}
		String type = java2cpp.get(name);
		if (type == null) {
			return name;
		} else {
			return type;
		}
	}
	
	public static void main(String[] args) {
		String test ="java.util.List<com.onest.webifc.data.bucket.DeleteObjects$ObjectUnit>";
		System.out.println(getListType(test));
	}

}
