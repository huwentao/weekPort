package com.example.mokey.weekport;

import android.os.Environment;
import android.test.ApplicationTestCase;

import com.example.mokey.weekport.data.XmlUtil;
import com.example.mokey.weekport.data.project.ProjectRoot;
import com.example.mokey.weekport.data.weekly.Task;
import com.example.mokey.weekport.data.weekly.WeeklyRoot;
import com.example.mokey.weekport.ui.core.WeekPortApplication;

import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import hirondelle.date4j.DateTime;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<WeekPortApplication> {

    private File testFile = null;
    private File projFile;

    /**
     * 测试准备，准备XML文件
     *
     * @throws Exception
     */
    @Override protected void setUp() throws Exception {
        super.setUp();
        createApplication();
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File file = Environment.getExternalStorageDirectory();
            file = new File(file.getAbsolutePath() + "/weekport/report/");
            if (!file.exists()) {
                file.mkdirs();
            }
            String deirectory = file.getAbsolutePath();
            DateTime dateTime = DateTime.now(TimeZone.getDefault());
            file = new File(deirectory + "/" + dateTime.format("YYYYMMDD.demo.xml"));
            if (!file.exists()) {
                boolean flag = file.createNewFile();
                if (flag) {
                    BufferedSource from = Okio.buffer(Okio.source(getApplication().getAssets().open("20150824.xml")));
                    BufferedSink to = Okio.buffer(Okio.sink(file));
                    to.writeAll(from);
                    to.flush();
                    from.close();
                    to.close();
                }
                testFile = file;
            }
            file = new File(deirectory + "/" + dateTime.format("YYYYMMDD.projectdemo.xml"));
            if (!file.exists()) {
                boolean flag = file.createNewFile();
                if (flag) {
                    BufferedSource from = Okio.buffer(Okio.source(getApplication().getAssets().open("open20150812.xml")));
                    BufferedSink to = Okio.buffer(Okio.sink(file));
                    to.writeAll(from);
                    to.flush();
                    from.close();
                    to.close();
                }
                projFile = file;
            }
        }
    }

    public ApplicationTest() {
        super(WeekPortApplication.class);
    }

    /**
     * 测试获取list内容类别CLASS
     */
    public void testApp() {
        Class<TestClass> testClassClass = TestClass.class;
        try {
            Field field = testClassClass.getDeclaredField("tasks");
            ParameterizedType type = (ParameterizedType) field.getGenericType();
            Class aClass = (Class) type.getActualTypeArguments()[0];
            System.out.println(aClass);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试解析类中添加注解的field
     */
    public void testXmlUtil_handleTags() {
        XmlUtil xmlUtil = XmlUtil.getInstance();
        try {
            Map<String, Field> fieldList = xmlUtil.handleTags(WeeklyRoot.class);
            for (Map.Entry<String, Field> entry : fieldList.entrySet()) {
                System.out.println("field.getName====>" + entry.getKey() +
                        "\tfield.getValue====>" + entry.getValue().getName());
            }
            System.out.println("=========================================================");
            Map<String, Field> fieldList2 = xmlUtil.handleTags(ProjectRoot.class);
            for (Map.Entry<String, Field> entry : fieldList2.entrySet()) {
                System.out.println("field.getName====>" + entry.getKey() +
                        "\tfield.getValue====>" + entry.getValue().getName());
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试读取XML文件内容后并写入新的XML文件
     *
     * @throws IOException
     * @throws IllegalAccessException
     * @throws XmlPullParserException
     * @throws InstantiationException
     */
    public void testXmlUtil_parse2Object() throws IOException, IllegalAccessException, XmlPullParserException, InstantiationException {
//        InputStream inputStream = getContext().getAssets().open();
        XmlUtil xmlUtil = XmlUtil.getInstance();
        BufferedSource bufferedSource = Okio.buffer(Okio.source(testFile));
        System.out.println("========================================================");
        System.out.println("base64 == " + bufferedSource.readByteString().base64());
        System.out.println("hex == " + bufferedSource.readByteString().hex());
        System.out.println("size == " + bufferedSource.readByteString().size());
        System.out.println("readByteString == " + bufferedSource.readByteString().toString());
        System.out.println("utf8 == " + bufferedSource.readByteString().utf8());

        WeeklyRoot weeklyRoot = xmlUtil.parseXMLToObject(WeeklyRoot.class, testFile);

        System.out.println(weeklyRoot.getReportPerson() + "<=>" + weeklyRoot.getIdNo());
        System.out.println(weeklyRoot.getDocType() + "<=>" + weeklyRoot.getDocVersion());
        System.out.println(weeklyRoot.getReportWeek() + "<=>" + weeklyRoot.getReportYear());
        System.out.println(weeklyRoot.getWeekBrief());
        System.out.println(weeklyRoot.getWeekBrief().getNextPlan());
        System.out.println(weeklyRoot.getWeekBrief().getWeekSummary());

        for (Task task : weeklyRoot.getTaskList()) {
            System.out.println("========================================================");
            System.out.println("getApplyNo=" + task.getApplyNo());
            System.out.println("getMealFee=" + task.getMealFee());
            System.out.println("getMemo1=" + task.getMemo1());
            System.out.println("getNormalHour=" + task.getNormalHour());
            System.out.println("getOtherFee=" + task.getOtherFee());
            System.out.println("getOutFlag=" + task.getOutFlag());
            System.out.println("getOvertimeHour=" + task.getOvertimeHour());
            System.out.println("getProjId=" + task.getProjId());
            System.out.println("getSzitId=" + task.getSzitId());
            System.out.println("getTaskDate=" + task.getTaskDate());
            System.out.println("getTaskMemo=" + task.getTaskMemo());
            System.out.println("getTaskSeq=" + task.getTaskSeq());
            System.out.println("getTaskType=" + task.getTaskType());
            System.out.println("getTrafficFee=" + task.getTrafficFee());
        }


        File file = Environment.getExternalStorageDirectory();
        file = new File(file.getAbsolutePath() + "/weekport/report/");
        if (!file.exists()) {
            file.mkdirs();
        }
        String deirectory = file.getAbsolutePath();
        DateTime dateTime = DateTime.now(TimeZone.getDefault());
        file = new File(deirectory + "/" + dateTime.format("test.demo.xml"));
        if (!file.exists()) {
            boolean flag = file.createNewFile();
            if (flag) {
                BufferedSource from = Okio.buffer(Okio.source(getApplication().getAssets().open("20150824.xml")));
                BufferedSink to = Okio.buffer(Okio.sink(file));
                to.writeAll(from);
                to.flush();
                from.close();
                to.close();
            }
            xmlUtil.createXMLFile(weeklyRoot, file);
        }
    }

    /**
     * 测试解析XML内容
     *
     * @throws IOException
     * @throws XmlPullParserException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public void testXmlUtil_parse3Object() throws IOException, XmlPullParserException, InstantiationException, IllegalAccessException {
        XmlUtil xmlUtil = XmlUtil.getInstance();
        long star = DateTime.now(TimeZone.getDefault()).getMilliseconds(TimeZone.getDefault());

        ProjectRoot projectRoot = xmlUtil.parseXMLToObject(ProjectRoot.class, projFile);

        long end = DateTime.now(TimeZone.getDefault()).getMilliseconds(TimeZone.getDefault());
        System.out.println("end-star<>" + (end - star));
        System.out.println("getDocType=" + projectRoot.getDocType());
        System.out.println("getDocVersion=" + projectRoot.getDocVersion());
        System.out.println("getProjList().size=" + projectRoot.getProjList().size());
        System.out.println("getProjName=" + projectRoot.getProjList().get(0).getProjName());
    }

    public class TestClass {
        private List<Task> tasks = new ArrayList<>();
    }
}