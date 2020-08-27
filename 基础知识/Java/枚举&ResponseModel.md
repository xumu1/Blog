

# 枚举&ResponseModel

# 枚举

```java
import org.junit.Test;

public class test1 {

    public enum Size{
        SMALL("S"),LARGE("L");

        private String abbreviation;

        private Size(String abbreviation){
            this.abbreviation = abbreviation;
        }
        public String getAbbreviation(){
            return abbreviation;
        }
    }

    public static void main(String[] args) {
        Size large = Size.LARGE;
        Size s = Size.valueOf("SMALL");

        System.out.println(large);
        System.out.println(s);

        String abbreviation = large.abbreviation;
        String abbreviation1 = large.getAbbreviation();

        System.out.println(abbreviation);
        System.out.println(abbreviation1);
    }
}
```

**output：**

LARGE
SMALL
L
L

```java
package edu.ustc.iot.pojo.enums;

import lombok.Getter;

@Getter
public enum ResponseEnum {
    SUCCESS(0,"成功"),

    PASSWORD_ERROR(1,"密码错误"),

    USERNAME_EXIST(2,"用户名已存在"),

    NEED_LOGIN(10,"用户未登录，请先登录"),

    PARAM_ERROR(3,"参数错误"),

    EMAIL_EXIST(4,"邮箱已存在"),

    ERROR(-1,"服务端错误"),

    USERNAME_OR_PASSWORD_ERROR(11, "用户名或者密码错误"),

    DELETE_ERROR(5,"删除失败"),

    INSERT_ERROR(6,"添加失败"),

    UPDATE_ERROR(7,"更新失败")

    ;
    Integer code;

    String desc;

    ResponseEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
```



# ResponseModel

```java
/**
 * 返回给客户端的响应体
 */
public final class ResponseModel {

    private String code;

    private String message;

    private boolean success;

    private Object data;

    public String getCode() {
        return code;
    }

    public ResponseModel setCode(String code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ResponseModel setMessage(String message) {
        this.message = message;
        return this;
    }

    public boolean isSuccess() {
        return success;
    }

    public ResponseModel setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public Object getData() {
        return data;
    }

    public ResponseModel setData(Object data) {
        this.data = data;
        return this;
    }

    public static ResponseModel success() {
        return new ResponseModel().setSuccess(true).setCode(SUCCESS_CODE).setMessage(SUCCESS_MESSAGE);
    }

    public static ResponseModel success(Object data) {
        return new ResponseModel().setSuccess(true).setCode(SUCCESS_CODE).setMessage(SUCCESS_MESSAGE).setData(data);
    }

    public static ResponseModel fail() {
        return new ResponseModel().setSuccess(false).setCode(FAIL_CODE).setMessage(FAIL_MESSAGE);
    }

    public static ResponseModel fail(String message) {
        return new ResponseModel().setSuccess(false).setCode(FAIL_CODE).setMessage(message);
    }

    public static ResponseModel exception(String message) {
        return new ResponseModel().setSuccess(false).setCode(EXCEPTION_CODE).setMessage(message);
    }





    public final static String SUCCESS_CODE = "0000";

    public final static String SUCCESS_MESSAGE = "success";


    public final static String FAIL_CODE = "3000";

    public final static String FAIL_MESSAGE = "fail";


    public final static String EXCEPTION_CODE = "5000";

}
```