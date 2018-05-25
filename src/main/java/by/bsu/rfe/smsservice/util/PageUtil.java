package by.bsu.rfe.smsservice.util;

import by.bsu.rfe.smsservice.common.dto.page.PageRequestDTO;
import by.bsu.rfe.smsservice.common.pagination.ChunkRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * Helper class provides methods to manage pagination.
 */
public final class PageUtil {

  private PageUtil() {
  }

  /**
   * Creates page to do database query from dto parameters.
   *
   * @param pageRequestDTO request dto to get page properties
   * @return page query
   */
  public static Pageable createPage(PageRequestDTO pageRequestDTO) {
    Sort sort = new Sort(
        new Sort.Order(Sort.Direction.fromString(pageRequestDTO.getSortDirection()),
            pageRequestDTO.getSortField()));
    return new ChunkRequest(pageRequestDTO.getSkip(), pageRequestDTO.getOffset(), sort);
  }
}
