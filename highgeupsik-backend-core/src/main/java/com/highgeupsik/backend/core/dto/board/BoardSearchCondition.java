package com.highgeupsik.backend.core.dto.board;

import com.highgeupsik.backend.core.exception.ErrorMessages;
import com.highgeupsik.backend.core.model.board.Category;
import com.highgeupsik.backend.core.model.school.Region;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class BoardSearchCondition {

    @NotNull(message = ErrorMessages.REGION_NOT_NULL)
    private Region region;
//    @NotNull(message = CATEGORY_NOT_NULL)
    private Category category;

    private String keyword;

    private Integer likeCount;
}
