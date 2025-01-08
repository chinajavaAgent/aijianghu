package com.aigroup.world.mapper;

import com.aigroup.world.entity.AdminTips;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 管理员与锦囊关联Mapper接口
 */
@Mapper
public interface AdminTipsMapper extends BaseMapper<AdminTips> {
    /**
     * 批量插入管理员锦囊关联记录
     *
     * @param adminTipsList 管理员锦囊关联记录列表
     * @return 插入成功的记录数
     */
    int insertBatch(@Param("list") List<AdminTips> adminTipsList);
} 