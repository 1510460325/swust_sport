package cn.wzy.sport.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Role_Auth {
    private Integer id;

    private Integer roUrlid;

    private Integer roAllowrole;

    }