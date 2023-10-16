package com.oliver.service;

import com.oliver.entity.Recommendation;
import com.baomidou.mybatisplus.extension.service.IService;
import com.oliver.utils.Result;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author oliver
 * @since 2023-09-27
 */
public interface RecommendationService extends IService<Recommendation> {

    List<Recommendation> selectAllNews();

    Result addsomting(Recommendation recommendation);
}
