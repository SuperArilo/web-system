package online.superarilo.myblog.controller;


import online.superarilo.myblog.entity.Tags;
import online.superarilo.myblog.service.ITagsService;
import online.superarilo.myblog.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
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
@RequestMapping("/tags")
public class TagsController {



    private ITagsService tagsService;

    @Autowired
    public void setTagsService(ITagsService ts) {
        this.tagsService = ts;
    }
    @GetMapping("/list")
    public Result<List<Tags>> listTags() {
        return tagsService.listTags();
    }

    @PostMapping("/save")
    public Result<String> saveTags(List<Tags> tags) {
        return new Result<>(true, HttpStatus.OK, "保存成功", "保存成功");
    }

    @PostMapping("/update")
    public Result<String> saveTags(Tags tag) {
        return new Result<>(true, HttpStatus.OK, "修改成功", "修改成功");
    }

    @PostMapping("/remove/{id}")
    public Result<String> removeTag(List<Long> tagIds) {
        return new Result<>(true, HttpStatus.OK, "删除成功", "删除成功");
    }
}
