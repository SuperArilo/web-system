package online.superarilo.myblog.utils;

import lombok.Data;

import java.util.List;

@Data
public class PageUtil<T> extends Page {

    /**
     * 数据
     */
    private List<T> data;



}
