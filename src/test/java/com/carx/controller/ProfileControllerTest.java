package com.carx.controller;

import com.carx.domain.entity.Profile;
import com.carx.service.ProfileService;
import com.despegar.http.client.HttpResponse;
import com.despegar.http.client.PostMethod;
import com.despegar.sparkjava.test.SparkServer;
import com.google.gson.Gson;
import org.junit.ClassRule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;
import spark.servlet.SparkApplication;

import java.time.ZonedDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Created by ZotovES on 08.04.2020
 */
@DisplayName("Тестирование контроллера управления профилями пользователя")
@Transactional(rollbackFor = Exception.class)
class ProfileControllerTest {
    public static class ProfileControllerTestSparkApplication implements SparkApplication {

        @Override
        public void init() {
            UUID uuid = UUID.fromString("ac567cf3-9601-465f-b070-fc6320693fcd");
            var profile = Profile.builder()
                    .uuid(uuid)
                    .money(1)
                    .country("RUS")
                    .createDate(ZonedDateTime.now())
                    .build();
            ProfileService profileService = mock(ProfileService.class);
            when(profileService.createOrUpdateProfile(any())).thenReturn(profile);
            new ProfileController(profileService, new Gson());
        }
    }

    public static class MySparkServer<T> extends SparkServer {
        public MySparkServer(Class sparkApplicationClass, int port) {
            super(sparkApplicationClass, port);
        }

        @Override
        protected void before() throws Throwable {
            super.before();
        }
    }

    @ClassRule
    private static MySparkServer<ProfileControllerTestSparkApplication> testServer = new MySparkServer<>(ProfileControllerTestSparkApplication.class, 4567);

    @Test
    @DisplayName("Тестирование контролера создания профиля")
    void createProfileTest() throws Throwable {
        testServer.before();
        PostMethod post = testServer.post("/profile", "{\n" +
                "    \"uuid\": \"ac567cf3-9601-465f-b070-fc6320693fcd\",\n" +
                "    \"country\":\"RUS\",\n" +
                "    \"money\": 3\n" +
                "}", false);
        post.addHeader("Test-Header", "test");
        HttpResponse httpResponse = testServer.execute(post);
        assertEquals(200, httpResponse.code());
        assertTrue(new String(httpResponse.body()).contains("ac567cf3-9601-465f-b070-fc6320693fcd"));
        assertNotNull(testServer.getApplication());
    }
}
