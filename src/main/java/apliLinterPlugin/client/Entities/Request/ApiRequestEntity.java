package apliLinterPlugin.client.Entities.Request;


import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "api_definition",
        "api_definition_string",
        "api_definition_url",
        "ignore_rules"
})
public class ApiRequestEntity  {

    @JsonProperty("api_definition")
    private ApiDefinition apiDefinition;
    @JsonProperty("api_definition_string")
    private String apiDefinitionString;
    @JsonProperty("api_definition_url")
    private String apiDefinitionUrl;
    @JsonProperty("ignore_rules")
    private List<String> ignoreRules = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("api_definition")
    public ApiDefinition getApiDefinition() {
        return apiDefinition;
    }

    @JsonProperty("api_definition")
    public void setApiDefinition(ApiDefinition apiDefinition) {
        this.apiDefinition = apiDefinition;
    }

    @JsonProperty("api_definition_string")
    public String getApiDefinitionString() {
        return apiDefinitionString;
    }

    @JsonProperty("api_definition_string")
    public void setApiDefinitionString(String apiDefinitionString) {
        this.apiDefinitionString = apiDefinitionString;
    }

    @JsonProperty("api_definition_url")
    public String getApiDefinitionUrl() {
        return apiDefinitionUrl;
    }

    @JsonProperty("api_definition_url")
    public void setApiDefinitionUrl(String apiDefinitionUrl) {
        this.apiDefinitionUrl = apiDefinitionUrl;
    }

    @JsonProperty("ignore_rules")
    public List<String> getIgnoreRules() {
        return ignoreRules;
    }

    @JsonProperty("ignore_rules")
    public void setIgnoreRules(List<String> ignoreRules) {
        this.ignoreRules = ignoreRules;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}
