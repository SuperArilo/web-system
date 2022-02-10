package online.superarilo.myblog.vo;

import lombok.Data;
import online.superarilo.myblog.entity.SysDictionary;

import java.util.List;

@Data
public class SysDictionaryVO extends SysDictionary {

    private List<SysDictionary> children;

    private String username;
}
