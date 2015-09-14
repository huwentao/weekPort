package com.example.mokey.weekport.data;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mokey on 15-9-6.
 */
public class XmlUtil {
    public <T> T getObjectFromXML(Class<T> tClass, InputStream inputStream)
            throws XmlPullParserException,
            IllegalAccessException,
            InstantiationException, IOException {
        T t = null;
        List objects = null;
        Field[] fields = tClass.getDeclaredFields();
        XmlPullParser pullParser = Xml.newPullParser();//由android.util.Xml创建一个XmlPullParser实例
        pullParser.setInput(inputStream, "UTF-8");//设置输入流 并指明编码方式
        int eventType = pullParser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    t = tClass.newInstance();
                    break;
                case XmlPullParser.START_TAG:
                    String name = pullParser.getName();
                    if (t != null) {
                        for (Field field : fields) {
                            field.setAccessible(true);
                            XmlFieldName xmlFieldName = field.getAnnotation(XmlFieldName.class);
                            if (name.equals(xmlFieldName.fieldName())) {
                                if (xmlFieldName.type() == FieldType.ListType) {
                                    ParameterizedType type = (ParameterizedType) field.getGenericType();
                                    Class listTypeClass = (Class) type.getActualTypeArguments()[0];
                                    objects = new ArrayList();
                                    listTypeClass.newInstance();
                                    objects.add(objects);
                                } else if (xmlFieldName.type() == FieldType.WeeklyRootType) {

                                } else if (xmlFieldName.type() == FieldType.StringType) {
                                    field.set(t, pullParser.nextText());
                                }
                            }
                        }
                    }
                    break;
                case XmlPullParser.END_TAG:

                    break;
            }
            eventType = pullParser.next();
        }
        return t;
    }
}
