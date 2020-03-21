package com.yjh.util;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class MyBeanUtil  {

    public static void copy(Object soure,Object target){
        BeanUtils.copyProperties(soure,target);
    }

    public static  <S,T> List<T> copyList(List<S> source, Class<T> clazz){
       List<T> list = new ArrayList<>(source.size());
        for (S s : source) {
            try {
                T t = clazz.newInstance();
                copy(s,t);
                list.add(t);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return list;
    }


}
