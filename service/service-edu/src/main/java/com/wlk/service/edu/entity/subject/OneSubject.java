package com.wlk.service.edu.entity.subject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

//一级分类
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OneSubject {
    private String id;
    private String title;
    private List<TwoSubject> children = new ArrayList<>();
}
