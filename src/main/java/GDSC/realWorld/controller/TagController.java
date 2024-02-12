package GDSC.realWorld.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import GDSC.realWorld.service.TagService;

@RestController
@RequestMapping("/api/tags")
public class TagController {
    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public ResponseEntity<Map<String, List<String>>> getTags() {
        List<String> tags = tagService.getAllTags();
        Map<String, List<String>> response = new HashMap<>();
        response.put("tags", tags);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
