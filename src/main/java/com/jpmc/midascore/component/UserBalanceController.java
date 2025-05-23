package com.jpmc.midascore.component;

import com.jpmc.midascore.foundation.Balance;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// 啥意思，大致讲一下RestfulAPI
//每个 URL 代表一个“资源”（对象），比如用户、订单、余额。
//用 HTTP 方法表示动作：
//GET 查，POST 增，PUT 改，DELETE 删。
//让接口看起来像“数据访问”，而不是“动作调用”，简洁优雅。
@RestController
//@RestController 是干嘛的？
// 处理“网络请求”的控制器。会自动帮你：
// 1. 把返回值转成 JSON；
// 2. 把请求参数绑定成 Java 对象；
// 3.接收前端发来的请求，返回结果。
public class UserBalanceController {
    private final DatabaseConduit databaseConduit;

    public UserBalanceController(DatabaseConduit databaseConduit) {
        this.databaseConduit = databaseConduit;
    }

    @GetMapping(value = "/balance")
    // @RequestParam 出错会发生什么
    // 没传参报错：MissingServletRequestParameterException: Required request parameter 'userId' for method parameter type Long is not present
    // 参数类型错误：MethodArgumentTypeMismatchException: Failed to convert value of type 'String' to required type 'Long'
    // HTTP 状态码均为：400 Bad Request
    public Balance queryBalance(@RequestParam("userId") Long userId) {
        float balance = databaseConduit.getUserBalance(userId);
        return new Balance(balance);
    }
}