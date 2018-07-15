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
public class User_Message {
    private Integer id;

    private Integer usUserid;

    private String usMessage;

    private Integer usRoomid;

    private Date usSendtime;

}