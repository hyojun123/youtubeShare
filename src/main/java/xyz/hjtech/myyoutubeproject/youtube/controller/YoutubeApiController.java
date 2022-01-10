package xyz.hjtech.myyoutubeproject.youtube.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import xyz.hjtech.myyoutubeproject.youtube.model.BoastEntity;
import xyz.hjtech.myyoutubeproject.youtube.model.LikeYoutubeEntity;
import xyz.hjtech.myyoutubeproject.youtube.model.YoutubeEntity;
import xyz.hjtech.myyoutubeproject.youtube.model.dto.*;
import xyz.hjtech.myyoutubeproject.youtube.service.YoutubeService;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
public class YoutubeApiController {

    @GetMapping("/health")
    public String healthCheck() {
        return "success";
    }

    private final YoutubeService youtubeService;

    @GetMapping("/youtube/{searchTxt}")
    public List<GetYoutubeDto> findBySearchTxt(@PathVariable String searchTxt, @RequestParam("userUuid") String userUuid) {
        return youtubeService.findBySearchTxt(searchTxt, userUuid);
    }

    @GetMapping("/youtube/like")
    public GetLikeEntity findByUserUuid(@RequestParam("userUuid") String userUuid){
        return youtubeService.findByUserUuid(userUuid);
    }

    @PostMapping("/youtube/like")
    public LikeYoutubeEntity postLikeVideo(@RequestBody PostLikeDto likeDto) {
        return youtubeService.postLikeVideo(likeDto);
    }

    @DeleteMapping("/youtube/like")
    public void deleteLikeVideo(@RequestBody DeleteLikeDto deleteLikeDto) {
        youtubeService.deleteLikeVideo(deleteLikeDto);
    }

    @GetMapping("/youtube/my/boast")
    public BoastEntity findMyBoast(@RequestParam("userUuid") String userUuid) {
        return youtubeService.findMyBoast(userUuid);
    }

    @PostMapping("/youtube/boast")
    public BoastEntity insertBoardList(@RequestBody PostBoastDto boastDto) {
        return youtubeService.postBoast(boastDto);
    }

    @GetMapping("/youtube/boast/list")
    public List<BoastDto> findAllBoastList() {
        return youtubeService.findAllBoastList();
    }

    @GetMapping("/youtube/boast/{id}")
    public BoastEntity findBoastById(@PathVariable Long id) {
        return youtubeService.findBoastById(id);
    }

    @GetMapping("/youtube/boast")
    public List<BoastDto> findBoastBySearchTxt(@RequestParam("searchTxt") String searchTxt) {
        return youtubeService.findBoastBySearchTxt(searchTxt);
    }

    @PostMapping("/youtube/boast/like/{id}")
    public BoastEntity postLikeBoast(@PathVariable("id") Long id) {
        return youtubeService.postLikeBoast(id);
    }

    @DeleteMapping("/youtube/boast")
    public void deleteBoast(@RequestBody DeleteBoastDto boastDto) {
        youtubeService.deleteBoast(boastDto);
    }
}
