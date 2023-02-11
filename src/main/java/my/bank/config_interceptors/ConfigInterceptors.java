package my.bank.config_interceptors;
import my.bank.controllers.interceptor.HomeInterseptorHandler;
import my.bank.users.clients.interceptor.ClientInterceptorHandler;
import my.bank.users.employees.interceptor.EmployeeInterseptorHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class ConfigInterceptors extends WebMvcConfigurationSupport {

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HomeInterseptorHandler()).addPathPatterns("/");
        registry.addInterceptor(new EmployeeInterseptorHandler()).addPathPatterns("/employee");
        registry.addInterceptor(new EmployeeInterseptorHandler()).addPathPatterns("/employee/**");
        registry.addInterceptor(new ClientInterceptorHandler()).addPathPatterns("/client");
        registry.addInterceptor(new ClientInterceptorHandler()).addPathPatterns("/client/**");
    }
}
