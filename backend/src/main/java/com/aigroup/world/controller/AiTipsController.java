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

        // 获取锦囊下的所有项目信息
        List<SharePosterData.ProjectInfo> projectInfos = aiTips.getProjects().stream()
            .map(project -> {
                SharePosterData.ProjectInfo info = new SharePosterData.ProjectInfo();
                info.setTitle(project.getName());
                info.setDescription(project.getDescription());
                info.setVideoUrl(project.getVideoUrl());
                
                // 获取所有项目案例
                if (project.getCases() != null && !project.getCases().isEmpty()) {
                    List<String> cases = project.getCases().stream()
                        .map(projectCase -> {
                            StringBuilder caseInfo = new StringBuilder();
                            // 添加案例描述
                            caseInfo.append(projectCase.getDescription());
                            // 如果有图片，添加图片URL
                            if (projectCase.getImageUrl() != null && !projectCase.getImageUrl().isEmpty()) {
                                caseInfo.append(" [图片: ").append(projectCase.getImageUrl()).append("]");
                            }
                            return caseInfo.toString();
                        })
                        .collect(Collectors.toList());
                    info.setCases(cases);
                }
                
                // 获取所有项目福利
                if (project.getBenefits() != null && !project.getBenefits().isEmpty()) {
                    List<String> benefits = project.getBenefits().stream()
                        .map(ProjectBenefit::getContent)
                        .collect(Collectors.toList());
                    info.setBenefits(benefits);
                }
                
                return info;
            })
            .collect(Collectors.toList());

        posterData.setProjects(projectInfos);
        return Result.success(posterData);
    }
} 