package com.yjh.mybatis;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.yjh.mybatis.method.DeleteAll;
import com.yjh.mybatis.method.GetLastId;
import com.yjh.mybatis.method.InsertList;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MyInjector extends DefaultSqlInjector {
    @Override
    public List<AbstractMethod> getMethodList() {
        List<AbstractMethod> list = super.getMethodList();
        list.add(new DeleteAll());
        list.add(new InsertList());
        list.add(new GetLastId());
        return list;
    }
}
