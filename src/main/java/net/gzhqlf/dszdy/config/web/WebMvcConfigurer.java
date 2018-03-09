package net.gzhqlf.dszdy.config.web;

import net.gzhqlf.dszdy.config.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by DESTINY on 2017/10/17.
 */

@Configuration
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {

//    @Override
//    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        //converters.add(fastJsonHttpMessageConverterEx());
//        super.configureMessageConverters(converters);
//    }
//
//    @Bean
//    public FastJsonHttpMessageConverterEx fastJsonHttpMessageConverterEx() {
//        return new FastJsonHttpMessageConverterEx();
//    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/m/**")
                .excludePathPatterns("/m/")
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin/")
                //.excludePathPatterns("/m/login.*")
                //.excludePathPatterns("/m/register.*")
                //.excludePathPatterns("/m/findPassword.*")
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/token/getTokenCode")
                .excludePathPatterns("/api/user/getMobileVerifyCode")
                .excludePathPatterns("/api/user/register")
                .excludePathPatterns("/api/weChat/**");
                //.excludePathPatterns("/api/user/login")
                //.excludePathPatterns("/api/user/modifyPasswordByMobileCode");

        super.addInterceptors(registry);

    }
}
