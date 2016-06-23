package co.zero.vogue.resource;

import co.zero.common.files.ExcelUtils;
import co.zero.vogue.common.type.ProbabilityType;
import co.zero.vogue.common.type.EventType;
import co.zero.vogue.common.type.SeverityType;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.util.IOUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by htenjo on 5/29/16.
 */
@CrossOrigin
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

    @RequestMapping(value = "/utils/cleanEventFile", method = RequestMethod.POST)
    public ResponseEntity<ByteArrayResource> getEventFileClean(
            @RequestParam(value="eventFile") MultipartFile file) throws IOException, InvalidFormatException {
        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        ExcelUtils.removeAllMergedCellsFromWorkbook(workbook);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        workbook.write(baos);
        ByteArrayResource byteArrayResource = new ByteArrayResource(baos.toByteArray());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(byteArrayResource.contentLength())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(byteArrayResource);
    }
}
