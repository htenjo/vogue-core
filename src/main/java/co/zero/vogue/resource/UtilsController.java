package co.zero.vogue.resource;

import co.zero.vogue.common.ProbabilityType;
import co.zero.vogue.common.ReportType;
import co.zero.vogue.common.SeverityType;
import co.zero.vogue.model.Greeting;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by htenjo on 5/29/16.
 */
@RestController
public class UtilsController {
    @RequestMapping("/utils/reportTypes")
    public ReportType[] getReportTypes(){
        return ReportType.values();
    }

    @RequestMapping("/utils/probabilityTypes")
    public ProbabilityType[] getProbabilityTypes(){
        return ProbabilityType.values();
    }

    @RequestMapping("/utils/severityTypes")
    public SeverityType[] getSeverityTypes(){
        return SeverityType.values();
    }
}
