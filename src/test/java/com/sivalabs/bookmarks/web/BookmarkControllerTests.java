package com.sivalabs.bookmarks.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.sivalabs.bookmarks.domain.Bookmark;
import java.time.Instant;

import com.sivalabs.bookmarks.domain.BookmarkService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(
    webEnvironment = RANDOM_PORT,
    properties = {"spring.datasource.url=jdbc:tc:postgresql:15-alpine:///dbname"}
)
@AutoConfigureMockMvc
class BookmarkControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookmarkService service;

    @Test
    void shouldFetchBookmarks() throws Exception {
        this.mockMvc
                .perform(get("/api/bookmarks"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetBookmarkById() throws Exception {
        Bookmark bookmark = new Bookmark(null, "New Bookmark", "https://my-new-bookmark.com", Instant.now());
        Bookmark savedBookmark = service.save(bookmark);
        this.mockMvc
                .perform(get("/api/bookmarks/{id}", savedBookmark.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(savedBookmark.getId()), Long.class))
                .andExpect(jsonPath("$.title", equalTo(savedBookmark.getTitle())))
                .andExpect(jsonPath("$.url", equalTo(savedBookmark.getUrl())));
    }

    @Test
    void shouldCreateBookmarkSuccessfully() throws Exception {
        this.mockMvc
                .perform(
                        post("/api/bookmarks")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        """
                                                {
                                                    "title": "SivaLabs Blog",
                                                    "url": "https://sivalabs.in"
                                                }
                                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.title", equalTo("SivaLabs Blog")))
                .andExpect(jsonPath("$.url", equalTo("https://sivalabs.in")));
    }

    @ParameterizedTest
    @ValueSource(strings = {"{ \"title\": \"SivaLabs Blog\" }", "{ \"url\": \"https://sivalabs.in\" }"})
    void shouldFailToCreateBookmarkWhenTitleOrUrlIsNotPresent(String payload) throws Exception {
        this.mockMvc
                .perform(post("/api/bookmarks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldDeleteBookmarkById() throws Exception {
        Bookmark bookmark = new Bookmark(null, "New Bookmark", "https://my-new-bookmark.com", Instant.now());
        Bookmark savedBookmark = service.save(bookmark);
        assertThat(service.getBookmarkById(savedBookmark.getId())).isPresent();
        this.mockMvc.perform(delete("/api/bookmarks/{id}", savedBookmark.getId())).andExpect(status().isNoContent());
        assertThat(service.getBookmarkById(savedBookmark.getId())).isEmpty();
    }
}
