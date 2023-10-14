package sovcombank.jabka.newsservice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sovcombank.jabka.newsservice.models.News;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
}
