package pl.devcofee.jeehealth.control;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import lombok.Getter;

@Startup
@Singleton
public class ServerInfo {

    @Getter
    private ZonedDateTime startTime;

    private MemoryUsage heapUsageAtStartTime;
    private MemoryMXBean memoryMxBean;

    @PostConstruct
    public void initialize() {
        this.startTime = ZonedDateTime.now();

        this.memoryMxBean = ManagementFactory.getMemoryMXBean();
        this.heapUsageAtStartTime = this.memoryMxBean.getHeapMemoryUsage();
    }

    public BigDecimal getAvailableMemoryInMb() {
        MemoryUsage heapMemoryUsage = this.memoryMxBean.getHeapMemoryUsage();
        long availableMemory = (heapMemoryUsage.getCommitted() - heapMemoryUsage.getUsed());

        return toMb(availableMemory);
    }

    private BigDecimal toMb(long bytes) {
        return new BigDecimal(bytes).divide(new BigDecimal(1024)).divide(new BigDecimal(1024));
    }

    public BigDecimal getUsedMemoryInMb() {
        MemoryUsage heapMemoryUsage = this.memoryMxBean.getHeapMemoryUsage();
        return toMb(heapMemoryUsage.getUsed());
    }

    public BigDecimal getUsedMemoryAtStartTimeInMb() {
        return toMb(this.heapUsageAtStartTime.getUsed());
    }
}
