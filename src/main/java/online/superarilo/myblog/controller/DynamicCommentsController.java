package online.superarilo.myblog.controller;

import online.superarilo.myblog.entity.DynamicComments;
import online.superarilo.myblog.service.IDynamicCommentsService;
import online.superarilo.myblog.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @since 2022-02-18
 * 该类已弃用 替代为 online.superarilo.myblog.controller.DynamicCommentController
 */

//@RestController
//@RequestMapping("/dynamic/comments")
public class DynamicCommentsController {

    private static final int DEFAULT_PAGE_NUMBER = 0;
    private static final int PAGE_SIZE = 8;


    private IDynamicCommentsService dynamicCommentsService;

    @Autowired
    public void setDynamicCommentsService(IDynamicCommentsService dcs) {
        this.dynamicCommentsService = dcs;
    }

    /**
     * 请求一级评论
     * @param dynamicId 需要查询的动态id
     * @return 结果
     */
    @GetMapping(value = "/list")
    public Result<Map<String,Object>> listComments(@RequestParam(value = "commentParentId", defaultValue = "0") Long commentParentId,
                                                   @RequestParam("dynamicId") Long dynamicId,
                                                   @RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber) {
        if(commentParentId < 0) {
            commentParentId = 0L;
        }
        int pageStart = DEFAULT_PAGE_NUMBER;
        if(pageNumber > 1) {
            pageStart = (pageNumber - 1) * PAGE_SIZE;
        }
        Map<String, Object> map = dynamicCommentsService.listCommentsByDynamicId(commentParentId, dynamicId, pageStart, PAGE_SIZE);
        return new Result<>(true, HttpStatus.OK, "查询成功", map);
    }


    /**
     * 用户评论
     */
    @PostMapping("/comment/{dynamicId}")
    public Result<String> commentByDynamicId(@PathVariable("dynamicId") Long dynamicId, @RequestBody DynamicComments dynamicComments, HttpServletRequest request) {
        return dynamicCommentsService.commentByDynamicId(dynamicId, dynamicComments, request);
    }
}
