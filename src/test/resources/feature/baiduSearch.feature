@Search @smoke
Feature: 百度搜索
      打开百度进行搜索

  @BaiduSearch
  Scenario: 百度搜索selenium
    Given Go to baidu home
    When  I find baidu logo
    And   Type the search text "selenium"
    And   Click the submit
    Then  Wait the query result
