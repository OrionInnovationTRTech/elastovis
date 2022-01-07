package elastovis.loganalysis.repository;

import elastovis.loganalysis.document.Pm;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@EnableElasticsearchRepositories
public interface PmRepository extends ElasticsearchRepository<Pm, Long> {

}

