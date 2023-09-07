package cn.lank8s.demo.operator;

import io.javaoperatorsdk.operator.api.reconciler.ControllerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

//@ComponentScan(
//		includeFilters = {
//				@ComponentScan.Filter(type = FilterType.ANNOTATION, value = ControllerConfiguration.class)
//		})
@ComponentScan
@SpringBootApplication
public class OperatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(OperatorApplication.class, args);
	}

}
