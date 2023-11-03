package com.sivalabs.bookmarks.web;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import com.sivalabs.bookmarks.ContainersConfig;
import com.sivalabs.bookmarks.domain.Bookmark;
import java.time.Instant;

import com.sivalabs.bookmarks.domain.BookmarkService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@Import(ContainersConfig.class)
class BookmarkControllerTests {

    @LocalServerPort
    int port;

    @Autowired
    private BookmarkService service;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void shouldFetchBookmarks() throws Exception {
        given()
        .when()
            .get("/api/bookmarks")
        .then()
            .statusCode(200);
    }

    @Test
    void shouldGetBookmarkById() {
        Bookmark bookmark = new Bookmark(null, "New Bookmark", "https://my-new-bookmark.com", Instant.now());
        Bookmark savedBookmark = service.save(bookmark);
        given()
        .when()
            .get("/api/bookmarks/{id}", savedBookmark.getId())
        .then()
            .statusCode(200)
            .body("id", equalTo(savedBookmark.getId().intValue()))
            .body("title", equalTo(savedBookmark.getTitle()))
            .body("url", equalTo(savedBookmark.getUrl()));
    }

    @Test
    void shouldCreateBookmarkSuccessfully() {
        given()
            .contentType(ContentType.JSON)
            .body("""
                    {
                        "title": "SivaLabs Blog",
                        "url": "https://sivalabs.in"
                    }
                    """)
        .when()
            .post("/api/bookmarks")
        .then()
            .statusCode(201)
            .body("id", notNullValue())
            .body("title", equalTo("SivaLabs Blog"))
            .body("url", equalTo("https://sivalabs.in"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"{ \"title\": \"SivaLabs Blog\" }", "{ \"url\": \"https://sivalabs.in\" }"})
    void shouldFailToCreateBookmarkWhenTitleOrUrlIsNotPresent(String payload) {
        given()
            .contentType(ContentType.JSON)
            .body(payload)
        .when()
            .post("/api/bookmarks")
        .then()
            .statusCode(400);
    }

    @Test
    void shouldDeleteBookmarkById() throws Exception {
        Bookmark bookmark = new Bookmark(null, "New Bookmark", "https://my-new-bookmark.com", Instant.now());
        Bookmark savedBookmark = service.save(bookmark);
        assertThat(service.getBookmarkById(savedBookmark.getId())).isPresent();

        given().when().delete("/api/bookmarks/{id}", savedBookmark.getId()).then().statusCode(204);

        assertThat(service.getBookmarkById(savedBookmark.getId())).isEmpty();
    }
}
