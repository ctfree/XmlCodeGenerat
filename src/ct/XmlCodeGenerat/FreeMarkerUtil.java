package ct.XmlCodeGenerat;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

/**
 * FreeMarker操作类
 * 
 * @author sunjun
 * 
 */
public class FreeMarkerUtil {

 /**
  * 定义FreeMarker Configuration对象config
  */
 private Configuration config = null;

 /**
  * 编码
  */
 private String encoding = "UTF-8";

 /**
  * FreeMarkerUtil对象FREEMARKER
  */
 private static final FreeMarkerUtil FREEMARKER = new FreeMarkerUtil();

 /**
  * 构造方法
  * 
  * @throws IOException
  */
 private FreeMarkerUtil() {
  try {
   config = new Configuration();
   config.setDirectoryForTemplateLoading(new File(
     Env.APPLICATION_REAL_PATH));
   // config.setTemplateLoader(getTemplateLoader(servletContext));
   config
     .setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
   config.setDefaultEncoding(encoding);
   config.setObjectWrapper(new DefaultObjectWrapper());
  } catch (IOException e) {
   e.printStackTrace();
  }
 }

 /**
  * 得到FreeMarkerUtil对象
  * 
  * @return FreeMarkerUtil对象
  */
 public static FreeMarkerUtil getInstance() {
  return FREEMARKER;
 }

 /**
  * @param config
  *            the config to set
  */
 public void setConfig(Configuration config) {
  this.config = config;
 }

 /**
  * 设置编码
  * 
  * @param encoding
  *            the encoding to set
  */
 public void setEncoding(String encoding) {
  this.encoding = encoding;
 }

 /**
  * 生成静态页面
  * 
  * @param ftlFile
  *            模板文件(相对根目录的绝对路径，以/开头)
  * @param data
  *            数据Map
  * @param file
  *            生成的文件
  * @throws IOException
  * @throws TemplateException
  */
 public void createFile(String ftlFile, Map data, File file)
   throws IOException, TemplateException {
  Writer out = null;
  FileOutputStream output = null;
  try {
   Template template = config.getTemplate(ftlFile);
   output = new FileOutputStream(file);
   out = new OutputStreamWriter(output, encoding);
   template.process(data, out);
  } catch (Exception ex) {
   ex.printStackTrace();
  } finally {
   // 关闭out output
   if (out != null) {
    out.flush();
    out.close();
   }
   if (output != null) {
    output.flush();
    output.close();
   }
  }
 }

 /**
  * 输出到字符串
  * 
  * @param ftlFile
  *            模板文件(相对根目录的绝对路径，以/开头)
  * @param data
  *            数据Map
  * @return 结果字符串
  */
 public String getString(String ftlFile, Map data) {
  StringWriter writer = null;
  String result = "";
  try {
   Template template = config.getTemplate(ftlFile);
   template.setEncoding(encoding);
   writer = new StringWriter();
   template.process(data, writer);
   result = writer.toString();
  } catch (Exception e) {
   e.printStackTrace();
  } finally {
   // 关闭write
   try {
    if (writer != null) {
     writer.flush();
     writer.close();
    }
   } catch (Exception ex) {
    ex.printStackTrace();
   }
  }
  return result;
 }

 /**
  * 输出到内存
  * 
  * @param ftlFile
  *            模板文件(相对模板文件目录的相对路径)
  * @param data
  *            数据Map
  * @return ByteArrayOutputStream
  * @throws IOException
  */
 public ByteArrayOutputStream createByteArray(String ftlFile, Map data)
   throws IOException {
  ByteArrayOutputStream os = null;
  BufferedWriter writer = null;
  try {
   Template template = config.getTemplate(ftlFile);
   template.setEncoding(encoding);
   os = new ByteArrayOutputStream();
   writer = new BufferedWriter(new OutputStreamWriter(os));
   template.process(data, writer);
  } catch (Exception e) {
   e.printStackTrace();
  } finally {
   // 关闭os write
   if (writer != null) {
    writer.flush();
    writer.close();
   }
   if (os != null) {
    os.flush();
    os.close();
   }
  }
  return os;
 }
}