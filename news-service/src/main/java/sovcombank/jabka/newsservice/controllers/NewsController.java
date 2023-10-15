package sovcombank.jabka.newsservice.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.sovkombank.openapi.api.NewsApi;
import ru.sovkombank.openapi.model.NewsOpenAPI;

import java.util.List;

@RestController
@RequestMapping("/api/news")
public class NewsController implements NewsApi {

    @Override
    @PostMapping
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Void> createNewsPost(NewsOpenAPI newsOpenAPI) {
        return NewsApi.super.createNewsPost(newsOpenAPI);
    }

    @Override
    @DeleteMapping
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Void> deleteNewsById(Long id) {
        return NewsApi.super.deleteNewsById(id);
    }

    @Override
    @PutMapping
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<NewsOpenAPI> editNewsById(NewsOpenAPI newsOpenAPI) {
        return NewsApi.super.editNewsById(newsOpenAPI);
    }

    @Override
    @GetMapping
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<List<NewsOpenAPI>> showAllNewsInfo() {
        return NewsApi.super.showAllNewsInfo();
    }

    @Override
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<NewsOpenAPI> showNewsIdInfo(@Valid @RequestParam("id") Long id) {
        return NewsApi.super.showNewsIdInfo(id);
    }
}
