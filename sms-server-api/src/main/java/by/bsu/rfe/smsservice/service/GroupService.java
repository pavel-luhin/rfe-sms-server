package by.bsu.rfe.smsservice.service;

import by.bsu.rfe.smsservice.common.dto.GroupDTO;
import by.bsu.rfe.smsservice.common.dto.page.PageRequestDTO;
import by.bsu.rfe.smsservice.common.dto.page.PageResponseDTO;
import by.bsu.rfe.smsservice.common.entity.GroupEntity;
import java.util.List;

public interface GroupService {

  void addGroup(GroupDTO groupDTO);

  void removeGroup(Integer groupId);

  GroupDTO getGroup(Integer groupId);

  GroupEntity getGroupByName(String groupName);

  PageResponseDTO<GroupDTO> getGroups(PageRequestDTO pageRequestDTO, String query);

  GroupEntity createGroupFromNumbers(List<String> numbers);

  List<GroupEntity> findTemporaryGroups();
}
