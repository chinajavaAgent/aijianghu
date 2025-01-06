package com.aigroup.world.controller;

import com.aigroup.world.common.Result;
import com.aigroup.world.entity.AiTips;
import com.aigroup.world.entity.Project;
import com.aigroup.world.service.AiTipsService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * AI锦囊Controller
 */
@RestController
@RequestMapping("/api/tips")
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
    @GetMapping("/{id}/share-poster")
    public Result<Map<String, Object>> getSharePoster(@PathVariable Long id) {
        AiTips aiTips = aiTipsService.getAiTipsDetail(id);
        if (aiTips == null) {
            return Result.error("锦囊不存在");
        }

        Map<String, Object> posterData = new HashMap<>();
        posterData.put("title", aiTips.getTitle());
        posterData.put("description", aiTips.getDescription());
        posterData.put("backgroundColor", "#FFFFFF");
        posterData.put("brandName", "AI锦囊");
        
        // 获取成功案例
        List<String> cases = new ArrayList<>();
        if (aiTips.getProjects() != null && !aiTips.getProjects().isEmpty()) {
            for (Project project : aiTips.getProjects()) {
                if (project.getCases() != null && !project.getCases().isEmpty()) {
                    cases.add(project.getCases().get(0).getDescription());
                }
            }
        }
        posterData.put("cases", cases);
        posterData.put("qrCodeTip", "扫码查看详情");

        return Result.success(posterData);
    }
} 