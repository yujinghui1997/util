package com.yjh.mybatis.method;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.yjh.mybatis.MySqlMethod;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

import java.lang.reflect.Field;

public class GetLastId extends AbstractMethod {

    public GetLastId(){}

    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        // 获取类对应的所有属性
        Class<?> clzz = null;
        Field[] fields = modelClass.getDeclaredFields();
        for (Field field : fields) {
           if (field.isAnnotationPresent(TableId.class)){
               clzz = field.getType();
           }
        }
        MySqlMethod sqlMethod = MySqlMethod.GETLASTID;
        String sql = String.format(sqlMethod.getSql());
        SqlSource sqlSource = this.languageDriver.createSqlSource(this.configuration, sql, modelClass);
        return this.addSelectMappedStatement(mapperClass, sqlMethod.getMethod(), sqlSource,clzz,tableInfo);
    }
}
