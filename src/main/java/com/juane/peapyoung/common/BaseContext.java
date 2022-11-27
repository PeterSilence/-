package com.juane.peapyoung.common;

/**
 * 基于ThreadLocal封装工具类，用户保存和获取当前登录用户id.工具类，直接使用而不用实例化
 * 此工具类的目的在于用户登录时记录用户id
 */
public class BaseContext {
    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();//用于存储登录用户的id，id为long型

    /**
     * 设置值
     * @param id
     */
    public static void setCurrentId(String id){
        System.out.println("---------------------------------------");
        System.out.println(threadLocal);
        System.out.println("---------------------------------------");
        threadLocal.set(id);
        System.out.println(id);
    }

    /**
     * 获取值
     * @return
     */
    public static String getCurrentId(){
        return threadLocal.get();
    }
}