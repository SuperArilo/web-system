package online.superarilo.myblog;





import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;


@SpringBootTest
class BackgroundSystemApplicationTests {

    @Test
    void contextLoads() {

//        FastAutoGenerator.create("jdbc:mysql://localhost:3357/myblog_dev?useUnicode=true&character_set_server=utf8mb4&serverTimezone=Asia/Shanghai", "root", "123456")
//                .globalConfig(builder -> {
//                    builder.author("caoguirong") // 设置作者
//                            .outputDir("D:\\workspaces\\web-system\\src\\main\\java\\"); // 指定输出目录
//                })
//                .packageConfig(builder -> {
//                    builder.parent("online.superarilo") // 设置父包名
//                            .moduleName("myblog") // 设置父包模块名
//                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, "D:\\workspaces\\web-system\\src\\main\\resources\\myblog\\mapper\\")); // 设置mapperXml生成路径
//                })
//                .strategyConfig(builder -> {
//                    builder.addInclude("users_dynamics", "user_information", "tags","reporting_system","dynamic_tags_relations","dynamic_comments","collections"); // 设置需要生成的表名
//                })
//                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
//                .execute();



    }

}
