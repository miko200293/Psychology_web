package com.oliver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oliver.entity.Counselors;
import com.oliver.entity.Users;
import com.oliver.mapper.CounselorsMapper;
import com.oliver.service.CounselorsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oliver.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author oliver
 * @since 2023-09-04
 */
@Service
public class CounselorsServiceImpl extends ServiceImpl<CounselorsMapper, Counselors> implements CounselorsService {
    @Autowired
    private CounselorsMapper counselorsMapper;

    @Override
    public void saveCounselors(Counselors courselors) {
        counselorsMapper.insertCounselors(courselors);

    }

    @Override
    public Result deletedCounselors(Counselors courselors) {
        QueryWrapper<Counselors>qw=new QueryWrapper<Counselors>();
        if(courselors.getCounselorID()!=null){
            qw.eq("CounselorID",courselors.getCounselorID());
        }
        Counselors counselors2 =this.getBaseMapper().selectOne(qw);
        if(counselors2==null){
            return Result.errorByCodeMessage(401,"删除的用户不存在");
        }
        counselorsMapper.deleteById(courselors);
        return Result.successString("删除用户成功");
    }

    @Override
    public Result updateSomeOne(Counselors counselors) {
        QueryWrapper<Counselors>qw=new QueryWrapper<>();
        qw.eq("CounselorID",counselors.getCounselorID());
        Counselors counselor3=this.getBaseMapper().selectOne(qw);
        if(counselor3==null){
            return Result.errorByCodeMessage(401,"更新用户不存在");
        }
        counselorsMapper.updateById(counselors);
        return Result.successString("更新成功");
    }

    @Override
    public IPage selectAll() {
        Page<Counselors> page1= new Page<>(0,8);
        IPage iPage= counselorsMapper.selectPage(page1,null);
        return iPage;
    }

    //unusefully
    @Override
    public Result login(Counselors courselors) {
        QueryWrapper qw= new QueryWrapper<>();
        qw.eq("CounselorID",courselors.getCounselorID());
        List<Counselors> list1=counselorsMapper.selectList(qw);
        if(list1.isEmpty()){
            return null;
        }
        return null;
    }

    @Override
    public Result searchSomeOne(Counselors counselors) {
        QueryWrapper qw= new  QueryWrapper<>();
        qw.eq("Name",counselors.getName());
        List<Counselors>list1=counselorsMapper.selectList(qw);
        if(list1.isEmpty()){
            return null;
        }
        return Result.successByKeyValue("success",list1);
    }

    @Override
    public Result searchByNumber(Counselors counselors) {
        QueryWrapper qw= new  QueryWrapper<>();
        qw.eq("Title",counselors.getTitle());
        List<Counselors>list1=counselorsMapper.selectList(qw);
        if(list1.isEmpty()){
            return null;
        }
        return Result.successByKeyValue("success",list1);
    }

    @Override
    public Result searchByNumber1(Counselors counselors) {
        QueryWrapper qw= new  QueryWrapper<>();
        qw.eq("Title",counselors.getTitle());
        List<Counselors>list1=counselorsMapper.selectList(qw);
        if(list1.isEmpty()){
            return null;
        }
        return Result.successByKeyValue("success",list1);
    }

    @Override
    public Result serarchById(Counselors counselors) {
        QueryWrapper qw= new  QueryWrapper<>();
        qw.eq("CounselorID",counselors.getCounselorID());
        List<Counselors>list1=counselorsMapper.selectList(qw);
        if(list1.isEmpty()){
            return null;
        }
        return Result.successByKeyValue("success",list1);
    }
}
