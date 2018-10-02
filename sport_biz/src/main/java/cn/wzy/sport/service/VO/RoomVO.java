package cn.wzy.sport.service.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author wzy 不短不长八字刚好.
 * @since 2018/10/2 14:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class RoomVO {

	private Integer id;

	private String roSportname;

	private Long roStart;

	private Long roEnd;

	private String roLocation;

	private Integer roOrinum;

	private Integer roNum;

	private Integer roStatus;

	private Integer roOwnerid;
}
