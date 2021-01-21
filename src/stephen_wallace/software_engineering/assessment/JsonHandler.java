package stephen_wallace.software_engineering.assessment;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JsonHandler {

    /*
     *   Adds a message to the json file
     */
    public void addToJson(String header, String body) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        try{
            List<JsonNode> jsonNodes = new ArrayList<>();
            List<Output> outputs = Arrays.asList(mapper.readValue(Paths.get("message.json").toFile(), Output[].class));
            for (Output output: outputs){
                String json = mapper.writeValueAsString(output);
                jsonNodes.add(mapper.readTree(json));
            }

            String json = mapper.writeValueAsString(new Output(header, body));
            jsonNodes.add(mapper.readTree(json));

            ArrayNode arrayNode = mapper.createArrayNode();
            arrayNode.addAll(jsonNodes);

            mapper.writer(new DefaultPrettyPrinter()).writeValue(new File("message.json"), arrayNode);
        }catch (IOException e) {
            try{
                String json = mapper.writeValueAsString(new Output(header, body));
                JsonNode jsonNode = mapper.readTree(json);

                ArrayNode arrayNode = mapper.createArrayNode();
                arrayNode.add(jsonNode);

                mapper.writer(new DefaultPrettyPrinter()).writeValue(new File("message.json"), arrayNode);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
