package cn.wzy.sport.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * Create by Wzy
 * on 2018/7/21 17:22
 * 不短不长八字刚好
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class MessageVO {
    private Integer roomId;

    private String usName;

    private Integer total;

    private Date sendTime;

    private Integer userId;
}
