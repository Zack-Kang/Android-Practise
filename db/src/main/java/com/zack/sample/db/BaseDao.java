package com.zack.sample.db;

import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.zack.sample.db.annotion.DbTable;

import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * Created by Zack on 2017/9/7.
 */

public abstract class BaseDao<T> implements IBaseDao<T> {
    /**
     * 持有操作数据库类的引用
     */
    private SQLiteDatabase sqLiteDatabase;
    /**
     * 保证实例化一次
     */
    private boolean isInit = false;
    /**
     * 持有操作数据库表所对应的java类型
     * User.class
     */
    private Class<T> entityClazz;
    private String tableName;
    /**
     * 表明与成员变量名的映射关系
     * key:表名
     * value:成员变量名
     */
    private HashMap<String,Field> fieldHashMap;
    protected synchronized boolean init(Class<T> entity, SQLiteDatabase sqLiteDatabase){
        if (!isInit){
            this.sqLiteDatabase = sqLiteDatabase;
            if (entity.getAnnotation(DbTable.class)!=null){
                tableName = entity.getAnnotation(DbTable.class).value();
            }else{
                tableName = entity.getClass().getSimpleName();
            }
            if (!sqLiteDatabase.isOpen()){
                return false;
            }else{

            }
            initFieldMap();
            isInit = true;
        }
    }

    /**
     * 维护映射关系
     */
    private void initFieldMap() {
        String sql= "select * from "+ tableName + " limit 1,0";
    }

    @Override
    public Long insert(T entity) {
        return null;
    }

    @Override
    public int update(T entity, T where) {
        return 0;
    }

    /**
     * 创建表
     */
}
