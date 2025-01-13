package org.base.error.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.base.error.ParamException;
import org.base.error.annotation.Loggable;
import org.base.error.service.ErrorService;
import org.base.error.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
@Transactional(rollbackFor = Exception.class)
public class ErrorServiceImpl implements ErrorService {


    @Loggable(bindParam="user",taskId = "name")
    @Override
    public String getDate(User user) {
        if (user.getId() == 12) {
            return "成功";
        }else {
            throw new ParamException("错误");
        }
    }
}
