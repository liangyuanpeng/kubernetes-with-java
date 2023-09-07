//package cn.lank8s.demo.operator.bean;
//
//import cn.lank8s.demo.operator.reconciler.MyTomcatReconciler;
//import io.fabric8.kubernetes.client.KubernetesClient;
//import io.javaoperatorsdk.operator.Operator;
//import org.springframework.stereotype.Component;
//
///** This component just showcases what beans are registered. */
//@Component
//public class SampleComponent {
//
//  private final Operator operator;
//
//  private final KubernetesClient kubernetesClient;
//
//  private final MyTomcatReconciler customServiceReconciler;
//
//  public SampleComponent(
//      Operator operator,
//      KubernetesClient kubernetesClient,
//      MyTomcatReconciler customServiceReconciler) {
//    this.operator = operator;
//    this.kubernetesClient = kubernetesClient;
//    this.customServiceReconciler = customServiceReconciler;
//  }
//}
