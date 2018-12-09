package cn.wzy.sport.service.VO;

import cn.wzy.sport.entity.Sport_Info;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Sport_InfoVO extends Sport_Info {
	private String file1;
	private String file2;
}
