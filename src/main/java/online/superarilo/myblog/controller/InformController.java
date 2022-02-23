package online.superarilo.myblog.controller;


import online.superarilo.myblog.service.IInformService;
import online.superarilo.myblog.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author caoguirong
 * @since 2022-02-23
 */
@RestController
@RequestMapping("/inform")
public class InformController {

    private IInformService informService;

    @Autowired
    public void setInformService(IInformService informService) {
        this.informService = informService;
    }


    @GetMapping("/list")
    public JsonResult listInformByReceiver(@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber, @RequestParam("receiver") Long receiver) {
        return informService.listInformByReceiver(pageNumber, receiver);
    }


}
