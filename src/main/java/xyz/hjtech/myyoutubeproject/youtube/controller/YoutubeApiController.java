package xyz.hjtech.myyoutubeproject.youtube.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
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
}
