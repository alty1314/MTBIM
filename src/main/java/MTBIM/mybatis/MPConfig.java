package MTBIM.mybatis;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/*分页拦截器，用于实现分页效果*/
@Configuration
public class MPConfig {
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        //创建拦截器的容器外壳
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        //设置拦截器内容
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());

        //返回拦截器的设置
        return interceptor;


    }
}
