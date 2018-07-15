package cn.wzy.sport.service.model;

import cn.wzy.sport.entity.User_Info;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Create by Wzy
 * on 2018/7/14 16:22
 * 不短不长八字刚好
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class LoginResult extends User_Info {
    private int status;
}
