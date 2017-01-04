package by.bsu.rfe.smsservice.common.dto;

import java.util.List;

/**
 * Created by pluhin on 11/17/16.
 */
public class PageResponseDTO<T> {
    private List<T> items;
    private Long count;

    public PageResponseDTO(List<T> items, Long count) {
        this.items = items;
        this.count = count;
    }

    public List<T> getItems() {
        return items;
    }

    public Long getCount() {
        return count;
    }
}
