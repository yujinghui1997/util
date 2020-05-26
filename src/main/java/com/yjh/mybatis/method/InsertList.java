package com.yjh.mybatis.method;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlScriptUtils;
import com.yjh.mybatis.MySqlMethod;
import com.yjh.util.MyStrUtil;
import org.apache.ibatis.executor.keygen.Jdbc3KeyGenerator;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class InsertList extends AbstractMethod {
    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        MySqlMethod method = MySqlMethod.INSERT_LIST;
        String key = "({})";
        String value = "({})";
        List<TableFieldInfo> fieldList = tableInfo.getFieldList();
        List<String> strings = fieldList.stream().map(s -> s.getProperty()).collect(Collectors.toList());
        if (StringUtils.isNotEmpty(tableInfo.getKeyProperty())) {
            if (tableInfo.getIdType() != IdType.AUTO) {
                if (tableInfo.getKeyProperty().equals("id") || tableInfo.getKeyColumn().equals("id")) {
                    // 获取类对应的所有属性
                    Field[] fields = modelClass.getDeclaredFields();
                    List<String> allField = new ArrayList<>();
                    for (int i = 0; i < fields.length; i++) {
                        allField.add(fields[i].getName());
                    }
                    allField.removeAll(strings);
                    String field = allField.get(0);
                    try {
                        Field f = modelClass.getDeclaredField(field);
                        if (f.isAnnotationPresent(TableField.class)) {
                            TableField tableField = f.getAnnotation(TableField.class);
                            if (StrUtil.hasBlank(tableField.value())) {
                                key = "(" + field + ",{})";
                            } else {
                                key = "(" + tableField.value() + ",{})";
                            }
                            value = "(#{item." + field + "},{})";
                        }
                    } catch (Exception e) {
                    }
                }
            }
        }
        String pro = fieldList.stream().map(s -> s.getColumn()).collect(Collectors.joining(","));
        String colum = fieldList.stream().map(s -> {
            return "#{item." + s.getProperty() + "}";
        }).collect(Collectors.joining(","));
        key = MyStrUtil.format(key, pro);
        value = MyStrUtil.format(value, colum);
        String foreach = SqlScriptUtils.convertForeach(value, "list", (String) null, "item", ",");
        String sql = String.format(method.getSql(), tableInfo.getTableName(), key, foreach);
        SqlSource sqlSource = this.languageDriver.createSqlSource(this.configuration, sql, modelClass);
        return this.addInsertMappedStatement(mapperClass, modelClass, method.getMethod(), sqlSource, new NoKeyGenerator(), "id", "id");
    }


}
