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
        if (news != null) {
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
    @DeleteMapping
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Void> deleteNewsById(@Valid Long id) {
        if (newsRepository.findById(id).isEmpty()) {
            return ResponseEntity
                    .notFound()
                    .build();
        } else if (newsRepository.findById(id).isPresent()) {
            newsRepository.deleteById(id);
            return ResponseEntity
                    .ok()
                    .build();
        }
        return ResponseEntity
                .badRequest()
                .build();

    }

    @Override
    @PutMapping
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<NewsOpenAPI> editNewsById(NewsOpenAPI newsOpenAPI) {
        News news = newsMapper.toNews(newsOpenAPI);
        Long id = news.getId();
        if (newsRepository.findById(id).isEmpty()) {
            return ResponseEntity
                    .notFound()
                    .build();
        } else if (newsRepository.findById(id).isPresent()) {
            newsRepository.save(news);
            return ResponseEntity
                    .ok()
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
        if (!newsRepository.findAll().isEmpty()) {
            return ResponseEntity
                    .ok()
                    .body(
                            newsRepository
                                    .findAll()
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
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<NewsOpenAPI> showNewsIdInfo(@Valid @RequestParam("id") Long id) {
        if (newsRepository.findById(id).isEmpty()) {
            return ResponseEntity
                    .notFound()
                    .build();
        } else if (newsRepository.findById(id).isPresent()) {
            News news = newsRepository.findById(id).get();
            return ResponseEntity
                    .ok()
                    .body(
                            newsMapper.toOpenApi(news)
                    );
        }
        return ResponseEntity
                .badRequest()
                .build();
    }
}
