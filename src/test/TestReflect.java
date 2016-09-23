package test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by sunlines on 2016/9/23.
 */
public class TestReflect {
    public static void main(String[] args) {
        try {
            Class clazz = Class.forName("test.A");
            Constructor[] constructor = clazz.getConstructors();
            A a = (A) constructor[0].newInstance();
            Method method = clazz.getMethod("method");
            method.invoke(a);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
class A{
    public A() {
    }
    public void method(){
        System.out.println("hehe");
    }
}