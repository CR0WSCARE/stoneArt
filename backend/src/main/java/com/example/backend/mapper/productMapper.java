package com.example.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.backend.pojo.product;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface productMapper extends BaseMapper<product> {
}
