# 函数计算 selenium chrome java 模板项目

该项目模板是一个在阿里云函数计算平台 java8 环境下运行 chromedriver 的模板项目。

## 依赖工具

本项目是在 MacOS 下开发的，涉及到的工具是平台无关的，对于 Linux 和 Windows 桌面系统应该也同样适用。在开始本例之前请确保如下工具已经正确的安装，更新到最新版本，并进行正确的配置。

* [Docker](https://www.docker.com/)
* [Fun](https://github.com/aliyun/fun)
* [Fcli](https://github.com/aliyun/fcli)

Fun 和 Fcli 工具依赖于 docker 来模拟本地环境。

对于 MacOS 用户可以使用 [homebrew](https://brew.sh/) 进行安装：

```bash
brew cask install docker
brew tap vangie/formula
brew install fun
brew install fcli
```

Windows 和 Linux 用户安装请参考：

1. https://github.com/aliyun/fun/blob/master/docs/usage/installation.md
2. https://github.com/aliyun/fcli/releases

安装好后，记得先执行 `fun config` 初始化一下配置。

**注意**, 如果你已经安装过了 fun，确保 fun 的版本在 2.10.2 以上。

```bash
$ fun --version
2.10.1
```

## 快速开始

### 初始化

使用 fun init 命令可以快捷地将本模板项目初始化到本地。

```bash
fun init vangie/selenium-java-example
```

## 安装依赖

```bash
$ fun install
...
```

### 本地测试

测试代码 ChromeDemo 的内容为：

```java
public class ChromeDemo implements StreamRequestHandler {


    public void handleRequest(InputStream inputStream,
                              OutputStream outputStream,
                              Context context) throws IOException {

        System.setProperty("webdriver.chrome.driver", "/code/chromedriver");

        ChromeOptions options = new ChromeOptions();
        options.setBinary("/code/headless-chromium");
        options.addArguments("--disable-extensions"); // disabling extensions
        options.addArguments("--disable-gpu"); // applicable to windows os only
        options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
        options.addArguments("--no-sandbox"); // Bypass OS security model
        options.addArguments("--headless");

        WebDriver driver = new ChromeDriver(options);

        driver.get("https://ide.fc.aliyun.com");

        outputStream.write(("Page title is: " + driver.getTitle() + "\n").getBytes());

        driver.quit();

    }

}
```

### 本地运行

```bash
$ mvn package && fun local invoke selenium
...
FC Invoke Start RequestId: 68c83b4c-b053-479c-9b0e-9503582ccb56
handle user request is com.aliyun.fc.selenium.ChromeDemo::handleRequest
cache is null!
Starting ChromeDriver 2.35.528139 (47ead77cb35ad2a9a83248b292151462a66cd881) on port 20652
Only local connections are allowed.
Mar 05, 2019 11:34:27 AM org.openqa.selenium.remote.ProtocolHandshake createSession
INFO: Detected dialect: OSS
Page title is: 云端集成开发环境
FC Invoke End RequestId: 68c83b4c-b053-479c-9b0e-9503582ccb56


RequestId: 68c83b4c-b053-479c-9b0e-9503582ccb56          Billed Duration: 5265 ms        Memory Size: 1998 MB    Max Memory Used: 240 MB

```

### 部署

```bash
$ mvn package && fun deploy
```

### 执行

```bash
$  fcli function invoke -s chrome -f selenium
  Page title is: 云端集成开发环境
```

## 参考阅读

1. https://github.com/smithclay/lambdium
2. https://medium.com/clog/running-selenium-and-headless-chrome-on-aws-lambda-fb350458e4df