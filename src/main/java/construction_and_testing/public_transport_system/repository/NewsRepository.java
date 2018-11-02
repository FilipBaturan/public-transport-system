package construction_and_testing.public_transport_system.repository;

import construction_and_testing.public_transport_system.domain.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Long> {
}
