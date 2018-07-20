package cn.wzy.sport.service.model;

import cn.wzy.sport.entity.Complain_Info;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Create by Wzy
 * on 2018/7/20 22:23
 * 不短不长八字刚好
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ComplainVO extends Complain_Info {
    private String usName;
}
