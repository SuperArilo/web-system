package online.superarilo.myblog.config;


import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;


@Configuration
public class MybatisConfig {

    private static int inc = 0;

    @Bean
    public IdentifierGenerator identifierGenerator() {

        return new IdentifierGenerator() {

            private static final long LIMIT = 10000000000L;
            private static long last = 0;

            @Override
            public String nextUUID(Object entity) {
                return IdentifierGenerator.super.nextUUID(entity);
            }

            @Override
            public Number nextId(Object entity) {
                // 10 digits.
                long id = System.currentTimeMillis() % LIMIT;
                if ( id <= last ) {
                    id = (last + 1) % LIMIT;
                }
                return last = id;

//                SnowFlakeUtil snowFlake = new SnowFlakeUtil(1,1,1);
//                return snowFlake.nextId();
            }
        };
    }
}
