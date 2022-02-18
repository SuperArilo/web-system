package online.superarilo.myblog.controller;


import online.superarilo.myblog.entity.DynamicComments;
import online.superarilo.myblog.service.IDynamicCommentService;
import online.superarilo.myblog.utils.Result;
import online.superarilo.myblog.vo.DynamicCommentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author caoguirong
 * @since 2022-02-18
 */
@RestController
@RequestMapping("/dynamic/comments")
public class DynamicCommentController {

    private static final int DEFAULT_PAGE_NUMBER = 0;
    private static final int DEFAULT_PAGE_SIZE = 8;

    private IDynamicCommentService dynamicCommentService;

    @Autowired
    public void setDynamicCommentsService(IDynamicCommentService dynamicCommentService) {
        this.dynamicCommentService = dynamicCommentService;
    }

    /**
     * 请求评论
     * @param dynamicId 需要查询的动态id
     * @return 结果
     */
    @GetMapping(value = "/list")
    public Result<Map<String, Object>> listComments(@RequestParam("dynamicId") Long dynamicId,
                                                   @RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber) {
        int pageStart = DEFAULT_PAGE_NUMBER;
        if(pageNumber > 1) {
            pageStart = (pageNumber - 1) * DEFAULT_PAGE_SIZE;
        }
        return dynamicCommentService.listCommentsByDynamicId(dynamicId, pageStart, DEFAULT_PAGE_SIZE);
    }

    /**
     * 用户评论
     */
    @PostMapping("/comment/{dynamicId}")
    public Result<String> commentByDynamicId(@PathVariable("dynamicId") Long dynamicId, @RequestBody DynamicCommentVO dynamicCommentVO, HttpServletRequest request) {
        return dynamicCommentService.commentByDynamicId(dynamicId, dynamicCommentVO, request);
    }

}
