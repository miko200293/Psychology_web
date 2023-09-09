package com.oliver.service;

import com.oliver.entity.Counselors;
import com.baomidou.mybatisplus.extension.service.IService;
import com.oliver.entity.Users;
import com.oliver.utils.Result;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author oliver
 * @since 2023-09-04
 */
public interface CounselorsService extends IService<Counselors> {
    void saveCounselors(Counselors courselors );

    Result deletedCounselors(Counselors courselors );

    Result updateSomeOne(Counselors counselors);
}
