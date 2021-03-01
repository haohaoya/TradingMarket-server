package top.hhduan.market;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@MapperScan(basePackages = "top.hhduan.market.mapper")
@Slf4j
public class TradingMarketApplication {
    public static void main(String[] args){
        SpringApplication.run(TradingMarketApplication.class,args);

        CountDownLatch countDownLatch = new CountDownLatch(1);

        // 心跳检测程序是否存活
        try{
            while(true){
                if(!countDownLatch.await(60, TimeUnit.SECONDS)){
                    log.info("----------------- SecondaryApplication server is alive---------------------");
                }
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
