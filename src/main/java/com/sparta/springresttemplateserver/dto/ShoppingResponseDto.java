package com.sparta.springresttemplateserver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
public class ShoppingResponseDto {
    private final int total;
    private final int start = 1;
    private final int display;
    private final List<ShoppingItem> items;

    public ShoppingResponseDto(List<ShoppingItem> items) {
        this.items = List.copyOf(items);
        this.total = items.size();
        this.display = items.size();
    }

    @Getter
    @AllArgsConstructor
    public static class ShoppingItem {
        private final String title;
        private final String link;
        private final String image;
        private final String lprice;
        private final String mallName;
    }
}
