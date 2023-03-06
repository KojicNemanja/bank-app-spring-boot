package my.bank.database.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import my.bank.database.models.Database;
import java.nio.file.Files;
import java.nio.file.Path;


public class DBConfigFile {
    private static Database database = null;

    public static Database readData(){
        if(database == null){
            try {
                ObjectMapper mapper = new ObjectMapper();
                Path dbconfig_path = Path.of(System.getenv("JAVA_RESOURCES") + "/bank_app/Database/config.json");
                String json_data = Files.readString(dbconfig_path);
                database =  mapper.readValue(json_data, Database.class);
            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        }
        return database;
    }
}
