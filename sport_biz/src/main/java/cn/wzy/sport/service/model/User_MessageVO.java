package cn.wzy.sport.service.model;

import cn.wzy.sport.entity.User_Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Create by Wzy
 * on 2018/7/21 15:50
 * 不短不长八字刚好
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class User_MessageVO extends User_Message {
    private String usName;
    private Integer usStatus;
}
