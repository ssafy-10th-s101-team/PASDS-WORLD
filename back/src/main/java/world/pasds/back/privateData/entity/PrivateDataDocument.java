package world.pasds.back.privateData.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@JsonIgnoreProperties(ignoreUnknown = true)
@Document(indexName = "private_data")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrivateDataDocument {

    @Id
    private String id;

    @Setter
    private String title;

    private Long privateDataId;

    private Long organizationId;

    private Long teamId;

    @Setter
    private String organizationName;

    @Setter
    private String teamName;

}
