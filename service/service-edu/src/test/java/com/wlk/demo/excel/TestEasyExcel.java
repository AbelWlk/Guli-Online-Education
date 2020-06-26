package com.wlk.demo.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

public class TestEasyExcel {
    public static void main(String[] args) {
        /*String fileName = "E:\\write.xlsx";
        EasyExcel.write(fileName, DemoData.class).sheet("write01").doWrite(data());*/


        String fileName = "E:\\write.xlsx";
        EasyExcel.read(fileName,DemoData.class,new ExcelListener()).sheet("write01").doRead();


    }

    //循环设置要添加的数据，最终封装到list集合中
    private static List<DemoData> data() {
        List<DemoData> list = new ArrayList<DemoData>();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setSno(i);
            data.setSname("张三" + i);
            list.add(data);
        }
        return list;
    }
}
