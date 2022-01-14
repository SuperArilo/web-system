package online.superarilo.myblog.service;

import online.superarilo.myblog.entity.MediaManager;
import com.baomidou.mybatisplus.extension.service.IService;
import online.superarilo.myblog.utils.Result;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author caoguirong
 * @since 2022-01-12
 */
public interface IMediaManagerService extends IService<MediaManager> {


    /**
     * 用户上传图片集合
     * @param files
     * @param uid
     * @return
     */
    Result<List> uploadImages(List<MultipartFile> files, Integer uid);
}
