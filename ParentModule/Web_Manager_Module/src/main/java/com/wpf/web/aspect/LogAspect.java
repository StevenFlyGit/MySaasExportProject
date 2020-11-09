package com.wpf.web.aspect;

import com.wpf.domain.system.SysLog;
import com.wpf.domain.system.User;
import com.wpf.service.system.SysLogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 创建时间：2020/11/9
 * 控制日志记录的切面类
 * @author wpf
 */
@Component
@Aspect //标注一个切面类
public class LogAspect {

    @Autowired
    private SysLogService sysLogService;
    @Autowired
    HttpServletRequest request;

    /**
     * 配置事务通知，对controller中的方法增强记录日志的功能
     * 自动记录日志的方法：获取方法名、方法所在的类
     * 使用哪种通知? 环绕
     * 切入点表达式：
     * execution(* cn..UserServiceImpl.save())
     * |-- 表示拦截cn包及其所有子包下的UserServiceImpl类的save方法
     * |-- cn.. 省略包及其子包
     * |-- execution(* com..*.*(..))  会拦截到jar包中的一些（jar包中的很多类都是com开头的）
     * 最好不要这样些，建议写全一点：com.itheima..
     * @param pcj 需要执行的方法对象
     * @return 执行的结果
     */
    @Around("execution(* com.wpf.web.controller..*.*(..))") //采用环绕通知来记录日志，因为只有这种方式能获取执行方法的信息
    public Object saveLog(ProceedingJoinPoint pcj) {

        try {
            //先执行方法
            Object result = pcj.proceed();

            //记录日志信息到数据库中
            String userIp = request.getRemoteAddr(); //获取访问用户的Ip
            User user = (User) request.getSession().getAttribute("LoginUser");//从session域中获取用户对象

            //获取用户的相关信息(先要进行非空校验)
            String userName = "";
            String companyName = "";
            String companyId = "";
            if (user != null) {
                userName = user.getUserName();
                companyName = user.getCompanyName();
                companyId = user.getCompanyId();
            }

            //创建并封装日志对象，才有lomBook的方式
            SysLog log = SysLog.builder()
                    .companyId(companyId)
                    .companyName(companyName)
                    .time(new Date())
                    .ip(userIp)
                    .method(pcj.getSignature().getName()) //获取当前方法的名称
                    .action(pcj.getTarget().getClass().getName()) //获取目标对象的全名
                    .userName(userName)
                    .build();

            //调用SysLog业务来存储日志信息
            sysLogService.saveLog(log);

            return result;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            throw new RuntimeException(throwable);
        }
    }

}
