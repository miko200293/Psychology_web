package com.oliver.controller;

import com.oliver.entity.Counselors;
import com.oliver.entity.Users;
import com.oliver.mapper.CounselorsMapper;
import com.oliver.service.CounselorsService;
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
 * @since 2023-09-04
 */
@RestController
@RequestMapping("/counselors")
public class CounselorsController {


    @Autowired
    private CounselorsService   counselorsService;

    @Autowired
    private CounselorsMapper counselorsMapper;

    //管理员插入新咨询师资料
    @PostMapping("/insert")
    public Result getInserted(@RequestBody Counselors counselors){
        if(counselors==null){
            return Result.error();
        }else if(counselors.getName()!=null||counselors.getGender()!=null||counselors.getIntroduction()!=null){
            counselorsService.saveCounselors(counselors);
            return Result.ok();

        }
        return Result.error();

    }
    //查询所有咨询师的资料 limited 10
    @GetMapping("/selectall")
    public List<Counselors> getSelected(){
        List<Counselors> list= counselorsMapper.findCounselors();
        return list;
    }

}
