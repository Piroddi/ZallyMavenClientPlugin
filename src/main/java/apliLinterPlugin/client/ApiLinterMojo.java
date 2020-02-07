package apliLinterPlugin.client;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.DocumentException;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import apliLinterPlugin.client.Entities.Request.ApiRequestEntity;
import apliLinterPlugin.client.Entities.Response.ApiResponseEntity;
import org.apache.maven.project.MavenProject;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Goal is to invokes the Api Lint Server, generate PDF Report from response and fail build is the api failures is above the provided threshold.
 */
@Mojo(name = "ApiLinter", defaultPhase = LifecyclePhase.VERIFY)
public class ApiLinterMojo extends AbstractMojo
{
    /**
     * Location of the  pdf and json report file.
     */
    @Parameter( defaultValue = "target/apilinter/", property = "outputDir")
    private File outputDirectory;

    /**
     * Location of the  import yaml(swagger) file.
     */
    @Parameter( property = "swagger-yaml", required = true )
    private File inputYamlFile;

    /**
     * Maximum number of MUST violations to fail build.
     */
    @Parameter( defaultValue = "10", property = "maxNoOfMustViolations", required = true )
    private int maxNoOfMustViolations;


    /**
     * Url of Api Linter Server.
     */
    @Parameter( defaultValue = "http://localhost:8080", property = "apiLinterServerUrl")
    private URL ApiLinterServerUrl;

    @Parameter(defaultValue = "${project}", readonly = true, required = true)
    private MavenProject project;

    @Inject
    private ApiPdfReport apiPdfReport;

    @Inject
    private ApiLinterClient apiLinterClient;

    @Override
    public void execute() throws MojoExecutionException{

        ApiRequestEntity apiRequestEntity = new ApiRequestEntity();

        try {
            String swagger_doc = new String(Files.readAllBytes(Paths.get(inputYamlFile.getPath())));
            apiRequestEntity.setApiDefinitionString(swagger_doc);
        } catch (IOException ioe) {
            throw new MojoExecutionException("Unable to load input yaml file. Details: " + ioe.getMessage());
        }

        Response apiLinterServerResponse = apiLinterClient.invokeApiServer(apiRequestEntity,ApiLinterServerUrl);

        int responseStatus = apiLinterServerResponse.getStatus();

        if( responseStatus == 200){
            apiLinterReportGeneration(apiLinterServerResponse);
        }else if (responseStatus == 400){
            getLog().warn("Invalid Swagger doc, Please verify correct swagger doc is provided to plugin");
        }else if (responseStatus == 500){
            getLog().warn("Cant access Linter Server. Error Details: " + apiLinterServerResponse.getEntity().toString());
        }
    }

    private void apiLinterReportGeneration(Response apiLinterServerResponse) throws MojoExecutionException {
        ApiResponseEntity apiResponseEntity = apiLinterServerResponse.readEntity(ApiResponseEntity.class);

        //Create Json File with Violations
        try {
            File file = new File(outputDirectory +"/ApiViolationsReport.json");
            file.getParentFile().mkdirs();
            ObjectMapper mapper = new ObjectMapper();
            FileWriter writer = new FileWriter(file, true);
            String json = mapper.writeValueAsString(apiResponseEntity);
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Create Simple pdf reports of violations
        try {
            apiPdfReport.generatePDF(apiResponseEntity,outputDirectory, project.getName());
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }

        if (apiResponseEntity.getViolationsCount().getMust() > maxNoOfMustViolations){
            throw new MojoExecutionException("To many API Violations, please see API report");
        }else if(apiResponseEntity.getViolationsCount().getMust() > 1) {
            getLog().warn("There are some API hygiene issues. Please see report");
        }
    }
}
