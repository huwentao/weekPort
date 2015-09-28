package com.example.mokey.weekport.data.annotation;

import android.content.Context;
import android.os.Environment;
import android.util.Xml;

import com.example.mokey.weekport.data.project.Proj;
import com.example.mokey.weekport.data.project.ProjectRoot;
import com.example.mokey.weekport.db.DbUtils;
import com.example.mokey.weekport.db.exception.DbException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import hirondelle.date4j.DateTime;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;

/**
 * XML解析工具
 * Created by mokey on 15-9-6.
 */
public class XmlUtil {
    private static XmlUtil xmlUtil = null;
    public final static String xmlProjectDirectory = "/weekport/project/";
    public final static String xmlProjectFileName = "project.xml";
    public final static String xmlWeekPortDirectory = "/weekport/export/";


    private XmlUtil() {

    }

    public static XmlUtil getInstance() {
        if (xmlUtil == null) {
            xmlUtil = new XmlUtil();
        }
        return xmlUtil;
    }

    /**
     * @return
     */
    public static File getXmlProjectDirectory() {
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + xmlProjectDirectory);
        if (file.exists() || file.mkdirs()) {
        }
        return file;
    }

    public static File getXmlWeekPortDirectory() {
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + xmlWeekPortDirectory);
        if (file.exists() || file.mkdirs()) {
        }
        return file;
    }

    /**
     * @return
     */
    public List<File> getProjectListFiles() {
        String storageState = Environment.getExternalStorageState();
        if (storageState.equals(Environment.MEDIA_MOUNTED)) {
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + xmlProjectDirectory);
            if (file.exists())
                return Arrays.asList(file.listFiles());
        }
        return null;
    }

    /**
     * @param context
     * @param dbUtils
     * @param requirementFileName
     * @throws IOException
     * @throws IllegalAccessException
     * @throws XmlPullParserException
     * @throws InstantiationException
     * @throws DbException
     */
    public void initXmlFile(Context context, DbUtils dbUtils, String requirementFileName)
            throws IOException,
            IllegalAccessException,
            XmlPullParserException,
            InstantiationException,
            DbException {
        String storageState = Environment.getExternalStorageState();
        if (storageState.equals(Environment.MEDIA_MOUNTED)) {
            File file = getXmlProjectDirectory();
            File requirement = new File(file.getAbsolutePath() + "/" + requirementFileName);
            if (requirement.exists() || requirement.createNewFile()) {
                InputStream inputStream = context.getAssets().open(xmlProjectFileName);
                BufferedSource bufferedSource = Okio.buffer(Okio.source(inputStream));
                BufferedSink bufferedSink = Okio.buffer(Okio.sink(requirement));
                bufferedSink.writeAll(bufferedSource);
                bufferedSink.flush();
                bufferedSink.close();
                bufferedSource.close();
            }

            ProjectRoot projectRoot = parseXMLToObject(ProjectRoot.class, requirement);
            DateTime dateTime = DateTime.now(TimeZone.getDefault());
            projectRoot.setCreateTime(dateTime.format("YYYY-MM-DD hh:mm:ss"));
            for (Proj proj : projectRoot.getProjList()) {
                proj.setProjectId(projectRoot.getProjectId());
            }
            dbUtils.saveOrUpdate(projectRoot);
        }
    }

    /**
     * 解析XML文件到指定类
     *
     * @param tClass  目标类
     * @param xmlFile XML文件
     * @param <T>
     * @return
     * @throws XmlPullParserException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws IOException
     */
    public <T> T parseXMLToObject(Class<T> tClass, File xmlFile)
            throws XmlPullParserException,
            IllegalAccessException,
            InstantiationException, IOException {
        T t = parseObjectPart(tClass, xmlFile);
        Map<String, Field> fieldList = clearField(tClass);
        for (Map.Entry<String, Field> fieldEntry : fieldList.entrySet()) {
            Field field = fieldEntry.getValue();
            XmlFieldName xmlFieldName = field.getAnnotation(XmlFieldName.class);
            if (xmlFieldName.type() == FieldType.ListType) {
                field.setAccessible(true);
                List list = parseListPart(field, xmlFile);
                field.set(t, list);
            } else if (xmlFieldName.type() == FieldType.WeekBriefType) {
                field.setAccessible(true);
                Object obj = parseObjectPart(FieldType.WeekBriefType.getTypeClass(), xmlFile);
                field.set(t, obj);
            }
        }
        return t;
    }

    /**
     * 解析XML文件中的LIST部分
     *
     * @param listField 转化类中的LIST FIELD
     * @param xmlfile   XML文件
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws XmlPullParserException
     * @throws IOException
     */
    private List parseListPart(Field listField, File xmlfile) throws InstantiationException,
            IllegalAccessException,
            XmlPullParserException,
            IOException {
        InputStream xmlInputStream = Okio.buffer(Okio.source(xmlfile)).inputStream();

        ParameterizedType type = (ParameterizedType) listField.getGenericType();
        Class aClass = (Class) type.getActualTypeArguments()[0];
        List<Object> tList = new ArrayList<>();
        Object listObj = null;
        Map<String, Field> fieldList = clearField(aClass);
        XmlRootName xmlRootName = (XmlRootName) aClass.getAnnotation(XmlRootName.class);

        XmlPullParser pullParser = Xml.newPullParser();//由android.util.Xml创建一个XmlPullParser实例
        pullParser.setInput(xmlInputStream, "UTF-8");//设置输入流 并指明编码方式
        int eventType = pullParser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            String name = null;
            Field field = null;
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    name = pullParser.getName();
                    if (xmlRootName != null && name.equals(xmlRootName.rootName())) {
                        listObj = aClass.newInstance();
                    } else {
                        field = fieldList.get(name);
                        if (field != null) {
                            XmlFieldName xmlFieldName = field.getAnnotation(XmlFieldName.class);
                            if (xmlFieldName != null && xmlFieldName.type() == FieldType.StringType) {
                                field.setAccessible(true);
                                pullParser.next();
                                field.set(listObj, pullParser.getText());
                            }
                        }
                    }
                    break;
                case XmlPullParser.END_TAG:
                    name = pullParser.getName();
                    if (xmlRootName != null && name.equals(xmlRootName.rootName())) {
                        tList.add(listObj);
                    }
                    break;
            }
            eventType = pullParser.next();
        }
        return tList;
    }

    /**
     * 解析XML文件中的POJO部分
     *
     * @param tClass  转化类中的POJO FIELD
     * @param xmlfile mlfile XML文件
     * @param <T>
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws XmlPullParserException
     * @throws IOException
     */
    public <T> T parseObjectPart(Class<T> tClass, File xmlfile) throws InstantiationException,
            IllegalAccessException,
            XmlPullParserException,
            IOException {
        InputStream xmlInputStream = Okio.buffer(Okio.source(xmlfile)).inputStream();

        T t = null;
        Map<String, Field> fieldList = clearField(tClass);
        XmlPullParser pullParser = Xml.newPullParser();//由android.util.Xml创建一个XmlPullParser实例
        pullParser.setInput(xmlInputStream, "UTF-8");//设置输入流 并指明编码方式
        int eventType = pullParser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    t = tClass.newInstance();
                    break;
                case XmlPullParser.START_TAG:
                    String name = pullParser.getName();
                    Field field = fieldList.get(name);
                    if (field != null) {
                        XmlFieldName xmlFieldName = field.getAnnotation(XmlFieldName.class);
                        if (xmlFieldName != null && xmlFieldName.type() == FieldType.StringType) {
                            field.setAccessible(true);
                            field.set(t, pullParser.nextText());
                        }
                    }
                    break;
            }
            eventType = pullParser.next();
        }
        return t;
    }

    /**
     * 处理类的FIELD，去掉未添加注解的部分
     *
     * @param tClass
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public Map<String, Field> handleTags(Class<?> tClass) throws IllegalAccessException, InstantiationException {
        List<Field> fieldList = Arrays.asList(tClass.getDeclaredFields());
        Map<String, Field> tempFieldList = clearField(tClass);
        for (int i = 0; i < fieldList.size(); i++) {
            XmlFieldName xmlFieldName = fieldList.get(i).getAnnotation(XmlFieldName.class);
            if (xmlFieldName != null) {
                if (xmlFieldName.type() == FieldType.ListType) {
                    ParameterizedType type = (ParameterizedType) fieldList.get(i).getGenericType();
                    Class listTypeClass = (Class) type.getActualTypeArguments()[0];
                    tempFieldList.putAll(clearField(listTypeClass));
                } else if (xmlFieldName.type() == FieldType.WeekBriefType) {
                    Class typeClass = xmlFieldName.type().getTypeClass();
                    tempFieldList.putAll(clearField(typeClass));
                }
            }
        }
        return tempFieldList;
    }

    /**
     * 取带有注解的类字段
     *
     * @param aClass 类
     * @return
     */
    public Map<String, Field> clearField(Class aClass) {
        Map<String, Field> tempListField = new HashMap<>();
        List<Field> fieldList = Arrays.asList(aClass.getDeclaredFields());
        for (int i = 0; i < fieldList.size(); i++) {
            XmlFieldName xmlFieldName = fieldList.get(i).getAnnotation(XmlFieldName.class);
            if (xmlFieldName != null) {
                tempListField.put(xmlFieldName.fieldName(), fieldList.get(i));
            }
        }
        return tempListField;
    }

    /**
     * 把POJO中的属性写入到XML
     *
     * @param obj     目标POJO
     * @param xmlFile 目标XML文件
     * @throws IOException
     * @throws IllegalAccessException
     */
    public void createXMLFile(Object obj, File xmlFile) throws IOException, IllegalAccessException {
        BufferedSink bufferedSink = Okio.buffer(Okio.sink(xmlFile));
        XmlSerializer xmlSerializer = Xml.newSerializer();
        xmlSerializer.setOutput(bufferedSink.outputStream(), "UTF-8");
        xmlSerializer.startDocument("utf-8", true);
        createXmlFileObjectPart(xmlSerializer, obj);
        xmlSerializer.endDocument();
    }

    /**
     * 把POJO中的属性写入到XML
     *
     * @param xmlSerializer XML写入工具
     * @param obj           obj 目标POJO
     * @throws IOException
     * @throws IllegalAccessException
     */
    private void createXmlFileObjectPart(XmlSerializer xmlSerializer, Object obj) throws IOException, IllegalAccessException {
        XmlRootName xmlRootName = obj.getClass().getAnnotation(XmlRootName.class);
        xmlSerializer.startTag(null, xmlRootName.rootName());
        Map<String, Field> fieldMap = clearField(obj.getClass());
        for (Map.Entry<String, Field> fieldEntry : fieldMap.entrySet()) {
            Field field = fieldEntry.getValue();
            XmlFieldName xmlFieldName = field.getAnnotation(XmlFieldName.class);
            if (xmlFieldName.type() == FieldType.ListType) {
                field.setAccessible(true);
                Object o = field.get(obj);
                if (o instanceof List) {
                    List objList = (List) o;
                    xmlSerializer.startTag(null, xmlFieldName.fieldName());
                    if (objList.size() > 0) {
                        for (int i = 0; i < objList.size(); i++) {
                            createXmlFileObjectPart(xmlSerializer, objList.get(i));
                        }
                    }
                    xmlSerializer.endTag(null, xmlFieldName.fieldName());
                }
                field.setAccessible(false);
            } else if (xmlFieldName.type() == FieldType.StringType) {
                field.setAccessible(true);
                Object o = field.get(obj);
                xmlSerializer.startTag(null, xmlFieldName.fieldName());
                xmlSerializer.text(String.valueOf(o));
                xmlSerializer.endTag(null, xmlFieldName.fieldName());
                field.setAccessible(false);
            } else {
                field.setAccessible(true);
                Object o = field.get(obj);
                createXmlFileObjectPart(xmlSerializer, o);
                field.setAccessible(false);
            }
        }
        xmlSerializer.endTag(null, xmlRootName.rootName());
    }

    /**
     *
     */
    public class TagObject {
        int index;
        Object tagObject;
        Class<?> tabClass;

        public TagObject(int index, Object tagObject, Class<?> tabClass) {
            this.index = index;
            this.tagObject = tagObject;
            this.tabClass = tabClass;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public Object getTagObject() {
            return tagObject;
        }

        public void setTagObject(Object tagObject) {
            this.tagObject = tagObject;
        }

        public Class<?> getTabClass() {
            return tabClass;
        }

        public void setTabClass(Class<?> tabClass) {
            this.tabClass = tabClass;
        }
    }

    public class TypeReference {

        public <T> List<T> gettList(Class<T> tClass) {
            List<T> tList = new ArrayList<>();
            return tList;
        }
    }


}
