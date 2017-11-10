package pl.devcofee.jeehealth.boundary;

import java.time.format.DateTimeFormatter;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import pl.devcofee.jeehealth.control.ConfigurationPropertiesProvider;
import pl.devcofee.jeehealth.control.JNDILister;
import pl.devcofee.jeehealth.control.OSInfoProvider;
import pl.devcofee.jeehealth.control.ServerInfo;

@Path("health")
@Produces(MediaType.APPLICATION_JSON)
public class HealthResource {

    @EJB
    private ServerInfo serverInfo;

    @GET
	@Path("ping")
	public Response ping() {
        return Response.ok().build();
	}

	@GET
    @Path("osInfo")
	public JsonObject osInfo() {
        return OSInfoProvider.getOSInfo();
    }

    @GET
    @Path("systemProperties")
    public JsonObject systemProperties() {
        return ConfigurationPropertiesProvider.getSystemProperties();
    }

    @GET
    @Path("environmentVariables")
    public JsonObject environmentVariables() {
        return ConfigurationPropertiesProvider.getEnvironmentVariables();
    }

    @GET
    @Path("jndi/{namespace}")
    public JsonObject jndi(@PathParam("namespace") String namespace) {
        return JNDILister.list(namespace);
    }

    @GET
    @Path("bootTime")
    public JsonObject bootTime() {
        return Json.createObjectBuilder()
                .add("Boot Time", serverInfo.getStartTime().format(DateTimeFormatter.ISO_TIME))
                .build();
    }

    @GET
    @Path("memoryInfo")
    public JsonObject memoryInfo() {
        return Json.createObjectBuilder()
                .add("Used memory [mb]", serverInfo.getUsedMemoryInMb())
                .add("Available memory [mb]", serverInfo.getAvailableMemoryInMb())
                .add("Boot used memory [mb]", serverInfo.getUsedMemoryAtStartTimeInMb())
                .build();
    }

	@GET
    @Path("help")
    public JsonObject help() {
        return Json.createObjectBuilder()
                .add("ping", "Simple ping, always return status 200 (OK).")
                .add("help", "Prints help info - all available health check methods.")
                .add("osInfo", "Shows basic information about Operating System.")
                .add("systemProperties", "Prints all system properties.")
                .add("environmentVariables", "Prints all environment variables.")
                .add("jndi/{namespace}", "Prints all JNDI entries for selected {namespace}")
                .add("bootTime", "Shows time spent on server's startup.")
                .add("memoryInfo", "Shows current memory state.")
                .build();
    }
}