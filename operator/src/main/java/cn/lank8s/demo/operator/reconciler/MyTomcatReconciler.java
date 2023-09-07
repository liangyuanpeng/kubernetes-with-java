package cn.lank8s.demo.operator.reconciler;

import cn.lank8s.demo.operator.apis.MyTomcat;
import io.fabric8.kubernetes.api.model.*;
import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.api.model.apps.DeploymentBuilder;
import io.fabric8.kubernetes.api.model.apps.DeploymentSpec;
import io.fabric8.kubernetes.api.model.apps.DeploymentSpecBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.informers.ResourceEventHandler;
import io.fabric8.kubernetes.client.informers.SharedIndexInformer;
import io.fabric8.zjsonpatch.internal.guava.Lists;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.api.reconciler.ControllerConfiguration;
import io.javaoperatorsdk.operator.api.reconciler.Reconciler;
import io.javaoperatorsdk.operator.api.reconciler.UpdateControl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;

/** A very simple sample controller that creates a service with a label. */
@ControllerConfiguration
@Component
public class MyTomcatReconciler implements Reconciler<MyTomcat> {

  private static final Logger log = LoggerFactory.getLogger(MyTomcatReconciler.class);

  private final KubernetesClient kubernetesClient;

  public MyTomcatReconciler() {
    this(new DefaultKubernetesClient());
    SharedIndexInformer<Deployment> inform = kubernetesClient.apps()
            .deployments()
            .inform();
    inform.addEventHandler(new ResourceEventHandler<Deployment>() {
      @Override
      public void onAdd(Deployment deployment) {
        log.info("deployment.event.onAdd:{}",deployment.getMetadata().getName());
      }

      @Override
      public void onUpdate(Deployment deployment, Deployment t1) {
        log.info("deployment.event.onUpdate:{}",deployment.getMetadata().getName());
      }

      @Override
      public void onDelete(Deployment deployment, boolean b) {
        log.info("deployment.event.onDelete:{}",deployment.getMetadata().getName());
      }
    });

  }

  public MyTomcatReconciler(KubernetesClient kubernetesClient) {
    this.kubernetesClient = kubernetesClient;
  }

  @Override
  public UpdateControl<MyTomcat> reconcile(
          MyTomcat resource, Context<MyTomcat> context) {
    log.info("Reconciling: {}", resource.getMetadata().getName());

    DeploymentSpec deploymentSpec = new DeploymentSpec();
    PodTemplateSpec podTemplateSpec = new PodTemplateSpec();
    deploymentSpec.setTemplate(podTemplateSpec);
    deploymentSpec.setReplicas(1);
    LabelSelector labelSelector = new LabelSelector();
    deploymentSpec.setSelector(labelSelector);

    Map<String,String> labels = new HashMap<>();
    labels.put("app","tomcat");
    labelSelector.setMatchLabels(labels);

    ObjectMeta objectMeta = new ObjectMeta();
    objectMeta.setLabels(labels);
    podTemplateSpec.setMetadata(objectMeta);

    PodSpec podSpec = new PodSpec();
    podTemplateSpec.setSpec(podSpec);
    Container container = new Container();
    container.setName("tomcat");
    container.setImage(resource.getSpec().getImage());
    List list = new ArrayList<>();
    list.add(container);
    podSpec.setContainers(list);

    var mt = new DeploymentBuilder()
    .withNewMetadata()
            .withName(resource.getSpec().getName())
            .withLabels(labels)
                    .endMetadata()
                            .withSpec(deploymentSpec)
                                    .build();
    mt.addOwnerReference(resource);

    kubernetesClient.apps()
            .deployments()
            .inNamespace(resource.getMetadata().getNamespace())
            .createOrReplace(mt);



    return UpdateControl.updateResource(resource);
  }
}
