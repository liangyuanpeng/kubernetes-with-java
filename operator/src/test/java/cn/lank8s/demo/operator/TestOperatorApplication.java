package cn.lank8s.demo.operator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestOperatorApplication {

	public static void main(String[] args) {
		SpringApplication.from(OperatorApplication::main).with(TestOperatorApplication.class).run(args);
	}

}
