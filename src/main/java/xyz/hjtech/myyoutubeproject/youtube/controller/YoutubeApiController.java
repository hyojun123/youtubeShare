package xyz.hjtech.myyoutubeproject.youtube.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import xyz.hjtech.myyoutubeproject.youtube.model.YoutubeEntity;
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
}
