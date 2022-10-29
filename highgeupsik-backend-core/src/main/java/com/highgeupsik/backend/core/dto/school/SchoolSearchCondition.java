package com.highgeupsik.backend.core.dto.school;

import com.highgeupsik.backend.core.exception.ErrorMessages;
import com.highgeupsik.backend.core.model.school.Region;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SchoolSearchCondition {

    @NotNull(message = ErrorMessages.REGION_NOT_NULL)
    private Region region;
    private String keyword;
}
