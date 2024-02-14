package GDSC.realWorld.login;

import GDSC.realWorld.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
public class LoginInterceptor implements  HandlerInterceptor {

    public static final String LOG_ID = "logId";
    //이 부분 필드가 세션 저장 한 Key 값이랑 달라서 이 값으론 불러오지 못함

//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        String requestURI = request.getRequestURI();
//        String uuid = UUID.randomUUID().toString();
//
//        request.setAttribute(LOG_ID, uuid);
//
//        if (handler instanceof HandlerMethod) {
//            HandlerMethod hm = (HandlerMethod) handler;
//        }
//
//        log.info("REQUEST [{}][{}][{}]", uuid, requestURI, handler);
//        return true;
//    }


    //세션 검사 부분인 preHandle 재작성
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        //추후 CORS preflight 관련
        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }
        //세션 존재하지 않는 경우
        if (session == null) {
            response.sendError(401);
            return false;
        }
        //유저의 세션이 유효하지 않는 경우
        User user = (User) session.getAttribute(SessionConst.LOGIN_USER);
        if (user == null) {
            response.sendError(401);
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("pohstHandler [{}]", modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String requestURI = request.getRequestURI();
        String uuid = (String) request.getAttribute(LOG_ID);
        //여기서 LOG_ID의 값대로 세션을 저장하지 않아서 불러오지 못함
        log.info("RESPONSE [{}][{}][{}]", uuid, requestURI, handler);

        if (ex != null) {
            log.error("afterCompletion error:", ex);
        }
    }
}
