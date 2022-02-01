package com.example.jobsapi.util;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/externalApi")
public class ExternalJobsApi {

    @GetMapping("/search")
    public List<PositionSearchResult> search(@RequestParam String keyword, @RequestParam String location) {
        List<PositionSearchResult> positions = new ArrayList<>();

        PositionSearchResult positionOne = new PositionSearchResult();
        positionOne.setJobTitle(keyword);
        positionOne.setLocation(location);
        positionOne.setURL("{external job url one}");
        positions.add(positionOne);

        PositionSearchResult positionTwo = new PositionSearchResult();
        positionTwo.setJobTitle(keyword);
        positionTwo.setLocation(location);
        positionTwo.setURL("{external job url two}");
        positions.add(positionTwo);

        return positions;
    }
}
