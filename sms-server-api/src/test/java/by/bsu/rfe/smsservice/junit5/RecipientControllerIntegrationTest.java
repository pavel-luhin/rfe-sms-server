package by.bsu.rfe.smsservice.junit5;

import static by.bsu.rfe.smsservice.TestConstants.TEST_GROUP_ID;
import static by.bsu.rfe.smsservice.TestConstants.TEST_GROUP_NAME;
import static by.bsu.rfe.smsservice.TestConstants.USERNAME;
import static by.bsu.rfe.smsservice.common.constants.AuthorityConstants.ROLE_APPLICATION;
import static by.bsu.rfe.smsservice.common.constants.AuthorityConstants.ROLE_USER;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import by.bsu.rfe.smsservice.common.dto.GroupDTO;
import by.bsu.rfe.smsservice.common.dto.PersonDTO;
import by.bsu.rfe.smsservice.common.entity.GroupEntity;
import by.bsu.rfe.smsservice.common.entity.PersonEntity;
import by.bsu.rfe.smsservice.repository.GroupRepository;
import by.bsu.rfe.smsservice.repository.PersonRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;

@DisplayName("Testing recipient controller")
public class RecipientControllerIntegrationTest extends BaseIntegrationTest {

  @Autowired
  private GroupRepository groupRepository;

  @Autowired
  private PersonRepository personRepository;

  @Test
  @DisplayName("Create group forbidden for application")
  @WithMockUser(username = USERNAME, authorities = ROLE_APPLICATION)
  void createGroupForbiddenForApplication() throws Exception {
    GroupDTO groupDTO = new GroupDTO();

    mockMvc.perform(post("/rest/recipient/group")
        .content(objectMapper.writeValueAsBytes(groupDTO))
        .contentType(APPLICATION_JSON_UTF8_VALUE))
        .andExpect(status().isForbidden());
  }

  @Test
  @DisplayName("Create group forbidden for anonymous")
  public void createGroupForbiddenForAnonymous() throws Exception {
    GroupDTO groupDTO = new GroupDTO();

    mockMvc.perform(post("/rest/recipient/group")
        .content(objectMapper.writeValueAsBytes(groupDTO))
        .contentType(APPLICATION_JSON_UTF8_VALUE))
        .andExpect(status().isUnauthorized());
  }

  @Test
  @DisplayName("Create group success for user")
  @WithMockUser(username = USERNAME, authorities = ROLE_USER)
  public void createGroupSuccess() throws Exception {
    String groupName = "erwgerger";
    GroupDTO groupDTO = new GroupDTO();
    groupDTO.setName(groupName);

    mockMvc.perform(post("/rest/recipient/group")
        .content(objectMapper.writeValueAsBytes(groupDTO))
        .contentType(APPLICATION_JSON_UTF8_VALUE))
        .andExpect(status().isNoContent());

    GroupEntity groupEntity = groupRepository.findAll().stream()
        .filter(groupEntity1 -> groupEntity1.getName().equals(groupName))
        .findFirst()
        .get();
  }

  @Test
  @DisplayName("Get group success for user")
  @WithMockUser(username = USERNAME, authorities = ROLE_USER)
  public void getGroupSuccess() throws Exception {
    mockMvc.perform(get("/rest/recipient/group/{id}", TEST_GROUP_ID))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value(TEST_GROUP_NAME))
        .andExpect(jsonPath("$.id").value(TEST_GROUP_ID));
  }

  @Test
  @DisplayName("Get group forbidden for application")
  @WithMockUser(username = USERNAME, authorities = ROLE_APPLICATION)
  public void getGroupForbidden() throws Exception {
    mockMvc.perform(get("/rest/recipient/group"))
        .andExpect(status().isForbidden());
  }

  @Test
  @DisplayName("Get group forbidden for anonymous")
  public void getGroupForbiddenForAnonymous() throws Exception {
    mockMvc.perform(get("/rest/recipient/group"))
        .andExpect(status().isUnauthorized());
  }

  @Test
  @WithMockUser(username = USERNAME, authorities = ROLE_USER)
  @DisplayName("Remove group success for user")
  public void removeGroupSuccess() throws Exception {
    mockMvc.perform(delete("/rest/recipient/group/{id}", TEST_GROUP_ID))
        .andExpect(status().isNoContent());
  }

  @Test
  @DisplayName("Remove group forbidden for application")
  @WithMockUser(username = USERNAME, authorities = ROLE_APPLICATION)
  public void removeGroupForbidden() throws Exception {
    mockMvc.perform(delete("/rest/recipient/group/{id}", TEST_GROUP_ID))
        .andExpect(status().isForbidden());
  }

  @Test
  @DisplayName("Remove group forbidden for anonymous")
  public void removeGroupUnauthorized() throws Exception {
    mockMvc.perform(delete("/rest/recipient/group/{id}", TEST_GROUP_ID))
        .andExpect(status().isUnauthorized());
  }

  @Test
  @DisplayName("Add person success for user")
  @WithMockUser(username = USERNAME, authorities = ROLE_USER)
  public void addPersonsSuccess() throws Exception {
    String firstName = "erklgkwler";
    String lastName = "lkernttre";
    String phoneNumber = "+123456789012";
    String email = "email@example.com";

    PersonDTO personDTO = new PersonDTO();
    personDTO.setFirstName(firstName);
    personDTO.setLastName(lastName);
    personDTO.setPhoneNumber(phoneNumber);
    personDTO.setEmail(email);

    mockMvc.perform(post("/rest/recipient/persons")
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .content(objectMapper.writeValueAsBytes(personDTO)))
        .andExpect(status().isNoContent());

    PersonEntity entity = personRepository.findAll()
        .stream()
        .filter(personEntity -> personEntity.getFirstName().equals(firstName))
        .filter(personEntity -> personEntity.getLastName().equals(lastName))
        .findFirst()
        .get();

    assertAll(
        () -> assertEquals(firstName, entity.getFirstName()),
        () -> assertEquals(lastName, entity.getLastName()),
        () -> assertEquals(phoneNumber, entity.getPhoneNumber()),
        () -> assertEquals(email, entity.getEmail())
    );
  }

  @Test
  @DisplayName("Add person forbidden for application")
  @WithMockUser(username = USERNAME, authorities = ROLE_APPLICATION)
  public void addPersonsForbidden() throws Exception {
    String firstName = "erklgkwler";
    String lastName = "lkernttre";
    String phoneNumber = "+123456789012";
    String email = "email@example.com";

    PersonDTO personDTO = new PersonDTO();
    personDTO.setFirstName(firstName);
    personDTO.setLastName(lastName);
    personDTO.setPhoneNumber(phoneNumber);
    personDTO.setEmail(email);

    mockMvc.perform(post("/rest/recipient/persons")
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .content(objectMapper.writeValueAsBytes(personDTO)))
        .andExpect(status().isForbidden());
  }

  @Test
  @DisplayName("Add person unauthorized for anonymoius")
  public void addPersonsUnauthorized() throws Exception {
    String firstName = "erklgkwler";
    String lastName = "lkernttre";
    String phoneNumber = "+123456789012";
    String email = "email@example.com";

    PersonDTO personDTO = new PersonDTO();
    personDTO.setFirstName(firstName);
    personDTO.setLastName(lastName);
    personDTO.setPhoneNumber(phoneNumber);
    personDTO.setEmail(email);

    mockMvc.perform(post("/rest/recipient/persons")
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .content(objectMapper.writeValueAsBytes(personDTO)))
        .andExpect(status().isUnauthorized());
  }
}
