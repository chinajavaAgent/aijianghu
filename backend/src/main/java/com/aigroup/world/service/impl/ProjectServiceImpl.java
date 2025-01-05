package com.aigroup.world.service.impl;

import com.aigroup.world.entity.Project;
import com.aigroup.world.entity.ProjectCase;
import com.aigroup.world.entity.ProjectBenefit;
import com.aigroup.world.mapper.ProjectMapper;
import com.aigroup.world.mapper.ProjectCaseMapper;
import com.aigroup.world.mapper.ProjectBenefitMapper;
import com.aigroup.world.service.ProjectService;
import com.aigroup.world.service.FileService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private ProjectCaseMapper projectCaseMapper;

    @Autowired
    private ProjectBenefitMapper projectBenefitMapper;

    @Autowired
    private FileService fileService;

    @Override
    public List<Project> getProjectsByTipId(Long tipId) {
        // 获取项目列表
        List<Project> projects = projectMapper.selectList(
            new LambdaQueryWrapper<Project>()
                .eq(Project::getTipId, tipId)
        );

        // 加载每个项目的案例和福利
        for (Project project : projects) {
            List<ProjectCase> cases = projectCaseMapper.selectList(
                new LambdaQueryWrapper<ProjectCase>()
                    .eq(ProjectCase::getProjectId, project.getId())
            );
            project.setCases(cases);

            List<ProjectBenefit> benefits = projectBenefitMapper.selectList(
                new LambdaQueryWrapper<ProjectBenefit>()
                    .eq(ProjectBenefit::getProjectId, project.getId())
            );
            project.setBenefits(benefits);
        }

        return projects;
    }

    @Override
    @Transactional
    public Project createProject(Project project) {
        projectMapper.insert(project);
        return project;
    }

    @Override
    @Transactional
    public boolean updateProject(Project project) {
        return projectMapper.updateById(project) > 0;
    }

    @Override
    @Transactional
    public boolean deleteProject(Long id) {
        // 删除项目及其关联的案例和福利
        projectCaseMapper.delete(
            new LambdaQueryWrapper<ProjectCase>()
                .eq(ProjectCase::getProjectId, id)
        );
        projectBenefitMapper.delete(
            new LambdaQueryWrapper<ProjectBenefit>()
                .eq(ProjectBenefit::getProjectId, id)
        );
        return projectMapper.deleteById(id) > 0;
    }

    @Override
    @Transactional
    public ProjectCase addCase(ProjectCase projectCase) {
        projectCaseMapper.insert(projectCase);
        return projectCase;
    }

    @Override
    @Transactional
    public boolean updateCase(ProjectCase projectCase) {
        return projectCaseMapper.updateById(projectCase) > 0;
    }

    @Override
    @Transactional
    public boolean deleteCase(Long projectId, Long caseId) {
        return projectCaseMapper.delete(
            new LambdaQueryWrapper<ProjectCase>()
                .eq(ProjectCase::getProjectId, projectId)
                .eq(ProjectCase::getId, caseId)
        ) > 0;
    }

    @Override
    public String uploadCaseImage(Long projectId, Long caseId, MultipartFile file) {
        String imageUrl = fileService.uploadImage(file);
        
        // 更新案例图片URL
        ProjectCase projectCase = new ProjectCase();
        projectCase.setId(caseId);
        projectCase.setProjectId(projectId);
        projectCase.setImageUrl(imageUrl);
        projectCaseMapper.updateById(projectCase);
        
        return imageUrl;
    }

    @Override
    public String uploadProjectVideo(Long projectId, MultipartFile file) {
        String videoUrl = fileService.uploadVideo(file);
        
        // 更新项目视频URL
        Project project = new Project();
        project.setId(projectId);
        project.setVideoUrl(videoUrl);
        projectMapper.updateById(project);
        
        return videoUrl;
    }

    @Override
    @Transactional
    public ProjectBenefit addBenefit(ProjectBenefit benefit) {
        projectBenefitMapper.insert(benefit);
        return benefit;
    }

    @Override
    @Transactional
    public boolean updateBenefit(ProjectBenefit benefit) {
        return projectBenefitMapper.updateById(benefit) > 0;
    }

    @Override
    @Transactional
    public boolean deleteBenefit(Long projectId, Long benefitId) {
        return projectBenefitMapper.delete(
            new LambdaQueryWrapper<ProjectBenefit>()
                .eq(ProjectBenefit::getProjectId, projectId)
                .eq(ProjectBenefit::getId, benefitId)
        ) > 0;
    }
} 