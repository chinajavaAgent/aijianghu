package com.aigroup.world.service;

import com.aigroup.world.entity.Project;
import com.aigroup.world.entity.ProjectCase;
import com.aigroup.world.entity.ProjectBenefit;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProjectService {
    /**
     * 分页获取项目列表
     */
    Page<Project> getProjects(Integer page, Integer size, Long tipId);

    /**
     * 获取项目详情
     */
    Project getProjectById(Long id);

    /**
     * 创建项目
     */
    Project createProject(Project project);

    /**
     * 更新项目
     */
    boolean updateProject(Project project);

    /**
     * 删除项目
     */
    boolean deleteProject(Long id);

    /**
     * 添加案例
     */
    ProjectCase addCase(ProjectCase projectCase);

    /**
     * 更新案例
     */
    boolean updateCase(ProjectCase projectCase);

    /**
     * 删除案例
     */
    boolean deleteCase(Long projectId, Long caseId);

    /**
     * 上传案例图片
     */
    String uploadCaseImage(Long projectId, Long caseId, MultipartFile file);

    /**
     * 上传项目视频
     */
    String uploadProjectVideo(Long projectId, MultipartFile file);

    /**
     * 添加福利
     */
    ProjectBenefit addBenefit(ProjectBenefit benefit);

    /**
     * 更新福利
     */
    boolean updateBenefit(ProjectBenefit benefit);

    /**
     * 删除福利
     */
    boolean deleteBenefit(Long projectId, Long benefitId);
} 