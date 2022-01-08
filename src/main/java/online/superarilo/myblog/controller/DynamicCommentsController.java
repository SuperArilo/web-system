package online.superarilo.myblog.controller;

import online.superarilo.myblog.service.IDynamicCommentsService;
import online.superarilo.myblog.utils.Result;
import online.superarilo.myblog.vo.DynamicCommentsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author caoguirong
 * @since 2022-01-08
 */
@RestController
@RequestMapping("/dynamic/comments")
public class DynamicCommentsController {

    private static final int DEFAULT_PAGE_NUMBER = 0;
    private static final int PAGE_SIZE = 8;

    @Autowired
    private IDynamicCommentsService dynamicCommentsService;

    /**
     * 请求一级评论
     * @param dynamicId 需要查询的动态id
     * @return
     */
    @GetMapping(value = "/list")
    public Result<List<DynamicCommentsVO>> listComments(@RequestParam(value = "commentParentId", defaultValue = "0") Integer commentParentId,
                                                        @RequestParam("dynamicId") Integer dynamicId,
                                                        @RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber) {
        if(commentParentId < 0) {
            commentParentId = 0;
        }
        int pageStart = DEFAULT_PAGE_NUMBER;
        if(pageNumber > 1) {
            pageStart = (pageNumber - 1) * PAGE_SIZE;
        }

        return dynamicCommentsService.listCommentsByDynamicId(commentParentId, dynamicId, pageStart, PAGE_SIZE);
    }
}
