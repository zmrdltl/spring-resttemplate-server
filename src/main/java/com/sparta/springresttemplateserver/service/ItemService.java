package com.sparta.springresttemplateserver.service;

import com.sparta.springresttemplateserver.dto.ItemResponseDto;
import com.sparta.springresttemplateserver.dto.ShoppingResponseDto;
import com.sparta.springresttemplateserver.dto.UserRequestDto;
import com.sparta.springresttemplateserver.entity.Item;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Service
public class ItemService {

    private final List<Item> itemList = Arrays.asList(
            new Item("Mac", 3_888_000),
            new Item("iPad", 1_230_000),
            new Item("iPhone", 1_550_000),
            new Item("Watch", 450_000),
            new Item("AirPods", 350_000)
    );

    public ShoppingResponseDto searchShopping(String query, String baseUrl) {
        String keyword = query.strip().toLowerCase(Locale.ROOT);
        List<ShoppingResponseDto.ShoppingItem> items = itemList.stream()
                .filter(item -> shoppingTitle(item).toLowerCase(Locale.ROOT).contains(keyword))
                .map(item -> new ShoppingResponseDto.ShoppingItem(
                        shoppingTitle(item),
                        UriComponentsBuilder.fromUriString(baseUrl)
                                .path("/api/server/get-call-obj")
                                .queryParam("query", "{query}")
                                .encode()
                                .buildAndExpand(item.getTitle())
                                .toUriString(),
                        baseUrl + "/images/product.svg",
                        String.valueOf(item.getPrice()),
                        "실습몰"
                ))
                .toList();

        return new ShoppingResponseDto(items);
    }

    private String shoppingTitle(Item item) {
        // 기존 Mac 조회 실습은 유지하고, 쇼핑 검색에서는 MacBook으로 표시한다.
        return "Mac".equals(item.getTitle()) ? "MacBook" : item.getTitle();
    }

    public Item getCallObject(String query) {
        for (Item item : itemList) {
            if(item.getTitle().equals(query)) {
                return item;
            }
        }
        return null;
    }

    public ItemResponseDto getCallList() {
        ItemResponseDto responseDto = new ItemResponseDto();
        for (Item item : itemList) {
            responseDto.setItems(item);
        }
        return responseDto;
    }

    public Item postCall(String query, UserRequestDto userRequestDto) {
        System.out.println("userRequestDto.getUsername() = " + userRequestDto.getUsername());
        System.out.println("userRequestDto.getPassword() = " + userRequestDto.getPassword());

        return getCallObject(query);
    }

    public ItemResponseDto exchangeCall(String token, UserRequestDto requestDto) {
        System.out.println("token = " + token);
        System.out.println("requestDto.getUsername() = " + requestDto.getUsername());
        System.out.println("requestDto.getPassword() = " + requestDto.getPassword());

        return getCallList();
    }
}
