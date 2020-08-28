package com.example.jlib;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args){
        System.out.println("***********************");
        generic();
    }

    private static void generic() {
        List<String> list = new ArrayList<>();
        list.add("java");
        list.add("c++");
        list.add("c");
        list.add("go");
        list.add("python");
        list.add("kotlin");
        test(list);
    }

    static void test(List<?> list){
        Generic<String> generic = new Generic();
        try {
            generic.printGenericType();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    static class Generic<T>{
        T t;
        private Map<String, Number> collection;
        void printGenericType() throws NoSuchFieldException {
            Class<?> clazz = Generic.class;
            Field field = clazz.getDeclaredField("collection");
            Type type = field.getGenericType();
            ParameterizedType ptype = (ParameterizedType)type;
            System.out.println(ptype.getActualTypeArguments()[0]);
            System.out.println(ptype.getActualTypeArguments()[1]);


            Field fieldt = clazz.getDeclaredField("t");
            Type typet = fieldt.getGenericType();
            ParameterizedType ptypet = (ParameterizedType)typet;
            System.out.println(ptypet.getActualTypeArguments()[0]);
            System.out.println(ptypet.getActualTypeArguments()[1]);
        }
    }
}