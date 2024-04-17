# Ioc Container
스프링 IoC 컨테이너는 스프링의 핵심 요소로, 애플리케이션 구성 요소를 효율적으로 관리하며 깔끔하고 유지보수가 용이한 코드 작성을 지원하여 애플리케이션 품질을 향상시킵니다.  

[목차](../spring_core.md)
1. 컨테이너 개요
2. 구성 메타테이터
3. 컨테이너 인스턴스
4. 컨테이너 빈 사용
## 1. 컨테이너 개요
스프링 프레임워크의 IoC 컨테이너는 애플리케이션의 객체 생성과 구성 및 관리를 담당합니다.
개발자가 구성과 의존성만 정의하면 컨테이너가 정의된 기반으로 애플리케이션을 실행시 객체를 생성하고 의존성을 주입합니다.
### 컨테이너의 핵심 개념
- Bean: 컨테이너가 관리하는 객체이며 빈의 생성과 생명주기 의존성 관리를 담당합니다.
- BeanFactory: 스프링 IoC 컨테이너의 가장 기본 형태입니다.
- ApplicationContext: BeanFactory의 하위 인터페이스로 향상된 기능을 제공합니다.
## 2. 구성 메타테이터
컨테이너는 구성 메타 데이터를 사용하여 애플리케이션의 객체들이 생성되고 상호 연결되어 있는지를 알게 됩니다.
스프링은 다양한 방식으로 이 구성 메타데이터를 제공합니다.
### XML 기반 구성
XML 구성은 스프링 초기 버전부터 지원되는 방식입니다. 파일내 `<bean>`태그를 사용하여 객체 정의와 의존성을 선언합니다.
```xml
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
                           http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="myBean" class="com.example.MyClass">
        <property name="dependency" ref="myDependency"/>
    </bean>

    <bean id="myDependency" class="com.example.MyDependency"/>
</beans>
```
위 예제에서 myBean은 MyClass의 인스턴스이며 myDependency라는 이름으로 다른 빈에 대한 의존성을 가집니다.
### 어노테이션 기반 구성
코드 내에서 직접 빈의 메타테이터를 선언합니다. `@Component` `@Service` `@Repository`와 같은 어노테이션을 사용하여 클래스를 
스프링 빈으로 자동 등록하고 `@Autowired`를 통해 의존성을 자동으로 주입받습니다.
```java
@Component
public class MyBean {
    private final MyDependency myDependency;

    @Autowired
    public MyBean(MyDependency myDependency) {
        this.myDependency = myDependency;
    }
}

@Component
public class MyDependency {
}
```
위 예제는 MyBean과 MyDependency 모두 스프링에 의해 관리 되는 빈으로 자동 등록됩니다. MyBean은 생성자를 통해 MyDependency에 대한 의존성 주입을 받습니다.
### 자바 기반 구성
자바 기반 구성은 `@Configuration` 어노테이션이 붙은 클래스 내에서 빈을 정의합니다. 이방식은 `@Bean` 어노테이션을 사용하여 메소드 레벨에서 빈의 인스턴스를 생성하고 구성합니다.
```java
@Configuration
public class AppConfig {
    @Bean
    public MyBean myBean() {
        return new MyBean(myDependency());
    }

    @Bean
    public MyDependency myDependency() {
        return new MyDependency();
    }
}
```
이 예제에서 AppConfig 클래스는 두 개의 메소드 myBean과 myDependency를 정의하고 있으며, 각각 MyBean과 MyDependency 타입의 빈을 생성하고 반환합니다. myBean 메소드에서는 myDependency 메소드를 호출하여 의존성을 주입합니다.
## 3. 컨테이너 초기화
스프링에서 IoC 컨테이너는 주로 `ApplicationContext` 인터페이스의 구현체를 사용하여 인스턴스화 됩니다.
구현체로는 `ClassPathXmlApplicationContext` `FileSystemXmlApplicationContext` `AnnotationConfigApplicationContext`등이 있습니다.
### XML 기반
`ClassPathXmlApplicationContext` 또는 `FileSystemXmlApplicationContext`를 사용하여 XML 설정 파일에서 빈 정의를 로드할 수 있습니다.
```java
ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
```
여기서 "applicationContext.xml"은 클래스 패스에 위치한 XML 설정 파일입니다.

### 자바 기반
`AnnotationConfigApplicationContext`를 사용하여 자바 클래스에서 빈 정의를 로드할 수 있습니다. 이 클래스는 `@Configuration` 어노테이션이 붙은 자바 클래스를 설정 정보로 사용합니다.
```java
ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
```
## 4. 컨테이너 빈 사용
컨테이너를 초기화를 하면 ApplicationContext 인터페이스의 메소드을 사용하여 빈을 사용할수 있습니다.

