package sovcombank.jabka.newsservice.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.sovkombank.openapi.api.NewsApi;
import ru.sovkombank.openapi.model.NewsOpenAPI;
import sovcombank.jabka.newsservice.NewsRepository;
import sovcombank.jabka.newsservice.mappers.NewsMapper;
import sovcombank.jabka.newsservice.models.News;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsController implements NewsApi {
    private final NewsMapper newsMapper;
    private final NewsRepository newsRepository;

    @Override
    @PostMapping
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Void> createNewsPost(NewsOpenAPI newsOpenAPI) {
        News news = newsMapper.toNews(newsOpenAPI);
        if (Objects.nonNull(news)) {
            newsRepository.save(news);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .build();
        }
        return ResponseEntity
                .badRequest()
                .build();

    }

    @Override
    @GetMapping
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<List<NewsOpenAPI>> showAllNewsInfo() {
        List<News> news = newsRepository.findAll();
        if (!news.isEmpty()) {
            return ResponseEntity
                    .ok()
                    .body(
                            news
                                    .stream()
                                    .map(newsMapper::toOpenApi)
                                    .collect(Collectors.toList())
                    );
        }
        return ResponseEntity
                .notFound()
                .build();
    }

    @Override
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Void> deleteNewsById(@Valid @PathVariable Long id) {
        Optional<News> newsOpt = newsRepository.findById(id);
        if (newsOpt.isEmpty()) {
            return ResponseEntity
                    .notFound()
                    .build();
        } else {
            newsRepository.deleteById(id);
            return ResponseEntity
                    .ok()
                    .build();
        }
    }


    @Override
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Void> editNewsById(@PathVariable(name = "id") Long id,
                                             @Valid @RequestBody NewsOpenAPI newsOpenAPI) {
        News news = newsMapper.toNews(newsOpenAPI);
        Optional<News> newsOpt = newsRepository.findById(id);
        if (newsOpt.isEmpty()) {
            return ResponseEntity
                    .notFound()
                    .build();
        } else {
            newsRepository.save(news);
            return ResponseEntity
                    .ok()
                    .build();
        }
    }

    @Override
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<NewsOpenAPI> showNewsIdInfo(@Valid @RequestParam("id") Long id) {
        Optional<News> newsOpt = newsRepository.findById(id);
        if (newsOpt.isEmpty()) {
            return ResponseEntity
                    .notFound()
                    .build();
        } else {
            News news = newsOpt.get();
            return ResponseEntity
                    .ok()
                    .body(
                            newsMapper.toOpenApi(news)
                    );
        }
    }
}
