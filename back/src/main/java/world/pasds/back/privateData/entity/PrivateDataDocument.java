package world.pasds.back.privateData.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.ZonedDateTime;

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

    private String organizationName;

    private String teamName;

    @Field(type = FieldType.Date, format = {}, pattern = "uuuu-MM-dd'T'HH:mm:ssXXX")
    private ZonedDateTime timestamp;
}
