//package online.superarilo.myblog.config;
//
//
//import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
//import online.superarilo.myblog.utils.SnowFlakeUtil;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.UUID;
//
//@Configuration
//public class MybatisConfig {
//
//    @Bean
//    public IdentifierGenerator identifierGenerator() {
//        return new IdentifierGenerator() {
//            @Override
//            public String nextUUID(Object entity) {
//                return IdentifierGenerator.super.nextUUID(entity);
//            }
//
//            @Override
//            public Number nextId(Object entity) {
//                SnowFlakeUtil snowFlake = new SnowFlakeUtil(1L,1L);
//                return snowFlake.nextId();
//            }
//        };
//    }
//}
