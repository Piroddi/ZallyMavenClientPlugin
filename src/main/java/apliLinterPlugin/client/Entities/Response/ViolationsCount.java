
package apliLinterPlugin.client.Entities.Response;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "must",
    "should",
    "may",
    "hint"
})
public class ViolationsCount {

    @JsonProperty("must")
    private Integer must;
    @JsonProperty("should")
    private Integer should;
    @JsonProperty("may")
    private Integer may;
    @JsonProperty("hint")
    private Integer hint;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("must")
    public Integer getMust() {
        return must;
    }

    @JsonProperty("must")
    public void setMust(Integer must) {
        this.must = must;
    }

    @JsonProperty("should")
    public Integer getShould() {
        return should;
    }

    @JsonProperty("should")
    public void setShould(Integer should) {
        this.should = should;
    }

    @JsonProperty("may")
    public Integer getMay() {
        return may;
    }

    @JsonProperty("may")
    public void setMay(Integer may) {
        this.may = may;
    }

    @JsonProperty("hint")
    public Integer getHint() {
        return hint;
    }

    @JsonProperty("hint")
    public void setHint(Integer hint) {
        this.hint = hint;
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
