package co.zero.vogue.resource;

import co.zero.vogue.common.ProbabilityType;
import co.zero.vogue.common.IncidentType;
import co.zero.vogue.common.SeverityType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by htenjo on 5/29/16.
 */
@RestController
public class UtilsController {
    @RequestMapping("/utils/reportTypes")
    public IncidentType[] getReportTypes(){
        return IncidentType.values();
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
