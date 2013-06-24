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
 * FreeMarker������
 * 
 * @author sunjun
 * 
 */
public class FreeMarkerUtil {

 /**
  * ����FreeMarker Configuration����config
  */
 private Configuration config = null;

 /**
  * ����
  */
 private String encoding = "UTF-8";

 /**
  * FreeMarkerUtil����FREEMARKER
  */
 private static final FreeMarkerUtil FREEMARKER = new FreeMarkerUtil();

 /**
  * ���췽��
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
  * �õ�FreeMarkerUtil����
  * 
  * @return FreeMarkerUtil����
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
  * ���ñ���
  * 
  * @param encoding
  *            the encoding to set
  */
 public void setEncoding(String encoding) {
  this.encoding = encoding;
 }

 /**
  * ���ɾ�̬ҳ��
  * 
  * @param ftlFile
  *            ģ���ļ�(��Ը�Ŀ¼�ľ���·������/��ͷ)
  * @param data
  *            ����Map
  * @param file
  *            ���ɵ��ļ�
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
   // �ر�out output
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
  * ������ַ���
  * 
  * @param ftlFile
  *            ģ���ļ�(��Ը�Ŀ¼�ľ���·������/��ͷ)
  * @param data
  *            ����Map
  * @return ����ַ���
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
   // �ر�write
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
  * ������ڴ�
  * 
  * @param ftlFile
  *            ģ���ļ�(���ģ���ļ�Ŀ¼�����·��)
  * @param data
  *            ����Map
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
   // �ر�os write
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