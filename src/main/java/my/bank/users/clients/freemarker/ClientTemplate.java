package my.bank.users.clients.freemarker;

import java.io.StringWriter;

public class ClientTemplate {

    public static String render(String template_name, Object model_data){
        try {
            StringBuilder full_name = new StringBuilder();
            full_name.append(template_name);
            full_name.append(".ftl");
            StringWriter writer = new StringWriter();
            freemarker.template.Template page = EmployeeFreeMarkerConfiguration.getConfiguration().getTemplate(full_name.toString());
            page.process(model_data, writer);
            return writer.toString();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    public static String render(String template_name){
        return render(template_name, null);
    }

}
