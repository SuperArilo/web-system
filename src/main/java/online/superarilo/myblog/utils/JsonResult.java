package online.superarilo.myblog.utils;


import org.springframework.http.HttpStatus;

import java.util.HashMap;

public class JsonResult extends HashMap<String, Object> {

    private static final String CODE = "code";

    private static final String MESSAGE = "message";

    private static final String DATA = "data";

    private static final String SUCCESS = "success";

    private static final String ERROR = "error";

    private JsonResult() {
        this.put(CODE, HttpStatus.OK.value());
        this.put(MESSAGE, SUCCESS);
        this.put(DATA, null);
    }

    private JsonResult(Integer code) {
        this();
        this.put(CODE, code);
    }

    private JsonResult(Integer code, String message) {
        this(code);
        this.put(MESSAGE, message);
    }

    private JsonResult(Integer code, String message, Object data) {
        this(code, message);
        this.put(DATA, data);
    }

    public static JsonResult OK() {
        return new JsonResult(HttpStatus.OK.value(), SUCCESS);
    }

    public static JsonResult OK(String message) {
        return new JsonResult(HttpStatus.OK.value(), message);
    }

    public static JsonResult OK(String message, Object data) {
        return new JsonResult(HttpStatus.OK.value(), message, data);
    }

    public static JsonResult OK(Object data) {
        return new JsonResult(HttpStatus.OK.value(), SUCCESS, data);
    }

    public static JsonResult ERROR(Integer code, String message) {
        return new JsonResult(code, message);
    }

    public static JsonResult ERROR(Integer code, Object data) {
        return new JsonResult(code, ERROR, data);
    }

    public static JsonResult PAGE(PageUtils page) {
        return OK(page);
    }

    public JsonResult put(String key, Object value) {
        super.put(key, value);
        return this;
    }

}
