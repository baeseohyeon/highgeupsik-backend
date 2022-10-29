package com.highgeupsik.backend.api.service.board;

import com.highgeupsik.backend.api.repository.board.BoardRepository;
import com.highgeupsik.backend.api.repository.user.UserRepository;
import com.highgeupsik.backend.core.exception.ErrorMessages;
import com.highgeupsik.backend.core.exception.ResourceNotFoundException;
import com.highgeupsik.backend.core.model.UploadFile;
import com.highgeupsik.backend.core.model.board.Board;
import com.highgeupsik.backend.core.model.board.Category;
import com.highgeupsik.backend.core.model.user.User;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.highgeupsik.backend.core.dto.board.BoardReqDTO;
import com.highgeupsik.backend.core.dto.image.UploadFileDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public Board saveBoard(Long userId, String title, String content, Category category) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.USER_NOT_FOUND));
        return boardRepository.save(Board.builder()
            .user(user)
            .content(content)
            .title(title)
            .category(category)
            .region(user.getStudentCard().getSchool().getRegion())
            .build());
    }

    public Long makeBoard(Long userId, BoardReqDTO boardReqDTO) {
        Board board = saveBoard(userId, boardReqDTO.getTitle(), boardReqDTO.getContent(), boardReqDTO.getCategory());
        if (isImageExists(boardReqDTO)) {
            addUploadFiles(board, boardReqDTO.getUploadFileDTOList());
        }
        return board.getId();
    }

    private boolean isImageExists(BoardReqDTO boardReqDTO) {
        return !boardReqDTO.getUploadFileDTOList().isEmpty();
    }

    public Long updateBoard(Long userId, Long boardId, BoardReqDTO boardReqDTO) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.USER_NOT_FOUND));
        Board board = boardRepository.findById(boardId)
            .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.BOARD_NOT_FOUND));
        board.validateWriter(user);
        board.deleteFiles();
        if (isImageExists(boardReqDTO)) {
            addUploadFiles(board, boardReqDTO.getUploadFileDTOList());
        }
        board.updateBoard(boardReqDTO.getTitle(), boardReqDTO.getContent(), boardReqDTO.getCategory());
        return board.getId();
    }

    public void deleteBoard(Long userId, Long boardId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.USER_NOT_FOUND));
        Board board = boardRepository.findById(boardId)
            .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.BOARD_NOT_FOUND));
        board.validateWriter(user);
        board.delete();
    }

    public void addUploadFiles(Board board, List<UploadFileDTO> uploadFileDTOList) {
        board.setThumbnail(uploadFileDTOList.get(0).getFileDownloadUri());
        uploadFileDTOList.forEach(file -> board.setFile(new UploadFile(file.getFileName(), file.getFileDownloadUri())));
    }
}
