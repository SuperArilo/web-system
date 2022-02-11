package online.superarilo.myblog.controller;



import com.alibaba.fastjson.JSONObject;
import online.superarilo.myblog.service.IUsersDynamicsService;
import online.superarilo.myblog.utils.RedisUtil;
import online.superarilo.myblog.utils.Result;
import online.superarilo.myblog.vo.UsersDynamicsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.TimeUnit;

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
        List<UsersDynamicsVO> usersDynamicsVOS;

        String key = "list:" + pageNumber + pageSize;
        if(RedisUtil.hasKey(key)) {
            usersDynamicsVOS = (List<UsersDynamicsVO>) JSONObject.parse(String.valueOf(RedisUtil.get(key)));
        }else {
            Map<String, Object> queryParams = new HashMap<>();
            queryParams.put("tagIds", tagIds);
            queryParams.put("order", order);
            queryParams.put("pageNumber", (pageNumber - 1) * pageSize);
            queryParams.put("pageSize", pageSize);
            usersDynamicsVOS = usersDynamicsService.listUserDynamics(queryParams);
            RedisUtil.set("list:" + pageNumber + pageSize, JSONObject.toJSONString(usersDynamicsVOS), 24 * 60 * 60L, TimeUnit.SECONDS);
        }
        return new Result<>(true, HttpStatus.OK, "success", usersDynamicsVOS);
    }

    @GetMapping("/details")
    public Result<UsersDynamicsVO> queryDynamicById(Long dynamicId, HttpServletRequest request) {
        usersDynamicsService.incrementDynamicPageView(dynamicId, request);
        return usersDynamicsService.queryDynamicById(dynamicId);
    }


    @PostMapping("/")
    public Result<String> saveUserDynamic(@RequestBody UsersDynamicsVO usersDynamicsVO) {

        Set<String> keys = RedisUtil.likeKeys("list:*");
        if(!Objects.isNull(keys) && keys.size() > 0) {
            for (String key: keys) {
                RedisUtil.delete(key);
            }
        }
        return usersDynamicsService.saveUserDynamic(usersDynamicsVO);
    }

    /**
     * 动态浏览量递增
     * @param dynamicId
     * @param request
     * @return
     */
//    @GetMapping("/increment")
//    public Result<String> incrementDynamicPageView(Integer dynamicId, HttpServletRequest request) {
//        return usersDynamicsService.incrementDynamicPageView(dynamicId, request);
//    }

}
