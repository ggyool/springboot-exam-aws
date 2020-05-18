package org.ggyool.exam01.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
// final 붙어있는 field 생성자 생성
@RequiredArgsConstructor
public class HelloResponseDto {
    private final String name;
    private final int amount;
}
