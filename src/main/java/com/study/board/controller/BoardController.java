package com.study.board.controller;

import com.study.board.entity.Board;
import com.study.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    // 게시글 작성
    @GetMapping("/board/write") //localhost:8080/board/write
    public String boardWriteForm() {

        return "boardwrite";
    }

    // 게시글 작성 프로세스
    @PostMapping("/board/writepro")
    public String boardWritePro(Board board, Model model) {

        boardService.boardWrite(board);

        model.addAttribute("message", "글 작성이 완료되었습니다.");
        model.addAttribute("searchUrl", "/board/list");

        return "message";
    }

    // 게시글 리스트
    @GetMapping("/board/list")
    public String boardList(Model model) {

        model.addAttribute("list", boardService.boardList());

        return "boardlist";
    }

    // 게시글 보기
    @GetMapping("/board/view")  // localhost:8080/board/view?id=1
    public String boardView(Model model, Integer id) {

        model.addAttribute("board", boardService.boardView(id));

        return "boardview";
    }

    // 게시글 수정, 업데이트
    @GetMapping("/board/modify/{id}")
    public String boardModify(@PathVariable("id") Integer id,
                              Model model) {

        // 저장되어있던 글을 그대로 가져온다. 이후 수정작업을 시작
        model.addAttribute("board", boardService.boardView(id));

        return "boardmodify";
    }

    // 게시글 수정, 업데이트
    @PostMapping("/board/update/{id}")
    public String boardUpdate(@PathVariable("id") Integer id,
                              Board board, Model model) {
        // 기존 내용 가져오기
        Board boardTemp = boardService.boardView(id);

        // 기존 내용에 수정된 내용 덮어쓰기
        boardTemp.setTitle(board.getTitle());
        boardTemp.setContent(board.getContent());
        boardService.boardWrite(boardTemp);

        // 게시글 수정 후 메시지 처리
        model.addAttribute("message", "게시글이 수정 되었습니다.");
        model.addAttribute("searchUrl", "/board/list");

        return "message";
    }

    // 게시글 삭제
    @GetMapping("board/delete")
    public String boardDelete(Integer id) {

        boardService.boardDelete(id);
//        return "/board/list";
        return "redirect:/board/list";
    }
}
