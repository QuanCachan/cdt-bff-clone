package com.cacf.cdt.bffclone.entity.cdt.user;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Delegate;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Table(name = "cdt_user_idd_files_seen")
@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder
@AllArgsConstructor
public class CDTUserIDDFileSeen {
    @NotNull
    @EmbeddedId
    @Delegate
    private CDTUserLastIDDFileSeenID id;
    @NotNull
    private ObjectNode iddFileRaw;

    @Getter
    @Setter
    @Embeddable
    public static class CDTUserLastIDDFileSeenID implements Serializable {
        @NotBlank
        private String cdtUserNumber;
        @NotBlank
        private String iddFileNumber;
    }
}
