package my.bank.freemarker;

import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;

public class Template {

    public static String render(String template_name, Object model_data) throws IOException, TemplateException {
        StringBuilder full_name = new StringBuilder();
        full_name.append(template_name);
        full_name.append(".ftl");
        StringWriter writer = new StringWriter();
        freemarker.template.Template page = FreeMarkerConfiguration.getConfiguration().getTemplate(full_name.toString());
        page.process(model_data, writer);
        return writer.toString();
    }

    public static String render(String template_name) throws IOException, TemplateException {
        return render(template_name, null);
    }

}
