package sovcombank.jabka.newsservice.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.sovkombank.openapi.api.NewsApi;
import ru.sovkombank.openapi.model.NewsOpenAPI;
import sovcombank.jabka.newsservice.services.interfaces.NewsService;

import java.util.List;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsController implements NewsApi {
    private final NewsService newsService;

    @Override
    @PostMapping
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Void> createNewsPost(@RequestBody NewsOpenAPI newsOpenAPI) {
        return newsService.createNewsPost(newsOpenAPI);
    }

    @Override
    @GetMapping
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<List<NewsOpenAPI>> showAllNewsInfo() {
        return newsService.showAllNewsInfo();
    }

    @Override
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Void> deleteNewsById(@Valid @PathVariable Long id) {
        return newsService.deleteNewsById(id);
    }


//    @Override
//    @PutMapping("/{id}")
//    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
//    public ResponseEntity<Void> editNewsById(@PathVariable(name = "id") Long id,
//                                             @Valid @RequestBody NewsOpenAPI newsOpenAPI) {
//        return newsService.editNewsById(id, newsOpenAPI);
//    }

    @Override
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<NewsOpenAPI> showNewsIdInfo(@Valid @RequestParam("id") Long id) {
        return newsService.showNewsIdInfo(id);
    }
}
