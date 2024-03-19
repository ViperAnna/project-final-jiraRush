package com.javarush.jira.profile.internal.web;

import com.javarush.jira.AbstractControllerTest;
import com.javarush.jira.common.util.JsonUtil;
import com.javarush.jira.profile.internal.ProfileRepository;
import com.javarush.jira.profile.internal.model.Profile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import static com.javarush.jira.login.internal.web.UserTestData.*;
import static com.javarush.jira.profile.internal.web.ProfileRestController.REST_URL;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class ProfileRestControllerTest extends AbstractControllerTest {

    @Autowired
    private ProfileRepository profileRepository;

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAuthOfUsersProfile() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void getUnAuthOfUsersProfile() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isUnauthorized())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getNewUserProfile() throws Exception {
        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(ProfileTestData.getNewTo())))
                .andExpect(status().isNoContent());

        Profile profileCurrent = profileRepository.getExisted(USER_ID);
        Profile profileExpected = ProfileTestData.getNew(USER_ID);

        ProfileTestData.PROFILE_MATCHER.assertMatch(profileCurrent, profileExpected);
    }


    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateTest() throws Exception {
        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(ProfileTestData.getUpdatedTo())))
                .andExpect(status().isNoContent());

        Profile profileCurrent = profileRepository.getExisted(USER_ID);
        Profile profileExpected = ProfileTestData.getUpdated(USER_ID);

        ProfileTestData.PROFILE_MATCHER.assertMatch(profileCurrent, profileExpected);
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void testInvalidTo() throws Exception {
        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(ProfileTestData.getInvalidTo())))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void testGetWithUnknownNotificationTo() throws Exception {
        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(ProfileTestData.getWithUnknownNotificationTo())))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void testGetWithUnknownContactTo() throws Exception {
        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(ProfileTestData.getWithUnknownContactTo())))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void testGetWithContactHtmlUnsafeTo() throws Exception {
        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(ProfileTestData.getWithContactHtmlUnsafeTo())))
                .andExpect(status().is4xxClientError());
    }
}