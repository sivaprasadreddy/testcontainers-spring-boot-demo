package com.sivalabs.bookmarks.domain;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BookmarkService {

    private final BookmarkRepository repo;

    BookmarkService(BookmarkRepository repo) {
        this.repo = repo;
    }

    public List<Bookmark> getBookmarks() {
        return repo.findAll();
    }

    public Optional<Bookmark> getBookmarkById(Long id) {
        return repo.findById(id);
    }

    public Bookmark save(Bookmark bookmark) {
        return repo.save(bookmark);
    }

    public List<Bookmark> saveAll(List<Bookmark> bookmarks) {
        return repo.saveAll(bookmarks);
    }

    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    public void deleteAll() {
        repo.deleteAllInBatch();
    }
}
