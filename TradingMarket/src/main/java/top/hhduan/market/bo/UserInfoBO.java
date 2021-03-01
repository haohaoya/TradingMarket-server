package top.hhduan.market.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: duanhaohao
 * @date: 2020/12/25 16:43
 * @description: 修改用户信息入参
 * @version: 1.0
 */
@Setter
@Getter
@ToString(callSuper = true)
public class UserInfoBO extends BaseBO {

    private static final long serialVersionUID = 5372633025027598083L;
    /**
     * 昵称
     */
    private String userName;

    /**
     * 收获地址
     */
    private String address;

    /**
     * 用户头像
     */
    private MultipartFile userAvatar;

}
