package cn.wzy.sport.service.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author wzy 不短不长八字刚好.
 * @since 2018/10/1 19:56
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class AvatarVo {

	private Integer userId;

	private String avatar;
}
