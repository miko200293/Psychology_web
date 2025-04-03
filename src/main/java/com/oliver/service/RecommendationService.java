package com.oliver.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.oliver.entity.Recommendation;
import com.baomidou.mybatisplus.extension.service.IService;
import com.oliver.utils.Result;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author oliver
 * @since 2023-09-27
 */
public interface RecommendationService extends IService<Recommendation> {

    IPage selectAllNews();

    Result addsomting(Recommendation recommendation);

    Result deleteRecommendationByID(Recommendation recommendation);

    Result updateNews(Recommendation recommendation);



    Result selectOneNews(Recommendation recommendation);
}
