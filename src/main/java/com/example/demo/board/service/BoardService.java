package com.example.demo.board.service;

import org.springframework.stereotype.Service;
import com.example.demo.board.domain.BoardResponseDTO;
import com.example.demo.board.domain.UserBoardResponseDTO;
import java.util.List;
import com.example.demo.board.dao.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class BoardService {

    @Autowired
    private BoardMapper boardMapper;

    public List<BoardResponseDTO> list() {
        System.out.println("debug >>> BoardService list");
        return boardMapper.listRow();
    }

    public List<UserBoardResponseDTO> history(String id) {
        System.out.println("debug >>> BoardService history");
        return boardMapper.myHistoryRow(id);
    }

}
