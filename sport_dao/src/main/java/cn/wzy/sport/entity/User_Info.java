package cn.wzy.sport.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class User_Info {
    private Integer id;

    private String usName;

    private String usPassword;

    private String usImg;

    private Integer usSex;

    private Integer usAge;

    private String usNickname;

    private String usSign;

    private String usClass;

    private String usMajor;

    private String usInstitution;

    private Integer usRoomid;

    private Integer usStatus;

    private Integer usRole;

}