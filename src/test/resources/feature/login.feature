@Login
Feature: Test login the CSDN
  Assert the login function

  Scenario Outline: Successful Login the CSDN——该例子对应的step没实现
    Given 打开CSDN登录页面 "https://passport.csdn.net/account/login?"
    When 输入用户名 "<username>" 密码 "<password>"
    Then 检查是否登录成功 "<exceptText>"

    Examples:test login
      | username 	 | password 	| exceptText	|
      | test1   	 | password1 	| 登录成功   	|
      | test2   	 | password2 	| 登录失败   	|