package xyz.hjtech.myyoutubeproject.youtube.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import xyz.hjtech.myyoutubeproject.youtube.model.BoastEntity;
import xyz.hjtech.myyoutubeproject.youtube.model.YoutubeEntity;
import xyz.hjtech.myyoutubeproject.youtube.model.dto.BoastDto;
import xyz.hjtech.myyoutubeproject.youtube.model.dto.DeleteBoastDto;
import xyz.hjtech.myyoutubeproject.youtube.model.dto.PostBoastDto;
import xyz.hjtech.myyoutubeproject.youtube.service.YoutubeService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class YoutubeApiController {

    @GetMapping("/health")
    public String healthCheck() {
        return "success";
    }

    private final YoutubeService youtubeService;

    @CrossOrigin("*")
    @GetMapping("/youtube/{searchTxt}")
    public List<YoutubeEntity> findBySearchTxt(@PathVariable String searchTxt) {
        return youtubeService.findBySearchTxt(searchTxt);
    }

    @CrossOrigin("*")
    @GetMapping("/youtube/like")
    public List<YoutubeEntity> findByVideoIds(@RequestParam("ids") List<String> ids){
        return youtubeService.findByVideoIds(ids);
    }

    @CrossOrigin("*")
    @PostMapping("/youtube/boast")
    public void insertBoardList(@RequestBody PostBoastDto boastDto) {
        youtubeService.postBoast(boastDto);
    }

    @CrossOrigin("*")
    @GetMapping("/youtube/boast/list")
    public List<BoastDto> findAllBoastList() {
        return youtubeService.findAllBoastList();
    }

    @CrossOrigin("*")
    @GetMapping("/youtube/boast/{id}")
    public BoastEntity findBoastById(@PathVariable Long id) {
        return youtubeService.findBoastById(id);
    }

    @CrossOrigin("*")
    @GetMapping("/youtube/boast")
    public List<BoastDto> findBoastBySearchTxt(@RequestParam("searchTxt") String searchTxt) {
        return youtubeService.findBoastBySearchTxt(searchTxt);
    }

    @CrossOrigin("*")
    @PostMapping("/youtube/boast/like/{id}")
    public BoastEntity postLikeBoast(@PathVariable("id") Long id) {
        return youtubeService.postLikeBoast(id);
    }

    @CrossOrigin("*")
    @DeleteMapping("/youtube/boast")
    public void deleteBoast(@RequestBody DeleteBoastDto boastDto) {
        youtubeService.deleteBoast(boastDto);
    }
}
