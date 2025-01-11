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
    
    @TableField("tip_id")
    private Long tipId;
    
    private String name;
    
    private String description;
    
    private String detail;
    
    @TableField("cover_image")
    private String coverImage;
    
    @TableField("video_url")
    private String videoUrl;
    
    private Integer status;
    
    @TableField("start_time")
    private LocalDateTime startTime;
    
    @TableField("end_time")
    private LocalDateTime endTime;
    
    @TableField("author_id")
    private Long authorId;
    
    private Integer views;
    
    private Integer likes;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    
    @TableLogic
    private Integer deleted;
    
    @TableField(exist = false)
    private List<ProjectCase> cases;
    
    @TableField(exist = false)
    private List<ProjectBenefit> benefits;
    
    @TableField(exist = false)
    private List<Project> relatedProjects;
} 