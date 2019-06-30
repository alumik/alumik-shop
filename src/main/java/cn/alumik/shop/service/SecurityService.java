package cn.alumik.shop.service;

public interface SecurityService {

    String findLoggedInUsername();

    void autoLogin(String username, String password);
}
