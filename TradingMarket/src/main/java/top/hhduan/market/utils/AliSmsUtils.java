package top.hhduan.market.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sms.model.v20160927.SingleSendSmsRequest;
import com.aliyuncs.sms.model.v20160927.SingleSendSmsResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: duanhaohao
 * @date: 2020/9/15 12:40
 * @description: 阿里云短信发送工具类
 * @version: 1.0
 */
@Slf4j
public class AliSmsUtils {

    private static String accessKeyId = PropertiesUtil.get("accessKeyId");

    private static String secret = PropertiesUtil.get("secret");

    private static String endpointName = PropertiesUtil.get("endpointName");

    private static String regionId = PropertiesUtil.get("regionId");

    private static String product = PropertiesUtil.get("product");

    private static String domain = PropertiesUtil.get("domain");

    private static String signName = PropertiesUtil.get("signName");

    private static String templateCode = PropertiesUtil.get("templateCode");


    /**
     * 发送验证码
     *
     * @param phoneNum
     * @param code
     * @return
     */
    public static String getSms(String phoneNum, String code) {
        try {
            IClientProfile profile = DefaultProfile.getProfile(regionId,
                    accessKeyId, secret);
            DefaultProfile.addEndpoint(endpointName, regionId, product, domain);
            IAcsClient client = new DefaultAcsClient(profile);
            SingleSendSmsRequest request = new SingleSendSmsRequest();
            // 控制台创建的签名名称
            request.setSignName(signName);
            // 控制台创建的模板CODE
            request.setTemplateCode(templateCode);
            // 短信模板中的变量；数字需要转换为字符串；个人用户每个变量长度必须小于15个字符。"
            request.setParamString("{\"code\":\"" + code + "\"}");
            // 接收号码
            request.setRecNum(phoneNum);
            //log.info("阿里云发送短信上送报文：{}", JSON.toJSONString(request));
            SingleSendSmsResponse httpResponse = client.getAcsResponse(request);
            //log.info("阿里云发送短信返回报文：{}", JSON.toJSONString(httpResponse));
            return httpResponse.getModel();
        } catch (ClientException e) {
            //log.info("阿里云短信发送失败", e);
        }
        return null;
    }

    public static void main(String[] args) {
        String s = getSms("15991823319", "666666");
        System.out.println(s);
    }
}
