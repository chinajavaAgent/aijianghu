package com.aigroup.world.mapper.secondary;

import com.aigroup.world.entity.TUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.dynamic.datasource.annotation.DS;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@DS("secondary")
public interface TUserMapper extends BaseMapper<TUser> {

}
