package sovcombank.jabka.newsservice.mappers;

import org.mapstruct.Mapper;
import ru.sovkombank.openapi.model.NewsOpenAPI;
import sovcombank.jabka.newsservice.models.News;

@Mapper(componentModel = "spring")
public interface NewsMapper {
    News toNews(NewsOpenAPI newsOpenAPI);
    NewsOpenAPI toOpenApi(News news);
}
