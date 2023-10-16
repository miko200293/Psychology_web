package com.oliver.mapper;

import com.oliver.entity.Recommendation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author oliver
 * @since 2023-09-27
 */
@Mapper
public interface RecommendationMapper extends BaseMapper<Recommendation> {

    @Select("select *from recommendation limit 10")
    List<Recommendation> selectAll();

    @Insert(value = "insert into recommendation (id,title,summary,imageUrl,linkUrl,createdAt,updatedAt)values (#{id},#{title},#{summary},#{imageUrl},#{linkUrl},#{createdAt},#{updatedAt})")
    void addSomething(Recommendation recommendation);
}
