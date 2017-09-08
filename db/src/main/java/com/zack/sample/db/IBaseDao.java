package com.zack.sample.db;

/**
 * Created by Zack on 2017/9/7.
 */

public interface IBaseDao<T> {
    Long insert(T entity);
    int update(T entity, T where);
}
