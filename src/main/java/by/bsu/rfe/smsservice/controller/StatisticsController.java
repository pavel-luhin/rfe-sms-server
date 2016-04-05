package by.bsu.rfe.smsservice.controller;

import by.bsu.rfe.smsservice.common.dto.StatisticsDTO;
import by.bsu.rfe.smsservice.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by pluhin on 12/27/15.
 */
@Controller
@RequestMapping(value = "/statistics")
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, produces = {"application/json; charset=UTF-8"})
    public List<StatisticsDTO> getFullStatistics() {
        return statisticsService.getFullStatistics();
    }

    @ResponseBody
    @RequestMapping(value = "/page", method = RequestMethod.GET, produces = {"application/json; charset=UTF-8"})
    public List<StatisticsDTO> getStatisticsPage(@RequestParam int skip, @RequestParam int offset,
                                          @RequestParam String sortField, @RequestParam String sortDirection) {
        return statisticsService.getStatisticsPage(skip, offset, sortField, sortDirection);
    }

    @ResponseBody
    @RequestMapping(value = "/count", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Long countStatistics() {
        return statisticsService.count();
    }
}
