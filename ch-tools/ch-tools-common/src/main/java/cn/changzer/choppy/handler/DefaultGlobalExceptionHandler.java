package cn.changzer.choppy.handler;

import cn.changzer.choppy.enums.CommonCode;
import cn.changzer.choppy.exception.BizException;
import cn.changzer.choppy.result.Result;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.validation.BindException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 *
 */
//@ControllerAdvice(annotations = {RestController.class, Controller.class})
//@ResponseBody
@Slf4j
public abstract class DefaultGlobalExceptionHandler {
    @ExceptionHandler(BizException.class)
    public Result<String> bizException(BizException ex, HttpServletRequest request) {
        log.warn("BizException:", ex);
        return Result.result(ex.getCode(), "", ex.getMessage()).setPath(request.getRequestURI());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result httpMessageNotReadableException(HttpMessageNotReadableException ex, HttpServletRequest request) {
        log.warn("HttpMessageNotReadableException:", ex);
        String message = ex.getMessage();
        if (StrUtil.containsAny(message, "Could not read document:")) {
            String msg = String.format("无法正确的解析json类型的参数：%s", StrUtil.subBetween(message, "Could not read document:", " at "));
            return Result.result(CommonCode.PARAM_EX.getCode(), "", msg).setPath(request.getRequestURI());
        }
        return Result.result(CommonCode.PARAM_EX.getCode(), "", CommonCode.PARAM_EX.getMsg()).setPath(request.getRequestURI());
    }

    @ExceptionHandler(BindException.class)
    public Result bindException(BindException ex, HttpServletRequest request) {
        log.warn("BindException:", ex);
        try {
            String msgs = ex.getBindingResult().getFieldError().getDefaultMessage();
            if (StrUtil.isNotEmpty(msgs)) {
                return Result.result(CommonCode.PARAM_EX.getCode(), "", msgs).setPath(request.getRequestURI());
            }
        } catch (Exception ee) {
        }
        StringBuilder msg = new StringBuilder();
        List<FieldError> fieldErrors = ex.getFieldErrors();
        fieldErrors.forEach((oe) ->
                msg.append("参数:[").append(oe.getObjectName())
                        .append(".").append(oe.getField())
                        .append("]的传入值:[").append(oe.getRejectedValue()).append("]与预期的字段类型不匹配.")
        );
        return Result.result(CommonCode.PARAM_EX.getCode(), "", msg.toString()).setPath(request.getRequestURI());
    }


    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Result methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
        log.warn("MethodArgumentTypeMismatchException:", ex);
        MethodArgumentTypeMismatchException eee = (MethodArgumentTypeMismatchException) ex;
        StringBuilder msg = new StringBuilder("参数：[").append(eee.getName())
                .append("]的传入值：[").append(eee.getValue())
                .append("]与预期的字段类型：[").append(eee.getRequiredType().getName()).append("]不匹配");
        return Result.result(CommonCode.PARAM_EX.getCode(), "", msg.toString()).setPath(request.getRequestURI());
    }

    @ExceptionHandler(IllegalStateException.class)
    public Result illegalStateException(IllegalStateException ex, HttpServletRequest request) {
        log.warn("IllegalStateException:", ex);
        return Result.result(CommonCode.ILLEGALA_ARGUMENT_EX.getCode(), "", CommonCode.ILLEGALA_ARGUMENT_EX.getMsg()).setPath(request.getRequestURI());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Result missingServletRequestParameterException(MissingServletRequestParameterException ex, HttpServletRequest request) {
        log.warn("MissingServletRequestParameterException:", ex);
        StringBuilder msg = new StringBuilder();
        msg.append("缺少必须的[").append(ex.getParameterType()).append("]类型的参数[").append(ex.getParameterName()).append("]");
        return Result.result(CommonCode.ILLEGALA_ARGUMENT_EX.getCode(), "", msg.toString()).setPath(request.getRequestURI());
    }

    @ExceptionHandler(NullPointerException.class)
    public Result nullPointerException(NullPointerException ex, HttpServletRequest request) {
        log.warn("NullPointerException:", ex);
        return Result.result(CommonCode.NULL_POINT_EX.getCode(), "", CommonCode.NULL_POINT_EX.getMsg()).setPath(request.getRequestURI());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public Result illegalArgumentException(IllegalArgumentException ex, HttpServletRequest request) {
        log.warn("IllegalArgumentException:", ex);
        return Result.result(CommonCode.ILLEGALA_ARGUMENT_EX.getCode(), "", CommonCode.ILLEGALA_ARGUMENT_EX.getMsg()).setPath(request.getRequestURI());
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public Result httpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException ex, HttpServletRequest request) {
        log.warn("HttpMediaTypeNotSupportedException:", ex);
        MediaType contentType = ex.getContentType();
        if (contentType != null) {
            StringBuilder msg = new StringBuilder();
            msg.append("请求类型(Content-Type)[").append(contentType.toString()).append("] 与实际接口的请求类型不匹配");
            return Result.result(CommonCode.MEDIA_TYPE_EX.getCode(), "", msg.toString()).setPath(request.getRequestURI());
        }
        return Result.result(CommonCode.MEDIA_TYPE_EX.getCode(), "", "无效的Content-Type类型").setPath(request.getRequestURI());
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    public Result missingServletRequestPartException(MissingServletRequestPartException ex, HttpServletRequest request) {
        log.warn("MissingServletRequestPartException:", ex);
        return Result.result(CommonCode.REQUIRED_FILE_PARAM_EX.getCode(), "", CommonCode.REQUIRED_FILE_PARAM_EX.getMsg()).setPath(request.getRequestURI());
    }

    @ExceptionHandler(ServletException.class)
    public Result servletException(ServletException ex, HttpServletRequest request) {
        log.warn("ServletException:", ex);
        String msg = "UT010016: Not a multi part request";
        if (msg.equalsIgnoreCase(ex.getMessage())) {
            return Result.result(CommonCode.REQUIRED_FILE_PARAM_EX.getCode(), "", CommonCode.REQUIRED_FILE_PARAM_EX.getMsg());
        }
        return Result.result(CommonCode.SYSTEM_BUSY.getCode(), "", ex.getMessage()).setPath(request.getRequestURI());
    }

    @ExceptionHandler(MultipartException.class)
    public Result multipartException(MultipartException ex, HttpServletRequest request) {
        log.warn("MultipartException:", ex);
        return Result.result(CommonCode.REQUIRED_FILE_PARAM_EX.getCode(), "", CommonCode.REQUIRED_FILE_PARAM_EX.getMsg()).setPath(request.getRequestURI());
    }

    /**
     * jsr 规范中的验证异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Result<String> constraintViolationException(ConstraintViolationException ex, HttpServletRequest request) {
        log.warn("ConstraintViolationException:", ex);
        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
        String message = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(";"));
        return Result.result(CommonCode.BASE_VALID_PARAM.getCode(), "", message).setPath(request.getRequestURI());
    }

    /**
     * spring 封装的参数验证异常， 在conttoller中没有写result参数时，会进入
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result methodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        log.warn("MethodArgumentNotValidException:", ex);
        return Result.result(CommonCode.BASE_VALID_PARAM.getCode(), "", ex.getBindingResult().getFieldError().getDefaultMessage()).setPath(request.getRequestURI());
    }

    /**
     * 其他异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Result<String> otherExceptionHandler(Exception ex, HttpServletRequest request) {
        log.warn("Exception:", ex);
        return Result.result(CommonCode.SYSTEM_BUSY.getCode(), "", CommonCode.SYSTEM_BUSY.getMsg()).setPath(request.getRequestURI());
    }


    /**
     * 返回状态码:405
     */
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public Result<String> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex, HttpServletRequest request) {
        log.warn("HttpRequestMethodNotSupportedException:", ex);
        return Result.result(CommonCode.METHOD_NOT_ALLOWED.getCode(), "", CommonCode.METHOD_NOT_ALLOWED.getMsg()).setPath(request.getRequestURI());
    }



    @ExceptionHandler(SQLException.class)
    public Result sqlException(SQLException ex, HttpServletRequest request) {
        log.warn("SQLException:", ex);
        return Result.result(CommonCode.SQL_EX.getCode(), "", CommonCode.SQL_EX.getMsg()).setPath(request.getRequestURI());
    }


}
