package world.pasds.back.privateData.repository.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import world.pasds.back.privateData.entity.PrivateDataDocument;

public interface PrivateDataSearchRepository extends ElasticsearchRepository<PrivateDataDocument, String> {

    PrivateDataDocument findByPrivateDataId(Long privateDataId);
}
