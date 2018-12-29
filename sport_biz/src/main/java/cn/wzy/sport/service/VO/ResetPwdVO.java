package cn.wzy.sport.service.VO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ResetPwdVO {

	private Integer oldPwd;

	private String newPwd;

	private Integer userId;
}
