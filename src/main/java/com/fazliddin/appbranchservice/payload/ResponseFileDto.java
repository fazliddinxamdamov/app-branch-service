package com.fazliddin.appbranchservice.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseFileDto {

    private String name;

    private String originalName;

    private String url;

    private String type;

    private long size;
}
