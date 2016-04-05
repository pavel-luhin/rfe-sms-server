package by.bsu.rfe.smsservice.service.impl;

import by.bsu.rfe.smsservice.common.dto.StatisticsDTO;
import by.bsu.rfe.smsservice.common.entity.StatisticsEntity;
import by.bsu.rfe.smsservice.common.pagination.ChunkRequest;
import by.bsu.rfe.smsservice.repository.StatisticsRepository;
import by.bsu.rfe.smsservice.service.StatisticsService;
import by.bsu.rfe.smsservice.util.DozerUtil;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.List;

/**
 * Created by pluhin on 3/20/16.
 */
@Service
public class StatisticsServiceImpl implements StatisticsService {
    @Autowired
    private StatisticsRepository statisticsRepository;
    @Autowired
    private Mapper mapper;

    @Override
    public List<StatisticsDTO> getFullStatistics() {
        List<StatisticsEntity> statisticsEntities = statisticsRepository.findAll();
        return DozerUtil.mapList(mapper, statisticsEntities, StatisticsDTO.class);
    }

    public List<StatisticsDTO> getStatisticsPage(int skip, int offset, String sortField, String sortDirection) {
        Sort sort = new Sort(new Sort.Order(Sort.Direction.fromString(sortDirection), sortField));
        Pageable pageable = new ChunkRequest(skip, offset, sort);
        Page<StatisticsEntity> entities = statisticsRepository.findAll(pageable);
        return DozerUtil.mapList(mapper, entities.getContent(), StatisticsDTO.class);
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
