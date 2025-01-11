package com.aigroup.world.controller;

import com.aigroup.world.common.Result;
import com.aigroup.world.entity.AiTips;
import com.aigroup.world.entity.Project;
import com.aigroup.world.entity.ProjectCase;
import com.aigroup.world.entity.ProjectBenefit;
import com.aigroup.world.service.AiTipsService;
import com.aigroup.world.dto.SharePosterData;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * AI锦囊Controller
 */
@RestController
@RequestMapping("/aiGroup/tips")
public class AiTipsController {

    @Autowired
    private AiTipsService aiTipsService;

    /**
     * 分页获取AI锦囊列表
     */
    @GetMapping("/list")
    public Result<IPage<AiTips>> getAiTipsList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String category) {
        IPage<AiTips> pageResult = aiTipsService.getAiTipsList(page, size, category);
        return Result.success(pageResult);
    }

    /**
     * 获取AI锦囊详情
     */
    @GetMapping("/{id}")
    public Result<AiTips> getAiTipsDetail(@PathVariable Long id) {
        AiTips aiTips = aiTipsService.getAiTipsDetail(id);
        if (aiTips != null) {
            // 更新浏览量
            aiTipsService.updateViews(id);
            return Result.success(aiTips);
        }
        return Result.error("锦囊不存在");
    }

    /**
     * 创建AI锦囊
     */
    @PostMapping
    public Result<Boolean> createAiTips(@RequestBody AiTips aiTips) {
        return Result.success(aiTipsService.createAiTips(aiTips));
    }

    /**
     * 更新AI锦囊
     */
    @PutMapping("/{id}")
    public Result<Boolean> updateAiTips(@PathVariable Long id, @RequestBody AiTips aiTips) {
        aiTips.setId(id);
        return Result.success(aiTipsService.updateAiTips(aiTips));
    }

    /**
     * 删除AI锦囊
     */
    @DeleteMapping("/{id}")
    public Result<Boolean> deleteAiTips(@PathVariable Long id) {
        return Result.success(aiTipsService.deleteAiTips(id));
    }

    /**
     * 点赞AI锦囊
     */
    @PostMapping("/{id}/like")
    public Result<Boolean> likeAiTips(@PathVariable Long id) {
        return Result.success(aiTipsService.updateLikes(id, 1));
    }

    /**
     * 取消点赞AI锦囊
     */
    @DeleteMapping("/{id}/like")
    public Result<Boolean> unlikeAiTips(@PathVariable Long id) {
        return Result.success(aiTipsService.updateLikes(id, -1));
    }

    /**
     * 获取分享海报数据
     */
    @GetMapping("/{id}/poster")
    public Result<SharePosterData> getSharePoster(@PathVariable Long id) {
        AiTips aiTips = aiTipsService.getAiTipsDetail(id);
        if (aiTips == null) {
            return Result.error("锦囊不存在");
        }

        SharePosterData posterData = new SharePosterData();
        posterData.setTitle(aiTips.getTitle());
        posterData.setBrandName("AI群江湖");
        posterData.setQrCodeTip("扫码查看详情");

        List<SharePosterData.Project> projectInfos = new ArrayList<>();
        
        for (Project project : aiTips.getProjects()) {
            SharePosterData.Project projectInfo = new SharePosterData.Project();
            projectInfo.setTitle(project.getName());
            projectInfo.setDescription(project.getDescription());
            
            // 设置案例列表
            if (project.getCases() != null && !project.getCases().isEmpty()) {
                List<String> cases = project.getCases().stream()
                    .map(ProjectCase::getDescription)
                    .collect(Collectors.toList());
                projectInfo.setCases(cases);
            }
            
            // 设置福利列表
            if (project.getBenefits() != null && !project.getBenefits().isEmpty()) {
                List<String> benefits = project.getBenefits().stream()
                    .map(ProjectBenefit::getContent)
                    .collect(Collectors.toList());
                projectInfo.setBenefits(benefits);
            }
            
            projectInfos.add(projectInfo);
        }

        posterData.setProjects(projectInfos);
        return Result.success(posterData);
    }
} 