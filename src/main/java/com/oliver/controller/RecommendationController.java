package com.oliver.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oliver.entity.Counselors;
import com.oliver.entity.Recommendation;
import com.oliver.service.RecommendationService;
import com.oliver.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author oliver
 * @since 2023-09-27
 */
@RestController
@RequestMapping("/recommendation")
@CrossOrigin
public class RecommendationController {

    @Autowired
    private RecommendationService recommendationService;

    //查询所有的新闻，限制了10行
    @GetMapping("/selectall")
        public IPage selectAllNews(){
        IPage iPage = recommendationService.selectAllNews();
        return iPage;

    }

    //更新最新新闻文章
    @PostMapping("/updatenews")
    public Result updateNews(@RequestBody Recommendation recommendation){
        Result result =recommendationService.updateNews(recommendation);
        return result;
    }


    @DeleteMapping("/deleteMapping")
    public Result deleteNewsByID(@RequestBody Recommendation recommendation){
       Result result= recommendationService.deleteRecommendationByID(recommendation);
        return result;
    }

    @PostMapping("/addNews")
    public Result addNews(@RequestBody Recommendation recommendation){
       Result result= recommendationService.addsomting(recommendation);
        return result;

    }



}
