package com.example.backgroundsystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.backgroundsystem.entity.Dynamic;
import com.example.backgroundsystem.entity.DynamicImage;
import com.example.backgroundsystem.mapper.DynamicMapper;
import com.example.backgroundsystem.service.DynamicImageInterface;
import com.example.backgroundsystem.service.DynamicInterface;
import com.example.backgroundsystem.utils.MdImgUD;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class DynamicService extends ServiceImpl<DynamicMapper, Dynamic> implements  DynamicInterface {

    private final DynamicImageInterface dynamicImageInterface;
    private final DynamicMapper dynamicMapper;
    public DynamicService(DynamicImageInterface dynamicImageInterface, DynamicMapper dynamicMapper) {
        this.dynamicImageInterface = dynamicImageInterface;
        this.dynamicMapper = dynamicMapper;
    }

    @Override
    public Object dynamicGetFromPage(Integer page, Integer size) {
        return dynamicMapper.dynamicGet((page - 1) * size, size);
    }

    @Override
    public Map<String, Object> dynamicSet(Integer uid, String content, List<MultipartFile> listFile, HttpServletRequest request) {
        Map<String,Object> returnMap = new HashMap<>();
        MdImgUD mdImgUD = new MdImgUD();
        Map<String,Object> fileMap = mdImgUD.check(listFile);
        if (!(Boolean) fileMap.get("result")){
            return fileMap;
        }
        String path = "D:/TempCenter/imageBlog/dynamic";
        List<String> listFileName = (List<String>) fileMap.get("fileSuffix");
        if (mdImgUD.upload(listFile,path,listFileName)){
            Dynamic dynamicNew = new Dynamic();
            dynamicNew.setContent(content);
            dynamicNew.setCommentSum(0);
            dynamicNew.setWatchSum(0);
            dynamicNew.setLikeSum(0);
            dynamicNew.setCreateTime(new Date());
            dynamicNew.setUId(uid);
            this.save(dynamicNew);
            List<DynamicImage> dynamicImageList = new ArrayList<>();
            for(String fileNameSub : listFileName) {
                DynamicImage dynamicImageNew = new DynamicImage();
                dynamicImageNew.setDynamicId(dynamicNew.getDynamicId());
                dynamicImageNew.setImageName(fileNameSub);
                dynamicImageNew.setImageUrl(request.getRequestURL().toString().replace(request.getRequestURI(),"") + "/image/dynamic/" + fileNameSub);
                dynamicImageList.add(dynamicImageNew);
            }
            dynamicImageInterface.saveBatch(dynamicImageList);
            returnMap.put("result",true);
            returnMap.put("message","新建成功！");
        } else {
            returnMap.put("message","发生错误！");
            returnMap.put("result",false);
        }
        return returnMap;
    }

    @Override
    public void dynamicDel(Integer userId, List<Integer> dynamicIdList) {
        MdImgUD mdImgUD = new MdImgUD();
        String path = "D:/TempCenter/imageBlog/dynamic";
        List<String> fileNameList = this.dynamicMapper.dynamicImageNameGet(dynamicIdList);
        new Thread(() -> mdImgUD.delFile(path,fileNameList)).start();
        new Thread(() -> this.dynamicMapper.dynamicDel(userId,dynamicIdList)).start();
    }
}
