package online.superarilo.myblog.controller;


import online.superarilo.myblog.annotation.Log;
import online.superarilo.myblog.entity.SysDictionary;
import online.superarilo.myblog.service.ISysDictionaryService;
import online.superarilo.myblog.utils.Result;
import online.superarilo.myblog.vo.SysDictionaryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author caoguirong
 * @since 2022-02-10
 */
@RestController
@RequestMapping("/dictionary")
public class SysDictionaryController {


    private ISysDictionaryService sysDictionaryService;

    @Autowired
    public void setSysDictionaryService(ISysDictionaryService sds) {
        sysDictionaryService = sds;
    }

    @Log
    @GetMapping("/list")
    public Result<List<SysDictionaryVO>> list() {
        return sysDictionaryService.listDictionary();
    }

    @Log
    @PostMapping("/save")
    public Result<String> saveDictionary(@RequestBody SysDictionary sysDictionary, HttpServletRequest request) {
        return sysDictionaryService.saveDictionary(sysDictionary, request);
    }
}
