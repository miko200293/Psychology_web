package com.oliver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oliver.entity.Counselors;
import com.oliver.mapper.CounselorsMapper;
import com.oliver.service.CounselorsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
