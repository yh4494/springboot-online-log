package com.allens.onlinelog.apis;

import com.allens.onlinelog.config.AllensLogConstant;
import com.allens.onlinelog.config.AllensLogProperties;
import com.allens.onlinelog.config.LocalCacheHelper;
import com.allens.onlinelog.tools.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * OnlineLogApis
 *
 * @author allens
 * @since 2024/5/8
 */
@Controller
@Slf4j
@RequestMapping("/log")
public class OnlineLogApis {

    @Resource
    AllensLogProperties allensLogProperties;

    @Value("${server.servlet.context-path:}")
    private String contextPath;

    @Value("${logging.online.title:在线日志}")
    private String title;

    public OnlineLogApis() {
        log.info("OnlineLogApis init...");
    }

    @ModelAttribute
    public void sharedModel(Model model) {
        model.addAttribute("contextPath", contextPath);
        model.addAttribute("title", title);
    }

    @GetMapping("/home")
    public String logPage(@ModelAttribute ModelMap shareModel) {
        return "log";
    }

    /**
     * 通过offset从日志文件中获取日志, 每次返回500K
     * @param offset 日志定位
     * @return
     */
    @GetMapping("/seek")
    @ResponseBody
    public OffsetResult getLog(String offset) {
        long offsetCount = Long.parseLong(offset) < 0 ? 0 : Long.parseLong(offset);
        if (StringUtils.isEmpty(allensLogProperties.getLogFile())) {
            return OffsetResult.empty();
        }
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(allensLogProperties.getLogFile(), "r")) {
            randomAccessFile.seek(offsetCount);
            byte[] bytes = new byte[1024 * 1024 * 2];
            // 只读取2MB每次
            int read = randomAccessFile.read(bytes, 0, 1024 * 1024 * 2);
            if (read == -1) {
                return OffsetResult.empty(offsetCount);
            }
            return new OffsetResult(offsetCount + (long) read, new String(bytes, 0, read));
        } catch (FileNotFoundException e) {
            log.error("file not found", e);
        } catch (IOException e) {
            log.error("io exception", e);
        }
        return OffsetResult.empty();
    }

    @GetMapping("/login")
    public String logLoginPage(@ModelAttribute ModelMap shareModel) {
        return "log-login";
    }

    @PostMapping("/login")
    @ResponseBody
    public String login(@RequestBody LogLoginVo logLoginVo,
                        HttpServletResponse response) {
        if (allensLogProperties.getUsername().equals(logLoginVo.getUsername())
                && allensLogProperties.getPassword().equals(logLoginVo.getPassword())) {
            String token = TokenUtils.getToken();
            response.addCookie(new Cookie(AllensLogConstant.ALLENS_ONLINE_LOG, token));
            LocalCacheHelper.setCacheObject(token, "TRUE");
            return "success";
        }
        return "fail";
    }

}
