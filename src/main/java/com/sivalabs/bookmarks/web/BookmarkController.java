package com.sivalabs.bookmarks.web;

import com.sivalabs.bookmarks.domain.Bookmark;
import com.sivalabs.bookmarks.domain.BookmarkNotFoundException;
import com.sivalabs.bookmarks.domain.BookmarkService;
import jakarta.validation.Valid;
import java.time.Instant;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookmarks")
class BookmarkController {

    private final BookmarkService service;

    BookmarkController(BookmarkService service) {
        this.service = service;
    }

    @GetMapping
    public List<Bookmark> getBookmarks() {
        return service.getBookmarks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bookmark> getBookmarkById(@PathVariable Long id) {
        return service.getBookmarkById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new BookmarkNotFoundException(id));
    }

    @PostMapping
    public ResponseEntity<Bookmark> save(@Valid @RequestBody Bookmark bookmark) {
        bookmark.setId(null);
        bookmark.setCreatedAt(Instant.now());
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(bookmark));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
