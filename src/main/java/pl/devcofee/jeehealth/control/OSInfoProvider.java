package pl.devcofee.jeehealth.control;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import javax.json.Json;
import javax.json.JsonObject;

public class OSInfoProvider {

    public static JsonObject getOSInfo() {
        OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();

        return Json.createObjectBuilder()
                .add("System Load Average", osBean.getSystemLoadAverage())
                .add("Available Processors", osBean.getAvailableProcessors())
                .add("Architecture", osBean.getArch())
                .add("Name", osBean.getName())
                .add("Version", osBean.getVersion())
                .build();

    }
}
