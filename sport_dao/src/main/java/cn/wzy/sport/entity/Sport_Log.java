package cn.wzy.sport.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Sport_Log {
    private Integer id;

    private String spName;

    private Integer spType;

    private Integer spUserid;

    private Date spSportdate;

}