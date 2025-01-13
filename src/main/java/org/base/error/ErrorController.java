package org.base.error;

import lombok.RequiredArgsConstructor;
import org.base.error.service.ErrorService;
import org.base.error.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
public class ErrorController {

    private final ErrorService errorService;

    @PostMapping(value = "/data")
    public String getDate() {
        User user = new User();
        user.setId(1L);
        user.setName("测试异常");
        return errorService.getDate(user);
    }

}
