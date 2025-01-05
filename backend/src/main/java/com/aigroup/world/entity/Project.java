package com.aigroup.world.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@TableName("project")
public class Project {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private Long tipId;
    
    private String title;
    
    private String description;
    
    private String videoUrl;
    
    @TableField(exist = false)
    private List<ProjectCase> cases;
    
    @TableField(exist = false)
    private List<ProjectBenefit> benefits;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    
    @TableLogic
    private Integer deleted;
} 