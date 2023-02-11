package my.bank.users.clients.freemarker;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;

import java.io.File;
import java.io.IOException;

public class EmployeeFreeMarkerConfiguration {
    private static Configuration configuration = null;

    public static Configuration getConfiguration() throws IOException {
        if(configuration == null){
            configuration = new Configuration(Configuration.VERSION_2_3_31);
            String template_path = System.getenv("JAVA_RESOURCES_E") + "/web_app_test/Templates/client/";
            File template_file = new File(template_path);
            configuration.setDirectoryForTemplateLoading(template_file);
            configuration.setDefaultEncoding("UTF-8");
            configuration.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
            configuration.setLogTemplateExceptions(false);
            configuration.setWrapUncheckedExceptions(true);
            configuration.setFallbackOnNullLoopVariable(false);
        }
        return configuration;
    }

}
