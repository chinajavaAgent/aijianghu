package com.aigroup.world.controller;

import com.aigroup.world.entity.AiTips;
import com.aigroup.world.service.AiTipsService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<IPage<AiTips>> getAiTipsList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String category) {
        return ResponseEntity.ok(aiTipsService.getAiTipsList(page, size, category));
    }

    /**
     * 获取AI锦囊详情
     */
    @GetMapping("/{id}")
    public ResponseEntity<AiTips> getAiTipsDetail(@PathVariable Long id) {
        AiTips aiTips = aiTipsService.getAiTipsDetail(id);
        if (aiTips != null) {
            // 更新浏览量
            aiTipsService.updateViews(id);
            return ResponseEntity.ok(aiTips);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * 创建AI锦囊
     */
    @PostMapping
    public ResponseEntity<Boolean> createAiTips(@RequestBody AiTips aiTips) {
        return ResponseEntity.ok(aiTipsService.createAiTips(aiTips));
    }

    /**
     * 更新AI锦囊
     */
    @PutMapping("/{id}")
    public ResponseEntity<Boolean> updateAiTips(@PathVariable Long id, @RequestBody AiTips aiTips) {
        aiTips.setId(id);
        return ResponseEntity.ok(aiTipsService.updateAiTips(aiTips));
    }

    /**
     * 删除AI锦囊
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteAiTips(@PathVariable Long id) {
        return ResponseEntity.ok(aiTipsService.deleteAiTips(id));
    }

    /**
     * 点赞AI锦囊
     */
    @PostMapping("/{id}/like")
    public ResponseEntity<Boolean> likeAiTips(@PathVariable Long id) {
        return ResponseEntity.ok(aiTipsService.updateLikes(id, 1));
    }

    /**
     * 取消点赞AI锦囊
     */
    @DeleteMapping("/{id}/like")
    public ResponseEntity<Boolean> unlikeAiTips(@PathVariable Long id) {
        return ResponseEntity.ok(aiTipsService.updateLikes(id, -1));
    }
} 