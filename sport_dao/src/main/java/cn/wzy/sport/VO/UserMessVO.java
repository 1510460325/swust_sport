package cn.wzy.sport.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UserMessVO {

	private int userId;

	private String nickName;

	private String img;

	private String msg;

	private Date date;
}
