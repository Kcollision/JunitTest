本仓库用于NUAA课程《软件测试实验四：测试管理》的环境搭建，该README文档为实验报告内容。

## 搭建实验环境

### 创建GitHub仓库

​		首先在GitHub创建仓库JunitTest，仓库链接为[Kcollision/JunitTest](https://github.com/Kcollision/JunitTest)，然后进入实验三中单元测试的项目根地址，使用git将本地代码推送到该仓库：

```shell
git init
git remote add origin https://github.com/Kcollision/JunitTest.git
git add .
git commit -m "Initial commit"
git branch -M main
git push -u origin main
```

​		上传完成后结果如下图所示：

<img src="https://bu.dusays.com/2025/04/09/67f65830f31f6.png" alt="搭建测试环境" style="zoom:50%;" />

### 配置GitHub Actions（持续集成）

​		在GitHub仓库中进入Actions标签页，点击Set up a workflow yourself，创建一个新的工作流文件.github/workflows/ci.yml，内容如下：

```yaml
name: Java CI
on:
  push:
    branches:
      - main
  pull_request:
    branches:
      - main
jobs:
  build:
    runs-on: ubuntu-latest
    steps:
    - name: Checkout code
      uses: actions/checkout@v3
    - name: Set up JDK
      uses: actions/setup-java@v3
      with:
        java-version: '17'
        distribution: 'temurin'
    - name: Build with Maven
      run: mvn clean package
    - name: Run tests
      run: mvn test
```

### 配置缺陷管理模块

​		GitHub仓库中默认启用了Issues功能，因此该部分不用特别配置。

## 配置Maven项目

​		更新项目中的pom.xml如下，增加了maven的编译库，从而能够进行打包和执行的Actions测试。

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.demo</groupId>
    <artifactId>junitest</artifactId>
    <version>1.0-SNAPSHOT</version>

    <dependencies>
        <!-- JUnit -->
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.13.2</version>
            <scope>test</scope>
        </dependency>
        <!-- Apache Commons Math -->
        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-math3</artifactId>
            <version>3.6.1</version>
        </dependency>
    </dependencies>

  <build>
    <plugins>
      <!-- 打包为 JAR -->
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-jar-plugin</artifactId>
        <version>3.2.0</version>
        <configuration>
          <archive>
            <manifest>
              <mainClass>com.example.Main</mainClass>
            </manifest>
          </archive>
        </configuration>
      </plugin>
      <!-- 执行测试 -->
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-surefire-plugin</artifactId>
        <version>2.22.2</version>
      </plugin>
    </plugins>
  </build>
</project>

```

## 持续集成中的测试任务

​		每次提交代码到 GitHub 仓库时，GitHub Actions 会自动触发工作流。因此在 Actions 日志中可以看到以下内容：

1. Maven 构建日志。

2. 测试用例执行结果（包括成功和失败的测试）

   此处由于没有修改代码，因此测试中Build with Maven失败，如下所示：

<img src="https://bu.dusays.com/2025/04/09/67f65e82a9311.png" style="zoom:50%;" />

<img src="https://bu.dusays.com/2025/04/09/67f65f0bc8753.png" alt="build失败详细" style="zoom: 50%;" />

​		现在修改AbstractRealMatrix.java为正确的代码，上传到仓库后进行集成测试，结果如下：

![build成功](https://bu.dusays.com/2025/04/09/67f6602d60243.png)

​		该项目build成功的Actions日志如下，可以看到测试用例均通过，并且成功构建jar包。

![maven构建成功jar包](https://bu.dusays.com/2025/04/09/67f6618f3cd0c.png)

## 缺陷提交流程

### 提交缺陷

1. 在 GitHub 仓库中点击 Issues -> New Issue。

2. 填写缺陷信息，格式参考：

   * 标题：简洁描述问题（如 "Login failed when password is empty"）。

   - 描述：
     - 复现步骤（Steps to Reproduce）
     - 预期结果（Expected Behavior）
     - 实际结果（Actual Behavior）
     - 环境（Environment）：OS、JDK 版本、浏览器等
     - 附件（Screenshots/Logs）

3. 添加标签（Labels）如 bug、high priority；分配负责人（Assignees）。

### 关联代码和 Issue

​		在提交代码时，可通过 Commit Message 关联 Issue（如Fix #123），自动关闭问题。

## 整体流程

​		综上所述，项目开发结合测试管理的一般流程如下所示：

1. 开发：在本地修改代码并提交到 GitHub。

2. 持续集成：GitHub Actions 自动运行mvn test，执行冒烟测试。

3. 测试失败：

   * 查看 Actions 日志定位失败用例。

   * 在 Issues 中提交 Bug，关联失败 Commit。

4. 修复 Bug：创建新分支修复问题，提交后触发 CI 验证。
5. 回归测试：通过后合并代码到main分支。
