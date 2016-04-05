package by.bsu.rfe.smsservice.util;

import org.dozer.Mapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pluhin on 1/3/16.
 */
public class DozerUtil {

    public static <SRC, DEST> List<DEST> mapList(Mapper mapper, List<SRC> sourceList, Class<DEST> destClass) {
        List<DEST> destList = new ArrayList<>(sourceList.size());
        for (SRC sourceElem : sourceList) {
            destList.add(mapper.map(sourceElem, destClass));
        }
        return destList;
    }

}
