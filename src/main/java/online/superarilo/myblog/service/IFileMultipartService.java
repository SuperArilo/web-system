package online.superarilo.myblog.service;

import online.superarilo.myblog.utils.Result;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface IFileMultipartService {

    /**
     * 上传图片（图片将进行压缩处理）
     * @param imageFiles
     * @param uid
     */
//    Result<Map<String, String>> uploadImage(List<MultipartFile> imageFiles, Integer uid);
}
