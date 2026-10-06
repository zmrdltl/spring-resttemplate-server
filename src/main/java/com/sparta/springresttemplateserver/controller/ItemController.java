package com.sparta.springresttemplateserver.controller;

import com.sparta.springresttemplateserver.dto.ItemResponseDto;
import com.sparta.springresttemplateserver.dto.ShoppingResponseDto;
import com.sparta.springresttemplateserver.dto.UserRequestDto;
import com.sparta.springresttemplateserver.entity.Item;
import com.sparta.springresttemplateserver.service.ItemService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/server")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    // 종료된 네이버 쇼핑 API 대신 사용하는 로컬 상품 검색 실습
    @GetMapping("/search/shop")
    public ShoppingResponseDto searchShopping(@RequestParam String query) {
        String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .build()
                .toUriString();
        return itemService.searchShopping(query, baseUrl);
    }

    @GetMapping("/get-call-obj")
    public Item getCallObject(@RequestParam String query) {
        return itemService.getCallObject(query);
    }

    @GetMapping("/get-call-list")
    public ItemResponseDto getCallList() {
        return itemService.getCallList();
    }

    @PostMapping("/post-call/{query}")
    public Item postCall(@PathVariable String query, @RequestBody UserRequestDto requestDto) {
        return itemService.postCall(query, requestDto);
    }

    @PostMapping("/exchange-call")
    public ItemResponseDto exchangeCall(@RequestHeader("X-Authorization") String token, @RequestBody UserRequestDto requestDto) {
        return itemService.exchangeCall(token, requestDto);
    }
}
