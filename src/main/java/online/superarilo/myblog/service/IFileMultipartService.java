package online.superarilo.myblog.service;

import online.superarilo.myblog.utils.Result;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface IFileMultipartService {

    /**
     * 上传图片（图片将进行压缩处理）
     * @param imageFile
     * @param business
     * @param response
     */
    Result<Map<String, String>> uploadImage(MultipartFile imageFile, String business, HttpServletResponse response);
}
