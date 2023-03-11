package configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;

public class YamlParser {

    private final File yamlFile;

    public YamlParser(String yamlFilePath) {
        this.yamlFile = new File(yamlFilePath);
    }

    public Config configInitialize(){
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        try {
            return objectMapper.readValue(yamlFile, Config.class);
        } catch (IOException e) {
            throw new RuntimeException("Error in configInitialize method");
        }
    }

}
