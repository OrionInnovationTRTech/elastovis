package elastovis.loganalysis.document;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;
import elastovis.loganalysis.helper.Indices;

@Getter
@Setter
@ToString
@Setting(settingPath="static/es-settings.json")
@Document(indexName = Indices.PM_INDEX)
public class Pm {

    @Id
    @Field(type = FieldType.Keyword)
    private Long id;

    @Field(name = "metricAttribute", type = FieldType.Keyword)
    private String metricAttribute;

    @Field(name = "system", type = FieldType.Keyword)
    private String system;

    @Field(name = "entity", type = FieldType.Keyword)
    private String entity;

    @Field(name = "name", type = FieldType.Keyword)
    private String name;

    @Field(name = "metricValue", type = FieldType.Integer)
    private int metricValue;

    @Field(name = "metricDiff", type = FieldType.Integer)
    private int metricDiff;

    //  Pm File Attributes
    @Field(name = "timeStamp", type = FieldType.Date_Nanos)
    Long pmFileTimestamp;

    @Field(name = "fileName", type = FieldType.Keyword)
    String pmFileName;


}