package online.superarilo.myblog.controller;



import online.superarilo.myblog.service.IUsersDynamicsService;
import online.superarilo.myblog.utils.Result;
import online.superarilo.myblog.vo.UsersDynamicsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author caoguirong
 * @since 2022-01-08
 */
@RestController
@RequestMapping("/dynamic")
public class UsersDynamicsController {

    @Autowired
    private IUsersDynamicsService usersDynamicsService;

    /**
     * 根据条件查询所有动态信息
     * @return
     */
    @GetMapping("/list")
    public Result<List<UsersDynamicsVO>> listUserDynamics(Integer[] tagIds,
                                                          String order,
                                                          @RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber,
                                                          @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("tagIds", tagIds);
        queryParams.put("order", order);
        queryParams.put("pageNumber", (pageNumber -1) * pageSize);
        queryParams.put("pageSize", pageSize);
        return new Result<>(true, HttpStatus.OK, "success", usersDynamicsService.listUserDynamics(queryParams));
    }


    @PostMapping("/")
    public Result<String> saveUserDynamic(@RequestBody UsersDynamicsVO usersDynamicsVO) {
        return usersDynamicsService.saveUserDynamic(usersDynamicsVO);
    }


}
