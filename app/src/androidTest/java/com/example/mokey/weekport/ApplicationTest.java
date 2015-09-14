package com.example.mokey.weekport;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.example.mokey.weekport.data.weekly.Task;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {


    @Override protected void setUp() throws Exception {
        super.setUp();
    }

    public ApplicationTest() {
        super(Application.class);
    }

    public void testApp(){
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

    public class TestClass {
        private List<Task> tasks = new ArrayList<>();

    }
}