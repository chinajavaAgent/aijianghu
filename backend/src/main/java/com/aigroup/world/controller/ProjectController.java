package com.aigroup.world.controller;

import com.aigroup.world.common.Result;
import com.aigroup.world.entity.Project;
import com.aigroup.world.entity.ProjectCase;
import com.aigroup.world.entity.ProjectBenefit;
import com.aigroup.world.service.ProjectService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    /**
     * 获取项目列表
     */
    @GetMapping
    public Result<Page<Project>> getProjects(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) Long tipId) {
        Page<Project> projects = projectService.getProjects(page, size, tipId);
        return Result.success(projects);
    }

    /**
     * 获取项目详情
     */
    @GetMapping("/{id}")
    public Result<Project> getProject(@PathVariable Long id) {
        Project project = projectService.getProjectById(id);
        return Result.success(project);
    }

    /**
     * 创建项目
     */
    @PostMapping
    public Result<Project> createProject(@RequestBody Project project) {
        Project savedProject = projectService.createProject(project);
        return Result.success(savedProject);
    }

    /**
     * 更新项目
     */
    @PutMapping("/{id}")
    public Result<Boolean> updateProject(@PathVariable Long id, @RequestBody Project project) {
        project.setId(id);
        return Result.success(projectService.updateProject(project));
    }

    /**
     * 删除项目
     */
    @DeleteMapping("/{id}")
    public Result<Boolean> deleteProject(@PathVariable Long id) {
        return Result.success(projectService.deleteProject(id));
    }

    /**
     * 添加案例
     */
    @PostMapping("/{projectId}/cases")
    public Result<ProjectCase> addCase(@PathVariable Long projectId, @RequestBody ProjectCase projectCase) {
        projectCase.setProjectId(projectId);
        ProjectCase savedCase = projectService.addCase(projectCase);
        return Result.success(savedCase);
    }

    /**
     * 更新案例
     */
    @PutMapping("/{projectId}/cases/{caseId}")
    public Result<Boolean> updateCase(@PathVariable Long projectId, @PathVariable Long caseId, 
                                    @RequestBody ProjectCase projectCase) {
        projectCase.setId(caseId);
        projectCase.setProjectId(projectId);
        return Result.success(projectService.updateCase(projectCase));
    }

    /**
     * 删除案例
     */
    @DeleteMapping("/{projectId}/cases/{caseId}")
    public Result<Boolean> deleteCase(@PathVariable Long projectId, @PathVariable Long caseId) {
        return Result.success(projectService.deleteCase(projectId, caseId));
    }

    /**
     * 上传案例图片
     */
    @PostMapping("/{projectId}/cases/{caseId}/image")
    public Result<String> uploadCaseImage(@PathVariable Long projectId, @PathVariable Long caseId,
                                        @RequestParam("file") MultipartFile file) {
        String imageUrl = projectService.uploadCaseImage(projectId, caseId, file);
        return Result.success(imageUrl);
    }

    /**
     * 上传项目视频
     */
    @PostMapping("/{projectId}/video")
    public Result<String> uploadProjectVideo(@PathVariable Long projectId, @RequestParam("file") MultipartFile file) {
        String videoUrl = projectService.uploadProjectVideo(projectId, file);
        return Result.success(videoUrl);
    }

    /**
     * 添加福利
     */
    @PostMapping("/{projectId}/benefits")
    public Result<ProjectBenefit> addBenefit(@PathVariable Long projectId, @RequestBody ProjectBenefit benefit) {
        benefit.setProjectId(projectId);
        ProjectBenefit savedBenefit = projectService.addBenefit(benefit);
        return Result.success(savedBenefit);
    }

    /**
     * 更新福利
     */
    @PutMapping("/{projectId}/benefits/{benefitId}")
    public Result<Boolean> updateBenefit(@PathVariable Long projectId, @PathVariable Long benefitId,
                                       @RequestBody ProjectBenefit benefit) {
        benefit.setId(benefitId);
        benefit.setProjectId(projectId);
        return Result.success(projectService.updateBenefit(benefit));
    }

    /**
     * 删除福利
     */
    @DeleteMapping("/{projectId}/benefits/{benefitId}")
    public Result<Boolean> deleteBenefit(@PathVariable Long projectId, @PathVariable Long benefitId) {
        return Result.success(projectService.deleteBenefit(projectId, benefitId));
    }
} 