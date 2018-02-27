package by.bsu.rfe.smsservice.service.impl;

import by.bsu.rfe.smsservice.common.dto.StatisticsDTO;
import by.bsu.rfe.smsservice.common.dto.page.PageRequestDTO;
import by.bsu.rfe.smsservice.common.dto.page.PageResponseDTO;
import by.bsu.rfe.smsservice.common.entity.StatisticsEntity;
import by.bsu.rfe.smsservice.repository.StatisticsRepository;
import by.bsu.rfe.smsservice.service.StatisticsService;
import by.bsu.rfe.smsservice.util.DozerUtil;
import by.bsu.rfe.smsservice.util.PageUtil;
import java.util.List;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Created by pluhin on 3/20/16.
 */
@Service
public class StatisticsServiceImpl implements StatisticsService {

  private StatisticsRepository statisticsRepository;
  private Mapper mapper;

  @Autowired
  public StatisticsServiceImpl(
      StatisticsRepository statisticsRepository, Mapper mapper) {
    this.statisticsRepository = statisticsRepository;
    this.mapper = mapper;
  }

  @Override
  public List<StatisticsDTO> getFullStatistics() {
    List<StatisticsEntity> statisticsEntities = statisticsRepository.findAll();
    return DozerUtil.mapList(mapper, statisticsEntities, StatisticsDTO.class);
  }

  public PageResponseDTO getStatisticsPage(PageRequestDTO requestDTO) {
    Pageable pageable = PageUtil.createPage(requestDTO);

    Page<StatisticsEntity> entities = statisticsRepository.findAll(pageable);
    return new PageResponseDTO<>(
        DozerUtil.mapList(mapper, entities.getContent(), StatisticsDTO.class), count());
  }

  @Override
  public Long count() {
    return statisticsRepository.count();
  }

  @Override
  public void saveStatistics(StatisticsEntity statisticsEntity) {
    statisticsRepository.saveAndFlush(statisticsEntity);
  }
}
