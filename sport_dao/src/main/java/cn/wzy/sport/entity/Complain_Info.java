package cn.wzy.sport.entity;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Complain_Info {
    private Integer id;

    private Integer coUserid;

    private String coContent;

    private Date coCreatdate;
}