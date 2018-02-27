package by.bsu.rfe.smsservice.service;

import by.bsu.rfe.smsservice.common.dto.StatisticsDTO;
import by.bsu.rfe.smsservice.common.dto.page.PageRequestDTO;
import by.bsu.rfe.smsservice.common.dto.page.PageResponseDTO;
import by.bsu.rfe.smsservice.common.entity.StatisticsEntity;
import java.util.List;

/**
 * Created by pluhin on 3/20/16.
 */
public interface StatisticsService {

  List<StatisticsDTO> getFullStatistics();

  void saveStatistics(StatisticsEntity statisticsEntity);

  PageResponseDTO getStatisticsPage(PageRequestDTO requestDTO);

  Long count();
}
