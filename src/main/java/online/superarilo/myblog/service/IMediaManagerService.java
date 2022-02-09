package online.superarilo.myblog.service;

import online.superarilo.myblog.entity.MediaManager;
import com.baomidou.mybatisplus.extension.service.IService;
import online.superarilo.myblog.utils.Result;
import online.superarilo.myblog.vo.MediaManagerVO;
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
     * 查询用户的媒体数据
     * @param uid
     * @return
     */
    Result<List<MediaManager>> listMediaByUid(Long uid);


    /**
     * 用户上传图片集合
     * @param files
     * @param uid
     * @return
     */
    Result<List> uploadImages(List<MultipartFile> files, Long uid);


    /**
     * 删除用户媒体数据
     * @param mediaIds
     * @param uid
     * @return
     */
    Result<String> removeMediaByMeidaIdAndUid(List<Integer> mediaIds, Long uid);


}
