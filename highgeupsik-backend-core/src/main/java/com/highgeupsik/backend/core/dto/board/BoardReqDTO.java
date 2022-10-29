package com.highgeupsik.backend.core.dto.board;

import com.highgeupsik.backend.core.dto.image.UploadFileDTO;
import com.highgeupsik.backend.core.model.board.Category;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class BoardReqDTO {

    private String title;
    private String content;
    private Category category;
    private List<UploadFileDTO> uploadFileDTOList = new ArrayList<>();
}
