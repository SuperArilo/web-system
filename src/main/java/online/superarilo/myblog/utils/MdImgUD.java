package online.superarilo.myblog.utils;

import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class MdImgUD {
    public Boolean upload(List<MultipartFile> listFile, String path, List<String> listFileName) {
        for (int i = 0; i < listFile.size(); i++) {
            String sourcePath = path + "/" + listFileName.get(i);
            File contents = new File(path);
            if (!contents.exists()) {
                contents.mkdirs();
            }
            try {
                listFile.get(i).transferTo(new File(sourcePath));
            } catch (IOException e) {
                return false;
            }
        }
        return true;
    }
    public Map<String,Object> check(List<MultipartFile> listFile){
        Map<String,Object> map = new HashMap<>();
        Set<String> allowSuffix = new HashSet<>(Arrays.asList("jpg", "jpeg", "png", "gif"));
        List<String> fileSuffix = new ArrayList<>();
        for (MultipartFile file : listFile) {
            String fileName = Objects.requireNonNull(file.getOriginalFilename());
            int index = fileName.lastIndexOf(".");
            String suffix = fileName.substring(index + 1);
            if (index == -1 || suffix.isEmpty()) {
                map.put("message","文件后缀不能为空");
                map.put("result",false);
                return map;
            } else if (!allowSuffix.contains(suffix.toLowerCase())) {
                map.put("message","非法的文件，不允许的文件类型：" + suffix);
                map.put("result",false);
                return map;
            }
            String complete = getUUID() + "." + suffix;
            fileSuffix.add(complete);
        }
        map.put("result",true);
        map.put("fileSuffix",fileSuffix);
        return map;
    }
    public void delFile(String path,List<String> list) {
        for(String name : list){
            FileSystemUtils.deleteRecursively(new File(path + "//" + name));
        }
    }
    public String getUUID() {
        return UUID.randomUUID().toString().trim().replaceAll("-", "");
    }
}
