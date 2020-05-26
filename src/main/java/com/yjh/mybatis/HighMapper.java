package com.yjh.mybatis;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HighMapper<T> extends BaseMapper<T> {
    int deleteAll();
    int insertList(@Param("list")List<T> list);
}
