
package apliLinterPlugin.client.Entities.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "external_id",
    "message",
    "violations",
    "violations_count",
    "api_definition"
})
public class ApiResponseEntity {

    @JsonProperty("external_id")
    private String externalId;
    @JsonProperty("message")
    private String message;
    @JsonProperty("violations")
    private List<Violation> violations = null;
    @JsonProperty("violations_count")
    private ViolationsCount violationsCount;
    @JsonProperty("api_definition")
    private String apiDefinition;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("external_id")
    public String getExternalId() {
        return externalId;
    }

    @JsonProperty("external_id")
    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    @JsonProperty("message")
    public void setMessage(String message) {
        this.message = message;
    }

    @JsonProperty("violations")
    public List<Violation> getViolations() {
        return violations;
    }

    @JsonProperty("violations")
    public void setViolations(List<Violation> violations) {
        this.violations = violations;
    }

    @JsonProperty("violations_count")
    public ViolationsCount getViolationsCount() {
        return violationsCount;
    }

    @JsonProperty("violations_count")
    public void setViolationsCount(ViolationsCount violationsCount) {
        this.violationsCount = violationsCount;
    }

    @JsonProperty("api_definition")
    public String getApiDefinition() {
        return apiDefinition;
    }

    @JsonProperty("api_definition")
    public void setApiDefinition(String apiDefinition) {
        this.apiDefinition = apiDefinition;
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
