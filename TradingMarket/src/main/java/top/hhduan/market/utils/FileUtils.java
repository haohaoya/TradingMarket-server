package top.hhduan.market.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: duanhaohao
 * @date: 2020/8/10 11:29
 * @description:
 * @version: 1.0
 */
@Slf4j
public class FileUtils {

    public static StringBuilder saveFile(MultipartFile file, StringBuilder stringBuilder) {
        // 判断文件是否为空
        if (!file.isEmpty()) {
            try {
                // 保存的文件路径(如果用的是Tomcat服务器，文件会上传到\\%TOMCAT_HOME%\\webapps\\YourWebProject\\upload\\文件夹中
                // )
                Resource resource = new ClassPathResource("");
                String filePath = resource.getFile().getAbsolutePath().replace("\\target\\classes", "") + "/src/main/resources/static/" + file.getOriginalFilename();
                log.info("filePath:" + filePath);
                stringBuilder.append(filePath).append(",");
                File saveDir = new File(filePath);
                if (!saveDir.getParentFile().exists()) {
                    saveDir.getParentFile().mkdirs();
                }
                // 转存文件
                file.transferTo(saveDir);
                return stringBuilder;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return stringBuilder;
    }
}
