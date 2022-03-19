package online.superarilo.myblog.controller;

import online.superarilo.myblog.utils.Result;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class indexController {
    @RequestMapping("/notfound")
    @ResponseBody
    public Result<?> notFound() {
        return new Result<>(false, HttpStatus.NOT_FOUND, "你访问了服务器不存在的地方！");
    }

    @RequestMapping("/functionerror")
    @ResponseBody
    public Result<?> functionError() {
        return new Result<>(false, HttpStatus.METHOD_NOT_ALLOWED, "请求的方式不允许！");
    }

    @RequestMapping("/valueerror")
    @ResponseBody
    public Result<?> valueError() {
        return new Result<>(false, HttpStatus.BAD_REQUEST, "提交到服务器的参数类型有错误！");
    }

    @RequestMapping("/")
    public String index() {
        return "index";
    }
}
