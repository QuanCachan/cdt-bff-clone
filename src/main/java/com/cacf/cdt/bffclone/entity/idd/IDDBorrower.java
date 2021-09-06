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

@TypeDefs({
        @TypeDef(name = "string-array", typeClass = StringArrayType.class),
        @TypeDef(name = "int-array", typeClass = IntArrayType.class),
        @TypeDef(name = "json", typeClass = JsonType.class)
})
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
    @Column(columnDefinition = "jsonb")
    @Singular
    private List<IDDBorrowerDocument> attachments = new ArrayList<>();

    @Type(type = "json")
    @Column(columnDefinition = "jsonb")
    @Singular
    private List<IDDBorrowerDocument> documents = new ArrayList<>();
}
