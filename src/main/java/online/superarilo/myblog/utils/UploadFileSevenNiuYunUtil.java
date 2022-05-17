package online.superarilo.myblog.utils;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.apache.shiro.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

@Component
public class UploadFileSevenNiuYunUtil {


    private static final String ACCESS_KEY = "seven_niu_yun.AccessKey";
    private static final String SECRET_KEY = "seven_niu_yun.SecretKey";
    private static final String BUCKET = "seven_niu_yun.bucket";
    private static final String FILE_URI = "seven_niu_yun.fileUri";

    private static Auth auth;

    private static Environment environment;
    @Autowired
    public void setEnvironment(Environment env) {
        UploadFileSevenNiuYunUtil.environment = env;
        auth = Auth.create(environment.getProperty(ACCESS_KEY), environment.getProperty(SECRET_KEY));
    }

    public static String upload(InputStream steam, String key) {
        Configuration configuration = new Configuration(Region.region1());
        UploadManager uploadManager = new UploadManager(configuration);
        try {
            String token = auth.uploadToken(environment.getProperty(BUCKET));
            if(!StringUtils.hasLength(token)) {
                System.out.println("token is null");
               return null;
            }
            key = environment.getProperty(FILE_URI) + new SimpleDateFormat(DateUtil.YYYY_MM_BIAS_PATTERN).format(new Date()) + "/" + key;
            Response response = uploadManager.put(steam, key, token, null, null);

            if(response.isOK()) {
                return key;
            }
        } catch (QiniuException e) {
            Response r = e.response;
            // 请求失败时打印的异常的信息
            e.printStackTrace();
            System.out.println("error "+r.toString());
            try {
                // 响应的文本信息
                System.out.println(r.bodyString());
            } catch (QiniuException e1) {
                System.out.println("error "+e1.error());
            }
        }
        return null;
    }

    public static void delete(String key) {
        Configuration configuration = new Configuration(Region.region1());
        BucketManager bucketManager = new BucketManager(auth, configuration);
        try {
            bucketManager.delete(environment.getProperty(BUCKET), key);
        } catch (QiniuException e) {
            Response r = e.response;
            // 请求失败时打印的异常的信息
            e.printStackTrace();
            System.out.println("error "+r.toString());
            try {
                // 响应的文本信息
                System.out.println(r.bodyString());
            } catch (QiniuException e1) {
                System.out.println("error "+e1.error());
            }
        }
    }
}
