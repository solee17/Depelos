package edu.egg.depelos.controladores;

import java.util.Enumeration;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorsController implements ErrorController {

    @RequestMapping(value = "/error", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView renderErrorPage(HttpServletRequest httpRequest) {

        ModelAndView errorPage = new ModelAndView("error");
        String errorMsg = "";
        int httpErrorCode = getErrorCode(httpRequest);

        switch (httpErrorCode) {
            case 400: {
                errorMsg = "Page not found.";
                break;
            }
            case 403: {
                errorMsg = "You dont have permissions to do that!";
                break;
            }
            case 401: {
                errorMsg = "You dont have permissions to do that!";
                break;
            }
            case 404: {
                errorMsg = "Resource not found.";
                break;
            }
            case 500: {
                errorMsg = "Server error.";
                break;
            }
        }

        errorPage.addObject("code", httpErrorCode);
        errorPage.addObject("message", errorMsg);
        return errorPage;
    }

    private int getErrorCode(HttpServletRequest httpRequest) {
        
        Map map = httpRequest.getParameterMap();
        for(Object key : map.keySet()){
            String[] values = (String[]) map.get(key);
            for(String value : values){
                System.out.println(key.toString() + ": " + value);
            }
        }
        
        
        Enumeration<String> attribute = httpRequest.getAttributeNames();
        while(attribute.hasMoreElements()){
            String key = attribute.nextElement();
            System.out.println(key + ":" + httpRequest.getAttribute(key));
        }
        
        return (Integer) httpRequest.getAttribute("javax.servlet.error.status_code");
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }

}

