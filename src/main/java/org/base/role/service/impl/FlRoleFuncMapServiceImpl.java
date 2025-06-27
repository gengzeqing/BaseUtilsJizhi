package org.base.role.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.base.role.mapper.FlRoleFuncMapMapper;
import org.base.role.service.FlRoleFuncMapService;
import org.base.role.vo.FlRoleFuncMapVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
@Transactional(rollbackFor = Exception.class)
public class FlRoleFuncMapServiceImpl extends ServiceImpl<FlRoleFuncMapMapper, FlRoleFuncMapVo> implements FlRoleFuncMapService {

}
