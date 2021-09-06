package com.cacf.cdt.bffclone.entity.idd;

import com.vladmihalcea.hibernate.type.array.IntArrayType;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import com.vladmihalcea.hibernate.type.json.JsonType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Singular;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.ArrayList;
import java.util.List;

@Embeddable
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "number")
@Builder
@AllArgsConstructor
public class IDDBorrower {
    private String number;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

    @Type(type = "json")
    @Column(columnDefinition = "json")
    private CollectionWrapper<IDDDocument> attachments = new CollectionWrapper<>();

}
