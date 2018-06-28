package by.bsu.rfe.smsservice.common.dto.page;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PageResponseDTO<T> {

    private List<T> items;
    private Long count;
}
