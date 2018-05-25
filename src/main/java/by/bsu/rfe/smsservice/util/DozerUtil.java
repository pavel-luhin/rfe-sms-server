package by.bsu.rfe.smsservice.util;

import static java.util.stream.Collectors.toList;

import java.util.List;
import org.dozer.Mapper;

/**
 * Class provides helper methods to map objects with {@link org.dozer.DozerBeanMapper}
 */
public final class DozerUtil {

  private DozerUtil() {
  }

  /**
   * Maps list of objects to list of other objects.
   *
   * @param mapper mapper instance to map
   * @param sourceList source list of objects
   * @param destClass destination class to map to
   * @param <SRC> source objects type
   * @param <DEST> destination objects type
   * @return created list of destination elements
   */
  public static <SRC, DEST> List<DEST> mapList(Mapper mapper, List<SRC> sourceList,
      Class<DEST> destClass) {
    return sourceList
        .stream()
        .map(elem -> mapper.map(elem, destClass))
        .collect(toList());
  }

}
