package com.oliver.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.oliver.entity.Counselors;
import com.oliver.entity.Users;
import com.oliver.mapper.CounselorsMapper;
import com.oliver.service.CounselorsService;
import com.oliver.utils.Result;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Enumeration;
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
@CrossOrigin(origins = "*", methods = {RequestMethod.POST, RequestMethod.OPTIONS})
@Slf4j
public class CounselorsController {


    @Autowired
    private CounselorsService   counselorsService;

    @Autowired
    private CounselorsMapper counselorsMapper;



    //管理员插入新咨询师资料
    @PostMapping("/insert")
    public Result getInserted(@RequestBody Counselors counselors){
        if(counselors==null){
            return Result.errorByCodeMessage(401,"传输的数据为空");
        }else if(counselors.getName()!=null||counselors.getGender()!=null||counselors.getIntroduction()!=null){
            counselorsService.saveCounselors(counselors);
            return Result.ok();

        }
        return Result.errorByCodeMessage(402,"插入失败");

    }
    //查询所有咨询师的资料 limited 10
    @GetMapping("/selectall")
    public List<Counselors> getSelected(){
        List<Counselors> list= counselorsMapper.findCounselors();
        return list;
    }

    //查询所有的咨询师资料，分页管理一页八行
    @PostMapping("/selectAll")
    public IPage getSelectedPage(){
        IPage iPage = counselorsService.selectAll();
        return iPage;
    }

    @PostMapping("/deletesomeone")
    public Result deleteSomeOne(@RequestBody Counselors counselors){
        if(counselors==null){
            return Result.errorByCodeMessage(401,"传输的数据为null");
        }else if(counselors.getCounselorID()!=null){
           Result result1= counselorsService.deletedCounselors(counselors);
            return result1;
        }
        return Result.errorByCodeMessage(402,"删除失败");

    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/serarchbyid")
    public Result serarchById(@RequestBody Counselors counselors){
        if(counselors==null){
            return Result.errorByCodeMessage(401,"传输的数据为null");
        }else if(counselors.getCounselorID()!=null){
            Result result1= counselorsService.serarchById(counselors);
            return result1;
        }
        return Result.errorByCodeMessage(402,"查询失败");

    }
    @PostMapping("/updatesomeone")
    public  Result updateSomeOne(@RequestBody Counselors counselors){
        if(counselors==null){
            return Result.errorByCodeMessage(401,"传输的数据为null");
        }else if(counselors.getCounselorID()!=null){
            Result result2=counselorsService.updateSomeOne(counselors);
            return result2;
        }
        return Result.errorByCodeMessage(403,"更新失败");
    }

    //login
    @PostMapping("/login")
    public Result login(@RequestBody Counselors counselors){
        counselorsService.login(counselors);
        return null;
    }

    @PostMapping("/searchsomeone")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public Result SearchSomeOne(@RequestBody Counselors counselors){
        Result result =counselorsService.searchSomeOne(counselors);
        return result;
    }

    @PostMapping("/searchbynumber")
    public Result SearchByNumber(@RequestBody Counselors counselors ){
        if(counselors==null){
            return Result.errorByCodeMessage(401,"传输的数据为null");}
        Result result =counselorsService.searchByNumber(counselors);
        return result;
    }

    @GetMapping("/searchbynumber")
    public Result SearchByNumber1(@RequestBody Counselors counselors){
        if(counselors==null){
            return Result.errorByCodeMessage(401,"传输的数据为null");}
        Result result =counselorsService.searchByNumber1(counselors);
        return result;
    }
}
