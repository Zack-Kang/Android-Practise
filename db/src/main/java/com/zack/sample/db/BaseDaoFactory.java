package com.zack.sample.db;

import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

/**
 * Created by Zack on 2017/9/7.
 */

public class BaseDaoFactory {
    private String sqliteDatabasePath;
    public SQLiteDatabase sqLiteDatabase;
    private static BaseDaoFactory instance = new BaseDaoFactory();
    private BaseDaoFactory() {
        sqliteDatabasePath = Environment.getDataDirectory().getAbsolutePath()+"user.db";
        openDatabase();
    }

    private void openDatabase() {
        this.sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(sqliteDatabasePath, null);
    }

    public synchronized <T extends BaseDao<M>,M> T getDatabaseHelper(Class<T> clazz,Class<M> entityClazz){
        BaseDao baseDao = null;
        try {
            baseDao = clazz.newInstance();
            baseDao.init(entityClazz,sqLiteDatabase);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return (T) baseDao;
    }

    public static BaseDaoFactory getInstance() {
        return instance;
    }
}
