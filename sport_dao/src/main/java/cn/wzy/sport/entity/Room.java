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
public class Room {
    private Integer id;

    private String roSportname;

    private Date roStartdate;

    private Date roEnddate;

    private String roLocation;

    private Integer roOrinum;

    private Integer roNum;

    private Integer roStatus;

    private Integer roOwnerid;

}