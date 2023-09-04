package com.oliver.mapper;

import com.oliver.entity.Counselors;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oliver.entity.Users;
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
 * @since 2023-09-04
 */
@Mapper
public interface CounselorsMapper extends BaseMapper<Counselors> {
    @Select("select * from Counselors limit 10 ")
    public List<Counselors> findCounselors();

    @Insert(value = "insert into Counselors(Name, Gender, Title, Introduction,Qualifications) values (#{Name},#{Gender},#{Title},#{Introduction},#{Qualifications})")
    void insertCounselors(Counselors counselors);
}
