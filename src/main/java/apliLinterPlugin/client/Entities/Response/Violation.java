
package apliLinterPlugin.client.Entities.Response;

import java.util.HashMap;
import java.util.Iterator;
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
    "title",
    "description",
    "violation_type",
    "rule_link",
    "paths",
    "pointer",
    "start_line",
    "end_line"
})
public class Violation {

    @JsonProperty("title")
    private String title;
    @JsonProperty("description")
    private String description;
    @JsonProperty("violation_type")
    private String violationType;
    @JsonProperty("rule_link")
    private String ruleLink;
    @JsonProperty("paths")
    private List<String> paths = null;
    @JsonProperty("pointer")
    private String pointer;
    @JsonProperty("start_line")
    private Object startLine;
    @JsonProperty("end_line")
    private Object endLine;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("violation_type")
    public String getViolationType() {
        return violationType;
    }

    @JsonProperty("violation_type")
    public void setViolationType(String violationType) {
        this.violationType = violationType;
    }

    @JsonProperty("rule_link")
    public String getRuleLink() {
        return ruleLink;
    }

    @JsonProperty("rule_link")
    public void setRuleLink(String ruleLink) {
        this.ruleLink = ruleLink;
    }

    @JsonProperty("paths")
    public String getPaths() {

        String separator = ",";
        StringBuilder sb = new StringBuilder();

        Iterator<String> iter = this.paths.iterator();
        while (iter.hasNext()) {
            String p = iter.next();
            sb.append(p);

            if (iter.hasNext())
                sb.append(separator);
        }

        return sb.toString();
    }

    @JsonProperty("paths")
    public void setPaths(List<String> paths) {
        this.paths = paths;
    }

    @JsonProperty("pointer")
    public String getPointer() {
        return pointer;
    }

    @JsonProperty("pointer")
    public void setPointer(String pointer) {
        this.pointer = pointer;
    }

    @JsonProperty("start_line")
    public Object getStartLine() {
        return startLine;
    }

    @JsonProperty("start_line")
    public void setStartLine(Object startLine) {
        this.startLine = startLine;
    }

    @JsonProperty("end_line")
    public Object getEndLine() {
        return endLine;
    }

    @JsonProperty("end_line")
    public void setEndLine(Object endLine) {
        this.endLine = endLine;
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
