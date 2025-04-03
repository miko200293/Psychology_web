package com.oliver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oliver.entity.Counselors;
import com.oliver.entity.Recommendation;
import com.oliver.mapper.RecommendationMapper;
import com.oliver.service.RecommendationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oliver.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author oliver
 * @since 2023-09-27
 */
@Service
public class RecommendationServiceImpl extends ServiceImpl<RecommendationMapper, Recommendation> implements RecommendationService {
    @Autowired
    private RecommendationMapper recommendationMapper;
    @Override
    public IPage selectAllNews() {
        Page<Recommendation> page1 = new Page<>(0,8);
        IPage iPage=recommendationMapper.selectPage(page1,null);
        return iPage;

    }

    @Override
    public Result addsomting(Recommendation recommendation) {
        QueryWrapper<Recommendation>qw=new QueryWrapper<Recommendation>();
        qw.eq("title",recommendation.getTitle());
        List<Recommendation> recommendation2 = recommendationMapper.selectList(qw);
        if(recommendation2.isEmpty()) {
            recommendationMapper.addSomething(recommendation);
            return Result.successByCodeMessage(200,"ok");
        }

        return Result.errorByCodeMessage(400,"faild");
    }

    @Override
    public Result deleteRecommendationByID(Recommendation recommendation) {
        QueryWrapper<Recommendation>qw=new QueryWrapper<Recommendation>();
        qw.eq("id",recommendation.getId());
        List<Recommendation> recommendation3=recommendationMapper.selectList(qw);
        if(!recommendation3.isEmpty()) {
            recommendationMapper.deleteById(recommendation.getId());
            return Result.successByCodeMessage(200,"成功删除");
        }

        return Result.errorByCodeMessage(400,"删除失败");
    }

    @Override
    public Result updateNews(Recommendation recommendation) {
        QueryWrapper<Recommendation>qw =new QueryWrapper<Recommendation>();
        qw.eq("id",recommendation.getId());
        List<Recommendation> recommendation4=recommendationMapper.selectList(qw);
        if(!recommendation4.isEmpty()){
            recommendationMapper.updateById(recommendation);
            return Result.successByCodeMessage(200,"更新成功");
        }
        return Result.errorByCodeMessage(400,"更新失败");
    }

    @Override
    public Result selectOneNews(Recommendation recommendation) {
        QueryWrapper qw= new  QueryWrapper<>();
        qw.eq("id",recommendation.getId());
        List<Recommendation>list1=recommendationMapper.selectList(qw);
        if(list1.isEmpty()){
            return null;
        }
        return Result.successByKeyValue("success",list1);
    }


}
