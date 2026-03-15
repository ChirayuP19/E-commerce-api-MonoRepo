package com.chirayu.ecommerce.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "customers")
public class CustomerDocument {
    @Id
    private String id;

    @Field(type = FieldType.Text, analyzer = "standard")
    private String firstname;

    @Field(type = FieldType.Text, analyzer = "standard")
    private String lastname;

    @Field(type = FieldType.Keyword)
    private String email;
}
