package world.pasds.back.privateData.repository.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import world.pasds.back.privateData.entity.PrivateDataDocument;

import java.util.List;

public interface PrivateDataSearchRepository extends ElasticsearchRepository<PrivateDataDocument, String> {

    PrivateDataDocument findByPrivateDataId(Long privateDataId);

    List<PrivateDataDocument> findAllByOrganizationId(Long organizationId);

    List<PrivateDataDocument> findAllByTeamId(Long teamId);
}
