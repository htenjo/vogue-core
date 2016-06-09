package co.zero.vogue.resource;

import co.zero.vogue.common.type.ProbabilityType;
import co.zero.vogue.common.type.EventType;
import co.zero.vogue.common.type.SeverityType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by htenjo on 5/29/16.
 */
@RestController
public class UtilsResource {
    @RequestMapping("/utils/reportTypes")
    public EventType[] getReportTypes(){
        return EventType.values();
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
